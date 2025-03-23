package com.amkor.service.fhoa.sendmail;

import java.util.List;

import org.springframework.scheduling.annotation.EnableAsync;

import com.amkor.util.PageData;

@EnableAsync
public interface SendmailManager {
    void sendToMail(String to,String subject,String content) throws Exception;
	
	void sendmail(String fileName, String userName, String departmentID, String fileTime) throws Exception;

	void sendToManager(String UserName, List<String> recipient, String Status, PageData pd, String EMAIL_FLAG)
			throws Exception;
}
