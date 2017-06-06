package com.sap.xm.scheduler.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.xm.scheduler.models.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

}
