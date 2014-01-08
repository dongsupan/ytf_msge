package com.yeepay.ytf.message.action;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeepay.ytf.message.tool.NotifyTo3gUtil;

@Controller
@RequestMapping("/ytf_msge")
public class SetFindMessageAction{
	
  	@RequestMapping(value = "/sendSms",method = RequestMethod.GET)
	public @ResponseBody String FindMessage(
			@RequestParam(value = "phone", required = true)String phone,

			@RequestParam(value = "msg", required = true)String msg,
			@RequestParam(value = "code", required = true)String code){

			List<String> recipientList=new ArrayList<String>();
			recipientList.add(phone);
			String msge=msg;
			HashMap<String,String> MsgParams=new HashMap<String,String>();
//		    MsgParams.put("name", name);
		    MsgParams.put("phone", phone);
		    MsgParams.put("message", msg);
			String response=null;
			
				try {
					response =	NotifyTo3gUtil.sendResertMsg(recipientList,MsgParams,code);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			return response;
		}
}
