package com.lls.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jxls.expression.JexlExpressionEvaluator;

import com.lls.report.constant.Constant;
import com.lls.report.entity.vo.CSVTemple;
import com.lls.report.enumtype.SupportTypeEnum;
import com.lls.report.function.JxlsExtFunction;
import com.lls.report.service.DownloadService;
import com.lls.report.util.FileUtil;

public class CSVDownloadServiceImpl implements DownloadService {

	@Override
	public void downloadFile(HttpServletResponse response, InputStream inputStream, Object dataObj, String fileName, Class<?> clazz) throws IOException, IllegalArgumentException {
		if(response == null || inputStream == null || dataObj == null || fileName == null || "".equals(fileName)){
			throw new IllegalArgumentException("downloadFile param error");
		}
		Map<String, Object> functionMap = new HashMap<>();
		functionMap.put(Constant.DEFAULTFUNCTION, JxlsExtFunction.class);
		if (clazz != null) {
			functionMap.put(Constant.DIVFUNCTION, clazz);
		}
		JexlExpressionEvaluator evaluator =  new JexlExpressionEvaluator();
		evaluator.getJexlEngine().setFunctions(functionMap);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		CSVTemple csvTemple = new CSVTemple(byteArrayOutputStream, dataObj);
		csvTemple.init(inputStream);
		csvTemple.createCsvContent(evaluator);
		
		FileUtil.exportFile(response, byteArrayOutputStream, fileName + "." + SupportTypeEnum.CSV.name().toLowerCase());
	}
	
}
