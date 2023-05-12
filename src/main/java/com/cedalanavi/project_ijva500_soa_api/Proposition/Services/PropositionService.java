package com.cedalanavi.project_ijva500_soa_api.Proposition.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.UserDetailsResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.AmendmentCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.VoteCreateRequest;

@Service
public class PropositionService {

	@Value("${proposition.service.url}")
	String propositionServiceUrl;
	
	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;


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

	public PropositionResource vote(UserDetailsResource userDetailsResource, VoteCreateRequest voteCreateRequest) throws Exception {
		voteCreateRequest.setIdUser(userDetailsResource.getIdUser());
		HttpEntity<VoteCreateRequest> request = new HttpEntity<VoteCreateRequest>(voteCreateRequest);
		return restTemplate.exchange(propositionServiceUrl + "/vote", HttpMethod.POST,  request, PropositionResource.class).getBody();
	}
}
