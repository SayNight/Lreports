package com.lls.report.service;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title DownloadService.java
 * @Description 下载服务
 * @author mrslee
 * @date 2017年9月7日 下午2:05:49
 * @version V1.0
 */
public interface DownloadService {
	/**
	 * 
	 * @Title downloadFile
	 * @Description 下载文件  
	 * @param response	返回流
	 * @param inputStream  模板输入流
	 * @param dataObj	需要下载的数据结果集（key只能由英文字母组成） 
	 * @param fileName	下载文件名称
	 * @param clazz		自定义扩展格式数据功能	
	 * @throws IOException
	 * @throws IllegalArgumentException 
	 * {@link }
	 * @since 2017年9月7日 下午6:05:37
	 */
	public void downloadFile(HttpServletResponse response, InputStream inputStream, Object dataObj, String fileName, Class<?> clazz) throws IOException, IllegalArgumentException;
}
