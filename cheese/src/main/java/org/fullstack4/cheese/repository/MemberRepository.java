package org.fullstack4.cheese.repository;

import org.fullstack4.cheese.domain.MemberEntity;
import org.fullstack4.cheese.repository.memberSearch.MemberSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>, MemberSearch {

    MemberEntity findByUserId(String user_id);
    MemberEntity findByUserEmail1AndUserEmail2(String user_email1,String user_email2);
//    int CountByUserId(String user_id);

}