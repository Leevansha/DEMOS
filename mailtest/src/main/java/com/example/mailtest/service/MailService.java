package com.example.mailtest.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.mailtest.Repositories.MailListAttachRepo;
import com.example.mailtest.Repositories.MailListRepo;
import com.example.mailtest.Repositories.SenderListRepo;
import com.example.mailtest.entity.Mail;
import com.example.mailtest.entity.MailList;
import com.example.mailtest.entity.MailListAttach;
import com.example.mailtest.entity.SenderList;

@Service
public class MailService {

	
	@Autowired
	public ExcelFileToDatabaseJobConfig mailservice;
	

	@Autowired
	public MailListRepo repository;
	
	

	@Autowired
	SenderListRepo senderList;
	
	@Autowired
	MailListAttachRepo mailListAttachrepo;
	
	@Async
	public void scheduleFixedRateTask(Mail mail) {
		
		SenderList sl = new SenderList();
		sl.setDetails(mail);
		
		sl=senderList.save(sl);
		mailservice.scheduleFixedRateTask(mail,sl.getSid());
		String path ="C:\\Eclipse\\Temp\\";
		
		List <String> af =new ArrayList<String>();
		List <MultipartFile> files = mail.getAttachFile();
		for(MultipartFile file:files) {
			String fileName = file.getOriginalFilename();
			MailListAttach attach = new MailListAttach();
			attach.setSid(sl.getSid());
			attach.setPath(path+fileName);
			
			try {
				file.transferTo(new File(path+fileName));
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			attach = mailListAttachrepo.save(attach);
		}
		

		List<MailList> mails= repository.findFalseByName(sl.getSid());
		
		for(MailList mailservice:mails)
		sendMessageWithAttachment(mailservice,sl);
	}

	private void sendMessageWithAttachment(MailList sender, SenderList sl) {
		String from = sl.getUserEmail();

	      final String username = "";//change accordingly
	      final String password = sl.getPassword();//change accordingly

	      // Assuming you are sending email through relay.jangosmtp.net
	      

//	      Properties props = new Properties();
//	      props.put("mail.smtp.host", "true");
//	        props.put("mail.smtp.starttls.enable", "false");
//	        props.put("mail.smtp.host", "smtp.gmail.com");
//	        props.put("mail.smtp.port", "587");
//	        props.put("mail.smtp.auth", "false");
//	    
//	        props.put("mail.debug", "true");
//	        

//	        Session session = Session.getInstance(props,
//			         new javax.mail.Authenticator() {
//			            protected PasswordAuthentication getPasswordAuthentication() {
//			               return new PasswordAuthentication(from, password);
//			            }
//			         });
  
	        //Session session = Session.getInstance(props,null);
	      
	      Properties properties = System.getProperties();  
          
	        // Setup mail server 
	        properties.setProperty("mail.smtp.host", "localhost"); 
	          
	        // SSL Port 
	        properties.put("mail.smtp.port", "587");  
	          
	        // enable authentication 
	        //properties.put("mail.smtp.auth", "true");  
	          
	        // SSL Factory 
	       // properties.put("mail.smtp.socketFactory.class", 
	         //       "javax.net.ssl.SSLSocketFactory");   
	        properties.put("mail.debug", "true");
	  
	        // creating Session instance referenced to  
	        // Authenticator object to pass in  
	        // Session.getInstance argument 
	        Session session = Session.getDefaultInstance(properties);
	          //  new javax.mail.Authenticator() { 
	                  
	                // override the getPasswordAuthentication  
	                // method 
//	                protected PasswordAuthentication  
//	                        getPasswordAuthentication() { 
//	                    return new PasswordAuthentication(from, 
//	                                                    password); 
//	                } 
//	            }); 
	        try {
	        	System.out.println("over1");
	        // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(sender.getEmailAddress()));

	         // Set Subject: header field
	         message.setSubject(sl.getSubject());

	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setContent(sl.getComment(),"text/html");

	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);

	         List<String> files = mailListAttachrepo.findBySid(sl.getSid());
			 System.out.println(files.size());
			 if(files!=null)
			 for (String file : files) {
				 
	             
	             System.out.println(file);
	             addAttachment(multipart,file);
			 }


			 message.setContent(multipart);
	         // Send message
			 message.setSentDate(new Date());
	         Transport.send(message);

	         sender.setFlag(true);
	         sender = repository.save(sender);
	         System.out.println("Sent message successfully....");
	  
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }


		
		
	}

	
	
	private void addAttachment(Multipart multipart, String filename)
	{
		
	    DataSource source = new FileDataSource(filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    try {
			messageBodyPart.setDataHandler(new DataHandler(source));
			 messageBodyPart.setFileName(filename);
			    multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}

	public List<MailList> getMailsByUser(Mail mail) {
		// TODO Auto-generated method stub
		SenderList s= senderList.findByName(mail.getUserEmail(),mail.getPassword(),mail.getSaveDate());
		
		return repository.findFalseByNameAll(s.getSid());
	}
}

//	public void sendSimpleMessage(
//			String to, String subject, String text) {
//		SimpleMailMessage message = new SimpleMailMessage(); 
//		message.setTo(to); 
//		message.setSubject(subject); 
//		message.setText(text);
//		emailSender.send(message);
//
//	}
//
//	public void sendMessageWithAttachment(
//			String to, String subject, String text, String pathToAttachment) {
//		// ...
//		
//		try {
//			
//			Thread.currentThread().sleep((1000*37)+(long) (1000*100*Math.random()));
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		MimeMessage message = emailSender.createMimeMessage();
//
//		MimeMessageHelper helper;
//		try {
//			helper = new MimeMessageHelper(message, true);
//			helper.setTo(to);
//			helper.setSubject(subject);
//			//html formatting true
//			helper.setText(text,true);
//			
//			FileSystemResource file 
//			= new FileSystemResource(new File(pathToAttachment));
//			helper.addAttachment("Invoice", file);
//			emailSender.send(message);
//
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//	
//		System.out.println("Sent Successfully");
//		// ...
//	}
//
//

