package com.example.mailtest.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.mailtest.entity.MailListAttach;

public interface MailListAttachRepo extends CrudRepository<MailListAttach,String>{

	@Query("Select obj.path from MailListAttach as obj where obj.sid = :sid")
	List<String> findBySid(@Param("sid")int i);

}
