package com.lls.report.entity.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jxls.expression.JexlExpressionEvaluator;

import com.lls.report.util.DataFmtUtil;

/**
 * 
 * @Title CSVTemple.java
 * @Description csv模板处理
 * @author mrslee
 * @date 2017年9月8日 上午10:40:59
 * @version V1.0
 */
public class CSVTemple {
	
	private OutputStream os = null;
	private String[] titles;
	private String[] names;
	private static final String outStreamEncoding = "UTF-8";
	private Object dataList;
	
	public CSVTemple(OutputStream os, Object dataObj) {
		this.os = os;
		this.dataList = dataObj;
	}

	/**
	 * 
	 * @Title init
	 * @Description 模板初始化  
	 * @param inputStream 
	 * {@link }
	 * @since 2017年9月7日 下午3:06:10
	 */
	public void init(InputStream inputStream) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String str = null;
			int count = 0;
			while((str = br.readLine()) != null){
				if(count == 0){
					names = str.split(",");
				}else if(count == 1){
					titles = str.split(",(?=\\$)");
				}
				count++;
			}
			if(names == null || titles== null || names.length != titles.length){
				throw new RuntimeException("template error ");
			}
			for(int i = 0; i < names.length; i++){
				if(null != names[i]){
					os.write(names[i].trim().getBytes(outStreamEncoding), 0, names[i].trim().getBytes(outStreamEncoding).length);
				}
				if(i != names.length-1){
					os.write((byte)',');	
				}
			}
			os.write((byte)'\n');
		} catch (Exception e) {
			throw new RuntimeException("could not open template file  ");
		}
	}
	/**
	 * 
	 * @Title createCsvContent
	 * @Description 创建模板内容  
	 * @param collection
	 * @param evaluator
	 * @throws IOException 
	 * {@link }
	 * @since 2017年9月7日 下午3:32:37
	 */
	@SuppressWarnings("unchecked")
	public void createCsvContent(JexlExpressionEvaluator evaluator) throws IOException {
		if (dataList == null || !(dataList instanceof List)) {
			return ;
		}
		for (Iterator<?> iterator = ((List<?>) dataList).iterator(); iterator.hasNext();) {
			Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
			for (int column = 0; column < titles.length; column++) {
				//数据格式化
				Object value = DataFmtUtil.fmt(titles[column], rowMap, evaluator);
				if(null != value){
					byte[] bytes = value.toString().getBytes(outStreamEncoding);
					os.write(bytes, 0, bytes.length);
				}
				if(column != titles.length - 1 ){
					os.write((byte)',');
				}
			}
			os.write((byte)'\n');
		}
	}
	
}