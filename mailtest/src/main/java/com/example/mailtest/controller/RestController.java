package com.example.mailtest.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.mailtest.entity.Mail;
import com.example.mailtest.service.MailService;


@Controller
@RequestMapping("/")
public class RestController {

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/")
	public String getCompose(Model model) {
		
		return "redirect:uploadDetails";
	}
	
	@RequestMapping(value = "/uploadDetails", method = RequestMethod.GET)
	public String addStudent(Model model) {
		model.addAttribute("mail", new Mail());
		return "composeMail";
	}
	 @RequestMapping(value = "/uploadDetails", method = RequestMethod.POST)
     public String addStudent(@ModelAttribute("mail")Mail mail,BindingResult result, ModelMap model) {
		 Date date = new Date();
			mail.setSaveDate(date);
		 mailService.scheduleFixedRateTask(mail);
        model.addAttribute("allMails",mailService.getMailsByUser(mail));
     return "result";
  }
}
