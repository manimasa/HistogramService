package com.artins.ServiceName.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.artins.ServiceName.entity.ServiceNameEntity;

public interface ServiceNameRepository extends CrudRepository<ServiceNameEntity, Long> {

    List<ServiceNameEntity> findByPlayerNameOrderByScoreDesc(String playerName);
}
