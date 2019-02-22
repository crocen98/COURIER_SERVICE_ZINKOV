<%--<html>--%>
<%--<body>--%>
<%--<h2>Hello World!</h2>--%>
<%--</body>--%>
<%--</html>--%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<title>Main page</title>
<body>
<jsp:forward page="${pageContext.servletContext.contextPath}?command=all_cargo_types"></jsp:forward>
</body>
</html>