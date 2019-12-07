package com.artins.TextAnalizer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.artins.TextAnalizer.entity.TextAnalizerEntity;

public interface TextAnalizerRepository extends CrudRepository<TextAnalizerEntity, Long> {

    List<TextAnalizerEntity> findByPlayerNameOrderByScoreDesc(String playerName);
}
