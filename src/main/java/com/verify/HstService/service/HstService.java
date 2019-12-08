package com.verify.HstService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.verify.HstService.api.HstServiceResponse;


@Service
public interface HstService {
	List<HstServiceResponse>  getHst(String url);
}
