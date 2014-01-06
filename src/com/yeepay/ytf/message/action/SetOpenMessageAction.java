package com.yeepay.ytf.message.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.yeepay.ytf.message.tool.HttpRequestProxy;
import com.yeepay.ytf.message.tool.NotifyTo3gUtil;

import zcu.xutil.Logger;
import zcu.xutil.web.Action;
import zcu.xutil.web.ActionContext;
import zcu.xutil.web.Redirect;
import zcu.xutil.web.Stream;
import zcu.xutil.web.View;

public class SetOpenMessageAction implements Action{
	// 日志对象
	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public View execute(ActionContext context) throws ServletException,
			IOException {
		HttpServletRequest request = context.getRequest();
		String phon = request.getParameter("phone");
		String mssg = request.getParameter("msg");
		String phone = phon.trim();
		List<String> recipientList=new ArrayList<String>();
		recipientList.add(phone);
		String msg=mssg.trim();
		String response;
		try {
			response =	NotifyTo3gUtil.sendOpenMsg(recipientList, msg);
			} catch (NoSuchAlgorithmException e) {
				log.info("短信发送失败 :",e);
				return new Redirect("error.html");
			}
		return new Stream(response);
	}

}
