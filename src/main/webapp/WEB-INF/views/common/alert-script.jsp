<%@ page language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${not empty exception}">
    <spring:message var="exceptionMessage" code="${exception.message}"/>
    <script>
        $(document).ready(function () {
            $('#container').prepend('<div class="alert alert-block alert-error warning" style="margin-top: 10px;"><button class="close">x</button><p><strong>${exceptionMessage}</strong></p></div>');
            $('.alert .close').click(function (e) {
                $(this).parent().hide();
            });
        });
    </script>
</c:if>


	