package com.amkor.util.mail.picmail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Mail {
	private static String nickname = "SHCN-TEST-IT@amkor.com";
	private static final String username = "SHCN-TEST-IT@amkor.com";
	private static final String password = "";
	private static final String protocol = "smtp";
	private static final String host = "atcexim.cn.ds.amkor.com";
	private static final String port = "25";

	/**
	 * 发送邮件 主方法
	 * @param to
	 * @param subject
	 * @param content
	 */
	public static void send(String to,String subject,String content) {

		List<String> splitAddress = splitAddress(to);
		String[] nameList= splitAddress.toArray(new String[splitAddress.size()]);
		Map<String, String> imagesMap = new HashMap<>();
		//imagesMap.put("logo", "\\\\C3WJAVAP01\\uploadFiles\\mailPic\\logo.jpg");
		
		Email email = new Email();
		email.setToEmails(nameList);
		email.setSubject(subject);
		email.setContent(content);
		email.setAttachments(null);
		//email.setImagesMap(imagesMap);
		
		String[] bccName= {"Junhuan.Ren@amkor.com"};
		email.setBccEmails(bccName);
		
		if (sendEmail(email)) {
			System.out.println("邮件发送成功!");
		} else {
			System.out.println("邮件发送失败!请及时解决！");
		}
	}
	
	public static void main(String[] args) {

		String to = "Junhuan.Ren@amkor.onmicrosoft.com;akihi_ren@163.com";
		String subject = "HTML+内嵌图片+附件邮件主题";
		String body = "<h4>HTML+附件+内嵌图片的邮件测试！！！</h4></br><a href=http://www.apache.org>" + "点击跳转</a>"
				+ "<h4>LOGO图标</h4></hr><img src=\"cid:logo\">";

		Map<String, String> imagesMap = new HashMap<>();
		imagesMap.put("logo", "\\\\C3WJAVAP01\\uploadFiles\\mailPic\\logo.jpg");

		Email email = new Email();
		email.setToEmails(to.split(";"));
		email.setSubject(subject);
		email.setContent(body);
		email.setAttachments(null);
		email.setImagesMap(imagesMap);
		email.setBccEmails(to.split(";"));
		if (sendEmail(email)) {
			System.out.println("邮件发送成功!");
		} else {
			System.out.println("邮件发送失败!请及时解决！");
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param email
	 *            邮件信息实体
	 * @return 是否发送成功 TRUE 发送成功 FALSE 发送失败
	 */
	public static boolean sendEmail(Email email) {
		try {

			// 创建Session实例对象
			Session session = createSession();

			// 创建MimeMessage实例对象
			MimeMessage message = createMessage(session, email);

			// 发送邮件
			System.out.println("发送邮件中......");
			Transport.send(message);

			return true;
		} catch (Exception e) {
			System.out.println("发送邮件异常==》");
			e.printStackTrace();
			return false;
		} finally {
			System.out.println("邮件发送结束...");
		}
	}

	/**
	 * 创建与邮件服务器的会话对象
	 * 
	 * @return 服务器的会话对象
	 */
	public static Session createSession() {
		// 封装属性参数
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocol);
		props.setProperty("mail.smtp.host", host);
		props.setProperty("mail.smtp.port", port);
		props.setProperty("mail.smtp.auth", "true"); // 是否需要验证设置为TRUE，使用授权码发送邮件需要验证

		// 获取与服务器的会话Session对象
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 登陆账号及密码，密码需要是第三方授权码
				return new PasswordAuthentication(username, password);
			}
		});

		return session;
	}

	/**
	 * 创建邮件的核心内容
	 * 
	 * @param session
	 *            与服务器交互的会话对象
	 * @param email
	 *            邮件实体
	 * @return 生成的MimeMessage实例对象
	 * @throws Exception
	 */
	public static MimeMessage createMessage(Session session, Email email) throws Exception {
		// 创建MimeMessage实例对象
		MimeMessage message = new MimeMessage(session);
		// 设置发件人
		if (!nickname.isEmpty()) {
			// 自定义发件人昵称
			message.setFrom(new InternetAddress(MimeUtility.encodeText(nickname) ));
			//message.setFrom(new InternetAddress(MimeUtility.encodeText(nickname) + " <" + username + ">"));
		} else {
			message.setFrom(new InternetAddress(username));
		}

		// 设置收件人
		String[] toEmails = email.getToEmails();
		if (toEmails != null && toEmails.length != 0) {
			InternetAddress[] internetAddressTO = new InternetAddress[toEmails.length];
			for (int i = 0; i < toEmails.length; i++) {
				System.out.println("发送给 :"+toEmails[i]);
				internetAddressTO[i] = new InternetAddress(toEmails[i]);
			}
			message.setRecipients(Message.RecipientType.TO, internetAddressTO);
		}

		// 设置密送人
		String[] bccEmails = email.getBccEmails();
		if (bccEmails != null && bccEmails.length != 0) {
			InternetAddress[] internetAddressBCC = new InternetAddress[bccEmails.length];
			for (int i = 0; i < bccEmails.length; i++) {
				System.out.println("密送给 :"+bccEmails[i]);
				internetAddressBCC[i] = new InternetAddress(bccEmails[i]);
			}
			message.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
		}

		// 设置抄送人
		String[] ccEmails = email.getCcEmails();
		if (ccEmails != null && ccEmails.length != 0) {
			InternetAddress[] internetAddressCC = new InternetAddress[ccEmails.length];
			for (int i = 0; i < ccEmails.length; i++) {
				internetAddressCC[i] = new InternetAddress(ccEmails[i]);
			}
			message.setRecipients(Message.RecipientType.CC, internetAddressCC);
		}

		// 设置发生日期
		message.setSentDate(new Date());

		// 设置邮件主题
		message.setSubject(email.getSubject());

		/* 创建用于组合邮件正文和附件的MimeMultipart对象 */
		MimeMultipart multipart = new MimeMultipart();

		// 设置HTML内容
		MimeBodyPart content = createContent(email.getContent(), email.getImagesMap());
		multipart.addBodyPart(content);

		// 设置附件
		String[] attachments = email.getAttachments();
		if (attachments != null && attachments.length != 0) {
			for (String filename : attachments) {
				MimeBodyPart attachPart = createAttachment(filename);
				multipart.addBodyPart(attachPart);
			}
		}

		// 将组合的MimeMultipart对象设置为整个邮件的内容，要注意调用saveChanges方法进行更新
		message.setContent(multipart);

		// 保存并生成最终的邮件内容
		message.saveChanges();

		return message;
	}

	public static MimeBodyPart createContent(String body, Map<String, String> imagesMap) throws Exception {

		/* 创建代表组合MIME消息的MimeMultipart对象和该对象保存到的MimeBodyPart对象 */
		MimeBodyPart content = new MimeBodyPart();

		// 创建一个MimeMultipart对象
		MimeMultipart multipart = new MimeMultipart();

		if (!body.isEmpty()) {
			// 创建一个表示HTML正文的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中
			MimeBodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
			multipart.addBodyPart(htmlBodyPart);
		}

		if (imagesMap != null && !imagesMap.isEmpty()) {
			for (Map.Entry<String, String> entry : imagesMap.entrySet()) {
				/* 创建一个表示图片的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中 */
				MimeBodyPart pictureBodyPart = new MimeBodyPart();

				// FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
				FileDataSource fileDataSource = new FileDataSource(entry.getValue());

				// DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
				pictureBodyPart.setDataHandler(new DataHandler(fileDataSource));
				pictureBodyPart.setContentID(entry.getKey());

				multipart.addBodyPart(pictureBodyPart);
			}
		}

		// 将MimeMultipart对象保存到MimeBodyPart对象中
		content.setContent(multipart);

		return content;
	}

	/**
	 * 创建邮件中的附件
	 * 
	 * @param filepath
	 *            附件的路径
	 * @return 生成的附件对象
	 * @throws Exception
	 */
	public static MimeBodyPart createAttachment(String filepath) throws Exception {
		/* 创建一个表示附件的MimeBodyPart对象，并加入附件内容以及相应的信息 */
		MimeBodyPart attachPart = new MimeBodyPart();

		// FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
		FileDataSource fileDataSource = new FileDataSource(filepath);

		// DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
		attachPart.setDataHandler(new DataHandler(fileDataSource));

		// 设置附件名称,MimeUtility.encodeText可以处理乱码问题
		attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));

		return attachPart;
	}

	/**
	 * 
	 * @param addresses
	 * @return
	 */
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
