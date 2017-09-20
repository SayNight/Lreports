Simple description
------- 
You can download the XLS or the CSV file from the browser by the Lreports.

Download XLS file base on Jxls 2.4.2,CSV file base on java.IO,also support JXEL.

Lreports is a small Java library to make generation of Excel reports easy. Lreports uses a special markup in Excel templates to define output formatting and data layout.

Installation
-------  
    git clone https://github.com/SayNight/lls-report.git

Consideration
-------  
 * Identifiers / variables: Must start with a-z, A-Z, 1-9. Can then be followed by 0-9, a-z, A-Z, . e.g. Case insensitive.
 * Apache POI: required version 3.16+.

Quick Start
------- 

    @RequestMapping("downLoad")//spring mvc
	public void orderDownload(HttpServletRequest request, HttpServletResponse response){
		List dataList = getDataList(XXXXX);
		String CSVTEMPLATE = "/WEB-INF/template/csv/template.csv";
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
	        ServletContext servletContext = webApplicationContext.getServletContext();
	        DownloadService downloadService = DownloadFactory.createDownloadService(request.getParameter("downType"));// downType :SupportTypeEnum.XLS.name()  or SupportTypeEnum.CSV.name()
	        String fileName = FileUtil.fmtFileName(request, DateUtils.getReqDate()+ "template");
	        downloadService.downloadFile(response, servletContext.getResourceAsStream(CSVTEMPLATE), dataList, fileName, null);
	}
