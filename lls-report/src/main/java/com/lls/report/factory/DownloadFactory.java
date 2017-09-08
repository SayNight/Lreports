package com.lls.report.factory;

import com.lls.report.enumtype.SupportTypeEnum;
import com.lls.report.service.DownloadService;
/**
 * 
 * @Title DownloadFactory.java
 * @Description 下载工厂类
 * @author mrslee
 * @date 2017年9月7日 下午6:05:06
 * @version V1.0
 */
public class DownloadFactory {
	/**
	 * 下载服务包前缀
	 */
	private static String BASEPREPACKAGE = "com.lls.report.service.impl";
	/**
	 * 下载服务类名后缀
	 */
	private static String CLASSSUFFIX = "DownloadServiceImpl";
	
	/**
	 * 
	 * @Title createDownloadService
	 * @Description 创建下载服务   
	 * @param downType
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException 
	 * {@link }
	 * @since 2017年9月7日 下午2:47:11
	 */
	public static DownloadService createDownloadService(String downType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException{
		//避免下载类型不支持
		SupportTypeEnum supportTypeEnum = SupportTypeEnum.valueOf(downType);
		Class<?> clazz = Class.forName(BASEPREPACKAGE + "." + supportTypeEnum.name() + CLASSSUFFIX);
		return (DownloadService) clazz.newInstance();
	}
	
}
