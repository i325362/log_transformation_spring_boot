package com.sap.xm.scheduler.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.xm.scheduler.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}
