package com.lls.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import com.lls.report.constant.Constant;
import com.lls.report.enumtype.SupportTypeEnum;
import com.lls.report.function.JxlsExtFunction;
import com.lls.report.service.DownloadService;
import com.lls.report.util.FileUtil;

public class XLSDownloadServiceImpl implements DownloadService {

	@Override
	public void downloadFile(HttpServletResponse response, InputStream inputStream, Object dataObj, String fileName, Class<?> clazz) throws IOException, IllegalArgumentException {
		if(response == null || inputStream == null || dataObj == null || fileName == null || "".equals(fileName)){
			throw new IllegalArgumentException("downloadFile param error");
		}
		try(InputStream is = inputStream; ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Context context = new Context();
			context.putVar(Constant.DATAKEY, dataObj);
			JxlsHelper jxlsHelper = JxlsHelper.getInstance();
			Transformer transformer = jxlsHelper.createTransformer(is, outputStream);
			JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
			Map<String, Object> functionMap = new HashMap<>();
			functionMap.put(Constant.DEFAULTFUNCTION, JxlsExtFunction.class);
			if (clazz != null) {
				functionMap.put(Constant.DIVFUNCTION, clazz);
			}
			evaluator.getJexlEngine().setFunctions(functionMap);
			jxlsHelper.processTemplate(context, transformer);
			FileUtil.exportFile(response, outputStream, fileName + "." + SupportTypeEnum.XLS.name().toLowerCase());
		} 
		
	}
	
}
