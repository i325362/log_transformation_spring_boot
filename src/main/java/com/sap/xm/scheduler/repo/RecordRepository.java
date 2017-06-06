package com.sap.xm.scheduler.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.xm.scheduler.models.Record;

public interface RecordRepository extends JpaRepository<Record, Integer>{

}
