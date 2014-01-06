package com.yeepay.ytf.message.tool;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import zcu.xutil.Logger;


/**
 * @author Administrator
 *
 */
public class NotifyTo3gUtil {

		private 	static String  notifyUrl="https://boss3g.yeepay.com/notifier-boss/notify";
		private	 static String userName="ytfytf";
		private static String notifyRuleNameResert="易托付-密码重置 ";
		private static String notifyRuleNameOpen="易托付-账号开通";
		private static String notifyRuleName="易托付-动态码";
		private static String scretKey = "ytf123456";
		private static Logger logger = Logger.getLogger(NotifyTo3gUtil.class);
		/**
		 * 
		 * @param 电话列表
		 * @param 信息
		 * @return
		 * @throws NoSuchAlgorithmException
		 */
			public static String sendResertMsg(List<String> recipientList,String msg) throws NoSuchAlgorithmException{
						StringBuffer str=new StringBuffer();//创建一个StringBffer将联系人列表拼接成一个字符串
						for(String recipients:recipientList){
							str.append(recipients).append(",");
						}
						String recipientstr=str.substring(0,str.lastIndexOf(","));
						
						/*
						 * 将要发送的消息内容放入map中
						 */
						Map<String,String> MsgParams=new HashMap<String,String>();
						MsgParams.put("message", msg);
						/*
						 * 将信息内容转为json格式
						 */
						String submess=JsonUtil.map2json(MsgParams);
						 /*
						  * 数字签名加密
						  */
						String signstr=userName+notifyRuleName+recipientstr+scretKey;
						String sign=MD5Util.encrypt(signstr);
						//将以上参数放入map中
						Map<String,String> params=new HashMap<String,String>();
						params.put("sign",sign);
						params.put("content",submess);
						params.put("recipients", recipientstr);
						params.put("notifyRuleName",notifyRuleName);
						params.put("username", userName);
						String response = HttpRequestProxy.doPost(notifyUrl, params,"utf-8");
						 logger.info(response);
						 return response;
			}
			
			public static String sendOpenMsg(List<String> recipientList,String msg) throws NoSuchAlgorithmException{
				StringBuffer str=new StringBuffer();//创建一个StringBffer将联系人列表拼接成一个字符串
				for(String recipients:recipientList){
					str.append(recipients).append(",");
				}
				String recipientstr=str.substring(0,str.lastIndexOf(","));
				
				/*
				 * 将要发送的消息内容放入map中
				 */
				Map<String,String> MsgParams=new HashMap<String,String>();
				MsgParams.put("message", msg);
				/*
				 * 将信息内容转为json格式
				 */
				String submess=JsonUtil.map2json(MsgParams);
				 /*
				  * 数字签名加密
				  */
				String signstr=userName+notifyRuleNameOpen+recipientstr+scretKey;
				String sign=MD5Util.encrypt(signstr);
				//将以上参数放入map中
				Map<String,String> params=new HashMap<String,String>();
				params.put("sign",sign);
				params.put("content",submess);
				params.put("recipients", recipientstr);
				params.put("notifyRuleName",notifyRuleName);
				params.put("username", userName);
				String response = HttpRequestProxy.doPost(notifyUrl, params,"utf-8");
				 logger.info(response);
				 return response;
	}

}
