<%@ page language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${not empty successMessage}">
    <spring:message var="translatedSuccessMessage" code="${successMessage}"/>
    <script>
        $(document).ready(function () {
            $('#container').prepend('<div class="alert alert-block alert-success" style="margin-top: 10px;"><button class="close">x</button><p><strong>${translatedSuccessMessage}</strong></p></div>');
            $('.alert .close').click(function (e) {
                $(this).parent().hide();
            });
        });
    </script>
</c:if>


