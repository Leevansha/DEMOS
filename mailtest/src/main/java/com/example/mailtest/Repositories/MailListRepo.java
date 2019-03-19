package com.example.mailtest.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.mailtest.entity.MailList;

public interface MailListRepo extends CrudRepository<MailList,String> {

	@Query("Select obj from MailList obj where obj.flag=false")
	public List<MailList> findFalse();

	@Query("Select obj from MailList obj where obj.sid=:sid and obj.flag=false")
	public List<MailList> findFalseByName(@Param("sid") int i);

	@Query("Select obj.flag from MailList obj where obj.sid=:sid and obj.emailAddress=:email")
	public boolean existsByEmail(@Param("email")String email, @Param("sid") int sid);

	@Query("Select obj from MailList obj where obj.sid=:sid")
	public List<MailList> findFalseByNameAll(@Param("sid") int i);
	
}
