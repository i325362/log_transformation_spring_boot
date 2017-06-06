package com.sap.xm.scheduler.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.xm.scheduler.models.Person;
import com.sap.xm.scheduler.repo.PersonRepository;

@RestController
public class SampleController {

	@Autowired
	PersonRepository persionRepository;
	
//	@Autowired
//	@Qualifier("jdbcTemplate")
//	JdbcTemplate template;
	
	@RequestMapping("/createPersion")
	public void createPerson(){
//		template.execute("insert into t_person values (11221,'monisha')");
		Person persion = new Person();
//		persion.setId(12356);
		persion.setName("Monisha");
		persionRepository.saveAndFlush(persion);
	}
}
