package com.example.mailtest.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;

import org.springframework.web.multipart.MultipartFile;

public class Mail {

	private String userEmail;
	private String password;
	private String subject;
	private String comment;
	
	private List<MultipartFile> attachFile;
	private MultipartFile receiver;
	private Date saveDate;
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password.trim();
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
		this.comment = comment.trim();
	}
	
	public MultipartFile getReceiver() {
		return receiver;
	}
	public void setReceiver(MultipartFile receiver) {
		this.receiver = receiver;
	}
	public List<MultipartFile> getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(List<MultipartFile> attachFile) {
		this.attachFile = attachFile;
	}
	public Date getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
	
	
	
}
