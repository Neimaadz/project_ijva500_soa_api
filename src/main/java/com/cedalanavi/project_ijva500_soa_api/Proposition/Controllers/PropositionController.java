package com.cedalanavi.project_ijva500_soa_api.Proposition.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.UserDetailsResource;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Services.AuthenticationService;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.AmendmentCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.VoteCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Services.PropositionService;
import com.cedalanavi.project_ijva500_soa_api.utils.SearchPropositionParams;
import com.cedalanavi.project_ijva500_soa_api.utils.VoteType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/proposition")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Proposition Controller", description = "${SWAGGER.TAG.CONDITION.REQUIRED}")
public class PropositionController {
	
	@Autowired
	PropositionService propositionService;
	
	@Autowired
	AuthenticationService authenticationService;

	@GetMapping("/search")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Search propositions/amendments",
			description = "Retrieve propositions/amendments by given params",
			responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved all users informations"),
			parameters = @Parameter(name = "params", schema = @Schema(allOf = SearchPropositionParams.class))
	)
	public List<PropositionResource> searchPropositions(@RequestParam(required = false) LinkedMultiValueMap<String, String> params) {
		return propositionService.searchProposition(params);
	}

	@PostMapping()
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Create proposition",
			description = "Create proposition",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully created a proposition"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource createProposition(HttpServletRequest httpServletRequest, @RequestBody PropositionCreateRequest propositionCreateRequest) {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getDetails();
		return propositionService.createProposition(userDetailsResource, propositionCreateRequest);
	}
	
	@PostMapping(value = "/amendment")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Create amendment",
			description = "Create amendment",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully created an amendment"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource createAmendment(HttpServletRequest httpServletRequest, @RequestBody AmendmentCreateRequest amendmentCreateRequest) {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		return propositionService.createAmendment(userDetailsResource, amendmentCreateRequest);
	}
	
	@PostMapping(value = "/vote")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Vote proposition/amendment",
			description = "Vote proposition/amendment",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully voted proposition/amendment"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource vote(HttpServletRequest httpServletRequest, @Schema(implementation = VoteType.class) String voteType,
			@RequestBody VoteCreateRequest voteCreateRequest) throws Exception {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		voteCreateRequest.voteType = voteType;
		return propositionService.vote(userDetailsResource, voteCreateRequest);
	}

}
