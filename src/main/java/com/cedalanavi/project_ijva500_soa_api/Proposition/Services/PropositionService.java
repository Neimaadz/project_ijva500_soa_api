package com.cedalanavi.project_ijva500_soa_api.Proposition.Services;

import java.util.List;

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
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.AmendmentCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.CommentaryCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.VoteCreateRequest;

@Service
public class PropositionService {

	@Value("${proposition.service.url}")
	String propositionServiceUrl;

	RestTemplate restTemplate = new RestTemplate();

	public List<PropositionResource> searchProposition(LinkedMultiValueMap<String, String> params) {
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(propositionServiceUrl + "/search")
				.queryParams(params)
		        .encode()
		        .toUriString();
		return restTemplate.exchange(urlTemplate, HttpMethod.GET,  null, new ParameterizedTypeReference<List<PropositionResource>>(){}, params).getBody();
	}

	public PropositionResource createProposition(UserDetailsResource userDetailsResource, PropositionCreateRequest propositionCreateRequest) {
		propositionCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<PropositionCreateRequest> request = new HttpEntity<PropositionCreateRequest>(propositionCreateRequest);
		return restTemplate.exchange(propositionServiceUrl, HttpMethod.POST,  request, PropositionResource.class).getBody();
	}
	
	public PropositionResource createAmendment(UserDetailsResource userDetailsResource, AmendmentCreateRequest amendmentCreateRequest) {
		amendmentCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<AmendmentCreateRequest> request = new HttpEntity<AmendmentCreateRequest>(amendmentCreateRequest);
		return restTemplate.exchange(propositionServiceUrl + "/amendment", HttpMethod.POST,  request, PropositionResource.class).getBody();
	}
	
	public PropositionResource update(UserDetailsResource userDetailsResource, Long id, PropositionUpdateRequest updateRequest, String status) throws Exception {
		updateRequest.setStatus(status);
		PropositionResource proposition = restTemplate.exchange(propositionServiceUrl + "/" + id, HttpMethod.GET, null, PropositionResource.class).getBody();
		ReferentialUserRight isAdmin = userDetailsResource.referentialUserRights.stream().filter(t -> t.label.equals("ROLE_ADMIN")).findFirst().orElse(null);

		if(!userDetailsResource.getIdUser().equals(proposition.idUser) && isAdmin == null) {
			throw new Exception("Error : can not update, permission denied");
		}
		HttpEntity<PropositionUpdateRequest> request = new HttpEntity<PropositionUpdateRequest>(updateRequest);
		return restTemplate.exchange(propositionServiceUrl + "/update/" + id, HttpMethod.PUT,  request, PropositionResource.class).getBody();
	}

	public PropositionResource vote(UserDetailsResource userDetailsResource, Long id, String voteType) throws Exception {
		VoteCreateRequest voteCreateRequest = new VoteCreateRequest();
		voteCreateRequest.setVoteType(voteType);
		voteCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<VoteCreateRequest> request = new HttpEntity<VoteCreateRequest>(voteCreateRequest);
		return restTemplate.exchange(propositionServiceUrl + "/vote/" + id, HttpMethod.POST,  request, PropositionResource.class).getBody();
	}

	public PropositionResource addCommentary(UserDetailsResource userDetailsResource, Long id, CommentaryCreateRequest commentaryCreateRequest) {
		commentaryCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<CommentaryCreateRequest> request = new HttpEntity<CommentaryCreateRequest>(commentaryCreateRequest);
		return restTemplate.exchange(propositionServiceUrl + "/commentary/add/" + id, HttpMethod.POST,  request, PropositionResource.class).getBody();
	}
}
