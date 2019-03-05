
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<title>Main page</title>
<body>



<c:if test="${sessionScope.user != null}">
    <jsp:forward page="WEB-INF/pages/client_main.jsp"/>

</c:if>
    <jsp:forward page="WEB-INF/pages/main.jsp"/>
</body>
</html>