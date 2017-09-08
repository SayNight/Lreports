package com.lls.report.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title FileUtil.java
 * @Description 文件帮助类
 * @author mrslee
 * @date 2017年9月7日 下午2:16:51
 * @version V1.0
 */
public class FileUtil {
	
	private FileUtil(){}
	
	/**
	 * 
	 * @Title fmtFileName
	 * @Description 格式化文件名，以避免浏览器不识别  
	 * @param request
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * {@link }
	 * @since 2017年9月7日 下午2:17:56
	 */
	public static String fmtFileName(HttpServletRequest request, String fileName) throws IOException {
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.toLowerCase().indexOf("msie") > 0 && userAgent.toLowerCase().indexOf("msie 5") < 0 && userAgent.toLowerCase().indexOf("msie 6") < 0) {// IE8-10
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else if (userAgent.toLowerCase().indexOf("like gecko") > 0) {// IE11
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}
		return fileName;
	}
	
	/**
	 * 
	 * @Title exportFile
	 * @Description 从流中导出下载文件  
	 * @param response
	 * @param os
	 * @param fileName
	 * @throws IOException 
	 * {@link }
	 * @since 2017年9月7日 下午2:25:29
	 */
	public static void exportFile(HttpServletResponse response, OutputStream os, String fileName) throws IOException{
		try (OutputStream outputStream = response.getOutputStream();) {
			ByteArrayInputStream bis = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = bis.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
		}
	}
}
