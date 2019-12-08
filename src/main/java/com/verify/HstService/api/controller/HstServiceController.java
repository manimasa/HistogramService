package com.verify.HstService.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.verify.HstService.api.HstServiceRequest;
import com.verify.HstService.api.HstServiceResponse;
import com.verify.HstService.service.HstService;

@RestController
public class HstServiceController implements HstServiceResourceApi {
	@Autowired
	HstService service;

	@Override
	public ResponseEntity<List<HstServiceResponse>> scoreUrlTexts(@Valid HstServiceRequest req) {
		List<HstServiceResponse> result = service.getHst(req.getUrl());

		if (result != null) {
			return new ResponseEntity<List<HstServiceResponse>>(result, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
