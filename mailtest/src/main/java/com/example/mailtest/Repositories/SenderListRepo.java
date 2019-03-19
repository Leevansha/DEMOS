package com.example.mailtest.Repositories;

import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.mailtest.entity.SenderList;

public interface SenderListRepo extends CrudRepository<SenderList,String>{

	@Query("Select obj from SenderList obj where obj.saveDate=:saveDate  and obj.userEmail = :userEmail and obj.password = :password")
	public SenderList findByName(@Param("userEmail") String userEmail,@Param("password") String password, @Param("saveDate") Date saveDate);

	

}
