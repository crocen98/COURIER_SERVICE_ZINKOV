<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<tag:error errorMap="errors"/>


<h3>My couriers</h3>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">
            <fmt:message key="form.firstname" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.lastnmae" bundle="${bundle}"/>

        </th>
        <th scope="col">
            <fmt:message key="form.login" bundle="${bundle}"/>

        </th>
        <th scope="col">
            <fmt:message key="form.phone" bundle="${bundle}"/>

        </th>
        <th scope="col">
            <fmt:message key="form.email" bundle="${bundle}"/>

        </th>
        <th scope="col">
            <fmt:message key="main.role" bundle="${bundle}"/>

        </th>
        <th scope="col">
            <fmt:message key="main.status" bundle="${bundle}"/>

        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${requestScope.users}" varStatus="loop">
        <tr>
        <c:choose>
            <c:when test="${item.id != sessionScope.user.id}">
                <tr>
            </c:when>
            <c:otherwise>
                <tr style="background:#36b9cc; color: white;">
            </c:otherwise>
        </c:choose>
        <th scope="row">${loop.index + (param.page-1)*10}</th>
        <td>${item.firstName}</td>
        <td>${item.lastName}</td>
        <td>${item.login}</td>
        <td>${item.phone}</td>
        <td>${item.email}</td>
        <td>${item.userRole}</td>
        <td>
                ${item.userStatus}

            <c:if test="${item.id != sessionScope.user.id}">
                <form class="form_status" method="POST" action="${pageContext.servletContext.contextPath}/index?command=change_user_status">
                    <input type="hidden" value="${item.id}" name="user_id">
                    <input type="hidden" name="page">

                <c:choose>
                    <c:when test="${item.userStatus != 'ACTIVE'}">
                    <button class="btn btn-danger" style="">
                        Разблокировать
                        </c:when>
                        <c:otherwise>
                        <button class="btn btn-warning" style="">
                            Заблокировать
                            </c:otherwise>
                            </c:choose>
                        </button>
                </form>
            </c:if>
        </td>
        </tr>
    </c:forEach>

    </tbody>
</table>
<div class="container d-flex justify-content-end">
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.servletContext.contextPath}/index?command=to_all_users_page_command&page=1">Start</a>
            </li>
            <c:if test="${param.page > 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.servletContext.contextPath}/index?command=to_all_users_page_command&page=${param.page - 1}">${param.page - 1}</a>
                </li>
            </c:if>
            <li class="page-item" ><a class="page-link" style="background: darkgray"
                                     href="${pageContext.servletContext.contextPath}/index?command=to_all_users_page_command&page=${param.page}">${param.page}</a>
            </li>
            <c:if test="${requestScope.users_count > (param.page)*10}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.servletContext.contextPath}/index?command=to_all_users_page_command&page=${param.page + 1}">${param.page + 1}</a>
                </li>
            </c:if>
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.servletContext.contextPath}/index?command=to_all_users_page_command&page=<fmt:formatNumber value="${requestScope.users_count/10 + 1}" maxFractionDigits="0"/>">End</a>
            </li>

<script>

    var formStatyses = document.getElementsByClassName("form_status");
    for(var i=0;i<formStatyses.length;++i){
        formStatyses[i].addEventListener("submit" , function (evt) {
            evt.target.getElementsByTagName("input")[1].value = "${param.page}";
        })
    }
</script>
        </ul>
    </nav>
</div>

<jsp:include page="../frames/footer.jsp"/>