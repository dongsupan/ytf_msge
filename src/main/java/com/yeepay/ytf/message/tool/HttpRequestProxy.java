package com.yeepay.ytf.message.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class HttpRequestProxy 
		{
	    /**
	     * 连接超时
	     */
	   private static  int connectTimeOut = 5000;

	    /**
	     * 读取数据超时
	     */
	    private static int readTimeOut = 10000;

	    /**
	     * 请求编码
	     */
	   private static String requestEncoding = "utf-8";
	 /**
     * <pre>
     * 发送带参数的POST的HTTP请求
     * </pre>
     *
     * @param reqUrl HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
    @SuppressWarnings("rawtypes")
	public static String doPost(String reqUrl, Map<String,String> parameters,
            String recvEncoding){
        HttpURLConnection url_con = null;
        String responseContent = null;
        
        System.out.println("开始发送..............................");
        
        StringBuilder sb = new StringBuilder(reqUrl).append('?');
        try
        {
            
            for (Iterator iter = parameters.entrySet().iterator(); iter .hasNext();){
                Entry element = (Entry) iter.next();
                sb.append(element.getKey().toString());
                sb.append("=");
                sb.append(URLEncoder.encode(element.getValue().toString(), "UTF-8")  );
                sb.append("&");
            }
            if ( sb.length() > 0)
            {
            	 sb =  sb.deleteCharAt( sb.length() - 1);
            }
            System.out.println("路径为.............................."+sb.toString());
            
            URL url = new URL(sb.toString());
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            url_con.setRequestMethod("POST");
            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf=System.getProperty("line.separator");
            while (tempLine != null)
            {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
        finally
        {
            if (url_con != null)
            {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    /**
     * @return 连接超时(毫秒)
     * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
     */
    public static int getConnectTimeOut()
    {
        return HttpRequestProxy.connectTimeOut;
    }

    /**
     * @return 读取数据超时(毫秒)
     * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
     */
    public static int getReadTimeOut()
    {
        return HttpRequestProxy.readTimeOut;
    }

    /**
     * @return 请求编码
     * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
     */
    public static String getRequestEncoding()
    {
        return requestEncoding;
    }

    /**
     * @param connectTimeOut 连接超时(毫秒)
     * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
     */
    public static void setConnectTimeOut(int connectTimeOut)
    {
        HttpRequestProxy.connectTimeOut = connectTimeOut;
    }

    /**
     * @param readTimeOut 读取数据超时(毫秒)
     * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
     */
    public static void setReadTimeOut(int readTimeOut)
    {
        HttpRequestProxy.readTimeOut = readTimeOut;
    }

    /**
     * @param requestEncoding 请求编码
     * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
     */
    public static void setRequestEncoding(String requestEncoding){
        HttpRequestProxy.requestEncoding = requestEncoding;
    }
}


