<%/*
----------------------------------------------------------------------------------
File Name		: sgu055m_01m1.jsp
Author			: Maggie
Description		: SGU055M_導師班學生編班資訊查詢 - 處理邏輯頁面
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		2020/03/03	Maggie    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/modulepageinit.jsp"%>
<%@page import="com.nou.reg.dao.* ,
                com.acer.util.DateUtil ,
                com.nou.sys.dao.* ,
                com.nou.cou.* "%>

<%!
/** 處理查詢 Grid 資料 */

public void doQuery(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{
		int		pageNo		=	Integer.parseInt(Utility.checkNull(requestMap.get("pageNo"), "1"));
		int		pageSize	=	Integer.parseInt(Utility.checkNull(requestMap.get("pageSize"), "10"));
		
   	    Hashtable condiHt =  new Hashtable();   //畫面輸入的值
   	    Hashtable resultHt = null; //用來放回來的result
   	    
        Vector result = new Vector();
		Connection	conn		=	dbManager.getConnection(AUTCONNECT.mapConnect("REG", session));
        REGT005GATEWAY  R05  =    new REGT005GATEWAY(dbManager,conn,pageNo,pageSize);
    	result = R05.getSgu055mQuery(requestMap);
        out.println(DataToJson.vtToJson(R05.getTotalRowCount(), result));				
    }
	catch (Exception ex)
	{
		throw ex;
	}
	finally
	{
		dbManager.close();
	}
}
/** 匯出選取資料 */
public void doExportAll(HttpServletResponse response, JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	String filenm = "SGU055M_" + DateUtil.getNowDate() + DateUtil.getNowTimeMs() + ".xls";
	response.setHeader("Content-disposition","attachment; filename=" + filenm);
	response.setContentType("application/x-download");
	response.setHeader("Cache-Control", "max-age=60"); 
	out.clear();
	Connection	conn = null;	
	DBResult	rs = null;
	
	// 匯成EXCEL用
	jxl.write.WritableWorkbook wb = null;
    jxl.write.WritableSheet ws = null;
	
	/** 處理 Excel 表身資料 */
	try
	{
		conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("POP", session));
		
		REGT005GATEWAY  R05  =    new REGT005GATEWAY(dbManager,conn);
		Vector resultVt = R05.getSgu055mQuery(requestMap);

		// 放入EXCEL中-------------------------------開始
		wb = jxl.Workbook.createWorkbook(response.getOutputStream());						
		ws = wb.createSheet("學生註冊選課資料",0);	
		// 欄位表頭
		String[] colinfo = {"學年","學期","學號","姓名","中心","新舊生","導師班","導師","手機","電子信箱","繳費狀態","通訊地址","專班註記","身障註記"};
		
		for (int i=0;i<colinfo.length;i++)
			ws.addCell(new jxl.write.Label(i,0,colinfo[i]));
		
		// 放入資料
		int rowNum = 1; // 在EXCEL中的行數,第0行為表頭
		for (int i=0;i<resultVt.size();i++) {
			Hashtable content = (Hashtable)resultVt.get(i);
			
				ws.addCell(new jxl.write.Label(0,rowNum,content.get("AYEAR").toString()));
				ws.addCell(new jxl.write.Label(1,rowNum,content.get("CSMS").toString()));
				ws.addCell(new jxl.write.Label(2,rowNum,content.get("STNO").toString()));
				ws.addCell(new jxl.write.Label(3,rowNum,content.get("NAME").toString()));
				ws.addCell(new jxl.write.Label(4,rowNum,content.get("CENTER_ABBRNAME").toString()));
				ws.addCell(new jxl.write.Label(5,rowNum,content.get("NEWOLD_ST").toString()));
				ws.addCell(new jxl.write.Label(6,rowNum,content.get("SG_ST").toString()));
				ws.addCell(new jxl.write.Label(7,rowNum,content.get("TCH_NAME").toString()));
				ws.addCell(new jxl.write.Label(8,rowNum,content.get("MOBILE").toString()));
				ws.addCell(new jxl.write.Label(9,rowNum,content.get("EMAIL").toString()));
				ws.addCell(new jxl.write.Label(10,rowNum,content.get("PAYSTATUS").toString()));
				ws.addCell(new jxl.write.Label(11,rowNum,content.get("CRRSADDR").toString()));
				ws.addCell(new jxl.write.Label(12,rowNum,content.get("SPECIAL_STTYPE_CTYPE").toString()));
				ws.addCell(new jxl.write.Label(13,rowNum,content.get("HANDICAP_MK").toString()));
				rowNum++;
		}
		// 放入EXCEL中-------------------------------結束
	}
	catch (Exception ex)
	{
		throw ex;
	}
	finally
	{
		if (rs != null)
			rs.close();
		rs	=	null;
		
		try {			
				wb.write();
				wb.close();
			} catch (Exception e) {
			
			}
	}
}


private void doPrint(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	
}

//取得申請中心名稱
private String getCenterName(DBManager dbManager, Connection conn, String CENTER_CODE) throws Exception
{
	String		CENTER_NAME		=	"全部中心";
	DBResult	rs				=	null;
	
	try
	{
		SYST002DAO	SYST002DAO	=	new SYST002DAO(dbManager, conn);
		SYST002DAO.setResultColumn("CENTER_NAME");
		SYST002DAO.setCENTER_CODE(CENTER_CODE);
		
		rs	=	SYST002DAO.query();
		
		if (rs.next())
			CENTER_NAME	=	rs.getString("CENTER_NAME");
		
		return CENTER_NAME;
	}
	catch(Exception e)
	{
		throw e;
	}
	finally
	{
		if (rs != null)
			rs.close();
	}
}
%>