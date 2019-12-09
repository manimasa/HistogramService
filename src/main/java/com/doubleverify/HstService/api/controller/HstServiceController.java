package com.doubleverify.HstService.api.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.doubleverify.HstService.api.HstServiceRequest;
import com.doubleverify.HstService.api.HstServiceResponse;
import com.doubleverify.HstService.service.HstService;

@RestController
@RequiredArgsConstructor
@Api(tags = "HistService API")
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HstServiceController implements HstServiceResourceApi {

	private final HstService service;

	@Override
	@RequestMapping(method = RequestMethod.POST, path = "v1/get-histogram/")
	@ApiOperation(value = "Return scores for texts on url")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 204, message = "No content")})
	public ResponseEntity<List<HstServiceResponse>> getHistogram(@Valid HstServiceRequest req) {
		List<HstServiceResponse> result = service.getHst(req.getUrl());

		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
