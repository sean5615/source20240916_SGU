<%/*
----------------------------------------------------------------------------------
File Name		: sgu046m_01m1.jsp
Author			: barry
Description		: �ץX�ɮv�Z�W�U - �B�z�޿譶��
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
/** �B�z�d�� Grid ��� */
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
		/** ���B�~�B�z�d��, �аѦ� Sample.txt */ 

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

/** �B�z�s�W�s�� */
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
		/** �B�z�s�W�ʧ@ */
		STUT006DAO	stut	=	new STUT006DAO(dbManager, conn, requestMap, session);
		try{
			stut.insert();
		}catch (Exception ex)
		{
			out.println(DataToJson.faileJson("���i���ƥӽ�!!"));
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

/** �ק�a�X��� */
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
		/** ���B�~�B�z�d��, �аѦ� Sample.txt */ 
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

/** �ק�s�� */
public void doModify(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));

		/** �ק���� */ 
        String	condition	=	"STNO	=	'" + Utility.dbStr(requestMap.get("STNO"))+ "' AND " +
								"ROWSTAMP	=	'" + Utility.dbStr(requestMap.get("ROWSTAMP")) + "' ";
		/** �B�z�ק�ʧ@ */
		STUT006DAO stut	=	new STUT006DAO(dbManager, conn, requestMap, session);
		int	updateCount	=	stut.update(condition);

		/** Commit Transaction */
		dbManager.commit();

		if (updateCount == 0)
			out.println(DataToJson.faileJson("������Ƥw�Q���ʹL, <br>�Э��s�d�߭ק�!!"));
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

/** �R����� */
public void doDelete(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{	
	try
	{
		Connection	conn		=	dbManager.getConnection(AUTCONNECT.mapConnect("CCS", session));

		/** �R������ */
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

		/** �B�z�R���ʧ@ */
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
		/** ��l�� rptFile */
	
		RptFile		rptFile	=	new RptFile(session.getId());
		rptFile.setColumn("���Y_1,���Y_2,���Y_3,���Y_4,��_1,��_2,��_3,��_4,��_5,��_6,��_7,��_8,��_9,��_10,��_11,��_12,��_13,��_14,��_15,��_16,��_17,��_18,��_19,��_20,��_21,��_22,��_23,��_24,��_25,��_26,��_27,��_28,��_29,��_30,��_31,��_32,��_33,��_34,��_35,��_36,��_37,��_38,��_39,��_40,��_41,��_42,��_43,��_44,��_45,��_46,��_47,��_48");		

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
				//����,�ɺ�12��
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
				//�U�@���Ĥ@��,���Y
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
			out.println("<script>top.close();alert(\"�L�ŦX��ƥi�ѦC�L!!\");</script>");
			return;
		}
		//rptFile.close();
		//if (true) return;
		/** ��l�Ƴ����� */
		report		report_	=	new report(dbManager, conn, out, "stu004m_01r1", report.onlineHtmlMode);

		/** �R�A�ܼƳB�z */
		//Hashtable	ht	=	new Hashtable();
		//report_.setDynamicVariable(ht);

		/** �}�l�C�L */
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
		/**���P�_ �O���O�o�ӾǨ�ǥ�**/		
		STUT003DAO S3 = new STUT003DAO(dbManager,conn);	
		S3.setResultColumn(" COUNT(1) AS NUM ");
		S3.setWhere(" ASYS ='"+(String)session.getAttribute("ASYS")+"' AND STNO ='"+(String)requestMap.get("STNO")+"' ");
		rs = S3.query();
		if(rs.next()){
		   if(rs.getInt("NUM")==0){
				Hashtable rowHt   =   new Hashtable();
				rowHt.put("MSG","�ӥͬd�L���");
				vtData.add(rowHt);
				out.println(DataToJson.vtToJson (vtData));	
				return;	
		   }
		}
		/**�b�P�_ �O���O�o�Ӥ��ߪ��ǥͩο�ץ�**/		
		
		STUT003GATEWAY stut = new STUT003GATEWAY(dbManager,conn);
		requestMap.put("AUTGETRANGE",session.getAttribute("AUTGETRANGE"));
		requestMap.put("ASYS",session.getAttribute("ASYS"));
	    stut.getNameSTUT006(requestMap,vtData);	
	    
		/** ���B�~�B�z�d��, �аѦ� Sample.txt */ 
		
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
	return ym.substring(0,4) + "�~" + ym.substring(4,6) + "��";
}

//�ץX�s��
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
		ws = wb.createSheet("�s��",0);
		//String COLUMN_HIDDEN = "����,�ѥ[�ɮv�Z,�Ǹ�,�ǥͧO,�Ǩt,�m�W,�ʧO,�X�ͦ~���,�����Ҧr��,�q��(��),�q��(�v),���,¾�~�O,�l���ϸ�,�q�T�a�},�q�l�H�c,��K���O,��ê���O,��ê����,�����ڧO,��׬��1,��׬��2,��׬��3,��׬��4,��׬��5,��׬��6,��׬��7,��׬��8,��׬��9";
		String COLUMN_HIDDEN = "����,�Юv�m�W,�ѥ[�ɮv�Z,�Ǹ�,�ǥͧO,�Ǩt,�m�W,�ʧO,�X�ͦ~���,�����Ҧr��,�q��(��),�q��(�v),���,¾�~�O,�l���ϸ�,�q�T�a�},�q�l�H�c,��K���O,��ê���O,��ê����,�����ڧO,��׬��1,��׬��2,��׬��3,��׬��4,��׬��5,��׬��6,��׬��7,��׬��8,��׬��9";
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
			/** �N���ۤ@���L�h */
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
					ws.addCell(new jxl.write.Label(0,y,rs.get("CENTER_ABBRNAME").toString()));	//����
					ws.addCell(new jxl.write.Label(1,y,rs.get("TCH_NAME").toString()));	//�Юv�m�W
					ws.addCell(new jxl.write.Label(2,y,rs.get("TUTOR_CLASS_MK").toString()));	//�ѥ[�ɮv�Z
					ws.addCell(new jxl.write.Label(3,y,rs.get("STNO").toString()));	//�Ǹ�
					ws.addCell(new jxl.write.Label(4,y,rs.get("STTYPE").toString()));	//�ǥͧO
					ws.addCell(new jxl.write.Label(5,y,rs.get("TOTAL_CRS_NAME").toString()));	//�Ǩt
					ws.addCell(new jxl.write.Label(6,y,rs.get("NAME").toString()));	//�m�W
					ws.addCell(new jxl.write.Label(7,y,rs.get("SEX").toString()));	//�ʧO
					ws.addCell(new jxl.write.Label(8,y,rs.get("BIRTHDATE").toString()));	//�X�ͦ~���
					ws.addCell(new jxl.write.Label(9,y,rs.get("IDNO").toString()));	//�����Ҧr��
					ws.addCell(new jxl.write.Label(10,y,"("+rs.get("AREACODE_OFFICE").toString()+")"+rs.get("TEL_OFFICE").toString()+"-"+rs.get("TEL_OFFICE_EXT").toString()));	//�q��(��)
					ws.addCell(new jxl.write.Label(11,y,"("+rs.get("AREACODE_HOME").toString()+")"+rs.get("TEL_HOME").toString()));	//�q��(�v)
					ws.addCell(new jxl.write.Label(12,y,rs.get("MOBILE").toString()));	//���
					ws.addCell(new jxl.write.Label(13,y,rs.get("VOCATION").toString()));	//¾�~�O
					ws.addCell(new jxl.write.Label(14,y,rs.get("CRRSADDR_ZIP").toString()));	//�l���ϸ�
					ws.addCell(new jxl.write.Label(15,y,rs.get("CRRSADDR").toString()));	//�q�T�a�}
					ws.addCell(new jxl.write.Label(16,y,rs.get("EMAIL").toString()));	//�q�l�H�c
					ws.addCell(new jxl.write.Label(17,y,rs.get("REDUCE_TYPE").toString()));	//��K���O
					ws.addCell(new jxl.write.Label(18,y,rs.get("HANDICAP_TYPE").toString()));	//��ê���O
					ws.addCell(new jxl.write.Label(19,y,rs.get("HANDICAP_GRADE").toString()));	//��ê����
					ws.addCell(new jxl.write.Label(20,y,rs.get("ORIGIN_RACE").toString()));	//�����ڧO
					ws.addCell(new jxl.write.Label(21,y,rs.get("CRS_NAME").toString()));	//��׬��1
					x=22;
					STNO_BEFORE = STNO_NOW;
				}
				y++;
			}
		} finally {
		}
		LoadingStatus.setStatus(session.getId(), 60, "��ƶץX����", LoadingStatus.success);

	}
	catch (Exception ex)
	{
		LoadingStatus.setStatus(session.getId(), 60, "��ƶץX����", LoadingStatus.success);
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

//�ץX���٥�
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
		ws = wb.createSheet("����",0);
		String COLUMN_HIDDEN = "����,�Юv�m�W,�Ǹ�,�ǥͧO,�Ǩt,�m�W,�ʧO,�X�ͦ~���,�����Ҧr��,�q��(��),�q��(�v),���,¾�~�O,�l���ϸ�,�q�T�a�},�q�l�H�c,��K���O,��ê���O,��ê����,�����ڧO,��׬��1,��׬��2,��׬��3,��׬��4,��׬��5,��׬��6,��׬��7,��׬��8,��׬��9";
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
			/** �N���ۤ@���L�h */
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
					ws.addCell(new jxl.write.Label(0,y,Utility.nullToSpace(rs.get("CENTER_ABBRNAME"))));	//����
					ws.addCell(new jxl.write.Label(1,y,rs.get("TCH_NAME").toString()));	//�Юv�m�W
					ws.addCell(new jxl.write.Label(2,y,Utility.nullToSpace(rs.get("STNO"))));	//�Ǹ�
					ws.addCell(new jxl.write.Label(3,y,Utility.nullToSpace(rs.get("STTYPE"))));	//�ǥͧO
					ws.addCell(new jxl.write.Label(4,y,Utility.nullToSpace(rs.get("TOTAL_CRS_NAME"))));	//�Ǩt
					ws.addCell(new jxl.write.Label(5,y,Utility.nullToSpace(rs.get("NAME"))));	//�m�W
					ws.addCell(new jxl.write.Label(6,y,Utility.nullToSpace(rs.get("SEX"))));	//�ʧO
					ws.addCell(new jxl.write.Label(7,y,Utility.nullToSpace(rs.get("BIRTHDATE"))));	//�X�ͦ~���
					ws.addCell(new jxl.write.Label(8,y,Utility.nullToSpace(rs.get("IDNO"))));	//�����Ҧr��
					ws.addCell(new jxl.write.Label(9,y,"("+Utility.nullToSpace(rs.get("AREACODE_OFFICE"))+")"+Utility.nullToSpace(rs.get("TEL_OFFICE"))+"-"+Utility.nullToSpace(rs.get("TEL_OFFICE_EXT"))));	//�q��(��)
					ws.addCell(new jxl.write.Label(10,y,"("+Utility.nullToSpace(rs.get("AREACODE_HOME"))+")"+Utility.nullToSpace(rs.get("TEL_HOME"))));	//�q��(�v)
					ws.addCell(new jxl.write.Label(11,y,Utility.nullToSpace(rs.get("MOBILE"))));	//���
					ws.addCell(new jxl.write.Label(12,y,Utility.nullToSpace(rs.get("VOCATION"))));	//¾�~�O
					ws.addCell(new jxl.write.Label(13,y,Utility.nullToSpace(rs.get("CRRSADDR_ZIP"))));	//�l���ϸ�
					ws.addCell(new jxl.write.Label(14,y,Utility.nullToSpace(rs.get("CRRSADDR"))));	//�q�T�a�}
					ws.addCell(new jxl.write.Label(15,y,Utility.nullToSpace(rs.get("EMAIL"))));	//�q�l�H�c
					ws.addCell(new jxl.write.Label(16,y,Utility.nullToSpace(rs.get("REDUCE_TYPE"))));	//��K���O
					ws.addCell(new jxl.write.Label(17,y,Utility.nullToSpace(rs.get("HANDICAP_TYPE"))));	//��ê���O
					ws.addCell(new jxl.write.Label(18,y,Utility.nullToSpace(rs.get("HANDICAP_GRADE"))));	//��ê����
					ws.addCell(new jxl.write.Label(19,y,Utility.nullToSpace(rs.get("ORIGIN_RACE"))));	//�����ڧO
					ws.addCell(new jxl.write.Label(20,y,Utility.nullToSpace(rs.get("CRS_NAME"))));	//��׬��1
					x=21;
					STNO_BEFORE = STNO_NOW;
				}
				y++;
			}
		} finally {
		}
		LoadingStatus.setStatus(session.getId(), 60, "��ƶץX����", LoadingStatus.success);

	}
	catch (Exception ex)
	{
		LoadingStatus.setStatus(session.getId(), 60, "��ƶץX����", LoadingStatus.success);
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

//�ץX������
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
		ws = wb.createSheet("����",0);
		String COLUMN_HIDDEN = "����,�Юv�m�W,�Ǹ�,�m�W,�ʧO,�q��(��),�q��(�v),���,�l���ϸ�,�q�T�a�},�q�l�H�c";
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
			/** �N���ۤ@���L�h */
			for (int i = 1; i <= rs1.getColumnCount(); i++)
				rowHt.put(rs1.getColumnName(i), rs1.getString(i));
			vtData.add(rowHt);
		}
		
		int y = 1;
		try {
			for (int k=0;k<vtData.size();k++){
				Hashtable rs = (Hashtable)vtData.get(k);
				ws.addCell(new jxl.write.Label(0,y,rs.get("CENTER_ABBRNAME").toString()));	//����
				ws.addCell(new jxl.write.Label(1,y,rs.get("TCH_NAME").toString()));	//�Юv�m�W
				ws.addCell(new jxl.write.Label(2,y,rs.get("STNO").toString()));	//�Ǹ�
				ws.addCell(new jxl.write.Label(3,y,rs.get("NAME").toString()));	//�m�W
				ws.addCell(new jxl.write.Label(4,y,rs.get("SEX").toString()));	//�ʧO
				ws.addCell(new jxl.write.Label(5,y,"("+rs.get("AREACODE_OFFICE").toString()+")"+rs.get("TEL_OFFICE").toString()+"-"+rs.get("TEL_OFFICE_EXT").toString()));	//�q��(��)
				ws.addCell(new jxl.write.Label(6,y,"("+rs.get("AREACODE_HOME").toString()+")"+rs.get("TEL_HOME").toString()));	//�q��(�v)
				ws.addCell(new jxl.write.Label(7,y,rs.get("MOBILE").toString()));	//���
				ws.addCell(new jxl.write.Label(8,y,rs.get("CRRSADDR_ZIP").toString()));	//�l���ϸ�
				ws.addCell(new jxl.write.Label(9,y,rs.get("CRRSADDR").toString()));	//�q�T�a�}
				ws.addCell(new jxl.write.Label(10,y,rs.get("EMAIL").toString()));	//�q�l�H�c

				y++;
			}
		} finally {
		}
		LoadingStatus.setStatus(session.getId(), 60, "��ƶץX����", LoadingStatus.success);

	}
	catch (Exception ex)
	{
		LoadingStatus.setStatus(session.getId(), 60, "��ƶץX����", LoadingStatus.success);
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