package com.lls.report.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Title JxlsExtFunction.java
 * @Description 自定义数据格式化扩展功能
 * @author mrslee
 * @date 2017年9月6日 上午11:37:57
 * @version V1.0
 */
public class JxlsExtFunction {
	
	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static JxlsExtFunction jxlsExtFunction = null;
	
	/**
	 * 
	 * @Title getInstance
	 * @Description double check init  
	 * @return 
	 * {@link }
	 * @since 2017年9月6日 下午12:24:29
	 */
	public static JxlsExtFunction  getInstance(){
		if(jxlsExtFunction == null){
			synchronized (JxlsExtFunction.class) {
				jxlsExtFunction = new JxlsExtFunction();
				return jxlsExtFunction;
			}
		}
		return jxlsExtFunction;
	}
	
	private JxlsExtFunction(){}
	
	public static String fmtDate(Object date) {
		if(date == null){
			return "";
		}
		Date fmtDate = (Date) date;
		return SHORT_DATE_FORMAT.format(fmtDate);
	}
	
	public static String fmtDateTime(Object date) {
		if(date == null){
			return "";
		}
		Date fmtDate = (Date) date;
		return LONG_DATE_FORMAT.format(fmtDate);
	}
	
	public static String fmtAmount(Object number){
		if(number == null){
			number = BigDecimal.ZERO;
		}
		DecimalFormat format = new DecimalFormat("##0.0#");
		format.setRoundingMode(RoundingMode.HALF_UP);
		format.setMultiplier(1);
		return format.format(number);
	}
	
	public static String fmtNumber(Object number){
		if(number == null){
			return "";
		}
		DecimalFormat format = new DecimalFormat("##0.0#");
		format.setRoundingMode(RoundingMode.HALF_UP);
		format.setMultiplier(1);
		return format.format(number);
	}
	
	/**
	 * 
	 * @Title fmtEnum
	 * @Description 利用反射获取枚举描述  
	 * @param enumPackage
	 * @param value
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException 
	 * {@link }
	 * @since 2017年9月6日 下午4:58:39
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String fmtEnum(String enumPackage, String value) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(enumPackage == null || "".equals(enumPackage) || value == null || "".equals(value)){
			return "";
		}
		Class<Enum> clzz = (Class<Enum>) Class.forName(enumPackage);
		Enum<?> result = Enum.valueOf(clzz, value);
		Method method = clzz.getDeclaredMethod("getDesc");// 枚举类获取描述必须用此方法--getDesc()
		return (String) method.invoke(result, null);
	}
}
