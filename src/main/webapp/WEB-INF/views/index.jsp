<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<%@ page language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         session="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Index maker
    </title>

    <spring:url value="/css/bootstrap.min.css" var="cssUrl"/>
    <link href="${cssUrl}" rel="stylesheet">
</head>
<body>

<div id="container" class="container">
    <h1>Index maker
    </h1>

    <spring:url value="/excel/list" var="excelListUrl"/>
    <a href="${excelListUrl}" class="btn btn-large btn-block btn-primary">
        Excel list
    </a>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<spring:url value="/js/bootstrap.min.js" var="jsUrl"/>
<script src="${jsUrl}"></script>
</body>
</html>
