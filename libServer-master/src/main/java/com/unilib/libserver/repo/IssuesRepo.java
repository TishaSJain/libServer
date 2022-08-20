package com.unilib.libserver.repo;


import com.unilib.libserver.entity.Issues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IssuesRepo extends JpaRepository<Issues,Integer> {
    @Query(value="select id,due_date,issue_date,return_date,bid,uid from issues i where i.uid=:userId", nativeQuery = true)
    List<Issues> findAllByUid(@Param("userId") int userId);

    @Query(value="select id,due_date,issue_date,return_date,bid,uid from issues i where i.bid=:bid", nativeQuery = true)
    List<Issues> findAllByBid(@Param("bid") int bid);
}
