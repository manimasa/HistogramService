package com.artins.TextAnalizer.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/scorecard/scores/")
@Api(tags = "TextAnalizer API")
public interface TextAnalizerResourceApi {
	
	@GetMapping(value = "{playerName}")
	@ApiOperation(value = "Returns the player's core card")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok")})
	public List<TextAnalizerResponse> getScores(@Valid @PathVariable String playerName);
	
	@ApiOperation(value = "Save a player's score card")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok")})
	@PostMapping(value = "save", consumes = "application/json", produces = "application/json")
	public TextAnalizerResponse save(@Valid @RequestBody TextAnalizerRequest request);

}
