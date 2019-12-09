package com.doubleverify.HstService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doubleverify.HstService.api.HstServiceResponse;


public interface HstService {
	List<HstServiceResponse>  getHst(String url);
}
