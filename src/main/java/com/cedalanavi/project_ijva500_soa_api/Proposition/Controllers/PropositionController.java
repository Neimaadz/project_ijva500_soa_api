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
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.CommentaryCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionResource;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Data.PropositionUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Proposition.Services.PropositionService;
import com.cedalanavi.project_ijva500_soa_api.utils.PropositionStatus;
import com.cedalanavi.project_ijva500_soa_api.utils.PropositionTypes;
import com.cedalanavi.project_ijva500_soa_api.utils.SearchPropositionParams;
import com.cedalanavi.project_ijva500_soa_api.utils.VoteTypes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
			summary = "Search propositions/amendments",
			description = "<h2>Retrieve propositions/amendments by given params</h2> "
					+ "<h4><b>Params are optionals.</b> To retrieve all data, remove <b>params</b></h4>",
			parameters = {
					@Parameter(name = "type", description = "<b>(optional)</b> Type of the suggestion to search)"),
					@Parameter(name = "params", description = "<b>(optional)</b> Search request parameters", schema = @Schema(allOf = SearchPropositionParams.class))
			},
			responses = @ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved all users informations")
	)
	public List<PropositionResource> searchPropositions(
			@RequestParam(required = false) @Schema(implementation = PropositionTypes.class) String type,
			@RequestParam(required = false) LinkedMultiValueMap<String, String> params) {
		return propositionService.searchProposition(params);
	}

	@PostMapping("/proposition")
	@Operation(
			summary = "Create proposition",
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
			summary = "Create amendment",
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
			summary = "Update proposition/amendment by id",
			description = "<h2>Update proposition/amendment by id</h2>"
					+ "<h4>Update proposition or amendment for only given informations. <b>Not all infos are required</b></h4>"
					+ "<h4>Can only update proposition/amendment added by the current user</h4>",
			parameters = {
					@Parameter(name = "id", description = "Proposition/amendment id to update"),
					@Parameter(name = "status", description = "Proposition/amendment status")
			},
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "<b>(optional)</b> Data to send"),
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully created an amendment"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource update(HttpServletRequest httpServletRequest,
			@PathVariable Long id, @RequestParam @Schema(implementation = PropositionStatus.class) String status,
			@RequestBody(required = false) PropositionUpdateRequest propositionUpdateRequest) throws Exception {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		if (propositionUpdateRequest == null) {
			propositionUpdateRequest = new PropositionUpdateRequest();
		}
		return propositionService.update(userDetailsResource, id, propositionUpdateRequest, status);
	}
	
	@PostMapping("/vote/{id}")
	@Operation(
			summary = "Vote proposition/amendment by id",
			description = "<h2>Vote proposition/amendment by id</h2>"
					+ "<h4>Vote a proposition/amendment by giving id. <b>Can only vote once per status</b></h4>",
			parameters = {
					@Parameter(name = "id", description = "Id of proposition/amendment to vote"),
					@Parameter(name = "voteType", description = "Type of vote")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully voted proposition/amendment"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public PropositionResource vote(HttpServletRequest httpServletRequest,
			@PathVariable Long id, @RequestParam @Schema(implementation = VoteTypes.class) String voteType) throws Exception {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		return propositionService.vote(userDetailsResource, id, voteType);
	}
	
	@PostMapping("/commentary/add/{id}")
	@Operation(
			summary = "Comment a proposition/amendment by id",
			description = "<h2>Comment a proposition/amendment by giving the id</h2>",
			parameters = {
					@Parameter(name = "id", description = "Id of proposition/amendment")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Comment successfully added")
			}
	)
	public PropositionResource addCommentary(HttpServletRequest httpServletRequest,
			@PathVariable Long id, @RequestBody CommentaryCreateRequest commentaryCreateRequest) throws Exception {
		UserDetailsResource userDetailsResource = authenticationService.currentSession(httpServletRequest);
		return propositionService.addCommentary(userDetailsResource, id, commentaryCreateRequest);
	}

}
