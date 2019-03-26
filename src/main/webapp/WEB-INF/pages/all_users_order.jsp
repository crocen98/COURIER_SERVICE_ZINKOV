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
            <fmt:message key="order.courier_name" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.login" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.email" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.phone" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="order.start_time" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="order.finish_time" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="cargo_type" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="transport_type" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="order.status" bundle="${bundle}"/>
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${requestScope.orders}" varStatus="loop">

        <tr>
            <th scope="row">${loop.index + (param.page-1)*20}</th>
        <td>${item.courierFirstName} ${item.courierLastName}</td>
        <td>${item.courierLogin}</td>
        <td>${item.courierEmail}</td>
        <td>${item.courierPhone}</td>
        <td>${item.startTime}</td>
        <td>
            <c:if test="${item.finishTime == null}">
                --
            </c:if>
            <c:if test="${item.finishTime != null}">
                ${item.finishTime}
            </c:if>
        </td>
        <td>${item.cargoType}</td>
        <td>${item.transportType}</td>
            <td>${item.status}</td>


        </tr>
    </c:forEach>

    </tbody>
</table>
<div class="container d-flex justify-content-end">
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.servletContext.contextPath}/index?command=to_all_orders_page_command&page=1">Start</a>
            </li>
            <c:if test="${param.page > 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.servletContext.contextPath}/index?command=to_all_orders_page_command&page=${param.page - 1}">${param.page - 1}</a>
                </li>
            </c:if>
            <li class="page-item"><a class="page-link" style="background: darkgray"
                                     href="${pageContext.servletContext.contextPath}/index?command=to_all_orders_page_command&page=${param.page}">${param.page}</a>
            </li>
            <c:if test="${requestScope.orders_count > (param.page)*20}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.servletContext.contextPath}/index?command=to_all_orders_page_command&page=${param.page + 1}">${param.page + 1}</a>
                </li>
            </c:if>
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.servletContext.contextPath}/index?command=to_all_orders_page_command&page=<fmt:formatNumber value="${requestScope.orders_count/20 + 1}" maxFractionDigits="0"/>">End</a>
            </li>

            <script>

                var formStatyses = document.getElementsByClassName("form_status");
                for (var i = 0; i < formStatyses.length; ++i) {
                    formStatyses[i].addEventListener("submit", function (evt) {
                        evt.target.getElementsByTagName("input")[1].value = "${param.page}";
                    })
                }
            </script>
        </ul>
    </nav>
</div>

<jsp:include page="../frames/footer.jsp"/>