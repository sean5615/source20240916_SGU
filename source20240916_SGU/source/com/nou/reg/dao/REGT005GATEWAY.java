package com.nou.reg.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

import com.acer.apps.Page;
import com.acer.db.DBManager;
import com.acer.db.query.DBResult;
import com.acer.util.DateUtil;
import com.acer.util.Utility;
import com.nou.pcs.dao.PCST004GATEWAY;
import com.nou.reg.bo.REGGETSTNOFEE;
import com.nou.sys.SYSGETSMSDATA;
import com.nou.sys.dao.SYST001DAO;
import com.nou.sys.dao.SYST002DAO;
/*
 * (REGT005) Gateway/*
 *-------------------------------------------------------------------------------*
 * Author    : 國長      2007/05/04
 * Modification Log :
 * Vers     Date           By             Notes
 *--------- -------------- -------------- ----------------------------------------
 * V0.0.1   2007/05/04     國長           建立程式
 *                                        新增 getRegt005ForUse(Hashtable ht)
 *          2007/06/11     俊賢           新增 getRegt005Stut002Stut003ForUse(Hashtable ht,String SMSSD)
 *          2007/06/15     俊賢           新增 getRegt005Regt007ForUse(Hashtable ht)
 *          2007/06/20     俊賢           新增 getRegt005Stut002Stut003DataForUse(Hashtable ht)
 *          2007/06/21     俊賢           新增 getReg002Regt005Stut002Stut003ForUse(Hashtable ht)
 *          2007/06/21     俊賢           新增 getRegt002Stut002Stut003ForUse(Hashtable ht)
                                               getStut002Stut003ForUse(Hashtable ht)
 *          2007/07/03     立文           新增 getPrintRegt005Stut003Stut002ForUse(Hashtable ht)
 *          2007/07/05     立文           新增 getPrint2Regt005Stut003Stut002ForUse(Hashtable ht)
 *          2007/07/05     立文           新增 getPrint3Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice)
 *          2007/07/05     立文           新增 getPrint4Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice)
 *          2007/07/05     立文           新增 getPrint5Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice)
 *          2007/07/05     立文           新增 getPrint6Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice)
 *          2007/10/01     Jason          修改 getReg002Regt005Stut002Stut003ForUse(Hashtable ht)
 *          2007/10/01     Jason          修改 getRegt002Stut002Stut003ForUse(Hashtable ht)
 *          2007/10/01     Jason          新增 getRegt005Stut002Stut003Query(Hashtable ht)
 *          2007/10/01     立文           新增 getRegt005Stut003Stut002Syst002ForUse(Hashtable ht)
 *          2007/10/08     Jason          修改 getRegt005Regt007ForUse(Hashtable ht)
 *          2007/11/20     Jason          修改 getStut002Stut003ForUse(Hashtable ht)
                                          修改 getRegt005Stut002Stut003ForUse(Hashtable ht)
                                          修改 getRegt005Stut002Stut003DataForUse(Hashtable ht)
                                          修改 getRegt005Regt007ForUse(Hashtable ht)
 *	  2007/12/24	sorge	新增 getPub140mQueryTarget1(Vector vt, Hashtable ht)
 * V0.0.20  2008/03/03     葉瑞欽         新增 getRegt005Regt007Cout002Stut003Syst002Plat002ForUse(Hashtable ht)
 * V0.0.21  2008/03/07     葉瑞欽         修改 getRegt005Regt007Cout002Stut003Syst002Plat002ForUse(Hashtable ht)
 * V0.0.22	2008/03/07		sRu		修改 getRegt005Stut002Stut003ForUse(Hashtable ht) 勾稽 REGT010
 * V0.0.23	2008/03/13		sRu		修改 getRegt005Stut002Stut003DataForUse(Hashtable ht)
 * 									修改 getReg002Regt005Stut002Stut003ForUse
 * V0.0.24	2008/03/18		sRu		修改 getRegt005Regt007ForUse(Hashtable ht)
 * V0.0.25  2008/03/20      WEN     	修改  getRegt005Stut002Stut003ForUse(Hashtable ht)
 * V0.0.26	2008/03/20		sRu		修改 getRegt005Stut002Stut003DataForUse(Hashtable ht)
 * V0.0.27	2008/03/24		sRu		修改 getStut002Stut003ForUse(Hashtable ht)
 * 									修改getRegt005Stut002Stut003DataForUse(Hashtable ht)
 * V0.0.28	2008/03/28		sRu		修改 getStut002Stut003ForUse(Hashtable ht)
 * 										getRegt005Stut002Stut003DataForUse(Hashtable ht)
 * v0.0.29  008/04/07       wen     修改getRegt005Stut002Stut003ForUse(Hashtable ht)
 * v0.0.30  2008/4/21		north	新增getDataForStuindexs(Hashtable ht)
 * V0.0.31  2009/01/09      wen     新增getReg133Regt005age(Hashtable ht)
 *                                      getReg133Regt005(Hashtable ht)
 *                                      getReg131Regt005(Hashtable ht)
 *                                      getReg131Regt005age(Hashtable ht)
 *V0.0.32   2009/01/09       WEN     新增getRegt005Regt007Cout002Stut003Syst002Plat002TotForUse(Hashtable ht)
 *                           PEI     新增getsgu803rPrint(Vector vt, Hashtable ht)
 *                           PEI     新增getsgu804rPrint(Vector vt, Hashtable ht)
 *                           PEI     新增getsgu806rPrint(Vector vt, Hashtable ht)
 *                           PEI     新增getsgu807rPrint(Vector vt, Hashtable ht)
 *                           PEI     新增getsgu808rPrint(Vector vt, Hashtable ht)
 *          2010/03/09       PEI     新增getsgu8011rPrint(Vector vt, Hashtable ht)
 *          2010/03/10       PEI     新增getsgu809rPrint(Vector vt, Hashtable ht)
 *          2010/03/23       PEI     新增getsgu812rPrint(Vector vt, Hashtable ht)
 *--------------------------------------------------------------------------------
 */
public class REGT005GATEWAY {

    /** 資料排序方式 */
    private String orderBy = "";
    private DBManager dbmanager = null;
    private Connection conn = null;
    /* 頁數 */
    private int pageNo = 0;
    /** 每頁筆數 */
    private int pageSize = 0;

    /** 記錄是否分頁 */
    private boolean pageQuery = false;

    /** 用來存放 SQL 語法的物件 */
    private StringBuffer sql = new StringBuffer();

    /** <pre>
     *  設定資料排序方式.
     *  Ex: "AYEAR, SMS DESC"
     *      先以 AYEAR 排序再以 SMS 倒序序排序
     *  </pre>
     */
    public void setOrderBy(String orderBy) {
        if(orderBy == null) {
            orderBy = "";
        }
        this.orderBy = orderBy;
    }

    /** 取得總筆數 */
    public int getTotalRowCount() {
        return Page.getTotalRowCount();
    }

    /** 不允許建立空的物件 */
    private REGT005GATEWAY() {}

    /** 建構子，查詢全部資料用 */
    public REGT005GATEWAY(DBManager dbmanager, Connection conn) {
        this.dbmanager = dbmanager;
        this.conn = conn;
    }

    /** 建構子，查詢分頁資料用 */
    public REGT005GATEWAY(DBManager dbmanager, Connection conn, int pageNo, int pageSize) {
        this.dbmanager = dbmanager;
        this.conn = conn;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        pageQuery = true;
    }

    /**
     *
     * @param ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *         若該欄位有中文名稱，則其 KEY 請加上 _NAME, EX: SMS 其中文欄位請設為 SMS_NAME
     * @throws Exception
     */
    public Vector getRegt005ForUse(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append(
            "SELECT R05.ASYS, R05.AYEAR, R05.SMS, R05.CENTER_CODE, R05.STNO, R05.IDNO, R05.BIRTHDATE, R05.NAME, R05.TAKE_DATE, R05.MAG_ORDER_MK, R05.TAKE_ITEM_MK, R05.PAYMENT_STATUS, R05.TAKE_ABNDN, R05.TAKE_ABNDN_DATE, R05.UPD_DATE, R05.UPD_USER_ID, R05.TAKE_MANNER, R05.RETURN_MK " +
            "FROM REGT005 R05 " +
            "WHERE 1 = 1 "
        );
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND R05.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND R05.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND R05.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")) {
            sql.append("AND R05.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND R05.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND R05.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND R05.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("NAME")).equals("")) {
            sql.append("AND R05.NAME = '" + Utility.nullToSpace(ht.get("NAME")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("TAKE_DATE")).equals("")) {
            sql.append("AND R05.TAKE_DATE = '" + Utility.nullToSpace(ht.get("TAKE_DATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("MAG_ORDER_MK")).equals("")) {
            sql.append("AND R05.MAG_ORDER_MK = '" + Utility.nullToSpace(ht.get("MAG_ORDER_MK")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("TAKE_ITEM_MK")).equals("")) {
            sql.append("AND R05.TAKE_ITEM_MK = '" + Utility.nullToSpace(ht.get("TAKE_ITEM_MK")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("PAYMENT_STATUS")).equals("")) {
            sql.append("AND R05.PAYMENT_STATUS = '" + Utility.nullToSpace(ht.get("PAYMENT_STATUS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("TAKE_ABNDN")).equals("")) {
            sql.append("AND R05.TAKE_ABNDN = '" + Utility.nullToSpace(ht.get("TAKE_ABNDN")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("TAKE_ABNDN_DATE")).equals("")) {
            sql.append("AND R05.TAKE_ABNDN_DATE = '" + Utility.nullToSpace(ht.get("TAKE_ABNDN_DATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("UPD_DATE")).equals("")) {
            sql.append("AND R05.UPD_DATE = '" + Utility.nullToSpace(ht.get("UPD_DATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("UPD_USER_ID")).equals("")) {
            sql.append("AND R05.UPD_USER_ID = '" + Utility.nullToSpace(ht.get("UPD_USER_ID")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("TAKE_MANNER")).equals("")) {
            sql.append("AND R05.TAKE_MANNER = '" + Utility.nullToSpace(ht.get("TAKE_MANNER")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("RETURN_MK")).equals("")) {
            sql.append("AND R05.RETURN_MK = '" + Utility.nullToSpace(ht.get("RETURN_MK")) + "' ");
        }

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    private Vector Syst001(DBManager dbmanager,Connection conn,String KIND,String CODE) throws Exception
    {
        Vector result = new Vector();

        SYST001DAO SYST001 = new SYST001DAO(dbmanager, conn);
        SYST001.setResultColumn("CODE,CODE_NAME");
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(KIND).equals("")) {
            SYST001.setKIND(Utility.nullToSpace(KIND));
        }
        if(!Utility.nullToSpace(CODE).equals("")) {
            SYST001.setCODE(Utility.nullToSpace(CODE));
        }
        DBResult rs = SYST001.query();

        Hashtable rowHt = null;
        while (rs.next())
        {
            rowHt = new Hashtable();
            /** 將欄位抄一份過去 */
            for (int i = 1; i <= rs.getColumnCount(); i++)
                rowHt.put(rs.getColumnName(i), rs.getString(i));

            result.add(rowHt);
        }
        return result;
    }
    /**
     * JOIN 其它 Regt005,Stut002,Stut003,Syst003,Syst001 將欄位的中文化
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getRegt005Stut002Stut003ForUse(Hashtable ht) throws Exception {

        SYSGETSMSDATA sysgetsmsdata = new SYSGETSMSDATA(dbmanager);
        sysgetsmsdata.setCONN_TYPE("1");
        sysgetsmsdata.setSYS_DATE(DateUtil.getNowDate());
        sysgetsmsdata.setSMS_TYPE("3");
        sysgetsmsdata.execute();
        String nextayear = sysgetsmsdata.getAYEAR();
        String nextsms   = sysgetsmsdata.getSMS();

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        /** 影響sql暫時先拿掉 SYST008.TOTAL_CRS_NAME**/
        sql.append
        (
            "SELECT DISTINCT STUT003.ASYS ,S1.CODE_NAME AS CASYS ,REGT005.AYEAR ,REGT005.SMS ,S2.CODE_NAME AS SMS_NAME , " +
            "STUT002.NAME, STUT003.IDNO ,STUT003.STNO ,STUT003.BIRTHDATE ,STUT003.CENTER_CODE ,SYST002.CENTER_NAME,SYST002.CENTER_ABBRNAME, " +
            "STUT003.PRE_MAJOR_FACULTY,STUT003.STTYPE, STUT003.REDUCE_TYPE, (STUT002.AREACODE_HOME||'-'||STUT002.TEL_HOME) AS AREACODE_HOME, " +
            "S3.CODE_NAME AS CREDUCE_TYPE,SYST001.CODE_NAME AS CSTTYPE,A.PAID_AMT,A.PAYMENT_MANNER,S5.CODE_NAME AS CPAYMENT_MANNER, " +
            "NVL(REGT005.TOTAL_CRD_CNT,'0') AS TOTAL_CRD_CNT, REGT010.WRITEOFF_NO ,REGT005.UPD_USER_ID , REGT005.UPD_DATE, REGT010.ABNDN_ORDER  " +
            "FROM  REGT005 " +
            "JOIN REGT010 ON REGT010.AYEAR = REGT005.AYEAR AND REGT010.SMS = REGT005.SMS AND REGT010.STNO = REGT005.STNO " +
            "JOIN STUT003 ON REGT005.STNO = STUT003.STNO " +
            "JOIN STUT002 ON STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE " +
            "JOIN SYST002 ON SYST002.CENTER_CODE = STUT003.CENTER_CODE " +
            "JOIN SYST001 S1 ON S1.KIND='ASYS' AND S1.CODE = STUT003.ASYS " +
            "JOIN SYST001 S2 ON S2.KIND='SMS' AND S2.CODE = REGT005.SMS " +
            "JOIN SYST001 ON SYST001.KIND='STTYPE' AND SYST001.CODE = STUT003.STTYPE " +
          //  "LEFT JOIN SYST008 ON STUT003.ASYS=SYST008.ASYS AND STUT003.PRE_MAJOR_FACULTY=SYST008.FACULTY_CODE AND (STUT003.J_FACULTY_CODE = SYST008.TOTAL_CRS_NO OR (STUT003.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' AND NVL(STUT003.J_FACULTY_CODE,'X') ='X')) " +
            "LEFT JOIN REGT004 ON REGT004.STNO = STUT003.STNO " +
            "LEFT JOIN SYST001 S3 ON S3.KIND='REDUCE_TYPE' AND S3.CODE = STUT003.REDUCE_TYPE " +
            "JOIN SYST001 S4 ON S4.KIND='PAYMENT_STATUS' AND REGT005.PAYMENT_STATUS = S4.CODE " +
            "LEFT JOIN ( " +
                "SELECT A.AYEAR,A.SMS,A.STNO,SUM(B.PAID_AMT) AS PAID_AMT,MAX(B.PAYMENT_MANNER) AS PAYMENT_MANNER " +
                "FROM REGT005 A JOIN REGT010 B ON A.AYEAR=B.AYEAR AND A.SMS = B.SMS AND A.STNO = B.STNO " +
                "AND A.PAYMENT_STATUS = '2' " +
                "WHERE 1 = 1 "
        );
            if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
                sql.append("AND B.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
            }
            if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
                sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
            }
            if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
                sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
            }
            if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
                sql.append("AND A.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
            }
        sql.append
        (
                "GROUP BY A.AYEAR,A.SMS,A.STNO " +
            ") A ON A.AYEAR = REGT005.AYEAR AND A.SMS = REGT005.SMS AND A.STNO = REGT005.STNO " +
            "LEFT JOIN SYST001 S5 ON S5.KIND='PAYMENT_MANNER' AND A.PAYMENT_MANNER = S5.CODE " +
            "WHERE  1    =    1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND STUT003.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND REGT005.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND REGT005.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")) {
            sql.append("AND STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND REGT005.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND STUT003.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND STUT003.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }

        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "REGT005." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            REGGETSTNOFEE sel = null;
            while (rs.next())
            {
                String PAYMENT_STATUS = "1";
                String PAID_AMT = "0";
                String PAYABLE_TOTAL_AMT = "0";
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if(rs.getColumnName(i).equals("PAID_AMT"))
                    {
                        if(!rs.getString(i).equals(""))
                            PAID_AMT = rs.getString(i);
                    }
                    if(rs.getColumnName(i).equals("TOTAL_CRD_CNT"))
                    {
                        if(rowHt.get("AYEAR").equals(nextayear) && rowHt.get("SMS").equals(nextsms))
                        {
                            System.out.println("無");
                        }
                        else
                        {
                            rowHt.put(rs.getColumnName(i), rs.getString(i));
                        }
                    }
                    else
                    {
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }
                //如果是選課學年期
                if(rowHt.get("AYEAR").equals(nextayear) && rowHt.get("SMS").equals(nextsms))
                {
                    sel = new REGGETSTNOFEE(dbmanager,conn,rowHt,"TEST");
                    if(sel.getSelStatus("QUR").equals("1"))
                    {
                        System.out.println("有選課");
                        System.out.println("回傳學號"+sel.getSTNO());
                        System.out.println("回傳減免名稱"+sel.getREDUCE_NAME());
                        System.out.println("回傳學生科目"+sel.getSTU_LIST_CRSNO());
                        System.out.println("回傳學生應繳總金額"+sel.getPAYABLE_TOTAL_AMT());
                        System.out.println("回傳學生學訊費"+sel.getMAG_FEE());
                        System.out.println("回傳學生學分費"+sel.getTOTAL_CRD_FEE());
                        System.out.println("回傳學生學雜費"+sel.getTOTAL_MISC_FEE());
                        System.out.println("回傳學生實習費"+sel.getTOTAL_LAB_FEE());
                        System.out.println("回傳學生減免總金額"+sel.getTOTAL_REDUCE_FEE());
                        rowHt.put("REDUCE_NAME",sel.getREDUCE_NAME());
                        rowHt.put("STU_LIST_CRSNO",sel.getSTU_LIST_CRSNO());
                        PAYABLE_TOTAL_AMT = sel.getPAYABLE_TOTAL_AMT();
                        rowHt.put("PAYABLE_TOTAL_AMT",PAYABLE_TOTAL_AMT);
                        rowHt.put("REDUCE_FEE",sel.getTOTAL_REDUCE_FEE());
                        rowHt.put("MAG_FEE1",sel.getMAG_FEE());
                        rowHt.put("CRD_FEE",sel.getTOTAL_CRD_FEE());
                        rowHt.put("MISC_FEE",sel.getTOTAL_MISC_FEE());
                        rowHt.put("LAB_FEE",sel.getTOTAL_LAB_FEE());
                        rowHt.put("STU_REFUND_AMT",sel.getSTU_REFUND_AMT());
                        rowHt.put("TOTAL_CRD_CNT",sel.getSTU_TOTAL_CRD());
                        //判斷是否繳錢
                        if(!PAID_AMT.equals("0"))
                        {
                            if(PAYABLE_TOTAL_AMT.equals(PAID_AMT)){
                                PAYMENT_STATUS="2";
                            }else if(Integer.parseInt(PAID_AMT)>Integer.parseInt(PAYABLE_TOTAL_AMT)){
                                PAYMENT_STATUS="4";
                            }else if(Integer.parseInt(PAID_AMT)<Integer.parseInt(PAYABLE_TOTAL_AMT)){
                                PAYMENT_STATUS="3";
                            }
                        }
                    }
                    else
                    {
                        System.out.println("沒有選課");
                        System.out.println(sel.getMessage());
                        rowHt.put("REDUCE_NAME","");
                        rowHt.put("STU_LIST_CRSNO","");
                        rowHt.put("PAYABLE_TOTAL_AMT","0");
                        rowHt.put("REDUCE_FEE","0");
                        rowHt.put("MAG_FEE1","0");
                        rowHt.put("CRD_FEE","0");
                        rowHt.put("MISC_FEE","0");
                        rowHt.put("LAB_FEE","0");
                        rowHt.put("STU_REFUND_AMT","0");
                        rowHt.put("TOTAL_CRD_CNT","0");
                    }
                    rowHt.put("PAYMENT_STATUS",PAYMENT_STATUS);
                    rowHt.put("PAYMENT_STATUS1",((Hashtable)Syst001(dbmanager,conn,"PAYMENT_STATUS",PAYMENT_STATUS).get(0)).get("CODE_NAME").toString());
                    result.add(rowHt);
                }
                else
                {
                    Vector Rvt = new Vector();
                    PCST004GATEWAY  pcst004gateway  =    new PCST004GATEWAY(dbmanager, conn);
                    rowHt.put("PAYMENT_STATUS","2");
                    rowHt.put("ABNDN_ORDER","N");
                    Rvt =  pcst004gateway.getReg005Regt010Regt011Query(rowHt);
                    for(int i=0;i<Rvt.size();i++)
                    {
                        rowHt.put("REDUCE_NAME",((Hashtable)Rvt.get(i)).get("REDUCE_NAME").toString());
                        rowHt.put("STU_LIST_CRSNO",((Hashtable)Rvt.get(i)).get("CRS_NAME").toString());
                        rowHt.put("PAYABLE_TOTAL_AMT",((Hashtable)Rvt.get(i)).get("PAYABLE_TOTAL_AMT").toString());
                        rowHt.put("REDUCE_FEE",((Hashtable)Rvt.get(i)).get("REDUCE_FEE").toString());
                        rowHt.put("MAG_FEE1",((Hashtable)Rvt.get(i)).get("MAG_FEE").toString());
                        rowHt.put("CRD_FEE",((Hashtable)Rvt.get(i)).get("CRD_FEE").toString());
                        rowHt.put("MISC_FEE",((Hashtable)Rvt.get(i)).get("MISC_FEE").toString());
                        rowHt.put("LAB_FEE",((Hashtable)Rvt.get(i)).get("LAB_FEE").toString());
                        rowHt.put("STU_REFUND_AMT",((Hashtable)Rvt.get(i)).get("REFUND_AMT").toString());
                        rowHt.put("PAYMENT_STATUS",((Hashtable)Rvt.get(i)).get("PAYMENT_STATUS").toString());
                        rowHt.put("PAYMENT_STATUS1",((Hashtable)Rvt.get(i)).get("CPAYMENT_STATUS").toString());
                    }
                    if(Rvt.size()<=0)
                    {
                        rowHt.put("REDUCE_NAME","");
                        rowHt.put("STU_LIST_CRSNO","");
                        rowHt.put("PAYABLE_TOTAL_AMT","0");
                        rowHt.put("REDUCE_FEE","0");
                        rowHt.put("MAG_FEE1","0");
                        rowHt.put("CRD_FEE","0");
                        rowHt.put("MISC_FEE","0");
                        rowHt.put("LAB_FEE","0");
                        rowHt.put("STU_REFUND_AMT","0");
                        rowHt.put("PAYMENT_STATUS","1");
                        rowHt.put("PAYMENT_STATUS1",((Hashtable)Syst001(dbmanager,conn,"PAYMENT_STATUS","1").get(0)).get("CODE_NAME").toString());
                    }
                    result.add(rowHt);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if(rs != null)
            {
                rs.close();
            }
        }
        return result;
    }

    /**
     * 修改上面那個鬼method:getRegt005Stut002Stut003ForUse(Hashtable ht)
     * 因憑著GRID的欄位自己組SQL+上面那個method的部份寫入,原則上應該不會差太多
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getDataForReg007m(Hashtable ht) throws Exception {
        Vector result = new Vector();

        // 取得下一個學年期-->用來判斷是否為查詢下次選課
        String nextAyear = "";
        String nextSms = "";
        java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat smipleDate = new SimpleDateFormat("yyyyMMdd");
        String nowDate = smipleDate.format(cal.getTime());

        SYSGETSMSDATA sys = new SYSGETSMSDATA(dbmanager);
        sys.setCONN_TYPE("1");  // 不關閉dbmanager
        sys.setSYS_DATE(nowDate);
        sys.setSMS_TYPE("3");// 1.當期 2.前期 3.後期 4.前學年 5.後學年

        int executeResult = sys.execute();
        if(executeResult==1){
        	nextAyear = sys.getAYEAR();
        	nextSms = sys.getSMS();
        }

        if(sql.length() > 0)
            sql.delete(0, sql.length());

        // 目的:每個學生僅取得最後一張銷帳編號的那筆資料
        // 欄位規則:實收金額-->所有當學年期該學生每筆銷帳編號之實收金額總和    應付金額:尚未註銷之所有銷帳編號的應付金額總和(ABNDN_ORDER<>'Y')
        // 正常情況:這個SQL應該每學年期每個學生僅會有一筆資料,除非幾種情況除外,在ABNDN_ORDER<>'Y'下,有幾種狀況會產生多筆資料
        //        1.異動帳號不同 2.異動日期不同3.有產生補繳單?(不清楚...反正就產生什麼單的時候就會有兩筆)

        sql.append(
          "SELECT A.AYEAR,A.SMS,A.STNO,A.ASYS,A.WRITEOFF_NO,A.PAYMENT_MANNER,NVL(K.CENTER_CODE,B.CENTER_CODE) AS CENTER_CODE,B.IDNO,B.BIRTHDATE,C.NAME," +
        	"SUM(NVL(A.CRD_FEE,'0')) AS CRD_FEE,SUM(NVL(A.MISC_FEE,'0')) AS MISC_FEE ,SUM(NVL(A.MAG_FEE,'0')) AS MAG_FEE1," +
        	"SUM(NVL(A.LAB_FEE,'0')) AS LAB_FEE,NVL(SUM(E.REFUND_AMT),'0') AS STU_REFUND_AMT," +
        	"SUM(A.PAYABLE_TOTAL_AMT) AS PAYABLE_TOTAL_AMT,H.PAID_AMT,I.CODE_NAME AS SMS_NAME,A.UPD_USER_ID,A.UPD_DATE, " +
        	"D.CENTER_ABBRNAME,J.CODE_NAME AS CPAYMENT_MANNER, A.UPD_TIME,A.WRITEOFF_NO||LPAD(A.PAYABLE_TOTAL_AMT,6,'0') AS BANK_CODE,  "+
			"(CASE WHEN B.ENROLL_AYEARSMS=A.AYEAR||A.SMS OR B.FTSTUD_ENROLL_AYEARSMS=A.AYEAR||A.SMS THEN 'Y' ELSE 'N' END) AS ISNEW, " +
			"A.OVER_TAKE_FEE "+
          "FROM REGT010 A " + 
		  "JOIN STUT003 B ON A.STNO = B.STNO " +
		  "JOIN STUT002 C ON B.IDNO = C.IDNO AND B.BIRTHDATE = C.BIRTHDATE " +   
		  "LEFT JOIN STUT004 K ON K.AYEAR = A.AYEAR AND K.SMS = A.SMS AND K.STNO = A.STNO " +
		  "JOIN SYST002 D ON D.CENTER_CODE = NVL(K.CENTER_CODE, B.CENTER_CODE) " +
		  "LEFT JOIN PCST012 R4 ON R4.AYEAR = A.AYEAR AND R4.SMS = A.SMS AND R4.STNO = A.STNO AND R4.BUSS_RESULT IN ('0000','000') " +
		  "LEFT JOIN REGT009 E ON A.AYEAR = E.AYEAR AND A.SMS = E.SMS AND A.WRITEOFF_NO = E.WRITEOFF_NO " +
		  "JOIN SYST001 I ON A.SMS = I.CODE AND I.KIND = 'SMS' " +
		  "LEFT JOIN SYST001 J ON A.PAYMENT_MANNER = J.CODE AND J.KIND = 'PAYMENT_MANNER' " +
		  "JOIN " +
		  "(SELECT SUM(G.PAID_AMT) AS PAID_AMT,G.AYEAR,G.SMS,G.STNO,G.OVER_TAKE_MK FROM REGT010 G GROUP BY G.AYEAR,G.SMS,G.STNO,G.OVER_TAKE_MK) H " +
		  " ON A.AYEAR = H.AYEAR AND A.SMS = H.SMS AND A.STNO = H.STNO AND A.OVER_TAKE_MK = H.OVER_TAKE_MK "+
          "WHERE (A.ABNDN_ORDER<>'Y' OR A.ABNDN_ORDER IS NULL) "               
        );

        if(!Utility.nullToSpace(ht.get("ASYS")).equals(""))
        	sql.append("AND A.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        	sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        if(!Utility.nullToSpace(ht.get("SMS")).equals(""))
        	sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        if(!Utility.nullToSpace(ht.get("STNO")).equals(""))
        	sql.append("AND A.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals(""))
        	sql.append("AND K.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
        if(!Utility.nullToSpace(ht.get("IDNO")).equals(""))
        	sql.append("AND B.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals(""))
        	sql.append("AND B.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");

        sql.append(
        	"GROUP BY "+
				"(CASE WHEN B.ENROLL_AYEARSMS=A.AYEAR||A.SMS OR B.FTSTUD_ENROLL_AYEARSMS=A.AYEAR||A.SMS THEN 'Y' ELSE 'N' END),  "+
        		"A.AYEAR,A.SMS,A.STNO,D.CENTER_ABBRNAME,H.PAID_AMT,I.CODE_NAME,A.UPD_USER_ID,A.UPD_DATE,"+
        		"NVL(K.CENTER_CODE,B.CENTER_CODE),A.ASYS,B.BIRTHDATE,B.IDNO,A.WRITEOFF_NO,A.PAYMENT_MANNER,J.CODE_NAME,C.NAME, "+
        		"A.UPD_DATE,A.UPD_TIME,A.WRITEOFF_NO||LPAD(A.PAYABLE_TOTAL_AMT,6,'0'), "+
        		"A.OVER_TAKE_FEE " +
        	"ORDER BY A.AYEAR,A.SMS,A.STNO,A.UPD_DATE DESC,A.UPD_TIME DESC " // 2008.08.10 新增UPD_DATE,UPD_TIME
        );

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable rowHt = null;
            while (rs.next()){
            	rowHt = new Hashtable();

            	/** 將欄位抄一份過去 */
            	for (int i = 1; i <= rs.getColumnCount(); i++)
            		rowHt.put(rs.getColumnName(i), rs.getString(i));

                // 判斷繳費狀態
            	String PAYMENT_STATUS = ""; // 繳費狀態:1:未繳2:已繳3:短繳4:溢繳
            	String PAYMENT_STATUS1 = "";
                int PAID_AMT = rs.getInt("PAID_AMT"); // 實收金額
                int PAYABLE_TOTAL_AMT = rs.getInt("PAYABLE_TOTAL_AMT"); // 應付金額
                if(PAID_AMT==0){
                	PAYMENT_STATUS = "1";
                	PAYMENT_STATUS1 = "未繳";
                }else if(PAID_AMT==PAYABLE_TOTAL_AMT){
                	PAYMENT_STATUS = "2";
                	PAYMENT_STATUS1 = "已繳";
                }else if(PAID_AMT<PAYABLE_TOTAL_AMT){
                	PAYMENT_STATUS = "3";
                	PAYMENT_STATUS1 = "短繳";
                }else if(PAID_AMT>PAYABLE_TOTAL_AMT){
                	PAYMENT_STATUS = "4";
                	PAYMENT_STATUS1 = "溢繳";
                }

				DateUtil du = new DateUtil();
				String nowdate = du.getNowDate();
				String isnew = rs.getString("ISNEW");
				String asys = rs.getString("ASYS");
				String ayear = rs.getString("AYEAR");
				String sms = rs.getString("SMS");

				if( "3".equals(Utility.nullToSpace(ht.get("ID_TYPE"))) )
					rowHt.put("SHOW_W", "Y");
				else if( "1".equals(Utility.nullToSpace(ht.get("ID_TYPE"))) )
				{
					String chksql = "select case when '"+nowdate+"' > (case when '"+isnew+"'='Y' then NEW_SDATE_ATM else OLD_SDATE_ATM end) then 'Y' else 'N' end as result from regt001 where ayear='"+ayear+"' and sms='"+sms+"' and asys='"+asys+"' ";
					DBResult rs2 = dbmanager.getSimpleResultSet(conn);
                    rs2.open();
                    rs2.executeQuery(chksql);
					if(rs2.next())
						rowHt.put("SHOW_W", rs2.getString("RESULT"));
					else
						rowHt.put("SHOW_W", "N");
					rs2.close();
				}
				else
					rowHt.put("SHOW_W", "N");


                // 判斷是否查詢選課學年期(即下一個學期)
                //if(rowHt.get("AYEAR").equals(nextAyear) && rowHt.get("SMS").equals(nextSms)){
                if(true){
                	// 取得選課資料
                	Hashtable content = new Hashtable();
                	content.put("AYEAR",rowHt.get("AYEAR").toString());
                	content.put("SMS",rowHt.get("SMS").toString());
                	content.put("STNO",rowHt.get("STNO").toString());
                	content.put("USER_ID" ,"TEST");

                	System.out.println("學年:"+rowHt.get("AYEAR").toString());
                	System.out.println("學期:"+rowHt.get("SMS").toString());
                	System.out.println("學號:"+rowHt.get("STNO").toString());

                	REGGETSTNOFEE sel = new REGGETSTNOFEE(dbmanager,conn,content,"test");

                    // 請注意!該副程式取得科目列表的方式和第二頁不同,導致第一頁和第二頁不一致--->原則上上面有資料應該表示有選課資料
                    // 因此這裡直接到regt007 join cout001 cout002取得科目名稱
                    // 但有關減免費用的部份目前尚未去求證副程式是否有問題,尚須求證
                    String STU_LIST_CRSNO = ""; // 所選科目列表
                    int totalCrd = 0; // 計算選課學分數

                    String getCrsnoSql =
                    	"SELECT C.CRS_NAME,B.CRD,"+
                        "REGT005.TAKE_ABNDN AS TAKE_ABNDN  "+
                    	"FROM REGT005 REGT005 " +
                        "JOIN REGT007 A ON A.AYEAR=REGT005.AYEAR AND A.SMS=REGT005.SMS AND A.STNO=REGT005.STNO "+
                        "JOIN COUT001 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.CRSNO=B.CRSNO "+
                        "JOIN COUT002 C ON A.CRSNO=C.CRSNO "+
                    	"WHERE 1=1 "+
                		"AND REGT005.AYEAR='"+rs.getString("AYEAR")+"' "+
                		"AND REGT005.SMS='"+rs.getString("SMS")+"' "+
                		"AND REGT005.STNO='"+rs.getString("STNO")+"' "+
                        "AND A.UNTAKECRS_MK ='N' "+
                        "AND A.UNQUAL_TAKE_MK = 'N' ";

                    DBResult rs2 = dbmanager.getSimpleResultSet(conn);
                    rs2.open();
                    rs2.executeQuery(getCrsnoSql);
                    String TAKE_ABNDN ="";
                    while(rs2.next()){
                    	if(!STU_LIST_CRSNO.equals(""))
                    		STU_LIST_CRSNO+="、";
                    	STU_LIST_CRSNO+=rs2.getString("CRS_NAME");
                    	totalCrd += rs2.getInt("CRD");
                    	//by poto
                    	TAKE_ABNDN =   rs2.getString("TAKE_ABNDN");
                    }
                    rs2.close();

                    sel.getSelStatus("QUR"); // 副程式的execute
                    rowHt.put("REDUCE_NAME",sel.getREDUCE_NAME()); // 減免名稱
                    rowHt.put("STU_LIST_CRSNO",STU_LIST_CRSNO); // 所選科目列表
                    rowHt.put("REDUCE_FEE",sel.getTOTAL_REDUCE_FEE()); // 減免學分費總額
                    rowHt.put("TOTAL_CRD_CNT", totalCrd+""); // 選課學分數
                    rowHt.put("PAYMENT_STATUS",PAYMENT_STATUS); // 繳費狀態代碼
                    rowHt.put("PAYMENT_STATUS1",PAYMENT_STATUS1); // 繳費狀態名稱
                    rowHt.put("TAKE_ABNDN",TAKE_ABNDN); //regt005退選註記
                    result.add(rowHt);
                }
                // 不是查詢選課學年期(亦即不是下個學期)---下面沿用舊method不修改了.....沒有去研究裡頭的邏輯處理方式....等==>因為竟然還有一個method...
                else
                {
                    Vector Rvt = new Vector();
                    PCST004GATEWAY  pcst004gateway = new PCST004GATEWAY(dbmanager, conn);
                    rowHt.put("PAYMENT_STATUS","2");
                    rowHt.put("ABNDN_ORDER","N");
                    Rvt =  pcst004gateway.getReg005Regt010Regt011Query(rowHt);

                    for(int i=0;i<Rvt.size();i++)
                    {
                        rowHt.put("REDUCE_NAME",((Hashtable)Rvt.get(i)).get("REDUCE_NAME").toString());
                        rowHt.put("STU_LIST_CRSNO",((Hashtable)Rvt.get(i)).get("CRS_NAME").toString());
                        rowHt.put("REDUCE_FEE",((Hashtable)Rvt.get(i)).get("REDUCE_FEE").toString());
                        rowHt.put("PAYMENT_STATUS",((Hashtable)Rvt.get(i)).get("PAYMENT_STATUS").toString());
                        rowHt.put("PAYMENT_STATUS1",((Hashtable)Rvt.get(i)).get("CPAYMENT_STATUS").toString());
                        rowHt.put("TOTAL_CRD_CNT", ""); // 選課學分數
                    }
                    if(Rvt.size()==0)
                    {
                        rowHt.put("REDUCE_NAME","");
                        rowHt.put("STU_LIST_CRSNO","");
                        rowHt.put("REDUCE_FEE","0");
                        rowHt.put("PAYMENT_STATUS","1");
                        rowHt.put("PAYMENT_STATUS1",((Hashtable)Syst001(dbmanager,conn,"PAYMENT_STATUS","1").get(0)).get("CODE_NAME").toString());
                        rowHt.put("TOTAL_CRD_CNT", ""); // 選課學分數
                    }
                    result.add(rowHt);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if(rs != null)
            {
                rs.close();
            }
        }
        return result;
    }


    /**
     * REG007M 判斷如果非目前註冊選課學年期之後就抓歷程資料
     */
    private Vector getRegt005Regt007Regt012Regt010ForUse(DBManager dbmanager,Connection conn,Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

        sql.append
        (
            "SELECT A.AYEAR,A.SMS,A.STNO,E.REDUCE_NAME,B.CRSNO,B.CRS_NAME,D.PAYABLE_TOTAL_AMT,D.REDUCE_FEE, " +
            "D.PAID_AMT,D.LAB_FEE,D.MAG_FEE,D.CRD_FEE,D.MISC_FEE,D.PAYMENT_STATUS,S1.CODE_NAME AS PAYMENT_STATUS1 " +
            "FROM REGT005 A JOIN " +
            "( " +
            "SELECT A.AYEAR,A.SMS,A.STNO,A.CRSNO,C.CRS_NAME FROM REGT007 A JOIN COUT001 B ON A.AYEAR = B.AYEAR AND A.SMS = B.SMS AND A.CRSNO = B.CRSNO " +
            "JOIN COUT002 C ON B.CRSNO = C.CRSNO " +
            "LEFT JOIN REGT012 D ON A.AYEAR = D.AYEAR AND A.SMS = D.SMS AND A.STNO = D.STNO " +
            ")B " +
            "ON A.AYEAR = B.AYEAR AND A.SMS = B.SMS AND A.STNO = B.STNO "
        );
            if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
                sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
            }
            if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
                sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
            }
            if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
                sql.append("AND A.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
            }
        sql.append
        (
            "LEFT JOIN (SELECT A.AYEAR,A.SMS,A.STNO,B.CODE_NAME AS REDUCE_NAME FROM REGT004 A JOIN SYST001 B ON B.KIND = 'REDUCE_TYPE' AND A.REDUCE_TYPE = B.CODE) E " +
            "ON A.AYEAR = E.AYEAR AND A.SMS = E.SMS AND A.STNO = E.STNO " +
            "JOIN REGT010 D ON A.AYEAR = D.AYEAR AND A.SMS = D.SMS AND A.STNO = D.STNO AND D.ABNDN_ORDER = 'N' AND D.PAYMENT_STATUS = '2' " +
            "JOIN SYST001 S1 ON S1.KIND = 'PAYMENT_STATUS' AND S1.CODE = D.PAYMENT_STATUS " +
            "WHERE 1 = 1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND A.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }

        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "A." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = new Hashtable();
            int size = 0;
            String CRS_NAME = "";
            while (rs.next()) {
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if(size==0){
                        if(rs.getColumnName(i).equals("CRS_NAME")){
                            CRS_NAME = rs.getString(i);
                        }else{
                            rowHt.put(rs.getColumnName(i), rs.getString(i));
                        }
                    }else{
                        CRS_NAME = "," + rs.getString(i);
                    }
                }
                size ++;
            }
            rowHt.put("CRS_NAME",CRS_NAME);
            result.add(rowHt);
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    /**
     * JOIN 其它 Regt005,Regt007 將學生選課資料帶出來
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getRegt005Regt007ForUse(Hashtable ht) throws Exception {

        String PAID_AMT ="0";           //學生的實繳金額
        String REGT005_PAYMENT_STATUS = "1" ;
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append
        (
            "SELECT B.AYEAR,B.SMS,B.STNO,SUM(NVL(B.PAID_AMT,'0')) AS PAID_AMT,SUM(NVL(C.REFUND_AMT,'0')) AS REFUND_AMT,A.PAYMENT_STATUS FROM REGT005 A " +
            "JOIN REGT010 B ON A.AYEAR = B.AYEAR AND A.SMS = B.SMS AND A.STNO = B.STNO " +
            "AND B.PAYMENT_STATUS NOT IN('1') "
        );
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND B.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND B.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND B.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        sql.append
        (
            "LEFT JOIN REGT009 C ON B.AYEAR = C.AYEAR AND B.SMS = C.SMS AND B.STNO = C.STNO AND B.WRITEOFF_NO = C.WRITEOFF_NO " +
            "AND C.REFUND_PROCESS_STATUS = '2' " +
            "WHERE 1  =  1 " +
            //by poto 已繳費問題  2009/02/03
            //"GROUP BY B.AYEAR,B.SMS,B/STNO,B.PAID_AMT,C.REFUND_AMT,A.PAYMENT_STATUS "
            "GROUP BY B.AYEAR,B.SMS,B.STNO,A.PAYMENT_STATUS "
        );
        DBResult rsEOFF = null;
        try {
            // 取出所有資料
            rsEOFF = dbmanager.getSimpleResultSet(conn);
            rsEOFF.open();
            rsEOFF.executeQuery(sql.toString());
            if(rsEOFF.next()){
                //by poto
                //2008/10/21 實繳 = 實繳－退費金額
                PAID_AMT = String.valueOf(Integer.parseInt(rsEOFF.getString("PAID_AMT")) - Integer.parseInt(rsEOFF.getString("REFUND_AMT")));
                REGT005_PAYMENT_STATUS =  rsEOFF.getString("PAYMENT_STATUS");
            }
        } catch (Exception e) {
            throw e;
        }


        String TAKE_ITEM_MK ="0";           //是否寄發學生選課註意事項
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT STNO FROM REGT006 WHERE STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        DBResult rsSTNO = null;
        try {
            // 取出所有資料
            rsSTNO = dbmanager.getSimpleResultSet(conn);
            rsSTNO.open();
            rsSTNO.executeQuery(sql.toString());
            if(rsSTNO.next()){
                TAKE_ITEM_MK="1";
            }
            else{
                TAKE_ITEM_MK="0";
            }
        } catch (Exception e) {
            throw e;
        }

        String WRITEOFF_NO ="";           //最近的銷帳編號
        String PAYMENT_STATUS = "1";
        String PCST003_WRITEOFF_NO ="";  //檢查在PCST003 有沒有這個銷帳編號
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append
        (
            "SELECT  "+
            "REGT010.STNO,REGT010.WRITEOFF_NO, "+
            "NVL(PCST003.WRITEOFF_NO,'0') AS PCST003_WRITEOFF_NO, "+
            "REGT010.PAYABLE_TOTAL_AMT, "+
            "MAX(REGT010.PAYMENT_STATUS) AS PAYMENT_STATUS, "+
            "MAX(REGT010.UPD_DATE||REGT010.UPD_TIME||REGT010.ROWSTAMP) AS D  "+
            "FROM REGT010  "+
            "LEFT JOIN PCST003 ON REGT010.WRITEOFF_NO = PCST003.WRITEOFF_NO "+
            "WHERE 1 = 1 "
        );
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND REGT010.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND REGT010.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND REGT010.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        sql.append
        (
            "GROUP BY REGT010.STNO,REGT010.WRITEOFF_NO,PCST003.WRITEOFF_NO,REGT010.PAYABLE_TOTAL_AMT "+
            "ORDER BY D DESC  "
        );
        DBResult rsWRITEOFF_NO = null;
        try {
            // 取出所有資料
            rsWRITEOFF_NO = dbmanager.getSimpleResultSet(conn);
            rsWRITEOFF_NO.open();
            rsWRITEOFF_NO.executeQuery(sql.toString());
            int size = 0;
            if(rsWRITEOFF_NO.next()){
                if(size==0){
                    WRITEOFF_NO = rsWRITEOFF_NO.getString("WRITEOFF_NO");
                    PAYMENT_STATUS = rsWRITEOFF_NO.getString("PAYMENT_STATUS");
                    PCST003_WRITEOFF_NO = rsWRITEOFF_NO.getString("PCST003_WRITEOFF_NO");
                }
            }
            else{
                WRITEOFF_NO="";
            }
        } catch (Exception e) {
            throw e;
        }

        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append
        (
            "SELECT "+
            "REGT005.STNO,REGT007.CRSNO,REGT007.TUT_CMPS_CODE,REGT007.LAB_CMPS_CODE,REGT007.UNTAKECRS_MK,REGT007.MASTER_CLASS_CODE, " +
            "REGT005.MAG_ORDER_MK,REGT007.VOLNUTEER_TIME01, REGT007.VOLNUTEER_TIME02,REGT007.VOLNUTEER_TIME03,REGT007.VOLNUTEER_TIME04, " +
            "REGT007.VOLNUTEER_TIME05,REGT007.VOLNUTEER_TIME06,REGT007.VOLNUTEER_TIME07,REGT007.VOLNUTEER_TIME08,REGT007.VOLNUTEER_TIME09,REGT007.VOLNUTEER_TIME10, " +
            "CASE WHEN B.LAB_TIMES >0 AND B.TUT_TIMES>0 THEN '3' " +
            "WHEN B.LAB_TIMES =0 AND B.TUT_TIMES>0 THEN '1' " +
            "WHEN B.LAB_TIMES >0 AND B.TUT_TIMES=0 THEN '2' " +
            "ELSE '4' END AS CRSNO_STATUS " +
            "FROM REGT005 "+
            "LEFT JOIN REGT007 ON 1=1 "+
            "AND REGT007.AYEAR = REGT005.AYEAR AND REGT007.SMS = REGT005.SMS AND REGT007.STNO = REGT005.STNO " +
            "AND REGT007.UNQUAL_TAKE_MK='N' AND  REGT007.UNTAKECRS_MK='N' AND TRIM(REGT007.S_CLASS_TYPE) IS NULL "+
            "LEFT JOIN COUT001 B ON REGT007.AYEAR=B.AYEAR AND REGT007.SMS =B.SMS AND REGT007.CRSNO=B.CRSNO " +
            "WHERE 1  =   1  AND REGT005.TAKE_ABNDN = 'N' "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND REGT005.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND REGT005.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND REGT005.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "REGT007." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            String TUT_CMPS_CODE = "";
            String LAB_CMPS_CODE = "";
            String CRSNO_STATUS = "";
            while (rs.next()) {
                rowHt = new Hashtable();
                TUT_CMPS_CODE = "";
                LAB_CMPS_CODE = "";
                CRSNO_STATUS = "";
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if(rs.getColumnName(i).equals("TUT_CMPS_CODE")){
                        TUT_CMPS_CODE = rs.getString(i);
                    }else if(rs.getColumnName(i).equals("LAB_CMPS_CODE")){
                        LAB_CMPS_CODE = rs.getString(i);
                    }else if(rs.getColumnName(i).equals("CRSNO_STATUS")){
                        CRSNO_STATUS = rs.getString(i);
                    }else{
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }
                //by poto 因為暑期 有可能cout001 tut_times and lab_times都是 0 會造成TUT_CLASS_CODE 代不出來
                //所以 當SMS = 3  不要用 這個判斷(CRSNO_STATUS) 直接就抓 面授校區
                if("3".equals(Utility.nullToSpace(ht.get("SMS")))){
                    rowHt.put("TUT_CMPS_CODE", TUT_CMPS_CODE);
                    rowHt.put("LAB_CMPS_CODE", "");
                }else{
                    if(CRSNO_STATUS.equals("1")){
                        rowHt.put("TUT_CMPS_CODE", TUT_CMPS_CODE);
                        rowHt.put("LAB_CMPS_CODE", "");
                    }else if(CRSNO_STATUS.equals("2")){
                        rowHt.put("TUT_CMPS_CODE", "");
                        rowHt.put("LAB_CMPS_CODE", LAB_CMPS_CODE);
                    }else if(CRSNO_STATUS.equals("3")){
                        rowHt.put("TUT_CMPS_CODE", TUT_CMPS_CODE);
                        rowHt.put("LAB_CMPS_CODE", LAB_CMPS_CODE);
                    }else if(CRSNO_STATUS.equals("4")){
                        rowHt.put("TUT_CMPS_CODE", "");
                        rowHt.put("LAB_CMPS_CODE", "");
                    }
                }

                rowHt.put("REGT005_PAYMENT_STATUS",REGT005_PAYMENT_STATUS);
                rowHt.put("PCST003_WRITEOFF_NO",PCST003_WRITEOFF_NO);
                rowHt.put("WRITEOFF_NO",WRITEOFF_NO);
                rowHt.put("TAKE_ITEM_MK",TAKE_ITEM_MK);
                rowHt.put("PAYMENT_STATUS",PAYMENT_STATUS);
                rowHt.put("PAID_AMT",PAID_AMT);
                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    /**
     * JOIN 其它 Regt005,Stut002,Stut003,Syst003,Syst001 將欄位的中文化
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getRegt005Stut002Stut003DataForUse(Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

        sql.append
        (
            "SELECT DISTINCT STUT003.ASYS ,SYS_ASYS.CODE_NAME AS CASYS ,REGT005.AYEAR ,REGT005.SMS ,SYS_SMS.CODE_NAME AS SMS_NAME ,STUT002.NAME, " +
            "STUT003.IDNO ,STUT003.STNO ,STUT003.BIRTHDATE ,STUT003.CENTER_CODE ,SYST002.CENTER_NAME, " +
            "stut003.PRE_MAJOR_FACULTY,stut003.STTYPE,STUT003.SPECIAL_STTYPE_TYPE,(stut002.AREACODE_HOME||'-'||stut002.TEL_HOME) AS AREACODE_HOME, " +
            "SYST008.TOTAL_CRS_NAME,S2.CODE_NAME AS CREDUCE_TYPE,SYST001.CODE_NAME AS CSTTYPE, REGT005.STNO,STUT002.CRRSADDR, STUT002.MOBILE, " +
            "DECODE(STUT003.SPECIAL_STTYPE_TYPE,'1',SUBSTR(S3.CODE_NAME, 0, 1),'0','',S3.CODE_NAME) || DECODE(STUT002.RESIDENCE_DATE,'','　','居留生請確認居留證有效日期：' || substr(STUT002.RESIDENCE_DATE,1,4)||'年'||substr(STUT002.RESIDENCE_DATE,5,2)||'月'||substr(STUT002.RESIDENCE_DATE,7,2)||'日') AS SPECIAL_NAME " +
            "FROM  REGT005 JOIN STUT003 ON REGT005.STNO = STUT003.STNO " +
            "JOIN STUT002 ON STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE " +
            "JOIN SYST002 ON SYST002.CENTER_CODE = STUT003.CENTER_CODE " +
            "JOIN SYST001 SYS_SMS ON SYS_SMS.KIND='SMS' AND REGT005.SMS  = SYS_SMS.CODE " +
            "JOIN SYST001 SYS_ASYS ON SYS_ASYS.KIND='ASYS' AND STUT003.ASYS  = SYS_ASYS.CODE "+
            "LEFT JOIN SYST001 S3 ON S3.KIND='SPECIAL_STTYPE_MK' AND S3.CODE = STUT003.SPECIAL_STTYPE_TYPE "
        );

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("2"))
        {
        	sql.append
        	(
        			"LEFT JOIN SYST008  ON STUT003.ASYS = SYST008.ASYS AND STUT003.J_FACULTY_CODE = SYST008.FACULTY_CODE "
        	);
        }
        else
        {
        	sql.append
        	(
        		"LEFT JOIN SYST008 ON STUT003.ASYS=SYST008.ASYS AND STUT003.PRE_MAJOR_FACULTY=SYST008.FACULTY_CODE AND (STUT003.J_FACULTY_CODE = SYST008.TOTAL_CRS_NO OR (STUT003.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' AND NVL(STUT003.J_FACULTY_CODE,'X') ='X')) "
        	);
        }


        sql.append
        (
            "JOIN SYST001 ON SYST001.KIND='STTYPE' AND SYST001.CODE = STUT003.STTYPE " +
            "LEFT JOIN REGT004 ON REGT004.STNO = STUT003.STNO " +
            "LEFT JOIN SYST001 S2 ON S2.KIND='REDUCE_TYPE' AND S2.CODE = STUT003.REDUCE_TYPE " +
            "WHERE       1    =    1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND REGT005.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND REGT005.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND STUT003.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND REGT005.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND STUT003.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND STUT003.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }

        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "REGT005." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){
                    if (rs.getColumnName(i).equals("AREACODE_HOME")){
                        if(rs.getString(i).charAt(0)=='-'){
                            rowHt.put(rs.getColumnName(i), rs.getString(i).substring(1,rs.getString(i).length()));
                        }else{
                            rowHt.put(rs.getColumnName(i),rs.getString(i));
                        }
                    }else{
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    /**
     * JOIN 其它 Regt005,Stut002,Stut003,Syst003,Syst001 將欄位的中文化
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getStut002Stut003ForUse(Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

        sql.append
        (
            "SELECT DISTINCT STUT003.ASYS , STUT002.NAME, STUT003.IDNO ,STUT003.STNO ,STUT003.BIRTHDATE ,STUT003.CENTER_CODE ,SYST002.CENTER_NAME, " +
            "STUT003.PRE_MAJOR_FACULTY,STUT003.STTYPE,STUT003.REDUCE_TYPE, (STUT002.AREACODE_HOME||'-'||STUT002.TEL_HOME) AS AREACODE_HOME, " +
            "STUT002.EMAIL,STUT002.CRRSADDR,SYST008.TOTAL_CRS_NAME,S2.CODE_NAME AS CREDUCE_TYPE,SYST001.CODE_NAME AS CSTTYPE, " +
            "S3.CODE_NAME AS CASYS, STUT002.MOBILE,NVL(STUT003.SPECIAL_STTYPE_TYPE,'0') AS SPECIAL_STTYPE_TYPE, " +
            "DECODE(STUT003.SPECIAL_STTYPE_TYPE,'1',SUBSTR(S33.CODE_NAME,0,1),'0','',S33.CODE_NAME)||DECODE(STUT002.RESIDENCE_DATE,'','　','居留生請確認居留證有效日期：'||substr(STUT002.RESIDENCE_DATE,1,4)||'年'||substr(STUT002.RESIDENCE_DATE,5,2)||'月'||substr(STUT002.RESIDENCE_DATE,7,2)||'日') AS SPECIAL_NAME " +
            "FROM  STUT003 JOIN STUT002 ON STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE " +
            "JOIN SYST002 ON SYST002.CENTER_CODE = STUT003.CENTER_CODE " +
            "JOIN SYST001 ON SYST001.KIND='STTYPE' AND SYST001.CODE = STUT003.STTYPE " +
            "LEFT JOIN SYST001 S33 ON S33.KIND='SPECIAL_STTYPE_MK' AND S33.CODE = STUT003.SPECIAL_STTYPE_TYPE "+
            "JOIN SYST001 S3 ON S3.KIND='ASYS' AND S3.CODE = STUT003.ASYS "
        );

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("2"))
        {
        	sql.append
        	(
        			//"LEFT JOIN SYST008  ON STUT003.ASYS = SYST008.ASYS AND STUT003.J_FACULTY_CODE = SYST008.FACULTY_CODE "
        			"LEFT JOIN SYST008  ON STUT003.ASYS = SYST008.ASYS AND stut003.PRE_MAJOR_FACULTY = syst008.faculty_code and J_FACULTY_CODE = TOTAL_CRS_NO "
        	);
        }
        else
        {
        	sql.append
        	(
        		"LEFT JOIN SYST008 ON STUT003.ASYS=SYST008.ASYS AND STUT003.PRE_MAJOR_FACULTY=SYST008.FACULTY_CODE AND (STUT003.J_FACULTY_CODE = SYST008.TOTAL_CRS_NO OR (STUT003.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' AND NVL(STUT003.J_FACULTY_CODE,'X') ='X')) "
        	);
        }

        sql.append
        (
            "LEFT JOIN REGT004 ON REGT004.STNO = STUT003.STNO " +
            "LEFT JOIN SYST001 S2 ON S2.KIND='REDUCE_TYPE' AND S2.CODE = STUT003.REDUCE_TYPE " +
            "WHERE 1  =  1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND STUT003.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND STUT003.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND STUT003.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND STUT003.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")) {
            sql.append("AND STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("NAME")).equals("")) {
            sql.append("AND STUT002.NAME = '" + Utility.nullToSpace(ht.get("NAME")) + "' ");
        }
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "STUT003." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){
                    if (rs.getColumnName(i).equals("AREACODE_HOME")){
                        if(rs.getString(i).charAt(0)=='-'){
                            rowHt.put(rs.getColumnName(i), rs.getString(i).substring(1,rs.getString(i).length()));
                        }else{
                            rowHt.put(rs.getColumnName(i),rs.getString(i));
                        }
                    }else{
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
            /**
     * JOIN 其它 Regt005,Stut002,Stut003,Syst003,Syst001 將欄位的中文化
     * 主要為中輟生做維護資料
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getReg002Regt005Stut002Stut003ForUse(Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        String ENROLL_AYEARSMS = "";
        String AYEAR_SMS_1 = "";
        if(!Utility.nullToSpace(ht.get("ENROLL_AYEARSMS")).equals("")) {
            ENROLL_AYEARSMS = ht.get("ENROLL_AYEARSMS").toString();
        }
        if(!Utility.nullToSpace(ht.get("AYEAR_SMS_1")).equals("")) {
            AYEAR_SMS_1 = ht.get("AYEAR_SMS_1").toString();
        }
        String AYEAR_1 = "";
        String SMS_1  = "";
        String AYEAR1="";
        String SMS1  ="";
        String AYEAR2="";
        String SMS2  ="";
        try{
           AYEAR_1= AYEAR_SMS_1.substring(0,3);
           SMS_1  =AYEAR_SMS_1.substring(3,4);
        }catch(Exception e1){

        }
        try{
           AYEAR1=((String)ht.get("AYEAR1")).substring(0,3);
           SMS1  =((String)ht.get("AYEAR1")).substring(3,4);
        }catch(Exception e1){
           AYEAR1= "000";
           SMS1  = "0";
        }
        try{
           AYEAR2=((String)ht.get("AYEAR2")).substring(0,3);
           SMS2  =((String)ht.get("AYEAR2")).substring(3,4);
        }catch(Exception e1){
           AYEAR2="999";
           SMS2  ="9";
        }
       

        sql.append("SELECT  ");
        sql.append("STUT003.STNO,STUT003.BIRTHDATE,STUT003.IDNO ,STUT003.ASYS, SYS_ASYS.CODE_NAME AS ASYS1, ");
        sql.append("SYS_STTYPE.CODE_NAME AS STTYPE1, STUT003.CENTER_CODE, SYS_CENTER.CENTER_ABBRNAME AS CENTER_CODE1,  ");
        sql.append("STUT002.NAME, STUT002.CRRSADDR, STUT002.AREACODE_HOME,STUT002.TEL_HOME,STUT002.MOBILE ,  ");
        sql.append("STUT002.EMAIL,STUT003.ENROLL_AYEARSMS,STUT003.FTSTUD_ENROLL_AYEARSMS,STUT003.STTYPE,  ");
        sql.append("CASE WHEN STUT003.STTYPE='1' THEN STUT003.FTSTUD_ENROLL_AYEARSMS ELSE STUT003.ENROLL_AYEARSMS END STTYPE2  ");
        sql.append("FROM STUT003  ");
        sql.append("JOIN STUT002 ON STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE ");
        sql.append("JOIN SYST001 SYS_ASYS ON SYS_ASYS.KIND='ASYS' AND SYS_ASYS.CODE = STUT003.ASYS  ");
        sql.append("JOIN SYST001 SYS_STTYPE ON SYS_STTYPE.KIND='STTYPE' AND SYS_STTYPE.CODE = STUT003.STTYPE  ");
        sql.append("JOIN SYST002 SYS_CENTER ON SYS_CENTER.CENTER_CODE = STUT003.CENTER_CODE  ");
        sql.append("WHERE 1  =  1  ");
        sql.append("AND 0=(SELECT COUNT(1) FROM REGT005 WHERE AYEAR||DECODE(SMS,'3','0',SMS)  BETWEEN  '"+AYEAR1+"'||DECODE('"+SMS1+"','3','0',"+SMS1+") AND '"+AYEAR2+"'||DECODE('"+SMS2+"','3','0',"+SMS2+") AND REGT005.STNO =STUT003.STNO AND REGT005.TAKE_ABNDN = 'N' ) ");

        //======================================================================
        /** == 查詢條件 ST == */
        sql.append("AND STUT003.ENROLL_STATUS = '2' ");
        sql.append("AND STUT003.ASYS= '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        if(!Utility.nullToSpace(ht.get("AYEAR_SMS_1")).equals("")) {
            sql.append("AND STUT003.STNO = (SELECT STNO FROM REGT005 WHERE AYEAR ='"+AYEAR_1+"' AND SMS = '"+SMS_1+"' AND REGT005.STNO =STUT003.STNO AND REGT005.TAKE_ABNDN = 'N' ) ");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")) {
            if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("%")) {
                sql.append("AND STUT003.CENTER_CODE ='" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
            }
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND STUT003.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND STUT003.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND STUT003.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("NAME")).equals("")) {
            sql.append("AND STUT002.NAME = '" + Utility.nullToSpace(ht.get("NAME")) + "' ");
        }
        if(!Utility.nullToSpace(ENROLL_AYEARSMS).equals("")) {
            sql.append("AND (CASE WHEN STUT003.STTYPE='1' THEN STUT003.FTSTUD_ENROLL_AYEARSMS ELSE  STUT003.ENROLL_AYEARSMS END) >= '" + Utility.nullToSpace(ENROLL_AYEARSMS) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("FTSTUD_ENROLL_AYEARSMS")).equals("")) {
            sql.append("AND STUT003.FTSTUD_ENROLL_AYEARSMS >= '" + Utility.nullToSpace(ht.get("FTSTUD_ENROLL_AYEARSMS")) + "' ");
        }
       
        /**
         * 排除當年應屆畢業生 grat003 ,grav014
        **/       
        String ayearsms = "";
    	if("1".equals(Utility.nullToSpace(ht.get("ASYS")))){
		    DBResult rs1 = dbmanager.getSimpleResultSet(conn);
            rs1.open();
            rs1.executeQuery("select max(ayear||sms) as ayearsms from grat003");
            if(rs1.next())
            {
            	ayearsms = rs1.getString("AYEARSMS");        		
            }
            rs1.close();
            
    		sql.append("AND NOT EXISTS (SELECT 1 FROM GRAT003 G03 "+
    				   "WHERE G03.GRAD_REEXAM_STATUS = '1' "+
    				   "AND G03.AYEAR||G03.SMS = '"+ayearsms+"' "+
    				   "AND G03.STNO = STUT003.STNO ) ");
    	}else if("2".equals(Utility.nullToSpace(ht.get("ASYS")))){
    		DBResult rs1 = dbmanager.getSimpleResultSet(conn);
            rs1.open();
            rs1.executeQuery("select max(ayear||sms) as ayearsms from grav014");
            if(rs1.next())
            {
            	ayearsms = rs1.getString("AYEARSMS");            		
            }
            rs1.close();
    		sql.append("AND NOT EXISTS (SELECT 1 FROM GRAV014 G14 "+
    				   "WHERE G14.STATUS = '1' "+
    				   "AND G14.AYEAR||G14.SMS = '"+ayearsms+"' "+
    				   "AND G14.STNO = STUT003.STNO ) ");
    	}
       
        /** == 查詢條件 ED == */
        
        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "STUT003." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            String AREACODE_HOME = "";
            while (rs.next())
            {
                int Success = 0;
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){
                    if (rs.getColumnName(i).equals("AREACODE_HOME")){
                        if(!rs.getString(i).equals("")){
                            AREACODE_HOME += rs.getString(i);
                        }
                    }else if(rs.getColumnName(i).equals("TEL_HOME")){
                        if(!AREACODE_HOME.equals("")){
                            AREACODE_HOME += "-" + rs.getString(i);
                        }else{
                            AREACODE_HOME += rs.getString(i);
                        }
                    }else{
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }
                rowHt.put("AREACODE_HOME", AREACODE_HOME);
                result.add(rowHt);

                AREACODE_HOME ="";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    private String AYEARSMS(String AYEAR1,String AYEAR2,String SMS1,String SMS2)
    {
        //如果951~962的話就是951~952~953~961~962
        //"AND ((REGT005.AYEAR IN (095) AND REGT005.SMS IN(1,2,3)) OR (REGT005.AYEAR IN (096) AND REGT005.SMS IN(1))) "
        //(REGT005.AYEAR IN (095) AND REGT005.SMS IN(1,2,3))
        String SQL ="";
        String tempSMS=SMS1;
        String tempAYEAR=AYEAR1;
        int AYEARnum = Integer.parseInt(AYEAR2) - Integer.parseInt(AYEAR1);
        int allnum = (Integer.parseInt(SMS2) +(2*AYEARnum));
//        System.out.println("AYEAR1======>"+AYEAR1);
//        System.out.println("AYEAR2======>"+AYEAR2);
//        System.out.println("SMS1======>"+SMS1);
//        System.out.println("SMS2======>"+SMS2);
//        System.out.println("AYEARnum======>"+AYEARnum);
//        System.out.println("allnum======>"+allnum);
//        System.out.println("==============================");
        int num=0;
        for(int i=0;i<allnum;i++){
            int wheresms = i%3;
//            System.out.println("wheresms"+wheresms);
            if(wheresms>=2){
//                System.out.println("(Integer.parseInt(tempSMS)+wheresms)>4====>"+String.valueOf(Integer.parseInt(tempSMS)+wheresms));
                tempAYEAR = String.valueOf(Integer.parseInt(tempAYEAR)+1);
                tempSMS="1";
//                System.out.println("tempAYEAR"+tempAYEAR);
//                System.out.println("tempSMS"+tempSMS);
            }else{
                tempSMS = String.valueOf(Integer.parseInt(tempSMS)+num);
                num=1;
//                System.out.println("tempSMS"+tempSMS);
            }
            if(i==0){
                SQL += " (REGT005.AYEAR IN(" + tempAYEAR + ") AND REGT005.SMS IN (" + tempSMS + ")) ";
//                System.out.println("SQL"+SQL);
            }else{
                SQL += " OR (REGT005.AYEAR IN(" + tempAYEAR + ") AND REGT005.SMS IN (" + tempSMS + ")) ";
//                System.out.println("SQL"+SQL);
            }
//            System.out.println("==============================");
        }
        return SQL;
    }
    private boolean getStut003(DBManager dbmanager,Connection conn,String STNO,String ENROLL_AYEARSMS) throws Exception {

        boolean result = false;
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

        sql.append
        (
            "SELECT NVL(FTSTUD_ENROLL_AYEARSMS,'0') AS FTSTUD_ENROLL_AYEARSMS FROM STUT003 " +
            "WHERE 1 = 1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(STNO).equals("")) {
            sql.append("AND STUT003.STNO = '" + STNO + "' ");
        }

        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "STUT003." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            if (rs.next()) {
                System.out.println("FTSTUD_ENROLL_AYEARSMS====>"+rs.getString("FTSTUD_ENROLL_AYEARSMS"));
                System.out.println("ENROLL_AYEARSMS====>"+ENROLL_AYEARSMS);
                if(Integer.parseInt(ENROLL_AYEARSMS) >= Integer.parseInt(rs.getString("FTSTUD_ENROLL_AYEARSMS"))){
                    result = true;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    private String getStut003ForAYEARSMS(DBManager dbmanager,Connection conn,String STNO) throws Exception {

        String ENROLL_AYEARSMS = "";
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        System.out.println(STNO);
        sql.append
        (
            "SELECT NVL(FTSTUD_ENROLL_AYEARSMS,'') AS FTSTUD_ENROLL_AYEARSMS FROM STUT003 " +
            "WHERE 1 = 1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(STNO).equals("")) {
            sql.append("AND STUT003.STNO = '" + STNO + "' ");
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            if (rs.next()) {
                ENROLL_AYEARSMS = rs.getString("FTSTUD_ENROLL_AYEARSMS");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        System.out.println("ENROLL_AYEARSMS"+ENROLL_AYEARSMS);
        return ENROLL_AYEARSMS;
    }
    public Vector getRegt002Stut002Stut003ForUse(Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append
        (
            "SELECT STUT003.CENTER_CODE,STUT003.STTYPE,STUT002.NAME,STUT003.IDNO,STUT003.BIRTHDATE,STUT002.SEX, " +
            "STUT002.MARRIAGE,STUT002.CRRSADDR,NVL(STUT002.AREACODE_OFFICE,'') AS AREACODE_OFFICE,NVL(STUT002.TEL_OFFICE,'') AS TEL_OFFICE, " +
            "NVL(STUT002.TEL_OFFICE_EXT,'') AS TEL_OFFICE_EXT,NVL(STUT002.AREACODE_HOME,'') AS AREACODE_HOME,NVL(STUT002.TEL_HOME,'') AS TEL_HOME,NVL(STUT002.MOBILE,'') AS MOBILE,STUT002.ROWSTAMP, " +
            "NVL(B.PARENTS_RACE,'00') AS ORIGIN_RACE,NVL(B.HANDICAP_TYPE,'00') AS HANDICAP_TYPE,NVL(B.HANDICAP_GRADE,'00') AS HANDICAP_GRADE " +
            //",C.AYEAR7,C.AYEAR8,C.AYEAR9 " +
            ",A1.FACULTY_NAME AS AYEAR7, B1.FACULTY_NAME AS AYEAR8, C1.FACULTY_NAME AS AYEAR8 " +
            "FROM STUT003 JOIN STUT002 ON STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE " +
            "LEFT JOIN (  " +
                "SELECT A.AYEAR,A.SMS,A.STNO,B.PARENTS_RACE,C.HANDICAP_TYPE,C.HANDICAP_GRADE FROM (  " +
                    "SELECT MAX(AYEAR) AS AYEAR ,MAX(SMS) AS SMS ,'" + Utility.nullToSpace(ht.get("STNO")) + "' AS STNO FROM SGUT004 WHERE STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' " +
                ") A  " +
                    "LEFT JOIN SGUT004 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO AND B.HAND_NATIVE='1'  " +
                    "LEFT JOIN SGUT004 C ON A.AYEAR=C.AYEAR AND A.SMS=C.SMS AND A.STNO=C.STNO AND C.HAND_NATIVE='2'  " +
            ") B ON STUT003.STNO =  B.STNO " +
            "LEFT JOIN GRAT003 ON GRAT003.STNO = STUT003.STNO " +
            "LEFT JOIN SYST003 A1 ON 1 = 1 AND A1.ASYS = STUT003.ASYS  AND A1.FACULTY_CODE = GRAT003.DBMAJOR_GRAD_FACULTY_CODE1 " +
            "LEFT JOIN SYST003 B1 ON 1 = 1 AND B1.ASYS = STUT003.ASYS  AND B1.FACULTY_CODE = GRAT003.DBMAJOR_GRAD_FACULTY_CODE2 " +
            "LEFT JOIN SYST003 C1 ON 1 = 1 AND C1.ASYS = STUT003.ASYS  AND C1.FACULTY_CODE = GRAT003.GRAD_MAJOR_FACULTY " +
            //"LEFT JOIN ( " +
            //"SELECT GRAT007.STNO,STUT003.IDNO,GRAT007.GRAD_FACULTY_11,S1.TOTAL_CRS_NAME AS AYEAR7,GRAT007.GRAD_FACULTY_21,S2.TOTAL_CRS_NAME AS AYEAR8,GRAT007.GRAD_FACULTY_31,S3.TOTAL_CRS_NAME AS AYEAR9  " +
            //"FROM GRAT007 JOIN STUT003 ON GRAT007.STNO = STUT003.STNO  " +
            //"LEFT JOIN SYST008 S1 ON STUT003.ASYS=S1.ASYS AND GRAT007.GRAD_FACULTY_11=S1.FACULTY_CODE AND (STUT003.J_FACULTY_CODE = S1.TOTAL_CRS_NO OR (STUT003.ASYS='1' AND NVL(STUT003.J_FACULTY_CODE,'X') ='X')) " +
            //"LEFT JOIN SYST008 S2 ON STUT003.ASYS=S2.ASYS AND GRAT007.GRAD_FACULTY_21=S2.FACULTY_CODE AND (STUT003.J_FACULTY_CODE = S2.TOTAL_CRS_NO OR (STUT003.ASYS='1' AND NVL(STUT003.J_FACULTY_CODE,'X') ='X')) " +
            //"LEFT JOIN SYST008 S3 ON STUT003.ASYS=S3.ASYS AND GRAT007.GRAD_FACULTY_31=S3.FACULTY_CODE AND (STUT003.J_FACULTY_CODE = S3.TOTAL_CRS_NO OR (STUT003.ASYS='1' AND NVL(STUT003.J_FACULTY_CODE,'X') ='X')) " +
            //") C ON STUT003.STNO = C.STNO AND STUT003.IDNO = C.IDNO " +
            "WHERE 1    =    1 "
        );
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND STUT003.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND STUT003.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND STUT003.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND STUT003.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")) {
            sql.append("AND STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
        }
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "STUT003." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

    /** 用於撈取RPT-REG-02-260 列卬(依性別區分)資料 */
      public Vector getPrintRegt005Stut003Stut002ForUse(Hashtable ht ,boolean choice) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

    sql.append(
"SELECT A.CODE,COUNT(B.STNO_TOTAL) AS STNO_TOTAL FROM SYST001 A LEFT JOIN " +
"(SELECT C.SEX,A.STNO AS STNO_TOTAL " +
"FROM regt005 A JOIN STUT003 B ON A.STNO=B.STNO JOIN STUT002 C ON B.IDNO=C.IDNO  AND B.BIRTHDATE=C.BIRTHDATE " +
"WHERE 1  =  1 " +
"AND A.PAYMENT_STATUS='1' "
);



//
//        sql.append
//        (
//          "SELECT C.EDUBKGRD_GRADE,COUNT(A.STNO) AS STNO_TOTAL " +
//          "FROM regt005 A JOIN STUT003 B ON A.STNO=B.STNO " +
//          "JOIN STUT002 C ON B.IDNO=C.IDNO  AND B.BIRTHDATE=C.BIRTHDATE " +
//          "WHERE 1  =  1 " +
//          "AND A.PAYMENT_STATUS='1' "
//       );
        /** == 查詢條件 ST == */
        int a=0;
        int c=0;
        String b=null;
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {

        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
         if(Utility.nullToSpace(ht.get("SMS")).equals("1")){
              a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR")))) - 1;
              c  = Integer.parseInt((Utility.nullToSpace(ht.get("SMS")))) + 1;
              b=""+0+a;
             sql.append("AND A.AYEAR = '" + b + "' ");
        } else {
            c=1;
             a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR"))));
                 b=""+0+a;
               sql.append("AND A.AYEAR = '" + b + "' ");
               }
        }

            sql.append("AND A.SMS = '" + c + "' ");
        }
             if(c==1)
              c=2;
              else
              c=1;
             if(choice)
            sql.append("AND A.STNO IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");
              else
              sql.append("AND A.STNO NOT IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");
        sql.append("B ON A.CODE = B.SEX WHERE KIND='SEX' GROUP BY A.CODE ");
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
      /** 用於撈取RPT-REG-02-260 列卬(依年齡區分)資料 */
      public Vector getPrint2Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

    sql.append(
"SELECT B.BIRTHDATE,COUNT(A.STNO) FROM regt005 A JOIN " +
"STUT003 B ON A.STNO=B.STNO JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE WHERE " +
"A.PAYMENT_STATUS='1' "
);

       int a=0;
        int c=0;
        String b=null;
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {

        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
         if(Utility.nullToSpace(ht.get("SMS")).equals("1")){
              a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR")))) - 1;
              c  = Integer.parseInt((Utility.nullToSpace(ht.get("SMS")))) + 1;
              b=""+0+a;
             sql.append("AND A.AYEAR = '" + b + "' ");
        } else {
            c=1;
             a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR"))));
                 b=""+0+a;
               sql.append("AND A.AYEAR = '" + b + "' ");
               }
        }

            sql.append("AND A.SMS = '" + c + "' ");
        }
              if(c==1)
              c=2;
              else
              c=1;
             if(choice)
            sql.append("AND A.STNO IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "') ");
              else
              sql.append("AND A.STNO NOT IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "') ");
        sql.append("GROUP BY B.BIRTHDATE ");
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){

                          System.out.println("資料筆數" + i);
                    if(rs.getColumnName(i).equals("BIRTHDATE")){
                          String year = String.valueOf(Integer.parseInt(DateUtil.getNowDate())-Integer.parseInt(rs.getString(i))).substring(0,2);
                          rowHt.put(rs.getColumnName(i),year);
                          System.out.println(year);
                    }else{
                          rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }

                }
                result.add(rowHt);

            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

     /**  用於撈取RPT-REG-02-260 列卬(依職業區分)資料 */
      public Vector getPrint3Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

    sql.append(

  "SELECT A.CODE_NAME,COUNT(B.VO_TOTAL) AS STNO_COUNT FROM SYST001 A LEFT JOIN " +
  "(SELECT D.CODE AS VOCATION_ID,A.STNO AS VO_TOTAL FROM regt005 A JOIN " +
  "STUT003 B ON A.STNO=B.STNO JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE JOIN SYST001 D ON C.VOCATION = D.CODE AND KIND ='VOCATION' WHERE " +
  "A.PAYMENT_STATUS='1' "

//"SELECT B.BIRTHDATE,COUNT(A.STNO) FROM regt005 A JOIN " +
//"STUT003 B ON A.STNO=B.STNO JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE WHERE " +
//"A.PAYMENT_STATUS='1' "

      );
       int a=0;
        int c=0;
         String b=null;
          if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {

          if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
          if(Utility.nullToSpace(ht.get("SMS")).equals("1")){
         a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR")))) - 1;
        c  = Integer.parseInt((Utility.nullToSpace(ht.get("SMS")))) + 1;
       b=""+0+a;
     sql.append("AND A.AYEAR = '" + b + "' ");
        } else {
         c=1;
          a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR"))));
            b=""+0+a;
              sql.append("AND A.AYEAR = '" + b + "' ");
               }
                }

                  sql.append("AND A.SMS = '" + c + "' ");
                   }
                    if(c==1)
                    c=2;
                    else
                    c=1;
                  if(choice)
                 sql.append("AND A.STNO IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");
               else
             sql.append("AND A.STNO NOT IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");

        sql.append("B ON A.CODE = B.VOCATION_ID WHERE A.KIND='VOCATION' GROUP BY A.CODE_NAME");
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                   {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                    System.out.println(rs.getString(i));
                    }
                result.add(rowHt);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }


 /** 用於撈取RPT-REG-02-260 列卬(依學歷區分)資料 */
      public Vector getPrint4Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
        sql.delete(0, sql.length());
        }

sql.append(

  "SELECT A.CODE,COUNT(B.STNO) AS STNO_TOTAL FROM SYST001 A LEFT JOIN " +
  "(SELECT C.EDUBKGRD_GRADE AS EDUBKGRD_GRADE,A.STNO FROM regt005 A JOIN STUT003 B ON A.STNO=B.STNO JOIN " +
  "STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE JOIN SYST001 D ON C.EDUBKGRD_GRADE = D.CODE AND (D.CODE BETWEEN '01' AND '06') AND KIND='EDUBKGRD_GRADE' " +
  "WHERE A.PAYMENT_STATUS='1' "


//"SELECT B.BIRTHDATE,COUNT(A.STNO) FROM regt005 A JOIN " +
//"STUT003 B ON A.STNO=B.STNO JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE WHERE " +
//"A.PAYMENT_STATUS='1' "

         );
       int a=0;
        int c=0;
         String b=null;
          if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {

           if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            if(Utility.nullToSpace(ht.get("SMS")).equals("1")){
              a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR")))) - 1;
              c  = Integer.parseInt((Utility.nullToSpace(ht.get("SMS")))) + 1;
              b=""+0+a;
             sql.append("AND A.AYEAR = '" + b + "' ");
            } else {
           c=1;
          a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR"))));
         b=""+0+a;
        sql.append("AND A.AYEAR = '" + b + "' ");
        }
        }

            sql.append("AND A.SMS = '" + c + "' ");
        }
              if(c==1)
              c=2;
              else
              c=1;
             if(choice)
            sql.append("AND A.STNO IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");
              else
            sql.append("AND A.STNO NOT IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");

            sql.append("B ON A.CODE = B.EDUBKGRD_GRADE WHERE KIND='EDUBKGRD_GRADE' AND A.CODE BETWEEN '01' AND '06' GROUP BY A.CODE");
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                   {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                    System.out.println(rs.getString(i));
                    }
                    result.add(rowHt);
                    }

            } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
             rs.close();
             }
        }
        return result;
    }

    /** 用於撈取RPT-REG-02-260 列卬(依學歷區分)資料 */
      public Vector getPrint5Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
        sql.delete(0, sql.length());
        }

sql.append(

"SELECT A.CENTER_CODE ,COUNT(STNO) AS STNO_TOTAL FROM SYST002 A LEFT JOIN " +
"(SELECT B.CENTER_CODE,A.STNO FROM regt005 A JOIN STUT003 B ON A.STNO=B.STNO JOIN " +
"STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE " +
"WHERE A.PAYMENT_STATUS='1' "

         );
       int a=0;
        int c=0;
         String b=null;
          if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {

           if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            if(Utility.nullToSpace(ht.get("SMS")).equals("1")){
              a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR")))) - 1;
              c  = Integer.parseInt((Utility.nullToSpace(ht.get("SMS")))) + 1;
              b=""+0+a;
             sql.append("AND A.AYEAR = '" + b + "' ");
            } else {
           c=1;
          a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR"))));
         b=""+0+a;
        sql.append("AND A.AYEAR = '" + b + "' ");
        }
        }

            sql.append("AND A.SMS = '" + c + "' ");
        }
              if(c==1)
              c=2;
              else
              c=1;
             if(choice)
            sql.append("AND A.STNO IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");
              else
            sql.append("AND A.STNO NOT IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");

            sql.append("B ON A.CENTER_CODE = B.CENTER_CODE WHERE '1' = '1' AND A.CENTER_CODE NOT IN(00) GROUP BY A.CENTER_CODE");
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                   {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                    System.out.println(rs.getString(i));
                    }
                    result.add(rowHt);
                    }

            } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
             rs.close();
             }
        }
        return result;
    }
     /**  用於撈取RPT-REG-02-260 列卬(依學歷區分)資料 */
      public Vector getPrint6Regt005Stut003Stut002ForUse(Hashtable ht ,boolean choice) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
        sql.delete(0, sql.length());
        }

sql.append(

"SELECT A.CODE ,COUNT(STNO) AS STNO_TOTAL FROM SYST001 A LEFT JOIN " +
"(SELECT B.STTYPE,A.STNO FROM regt005 A JOIN STUT003 B ON A.STNO=B.STNO JOIN " +
"STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE=C.BIRTHDATE " +
"WHERE A.PAYMENT_STATUS='1' "

         );
       int a=0;
        int c=0;
         String b=null;
          if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {

           if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            if(Utility.nullToSpace(ht.get("SMS")).equals("1")){
              a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR")))) - 1;
              c  = Integer.parseInt((Utility.nullToSpace(ht.get("SMS")))) + 1;
              b=""+0+a;
             sql.append("AND A.AYEAR = '" + b + "' ");
            } else {
           c=1;
          a  = Integer.parseInt((Utility.nullToSpace(ht.get("AYEAR"))));
         b=""+0+a;
        sql.append("AND A.AYEAR = '" + b + "' ");
        }
        }

            sql.append("AND A.SMS = '" + c + "' ");
        }
              if(c==1)
              c=2;
              else
              c=1;
             if(choice)
            sql.append("AND A.STNO IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");
              else
            sql.append("AND A.STNO NOT IN (SELECT STNO FROM regt005 WHERE PAYMENT_STATUS='1' AND AYEAR='" + b + "' AND SMS='" + c + "')) ");

            sql.append("B ON A.CODE = B.STTYPE WHERE A.KIND='STTYPE' AND A.CODE IN ('1','2') GROUP BY A.CODE");
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                   {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                    System.out.println(rs.getString(i));
                    }
                    result.add(rowHt);
                    }

            } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
             rs.close();
             }
        }
        return result;
    }
    /**
     * regt003m選課的學生基本資料
     */
    public Vector getRegt005Stut002Stut003Query(Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT  " );
        sql.append("STUT003.ASYS,STUT003.CENTER_CODE,STUT003.STNO,STUT002.NAME,STUT003.IDNO,STUT003.BIRTHDATE,STUT003.LASTREG_AYEARSMS " );
        sql.append("FROM STUT003  ");
        sql.append("JOIN STUT002 ON STUT002.IDNO = STUT003.IDNO AND STUT002.BIRTHDATE = STUT003.BIRTHDATE ");
        /** == 查詢條件 ST == */
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND STUT003.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "STUT003." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

    /** 將方法複制一份過去來 修改阿賢的程式  用於撈取RPT-REG-02-260 列卬(依學歷區分)資料 */
      public Vector getRegt005Stut003Stut002Syst002ForUse(Hashtable ht) throws Exception {

        Vector result = new Vector();
        if(sql.length() > 0) {
        sql.delete(0, sql.length());
        }

sql.append(

            "SELECT H.CODE_NAME AS CASYS, G.CODE_NAME AS CSMS,F.CENTER_NAME,E.CODE_NAME AS CSTTYPE,A.ASYS, A.AYEAR, A.SMS, A.STNO, B.IDNO, C.NAME, C.DMSTADDR, C.DMSTADDR_ZIP, D.ADDR, D.ZIP " +
            "FROM REGT005 A JOIN STUT003 B ON A.STNO = B.STNO AND A.ASYS = B.ASYS " +
            "JOIN STUT002 C ON B.IDNO = C.IDNO JOIN SYST002 D ON D.CENTER_CODE = B.CENTER_CODE " +
            "JOIN SYST001 E ON E.KIND = 'STTYPE' AND E.CODE = B.STTYPE JOIN SYST002 F ON B.CENTER_CODE = F.CENTER_CODE " +
            "JOIN SYST001 G ON G.KIND = 'SMS' AND G.CODE = A.SMS JOIN SYST001 H ON H.KIND = 'ASYS' AND H.CODE = A.ASYS " +
            "WHERE 1 = 1 "
//             AND A.AYEAR = '096'
//            AND A.SMS = '1'
//            AND A.STNO = '881001001'
//            AND B.STTYPE = '2'
//             AND B.CENTER_CODE = '00'
         );
      if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
            }
         if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND A.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
         if(!Utility.nullToSpace(ht.get("STTYPE")).equals("")) {
            sql.append("AND B.STTYPE = '" + Utility.nullToSpace(ht.get("STTYPE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")) {
            sql.append("AND B.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' ");
            }



        /** == 查詢條件 ED == */

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println(sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                   {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                    System.out.println(rs.getString(i));
                    }
                    result.add(rowHt);
                    }

            } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
             rs.close();
             }
        }
        return result;
    }

	public void getPub140mQueryTarget1(Vector vt, Hashtable ht) throws Exception {
		DBResult rs = null;
		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());

			sql.append
			(
				"SELECT a.STNO, b.IDNO, b.CENTER_CODE, c.NAME, c.CRRSADDR_ZIP, c.CRRSADDR, DECODE(d.CODE, '13', SUBSTR(CODE_NAME,1,5), SUBSTR(CODE_NAME,1,2))AS CENTER_NAME " +
				"FROM REGT005 a, STUT003 b, STUT002 c, SYST001 d " +
				"WHERE '1'='1' AND a.PAYMENT_STATUS<>'1' AND a.MAG_ORDER_MK='Y' AND a.TAKE_ABNDN='N' AND a.STNO=b.STNO AND b.IDNO=c.IDNO AND b.BIRTHDATE=c.BIRTHDATE AND d.KIND='CENTER_CODE' AND d.CODE=b.CENTER_CODE "
			);

			sql.append("AND a.AYEAR = '" + Utility.dbStr(ht.get("AYEAR")) + "' ");
			sql.append("AND a.SMS = '" + Utility.dbStr(ht.get("SMS")) + "' ");
			sql.append("ORDER BY b.CENTER_CODE, a.STNO, c.CRRSADDR ");

			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				vt.add(rowHt);
			}

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
	/**
	   * REG131R 列印新生、舊生註冊人數統計表
	   */
	public Vector getReg131Regt005( Hashtable ht) throws Exception {
		Vector result = new Vector();
		DBResult rs = null;
		String title="";
		String AYEAR = Utility.checkNull(ht.get("AYEAR"),"");
		String SMS = Utility.checkNull(ht.get("SMS"),"");
		String STTYPE = Utility.checkNull(ht.get("STTYPE"),"");
		String ASYS = Utility.checkNull(ht.get("ASYS"),"");
		String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");
		int CASE = Integer.parseInt(Utility.checkNull(ht.get("CASE"),"0"));

		String sCond1 = "";
		if(PRT_MANNER.equals("2")){
			sCond1 = " AND A.PAYMENT_STATUS<>'1' ";
		}

		String sCond12 = "" ;	//未繼績選課sql

		 if(!AYEAR.equals("")) sCond12 += " AND AYEAR ='"+AYEAR+"' ";
	     if(!SMS.equals("")) sCond12 += " AND SMS ='"+SMS+"' ";
	     if(!STTYPE.equals("")) sCond12 += " AND STTYPE ='"+STTYPE+"' ";
	     if(ASYS.equals("0") || ASYS.equals("")){
	    	 sCond12 += " AND ASYS IN (1, 2) ";
	     }else{
	    	 sCond12 += " AND ASYS IN ("+ASYS+") ";
        }


		 switch (CASE){

		    case 1:

		    	title = " SEX,NEW_OLD,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY SEX,NEW_OLD " ;

		      break;
            case 2:

		    	title   =  " VOCATION,NEW_OLD,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY VOCATION,NEW_OLD " ;

		      break;
            case 3:

		    	title   =  " EDUBKGRD_GRADE,NEW_OLD,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY EDUBKGRD_GRADE,NEW_OLD " ;

		      break;
            case 4:

		    	title   =  " CENTER_CODE,NEW_OLD,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY CENTER_CODE,NEW_OLD " ;

		      break;
            case 5:

		    	title   =  " STTYPE,NEW_OLD,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY STTYPE,NEW_OLD " ;

		      break;
		 }
		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());


			sql.append("SELECT "+ title + " FROM( "+
                    "SELECT A.AYEAR,A.SMS,A.STNO,B.ASYS,C.VOCATION,C.SEX,DECODE(B.SPECIAL_STTYPE_TYPE,'','NOT_SPECIAL_STTYPE_TYPE','SPECIAL_STTYPE_TYPE') AS SPECIAL_STTYPE_TYPE, "+
					"C.EDUBKGRD_GRADE,B.STTYPE,B.CENTER_CODE, "+
                    "(CASE WHEN (( DECODE (NVL(B.FTSTUD_ENROLL_AYEARSMS,''),'' ,B.ENROLL_AYEARSMS, B.FTSTUD_ENROLL_AYEARSMS)) = (A.AYEAR || A.SMS)) THEN '1' ELSE '2' END) AS NEW_OLD "+
                    "FROM REGT005 A "+
                    "JOIN STUT003 B ON A.STNO=B.STNO "+
                    "JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE = C.BIRTHDATE "+
                    "WHERE 1=1 AND A.TAKE_ABNDN='N' AND A.TOTAL_CRD_CNT > 0 " + sCond1 +
                   ")WHERE 0=0 " + sCond12);


			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
       return result;
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
	/**
	   * REG131R 列印新生、舊生註冊人數統計表
	   */
	public Vector getReg131Regt005age( Hashtable ht) throws Exception {
		Vector result = new Vector();
		DBResult rs = null;
		String title="";
		String AYEAR = Utility.checkNull(ht.get("AYEAR"),"");
		String SMS = Utility.checkNull(ht.get("SMS"),"");
		String ASYS = Utility.checkNull(ht.get("ASYS"),"");
		String STTYPE = Utility.checkNull(ht.get("STTYPE"),"");
		String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");
		int CASE = Integer.parseInt(Utility.checkNull(ht.get("CASE"),"0"));

		String sCond1 = "";
		String sCond3 = "" ;
		if(PRT_MANNER.equals("2")){
			sCond1 = " AND A.PAYMENT_STATUS<>'1' ";
		}
		if(!STTYPE.equals("")) sCond1 += " AND B.STTYPE ='"+STTYPE+"' ";

		String sCond12 = "" ;	//未繼績選課sql
		 if(!AYEAR.equals("")) sCond12 += " AND AYEAR ='"+AYEAR+"' ";
	     if(!SMS.equals("")) sCond12 += " AND SMS ='"+SMS+"' ";
	     sCond3 = sCond12;
	     if(ASYS.equals("0") || ASYS.equals("")){
	    	 sCond12 += " AND ASYS IN (1, 2) ";
	     }else{
	    	 sCond12 += " AND ASYS IN ("+ASYS+") ";
	    }
	     

		 switch (CASE){

		    case 1:

		    	title = " AGE_LEVEL,NEW_OLD,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY AGE_LEVEL,NEW_OLD " ;

		      break;

		 }
		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());


			sql.append
			(
				"SELECT "+ title + " FROM "+
				"( "+
					"SELECT AYEAR,SMS,NEW_OLD,STNO,ASYS, "+
					"(CASE WHEN AGE<=19 THEN '1' "+
					"WHEN AGE>=20 AND AGE<=24 THEN '2' "+
					"WHEN AGE>=25 AND AGE<=29 THEN '3' "+
					"WHEN AGE>=30 AND AGE<=34 THEN '4' "+
					"WHEN AGE>=35 AND AGE<=39 THEN '5' "+
					"WHEN AGE>=40 AND AGE<=44 THEN '6' "+
					"WHEN AGE>=45 AND AGE<=49 THEN '7' "+
					"WHEN AGE>=50 AND AGE<=54 THEN '8' "+
					"WHEN AGE>=55 AND AGE<=59 THEN '9' "+
					"WHEN AGE>=60 AND AGE<=64 THEN '10' "+
					"WHEN AGE>=65 THEN '11' END) AGE_LEVEL "+
					"FROM "+
					"( "+
						"SELECT A.AYEAR,A.SMS,A.STNO,B.ASYS, "+
						" (CASE " +
						"WHEN (TO_NUMBER(SUBSTR(c.enroll_basedate, 5, 4) - SUBSTR(b.birthdate, 5, 4)) < 0) " +
						"THEN (SUBSTR(c.enroll_basedate, 1, 4) - SUBSTR(b.birthdate, 1, 4) - 1) " +
						"ELSE (SUBSTR(c.enroll_basedate, 1, 4) - SUBSTR(b.birthdate, 1, 4)) " +
						"END) AS AGE, "+
						"(CASE WHEN (( DECODE (NVL(B.FTSTUD_ENROLL_AYEARSMS,''),'' ,B.ENROLL_AYEARSMS, B.FTSTUD_ENROLL_AYEARSMS)) = (A.AYEAR || A.SMS)) THEN '1' ELSE '2' END) AS NEW_OLD "+
						"FROM REGT005 A " +
						"JOIN STUT003 B ON A.STNO=B.STNO " +
						"JOIN (SELECT AYEAR,SMS,ENROLL_BASEDATE FROM SYST005 WHERE 0=0 " + sCond3 + " ) C ON A.AYEAR=C.AYEAR " +
						"WHERE 1=1 AND A.TAKE_ABNDN='N'  " +sCond1+
					") "+
				")WHERE 0=0 " + 
				sCond12
			);


			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
     return result;
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
	/**
	   * REG133R 列印未繼續註冊登記人數統計表
	   */
	public Vector getReg133Regt005( Hashtable ht) throws Exception {
		Vector result = new Vector();
		DBResult rs = null;
		String title = Utility.checkNull(ht.get("TITLE"),"");
		String YEAR = Utility.checkNull(ht.get("YEAR"),"");
		String SMS = Utility.checkNull(ht.get("SMS"),"");
		String YEAR2 = Utility.checkNull(ht.get("YEAR2"),"");
		String SMS2 = Utility.checkNull(ht.get("SMS2"),"");
		String ASYS = Utility.checkNull(ht.get("ASYS"),"");
		String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");
		int CASE = Integer.parseInt(Utility.checkNull(ht.get("CASE"),"0"));

		String sCond1 = "";
		String  sCond3 = "" ;
		if(PRT_MANNER.equals("2")){
				sCond1 = " AND a.PAYMENT_STATUS<>'1' ";
				sCond3 = " AND b.PAYMENT_STATUS<>'1' ";
		}

		String sCond12 = "" ;	//未繼績選課sql
		 if(ASYS.equals("0") || ASYS.equals(""))
			 sCond12 += " AND ASYS IN (1, 2) ";
		    else
		    	sCond12 += " AND ASYS IN ("+ASYS+") ";




		 switch (CASE){

		    case 1:

		    	title = " SEX,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY SEX ORDER BY SEX " ;

		      break;
		    case 2:

		    	title = " VOCATION,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY VOCATION ORDER BY VOCATION ";
		      break;
		    case 3:

		    	title = " EDUBKGRD_GRADE,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY EDUBKGRD_GRADE ORDER BY EDUBKGRD_GRADE ";

		      break;
		    case 4:

		    	title = "CENTER_CODE,COUNT(STNO)TOTAL";
		    	sCond12 += " GROUP BY CENTER_CODE ORDER BY CENTER_CODE ";

		      break;
		    case 5:

		    	title = "STTYPE,COUNT(STNO)TOTAL";
		    	sCond12 += " GROUP BY STTYPE ORDER BY STTYPE ";

		      break;

		 }
		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());


			sql.append("SELECT "+ title + " FROM( "+
                 	"SELECT A.STNO,B.ASYS,C.VOCATION,C.SEX,C.EDUBKGRD_GRADE,B.STTYPE,B.CENTER_CODE "+
                     "FROM " +
                     	"( " +
                             "SELECT A.STNO FROM REGT005 A WHERE A.AYEAR = '" + YEAR2 + "' AND A.SMS = '" + SMS2 + "' and a.TAKE_ABNDN='N' AND a.PAYMENT_STATUS<>'1' " +sCond1 +
                             		// 要排除上個學期已畢業的學生
                             		"AND NOT EXISTS (SELECT 1 FROM GRAT003 D WHERE D.AYEAR=A.AYEAR AND D.SMS=A.SMS AND D.STNO=A.STNO AND D.GRAD_REEXAM_STATUS='2') "+
                             		"AND NOT EXISTS (SELECT 1 FROM GRAV014 D WHERE D.AYEAR=A.AYEAR AND D.SMS=A.SMS AND D.STNO=A.STNO AND D.STATUS='2') "+
                             "MINUS " +
                             "SELECT B.STNO FROM REGT005 B WHERE B.AYEAR = '" + YEAR + "' AND B.SMS = '" + SMS + "'  " +sCond3 +
                          ") A " +
                     "JOIN STUT003 B ON A.STNO=B.STNO " +
                     "JOIN STUT002 C ON B.IDNO=C.IDNO AND B.BIRTHDATE = C.BIRTHDATE "+

                     ")WHERE 0=0 " + sCond12);

System.out.println("我是REG133R:"+sql);
			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
         return result;
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

	/**
	   * REG133R 列印未繼續註冊登記人數統計表
	   */
	public Vector getReg133Regt005age( Hashtable ht) throws Exception {
		Vector result = new Vector();
		DBResult rs = null;
		String title = Utility.checkNull(ht.get("TITLE"),"");
		String YEAR = Utility.checkNull(ht.get("YEAR"),"");
		String SMS = Utility.checkNull(ht.get("SMS"),"");
		String YEAR2 = Utility.checkNull(ht.get("YEAR2"),"");
		String SMS2 = Utility.checkNull(ht.get("SMS2"),"");
		String ASYS = Utility.checkNull(ht.get("ASYS"),"");
		String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");
		int CASE = Integer.parseInt(Utility.checkNull(ht.get("CASE"),"0"));

		String sCond1 = "";
		String sCond3 = "";
		if(PRT_MANNER.equals("2")){
			sCond1 = " AND a.PAYMENT_STATUS<>'1' ";
			sCond3 = " AND b.PAYMENT_STATUS<>'1' ";
		}


		String sCond12 = "" ;	//未繼績選課sql
		 if(ASYS.equals("0") || ASYS.equals(""))
			 sCond12 += " AND ASYS IN (1, 2) ";
		    else
		    	sCond12 += " AND ASYS IN ("+ASYS+") ";



		 switch (CASE){

		    case 1:

		    	title = " AGE_LEVEL,COUNT(STNO)TOTAL ";
		    	sCond12 += " GROUP BY AGE_LEVEL ORDER BY AGE_LEVEL " ;

		      break;

		 }
		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());


			sql.append("SELECT "+title+" FROM( "+
                    "SELECT AYEAR,STNO,ASYS, "+
                    "(CASE WHEN AGE <= 19 THEN 1 "+
                    "WHEN AGE >= 20 AND AGE <= 24 THEN 2 "+
                    "WHEN AGE >= 25 AND AGE <= 29 THEN 3 "+
                    "WHEN AGE >= 30 AND AGE <= 34 THEN 4 "+
                    "WHEN AGE >= 35 AND AGE <= 39 THEN 5 "+
                    "WHEN AGE >= 40 AND AGE <= 44 THEN 6 "+
                    "WHEN AGE >= 45 AND AGE <= 49 THEN 7 "+
                    "WHEN AGE >= 50 AND AGE <= 54 THEN 8 "+
                    "WHEN AGE >= 55 AND AGE <= 59 THEN 9 "+
                    "WHEN AGE >= 60 AND AGE <= 64 THEN 10 "+
                    "WHEN AGE >= 65 THEN 11 END) AGE_LEVEL "+
                    "FROM (SELECT '"+YEAR2+"' AS AYEAR,A.STNO,B.ASYS, "+
                    "(SUBSTR(C.ENROLL_BASEDATE,0,4)-SUBSTR(B.BIRTHDATE,0,4)) AS AGE  "+
                    "FROM ( " +
                    	"SELECT A.STNO FROM REGT005 A WHERE A.AYEAR = '" + YEAR2 + "' AND A.SMS = '" + SMS2 + "' and A.TAKE_ABNDN='N' AND A.PAYMENT_STATUS<>'1' "  +sCond1 +
	                    	// 要排除上個學期已畢業的學生
	                 		" AND NOT EXISTS (SELECT 1 FROM GRAT003 D WHERE D.AYEAR=A.AYEAR AND D.SMS=A.SMS AND D.STNO=A.STNO AND D.GRAD_REEXAM_STATUS='2') "+
	                 		" AND NOT EXISTS (SELECT 1 FROM GRAV014 D WHERE D.AYEAR=A.AYEAR AND D.SMS=A.SMS AND D.STNO=A.STNO AND D.STATUS='2') "+
                    	"MINUS " +
                    	"SELECT B.STNO FROM REGT005 B WHERE  B.AYEAR = '" + YEAR + "' AND B.SMS = '" + SMS +"'  "  +sCond3 +
                 		") A "+
                    "JOIN STUT003 B ON A.STNO=B.STNO JOIN (SELECT AYEAR,SMS,ENROLL_BASEDATE "+
                    //"FROM SYST005 WHERE 0=0 "+other_where.substring(0,32)+" ) C ON A.AYEAR=C.AYEAR) "+
                    "FROM SYST005 WHERE AYEAR = '" + YEAR + "' AND SMS = '" + SMS + "') C ON 0=0) "+
                    ")WHERE 0=0 "+sCond12);

			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
       return result;
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

  /**
   * REG125R 各中心各科選課人數統計表，取出 科目編號、科目名稱、選課人數
   * 並依學生別、性別分開計算合計
   */
  public Vector getRegt005Regt007Cout002Stut003Syst002Plat002ForUse(Hashtable ht) throws Exception {
    Vector result = new Vector();
    if(sql.length() > 0) {
      sql.delete(0, sql.length());
    }

    String asys = Utility.checkNull(ht.get("ASYS"),"");                                // 學制
    String ayear = Utility.checkNull(ht.get("AYEAR"),"");                              // 學年
    String sms = Utility.checkNull(ht.get("SMS"),"");                                  // 學期
    String center_abrcode = Utility.checkNull(ht.get("CENTER_ABRCODE"),"");            // 中心別
    String FACULTY_CODE = Utility.checkNull(ht.get("FACULTY_CODE"),"");            	   // 學系別
    String cmps_code = Utility.checkNull(ht.get("CMPS_CODE"),"");                      // 校區
    String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");                    // 列印方式

    String strPRT = "";
	if(PRT_MANNER.equals("2"))
		strPRT = " AND PAYMENT_STATUS<>'1' ";
	else
    	strPRT = " AND PAYMENT_STATUS in  ('1','2','3','4') ";

    sql.append(
          "SELECT F.CRSNO,C.CRS_NAME,D.STTYPE,H.SEX,(CASE WHEN (D.FTSTUD_ENROLL_AYEARSMS = '"+ayear+sms+"' or D.ENROLL_AYEARSMS = '"+ayear+sms+"') THEN '1' ELSE '2' END) AS NEW_OLD,COUNT(F.STNO) AS SUM_STNO "+
            "FROM (SELECT B.ASYS,A.AYEAR,A.SMS,A.STNO,B.CRSNO,B.ALLOCATE_CMPS_CODE "+
                    "FROM (SELECT AYEAR,SMS,STNO "+
                            "FROM REGT005 "+
                           "WHERE 1=1 AND TAKE_ABNDN ='N' AND AYEAR='"+ayear+"' AND SMS='"+sms+"' "+strPRT+" ) A "+
                    "JOIN (SELECT ASYS,AYEAR,SMS,STNO,CRSNO,ALLOCATE_CMPS_CODE "+
                            "FROM REGT007 "+
                           "WHERE 1=1 AND AYEAR='"+ayear+"' AND SMS='"+sms+"' AND UNQUAL_TAKE_MK='N' AND UNTAKECRS_MK ='N'  "
    );
	if(PRT_MANNER.equals("2")) //已繳費
		  		 sql.append("AND PAYMENT_STATUS != '1' ");
	else
 		 		 sql.append("AND PAYMENT_STATUS in  ('1','2','3','4') ");
	
    if(asys.equals("0") || asys.equals(""))  // 學制
                  sql.append("AND ASYS IN (1, 2) ) B ");
    else
                  sql.append("AND ASYS='"+asys+"') B ");
    sql.append(
                      "ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO) F "+
            "JOIN COUT001 C1 ON C1.AYEAR = F.AYEAR AND C1.SMS = F.SMS AND F.CRSNO=C1.CRSNO "+
            "JOIN COUT002 C ON F.CRSNO=C.CRSNO "+ 		  
            "JOIN STUT003 D ON F.STNO=D.STNO "+
            "JOIN STUT002 H ON D.IDNO=H.IDNO AND D.BIRTHDATE=H.BIRTHDATE "
    );
    if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){  //判斷四次面授課程
        sql.append(" AND C1.open1 = 'Y' ");
    }
    if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){  //判斷多次面授課程
        sql.append(" AND (C1.open3 = 'Y' AND C1.open1 = 'N') ");
    }
    if(!FACULTY_CODE.equals(""))  // 學系別欄位判斷條件
        sql.append(
              "JOIN (SELECT FACULTY_CODE,FACULTY_NAME "+
                      "FROM SYST003 "+
                     "WHERE FACULTY_CODE='"+FACULTY_CODE+"') S3 ON C.FACULTY_CODE=S3.FACULTY_CODE "
        );

    if(!center_abrcode.equals(""))  // 中心別欄位判斷條件
      sql.append(
            "JOIN (SELECT CENTER_CODE,CENTER_ABRCODE "+
                    "FROM SYST002 "+
                   "WHERE CENTER_ABRCODE='"+center_abrcode+"') E ON D.CENTER_CODE=E.CENTER_CODE "
      );
    if(!cmps_code.equals(""))  // 校區別欄位判斷條件
      sql.append(
            "JOIN (SELECT AYEAR,SMS,CENTER_ABRCODE,CMPS_CODE,CMPS_NAME "+
                    "FROM PLAT002 "+
                   "WHERE CMPS_CODE='"+cmps_code+"') G "+
              "ON G.AYEAR=F.AYEAR AND G.SMS=F.SMS "+
             "AND E.CENTER_ABRCODE=G.CENTER_ABRCODE "+
             "AND F.ALLOCATE_CMPS_CODE=G.CMPS_CODE "
      );
    sql.append(
        "GROUP BY F.CRSNO,C.CRS_NAME,D.STTYPE,H.SEX,(CASE WHEN (D.FTSTUD_ENROLL_AYEARSMS = '"+ayear+sms+"' or D.ENROLL_AYEARSMS = '"+ayear+sms+"') THEN '1' ELSE '2' END) "+
        "ORDER BY F.CRSNO,D.STTYPE,H.SEX,(CASE WHEN (D.FTSTUD_ENROLL_AYEARSMS = '"+ayear+sms+"' or D.ENROLL_AYEARSMS = '"+ayear+sms+"') THEN '1' ELSE '2' END) "
    );

    DBResult rs = null;
    try {
      if(pageQuery) {  // 依分頁取出資料
        rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
      }
      else {  // 取出所有資料
        rs = dbmanager.getSimpleResultSet(conn);
        rs.open();
        rs.executeQuery(sql.toString());
      }
      Hashtable rowHt = null;
      while (rs.next()) {
        rowHt = new Hashtable();
        /** 將欄位抄一份過去 */
        for (int i = 1; i <= rs.getColumnCount(); i++)
          rowHt.put(rs.getColumnName(i), rs.getString(i));

        result.add(rowHt);
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if(rs != null) {
        rs.close();
      }
    }

    return result;
  }


  /**
   * REG125R 各中心各科選課人數統計表，取出 科目編號、科目名稱、選課人數
   * 並依學生別、性別分開計算合計
   */
  public Vector getRegt005Regt007Cout002Stut003Syst002Plat002TotForUse(Hashtable ht) throws Exception {
    Vector result = new Vector();
    if(sql.length() > 0) {
      sql.delete(0, sql.length());
    }

    String asys = Utility.checkNull(ht.get("ASYS"),"");                                // 學制
    String ayear = Utility.checkNull(ht.get("AYEAR"),"");                              // 學年
    String sms = Utility.checkNull(ht.get("SMS"),"");                                  // 學期
    String center_abrcode = Utility.checkNull(ht.get("CENTER_ABRCODE"),"");            // 中心別
    String cmps_code = Utility.checkNull(ht.get("CMPS_CODE"),"");                      // 校區
    String FACULTY_CODE = Utility.checkNull(ht.get("FACULTY_CODE"),"");            	   // 學系別
    String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");                    // 列印方式

    String strPRT = "";
	if(PRT_MANNER.equals("2"))
		strPRT = " AND PAYMENT_STATUS<>'1' ";
    else
    	strPRT = " AND PAYMENT_STATUS in  ('1','2','3','4') ";

    sql.append(
       //   "SELECT F.CRSNO,C.CRS_NAME,D.STTYPE,H.SEX,F.NEW_OLD,COUNT(F.STNO) AS SUM_STNO "+
    		"SELECT D.STTYPE,H.SEX,(CASE WHEN (D.FTSTUD_ENROLL_AYEARSMS = '"+ayear+sms+"' or D.ENROLL_AYEARSMS = '"+ayear+sms+"') THEN '1' ELSE '2' END) AS NEW_OLD,COUNT(F.STNO) AS SUM_STNO "+
            "FROM (SELECT B.ASYS,A.AYEAR,A.SMS,A.STNO,B.CRSNO,B.ALLOCATE_CMPS_CODE "+
                    "FROM (SELECT AYEAR,SMS,STNO "+
                            "FROM REGT005 "+
                           "WHERE 1=1 AND TAKE_ABNDN ='N' AND AYEAR='"+ayear+"' AND SMS='"+sms+"' "+strPRT+" ) A "+
                    "JOIN (SELECT ASYS,AYEAR,SMS,STNO,CRSNO,ALLOCATE_CMPS_CODE "+
                            "FROM REGT007 "+
                           "WHERE 1=1 AND AYEAR='"+ayear+"' AND SMS='"+sms+"' "+strPRT+" AND UNQUAL_TAKE_MK='N' AND UNTAKECRS_MK ='N' "
    );
    if(asys.equals("0") || asys.equals(""))  // 學制
                  sql.append("AND ASYS IN (1, 2) ) B ");
    else
                  sql.append("AND ASYS='"+asys+"') B ");
    sql.append(
                      "ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO) F "+
            "JOIN COUT001 C1 ON C1.AYEAR = F.AYEAR AND C1.SMS = F.SMS AND F.CRSNO=C1.CRSNO "+
            "JOIN COUT002 C ON F.CRSNO=C.CRSNO "+
            "JOIN STUT003 D ON F.STNO=D.STNO "+
            "JOIN STUT002 H ON D.IDNO=H.IDNO AND D.BIRTHDATE=H.BIRTHDATE "
    );
    if(!center_abrcode.equals(""))  // 中心別欄位判斷條件
      sql.append(
            "JOIN (SELECT CENTER_CODE,CENTER_ABRCODE "+
                    "FROM SYST002 "+
                   "WHERE CENTER_ABRCODE='"+center_abrcode+"') E ON D.CENTER_CODE=E.CENTER_CODE "
      );
    if(!cmps_code.equals(""))  // 校區別欄位判斷條件
      sql.append(
            "JOIN (SELECT AYEAR,SMS,CENTER_ABRCODE,CMPS_CODE,CMPS_NAME "+
                    "FROM PLAT002 "+
                   "WHERE CMPS_CODE='"+cmps_code+"') G "+
              "ON G.AYEAR=F.AYEAR AND G.SMS=F.SMS "+
             "AND E.CENTER_ABRCODE=G.CENTER_ABRCODE "+
             "AND F.ALLOCATE_CMPS_CODE=G.CMPS_CODE "
      );
    if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
        sql.append(" AND C1.open1 = 'Y' ");      //判斷四次面授課程
    }
    if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
        sql.append(" AND (C1.open3 = 'Y' AND C1.open1 = 'N') ");     //判斷多次面授課程
    }
    if(!FACULTY_CODE.equals(""))  // 學系別欄位判斷條件
        sql.append(
              "JOIN (SELECT FACULTY_CODE,FACULTY_NAME "+
                      "FROM SYST003 "+
                     "WHERE FACULTY_CODE='"+FACULTY_CODE+"') S3 ON C.FACULTY_CODE=S3.FACULTY_CODE "
        );
   /** sql.append(
        "GROUP BY F.CRSNO,C.CRS_NAME,D.STTYPE,H.SEX,F.NEW_OLD "+
        "ORDER BY F.CRSNO,D.STTYPE,H.SEX,F.NEW_OLD "
    );
**/
    sql.append(
            "GROUP BY D.STTYPE,H.SEX,(CASE WHEN (D.FTSTUD_ENROLL_AYEARSMS = '"+ayear+sms+"' or D.ENROLL_AYEARSMS = '"+ayear+sms+"') THEN '1' ELSE '2' END) "+
            "ORDER BY D.STTYPE,H.SEX,(CASE WHEN (D.FTSTUD_ENROLL_AYEARSMS = '"+ayear+sms+"' or D.ENROLL_AYEARSMS = '"+ayear+sms+"') THEN '1' ELSE '2' END) "
        );
    DBResult rs = null;
    try {
      if(pageQuery) {  // 依分頁取出資料
        rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
      }
      else {  // 取出所有資料
        rs = dbmanager.getSimpleResultSet(conn);
        rs.open();
        rs.executeQuery(sql.toString());
      }
      Hashtable rowHt = null;
      while (rs.next()) {
        rowHt = new Hashtable();
        /** 將欄位抄一份過去 */
        for (int i = 1; i <= rs.getColumnCount(); i++)
          rowHt.put(rs.getColumnName(i), rs.getString(i));

        result.add(rowHt);
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if(rs != null) {
        rs.close();
      }
    }

    return result;
  }

   /**
   * 判斷選課繳費狀態:學生首頁使用
   *
   */
  public Hashtable getDataForStuindexs(Hashtable ht) throws Exception {
    Hashtable result = new Hashtable();
    if(sql.length() > 0) {
      sql.delete(0, sql.length());
    }

    String ayear = Utility.checkNull(ht.get("AYEAR"),"");                           // 學年
    String sms = Utility.checkNull(ht.get("SMS"),"");                              // 學期
    String stno = Utility.checkNull(ht.get("STNO"),"");                            // 學號

    DBResult rs = null;
    try{
	    sql.append(
	          "SELECT SUM(NVL(A.PAYABLE_TOTAL_AMT,'0')) AS PAYABLE_TOTAL_AMT, C.PAID_AMT "+
	          "FROM REGT010 A, "+
	          	"(SELECT SUM(NVL(B.PAID_AMT,'0')) AS PAID_AMT,B.AYEAR,B.SMS,B.STNO FROM REGT010 B GROUP BY B.AYEAR,B.SMS,B.STNO) C "+
	          "WHERE "+
	          	"A.AYEAR=C.AYEAR AND "+
	          	"A.SMS=C.SMS AND "+
	          	"A.STNO=C.STNO AND "+
	          	"(A.ABNDN_ORDER<>'Y' OR A.ABNDN_ORDER IS NULL) AND "+
	          	"A.AYEAR='"+ayear+"' AND "+
	          	"A.SMS='"+sms+"' AND "+
	          	"A.STNO='"+stno+"' "+
	          	"GROUP BY C.PAID_AMT "
	    );

	    rs = dbmanager.getSimpleResultSet(conn);
	    rs.open();
	    rs.executeQuery(sql.toString());

	    String PAYMENT_STATUS = ""; // 繳費狀態
	    if(rs.next()){
	    	int PAID_AMT = rs.getInt("PAID_AMT"); // 實付金額
	        int PAYABLE_TOTAL_AMT = rs.getInt("PAYABLE_TOTAL_AMT"); // 選課應付總額

	        if(PAID_AMT==0)
	            PAYMENT_STATUS="未繳";
	        else if(PAYABLE_TOTAL_AMT==PAID_AMT)
	            PAYMENT_STATUS="已繳";
	        else if(PAID_AMT>PAYABLE_TOTAL_AMT)
	            PAYMENT_STATUS="溢繳";
	        else if(PAID_AMT<PAYABLE_TOTAL_AMT)
	            PAYMENT_STATUS="短繳";
	    }

	    result.put("PAYMENT_STATUS", PAYMENT_STATUS);

	    return result;
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if(rs != null) {
        rs.close();
      }
    }
  }


    /**
    by poto
    regt107r sql 之前的getSQL 6
     */
    public Vector getReg107r_1(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT COUNT(A.STNO) TOTAL,C.REDUCE_TYPE,E.CRSNO   \n");//maggie 105下多次面授課程可減免DECODE(D.OPEN1,'Y',C.REDUCE_TYPE,'') AS REDUCE_TYPE
        sql.append("FROM REGT005 A \n");
        //sql.append("JOIN REGT007 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO \n");//不判斷REGT007因多次面授逾期加退選會將原資料刪除再新增，因此取消MAGGIE2018/8/29
        sql.append("LEFT JOIN REGT004 C ON C.AYEAR=A.AYEAR AND C.SMS=A.SMS AND C.STNO=A.STNO  \n");
        sql.append("JOIN REGT010 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.STNO=D.STNO AND D.ABNDN_ORDER = 'N' \n");
        sql.append("JOIN REGT011 E ON D.WRITEOFF_NO = E.WRITEOFF_NO  AND A.AYEAR=E.AYEAR AND A.SMS=E.SMS \n");
        sql.append("JOIN COUT001 F ON F.AYEAR = A.AYEAR  AND F.SMS = A.SMS AND F.CRSNO = E.CRSNO \n");
        sql.append("WHERE 1=1 \n");
        

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("") && !Utility.nullToSpace(ht.get("ASYS")).equals("0")) {
            sql.append("AND D.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後
            if("1".equals(PRT_MANNER)){
            	sql.append("AND A.TAKE_ABNDN = 'N' \n");
            	sql.append("AND NVL(SUBSTR(D.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =D.AYEAR AND R10.SMS =D.SMS AND R10.STNO =D.STNO AND R10.ABNDN_ORDER = 'N' ) \n");
            }
            if("2".equals(PRT_MANNER)){
            	sql.append("AND A.TAKE_ABNDN = 'N' \n");
                sql.append("AND A.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND NVL(SUBSTR(D.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =D.AYEAR AND R10.SMS =D.SMS AND R10.STNO =D.STNO AND R10.ABNDN_ORDER = 'N' AND D.PAYMENT_STATUS != '1' ) \n");
            }
            if("3".equals(PRT_MANNER)){
                sql.append("AND A.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND NVL(SUBSTR(D.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =D.AYEAR AND R10.SMS =D.SMS AND R10.STNO =D.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N' ) \n");
            }
        }
        sql.append("GROUP BY C.REDUCE_TYPE,E.CRSNO \n"); //,D.OPEN1

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }


/**
    by poto
    regt107r sql 之前的getSQL 6
     */
    public Vector getReg107r_2(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT SUM(NVL(E.CRD_FEE,'0')+NVL(E.MISC_FEE,'0')) TOTAL,C.REDUCE_TYPE,E.CRSNO \n");//maggie 105下多次面授課程可減免DECODE(D.OPEN1,'Y',C.REDUCE_TYPE,'') AS REDUCE_TYPE
        sql.append("FROM REGT005 A \n");
        //sql.append("LEFT JOIN REGT007 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO \n");//不判斷REGT007因多次面授逾期加退選會將原資料刪除再新增，因此取消MAGGIE2018/8/29
        sql.append("LEFT JOIN REGT004 C ON C.AYEAR=A.AYEAR AND C.SMS=A.SMS AND C.STNO=A.STNO \n");
        sql.append("JOIN REGT010 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.STNO=D.STNO AND D.ABNDN_ORDER = 'N' \n");
        sql.append("JOIN REGT011 E ON D.WRITEOFF_NO = E.WRITEOFF_NO  AND A.AYEAR=E.AYEAR AND A.SMS=E.SMS \n");
        sql.append("JOIN COUT001 F ON F.AYEAR = A.AYEAR AND F.SMS = A.SMS AND F.CRSNO = E.CRSNO \n");
        sql.append("WHERE 1=1  \n");

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("") && !Utility.nullToSpace(ht.get("ASYS")).equals("0")) {
            sql.append("AND D.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後
            if("1".equals(PRT_MANNER)){
            	sql.append("AND A.TAKE_ABNDN = 'N' \n");
            	sql.append("AND NVL(SUBSTR(D.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =D.AYEAR AND R10.SMS =D.SMS AND R10.STNO =D.STNO AND R10.ABNDN_ORDER = 'N' )  \n");
            }
            if("2".equals(PRT_MANNER)){
            	sql.append("AND A.TAKE_ABNDN = 'N' \n");
                sql.append("AND A.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND (NVL(D.WRITEOFF_NO,0))= (select DISTINCT MAX(NVL(R10.WRITEOFF_NO,0)) FROM REGT010 R10 WHERE R10.AYEAR =D.AYEAR AND R10.SMS =D.SMS AND R10.STNO =D.STNO AND R10.ABNDN_ORDER = 'N' AND D.PAYMENT_STATUS != '1')  \n");
            }
            if("3".equals(PRT_MANNER)){
                sql.append("AND A.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND NVL(SUBSTR(D.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =D.AYEAR AND R10.SMS =D.SMS AND R10.STNO =D.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N' )  \n");
            }
        }
        sql.append("GROUP BY C.REDUCE_TYPE,E.CRSNO \n");//,F.OPEN1

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

    /**
    maggie from poto
    regt189r sql 之前的getSQL 6
     */
    public Vector getReg189r_1(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT COUNT(A.STNO) TOTAL,C.REDUCE_TYPE,B.CRSNO   \n");//maggie 105下多次面授課程可減免DECODE(D.OPEN1,'Y',C.REDUCE_TYPE,'') AS REDUCE_TYPE
        sql.append("FROM REGT005 A \n");
        sql.append("JOIN REGT007 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO \n");
        sql.append("LEFT JOIN REGT004 C ON C.AYEAR=A.AYEAR AND C.SMS=A.SMS AND C.STNO=A.STNO  \n");
        sql.append("JOIN COUT001 D ON D.AYEAR = A.AYEAR  AND D.SMS = A.SMS AND D.CRSNO = B.CRSNO \n");
        sql.append("join plat002 e on e.ayear = b.ayear and e.sms = b.sms and e.center_abrcode = substr(b.tut_class_code,1,1) and e.cmps_code = b.tut_cmps_code ");
        sql.append("WHERE 1=1 \n");
        sql.append("AND A.TAKE_ABNDN = 'N' AND B.UNQUAL_TAKE_MK = 'N' AND B.UNTAKECRS_MK = 'N' \n");

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("") && !Utility.nullToSpace(ht.get("ASYS")).equals("0")) {
            sql.append("AND B.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_ABRCODE")).equals("")) {
            sql.append("AND SUBSTR(B.TUT_CLASS_CODE,1,1) = '" + Utility.nullToSpace(ht.get("CENTER_ABRCODE")) + "' \n");
        }
		if(!Utility.nullToSpace(ht.get("CMPS_CODE")).equals("")) {
            sql.append("AND B.TUT_CMPS_CODE = '" + Utility.nullToSpace(ht.get("CMPS_CODE")) + "' \n");
        }
		if(!Utility.nullToSpace(ht.get("CMPS_NAME")).equals("")) {
            sql.append("AND E.CMPS_NAME like '%" + Utility.nullToSpace(ht.get("CMPS_NAME")) + "%' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後
            if("2".equals(PRT_MANNER)){
                sql.append("AND A.PAYMENT_STATUS IN ('2','3','4') \n");
            }
        }
        sql.append("GROUP BY C.REDUCE_TYPE,B.CRSNO \n"); //,D.OPEN1

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }


/**
    maggie from poto
    regt189r sql 之前的getSQL 6
     */
    public Vector getReg189r_2(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT SUM(NVL(E.CRD_FEE,'0')+NVL(E.MISC_FEE,'0')) TOTAL,C.REDUCE_TYPE,B.CRSNO \n");//maggie 105下多次面授課程可減免DECODE(D.OPEN1,'Y',C.REDUCE_TYPE,'') AS REDUCE_TYPE
        sql.append("FROM REGT005 A \n");
        sql.append("JOIN REGT007 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO \n");
        sql.append("LEFT JOIN REGT004 C ON C.AYEAR=A.AYEAR AND C.SMS=A.SMS AND C.STNO=A.STNO \n");
        //取最大有效銷帳編號對應科目繳費資訊
        //sql.append("JOIN REGT010 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.STNO=D.STNO AND NVL(SUBSTR(D.WRITEOFF_NO,7),0) = (select MAX(NVL(SUBSTR(AA.WRITEOFF_NO,7),0)  ) from regt010 AA where AA.AYEAR=D.AYEAR AND AA.SMS=D.SMS AND AA.STNO=D.STNO AND AA.ABNDN_ORDER = 'N' AND AA.PAYMENT_STATUS != '1' ) \n");
        sql.append("JOIN REGT010 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.STNO=D.STNO AND NVL(D.WRITEOFF_NO,0) = (select MAX(NVL(AA.WRITEOFF_NO,0)  ) from regt010 AA where AA.AYEAR=D.AYEAR AND AA.SMS=D.SMS AND AA.STNO=D.STNO AND AA.ABNDN_ORDER = 'N' ) \n");
        sql.append("LEFT JOIN REGT011 E ON D.WRITEOFF_NO = E.WRITEOFF_NO  AND B.CRSNO = E.CRSNO AND A.AYEAR=E.AYEAR AND A.SMS=E.SMS \n");
        sql.append("JOIN COUT001 F ON F.AYEAR = A.AYEAR AND F.SMS = A.SMS AND F.CRSNO = B.CRSNO \n");
        sql.append("join plat002 g on g.ayear = b.ayear and g.sms = b.sms and g.center_abrcode = substr(b.tut_class_code,1,1) and g.cmps_code = b.tut_cmps_code \n");
        sql.append("WHERE 1=1  \n");
        sql.append("AND A.TAKE_ABNDN = 'N' AND B.UNQUAL_TAKE_MK = 'N' AND B.UNTAKECRS_MK = 'N' \n");



        if(!Utility.nullToSpace(ht.get("ASYS")).equals("") && !Utility.nullToSpace(ht.get("ASYS")).equals("0")) {
            sql.append("AND B.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("CENTER_ABRCODE")).equals("")) {
            sql.append("AND SUBSTR(B.TUT_CLASS_CODE,1,1) = '" + Utility.nullToSpace(ht.get("CENTER_ABRCODE")) + "' \n");
        }
		if(!Utility.nullToSpace(ht.get("CMPS_CODE")).equals("")) {
            sql.append("AND B.TUT_CMPS_CODE = '" + Utility.nullToSpace(ht.get("CMPS_CODE")) + "' \n");
        }
		if(!Utility.nullToSpace(ht.get("CMPS_NAME")).equals("")) {
            sql.append("AND G.CMPS_NAME like '%" + Utility.nullToSpace(ht.get("CMPS_NAME")) + "%' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後
            if("2".equals(PRT_MANNER)){
                sql.append("AND A.PAYMENT_STATUS IN ('2','3','4') \n");
            }
        }
        sql.append("GROUP BY C.REDUCE_TYPE,B.CRSNO \n");//,F.OPEN1

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

/**
    by poto

     */
    public Vector getPcs110r_1(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT COUNT(1) TOTAL,NVL(SUM(A.MAG_FEE) ,'0') MAG_FEE     \n");
        sql.append("FROM REGT005 B     \n");
        sql.append("JOIN REGT010 A ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO    \n");
        sql.append("WHERE 1=1   \n");
        sql.append("AND B.MAG_ORDER_MK='Y'   \n");   

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND A.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後 3出納對帳用
            if("1".equals(PRT_MANNER)){
                sql.append("AND B.TAKE_ABNDN = 'N' \n");
                sql.append("AND NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND R10.ABNDN_ORDER = 'N' )  \n");
            }
            if("2".equals(PRT_MANNER)){
            	sql.append("AND B.TAKE_ABNDN = 'N' \n");
                sql.append("AND B.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND (NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO　AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N' )  \n");
                sql.append("OR SUBSTR(A.WRITEOFF_NO,1,4) = '5293' AND A.ABNDN_ORDER = 'N' AND A.PAYMENT_STATUS != '1')  \n");
            }
            if("3".equals(PRT_MANNER)){
                sql.append("AND B.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N' )  \n");
            }
        }


        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }


/**
    by poto

     */
    public Vector getPcs110r_2(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT COUNT(A.STNO)TOTAL,SUM(A.LAB_FEE)LAB_FEE  \n");
        sql.append("FROM REGT005 C \n");
        sql.append("JOIN REGT010 A ON C.AYEAR=A.AYEAR AND C.SMS=A.SMS AND C.STNO=A.STNO \n");
        sql.append("JOIN REGT007 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.ASYS=B.ASYS AND A.STNO=B.STNO  \n"); 
        sql.append("WHERE 1=1 \n");

        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND A.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND C.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND C.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後 3出納對帳用
            if("1".equals(PRT_MANNER)){
                sql.append("AND C.TAKE_ABNDN='N'  AND B.UNQUAL_TAKE_MK = 'N' AND B.UNTAKECRS_MK = 'N' AND NVL(A.LAB_FEE,'0') <> 0  \n");
                sql.append("AND NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND R10.ABNDN_ORDER = 'N' )  \n");
            }
            if("2".equals(PRT_MANNER)){
            	sql.append("AND C.TAKE_ABNDN='N'  AND B.UNQUAL_TAKE_MK = 'N' AND B.UNTAKECRS_MK = 'N' AND NVL(A.LAB_FEE,'0') <> 0  \n");
            	sql.append("AND C.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND (NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N'  )  \n");
                sql.append("OR SUBSTR(A.WRITEOFF_NO,1,4) = '5293' AND A.ABNDN_ORDER = 'N' AND A.PAYMENT_STATUS != '1') \n");
            }
            if("3".equals(PRT_MANNER)){
                sql.append("AND C.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N' )  \n");
            }
        }


        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }



/**
    by poto

     */
    public Vector getPcs110r_3(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append("SELECT \n");
        sql.append("SUM(A.CRD_FEE+A.MISC_FEE) CRD_FEE \n");
        sql.append("FROM REGT005 B  \n");
        sql.append("JOIN REGT010 A ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.STNO=B.STNO  \n");
        sql.append("WHERE 1=1  \n");
        
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND A.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        }
        if(!Utility.nullToSpace(ht.get("PRT_MANNER")).equals("")) {
            String PRT_MANNER =  Utility.nullToSpace(ht.get("PRT_MANNER"));
            //1 繳費前 2繳費後 3出納對帳用
            if("1".equals(PRT_MANNER)){
            	sql.append("AND B.TAKE_ABNDN='N' \n");
            	sql.append("AND NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND R10.ABNDN_ORDER = 'N'   ) \n");
            }
            if("2".equals(PRT_MANNER)){
            	sql.append("AND B.TAKE_ABNDN='N' \n");
                sql.append("AND B.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND (NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N'  )  \n");
                sql.append("OR SUBSTR(A.WRITEOFF_NO,1,4) = '5293' AND A.ABNDN_ORDER = 'N' AND A.PAYMENT_STATUS != '1') \n");
            }
            if("3".equals(PRT_MANNER)){
                sql.append("AND B.PAYMENT_STATUS IN ('2','3','4') \n");
                sql.append("AND NVL(SUBSTR(A.WRITEOFF_NO,7),0)= (select MAX(NVL(SUBSTR(R10.WRITEOFF_NO,7),0)) FROM REGT010 R10 WHERE R10.AYEAR =A.AYEAR AND R10.SMS =A.SMS AND R10.STNO =A.STNO AND SUBSTR(R10.WRITEOFF_NO,1,4) != '5293' AND R10.ABNDN_ORDER = 'N'   ) \n");
            }
        }


        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "R05." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

	public Vector getDataForREG131R(Hashtable ht) throws Exception {        
        Vector result = new Vector();
        
        // 該學系如沒有任何一個中心選的話,也要出來,因此用syst008做為主檔
        String sql = 
        	"select distinct b.CENTER_CODE,b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE,a.FACULTY_ABBRNAME,  "+
	            "sum (case when c.ayear is not null then 1 else 0 end) OVER(PARTITION BY b.CENTER_CODE,b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE) as CENTER_FACULTY_NUM, "+
	            "sum (case when c.ayear is not null then 1 else 0 end) OVER(PARTITION BY b.CENTER_CODE) as CENTER_NUM, "+
	            "sum (case when c.ayear is not null then 1 else 0 end) OVER(PARTITION BY b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE) as FACULTY_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 1 else 0 end)  OVER(PARTITION BY b.CENTER_CODE,b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE) as CENTER_FACULTY_NEW_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 0 when c.ayear is not null then 1 else 0 end)  OVER(PARTITION BY b.CENTER_CODE,b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE) as CENTER_FACULTY_OLD_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 1 else 0 end)  OVER(PARTITION BY b.CENTER_CODE) as CENTER_NEW_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 0 when c.ayear is not null then 1 else 0 end)  OVER(PARTITION BY b.CENTER_CODE) as CENTER_OLD_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 1 else 0 end)  OVER(PARTITION BY b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE) as FACULTY_NEW_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 0 when c.ayear is not null then 1 else 0 end)  OVER(PARTITION BY b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE) as FACULTY_OLD_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 1 else 0 end)  OVER() as NEW_NUM, "+
	            "sum(case when B.ENROLL_AYEARSMS=c.AYEAR||c.SMS then 0 when c.ayear is not null then 1 else 0 end)  OVER() as OLD_NUM, "+
	            "sum (case when c.ayear is not null then 1 else 0 end) OVER() as NUM "+
	        "from syst008 a "+
	        "left join stut003 b on a.FACULTY_CODE||a.TOTAL_CRS_NO=b.PRE_MAJOR_FACULTY||b.J_FACULTY_CODE "+
	        "left join regt005 c on b.STNO=c.STNO "+
	        "and c.AYEAR='"+ht.get("AYEAR")+"' "+
	        "and c.SMS='"+ht.get("SMS")+"' "+
	        "and c.TAKE_ABNDN='N' "+
	        (ht.get("PRT_MANNER").toString().equals("2")?"and c.PAYMENT_STATUS != '1' ":"")+
	        "where 1=1 "+	        
	        "and b.ASYS='2' "+
	        "order by b.PRE_MAJOR_FACULTY,b.J_FACULTY_CODE,b.CENTER_CODE ";
        
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
	
	
	//原住民年齡層統計表
	public void getsgu803rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
        sql.append
		(       
				
				"SELECT A.STTYPE aS STTYPE, A.SEX AS SEX, "+
			 	"SUM( CASE WHEN AGE>=18 AND AGE <=20 THEN 1 ELSE 0 END ) B1820, "+  
			 	"SUM( CASE WHEN AGE>=21 AND AGE <=25 THEN 1 ELSE 0 END ) B2125, "+  
			 	"SUM( CASE WHEN AGE>=26 AND AGE <=30 THEN 1 ELSE 0 END ) B2630, "+ 
			 	"SUM( CASE WHEN AGE>=31 AND AGE <=35 THEN 1 ELSE 0 END ) B3135, "+  
			 	"SUM( CASE WHEN AGE>=36 AND AGE <=40 THEN 1 ELSE 0 END ) B3640, "+  
			 	"SUM( CASE WHEN AGE>=41 AND AGE <=45 THEN 1 ELSE 0 END ) B4145, "+  
			 	"SUM( CASE WHEN AGE>=46 AND AGE <=50 THEN 1 ELSE 0 END ) B4650, "+  
			 	"SUM( CASE WHEN AGE>=51 AND AGE <=55 THEN 1 ELSE 0 END ) B5155, "+  
			 	"SUM( CASE WHEN AGE>=56 AND AGE <=60 THEN 1 ELSE 0 END ) B5660, "+  
			 	"SUM( CASE WHEN AGE>=61 AND AGE <=65 THEN 1 ELSE 0 END ) B6165, "+  
			 	"SUM( CASE WHEN AGE>=66 AND AGE <=70 THEN 1 ELSE 0 END ) B6670, "+  
			 	"SUM( CASE WHEN AGE>=71 AND AGE <=75 THEN 1 ELSE 0 END ) B7175, "+  
			 	"SUM( CASE WHEN AGE>=76 AND AGE <=80 THEN 1 ELSE 0 END ) B7680, "+  
			 	"SUM( CASE WHEN AGE>=81 THEN 1 ELSE 0 END ) U81, "+  
			 	"COUNT(AGE) AS TOTAL_COUNT "+   
			 "FROM  "+
			 "	(	SELECT S1.CODE AS STTYPE, S2.CODE  AS SEX   "+
			 "		FROM SYST001 S1, SYST001 S2   "+
			 "		WHERE s1.KIND = 'STTYPE' AND s2.KIND = 'SEX' AND S1.CODE != '4'   "+
			 "	) A 	  "+
			 "		LEFT JOIN (	SELECT b.STTYPE, C.SEX,  "+
			 "						floor((TO_date(D.ENROLL_BASEDATE, 'yyyy/mm/dd') -   "+
			 "						to_date(   "+
			 "						CASE   "+
			 "							WHEN C.birthdate IN ('19550229','19590229')   "+
			 "							THEN substr(C.birthdate, 0, 4)||'0301'   "+
			 "							ELSE C.birthdate   "+
			 "						END, 'yyyy/mm/dd')) / 365) AS AGE   "+
			 "					FROM REGT005 a   "+
			 "						JOIN STUT003 b ON a.STNO=b.STNO   "+
			 "						JOIN STUT002 C ON b.IDNO=C.IDNO AND b.BIRTHDATE=C.BIRTHDATE "+
				"					JOIN SGUT004 e ON a.STNO=e.STNO AND e.HAND_NATIVE='1' AND AUDIT_MK='2'   "+
			 	"					LEFT JOIN SYST005 d ON d.ayear = a.ayear AND d.sms =a.sms   "+
			 	"				WHERE a.TAKE_ABNDN='N' AND PAYMENT_STATUS != '1'  "

			 	

				

		);

		    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
		         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		     }
	        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
		            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' )");
		     }
			
	        sql.append("M "+
	        		   "ON A.SEX = M.SEX AND A.STTYPE = M.STTYPE "+
	        		   "group by A.STTYPE,A.SEX "+
	        		   "order by A.STTYPE,A.SEX ");


	        
	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    
	
	
	
	//原住民統計表-獎學金人數及金額
	public void getsgu804rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
			sql.append
			(
					"select d.CENTER_CODE AS CENTER_CODE ,d.STTYPE as STTYPE,e.SEX as SEX,count(1) as TOTAL_COUNT,SUM(C.SCHOLAR_AMT) AS TOTAL_SCHOLAR_AMT  "+
					"from REGT005 a "+
					"join SGUT004 b on b.STNO=a.STNO and b.HAND_NATIVE = '1' and b.AUDIT_MK = '2' "+
					"join SGUT003 c on c.AYEAR=a.AYEAR and c.SMS=a.SMS and c.STNO=a.STNO and c.RE_AUDIT_RESULT='2' and c.SCHOLAR_TYPE_CODE='04' and c.award_mk = 'Y' "+
					"join STUT003 d on d.STNO=a.STNO "+
					"join STUT002 e on e.IDNO=d.IDNO "+
					"where a.TAKE_ABNDN = 'N' and a.PAYMENT_STATUS != '1' "
			);

		    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
		         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		     }
	        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
		            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		     }
			
	        sql.append("group by d.CENTER_CODE,d.STTYPE,e.SEX "+
	        		   "order by d.CENTER_CODE,d.STTYPE,e.SEX ");
	        
	        
	        
	        
	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    
	
	
//身心障礙統計表
	public void getsgu806rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
        sql.append
		(
				"select c.CENTER_CODE AS CENTER_CODE ,c.STTYPE as STTYPE,count(1) as TOTAL_COUNT "+
				"from REGT005 a "+
				"join SGUT004 b on b.STNO=a.STNO and b.HAND_NATIVE='2' and b.AUDIT_MK='2' "+
				"join STUT003 c on c.STNO=a.STNO "+
				"join STUT002 d on d.IDNO=c.IDNO "+
				"where a.TAKE_ABNDN = 'N' and a.PAYMENT_STATUS != '1' "

		);

	    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
	         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	     }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
	            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	     }
		
        sql.append("group by c.CENTER_CODE,c.STTYPE  "+
        		   "order by c.CENTER_CODE,c.STTYPE ");
	        
	        
	        
	        
	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    
	
	
	//身心障礙統計表-障礙程度
	public void getsgu807rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
        sql.append
		(
				"select b.HANDICAP_GRADE as HANDICAP_GRADE ,c.STTYPE as STTYPE,count(1)as TOTAL_COUNT "+
				"from REGT005 a "+
				"join SGUT004 b on b.STNO=a.STNO and b.HAND_NATIVE='2' and b.AUDIT_MK='2' "+
				"join STUT003 c on c.STNO=a.STNO "+
				"join STUT002 d on d.IDNo=c.IDNO "+
				"where  a.TAKE_ABNDN='N' and a.PAYMENT_STATUS !='1' "



		);

	    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
	         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	     }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
	            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	     }
		
        sql.append("group by b.HANDICAP_GRADE,c.STTYPE  "+
        		   "order by b.HANDICAP_GRADE,c.STTYPE ");
	        
	        
	        
	        
	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    
	
	
	//身心障礙年齡層統計表
	public void getsgu808rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
        sql.append
		(       
				
				"SELECT A.STTYPE AS STTYPE, "+
			 	"SUM( CASE WHEN AGE>=18 AND AGE <=20 THEN 1 ELSE 0 END ) B1820, "+  
			 	"SUM( CASE WHEN AGE>=21 AND AGE <=25 THEN 1 ELSE 0 END ) B2125, "+  
			 	"SUM( CASE WHEN AGE>=26 AND AGE <=30 THEN 1 ELSE 0 END ) B2630, "+ 
			 	"SUM( CASE WHEN AGE>=31 AND AGE <=35 THEN 1 ELSE 0 END ) B3135, "+  
			 	"SUM( CASE WHEN AGE>=36 AND AGE <=40 THEN 1 ELSE 0 END ) B3640, "+  
			 	"SUM( CASE WHEN AGE>=41 AND AGE <=45 THEN 1 ELSE 0 END ) B4145, "+  
			 	"SUM( CASE WHEN AGE>=46 AND AGE <=50 THEN 1 ELSE 0 END ) B4650, "+  
			 	"SUM( CASE WHEN AGE>=51 AND AGE <=55 THEN 1 ELSE 0 END ) B5155, "+  
			 	"SUM( CASE WHEN AGE>=56 AND AGE <=60 THEN 1 ELSE 0 END ) B5660, "+  
			 	"SUM( CASE WHEN AGE>=61 AND AGE <=65 THEN 1 ELSE 0 END ) B6165, "+  
			 	"SUM( CASE WHEN AGE>=66 AND AGE <=70 THEN 1 ELSE 0 END ) B6670, "+  
			 	"SUM( CASE WHEN AGE>=71 AND AGE <=75 THEN 1 ELSE 0 END ) B7175, "+  
			 	"SUM( CASE WHEN AGE>=76 AND AGE <=80 THEN 1 ELSE 0 END ) B7680, "+  
			 	"SUM( CASE WHEN AGE>=81 THEN 1 ELSE 0 END ) U81, "+  
			 	"COUNT(AGE) AS TOTAL_COUNT "+   
			 "FROM  "+
			 "	(	SELECT S1.CODE AS STTYPE  "+
			 "		FROM SYST001 S1 "+
			 "		WHERE s1.KIND = 'STTYPE'  AND S1.CODE != '4'   "+
			 "	) A 	  "+
			 "		LEFT JOIN (	SELECT b.STTYPE,   "+
			 "						floor((TO_date(D.ENROLL_BASEDATE, 'yyyy/mm/dd') -   "+
			 "						to_date(   "+
			 "						CASE   "+
			 "							WHEN C.birthdate IN ('19550229','19590229')   "+
			 "							THEN substr(C.birthdate, 0, 4)||'0301'   "+
			 "							ELSE C.birthdate   "+
			 "						END, 'yyyy/mm/dd')) / 365) AS AGE   "+
			 "					FROM REGT005 a   "+
			 "						JOIN STUT003 b ON a.STNO=b.STNO   "+
			 "						JOIN STUT002 C ON b.IDNO=C.IDNO AND b.BIRTHDATE=C.BIRTHDATE "+
				"					JOIN SGUT004 e ON a.STNO=e.STNO AND e.HAND_NATIVE='2' AND AUDIT_MK='2'   "+
			 	"					LEFT JOIN SYST005 d ON d.ayear = a.ayear AND d.sms =a.sms   "+
			 	"				WHERE a.TAKE_ABNDN='N' AND PAYMENT_STATUS != '1'  "

			 	

				

		);

		    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
		         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		     }
	        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
		            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' )");
		     }
			
	        sql.append("M "+
	        		   "ON A.STTYPE = M.STTYPE "+
	        		   "group by A.STTYPE "+
	        		   "order by A.STTYPE ");


	        
	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    
	

	//身心障礙類別及程度人數統計表
	public void getsgu811rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
        sql.append
		(
				
				"select "+
				"M.HANDICAP_TYPE,M.HANDICAP_GRADE,M.GRADE_name, "+
				"sum(case when D.asys ='1' then 1 else 0 end ) as NOU, "+
				"sum(case when D.asys ='2' then 1 else 0 end) as SNOU, "+
				"(sum(case when D.asys ='1' then 1 else 0 end ) + sum(case when D.asys ='2' then 1 else 0 end) -count(unique D.idno) ) AS NOUANDSNOU "+
				"from "+ 
				"(  "+
				"    select  "+
				"    b.code as HANDICAP_TYPE,a.code as HANDICAP_GRADE,b.code_NAME||a.code_NAME as GRADE_name "+
				"    from syst001 a,syst001 b where a.KIND = 'HANDICAP_GRADE'  and b.KIND ='HANDICAP_TYPE' "+
				"    order by HANDICAP_TYPE,HANDICAP_GRADE desc "+
				") M "+
				"left join "+
				"( "+
				"    select c.idno,c.asys,b.HANDICAP_GRADE as HANDICAP_GRADE,b.HANDICAP_TYPE as HANDICAP_TYPE  "+
				"    from REGT005 a  "+
				"    join SGUT004 b on b.STNO=a.STNO and b.HAND_NATIVE='2' and b.AUDIT_MK='2'  "+
				"    join STUT003 c on c.STNO=a.STNO  "+
				"    join STUT002 d on d.IDNo=c.IDNO  "+
				"    where  a.TAKE_ABNDN='N' and a.PAYMENT_STATUS !='1'   "


		);

	    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
	         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	     }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
	            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	     }
		
        sql.append(") D on M.HANDICAP_GRADE = D.HANDICAP_GRADE AND M.HANDICAP_TYPE = D.HANDICAP_TYPE "+
        		   "WHERE 1=1  "+
        		   "GROUP BY M.HANDICAP_TYPE,M.HANDICAP_GRADE,M.GRADE_name "+
        		   "ORDER BY M.HANDICAP_TYPE,M.HANDICAP_GRADE desc,M.GRADE_name ");
	        
	        
	        
	   
	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    

	
	//身心障礙統計表-獎、助學金
public void getsgu809rPrint(Vector vt, Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
    
			sql.append
			(
					"SELECT "+
					"M.HANDICAP_GRADE_NAME, "+
					"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='1' THEN '1' ELSE '0' END) AS NOU_COUNT_1, "+
					"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='1' THEN D.SCHOLAR_AMT ELSE 0 END) AS NOU_AMT_1, "+
					"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='2' THEN '1' ELSE '0' END) AS SNOU_COUNT_1, "+
					"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='2' THEN D.SCHOLAR_AMT ELSE 0 END) AS SNOU_AMT_1, "+
					"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='1' THEN '1' ELSE '0' END) AS NOU_COUNT_2, "+
					"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='1' THEN D.SCHOLAR_AMT ELSE 0 END) AS NOU_AMT_2, "+
					"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='2' THEN '1' ELSE '0' END) AS SNOU_COUNT_2, "+
					"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='2' THEN D.SCHOLAR_AMT ELSE 0 END) AS SNOU_AMT_2 "+
					"FROM  "+
					"(SELECT CODE AS HANDICAP_GRADE,CODE_NAME AS HANDICAP_GRADE_NAME FROM SYST001 WHERE KIND = 'HANDICAP_GRADE') M "+
					"LEFT JOIN( "+
					"    SELECT C.ASYS,E.SCHOLAR_TYPE,E.HANDICAP_GRADE,NVL(E.SCHOLAR_AMT,'0') AS SCHOLAR_AMT "+
					"    FROM SGUT003 E "+
					"    JOIN SGUT004 B ON B.STNO=E.STNO AND B.HAND_NATIVE='2' AND B.AUDIT_MK='2'  "+
					"    JOIN STUT003 C ON C.STNO=E.STNO  "+
					"    JOIN STUT002 D ON D.IDNO=C.IDNO AND D.BIRTHDATE = C.BIRTHDATE "+
					"    WHERE  1=1  "+
					"	 AND E.SCHOLAR_TYPE_CODE ='03' " +
					"	 AND E.RE_AUDIT_RESULT ='2' " +
					"	 AND E.AWARD_MK = 'Y'	"+
					" 	 AND E.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' "+
					" 	 AND E.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' "+
					") D ON M.HANDICAP_GRADE = D.HANDICAP_GRADE "+
        		    "group by M.HANDICAP_GRADE,M.HANDICAP_GRADE_NAME "+
        		    "order by M.HANDICAP_GRADE,M.HANDICAP_GRADE_NAME "
			);

	        DBResult rs = null;
	        try {
	            // 取出所有資料
	            rs = dbmanager.getSimpleResultSet(conn);
	            rs.open();
	            rs.executeQuery(sql.toString());
	            Hashtable rowHt = null;
	            while (rs.next()) {
	                rowHt = new Hashtable();
	                /** 將欄位抄一份過去 */
	                for (int i = 1; i <= rs.getColumnCount(); i++)
	                    rowHt.put(rs.getColumnName(i), rs.getString(i));

	                vt.add(rowHt);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if(rs != null) {
	                rs.close();
	            }
	        }
	    }    

//新制身心障礙統計表-獎、助學金
public void getsgu817rPrint(Vector vt, Hashtable ht) throws Exception {
    if(ht == null) {
        ht = new Hashtable();
    }
    if(sql.length() > 0) {
        sql.delete(0, sql.length());
    }

		sql.append
		(
				"SELECT "+
				"M.HANDICAP_GRADE_NAME, "+
				"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='1' THEN '1' ELSE '0' END) AS NOU_COUNT_1, "+
				"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='1' THEN D.SCHOLAR_AMT ELSE 0 END) AS NOU_AMT_1, "+
				"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='2' THEN '1' ELSE '0' END) AS SNOU_COUNT_1, "+
				"SUM(CASE WHEN D.ASYS='1' AND D.SCHOLAR_TYPE ='2' THEN D.SCHOLAR_AMT ELSE 0 END) AS SNOU_AMT_1, "+
				"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='1' THEN '1' ELSE '0' END) AS NOU_COUNT_2, "+
				"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='1' THEN D.SCHOLAR_AMT ELSE 0 END) AS NOU_AMT_2, "+
				"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='2' THEN '1' ELSE '0' END) AS SNOU_COUNT_2, "+
				"SUM(CASE WHEN D.ASYS='2' AND D.SCHOLAR_TYPE ='2' THEN D.SCHOLAR_AMT ELSE 0 END) AS SNOU_AMT_2 "+
				"FROM  "+
				"(SELECT CODE AS HANDICAP_GRADE,CODE_NAME AS HANDICAP_GRADE_NAME FROM SYST001 WHERE KIND = 'HANDICAP_GRADE') M "+
				"LEFT JOIN( "+
				"    SELECT C.ASYS,E.SCHOLAR_TYPE,E.HANDICAP_GRADE,NVL(E.SCHOLAR_AMT,'0') AS SCHOLAR_AMT "+
				"    FROM SGUT003 E "+
				"    JOIN SGUT004 B ON B.STNO=E.STNO AND B.HAND_NATIVE='2' AND B.AUDIT_MK='2'  "+
				"    JOIN STUT003 C ON C.STNO=E.STNO  "+
				"    JOIN STUT002 D ON D.IDNO=C.IDNO AND D.BIRTHDATE = C.BIRTHDATE "+
				"    WHERE  1=1  "+
				"	 AND E.SCHOLAR_TYPE_CODE in ('06','07') " +
				"	 AND E.RE_AUDIT_RESULT ='2' " +
				"	 AND E.AWARD_MK = 'Y'	"+
				" 	 AND E.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' "+
				" 	 AND E.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' "+
				") D ON M.HANDICAP_GRADE = D.HANDICAP_GRADE "+
    		    "group by M.HANDICAP_GRADE,M.HANDICAP_GRADE_NAME "+
    		    "order by M.HANDICAP_GRADE,M.HANDICAP_GRADE_NAME "
		);

        DBResult rs = null;
        try {
            // 取出所有資料
            rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                vt.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
    } 

//原住民統計表-申請學分學雜費減免
public void getsgu805rPrint(Vector vt, Hashtable ht) throws Exception {
    if(ht == null) {
        ht = new Hashtable();
    }
    if(sql.length() > 0) {
        sql.delete(0, sql.length());
    }

		sql.append
		(
				
				"select b.center_code as CENTER_CODE,b.STTYPE as STTYPE,c.SEX as SEX,count(1)as TOTAL_COUNT,sum(g.REDUCE_FEE) as TOTAL_REDUCE_FEE "+
				"from regt005 a  "+  
				"join stut003 b on a.stno = b.stno    "+
				"join stut002 c on b.IDNO = c.IDNO and b.BIRTHDATE = c.BIRTHDATE   "+ 
				"join sgut004 d on b.stno = d.stno and d.HAND_NATIVE = '1' and d.AUDIT_MK = '2'    "+
				"join syst001 e on e.kind = 'ORIGIN_RACE' and d.PARENTS_RACE = e.CODE  and e.CODE != '99'   "+
				"join regt004 f on a.ayear =f.ayear and a.sms =f.sms and a.stno =f.stno and f.AUDIT_STATUS = '1' and f.REDUCE_TYPE = '05' "+
				"join ( "+
				"    select r10.ayear,r10.sms,r10.stno,sum(r10.REDUCE_FEE) as REDUCE_FEE from regt010 r10 " +
				"    where r10.ABNDN_ORDER = 'N' " +
				"	 and ( "+
				"             exists( "+
				"                select 1 from regt010 r101  "+
				"                where r101.OVER_TAKE_MK ='N' and trim(r101.PAID_AMT) is not null "+
				"                and r101.ayear = r10.ayear and r101.sms = r10.sms and r101.stno = r10.stno "+                               
				"             ) "+
				"             or r10.OVER_TAKE_MK ='Y' "+ 
				"         )	 "+
				"group by r10.ayear,r10.sms,r10.stno "+
				") g on a.ayear =g.ayear and a.sms =g.sms and a.stno =g.stno "+
				"where a.TAKE_ABNDN = 'N' and a.PAYMENT_STATUS != '1'  "
		);

	    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
	         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	     }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
	            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	     }
		
        sql.append("group by b.center_code,b.STTYPE,c.SEX "+
        		   "order by b.center_code,b.STTYPE,c.SEX ");
        
        
        
        
        DBResult rs = null;
        try {
            // 取出所有資料
            rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                vt.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
    }    


//身心障礙統計表-申請學分學雜費減免
public void getsgu810rPrint(Vector vt, Hashtable ht) throws Exception {
    if(ht == null) {
        ht = new Hashtable();
    }
    if(sql.length() > 0) {
        sql.delete(0, sql.length());
    }

    sql.append
	(
			
			"select b.asys as ASYS,count(f.stno) as COUNT_APPLY , count(d.stno) as COUNT_TOTALSUM "+
			"from regt005 a   "+ 
			"join stut003 b on a.stno = b.stno   "+ 
			"join stut002 c on b.IDNO = c.IDNO and b.BIRTHDATE = c.BIRTHDATE    "+
			"join sgut004 d on b.stno = d.stno and d.HAND_NATIVE = '2' and d.AUDIT_MK = '2'    "+
			"left join regt004 f on a.ayear =f.ayear and a.sms =f.sms and a.stno =f.stno and f.AUDIT_STATUS = '1' and f.REDUCE_TYPE in('01','02','03','15','16','17')  "+
			"where a.TAKE_ABNDN = 'N' and a.PAYMENT_STATUS != '1'   "

	);

    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
     }
    if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
     }
	
    sql.append("group by b.asys "+
    		   "order by b.asys ");
        
        
        
        
        DBResult rs = null;
        try {
            // 取出所有資料
            rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                vt.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
    }    

	
	//身心障礙手冊後續鑑定需求明細表
	public void getsgu812rPrint(Vector vt, Hashtable ht) throws Exception {
	    if(ht == null) {
	        ht = new Hashtable();
	    }
	    if(sql.length() > 0) {
	        sql.delete(0, sql.length());
	    }
	
	    sql.append
		(
				
			"select a.AYEAR,a.SMS,e.CODE_NAME as SMA_NAME,c.CENTER_CODE,f.CENTER_ABBRNAME AS CENTER_NAME,c.STNO,d.NAME,d.SEX,g.CODE_NAME AS SEX_NAME,c.IDNO, "+
			"b.HANDICAP_GRADE,i.CODE_NAME AS GRADE_NAME,b.HANDICAP_TYPE,h.CODE_NAME AS TYPE_NAME,b.IDENTIFY_DATE,b.NEXT_IDENTIFY_DATE "+
			       
			"From REGT005 a "+
			"join SGUT004 b on b.STNO=a.STNO and b.HAND_NATIVE='2' and b.AUDIT_MK='2' "+
			"join STUT003 c on c.STNO=a.STNO "+
			"join STUT002 d on d.IDNO=c.IDNO "+
			"join SYST001 e on e.KIND='SMS' and e.CODE=a.SMS "+
			"join SYST002 f on f.CENTER_CODE=c.CENTER_CODE "+
			"join SYST001 g on g.KIND='SEX' and g.CODE=d.SEX "+
			"join SYST001 h on h.KIND='HANDICAP_TYPE' and h.CODE=b.HANDICAP_TYPE "+
			"join SYST001 i on i.KIND='HANDICAP_GRADE' and i.CODE=b.HANDICAP_GRADE "+
	
			"Where a.TAKE_ABNDN='N' and a.PAYMENT_STATUS !='1' "
		);
	
	    if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
	         sql.append("AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	     }
	    if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
	            sql.append("AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	     }
		
	    sql.append("order by a.AYEAR,a.SMS,c.CENTER_CODE,c.STNO,d.NAME,d.SEX,c.IDNO,b.HANDICAP_GRADE,b.HANDICAP_TYPE,b.IDENTIFY_DATE,b.NEXT_IDENTIFY_DATE ");

        DBResult rs = null;
        try {
            // 取出所有資料
            rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                vt.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
    }    

	
	//身心障礙手冊後續鑑定需求明細表
	public String getWRITEOFF_STR(String AYEAR, String SMS, String STNO) throws Exception {
		String WRITEOFF_DATA = "";
		
	    if(sql.length() > 0) {
	        sql.delete(0, sql.length());
	    }
	
	    sql.append
		(
			"SELECT TO_CHAR(B.WRITEOFF_NO) AS WRITEOFF,TO_CHAR(D.CODE_NAME) AS DROPOUT_REASON_NAME,DECODE(C.WRIT_MK,'Y','是','N','否','') AS WRIT_MK_NAME,NVL(B.PAID_AMT, 0) + NVL(B.OVER_TAKE_FEE, 0) AS PAID_AMT,NVL(C.REFUND_AMT,0) AS REFUND_MON,'REG' AS TABLE_NAME, "+
			"'1' AS SORT "+
			"FROM REGT005 A "+
			"JOIN REGT010 B ON A.AYEAR = B.AYEAR AND A.SMS = B.SMS AND A.STNO = B.STNO "+ 
			"LEFT JOIN REGT009 C ON C.AYEAR = B.AYEAR AND C.SMS = B.SMS AND C.STNO = B.STNO AND B.WRITEOFF_NO = C.WRITEOFF_NO "+
			"LEFT JOIN SYST001 D ON D.KIND = 'DROPOUT_REASON1' AND D.CODE = C.DROPOUT_REASON  "+
			"WHERE 1=1 "+
			"AND A.AYEAR = '" + Utility.nullToSpace(AYEAR) + "' "+
			"AND A.SMS = '" + Utility.nullToSpace(SMS) + "' "+
			"AND A.STNO = '" + Utility.nullToSpace(STNO) + "' "+
			"AND (  NVL(B.PAID_AMT, '0') ! = '0' OR NVL(B.OVER_TAKE_FEE, '0') ! = '0' ) "+      
			"UNION "+
			"SELECT TO_CHAR(B.WRITEOFF_NO) AS WRITEOFF,TO_CHAR(C.CODE_NAME) AS DROPOUT_REASON_NAME,DECODE(A.ACNT_MK,'Y','是','N','否','') AS WRIT_MK_NAME,NVL(B.PAID_AMT,0) - NVL(D.REFUND_AMT, 0) AS PAID_AMT,NVL(A.TOT_AMT,0) AS REFUND_MON,'STU' AS TABLE_NAME, "+
			"'2' AS SORT "+
			"FROM STUT007 A "+
			"JOIN REGT010 B ON A.AYEAR = B.AYEAR AND A.SMS = B.SMS AND A.STNO = B.STNO "+ 
			"LEFT JOIN SYST001 C ON C.KIND = 'REFUND_STATUS' AND C.CODE = A.REFUND_STATUS  "+
			"LEFT JOIN REGT009 D ON D.AYEAR = B.AYEAR AND D.SMS = B.SMS AND D.STNO = B.STNO AND B.WRITEOFF_NO = D.WRITEOFF_NO AND D.WRIT_MK = 'Y' "+
			"WHERE 1=1 "+
			"AND A.AYEAR = '" + Utility.nullToSpace(AYEAR) + "' "+
			"AND A.SMS = '" + Utility.nullToSpace(SMS) + "' "+
			"AND A.STNO = '" + Utility.nullToSpace(STNO) + "' "+
			"AND NVL(SUBSTR(B.WRITEOFF_NO,7),0) = (SELECT MAX(NVL(SUBSTR(R.WRITEOFF_NO,7),0)) FROM REGT010 R WHERE R.AYEAR =B.AYEAR AND R.SMS =B.SMS  AND R.STNO =B.STNO  AND R.ABNDN_ORDER = 'N') "+
			"ORDER BY SORT "
		);

        DBResult rs = null;
        try {
            // 取出所有資料
            rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());
            
            while (rs.next()) {            	
                WRITEOFF_DATA +="<tr> "+
				                "	<td width='14%'>銷帳<br>編號</td> "+
				                "	<td width='16%'>"+rs.getString("WRITEOFF")+"</td> "+
				                "	<td width='10%' align='center'>繳費<br>金額</td> "+
				                "	<td width='6%'>"+rs.getString("PAID_AMT")+"</td> "+
				                "	<td width='10%' align='center'>退費<br>金額</td> "+
				                "	<td width='6%'>"+rs.getString("REFUND_MON")+"</td> "+
				                "	<td width='10%' align='center'>退費<br>原因</td> "+
				                "	<td width='9%'>"+rs.getString("DROPOUT_REASON_NAME")+"</td> "+
				                "	<td width='14%' align='center'>會計審核<br>註記</td> "+
				                "	<td width='9%'>"+rs.getString("WRIT_MK_NAME")+"</td> "+
				                "</tr> "+
				                "";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return WRITEOFF_DATA;
    }    

	// 僅空專才需要
	public String getCenterFacultyHtml(DBManager dbManager, Connection conn,
			Hashtable requestMap) throws Exception {
		String result = "";

		if (!(Utility.checkNull(requestMap.get("ASYS"),"")).equals("2"))
			return result;

		// 取得統計資料
		Vector data = this.getDataForREG131R(requestMap);

		// 產生每個中心的總計資料(從上面的統計資料中整理出來的)
		Hashtable centerTotalData = getCenterTotalData(data);

		// 1.組表頭
		SYST002DAO syst002 = new SYST002DAO(dbManager, conn);
		syst002.setResultColumn("CENTER_CODE,CENTER_ABBRNAME");
		syst002.setOrderbyColumn("CENTER_CODE");
		syst002.setWhere("CENTER_CODE<>'00'");
		DBResult rs = syst002.query();

		String header = "(7)依科別及中心別區分<br><table width='98%' class=dir border=0 cellSpacing=0 cellPadding=0><tr><td width='8%' rowspan='2'>&nbsp;</td>";
		Vector centerData = new Vector();
		// 1.1組中心名稱
		while (rs.next()) {
			header += "<td colspan='2' width='6%' align='center' valign='middle'>"
					+ rs.getString("CENTER_ABBRNAME") + "</td>";
			centerData.add(rs.getString("CENTER_CODE"));
		}
		rs.close();
		header += "<td colspan='2' width='8%' align='center' valign='middle'>合計</td></tr>";

		// 1.2組中心下面的新舊生
		header += "<tr>";
		for (int i = 0; i <= centerData.size(); i++) {
			header += "<td width='" + (i == centerData.size() ? "4%" : "3%")
					+ "' align='center' valign='middle'>新<br>生</td><td width='"
					+ (i == centerData.size() ? "4%" : "3%")
					+ "' align='center' valign='middle'>舊<br>生</td>";
		}
		header += "</tr>";

		// 2. 開始組統計資料
		int executeCount = 0;
		String content = ""; // 表身內容
		int dataNum = data.size();
		while (data.size() > 0) {
			System.out.println("executeCount=" + executeCount);
			// 組每個中心學系下的新舊生人數
			content += "<tr><td rowspan='2' align='center' valign='middle'>"
					+ ((Hashtable) data.get(executeCount))
							.get("FACULTY_ABBRNAME") + "</td>";
			String centerFacultyTotal = "<tr>"; // 每個中心學系的總人數
			String facultyNewNum = "0";
			String facultyOldNum = "0";
			String facultyNum = "0";
			// 一個迴圈表示一整個系的資料(即報表上的一行)
			for (int i = 0; i < centerData.size(); i++) {

				if (executeCount < dataNum) {
					String newNum = "0";
					String oldNum = "0";
					String centerFacultyNum = "0";
					String centerCode = ((Hashtable) data.get(executeCount))
							.get("CENTER_CODE").toString();

					// 判斷該中心是否有資料,無資料人數印0
					if (centerData.get(i).toString().equals(centerCode)) {
						newNum = ((Hashtable) data.get(executeCount)).get(
								"CENTER_FACULTY_NEW_NUM").toString();
						oldNum = ((Hashtable) data.get(executeCount)).get(
								"CENTER_FACULTY_OLD_NUM").toString();
						facultyNewNum = ((Hashtable) data.get(executeCount))
								.get("FACULTY_NEW_NUM").toString();
						facultyOldNum = ((Hashtable) data.get(executeCount))
								.get("FACULTY_OLD_NUM").toString();
						centerFacultyNum = ((Hashtable) data.get(executeCount))
								.get("CENTER_FACULTY_NUM").toString();
						facultyNum = ((Hashtable) data.get(executeCount)).get(
								"FACULTY_NUM").toString();
						executeCount++;
					}

					content += "<td align='right' valign='middle'>" + newNum
							+ "</td><td  align='right' valign='middle'>"
							+ oldNum + "</td>";
					centerFacultyTotal += "<td colspan='2' align='right' valign='middle'>"
							+ centerFacultyNum + "</td>";
					if (i == centerData.size() - 1) {
						content += "<td align='right' valign='middle'>"
								+ facultyNewNum
								+ "</td><td  align='right' valign='middle'>"
								+ facultyOldNum + "</td>";
						centerFacultyTotal += "<td colspan='2' align='right' valign='middle'>"
								+ facultyNum + "</td>";
					}
				}

			}

			content += "</tr>" + centerFacultyTotal + "</tr>";

			// 表示最後一行,列印合計
			if (executeCount >= data.size()) {
				content += "<tr><td rowspan='2' valign='middle' align='center'>合計</td>";
				String centerTotal = "<tr>"; // 每個中心中心的總人數
				// 一個迴圈表示一整個系的資料(即報表上的一行)
				for (int i = 0; i < centerData.size(); i++) {
					String[] centerDataArray = null;
					if (centerTotalData.containsKey(centerData.get(i)
							.toString())) {
						centerDataArray = Utility.split(Utility
								.nullToSpace(centerTotalData.get(centerData
										.get(i).toString())), "|");
					}else{
						centerDataArray = new String[] { "0", "0", "0", "0",
								"0", "0" };
					}

					content += "<td align='right' valign='middle'>"
							+ (centerDataArray.length == 0 ? "0"
									: centerDataArray[0])
							+ "</td><td  align='right' valign='middle'>"
							+ (centerDataArray.length == 0 ? "0"
									: centerDataArray[1]) + "</td>";
					centerTotal += "<td colspan='2' align='right' valign='middle'>"
							+ centerDataArray[2] + "</td>";
					if (i == centerData.size() - 1) {
						content += "<td align='right' valign='middle'>"
								+ centerDataArray[3]
								+ "</td><td  align='right' valign='middle'>"
								+ centerDataArray[4] + "</td>";
						centerTotal += "<td colspan='2' align='right' valign='middle'>"
								+ centerDataArray[5] + "</td>";
					}
				}
				content += "</tr>" + centerTotal + "</tr>";

				break;
			}
		}

		result = "<table width='98%'>" + header + content + "</table>";

		return result;
	}

	// 置入中心總計資料
	private Hashtable getCenterTotalData(Vector data) {
		Hashtable result = new Hashtable();

		for (int i = 0; i < data.size(); i++) {
			Hashtable centerData = (Hashtable) data.get(i);
			result.put(
					centerData.get("CENTER_CODE").toString(),
					centerData.get("CENTER_NEW_NUM") + "|"
							+ centerData.get("CENTER_OLD_NUM") + "|"
							+ centerData.get("CENTER_NUM") + "|"
							+ centerData.get("NEW_NUM") + "|"
							+ centerData.get("OLD_NUM") + "|"
							+ centerData.get("NUM"));
		}

		return result;
	}
	
	// REG039M_查詢學生註冊選課學雜費資訊
    public Vector getReg039mQuery(Hashtable ht) throws Exception {
    	Vector result = new Vector();

		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());

			sql.append("SELECT distinct M.AYEAR,M.SMS, R12.CODE_NAME AS CSMS, M.STNO,R2.NAME, R1.CENTER_CODE, R3.CENTER_ABBRNAME, SUBSTR(R4.AUTH_DATE,1,8) AS AUTH_DATE, R7.WRITEOFF_NO,R7.PAYMENT_MANNER_NAME,R7.PAID_AMT,R7.ACC_DATE,R7.BATNUM,M.PAYMENT_STATUS, ");
			sql.append("DECODE(R8.STNO,'','否','是') AS SCDT004_STNO, ");
			sql.append("Case when R1.ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+""+ht.get("SMS").toString()+"' then '新生' when R1.FTSTUD_ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+""+ht.get("SMS").toString()+"' then '新生' else '舊生' End as NEWOLD_ST, ");
			sql.append("R2.MOBILE, R2.EMAIL, ");
			sql.append("Case when M.PAYMENT_STATUS = '1' then '未繳費' else '已繳費' end as PAYSTATUS ");			
			sql.append("FROM REGT005 M  ");
			sql.append("JOIN STUT003 R1 ON R1.STNO = M.STNO  ");
			sql.append("JOIN STUT002 R2 ON R2.IDNO = R1.IDNO AND R2.BIRTHDATE = R1.BIRTHDATE ");
			sql.append("JOIN SYST002 R3 ON R1.CENTER_CODE = R3.CENTER_CODE ");
			sql.append("LEFT JOIN PCST012 R4 ON R4.AYEAR = M.AYEAR AND R4.SMS = M.SMS AND R4.STNO = M.STNO AND R4.BUSS_RESULT IN ('0000','000') ");
			sql.append("LEFT JOIN ");
			sql.append("(SELECT R5.AYEAR,R5.SMS,R5.STNO,R5.WRITEOFF_NO,R6.PAYMENT_MANNER,R6.PAID_AMT,R6.INTO_DATE,R6.ACC_MK,R6.ACC_DATE,R6.BATNUM, S1.CODE_NAME AS PAYMENT_MANNER_NAME ");
			sql.append("FROM PCST004 R5  ");
			sql.append("JOIN REGT005 M1 ON R5.AYEAR = M1.AYEAR AND R5.SMS = M1.SMS AND R5.STNO = M1.STNO AND R5.PAYMENT_TYPE IN ('21','22') ");
			sql.append("JOIN PCST013 R6 ON R6.WRITEOFF_NO = R5.WRITEOFF_NO ");
			sql.append("JOIN SYST001 S1 ON S1.KIND = 'PAYMENT_MANNER' AND S1.CODE = R6.PAYMENT_MANNER ");
			sql.append("WHERE M1.AYEAR = '"+ht.get("AYEAR").toString()+"' AND M1.SMS = '"+ht.get("SMS").toString()+"' ");
			sql.append(") R7 ON R7.AYEAR = M.AYEAR AND R7.SMS = M.SMS AND R7.STNO = M.STNO ");
			sql.append("LEFT JOIN SCDT004 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO ");
			sql.append("JOIN SYST001 R10 ON R10.KIND = 'SMS' AND R10.CODE = SUBSTR(R1.ENROLL_AYEARSMS,4,1) ");
			sql.append("JOIN SYST001 R12 ON R12.KIND = 'SMS' AND R12.CODE = M.SMS ");
			sql.append("WHERE 1=1 ");
			if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
				sql.append("and M.AYEAR='"+ht.get("AYEAR").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("SMS")).equals("")){
				sql.append("and M.SMS='"+ht.get("SMS").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("STNO")).equals("")){
				sql.append("and M.STNO = '"+ht.get("STNO").toString()+"' ");
			}
			sql.append("AND M.TAKE_ABNDN = 'N' AND M.TOTAL_CRD_CNT!= '0' ");
            if(!Utility.nullToSpace(ht.get("PAYMENT_STATUS")).equals("")){
		   		sql.append("and M.PAYMENT_STATUS = '"+ht.get("PAYMENT_STATUS").toString()+"' ");
			}
            if("1".equals(ht.get("PAYMENT_STATUS").toString())){
            	sql.append("AND M.PAYMENT_STATUS = '1' ");
			}else if("2".equals(ht.get("PAYMENT_STATUS").toString())){
				sql.append("AND M.PAYMENT_STATUS != '1' ");
			}
			if(!Utility.nullToSpace(ht.get("IDNO")).equals("")){
				sql.append("and R1.IDNO = '"+ht.get("IDNO").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")){
				sql.append("and R3.CENTER_CODE = '"+ht.get("CENTER_CODE").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("ACC_DATE")).equals("")){
				sql.append("and R7.ACC_DATE = '"+ht.get("ACC_DATE").toString()+"' ");
			}
			if("1".equals(ht.get("NEWOLD_ST").toString())){
				sql.append("AND (R1.ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"' ");
				sql.append("OR R1.FTSTUD_ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"') ");
			}else if("2".equals(ht.get("NEWOLD_ST").toString())){
				sql.append("AND (R1.ENROLL_AYEARSMS != '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"' ");
				sql.append("AND (R1.FTSTUD_ENROLL_AYEARSMS != '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"' OR R1.FTSTUD_ENROLL_AYEARSMS is null)) ");
			}
			sql.append("ORDER BY M.AYEAR,M.SMS,R1.CENTER_CODE,M.STNO ");

			DBResult rs = null;
			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return result;
	}
    
 // SGU055M_導師班學生編班資訊查詢
    public Vector getSgu055mQuery(Hashtable ht) throws Exception {
    	Vector result = new Vector();

		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());

			sql.append("SELECT distinct M.AYEAR,M.SMS, R12.CODE_NAME AS CSMS, M.STNO,R2.NAME, R1.CENTER_CODE, R3.CENTER_ABBRNAME, M.PAYMENT_STATUS, ");
			sql.append("Case when R1.ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+""+ht.get("SMS").toString()+"' then '新生' when R1.FTSTUD_ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+""+ht.get("SMS").toString()+"' then '新生' else '舊生' End as NEWOLD_ST, ");
			sql.append("Case when R9.KIND = '1' then '新生' when R9.KIND = '2' then '身障生' when R9.KIND = '3' then '中輟生' else '未編班' End as SG_ST, R11.NAME AS TCH_NAME, R2.MOBILE, R2.EMAIL, ");
			sql.append("Case when M.PAYMENT_STATUS = '1' then '未繳費' else '已繳費' end as PAYSTATUS ");
			sql.append(", R2.CRRSADDR_ZIP||R2.CRRSADDR AS CRRSADDR ");
			sql.append(", Case when R1.SPECIAL_STTYPE_TYPE is null then '' else '專班生' End AS SPECIAL_STTYPE_CTYPE ");
			sql.append(", (SELECT '身障生' FROM SGUT004 X2 WHERE X2.STNO = M.STNO AND X2.HAND_NATIVE = '2' ) AS HANDICAP_MK ");
			sql.append("FROM REGT005 M  ");
			sql.append("JOIN STUT003 R1 ON R1.STNO = M.STNO  ");
			sql.append("JOIN STUT002 R2 ON R2.IDNO = R1.IDNO AND R2.BIRTHDATE = R1.BIRTHDATE ");
			sql.append("JOIN SYST002 R3 ON R1.CENTER_CODE = R3.CENTER_CODE ");
			sql.append("LEFT JOIN SGUT011 R9 ON R9.AYEAR = M.AYEAR AND R9.SMS = M.SMS AND R9.STNO = M.STNO ");
			sql.append("LEFT JOIN TRAT001 R11 ON R11.IDNO = R9.TCH_IDNO ");
			sql.append("JOIN SYST001 R10 ON R10.KIND = 'SMS' AND R10.CODE = SUBSTR(R1.ENROLL_AYEARSMS,4,1) ");
			sql.append("JOIN SYST001 R12 ON R12.KIND = 'SMS' AND R12.CODE = M.SMS ");
			sql.append("WHERE 1=1 ");
			if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
				sql.append("and M.AYEAR='"+ht.get("AYEAR").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("SMS")).equals("")){
				sql.append("and M.SMS='"+ht.get("SMS").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("STNO")).equals("")){
				sql.append("and M.STNO = '"+ht.get("STNO").toString()+"' ");
			}
			
				sql.append("AND M.TAKE_ABNDN = 'N' AND M.TOTAL_CRD_CNT!= '0' ");
			
            if(!Utility.nullToSpace(ht.get("PAYMENT_STATUS")).equals("")){
	            if("1".equals(ht.get("PAYMENT_STATUS").toString())){
	            	sql.append("AND M.PAYMENT_STATUS = '1' ");
				}else if("2".equals(ht.get("PAYMENT_STATUS").toString())){
					sql.append("AND M.PAYMENT_STATUS != '1' ");
				}
            }
            
			if(!Utility.nullToSpace(ht.get("IDNO")).equals("")){
				sql.append("and R1.IDNO = '"+ht.get("IDNO").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("CENTER_CODE")).equals("")){
				sql.append("and R3.CENTER_CODE = '"+ht.get("CENTER_CODE").toString()+"' ");
			}
			if(!Utility.nullToSpace(ht.get("ACC_DATE")).equals("")){
				sql.append("and R7.ACC_DATE = '"+ht.get("ACC_DATE").toString()+"' ");
			}
			
			if("1".equals(ht.get("NEWOLD_ST").toString())){
				sql.append("AND (R1.ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"' ");
				sql.append("OR R1.FTSTUD_ENROLL_AYEARSMS = '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"') ");
			}else if("2".equals(ht.get("NEWOLD_ST").toString())){
				sql.append("AND (R1.ENROLL_AYEARSMS != '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"' ");
				sql.append("AND (R1.FTSTUD_ENROLL_AYEARSMS != '"+ht.get("AYEAR").toString()+ht.get("SMS").toString()+"' OR R1.FTSTUD_ENROLL_AYEARSMS is null)) ");
			}
			if("1".equals(ht.get("SG_CLASS").toString())){
				sql.append("AND M.STNO IN (SELECT R91.STNO FROM SGUT011 R91 WHERE R91.AYEAR = '"+ht.get("AYEAR").toString()+"' AND R91.SMS = '"+ht.get("SMS").toString()+"' AND R91.CENTER_CODE = '"+ht.get("CENTER_CODE").toString()+"') ");
			}else if("2".equals(ht.get("SG_CLASS").toString())){
				sql.append("AND M.STNO NOT IN (SELECT R91.STNO FROM SGUT011 R91 WHERE R91.AYEAR = '"+ht.get("AYEAR").toString()+"' AND R91.SMS = '"+ht.get("SMS").toString()+"' AND R91.CENTER_CODE = '"+ht.get("CENTER_CODE").toString()+"') ");
			}
			if("1".equals(ht.get("SG_KIND").toString())){
				sql.append("AND R9.KIND = '1' ");
			}else if("2".equals(ht.get("SG_KIND").toString())){
				sql.append("AND R9.KIND = '2' ");
			}else if("3".equals(ht.get("SG_KIND").toString())){
				sql.append("AND R9.KIND = '3' ");			
			}
			sql.append("ORDER BY M.AYEAR,M.SMS,R1.CENTER_CODE,M.STNO ");

			DBResult rs = null;
			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return result;
	}
    
	
    /**
	   * REG131R 依縣市區分
	   */
	public Vector getReg131Regt005City( Hashtable ht) throws Exception {
		Vector result = new Vector();
		DBResult rs = null;
		String title="";
		String AYEAR = Utility.checkNull(ht.get("AYEAR"),"");
		String SMS = Utility.checkNull(ht.get("SMS"),"");
		String ASYS = Utility.checkNull(ht.get("ASYS"),"");
		String STTYPE = Utility.checkNull(ht.get("STTYPE"),"");
		//String PRT_MANNER = Utility.checkNull(ht.get("PRT_MANNER"),"");
		//int CASE = Integer.parseInt(Utility.checkNull(ht.get("CASE"),"0"));

		String sCond1 = "";
		String sCond3 = "" ;

		 if(!AYEAR.equals("")) sCond1 += " AND AYEAR ='"+AYEAR+"' ";
	     if(!SMS.equals("")) sCond1 += " AND SMS ='"+SMS+"' ";
	     if(!ASYS.equals("") && !"0".equals(ASYS)) sCond1 += " AND ASYS ='"+ASYS+"' ";
	     if(!STTYPE.equals("")) sCond1 += " AND STTYPE ='"+STTYPE+"' ";
	     
		try
		{
			if(sql.length() >0)
				sql.delete(0, sql.length());


			sql.append
			(
				"SELECT CRRSADDR_ZIP, ZIP_CITY, NEW_OLD, COUNT(STNO) TOTAL FROM "+
				"( "+
					"SELECT a.ayear, a.sms, a.stno, b.asys, c.vocation, c.sex, " +
					"d.ZIP_CITY, SUBSTR(c.CRRSADDR_ZIP,1,3) as CRRSADDR_ZIP, " +
					"DECODE(b.special_sttype_type,'','NOT_SPECIAL_STTYPE_TYPE','SPECIAL_STTYPE_TYPE') AS special_sttype_type, " +
					"c.edubkgrd_grade, b.sttype, b.center_code, " +
					"(CASE "+
					"WHEN ( ( DECODE(nvl(b.ftstud_enroll_ayearsms,''),'',b.enroll_ayearsms,b.ftstud_enroll_ayearsms) ) = ( a.ayear || a.sms ) ) THEN '1' " +
					"ELSE '2' "+
					"END "+
					") AS new_old "+
					"FROM "+
					"regt005 a "+
					"JOIN stut003 b ON a.stno = b.stno "+
					"JOIN stut002 c ON b.idno = c.idno "+
					"AND b.birthdate = c.birthdate "+
					"Left join syst006 d on SUBSTR(C.CRRSADDR_ZIP,1,3) = d.ZIP "+
					"WHERE "+
					"1 = 1 "+
					"AND   take_abndn = 'N' "+
					"AND   a.payment_status <> '1' " +
					") " +
					"WHERE "+
					"0 = 0 " +
					sCond1 +
					" GROUP BY " +
					"crrsaddr_zip, CRRSADDR_ZIP, ZIP_CITY, new_old " +
					"ORDER BY CRRSADDR_ZIP desc,NEW_OLD "
			);


			if(pageQuery) {
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			} else {
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			Hashtable rowHt = null;
			while (rs.next())
			{
				rowHt = new Hashtable();
				for (int i = 1; i <= rs.getColumnCount(); i++)
					rowHt.put(rs.getColumnName(i), rs.getString(i));
				result.add(rowHt);
			}
   return result;
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
    
}


