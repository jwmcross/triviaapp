package com.johncross.demo.santanderinterview.repository;

import com.johncross.demo.santanderinterview.entity.TriviaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaRepository extends JpaRepository<TriviaEntity, Long> {


}
