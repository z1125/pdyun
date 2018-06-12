<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-17
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <span>开 始 时 间：</span>
    <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMin" name="sellTimeMin"/>
    <span>结 束 时 间 ：</span>
    <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMax" name="sellTimeMax"/>

    &nbsp;&nbsp;
    <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
    <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;display: block; font-size:100%" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/trade/fanyong?unitsId=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: function(data){
	                var str = JSON.stringify(data.yingkuiTotal.totalMoney);
	                var str1 = JSON.stringify(data.yingkuiTotal.totalDifMoney);

	                if(str == null ){
	                document.getElementById('fanyong1').innerHTML='0元';
	                }
	                if(str1 == null ){
	                document.getElementById('fanyong2').innerHTML='0元';
	                }
	                if(str != null){
	                     document.getElementById('fanyong1').innerHTML=str+'元';
	                }
	                if(str1 != null){
	                     document.getElementById('fanyong2').innerHTML=str1+'元';
	                }
	                    return data.g;
	                }
	            ">

        <thead>
        <tr>
            <th field="agentRealName" align="center" data-options="width:100">所属合伙人</th>
            <th field="agentMobile" align="center" data-options="width:100">合伙人手机号</th>
            <th field="userChnName" align="center" data-options="width:100">会员用户姓名</th>
            <th field="jType" align="center" data-options="width:100">级别</th>
            <th field="serialNo" align="center" data-options="width:100">交易流水号</th>
            <th field="money" align="center" data-options="width:100">交易金额</th>
            <th field="difMoney" align="center" data-options="width:100">盈亏金额</th>
            <th field="totalMoney" align="center" data-options="width:100" hidden>盈亏总和</th>
            <th field="sellTime" align="center" data-options="width:100">交割时间</th>
        </tr>
        </thead>
    </table><br/><br/>
    <div style="float: right;">
        <span style="font-size: 15px;">交易总金额：</span><label id="fanyong1" style="font-size: 15px;"></label><br/><br/>
        <span style="font-size: 15px;">盈亏总金额：</span><label id="fanyong2" style="font-size: 15px;"></label>
    </div>
</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["sellTimeMin","sellTimeMax"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
//        $("#queryListBt").click(function () {
//            document.getElementById("fanyong").innerHTML="";
//
//        })
//        var iid = document.getElementById("queryListBt");
//        iid.onclick = function(){
//            var timer = setTimeout(function(){
//                yktotal();
//            },1000)
//        }

    })
//    function yktotal() {
//        var row = $('#listDataGrid').datagrid('getData');
//        var total = row.rows[0]['totalMoney'];
//        if(total == null){
//            document.getElementById("yingkuitotal").innerHTML=0+" 元";
//        }
//        document.getElementById("yingkuitotal").innerHTML=total+" 元";
//    }


    function jisuan() {
        var row = $('#listDataGrid').datagrid('getData');
        var total = row.rows[0]['totalMoney'];
//        document.getElementById("yingkuitotal").innerHTML=total+" 元";
//        var total = 0;
//        for (var i = 0; i < row.rows.length; i++) {
//            total += row.rows[i]['difMoney'];
//        }
        var percent = $("input[name=percent]").val();
        var money = percent/100*total;
        var _money = -money;
        document.getElementById("fanyong").innerHTML=_money+" 元";
    }
</script>
</body>
</html>