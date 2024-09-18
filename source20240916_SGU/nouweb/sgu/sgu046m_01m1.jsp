<%/*
----------------------------------------------------------------------------------
File Name		: sgu046m_01m1.jsp
Author			: barry
Description		: 匯出導師班名冊 - 處理邏輯頁面
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
1.0.0		097/12/25	barry    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/modulepageinit.jsp"%>
<%@page import="com.nou.stu.dao.*, com.nou.*"%>

<%!
/** 處理查詢 Grid 資料 */
public void doQuery(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{	
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));
		int		pageNo		=	Integer.parseInt(Utility.checkNull(requestMap.get("pageNo"), "1"));
		int		pageSize	=	Integer.parseInt(Utility.checkNull(requestMap.get("pageSize"), "10"));
		Vector	vtData		=	new	Vector();	
		String ASYS=Utility.checkNull(session.getAttribute("ASYS"), "");				
		STUT006GATEWAY stut = new STUT006GATEWAY(dbManager,conn,pageNo,pageSize);
	    stut.queryStut006(requestMap,vtData,ASYS);	
		/** 需額外處理範例, 請參考 Sample.txt */ 

		out.println(DataToJson.vtToJson (stut.getTotalRowCount(), vtData));		
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

/** 處理新增存檔 */
public void doAdd(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));	
		String CENTER_CODE = (String)requestMap.get("APP_CENTER_CODE");
		if("%".equals(CENTER_CODE)){
			requestMap.remove("APP_CENTER_CODE");
			requestMap.put("APP_CENTER_CODE","00");
		}
		requestMap.put("UPD_DATE",	DateUtil.getNowDate());
		requestMap.put("UPD_TIME",	DateUtil.getNowTimeS());
		requestMap.put("UPD_USER_ID",	(String)session.getAttribute("USER_ID"));
		requestMap.put("ROWSTAMP",	DateUtil.getNowTimeMs());
		/** 處理新增動作 */
		STUT006DAO	stut	=	new STUT006DAO(dbManager, conn, requestMap, session);
		try{
			stut.insert();
		}catch (Exception ex)
		{
			out.println(DataToJson.faileJson("不可重複申請!!"));
			return;
		}
		
		
		/** Commit Transaction */
		dbManager.commit();

		out.println(DataToJson.successJson());
	}
	catch (Exception ex)
	{
		/** Rollback Transaction */
		dbManager.rollback();

		throw ex;
	}
	finally
	{
		dbManager.close();
	}

}

/** 修改帶出資料 */
public void doQueryEdit(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));
		int		pageNo		=	Integer.parseInt(Utility.checkNull(requestMap.get("pageNo"), "1"));
		int		pageSize	=	Integer.parseInt(Utility.checkNull(requestMap.get("pageSize"), "10"));
		Vector	vtData		=	new	Vector();			
		STUT006GATEWAY stut = new STUT006GATEWAY(dbManager,conn);
	    stut.queryEditStut006(requestMap,vtData);	
		/** 需額外處理範例, 請參考 Sample.txt */ 
		out.println(DataToJson.vtToJson (stut.getTotalRowCount(), vtData));		
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

/** 修改存檔 */
public void doModify(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));

		/** 修改條件 */ 
        String	condition	=	"STNO	=	'" + Utility.dbStr(requestMap.get("STNO"))+ "' AND " +
								"ROWSTAMP	=	'" + Utility.dbStr(requestMap.get("ROWSTAMP")) + "' ";
		/** 處理修改動作 */
		STUT006DAO stut	=	new STUT006DAO(dbManager, conn, requestMap, session);
		int	updateCount	=	stut.update(condition);

		/** Commit Transaction */
		dbManager.commit();

		if (updateCount == 0)
			out.println(DataToJson.faileJson("此筆資料已被異動過, <br>請重新查詢修改!!"));
		else
			out.println(DataToJson.successJson());
	}
	catch (Exception ex)
	{
		/** Rollback Transaction */
		dbManager.rollback();

		throw ex;
	}
	finally
	{
		dbManager.close();
	}	
}

/** 刪除資料 */
public void doDelete(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{	
	try
	{
		Connection	conn		=	dbManager.getConnection(AUTCONNECT.mapConnect("CCS", session));

		/** 刪除條件 */
		StringBuffer	conditionBuff	=	new StringBuffer();

	
		String[]	STNO		 			=	Utility.split(requestMap.get("STNO").toString(), ",");
		String[]	APP_REISSUE_YYMM		=	Utility.split(requestMap.get("APP_REISSUE_YYMM").toString(), ",");
		String[]	REISSUE_TIMES			=	Utility.split(requestMap.get("REISSUE_TIMES").toString(), ",");
		

		for (int i = 0; i < STNO.length; i++)
		{
			if (i > 0)
				conditionBuff.append (" OR ");

			conditionBuff.append
			(
				"( STNO ='" + Utility.dbStr(STNO[i]) + "' AND APP_REISSUE_YYMM ='" + Utility.dbStr(APP_REISSUE_YYMM[i]) + "' AND REISSUE_TIMES ='" + Utility.dbStr(REISSUE_TIMES[i]) + "' )"
			);
		}

		/** 處理刪除動作 */
		STUT006DAO	stut	=	new STUT006DAO(dbManager, conn, requestMap, session);
		stut.delete(conditionBuff.toString());

		/** Commit Transaction */
		dbManager.commit();

		out.println(DataToJson.successJson());
	}
	catch (Exception ex)
	{
		/** Rollback Transaction */
		dbManager.rollback();

		throw ex;
	}
	finally
	{
		dbManager.close();
	}	
}

private void doPrint(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));		
		Vector	vtData		=	new	Vector();	
		String ASYS=(String)session.getAttribute("ASYS");				
		STUT006GATEWAY stut = new STUT006GATEWAY(dbManager,conn);	
	    stut.printStut006(requestMap,vtData,ASYS);	
		/** 初始化 rptFile */
	
		RptFile		rptFile	=	new RptFile(session.getId());
		rptFile.setColumn("表頭_1,表頭_2,表頭_3,表頭_4,表身_1,表身_2,表身_3,表身_4,表身_5,表身_6,表身_7,表身_8,表身_9,表身_10,表身_11,表身_12,表身_13,表身_14,表身_15,表身_16,表身_17,表身_18,表身_19,表身_20,表身_21,表身_22,表身_23,表身_24,表身_25,表身_26,表身_27,表身_28,表身_29,表身_30,表身_31,表身_32,表身_33,表身_34,表身_35,表身_36,表身_37,表身_38,表身_39,表身_40,表身_41,表身_42,表身_43,表身_44,表身_45,表身_46,表身_47,表身_48");		

		int row = 1;
		int col = 0;		
		int page = 1;
		String[] stno = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] name = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] reason = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] no = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String ym = null;
		String _ym = null;
		String times = null;
		String _times = null;
        
		for (int k = 0; k < vtData.size(); k++)
		{
		    Hashtable rs = (Hashtable)vtData.get(k);
			ym = rs.get("APP_REISSUE_YYMM").toString();
			times = rs.get("REISSUE_TIMES").toString();

			if (_ym == null) {
				_ym = ym;
				_times = times;
				rptFile.add(rs.get("CENTER_CODE_DESC"));				
				//rptFile.add("");
				rptFile.add(formatYM(rs.get("APP_REISSUE_YYMM").toString()));
				rptFile.add(rs.get("REISSUE_TIMES"));
				rptFile.add(page);
				page ++;
			}			
			if (!(ym.equals(_ym) && times.equals(_times)) || (row > 1 && row%12 == 1)) {
				if (col <= stno.length) {
					for (int i=0;i<stno.length;i++) {
						rptFile.add(stno[i]);
					}
					for (int i=0;i<stno.length;i++) {
						rptFile.add(name[i]);
					}
					for (int i=0;i<stno.length;i++) {
						rptFile.add(reason[i]);
					}
					for (int i=0;i<stno.length;i++) {
						rptFile.add(no[i]+"&nbsp;");
					}

					for (int i=0;i<stno.length;i++) {
						if (stno[i].equals("&nbsp;")) {
							row ++;
						}
					}

					col = 0;
					stno = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
					name = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
					reason = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
					no = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
					
				}
				//換頁,補滿12筆
				System.out.println(row);
				int a = (row-1)%12;		
				if (a != 0) {	
					for (int i=1;i<=(12-a);i++) {
						rptFile.add("&nbsp;");
						rptFile.add("&nbsp;");
						rptFile.add("&nbsp;");
						rptFile.add("&nbsp;");
					}
				}

				row = row + (12-a);
				//下一頁第一筆,表頭
				rptFile.add(rs.get("CENTER_CODE_DESC"));
				//rptFile.add("");								
				rptFile.add(formatYM(rs.get("APP_REISSUE_YYMM").toString()));
				rptFile.add(rs.get("REISSUE_TIMES"));
				rptFile.add(page);
				page ++;		
				_ym = ym;
				_times = times;	
			}
				
			
			if (col < 4) {
				stno[col] = rs.get("STNO").toString();
				name[col] = rs.get("NAME").toString();
				reason[col] = rs.get("REISSUE_REASON_DESC").toString();
				no[col] = rs.get("RECEIPT_NO").toString();
				col ++;				
			} else {
				for (int i=0;i<stno.length;i++) {
					rptFile.add(stno[i]);
				}
				for (int i=0;i<stno.length;i++) {
					rptFile.add(name[i]);
				}
				for (int i=0;i<stno.length;i++) {
					rptFile.add(reason[i]);
				}
				for (int i=0;i<stno.length;i++) {
					rptFile.add(no[i]+"&nbsp;");
				}
				
				col = 0;
				stno = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
				name = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
				reason = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
				no = new String[]{"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
				stno[col] = rs.get("STNO").toString();
				name[col] = rs.get("NAME").toString();
				reason[col] = rs.get("REISSUE_REASON_DESC").toString();
				no[col] = rs.get("RECEIPT_NO").toString();
				col ++;
			}			
			row ++;							
		}

		if (col != 0) {
			for (int i=0;i<stno.length;i++) {
				rptFile.add(stno[i]);
			}
			for (int i=0;i<stno.length;i++) {
				rptFile.add(name[i]);
			}
			for (int i=0;i<stno.length;i++) {
				rptFile.add(reason[i]);
			}
			for (int i=0;i<stno.length;i++) {
				rptFile.add(no[i]+"&nbsp;");
			}
			row =row + 4-(col+1);
			
		}

		if (ym != null) {
			for (int i=(row%12)+1;i<=12;i++) {
				rptFile.add("&nbsp;");
				rptFile.add("&nbsp;");
				rptFile.add("&nbsp;");
				rptFile.add("&nbsp;");			
			}
		}
	
		if (rptFile.size() == 0)
		{
			out.println("<script>top.close();alert(\"無符合資料可供列印!!\");</script>");
			return;
		}
		//rptFile.close();
		//if (true) return;
		/** 初始化報表物件 */
		report		report_	=	new report(dbManager, conn, out, "stu004m_01r1", report.onlineHtmlMode);

		/** 靜態變數處理 */
		//Hashtable	ht	=	new Hashtable();
		//report_.setDynamicVariable(ht);

		/** 開始列印 */
		report_.genReport(rptFile);
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

public void doGetSTDATA(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{		
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));
		int		pageNo		=	Integer.parseInt(Utility.checkNull(requestMap.get("pageNo"), "1"));
		int		pageSize	=	Integer.parseInt(Utility.checkNull(requestMap.get("pageSize"), "10"));
		Vector	vtData		=	new	Vector();	
		DBResult    rs      =   null;
		String ASYS=Utility.checkNull(session.getAttribute("ASYS"), "");	
		/**先判斷 是不是這個學制的學生**/		
		STUT003DAO S3 = new STUT003DAO(dbManager,conn);	
		S3.setResultColumn(" COUNT(1) AS NUM ");
		S3.setWhere(" ASYS ='"+(String)session.getAttribute("ASYS")+"' AND STNO ='"+(String)requestMap.get("STNO")+"' ");
		rs = S3.query();
		if(rs.next()){
		   if(rs.getInt("NUM")==0){
				Hashtable rowHt   =   new Hashtable();
				rowHt.put("MSG","該生查無資料");
				vtData.add(rowHt);
				out.println(DataToJson.vtToJson (vtData));	
				return;	
		   }
		}
		/**在判斷 是不是這個中心的學生或選修生**/		
		
		STUT003GATEWAY stut = new STUT003GATEWAY(dbManager,conn);
		requestMap.put("AUTGETRANGE",session.getAttribute("AUTGETRANGE"));
		requestMap.put("ASYS",session.getAttribute("ASYS"));
	    stut.getNameSTUT006(requestMap,vtData);	
	    
		/** 需額外處理範例, 請參考 Sample.txt */ 
		
		out.println(DataToJson.vtToJson (vtData));		
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

String formatYM(String ym) {
	if (ym.length() != 6) return ym;
	return ym.substring(0,4) + "年" + ym.substring(4,6) + "月";
}

//匯出新生
public void doExport1(HttpServletResponse response,JspWriter out, DBManager dbManager,HttpServletRequest request, HttpSession session,Hashtable requestMap) throws Exception {
	String path = APConfig.getProperty("SHARED_TMP_PATH") + File.separator + "work" + File.separator + "stu";
	String filenm = "SGU046M_NEW_" + DateUtil.getNowDate() + DateUtil.getNowTimeMs() + ".xls";
	response.setHeader("Content-disposition","attachment; filename=" + filenm);
	response.setContentType("application/vnd.ms-excel;charset=big5");
	response.setHeader("Cache-Control", "max-age=60"); 
	out.clear();

	jxl.write.WritableWorkbook wb = null;
    jxl.write.WritableSheet ws = null;
	try
	{
		Connection conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("STU", session));

		wb = jxl.Workbook.createWorkbook(response.getOutputStream());						
		ws = wb.createSheet("新生",0);
		//String COLUMN_HIDDEN = "中心,參加導師班,學號,學生別,學系,姓名,性別,出生年月日,身分證字號,電話(公),電話(宅),手機,職業別,郵遞區號,通訊地址,電子信箱,減免類別,障礙類別,障礙等級,原住民族別,選修科目1,選修科目2,選修科目3,選修科目4,選修科目5,選修科目6,選修科目7,選修科目8,選修科目9";
		String COLUMN_HIDDEN = "中心,教師姓名,參加導師班,學號,學生別,學系,姓名,性別,出生年月日,身分證字號,電話(公),電話(宅),手機,職業別,郵遞區號,通訊地址,電子信箱,減免類別,障礙類別,障礙等級,原住民族別,選修科目1,選修科目2,選修科目3,選修科目4,選修科目5,選修科目6,選修科目7,選修科目8,選修科目9";
		String title_value="";
		String[] colinfo = COLUMN_HIDDEN.split(",");
		for (int i=0;i<colinfo.length;i++) {
			ws.addCell(new jxl.write.Label(i,0,colinfo[i]));
		}
		
		//System.out.println("title_value"+title_value);
		Vector	vtData		=	new	Vector();
		String ASYS=Utility.checkNull(session.getAttribute("ASYS"), "");
		DBResult	rs1	=	null;
		StringBuffer	sql		=	new StringBuffer();
		sql.append("SELECT A.CENTER_CODE, H.CENTER_ABBRNAME, I.CODE_NAME TUTOR_CLASS_MK, A.STNO, J.CODE_NAME STTYPE, K.TOTAL_CRS_NAME TOTAL_CRS_NAME, C.NAME, " +
					"L.CODE_NAME SEX, B.BIRTHDATE, B.IDNO, C.AREACODE_OFFICE, C.TEL_OFFICE, C.TEL_OFFICE_EXT, C.AREACODE_HOME, C.TEL_HOME, C.MOBILE, M.CODE_NAME VOCATION, "+
					"C.CRRSADDR_ZIP, C.CRRSADDR, C.EMAIL, N.CODE_NAME ORIGIN_RACE, P.CODE_NAME REDUCE_TYPE, Q.CODE_NAME HANDICAP_TYPE, R.CODE_NAME HANDICAP_GRADE, O.CRS_NAME , S.NAME AS TCH_NAME "+
					"FROM SGUT011 A "+
					"JOIN STUT003 B ON A.STNO=B.STNO "+
					"JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE "+
					"LEFT JOIN REGT004 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.STNO=D.STNO AND D.AUDIT_STATUS='1' "+
					"LEFT JOIN SGUT004 E ON A.STNO=E.STNO AND E.HAND_NATIVE='1' AND E.AUDIT_MK='2' "+
					"LEFT JOIN SGUT004 F ON A.STNO=F.STNO AND F.HAND_NATIVE='2' AND F.AUDIT_MK='2' "+
					"LEFT JOIN REGT007 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.STNO=G.STNO AND G.UNTAKECRS_MK='N' AND G.UNQUAL_TAKE_MK='N' "+
					"LEFT JOIN SYST002 H ON A.CENTER_CODE=H.CENTER_CODE "+
					"LEFT JOIN SYST001 I ON I.KIND='TUTOR_CLASS_MK' AND I.CODE=B.TUTOR_CLASS_MK "+
					"LEFT JOIN SYST001 J ON J.KIND='STTYPE' AND J.CODE=B.STTYPE "+
					"LEFT JOIN SYST008 K ON K.FACULTY_CODE=B.PRE_MAJOR_FACULTY AND K.TOTAL_CRS_NO=B.J_FACULTY_CODE "+
					"LEFT JOIN SYST001 L ON L.KIND='SEX' AND L.CODE=C.SEX "+
					"LEFT JOIN SYST001 M ON M.KIND='VOCATION' AND M.CODE=C.VOCATION "+
					"LEFT JOIN SYST001 N ON N.KIND='ORIGIN_RACE' AND N.CODE=E.PARENTS_RACE "+
					"LEFT JOIN COUT002 O ON O.CRSNO=G.CRSNO "+
					"LEFT JOIN SYST001 P ON P.KIND='REDUCE_TYPE' AND P.CODE=D.REDUCE_TYPE "+
					"LEFT JOIN SYST001 Q ON Q.KIND='HANDICAP_TYPE' AND Q.CODE=F.HANDICAP_TYPE "+
					"LEFT JOIN SYST001 R ON R.KIND='HANDICAP_GRADE' AND R.CODE=F.HANDICAP_GRADE "+
					"LEFT JOIN TRAT001 S ON S.IDNO=A.TCH_IDNO "+
					"WHERE A.AYEAR='"+Utility.checkNull(requestMap.get("AYEAR"), "")+"' "+
					"AND A.SMS='"+Utility.checkNull(requestMap.get("SMS"), "")+"' "+
					"AND A.KIND='1' "+
					"AND B.TUTOR_CLASS_MK IN ('1','2') "
		);
		if(!"".equals(Utility.checkNull(requestMap.get("CENTER_CODE"), "")))
			sql.append("AND A.CENTER_CODE = '"+Utility.checkNull(requestMap.get("CENTER_CODE"), "")+"' ");
		if(!"".equals(Utility.checkNull(requestMap.get("IDNO"), "")))
			sql.append("AND A.TCH_IDNO = '"+Utility.checkNull(requestMap.get("IDNO"), "")+"' ");
		sql.append("ORDER BY A.CENTER_CODE, A.STNO, G.CRSNO");
		rs1	=	dbManager.getSimpleResultSet(conn);
		rs1.open();
		rs1.executeQuery(sql.toString());
		Hashtable rowHt = null;
		while (rs1.next()) {
			rowHt = new Hashtable();
			/** 將欄位抄一份過去 */
			for (int i = 1; i <= rs1.getColumnCount(); i++)
				rowHt.put(rs1.getColumnName(i), rs1.getString(i));
			vtData.add(rowHt);
		}
		
		String STNO_NOW = "";
		String STNO_BEFORE = "";
		int x = 0;
		int y = 1;
		try {
			for (int k=0;k<vtData.size();k++){
				Hashtable rs = (Hashtable)vtData.get(k);
				STNO_NOW = rs.get("STNO").toString();
				if(STNO_NOW.equals(STNO_BEFORE))
				{
					y--;
					ws.addCell(new jxl.write.Label(x,y,rs.get("CRS_NAME").toString()));
					x++;
				}else{
					x=0;
					ws.addCell(new jxl.write.Label(0,y,rs.get("CENTER_ABBRNAME").toString()));	//中心
					ws.addCell(new jxl.write.Label(1,y,rs.get("TCH_NAME").toString()));	//教師姓名
					ws.addCell(new jxl.write.Label(2,y,rs.get("TUTOR_CLASS_MK").toString()));	//參加導師班
					ws.addCell(new jxl.write.Label(3,y,rs.get("STNO").toString()));	//學號
					ws.addCell(new jxl.write.Label(4,y,rs.get("STTYPE").toString()));	//學生別
					ws.addCell(new jxl.write.Label(5,y,rs.get("TOTAL_CRS_NAME").toString()));	//學系
					ws.addCell(new jxl.write.Label(6,y,rs.get("NAME").toString()));	//姓名
					ws.addCell(new jxl.write.Label(7,y,rs.get("SEX").toString()));	//性別
					ws.addCell(new jxl.write.Label(8,y,rs.get("BIRTHDATE").toString()));	//出生年月日
					ws.addCell(new jxl.write.Label(9,y,rs.get("IDNO").toString()));	//身分證字號
					ws.addCell(new jxl.write.Label(10,y,"("+rs.get("AREACODE_OFFICE").toString()+")"+rs.get("TEL_OFFICE").toString()+"-"+rs.get("TEL_OFFICE_EXT").toString()));	//電話(公)
					ws.addCell(new jxl.write.Label(11,y,"("+rs.get("AREACODE_HOME").toString()+")"+rs.get("TEL_HOME").toString()));	//電話(宅)
					ws.addCell(new jxl.write.Label(12,y,rs.get("MOBILE").toString()));	//手機
					ws.addCell(new jxl.write.Label(13,y,rs.get("VOCATION").toString()));	//職業別
					ws.addCell(new jxl.write.Label(14,y,rs.get("CRRSADDR_ZIP").toString()));	//郵遞區號
					ws.addCell(new jxl.write.Label(15,y,rs.get("CRRSADDR").toString()));	//通訊地址
					ws.addCell(new jxl.write.Label(16,y,rs.get("EMAIL").toString()));	//電子信箱
					ws.addCell(new jxl.write.Label(17,y,rs.get("REDUCE_TYPE").toString()));	//減免類別
					ws.addCell(new jxl.write.Label(18,y,rs.get("HANDICAP_TYPE").toString()));	//障礙類別
					ws.addCell(new jxl.write.Label(19,y,rs.get("HANDICAP_GRADE").toString()));	//障礙等級
					ws.addCell(new jxl.write.Label(20,y,rs.get("ORIGIN_RACE").toString()));	//原住民族別
					ws.addCell(new jxl.write.Label(21,y,rs.get("CRS_NAME").toString()));	//選修科目1
					x=22;
					STNO_BEFORE = STNO_NOW;
				}
				y++;
			}
		} finally {
		}
		LoadingStatus.setStatus(session.getId(), 60, "資料匯出完畢", LoadingStatus.success);

	}
	catch (Exception ex)
	{
		LoadingStatus.setStatus(session.getId(), 60, "資料匯出失敗", LoadingStatus.success);
		throw ex;
	}
	finally
	{
        try {			
			wb.write();
			wb.close();
		} catch (Exception e) {
		
		}
		dbManager.close();
	}
}

//匯出身障生
public void doExport2(HttpServletResponse response,JspWriter out, DBManager dbManager,HttpServletRequest request, HttpSession session,Hashtable requestMap) throws Exception {
	String path = APConfig.getProperty("SHARED_TMP_PATH") + File.separator + "work" + File.separator + "stu";
	String filenm = "SGU046M_NEW_" + DateUtil.getNowDate() + DateUtil.getNowTimeMs() + ".xls";
	response.setHeader("Content-disposition","attachment; filename=" + filenm);
	response.setContentType("application/vnd.ms-excel;charset=big5");
	response.setHeader("Cache-Control", "max-age=60"); 
	out.clear();

	jxl.write.WritableWorkbook wb = null;
    jxl.write.WritableSheet ws = null;
	try
	{
		Connection conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("STU", session));

		wb = jxl.Workbook.createWorkbook(response.getOutputStream());						
		ws = wb.createSheet("身障",0);
		String COLUMN_HIDDEN = "中心,教師姓名,學號,學生別,學系,姓名,性別,出生年月日,身分證字號,電話(公),電話(宅),手機,職業別,郵遞區號,通訊地址,電子信箱,減免類別,障礙類別,障礙等級,原住民族別,選修科目1,選修科目2,選修科目3,選修科目4,選修科目5,選修科目6,選修科目7,選修科目8,選修科目9";
		String title_value="";
		String[] colinfo = COLUMN_HIDDEN.split(",");
		for (int i=0;i<colinfo.length;i++) {
			ws.addCell(new jxl.write.Label(i,0,colinfo[i]));
		}
		
		//System.out.println("title_value"+title_value);
		Vector	vtData		=	new	Vector();
		String ASYS=Utility.checkNull(session.getAttribute("ASYS"), "");
		DBResult	rs1	=	null;
		StringBuffer	sql		=	new StringBuffer();
		sql.append("SELECT A.CENTER_CODE, H.CENTER_ABBRNAME, I.CODE_NAME TUTOR_CLASS_MK, A.STNO, J.CODE_NAME STTYPE, K.TOTAL_CRS_NAME TOTAL_CRS_NAME, C.NAME, " +
					"L.CODE_NAME SEX, B.BIRTHDATE, B.IDNO, C.AREACODE_OFFICE, C.TEL_OFFICE, C.TEL_OFFICE_EXT, C.AREACODE_HOME, C.TEL_HOME, C.MOBILE, M.CODE_NAME VOCATION, "+
					"C.CRRSADDR_ZIP, C.CRRSADDR, C.EMAIL, N.CODE_NAME ORIGIN_RACE, P.CODE_NAME REDUCE_TYPE, Q.CODE_NAME HANDICAP_TYPE, R.CODE_NAME HANDICAP_GRADE, O.CRS_NAME ,S.NAME AS TCH_NAME "+
					"FROM SGUT011 A "+
					"JOIN STUT003 B ON A.STNO=B.STNO "+
					"JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE "+
					"LEFT JOIN REGT004 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.STNO=D.STNO AND D.AUDIT_STATUS='1' "+
					"LEFT JOIN SGUT004 E ON A.STNO=E.STNO AND E.HAND_NATIVE='1' AND E.AUDIT_MK='2' "+
					"LEFT JOIN SGUT004 F ON A.STNO=F.STNO AND F.HAND_NATIVE='2' AND F.AUDIT_MK='2' "+
					"JOIN REGT007 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.STNO=G.STNO AND G.UNTAKECRS_MK='N' AND G.UNQUAL_TAKE_MK='N' AND PAYMENT_STATUS = '2' "+
					"LEFT JOIN SYST002 H ON A.CENTER_CODE=H.CENTER_CODE "+
					"LEFT JOIN SYST001 I ON I.KIND='TUTOR_CLASS_MK' AND I.CODE=B.TUTOR_CLASS_MK "+
					"LEFT JOIN SYST001 J ON J.KIND='STTYPE' AND J.CODE=B.STTYPE "+
					"LEFT JOIN SYST008 K ON K.FACULTY_CODE=B.PRE_MAJOR_FACULTY AND K.TOTAL_CRS_NO=B.J_FACULTY_CODE "+
					"LEFT JOIN SYST001 L ON L.KIND='SEX' AND L.CODE=C.SEX "+
					"LEFT JOIN SYST001 M ON M.KIND='VOCATION' AND M.CODE=C.VOCATION "+
					"LEFT JOIN SYST001 N ON N.KIND='ORIGIN_RACE' AND N.CODE=E.PARENTS_RACE "+
					"LEFT JOIN COUT002 O ON O.CRSNO=G.CRSNO "+
					"LEFT JOIN SYST001 P ON P.KIND='REDUCE_TYPE' AND P.CODE=D.REDUCE_TYPE "+
					"LEFT JOIN SYST001 Q ON Q.KIND='HANDICAP_TYPE' AND Q.CODE=F.HANDICAP_TYPE "+
					"LEFT JOIN SYST001 R ON R.KIND='HANDICAP_GRADE' AND R.CODE=F.HANDICAP_GRADE "+
					"LEFT JOIN TRAT001 S ON S.IDNO=A.TCH_IDNO "+
					"WHERE A.AYEAR='"+Utility.checkNull(requestMap.get("AYEAR"), "")+"' "+
					"AND A.SMS='"+Utility.checkNull(requestMap.get("SMS"), "")+"' "+
					"AND A.KIND='2' ");
		if(!"".equals(Utility.checkNull(requestMap.get("CENTER_CODE"), "")))
			sql.append("AND A.CENTER_CODE = '"+Utility.checkNull(requestMap.get("CENTER_CODE"), "")+"' ");
		if(!"".equals(Utility.checkNull(requestMap.get("IDNO"), "")))
			sql.append("AND A.TCH_IDNO = '"+Utility.checkNull(requestMap.get("IDNO"), "")+"' ");
		sql.append("ORDER BY A.CENTER_CODE, A.STNO, G.CRSNO");
		rs1	=	dbManager.getSimpleResultSet(conn);
		rs1.open();
		rs1.executeQuery(sql.toString());
		Hashtable rowHt = null;
		while (rs1.next()) {
			rowHt = new Hashtable();
			/** 將欄位抄一份過去 */
			for (int i = 1; i <= rs1.getColumnCount(); i++)
				rowHt.put(rs1.getColumnName(i), rs1.getString(i));
			vtData.add(rowHt);
		}
		
		String STNO_NOW = "";
		String STNO_BEFORE = "";
		int x = 0;
		int y = 1;
		try {
			for (int k=0;k<vtData.size();k++){
				Hashtable rs = (Hashtable)vtData.get(k);
				STNO_NOW = rs.get("STNO").toString();
				if(STNO_NOW.equals(STNO_BEFORE))
				{
					y--;
					ws.addCell(new jxl.write.Label(x,y,rs.get("CRS_NAME").toString()));
					x++;
				}else{
					x=0;
					ws.addCell(new jxl.write.Label(0,y,Utility.nullToSpace(rs.get("CENTER_ABBRNAME"))));	//中心
					ws.addCell(new jxl.write.Label(1,y,rs.get("TCH_NAME").toString()));	//教師姓名
					ws.addCell(new jxl.write.Label(2,y,Utility.nullToSpace(rs.get("STNO"))));	//學號
					ws.addCell(new jxl.write.Label(3,y,Utility.nullToSpace(rs.get("STTYPE"))));	//學生別
					ws.addCell(new jxl.write.Label(4,y,Utility.nullToSpace(rs.get("TOTAL_CRS_NAME"))));	//學系
					ws.addCell(new jxl.write.Label(5,y,Utility.nullToSpace(rs.get("NAME"))));	//姓名
					ws.addCell(new jxl.write.Label(6,y,Utility.nullToSpace(rs.get("SEX"))));	//性別
					ws.addCell(new jxl.write.Label(7,y,Utility.nullToSpace(rs.get("BIRTHDATE"))));	//出生年月日
					ws.addCell(new jxl.write.Label(8,y,Utility.nullToSpace(rs.get("IDNO"))));	//身分證字號
					ws.addCell(new jxl.write.Label(9,y,"("+Utility.nullToSpace(rs.get("AREACODE_OFFICE"))+")"+Utility.nullToSpace(rs.get("TEL_OFFICE"))+"-"+Utility.nullToSpace(rs.get("TEL_OFFICE_EXT"))));	//電話(公)
					ws.addCell(new jxl.write.Label(10,y,"("+Utility.nullToSpace(rs.get("AREACODE_HOME"))+")"+Utility.nullToSpace(rs.get("TEL_HOME"))));	//電話(宅)
					ws.addCell(new jxl.write.Label(11,y,Utility.nullToSpace(rs.get("MOBILE"))));	//手機
					ws.addCell(new jxl.write.Label(12,y,Utility.nullToSpace(rs.get("VOCATION"))));	//職業別
					ws.addCell(new jxl.write.Label(13,y,Utility.nullToSpace(rs.get("CRRSADDR_ZIP"))));	//郵遞區號
					ws.addCell(new jxl.write.Label(14,y,Utility.nullToSpace(rs.get("CRRSADDR"))));	//通訊地址
					ws.addCell(new jxl.write.Label(15,y,Utility.nullToSpace(rs.get("EMAIL"))));	//電子信箱
					ws.addCell(new jxl.write.Label(16,y,Utility.nullToSpace(rs.get("REDUCE_TYPE"))));	//減免類別
					ws.addCell(new jxl.write.Label(17,y,Utility.nullToSpace(rs.get("HANDICAP_TYPE"))));	//障礙類別
					ws.addCell(new jxl.write.Label(18,y,Utility.nullToSpace(rs.get("HANDICAP_GRADE"))));	//障礙等級
					ws.addCell(new jxl.write.Label(19,y,Utility.nullToSpace(rs.get("ORIGIN_RACE"))));	//原住民族別
					ws.addCell(new jxl.write.Label(20,y,Utility.nullToSpace(rs.get("CRS_NAME"))));	//選修科目1
					x=21;
					STNO_BEFORE = STNO_NOW;
				}
				y++;
			}
		} finally {
		}
		LoadingStatus.setStatus(session.getId(), 60, "資料匯出完畢", LoadingStatus.success);

	}
	catch (Exception ex)
	{
		LoadingStatus.setStatus(session.getId(), 60, "資料匯出失敗", LoadingStatus.success);
		throw ex;
	}
	finally
	{
        try {			
			wb.write();
			wb.close();
		} catch (Exception e) {
		
		}
		dbManager.close();
	}
}

//匯出中輟生
public void doExport3(HttpServletResponse response,JspWriter out, DBManager dbManager,HttpServletRequest request, HttpSession session,Hashtable requestMap) throws Exception {
	String path = APConfig.getProperty("SHARED_TMP_PATH") + File.separator + "work" + File.separator + "stu";
	String filenm = "SGU046M_NEW_" + DateUtil.getNowDate() + DateUtil.getNowTimeMs() + ".xls";
	response.setHeader("Content-disposition","attachment; filename=" + filenm);
	response.setContentType("application/vnd.ms-excel;charset=big5");
	response.setHeader("Cache-Control", "max-age=60"); 
	out.clear();

	jxl.write.WritableWorkbook wb = null;
    jxl.write.WritableSheet ws = null;
	try
	{
		Connection conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("STU", session));

		wb = jxl.Workbook.createWorkbook(response.getOutputStream());						
		ws = wb.createSheet("中輟",0);
		String COLUMN_HIDDEN = "中心,教師姓名,學號,姓名,性別,電話(公),電話(宅),手機,郵遞區號,通訊地址,電子信箱";
		String title_value="";
		String[] colinfo = COLUMN_HIDDEN.split(",");
		for (int i=0;i<colinfo.length;i++) {
			ws.addCell(new jxl.write.Label(i,0,colinfo[i]));
		}
		
		//System.out.println("title_value"+title_value);
		Vector	vtData		=	new	Vector();
		String ASYS=Utility.checkNull(session.getAttribute("ASYS"), "");
		DBResult	rs1	=	null;
		StringBuffer	sql		=	new StringBuffer();
		sql.append("SELECT A.CENTER_CODE, H.CENTER_ABBRNAME, I.CODE_NAME TUTOR_CLASS_MK, A.STNO, J.CODE_NAME STTYPE, K.TOTAL_CRS_NAME TOTAL_CRS_NAME, C.NAME, "+
					"L.CODE_NAME SEX, B.BIRTHDATE, B.IDNO, C.AREACODE_OFFICE, C.TEL_OFFICE, C.TEL_OFFICE_EXT, C.AREACODE_HOME, C.TEL_HOME, C.MOBILE, S.NAME AS TCH_NAME, "+
					"C.CRRSADDR,C.CRRSADDR_ZIP, C.EMAIL "+
					"FROM SGUT011 A "+
					"JOIN STUT003 B ON A.STNO=B.STNO "+
					"JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE "+
					"LEFT JOIN SYST002 H ON A.CENTER_CODE=H.CENTER_CODE "+
					"LEFT JOIN SYST001 I ON I.KIND='TUTOR_CLASS_MK' AND I.CODE=B.TUTOR_CLASS_MK "+
					"LEFT JOIN SYST001 J ON J.KIND='STTYPE' AND J.CODE=B.STTYPE "+
					"LEFT JOIN SYST008 K ON K.FACULTY_CODE=B.PRE_MAJOR_FACULTY AND K.TOTAL_CRS_NO=B.J_FACULTY_CODE "+
					"LEFT JOIN SYST001 L ON L.KIND='SEX' AND L.CODE=C.SEX "+
					"LEFT JOIN TRAT001 S ON S.IDNO=A.TCH_IDNO "+
					"WHERE A.AYEAR='"+Utility.checkNull(requestMap.get("AYEAR"), "")+"' "+
					"AND A.SMS='"+Utility.checkNull(requestMap.get("SMS"), "")+"' "+
					"AND A.KIND='3' ");
		if(!"".equals(Utility.checkNull(requestMap.get("CENTER_CODE"), "")))
			sql.append("AND A.CENTER_CODE = '"+Utility.checkNull(requestMap.get("CENTER_CODE"), "")+"' ");
		if(!"".equals(Utility.checkNull(requestMap.get("IDNO"), "")))
			sql.append("AND A.TCH_IDNO = '"+Utility.checkNull(requestMap.get("IDNO"), "")+"' ");
		sql.append("ORDER BY A.CENTER_CODE, A.STNO");

		rs1	=	dbManager.getSimpleResultSet(conn);
		rs1.open();
		rs1.executeQuery(sql.toString());
		Hashtable rowHt = null;
		while (rs1.next()) {
			rowHt = new Hashtable();
			/** 將欄位抄一份過去 */
			for (int i = 1; i <= rs1.getColumnCount(); i++)
				rowHt.put(rs1.getColumnName(i), rs1.getString(i));
			vtData.add(rowHt);
		}
		
		int y = 1;
		try {
			for (int k=0;k<vtData.size();k++){
				Hashtable rs = (Hashtable)vtData.get(k);
				ws.addCell(new jxl.write.Label(0,y,rs.get("CENTER_ABBRNAME").toString()));	//中心
				ws.addCell(new jxl.write.Label(1,y,rs.get("TCH_NAME").toString()));	//教師姓名
				ws.addCell(new jxl.write.Label(2,y,rs.get("STNO").toString()));	//學號
				ws.addCell(new jxl.write.Label(3,y,rs.get("NAME").toString()));	//姓名
				ws.addCell(new jxl.write.Label(4,y,rs.get("SEX").toString()));	//性別
				ws.addCell(new jxl.write.Label(5,y,"("+rs.get("AREACODE_OFFICE").toString()+")"+rs.get("TEL_OFFICE").toString()+"-"+rs.get("TEL_OFFICE_EXT").toString()));	//電話(公)
				ws.addCell(new jxl.write.Label(6,y,"("+rs.get("AREACODE_HOME").toString()+")"+rs.get("TEL_HOME").toString()));	//電話(宅)
				ws.addCell(new jxl.write.Label(7,y,rs.get("MOBILE").toString()));	//手機
				ws.addCell(new jxl.write.Label(8,y,rs.get("CRRSADDR_ZIP").toString()));	//郵遞區號
				ws.addCell(new jxl.write.Label(9,y,rs.get("CRRSADDR").toString()));	//通訊地址
				ws.addCell(new jxl.write.Label(10,y,rs.get("EMAIL").toString()));	//電子信箱

				y++;
			}
		} finally {
		}
		LoadingStatus.setStatus(session.getId(), 60, "資料匯出完畢", LoadingStatus.success);

	}
	catch (Exception ex)
	{
		LoadingStatus.setStatus(session.getId(), 60, "資料匯出失敗", LoadingStatus.success);
		throw ex;
	}
	finally
	{
        try {			
			wb.write();
			wb.close();
		} catch (Exception e) {
		
		}
		dbManager.close();
	}
}

%>