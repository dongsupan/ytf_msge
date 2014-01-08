package com.yeepay.ytf.message.tool;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author Administrator
 *
 */
public class NotifyTo3gUtil {

		private 	static String  notifyUrl="http://boss3g.yeepay.com/notifier-boss/notify";
		private	 static String userName="ytfytf";
		private static String notifyRuleNameResert="易托付-密码重置 "; //code=900
		private static String notifyRuleNameOpen="易托付-账号开通";   
		private static String notifyRuleName="易托付-动态码";
		private static String notifyRuleNameInvite="易托付-邀请";
		private static String notifyRuleNameCheck="易托付-审核";
		private static String notifyRuleNameCause="易托付-审核原因";
		private static String notifyRuleAlertU="易托付-提醒上传";
		private static String scretKey = "ytf123456";
		/**
		 * 
		 * @param 电话列表
		 * @param 信息
		 * @return
		 * @throws NoSuchAlgorithmException
		 */
			public static String sendResertMsg(List<String> recipientList,Map<String, String> msg,String code) throws NoSuchAlgorithmException{
						StringBuffer str=new StringBuffer();//创建一个StringBffer将联系人列表拼接成一个字符串
						for(String recipients:recipientList){
							str.append(recipients).append(",");
						}
						String recipientstr=str.substring(0,str.lastIndexOf(","));
						
						/*
						 * 将要发送的消息内容放入map中
						 */
//						Map<String,String> MsgParams=new HashMap<String,String>();
////						MsgParams.put("name", name);
////						MsgParams.put("phone", recipientstr);
//						MsgParams.put("message", msg);
						
						/*
						 * 将信息内容转为json格式
						 */
						String submess=JsonUtil.map2json(msg);
						/**
						 * 签名map
						 */
						Map<String,String> CodeParams=new HashMap<String,String>();
						CodeParams.put("1", notifyRuleNameResert); //
						CodeParams.put("2", notifyRuleNameOpen);
						CodeParams.put("3", notifyRuleName);
						CodeParams.put("4", notifyRuleNameInvite);
						CodeParams.put("5", notifyRuleNameCheck);
						CodeParams.put("6", notifyRuleNameCause);
						CodeParams.put("7", notifyRuleAlertU);
						 /*
						  * 数字签名加密
						  */
						String signstr=userName+CodeParams.get(code)+recipientstr+scretKey;
						System.out.println(signstr);
						String sign=MD5Util.encrypt(signstr);
						//将以上参数放入map中
						Map<String,String> params=new HashMap<String,String>();
						params.put("sign",sign);
						params.put("content",submess);
						params.put("recipients", recipientstr);
						params.put("notifyRuleName",CodeParams.get(code));
						params.put("username", userName);
						String response = HttpRequestProxy.doPost(notifyUrl, params,"utf-8");
						 System.out.println(response);
						 return response;
			}
}
