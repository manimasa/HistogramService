package com.artins.ServiceName.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artins.ServiceName.model.ServiceNameModel;

@Service
public interface ServiceName {
	List<ServiceNameModel> getScoreCard(String player);
	ServiceNameModel saveScoreCard(ServiceNameModel scoreCard);
}
