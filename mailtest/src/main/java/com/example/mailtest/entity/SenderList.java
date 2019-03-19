package com.example.mailtest.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Senders")
public class SenderList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	private String userEmail;
	private String password;
	private String subject;
	private String comment;
	private Date saveDate;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
	public void setDetails(Mail mail) {
		// TODO Auto-generated method stub
		this.userEmail = mail.getUserEmail();
		this.password = mail.getPassword();
		this.subject = mail.getSubject();
		this.comment = mail.getComment();
		this.saveDate=mail.getSaveDate();
	}
	
	
}
