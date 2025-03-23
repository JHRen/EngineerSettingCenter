package com.amkor.service.fhoa.sendmail.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amkor.service.fhoa.sendmail.SendmailManager;
import com.amkor.util.Const;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.amkor.util.mail.SendMailToAll;
import com.amkor.util.mail.picmail.Mail;

import cn.hutool.core.date.DateUtil;

@Service("sendmailService")
public class SendmailService implements SendmailManager {
	@Async
	public void sendToMail(String to,String subject,String content) {
		Mail.send(to, subject, content);
	}
	
	
	
	@Async
	public void sendmail(String fileName, String userName,String departmentID, String fileTime) throws Exception {
		String title = "Test Internal OPS/OPL Sharing";// 所发送邮件的标题
		// String sendTo[] = { "SHCN-TEST-MFG-MANAGER" ,"SHCN-Test-ENG-MGR" };
		//String departmentID = Jurisdiction.getDEPARTMENT_ID();
		String nameList = null;
		if ("00101".equals(departmentID)) {
			nameList = Tools.readTxtFile(Const.EMAILTO_TEST); // 收件人
		} else if ("00102".equals(departmentID)) {
			nameList = Tools.readTxtFile(Const.EMAILTO_ASSY); // 收件人
		}

		if (fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx")) {

		} else {
			fileName = fileName + ".ppt";
		}
		try {
			Mail.send(nameList, title, buildContent("您有一份新的文档需要查看",fileName, userName, fileTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String buildContent(String subject, String fileName, String userName, String fileTime) throws IOException {

		// 加载邮件html模板
		String filehtmlName = "/templates.html";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filehtmlName);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = fileReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
		} finally {
			inputStream.close();
			fileReader.close();
		}

		// 填充html模板中的五个参数 
		String htmlText = MessageFormat.format(buffer.toString(), subject, fileName, userName, fileTime,
				DateUtil.today());

		return htmlText;
	}

	@Async
	public void sendToManager(String UserName,List<String > recipient, String Status, PageData pd, String EMAIL_FLAG) throws Exception {

		//String title = Tools.readTxtFile(Const.SYSNAME);// 所发送邮件的标题
		String title ="Engineer Setting Center";
		String from = "SHCN-TEST-IT@amkor.com";// 发件人
		String fileNames[] = null; // 附件

		//List<String> list = Jurisdiction.getRecipient();
		// 获取发送邮件的人
		 String sendTo[] =(String[])recipient.toArray(new String[0]);

		//String sendTo[] = { "353559843@qq.com" };// 收件人

		String mailbody = "";

		String[] EmailFlag = EMAIL_FLAG.split("_"); // 将发送的系统内容的识别标记 按下滑杠分割提取内容
		if ("lodfile".equals(EmailFlag[0])) {
			mailbody = "";
			mailbody = "<br>Hi All,</br>";
			if ("atccp".equals(EmailFlag[1])) {
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Lodfile System____ATC CP Line 操作申请</p>";
				} else {
					mailbody += "<p>Lodfile System____ATC CP Line 审批通知</p>";
				}
			} else if ("atcqca".equals(EmailFlag[1])) {
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Lodfile System____ATC FT Line(QCA) 操作申请</p>";
				} else {
					mailbody += "<p>Lodfile System____ATC FT Line(QCA) 审批通知</p>";
				}

			} else if ("qtscp".equals(EmailFlag[1])) {
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Lodfile System____QTS CP Line 操作申请</p>";
				} else {
					mailbody += "<p>Lodfile System____QTS CP Line 审批通知</p>";
				}

			} else if ("qtsqca".equals(EmailFlag[1])) {
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Lodfile System____QTS FT Line(QCA) 操作申请</p>";
				} else {
					mailbody += "<p>Lodfile System____QTS FT Line(QCA) 审批通知</p>";
				}

			} else {
				mailbody += "<p>此操作记录无法识别!</p>";
			}

			mailbody += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
			mailbody += "<tr bgcolor='#cccccc'>";
			mailbody += "<td align='center' valign='middle'>操作人</td>";
			mailbody += "<td align='center' valign='middle'>操作状态</td>";
			mailbody += "<td align='center' valign='middle'>操作时间</td>";

			mailbody += "<td align='center' valign='middle'>Device</td>";
			mailbody += "<td align='center' valign='middle'>Family Name</td>";
			mailbody += "<td align='center' valign='middle'>Operation</td>";
			mailbody += "<td align='center' valign='middle'>Operation Code</td>";
			mailbody += "<td align='center' valign='middle'>Customer Code</td>";
			mailbody += "<td align='center' valign='middle'>Lodfile</td>";
			if (!"A".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>审核状态</td>";
			}
			mailbody += "</tr>";

			mailbody += "<tr bgcolor='#ffffff'>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + UserName + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + Status + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + Tools.date2Str(new Date()) + "&nbsp;</td>";

			mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("device") + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("family_name") + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("operation") + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("operation_code") + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("customer_code") + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("lod_file") + "&nbsp;</td>";
			if ("T".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>&nbsp; 批准 &nbsp;</td>";
			} else if ("F".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>&nbsp; 拒绝  &nbsp;</td>";
			}

			mailbody += "</tr>";
			mailbody += "</table>";
			mailbody += "<p>http://atctest:8081/EngineerSettingCenter</p>";
			mailbody += "<br><br><br>";
			mailbody += "<p>如何你有任何问题，请邮件联系我们：SHCN-TEST-IT@amkor.com</p>";
			mailbody += "<p>Thanks and Best Regards</p>";
		} else if ("MCN".equals(EmailFlag[0])) {
			// EMAIL_FLAG:MCN_GF/MCN_SMIC/MCN_TSMC
			mailbody = "";

			if ("GF".equals(EmailFlag[1])) {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>MCN_GF 操作申请</p>";
				} else {
					mailbody += "<p>MCN_GF 审批通知</p>";
				}
				mailbody += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
				mailbody += "<tr bgcolor='#cccccc'>";
				mailbody += "<td align='center' valign='middle'>操作人</td>";
				mailbody += "<td align='center' valign='middle'>操作状态</td>";
				mailbody += "<td align='center' valign='middle'>操作时间</td>";

				mailbody += "<td align='center' valign='middle'>MCN Name</td>";
				mailbody += "<td align='center' valign='middle'>GF Part</td>";
				mailbody += "<td align='center' valign='middle'>Test PGM1</td>";
				mailbody += "<td align='center' valign='middle'>Job Name</td>";

			} else if ("SMIC".equals(EmailFlag[1])) {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>MCN_SMIC 操作申请</p>";
				} else {
					mailbody += "<p>MCN_SMIC 审批通知</p>";
				}
				mailbody += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
				mailbody += "<tr bgcolor='#cccccc'>";
				mailbody += "<td align='center' valign='middle'>操作人</td>";
				mailbody += "<td align='center' valign='middle'>操作状态</td>";
				mailbody += "<td align='center' valign='middle'>操作时间</td>";

				mailbody += "<td align='center' valign='middle'>TargetDevice</td>";
				mailbody += "<td align='center' valign='middle'>NickName</td>";
				mailbody += "<td align='center' valign='middle'>QCTName</td>";
				mailbody += "<td align='center' valign='middle'>MCN</td>";
			}else if ("SAMSUNG".equals(EmailFlag[1])) {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>MCN_SAMSUNG 操作申请</p>";
				} else {
					mailbody += "<p>MCN_SAMSUNG 审批通知</p>";
				}
				mailbody += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
				mailbody += "<tr bgcolor='#cccccc'>";
				mailbody += "<td align='center' valign='middle'>操作人</td>";
				mailbody += "<td align='center' valign='middle'>操作状态</td>";
				mailbody += "<td align='center' valign='middle'>操作时间</td>";
				mailbody += "<td align='center' valign='middle'>TargetDevice</td>";
				mailbody += "<td align='center' valign='middle'>NickName</td>";
				mailbody += "<td align='center' valign='middle'>QCTName</td>";
				mailbody += "<td align='center' valign='middle'>MCN</td>";
			} 
			else {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>MCN_TSMC 操作申请</p>";
				} else {
					mailbody += "<p>MCN_TSMC 审批通知</p>";
				}
				mailbody += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
				mailbody += "<tr bgcolor='#cccccc'>";
				mailbody += "<td align='center' valign='middle'>操作人</td>";
				mailbody += "<td align='center' valign='middle'>操作状态</td>";
				mailbody += "<td align='center' valign='middle'>操作时间</td>";
				mailbody += "<td align='center' valign='middle'>TargetDevice</td>";
				mailbody += "<td align='center' valign='middle'>NickName</td>";
				mailbody += "<td align='center' valign='middle'>QCTName</td>";
				mailbody += "<td align='center' valign='middle'>MCN</td>";
			}

			if (!"A".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>审核状态</td>";
			}
			mailbody += "</tr>";

			mailbody += "<tr bgcolor='#ffffff'>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + UserName + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + Status + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + Tools.date2Str(new Date()) + "&nbsp;</td>";
			if ("GF".equals(EmailFlag[1])) {
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("MCN_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("GF_Part") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("TEST_PGM1") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("JOB_NAME") + "&nbsp;</td>";
			} else if ("SMIC".equals(EmailFlag[1])) {
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("TargetDevice") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("NickName") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("QCTname") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("MCN") + "&nbsp;</td>";
			} else {
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("TargetDevice") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Nickname") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("QCTname") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("MCNDevice") + "&nbsp;</td>";
			}

			if ("T".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>&nbsp; 批准 &nbsp;</td>";
			} else if ("F".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>&nbsp; 拒绝  &nbsp;</td>";
			}
			mailbody += "</tr>";
			mailbody += "</table>";
			mailbody += "<p>http://10.86.140.200:8081/EngineerSettingCenter</p>";
			mailbody += "<br><br><br>";
			mailbody += "<p>如何你有任何问题，请邮件联系我们：SHCN-TEST-IT@amkor.com</p>";
			mailbody += "<p>Thanks and Best Regards</p>";
			// mail.sendMail( Tools.readTxtFile(Const.SYSNAME), mailbody, "2", recipient);

		} else if ("equipment".equals(EmailFlag[0])) {
			mailbody = "";

		} else if ("PC".equals(EmailFlag[0])) {
			// 4. Probe Card

			mailbody = "";
			if ("GF".equals(EmailFlag[1])) {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Probe Card<GF> 操作申请</p>";
				} else {
					mailbody += "<p>Probe Card<GF> 审批通知</p>";
				}
				mailbody += emailHead(EmailFlag);

			} else if ("SMIC".equals(EmailFlag[1])) {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Probe Card<SMIC> 操作申请</p>";
				} else {
					mailbody += "<p>Probe Card<SMIC> 审批通知</p>";
				}
				mailbody += emailHead(EmailFlag);
			} else {
				mailbody = "<br>Hi,</br>";
				if ("A".equals(EmailFlag[2])) {
					mailbody += "<p>Probe Card<TSMC> 操作申请</p>";
				} else {
					mailbody += "<p>Probe Card<TSMC> 审批通知</p>";
				}
				mailbody += emailHead(EmailFlag);
			}

			if (!"A".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>审核状态</td>";
			}
			mailbody += "</tr>";

			mailbody += "<tr bgcolor='#ffffff'>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + UserName + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + Status + "&nbsp;</td>";
			mailbody += "<td align='center' valign='middle'>&nbsp;" + Tools.date2Str(new Date()) + "&nbsp;</td>";
			if ("GF".equals(EmailFlag[1])) {
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Cust_Tool_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Tool_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Customer") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Device_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Touchdown_Per_Wafer") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Touchdown_Limit") + "&nbsp;</td>";
			} else if ("SMIC".equals(EmailFlag[1])) {
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Cust_Tool_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Tool_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Customer") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Device_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Touchdown_Per_Wafer") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Touchdown_Limit") + "&nbsp;</td>";
			} else {
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Cust_Tool_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Tool_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Customer") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Device_Name") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Touchdown_Per_Wafer") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("Touchdown_Limit") + "&nbsp;</td>";
			}

			if ("T".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>&nbsp; 批准 &nbsp;</td>";
			} else if ("F".equals(EmailFlag[2])) {
				mailbody += "<td align='center' valign='middle'>&nbsp; 拒绝  &nbsp;</td>";
			}
			mailbody += emailEnd();
		}
		// 邮件发送

		SendMailToAll.sendmail(title, from, sendTo, mailbody, fileNames, "text/html;charset=UTF-8");
	
		
	}
	/**
	 * 邮件头部内容
	 * @param EmailFlag
	 * @return
	 */
	public static String emailHead(String[] EmailFlag) {
		String mailHead = "";
		mailHead += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
		mailHead += "<tr bgcolor='#cccccc'>";
		mailHead += "<td align='center' valign='middle'>操作人</td>";
		mailHead += "<td align='center' valign='middle'>操作状态</td>";
		mailHead += "<td align='center' valign='middle'>操作时间</td>";

		if ("PC".equals(EmailFlag[0])) {
			mailHead += "<td align='center' valign='middle'>Cust_Tool_Name</td>";
			mailHead += "<td align='center' valign='middle'>Tool_Name</td>";
			mailHead += "<td align='center' valign='middle'>Customer</td>";
			mailHead += "<td align='center' valign='middle'>Device_Name</td>";
			mailHead += "<td align='center' valign='middle'>Touchdown_Per_Wafer</td>";
			mailHead += "<td align='center' valign='middle'>Touchdown_Limit</td>";
		}
	/*	if ("F".equals(EmailFlag[2]) || "T".equals(EmailFlag[2])) {
			
			mailHead += "<td align='center' valign='middle'>审批人</td>";
			mailHead += "<td align='center' valign='middle'>审批时间</td>";
		}*/
		return mailHead;
	}
	
	/**
	 * 邮件底部内容
	 * @return
	 */
	public static String emailEnd() {
		String endInfo="";
		endInfo += "</tr>";
		endInfo += "</table>";
		endInfo += "<p>http://atctest:8081/EngineerSettingCenter</p>";
		endInfo += "<br><br><br>";
		endInfo += "<p>如何你有任何问题，请邮件联系我们：SHCN-TEST-IT@amkor.com</p>";
		endInfo += "<p>Thanks and Best Regards</p>";
		
		return endInfo;
	}

}
