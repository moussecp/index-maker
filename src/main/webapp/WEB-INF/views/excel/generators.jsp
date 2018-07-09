<!DOCTYPE html>
<%@ page language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>html generators</title>

    <spring:url value="/css/bootstrap.min.css" var="cssUrl"/>
    <link href="${cssUrl}" rel="stylesheet">
</head>
<body>

<div id="container" class="container">

    <h1>
        HTML generators:
    </h1>

    <p>
        <spring:url value="/excel/list" var="rowContentUrl"/>
        <a href="${rowContentUrl}" class="btn btn-success">
            show rowContents
        </a>

        <spring:url value="/excel/index" var="showIndexUrl"/>
        <a href="${showIndexUrl}" class="btn btn-success">
            show index
        </a>
    </p>
    <p>
        <spring:url value="/excel/generateChapter1" var="generateChapter1Url"/>
        <a href="${generateChapter1Url}" class="btn btn-success">
            generate chap. 1 html
        </a>
    </p>
    <p>
        <spring:url value="/excel/generateChapter2" var="generateChapter2Url"/>
        <a href="${generateChapter2Url}" class="btn btn-success">
            generate chap. 2 html
        </a>
    </p>
    <p>
        <spring:url value="/excel/generateChapter3" var="generateChapter3Url"/>
        <a href="${generateChapter3Url}" class="btn btn-success">
            generate chap. 3 html
        </a>
    </p>



</div>

<%--<script src="http://code.jquery.com/jquery-latest.js"></script>--%>
<%--<spring:url value="/js/bootstrap.min.js" var="jsUrl"/>--%>
<%--<script src="${jsUrl}"></script>--%>
<%--<c:import url="../common/alert-script.jsp"/>--%>
<%--<c:import url="../common/success-script.jsp"/>--%>

</body>
</html>
