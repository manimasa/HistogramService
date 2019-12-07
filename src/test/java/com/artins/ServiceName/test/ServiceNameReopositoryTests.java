/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.artins.ServiceName.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.artins.ServiceName.entity.ServiceNameEntity;
import com.artins.ServiceName.repository.ServiceNameRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ServiceNameReopositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceNameRepository scorecardRepo;

    @Test
    public void testFindByPlayerName() {
        ServiceNameEntity scoreCard = new ServiceNameEntity(15, "Martins");
        entityManager.persist(scoreCard);

        List<ServiceNameEntity> findByPlayerName = scorecardRepo.findByPlayerNameOrderByScoreDesc(scoreCard.getPlayerName());

        assertThat(findByPlayerName).extracting(ServiceNameEntity::getPlayerName).containsOnly(scoreCard.getPlayerName());
    }
}