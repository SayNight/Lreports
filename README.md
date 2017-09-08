# lls-report
You can download the XLS or the CSV file through the browser --Java

Download XLS file base on Jxls 2.4.2，CSV file base on java.IO，also support JXEL。


#Use Guide
====  
 lls-report目前仅支持：xls(基于jxls二次改造)，cvs(基于流，支持jxel表达式) 格式下载。
 注意事项：结果集中列名不能下划线，建议起别名且只使用纯小写字母。
 使用步骤:
	（1）引入pom
		<dependency>
			<groupId>com.lls.report</groupId>
			<artifactId>lls-report</artifactId>
      <version>1.0</version>
		</dependency>
        （2）使用模板配置参考附件(example.xls,example.csv)。
	（3）代码
                DownloadService downloadService = DownloadFactory.createDownloadService(request.getParameter("downType"));//downType为jsp页面下载格式 form的name
	        String fileName = FileUtil.fmtFileName(request, DateUtils.getReqDate()+ "example”);//fileName  格式化文件名，若文件名称为英语且无特殊符号，此步骤可省略
	        downloadService.downloadFile(response, servletContext.getResourceAsStream(template), resultList, fileName, DownLoadFunction.class);
			  //response—下载需要使用其回写流
			 //servletContext.getResourceAsStream(template) —-  获取模板流 ,  template  模板
                      地址  //resultList  查询结果集
		       //fileName 文件名称
	     //DownLoadFunction.class 自定义扩展功能，比如对枚举英语转换车成中文时，多个状态对应一个中文描述，则可自定义

     目前默认功能扩展有：
	（1）格式化金额如：${jxext:fmtNumber(列名)}         如:${jxext:fmtNumber(amount)}   
	（2）日期：${jxext:fmtDateTime(列名)}                   如：${jxext:fmtDateTime(createtime)}
	（3）日期+时间：${jxext:fmtDateTime(列名)}          如${jxext:fmtDateTime(createtime)}
	（4）枚举的英文转换成中文：${jxext:fmtEnumTime('’,列名)}   如：${jxext:fmtEnumTime('com.lls.enumtype.StatusEnum’, status)}
