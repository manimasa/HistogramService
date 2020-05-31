package com.dols.HistogramService.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.dols.HistogramService.api.HistogramServiceRequest;
import com.dols.HistogramService.api.HistogramServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dols.HistogramService.service.HistogramService;

@RestController
@RequiredArgsConstructor
@Api(tags = "HistService API")
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HstServiceController implements HstServiceResourceApi {

	private final HistogramService service;

	@Override
	@RequestMapping(method = RequestMethod.POST, path = "v1/get-histogram/")
	@ApiOperation(value = "Return scores for texts on url")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 204, message = "No content")})
	public ResponseEntity<List<HistogramServiceResponse>> getHistogram(@Valid HistogramServiceRequest req) {
		List<HistogramServiceResponse> result = service.getHistogram(req.getUrl());

		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
