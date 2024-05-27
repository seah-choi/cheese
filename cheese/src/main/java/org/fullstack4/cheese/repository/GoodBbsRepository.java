package org.fullstack4.cheese.repository;

import org.fullstack4.cheese.domain.GoodBbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodBbsRepository extends JpaRepository<GoodBbsEntity, Integer> {
    GoodBbsEntity findByUserIdAndBbsIdx(String user_id, int bbsIdx);
}
