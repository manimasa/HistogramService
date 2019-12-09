package com.doubleverify.HstService.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doubleverify.HstService.api.HstServiceRequest;
import com.doubleverify.HstService.api.HstServiceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


public interface HstServiceResourceApi {
	@PostMapping
	ResponseEntity<List<HstServiceResponse>> getHistogram(@Valid @RequestBody HstServiceRequest req);
}
