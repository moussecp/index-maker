<!DOCTYPE html>
<%@ page language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>first cells list</title>

    <spring:url value="/css/bootstrap.min.css" var="cssUrl"/>
    <link href="${cssUrl}" rel="stylesheet">
</head>
<body>

<div id="container" class="container">

    <h1>
        Load Excel file:
    </h1>

    <p>
        <spring:url value="/excel/add" var="addUrl"/>
        <a href="${addUrl}" class="btn btn-success">
            add
        </a>
    </p>

    <table class="table table-striped table-bordered table-hover">
        <tr>
            <th>id</th>
            <th>firstCell content</th>
        </tr>

        <c:forEach items="${firstCells}" var="firstCell">
            <tr>
                <td>${firstCell.id}</td>
                <td>${firstCell.firstCell}</td>
            </tr>
        </c:forEach>
    </table>

</div>

<%--<script src="http://code.jquery.com/jquery-latest.js"></script>--%>
<%--<spring:url value="/js/bootstrap.min.js" var="jsUrl"/>--%>
<%--<script src="${jsUrl}"></script>--%>
<%--<c:import url="../common/alert-script.jsp"/>--%>
<%--<c:import url="../common/success-script.jsp"/>--%>

</body>
</html>
