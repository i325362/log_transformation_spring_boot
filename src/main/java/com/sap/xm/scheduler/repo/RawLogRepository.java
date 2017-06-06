package com.sap.xm.scheduler.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.xm.scheduler.models.RawLog;

public interface RawLogRepository extends JpaRepository<RawLog, String>{

}
