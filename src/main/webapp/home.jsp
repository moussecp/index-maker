<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>First Cells</title>
</head>
<body>

<table>
    <c:forEach items="${firstCells}" var="item">
        <tr>
            <td><c:out value="${firstCell}" /></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>