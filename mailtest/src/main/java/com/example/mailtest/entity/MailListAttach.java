package com.example.mailtest.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Mail_Attach")
public class MailListAttach {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int sid;
	private String path;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int i) {
		this.sid = i;
	}


	
	
	
	
	
}
