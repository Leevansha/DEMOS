package com.example.mailtest.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.example.mailtest.Repositories.MailListRepo;
import com.example.mailtest.Repositories.SenderListRepo;
import com.example.mailtest.entity.Mail;
import com.example.mailtest.entity.MailList;

@Configuration
public class ExcelFileToDatabaseJobConfig {
 
	@Autowired
	MailListRepo repository;

	@Autowired 
	 MailService mailservice;
	
	@Autowired
	SenderListRepo senderList;
	
	private String path ="C:\\Eclipse\\Temp\\";
	public void scheduleFixedRateTask(Mail sender,int sid) {

		MultipartFile multipartFile = sender.getReceiver();
		try {
		String fileName = multipartFile.getOriginalFilename();
		multipartFile.transferTo(new File(path+fileName));
		File fileToRead=new File(path+fileName);
		List<MailList> list;
        
		

		list= readExcel(fileToRead,sender,sid);
        System.out.println(list.size());
		repository.saveAll(list);
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		System.out.println("over");
		
		return;
		
	}


	
	
	private Map<String,int[]> getColumnIndices(Sheet datatypeSheet, File fileToRead,Workbook wb)
	{
		Map<String,int[]> map=new HashMap<>();
		for (Row row : datatypeSheet) {

			for (Cell cell : row) {

				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals("Name")) 
					{
						int[] array={cell.getColumnIndex(),cell.getRowIndex()};
						map.put(cell.getRichStringCellValue().getString().trim(), array);

					}

					if (cell.getRichStringCellValue().getString().trim().equals("Email")) 
					{

						int[] array={cell.getColumnIndex(),cell.getRowIndex()};
						map.put(cell.getRichStringCellValue().getString().trim(), array);
					}

				}
			}
		}
		return map;

	}


	public List<MailList> readExcel(File fileToRead, Mail sender, int sid) {
		List<MailList> list=new ArrayList<MailList>();
		FileInputStream inputStream=null;
		try {
			System.out.println(fileToRead.getName());
			inputStream = new FileInputStream(fileToRead);
			Workbook workbook = new XSSFWorkbook(inputStream);
			//workbook.getSettings().setRegion(CountryCode.INDIA); 
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Map<String,int[]> columnIndices= getColumnIndices(datatypeSheet,fileToRead,workbook);

			if(columnIndices!=null&&!columnIndices.isEmpty())
			{

				for (Row row : datatypeSheet) {
					if(row.getRowNum()!=columnIndices.get("Name")[1]
							&&row.getCell(columnIndices.get("Name")[0])!= null 
							&& row.getCell(columnIndices.get("Name")[0]).getCellType() != CellType.BLANK)
					{
						String email=row.getCell(columnIndices.get("Email")[0]).getStringCellValue();
						
						
						
						MailList entity = new MailList();
						
						entity.setName(row.getCell(columnIndices.get("Name")[0]).getStringCellValue());
						entity.setEmailAddress(row.getCell(columnIndices.get("Email")[0]).getStringCellValue());
						entity.setSid(sid);
						entity.setFlag(false);
						list.add(entity);
						System.out.println(entity);
						
					}

				}
			}

			inputStream.close();
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;


	}



}