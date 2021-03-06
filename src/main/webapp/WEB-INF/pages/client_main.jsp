<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../frames/header.jsp"/>

<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<tag:error errorMap="errors"/>

<c:set var="parameters_main_page" value="${pageContext.request.queryString}"/>
<c:set var="command" value="command"/>
<c:if test="${fn:contains(parameters_main_page, command)}">
<c:set var="paramsStringsWithCommandParameter" value="${fn:substringBefore(parameters_main_page, command)}"/>
<c:set var="parameters_main_page" value="${fn:substring(paramsStringsWithCommandParameter, 0, fn:length(paramsStringsWithCommandParameter) - 1)}"/>
</c:if>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h3><fmt:message key="main.profile" bundle="${bundle}"/>
            </h3>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.login" bundle="${bundle}"/>:
                </strong> ${sessionScope.user.login}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.firstname" bundle="${bundle}"/>:

                </strong> ${sessionScope.user.firstName}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.lastnmae" bundle="${bundle}"/>:
                </strong> <c:out value="${sessionScope.user.lastName}"/>
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="main.role" bundle="${bundle}"/>:
                </strong> ${sessionScope.user.userRole}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="main.status" bundle="${bundle}"/>:
                </strong> ${sessionScope.user.userStatus}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.phone" bundle="${bundle}"/>:
                </strong> ${sessionScope.user.phone}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.email" bundle="${bundle}"/>:
                </strong> ${sessionScope.user.email}
            </div>
        </div>
    </div>
</div>


<jsp:include page="../frames/footer.jsp"/>