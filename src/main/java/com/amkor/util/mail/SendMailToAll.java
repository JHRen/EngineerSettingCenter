package com.amkor.util.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import com.sun.mail.iap.Literal;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 利用java.mail的邮件发送程序
 */

public class SendMailToAll {

	public static void main(String[] args) {
		String title = "邮件主题";// 所发送邮件的标题
		String from = "SHCN-TEST-IT@amkor.com";// 从那里发送
		String sendTo[] = { "353559843@qq.com" };// 发送到那里
		// 邮件的文本内容，可以包含html标记则显示为html页面
		String content = "mail test!!!!!!<br><a href=#FFFFF0><h1>aaa</h1></a>";
		// 所包含的附件，及附件的重新命名
		// String fileNames[] = { "D:\\test.txt,test.txt","D:\\test2.txt,哈哈.txt" };
		String fileNames[] = null;
		try {
			// MailSender mailsender = new MailSender();
			sendmail(title, from, sendTo, content, fileNames, "text/html;charset=gb2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
	}
	
	@Async
	public static void sendmail(String subject, String from, String[] to, String text, String[] filenames,
			String mimeType) throws Exception {
		// ResourceBundle mailProps = ResourceBundle.getBundle("mail");
		// 可以从配置文件读取相应的参数
		Properties props = new Properties();

		String smtp = "atcexim.cn.ds.amkor.com"; // 设置发送邮件所用到的smtp 具体根据实际变换
		String servername = "SHCN-TEST-IT@amkor.com"; // 发件人账号
		String serverpaswd = ""; // 发件人密码

		javax.mail.Session mailSession; // 邮件会话对象
		javax.mail.internet.MimeMessage mimeMsg; // MIME邮件对象

		props = java.lang.System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", smtp); // 设置SMTP主机
		props.put("mail.smtp.auth", "true"); // 是否到服务器用户名和密码验证
		// 到服务器验证发送的用户名和密码是否正确
		Email_Autherticatorbean myEmailAuther = new Email_Autherticatorbean(servername, serverpaswd);
		// 设置邮件会话
		mailSession = javax.mail.Session.getInstance(props, (Authenticator) myEmailAuther);

		// 设置debug打印信息
		mailSession.setDebug(true);

		// 设置传输协议
		javax.mail.Transport transport = mailSession.getTransport("smtp");
		// 设置from、to等信息
		mimeMsg = new javax.mail.internet.MimeMessage(mailSession);
		
		//设置邮件标题
		mimeMsg.setSubject(subject);
		
		if (!from.isEmpty()) {

			InternetAddress sentFrom = new InternetAddress(from);
			mimeMsg.setFrom(sentFrom); // 设置发送人地址
		}

		InternetAddress[] sendTo = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			System.out.println("发送到:" + to[i]);
			sendTo[i] = new InternetAddress(to[i]);
		}

		mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
		
		// 设置抄送
		/*
		 * String[] cc= {"Junhuan.Ren@amkor.com"}; InternetAddress[] ccTo = new
		 * InternetAddress[cc.length]; for (int i = 0; i < cc.length; i++) {
		 * System.out.println("抄送到:" + cc[i]); ccTo[i] = new InternetAddress(cc[i]); }
		 * mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.CC,
		 * ccTo); mimeMsg.setSubject(subject, "gb2312");
		 */

		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		// messageBodyPart.setText(UnicodeToChinese(text));
		messageBodyPart1.setContent(text, mimeType);

		Multipart multipart = new MimeMultipart();// 附件传输格式
		multipart.addBodyPart(messageBodyPart1);
		
		if (filenames != null) {
			for (int i = 0; i < filenames.length; i++) {
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				// 选择出每一个附件名
				String filename = filenames[i].split(",")[0];
				System.out.println("附件名：" + filename);
				String displayname = filenames[i].split(",")[1];
				// 得到数据源
				FileDataSource fds = new FileDataSource(filename);
				// 得到附件本身并至入BodyPart
				messageBodyPart2.setDataHandler(new DataHandler(fds));
				// 得到文件名同样至入BodyPart
				// messageBodyPart2.setFileName(displayname);
				// messageBodyPart2.setFileName(fds.getName());
				messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
				multipart.addBodyPart(messageBodyPart2);
			}
		}
		mimeMsg.setContent(multipart);
		// 设置信件头的发送日期
		mimeMsg.setSentDate(new Date());
		mimeMsg.saveChanges();
		// 发送邮件
		transport.send(mimeMsg);
		transport.close();
	}
	
	/**
	 * 发送带图片的邮件正文
	 * to 收件人subject 标题content 正文isHtml 是否为HTMLfiles 附件列表
	 * @param nameList
	 * @throws Exception
	 */
	public static void sendmail(String to,String subject,String content) throws Exception {
		
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost("atcexim.cn.ds.amkor.com");
        senderImpl.setUsername("SHCN-TEST-IT@amkor.com");
        senderImpl.setPassword("");        

        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        senderImpl.setJavaMailProperties(props);
        
        MimeMessage mimeMessage = senderImpl.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
		
		List<String> splitAddress = splitAddress(to);
		String[] nameList= splitAddress.toArray(new String[splitAddress.size()]);
		InternetAddress[] sendTo = new InternetAddress[nameList.length];
		for (int i = 0; i < nameList.length; i++) {
			System.out.println("发送到:" + nameList[i]);
			sendTo[i] = new InternetAddress(nameList[i]);
		}

		
		mimeMessageHelper.setTo(sendTo);
		 
        mimeMessageHelper.setFrom("SHCN-TEST-IT@amkor.com");

        mimeMessageHelper.setSubject(subject);      
        
        mimeMessageHelper.setText(content,true);            

        FileSystemResource img = new FileSystemResource(new File("\\\\C3WJAVAP01\\uploadFiles\\mailPic\\logo.jpg"));

        mimeMessageHelper.addInline("image",img);       

        senderImpl.send(mimeMessage);   
		
	}
	
	private static List<String> splitAddress(String addresses){
		if(StrUtil.isBlank(addresses)) {
			return null;
		}
		
		List<String> result;
		if(StrUtil.contains(addresses, ',')) {
			result = StrUtil.splitTrim(addresses, ',');
		}else if(StrUtil.contains(addresses, ';')) {
			result = StrUtil.splitTrim(addresses, ';');
		}else {
			result = CollUtil.newArrayList(addresses);
		}
		return result;
	}

}