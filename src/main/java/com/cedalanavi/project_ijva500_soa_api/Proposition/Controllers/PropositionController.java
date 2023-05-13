package com.cedalanavi.project_ijva500_soa_api.Proposition.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.UserDetailsResource;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Services.AuthenticationService;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.AmendmentCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.VoteCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Services.PropositionService;
import com.cedalanavi.project_ijva500_soa_api.utils.SearchPropositionParams;
import com.cedalanavi.project_ijva500_soa_api.utils.VoteType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-proposition")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Proposition Controller", description = "<h4>${SWAGGER.TAG.CONDITION.REQUIRED}</h4>")
public class PropositionController {
	
	@Autowired
	PropositionService propositionService;
	
	@Autowired
	AuthenticationService authenticationService;

	@GetMapping("/search")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Search propositions/amendments",
			description = "<h2>Retrieve propositions/amendments by given params</h2> "
					+ "<h4><b>Params are optionals.</b> To retrieve all data, remove <b>params</b></h4>",
			responses = @ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved all users informations"),
			parameters = @Parameter(name = "params", schema = @Schema(allOf = SearchPropositionParams.class))
	)
	public List<PropositionResource> searchPropositions(@RequestParam(required = false) LinkedMultiValueMap<String, String> params) {
		return propositionService.searchProposition(params);
	}

	@PostMapping("/proposition")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Create proposition",
			description = "<h2>Create proposition</h2>",
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
	
	@PostMapping("/amendment")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Create amendment",
			description = "<h2>Create amendment</h2>",
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
	
	@PutMapping("/update/{id}")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Update proposition/amendment",
			description = "<h2>Update proposition/amendment by id</h2>"
					+ "<h4>Update proposition or amendment for only given informations. <b>Not all infos is required</b></h4>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully created an amendment"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource update(@PathVariable Long id, @RequestBody PropositionUpdateRequest updateRequest) {
		return propositionService.update(id, updateRequest);
	}
	
	@PostMapping("/vote")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Vote proposition/amendment",
			description = "<h2>Vote proposition/amendment</h2>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully voted proposition/amendment"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource vote(HttpServletRequest httpServletRequest,
			@Parameter(in = ParameterIn.QUERY, schema = @Schema(implementation = VoteType.class)) String voteType,
			@RequestBody VoteCreateRequest voteCreateRequest) throws Exception {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		voteCreateRequest.voteType = voteType;
		return propositionService.vote(userDetailsResource, voteCreateRequest);
	}

}
