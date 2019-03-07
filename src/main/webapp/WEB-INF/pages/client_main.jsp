<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>




<tag:error errorMap="errors"/>
HI USER ${sessionScope.user.login} ${sessionScope.user.userRole} ${sessionScope.user == null}


<jsp:include page="../frames/footer.jsp"/>