package com.cedalanavi.project_ijva500_soa_api.Proposition.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.UserDetailsResource;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRight;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectResource;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.TeamResource;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.ProjectService;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.AmendmentCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.CommentaryCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionVoteResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.VoteCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.utils.PropositionStatus;
import com.cedalanavi.project_ijva500_soa_api.utils.VoteTypes;

@Service
public class PropositionService {

	@Value("${proposition.service.url}")
	String propositionServiceUrl;
	
	@Autowired
	ProjectService projectService;

	RestTemplate restTemplate = new RestTemplate();

	public List<PropositionResource> searchProposition(LinkedMultiValueMap<String, String> params) {
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(propositionServiceUrl + "/search")
				.queryParams(params)
		        .encode()
		        .toUriString();
		List<PropositionResource> propositionResources = restTemplate.exchange(urlTemplate, HttpMethod.GET,  null, new ParameterizedTypeReference<List<PropositionResource>>(){}, params).getBody();
		
		propositionResources.forEach(propositionResource -> {
			setPropositionProject(projectService.getAll(), propositionResource);
		});
		
		return propositionResources;
	}

	public PropositionResource createProposition(UserDetailsResource userDetailsResource, PropositionCreateRequest propositionCreateRequest) {
		List<ProjectResource> projectResources = projectService.getAll();
		propositionCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<PropositionCreateRequest> request = new HttpEntity<PropositionCreateRequest>(propositionCreateRequest);
		PropositionResource propositionResource = restTemplate.exchange(propositionServiceUrl, HttpMethod.POST,  request, PropositionResource.class).getBody();
		setPropositionProject(projectResources, propositionResource);
		
		return propositionResource;
	}
	
	public PropositionResource createAmendment(UserDetailsResource userDetailsResource, AmendmentCreateRequest amendmentCreateRequest) {
		List<ProjectResource> projectResources = projectService.getAll();
		amendmentCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<AmendmentCreateRequest> request = new HttpEntity<AmendmentCreateRequest>(amendmentCreateRequest);
		PropositionResource propositionResource = restTemplate.exchange(propositionServiceUrl + "/amendment", HttpMethod.POST,  request, PropositionResource.class).getBody();
		setPropositionProject(projectResources, propositionResource);
		
		return propositionResource;
	}
	
	public PropositionResource update(UserDetailsResource userDetailsResource, Long id, PropositionUpdateRequest updateRequest, String status) throws Exception {
		updateRequest.setStatus(PropositionStatus.valueOf(status));
		PropositionResource proposition = restTemplate.exchange(propositionServiceUrl + "/" + id, HttpMethod.GET, null, PropositionResource.class).getBody();
		ReferentialUserRight isAdmin = userDetailsResource.referentialUserRights.stream().filter(t -> t.label.equals("ROLE_ADMIN")).findFirst().orElse(null);

		if(!userDetailsResource.getIdUser().equals(proposition.idUser) && isAdmin == null) {
			throw new Exception("Error : can not update, permission denied");
		}
		HttpEntity<PropositionUpdateRequest> request = new HttpEntity<PropositionUpdateRequest>(updateRequest);
		PropositionResource propositionResource = restTemplate.exchange(propositionServiceUrl + "/update/" + id, HttpMethod.PUT,  request, PropositionResource.class).getBody();
		setPropositionProject(projectService.getAll(), propositionResource);
		
		return propositionResource;
	}

	public PropositionResource vote(UserDetailsResource userDetailsResource, Long id, String voteTypeReq) throws Exception {
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("id", id.toString());
		PropositionResource propositionSearch = searchProposition(params).stream().findFirst().orElse(null);
		VoteTypes voteType = VoteTypes.valueOf(voteTypeReq);
				
		if (propositionSearch.getStatus() == PropositionStatus.EVALUATION && voteType != VoteTypes.SUPPORTED
				|| propositionSearch.getStatus() != PropositionStatus.EVALUATION && voteType == VoteTypes.SUPPORTED) {
			throw new Exception("Error : conditional constraint between proposition status and vote type");
		}
		PropositionVoteResource alreadyVoted = new PropositionVoteResource();
		
		if (propositionSearch.getStatus() == PropositionStatus.EVALUATION) {
			alreadyVoted = propositionSearch.getPropositionVotes().stream().filter(t -> t.getIdUser().equals(userDetailsResource.getIdUser()) && t.getVoteType().equals(VoteTypes.SUPPORTED)).findFirst().orElse(null);
		} else {
			alreadyVoted = propositionSearch.getPropositionVotes().stream().filter(t -> t.getIdUser().equals(userDetailsResource.getIdUser()) && !t.getVoteType().equals(VoteTypes.SUPPORTED)).findFirst().orElse(null);
		}
		
		if (alreadyVoted != null) {
			throw new Exception("Error : already voted");
		}
		

		VoteCreateRequest voteCreateRequest = new VoteCreateRequest();
		voteCreateRequest.setVoteType(voteTypeReq);
		voteCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<VoteCreateRequest> request = new HttpEntity<VoteCreateRequest>(voteCreateRequest);
		PropositionResource propositionResource = restTemplate.exchange(propositionServiceUrl + "/vote/" + id, HttpMethod.POST,  request, PropositionResource.class).getBody();
		setPropositionProject(projectService.getAll(), propositionResource);
		
		int countUsers = 0;
		for (ProjectResource projectResource : propositionResource.getProject().getProjects()) {
			for (TeamResource teamResource : projectResource.getTeams()) {
				countUsers += teamResource.usersIds.size();
			}
		}
		
		if (countUsers == propositionResource.propositionVotes.size()) {
			PropositionUpdateRequest updateRequest = new PropositionUpdateRequest();
			updateRequest.setStatus(PropositionStatus.IN_PROGRESS);
			HttpEntity<PropositionUpdateRequest> requestUpdate = new HttpEntity<PropositionUpdateRequest>(updateRequest);
			propositionResource = restTemplate.exchange(propositionServiceUrl + "/update/" + id, HttpMethod.PUT,  requestUpdate, PropositionResource.class).getBody();
		}
		setPropositionProject(projectService.getAll(), propositionResource);
		
		return propositionResource;
	}

	public PropositionResource addCommentary(UserDetailsResource userDetailsResource, Long id, CommentaryCreateRequest commentaryCreateRequest) {
		commentaryCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<CommentaryCreateRequest> request = new HttpEntity<CommentaryCreateRequest>(commentaryCreateRequest);
		PropositionResource propositionResource = restTemplate.exchange(propositionServiceUrl + "/commentary/add/" + id, HttpMethod.POST,  request, PropositionResource.class).getBody();
		setPropositionProject(projectService.getAll(), propositionResource);
		
		return propositionResource;
	}
	
	private void setPropositionProject(List<ProjectResource> projectResources, PropositionResource propositionResource) {
		propositionResource.project = projectResources.stream().filter(project -> propositionResource.idProject == project.getId()).findFirst().orElse(null);
	}
}
