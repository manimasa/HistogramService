package com.verify.HstService.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verify.HstService.api.HstServiceRequest;
import com.verify.HstService.api.HstServiceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/get-histogram/")
@Api(tags = "HistService API")
public interface HstServiceResourceApi {
	
	@ApiOperation(value = "Return scores for texts on url")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), 
							@ApiResponse(code = 204, message = "No content")})
	@PostMapping(consumes = "application/json", produces = "application/json")
	public  ResponseEntity<List<HstServiceResponse>> getHistogram(@Valid @RequestBody HstServiceRequest req);
}
