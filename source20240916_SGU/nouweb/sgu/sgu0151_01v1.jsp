<%/*
----------------------------------------------------------------------------------
File Name        : sgu0151_01v1
Author            : Jason
Description        : SGU0151_導師班編班 - 顯示頁面
Modification Log    :

Vers        Date           By                Notes
--------------    --------------    --------------    ----------------------------------
1.0.0		 097/07/18	  barry    	   SGU QA 171
0.0.1        096/07/17    Jason        Code Generate Create
0.0.2        097/01/28    WEN          Q1:作法  分配學生規則：LOOP處理每一個中心
                                         1a.分配新生    1b.分配身心障礙生    1c.分配中輟生
                                       Q2:點審核完畢->CONFIRM"是否審核完畢"，若回答Y->SGUT009.AUDIT_MK='Y'
----------------------------------------------------------------------------------
*/%>
<%
session.setAttribute("SGU0151_01_SELECT", "SGU#SELECT CODE AS SELECT_VALUE, CODE_NAME AS SELECT_TEXT FROM SYST001 WHERE KIND='SMS' ORDER BY CODE");
session.setAttribute("SGU0151_02_WINDOW", "SGU#SELECT  CENTER_CODE, CENTER_NAME FROM SYST002 ORDER BY CENTER_CODE");
session.setAttribute("SGU0151_02_BLUR", "SGU#SELECT CENTER_CODE, CENTER_NAME FROM SYST002 WHERE CENTER_CODE = '[CENTER_CODE]'");

session.setAttribute("SGU0151_03_BLUR", "SGU#SELECT DISTINCT A.IDNO AS TCH_IDNO, A.NAME AS NAME  FROM TRAT001 A , SGUT009 B WHERE A.IDNO = B.TCH_IDNO AND IDNO = '[TCH_IDNO]'  ORDER BY IDNO ");
session.setAttribute("SGU0151_03_WINDOW", "SGU#SELECT IDNO AS TCH_IDNO, NAME FROM TRAT001 FROM TRAT001 ORDER BY IDNO ");
session.setAttribute("SGU0151_03_SELECT", "SGU#SELECT DISTINCT A.IDNO AS SELECT_VALUE, A.NAME AS SELECT_TEXT  FROM TRAT001 A , SGUT009 B WHERE A.IDNO = B.TCH_IDNO AND B.AYEAR='[AYEAR]' AND B.SMS='[SMS]' AND B.CENTER_CODE ='[CENTER_CODE]' ORDER BY IDNO ");

session.setAttribute("SGU0151_04_BLUR", "SGU#SELECT STUT003.STNO AS STNO1, STUT002.NAME AS STNO_NAME FROM STUT003 JOIN STUT002 ON STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE WHERE STUT003.STNO = '[STNO1]'");
%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="/utility/errorpage.jsp" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<html>
<head>
    <%@ include file="/utility/viewpageinit.jsp"%>
    <script src="<%=vr%>script/framework/query1_1_0_2.jsp"></script>
    <script src="sgu0151_01c1.jsp"></script>
    <noscript>
        <p>您的瀏覽器不支援JavaScript語法，但是並不影響您獲取本網站的內容</p>
    </noscript>
</head>
<body background="<%=vr%>images/ap_index_bg.jpg" alt="背景圖" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- 定義查詢的 Form 起始 -->
<form name="QUERY" method="post" onsubmit="doQuery();" style="margin:0,0,5,0;">
    <input type=hidden name="control_type">
    <input type=hidden name="pageSize">
    <input type=hidden name="pageNo">
    <input type=hidden name="EXPORT_FILE_NAME">
    <input type=hidden name="EXPORT_COLUMN_FILTER">

    <!-- 查詢全畫面起始 -->
    <TABLE id="QUERY_DIV" width="96%" border="0" align="center" cellpadding="0" cellspacing="0" summary="排版用表格">
        <tr>
            <td width="13"><img src="<%=vr%>images/ap_search_01.jpg" alt="排版用圖示" width="13" height="12"></td>
            <td width="100%"><img src="<%=vr%>images/ap_search_02.jpg" alt="排版用圖示" width="100%" height="12"></td>
            <td width="13"><img src="<%=vr%>images/ap_search_03.jpg" alt="排版用圖示" width="13" height="12"></td>
        </tr>
        <tr>
            <td width="13" background="<%=vr%>images/ap_search_04.jpg" alt="排版用圖示">&nbsp;</td>
            <td width="100%" valign="top" bgcolor="#C5E2C3">
                <!-- 按鈕畫面起始 -->
                <table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" summary="排版用表格">
                    <tr class="mtbGreenBg">
                        <td align=left>【查詢畫面】</td>
                        <td align=right>
                            <div id="serach_btn">
                                <input type=button class="btn" value='回查詢頁' onkeypress="window.location='sgu015m_.jsp';"onclick="window.location='sgu015m_.jsp';" name='QUERY_BTN'>
                                <!-- 
                                <input type=button class="btn" value='列  印' name="PRT_ALL_BTN" onkeypress="doPrint('QUERY');"onclick="doPrint('QUERY');">
                                <input type=button class="btn" value='匯  出' name="EXPORT_ALL_BTN" onkeypress="doExport('QUERY');"onclick="doExport('QUERY');">
                                 -->
                            </div>
                        </td>
                    </tr>
                </table>
                <!-- 按鈕畫面結束 -->

                <!-- 查詢畫面起始 -->
                <table id="table1" width="100%" border="0" align="center" cellpadding="2" cellspacing="1" summary="排版用表格">
                    <tr>
                        <td align='right'>學年期：</td>
                        <td>
                            <input type=text name='AYEAR'>
                            <select name='SMS' id='f1'>
                                <option value=''>請選擇</option>
                                <script>Form.getSelectFromPhrase("SGU0151_01_SELECT", null, null);</script>
                            </select>
                        </td>
                        <td align='right'>中心代號：</td>
                        <td colspan='3'>
                            <input type='text' Column='CENTER_CODE' name='CENTER_CODE' onblur='Form.blurData("SGU0151_02_BLUR", "CENTER_CODE", this.value, ["CENTER_CODE","CENTER_NAME"], [_i("QUERY", "CENTER_CODE"),_i("QUERY", "CENTER_NAME")], false);' size="20">
                            <img id='img1' src='/images/select.gif' alt='開窗選取' style='cursor:hand' onkeypress='Form.openPhraseWindow("SGU0151_02_WINDOW", null, null, "中心代號,中心名稱", [_i("QUERY", "CENTER_CODE"),_i("QUERY", "CENTER_NAME")]);' onclick='Form.openPhraseWindow("SGU0151_02_WINDOW", null, null, "中心代號,中心名稱", [_i("QUERY", "CENTER_CODE"),_i("QUERY", "CENTER_NAME")]);'>
                            <input type=text Column='CENTER_NAME' name='CENTER_NAME' readonly>
                        </td>
                    </tr>
                    <tr>
                        <td align='right'>教師身分證字號：</td>
                        <td>
                            <input type='text' Column='TCH_IDNO' name='TCH_IDNO' onblur='Form.blurData("SGU0151_03_BLUR", "TCH_IDNO", this.value, ["NAME"], [_i("QUERY", "NAME")], true);' size="15">
                            <img id='img2' src='/images/select.gif' alt='開窗選取' style='cursor:hand' onkeypress='Form.openPhraseWindow("SGU0151_03_WINDOW", "", "", "身分證字號,姓名", [_i("QUERY", "TCH_IDNO"), _i("QUERY", "NAME")]);' onclick='Form.openPhraseWindow("SGU0151_03_WINDOW", "", "", "身分證字號,姓名", [_i("QUERY", "TCH_IDNO"), _i("QUERY", "NAME")]);'>
                            <input type='text' Column='NAME' name='NAME' readonly size="15">
                        </td>
                        <td align='right'>新生人數：</td>
                        <td><input type=text name='NEW_ST'></td>
                        <td align='right'>身心障礙生人數：</td>
                        <td><input type=text name='HANDICAP_ST'></td>
                    </tr>
                    <tr>
                        <td align='right'>中輟生人數：</td>
                        <td colspan='5'><input type=text name='RESIGN_ST'></td>
                    </tr>
                </table>
                <!-- 查詢畫面結束 -->
            </td>
            <td width="13" background="<%=vr%>images/ap_search_06.jpg" alt="排版用圖示">&nbsp;</td>
        </tr>
        <tr>
            <td width="13"><img src="<%=vr%>images/ap_search_07.jpg" alt="排版用圖示" width="13" height="13"></td>
            <td width="100%"><img src="<%=vr%>images/ap_search_08.jpg" alt="排版用圖示" width="100%" height="13"></td>
            <td width="13"><img src="<%=vr%>images/ap_search_09.jpg" alt="排版用圖示" width="13" height="13"></td>
        </tr>
    </table>
    <!-- 查詢全畫面結束 -->
</form>
<!-- 定義查詢的 Form 結束 -->

<!-- 標題畫面起始 -->
<table width="96%" border="0" align="center" cellpadding="4" cellspacing="0" summary="排版用表格">
    <tr>
        <td>
            <table width="500" height="27" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td background="<%=vr%>images/ap_index_title.jpg" alt="排版用圖示">
                        　　<span class="title">SGU0151_導師班學生資料</span>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!-- 標題畫面結束 -->

<!-- 定義編輯的 Form 起始 -->
<form name="EDIT" method="post" onsubmit="doSave();" style="margin:5,0,0,0;">
    <input type=hidden name="control_type">
    <input type=hidden name="ROWSTAMP">
    <input type=hidden name="AYEAR">
    <input type=hidden name="SMS">
    <input type=hidden name="STNO">
    <input type=hidden name="CENTER_CODE">
    <!-- 編輯全畫面起始 -->
    <TABLE id="EDIT_DIV" width="96%" border="0" align="center" cellpadding="0" cellspacing="0" summary="排版用表格">
        <tr>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_01.gif" alt="排版用圖示" width="13" height="14"></td>
            <td width="100%"><img src="<%=vr%>images/ap_index_mtb_02.gif" alt="排版用圖示" width="100%" height="14"></td>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_03.gif" alt="排版用圖示" width="13" height="14"></td>
        </tr>
        <tr>
            <td width="13" background="<%=vr%>images/ap_index_mtb_04.gif" alt="排版用圖示">&nbsp;</td>
            <td width="100%" valign="top" bgcolor="#FFFFFF">
                <!-- 按鈕畫面起始 -->
                <table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" summary="排版用表格">
                    <tr class="mtbGreenBg">
                        <td align=left>【編輯畫面】- <span id='EditStatus'>新增</span></td>
                        <td align=right>
                            <div id="edit_btn">
                                <input type=button class="btn" value='新  增' onkeypress='doAdd();'onclick='doAdd();'>
								<input type=button class="btn" value='清  除' onkeypress='doClear2();'onclick='doClear2();'>
                                <input type=submit name="SAVE_BTN" class="btn" value='存  檔'>
                            </div>
                        </td>
                    </tr>
                </table>
                <!-- 按鈕畫面結束 -->

                <!-- 編輯畫面起始 -->
                <table id="table2" width="100%" border="0" align="center" cellpadding="2" cellspacing="0" summary="排版用表格">
                    <tr>
                        <td align='right' class='tdgl1'>學號<font color=red>＊</font>：</td>
                        <td colspan='2' class='tdGrayLight'>
                            <input type='text' Column='STNO1' name='STNO1' onblur='Form.blurData("SGU0151_04_BLUR", "STNO1", this.value, ["STNO_NAME"], [_i("EDIT", "STNO_NAME")], true);' size="15">
                            <input type='text' Column='STNO_NAME' name='STNO_NAME' readonly size="15">
                        </td>
                        <td align='right' class='tdgl1'>輔導類別<font color=red>＊</font>：</td>
                        <td colspan='2' class='tdGrayLight'>
                                <select name='KIND' >
                                <option value=''>請選擇</option>
                                <option value='1'>新生</option>
                                <option value='2'>身障生</option>
                                <option value='3'>中輟生</option>
                                <script></script>
                                </select>
                        </td>
                    </tr>
                    <tr>
                        <td align='right' class='tdgl1'>教師姓名<font color=red>＊</font>：</td>
                        <td colspan='5' class='tdGrayLight'>
                            <!--input type='text' Column='TCH_IDNO' name='TCH_IDNO' onblur='Form.blurData("SGU0151_03_BLUR", "TCH_IDNO", this.value, ["NAME"], [_i("EDIT", "NAME")], true);' size="15">
                            <img src='/images/select.gif' alt='開窗選取' style='cursor:hand' onkeypress='Form.openPhraseWindow("SGU0151_03_WINDOW", "", "", "身分證字號,姓名", [_i("EDIT", "TCH_IDNO"), _i("EDIT", "NAME")]);' onclick='Form.openPhraseWindow("SGU0151_03_WINDOW", "", "", "身分證字號,姓名", [_i("EDIT", "TCH_IDNO"), _i("EDIT", "NAME")]);'-->
                              <select name='TCH_IDNO' >
                                <option value=''>請選擇</option>
                                <script></script>

                            </select>

                            <!--input type='text' Column='NAME' name='NAME' readonly size="15"-->
                        </td>
                        
                    </tr>
                </table>
                <!-- 編輯畫面結束 -->
            </td>
            <td width="13" background="<%=vr%>images/ap_index_mtb_06.gif" alt="排版用圖示">&nbsp;</td>
        </tr>
        <tr>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_07.gif" alt="排版用圖示" width="13" height="15"></td>
            <td width="100%"><img src="<%=vr%>images/ap_index_mtb_08.gif" alt="排版用圖示" width="100%" height="15"></td>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_09.gif" alt="排版用圖示" width="13" height="15"></td>
        </tr>
    </table>
    <!-- 編輯全畫面結束 -->
</form>
<!-- 定義編輯的 Form 結束 -->

<!-- 定義查詢結果的 Form 起始 -->
<form name="RESULT" method="post" style="margin:10,0,0,0;">
    <input type=hidden name="control_type">
    <input type=hidden keyColumn="Y" name="AYEAR">
    <input type=hidden keyColumn="Y" name="SMS">
    <input type=hidden keyColumn="Y" name="TCH_IDNO">
    <input type=hidden keyColumn="Y" name="STNO">
    <input type=hidden keyColumn="Y" name="CENTER_CODE">
    <input type=hidden keyColumn="Y" name="KIND">


    <!-- 查詢結果畫面起始 -->
    <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" summary="排版用表格">
        <tr>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_01.gif" alt="排版用圖示" width="13" height="14"></td>
            <td width="100%"><img src="<%=vr%>images/ap_index_mtb_02.gif" alt="排版用圖示" width="100%" height="14"></td>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_03.gif" alt="排版用圖示" width="13" height="14"></td>
        </tr>
        <tr>
            <td width="13" background="<%=vr%>images/ap_index_mtb_04.gif" alt="排版用圖示">&nbsp;</td>
            <td width="100%" bgcolor="#FFFFFF">
                <table width="100%" border="0" cellspacing="0" cellpadding="2" summary="排版用表格">
                    <tr>
                        <!-- 查詢結果按鈕起始 -->
                        <td align=left>
							<input type=button class="btn" value='全    選' name="SELALL_BTN" onkeypress="Form.setCheckBox(1, 'RESULT');"onclick="Form.setCheckBox(1, 'RESULT');">
                            <input type=button class="btn" value='全 不 選' name="SELNONE_BTN" onkeypress="Form.setCheckBox(0, 'RESULT');"onclick="Form.setCheckBox(0, 'RESULT');">
                            <input type=button class="btn" value='刪    除' name="DEL_BTN" onkeypress="doDelete('multi');"onclick="doDelete('multi');">
                            <input type=button class="btn" value='轉    班' name="CHG_BTN" onkeypress="doChang('multi');"onclick="doChang('multi');">
                            選取轉入教師姓名<select name='RESULT_TCH_IDNO' >
                                <option value=''>請選擇</option>
                                <script></script>
                            </select>
                        </td>
                        <!-- 查詢結果按鈕結束 -->

                        <!-- 分頁字串起始 -->
                        <td align=right nowrap>
                            <div id="page">
                                <b>
                                    <span id="pageStr"></span>
                                    【<input type='text' name='_scrollSize' size=2 value='10' style="text-align:center">
                                    <input type=button value='筆' onkeypress="setPageSize();"onclick="setPageSize();">
                                    <input type='text' name='_goToPage' size=2 value='1' style="text-align:right">
                                    / <span id="totalPage"></span> <input type=button value='頁' onkeypress='gotoPage(null)'onclick='gotoPage(null)'>
                                    <span id="totalRow">0</span> 筆】

                                </b>
                            </div>
                        </td>
                        <!-- 分頁字串結束 -->
                    </tr>
                </table>
                <!-- 查詢結果功能畫面起始 -->
                <div id="grid-scroll" style="overflow:auto;width:100%;height:300;"></div>
                <input type=hidden name='EXPORT_FILE_NAME'>
                <textarea name='EXPORT_CONTENT' cols=80 rows=3 style='display:none'></textarea>
                <textarea name='ALL_CONTENT' cols=80 rows=3 style='display:none'></textarea>
                <!-- 查詢結果功能畫面結束 -->
            </td>
            <td width="13" background="<%=vr%>images/ap_index_mtb_06.gif" alt="排版用圖示">&nbsp;</td>
        </tr>
        <tr>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_07.gif" alt="排版用圖示" width="13" height="15"></td>
            <td width="100%"><img src="<%=vr%>images/ap_index_mtb_08.gif" alt="排版用圖示" width="100%" height="15"></td>
            <td width="13"><img src="<%=vr%>images/ap_index_mtb_09.gif" alt="排版用圖示" width="13" height="15"></td>
        </tr>
    </table>
    <!-- 查詢結果畫面結束 -->
</form>
<!-- 定義查詢結果的 Form 結束 -->

<script>
    document.write ("<font color=\"white\">" + document.lastModified + "</font>");
    window.attachEvent("onload", page_init);
    window.attachEvent("onload", onloadEvent);
    //window.attachEvent("onload", setDefault);
</script>
</body>
</html>