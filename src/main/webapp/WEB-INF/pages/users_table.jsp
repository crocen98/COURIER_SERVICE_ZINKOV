<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<tag:error errorMap="errors"/>


<h3>My couriers</h3>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">First name</th>
        <th scope="col">Last name</th>
        <th scope="col">Login</th>
        <th scope="col">Phone</th>
        <th scope="col">Email</th>
        <th scope="col">Role</th>
        <th scope="col">Status</th>
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
        <th scope="row">${loop.index}</th>
        <td>${item.firstName}</td>
        <td>${item.lastName}</td>
        <td>${item.login}</td>
        <td>${item.phone}</td>
        <td>${item.email}</td>
        <td>${item.userRole}</td>
        <td>
                ${item.userStatus}

            <c:if test="${item.id != sessionScope.user.id}">
                <form method="POST" action="${pageContext.servletContext.contextPath}/couriers?command=change_user_status">
                    <input type="hidden" value="${item.id}" name="user_id">
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

<jsp:include page="../frames/footer.jsp"/>