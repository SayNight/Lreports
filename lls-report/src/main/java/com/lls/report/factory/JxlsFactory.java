package com.lls.report.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import com.lls.report.function.JxlsExtFunction;

public class JxlsFactory {

	public static void processTemplate(InputStream is, OutputStream os, Object dataObj, Object functionObj) throws IOException {
		Context context = new Context();
		context.putVar("data", dataObj);
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Transformer transformer = jxlsHelper.createTransformer(is, os);
		JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
		Map<String, Object> functionMap = new HashMap<>();
		functionMap.put("jxext", JxlsExtFunction.class);
		functionMap.put("jxdiv", functionObj);
		evaluator.getJexlEngine().setFunctions(functionMap);
		jxlsHelper.processTemplate(context, transformer);
	}

	public static void processTemplate(HttpServletRequest request, HttpServletResponse response, InputStream inputStream, Object dataObj, String fileName) throws IOException {
		processTemplate(request, response, inputStream, dataObj, fileName, null);
	}
	
	public static void processTemplate(HttpServletRequest request, HttpServletResponse response, InputStream inputStream, Object dataObj, String fileName, Object functionObj) throws IOException {
		try (	InputStream is = inputStream;
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				OutputStream outputStream = response.getOutputStream();) {
			JxlsFactory.processTemplate(is, os, dataObj, functionObj);
			ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fmtFileName(request, fileName));
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = bis.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
		}
	}
	

	private static String fmtFileName(HttpServletRequest request, String fileName) throws IOException {
		fileName = fileName + JxlsExtFunction.fmtDate(new Date()) + "." + "xls";
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.toLowerCase().indexOf("msie") > 0 && userAgent.toLowerCase().indexOf("msie 5") < 0
				&& userAgent.toLowerCase().indexOf("msie 6") < 0) {// IE8-10
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else if (userAgent.toLowerCase().indexOf("like gecko") > 0) {// IE11
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}
		return fileName;
	}

}
