<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-17
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form>
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>新密码</label>
                    <input type="text" class="form-control span1" name="safePassword" placeholder="新密码:20字以内"  maxlength="100" required >
                </div>
            </div><br/>
            <button type="submit" style="float: right" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3Agent/updatePwd1')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>