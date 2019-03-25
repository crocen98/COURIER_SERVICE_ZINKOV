<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../frames/header.jsp"/>
<script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
        type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">


<div class="container">
    <div class="row">
        <div class="col-md-5">
            <h3>Your order:</h3>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.price" bundle="${bundle}"/>
                </strong> ${requestScope.order.price}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.distance" bundle="${bundle}"/>
                </strong> ${requestScope.distance}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.description" bundle="${bundle}"/>
                </strong> <c:out value="${requestScope.order.description}"/>
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.order_time" bundle="${bundle}"/>
                </strong> ${requestScope.order.startTime}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.cargo_type" bundle="${bundle}"/>
                </strong> ${requestScope.cargo_type.type}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.transport_type" bundle="${bundle}"/>
                </strong> ${requestScope.transport_type.transportType}
            </div>

            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.courier" bundle="${bundle}"/>
                </strong> ${requestScope.courier.login}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.phone" bundle="${bundle}"/>
                </strong> ${requestScope.courier.phone}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="form.email" bundle="${bundle}"/>
                </strong> ${requestScope.courier.email}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.point_a" bundle="${bundle}"/>
                </strong> <span id=firstPoint><fmt:message key="courier_order.loading" bundle="${bundle}"/></span>
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.point_b" bundle="${bundle}"/>
                </strong> <span id=secondPoint><fmt:message key="courier_order.loading" bundle="${bundle}"/></span>
            </div>

            <a href="${pageContext.servletContext.contextPath}/index?command=cancel_order">
                <button type="button" class="btn btn-danger">
                    <fmt:message key="courier_order.cancel_order" bundle="${bundle}"/>
                </button>
            </a>
        </div>
        <div class="col-md-7" id="map" style=" display:inline-block;width:100%;"></div>
    </div>
</div>

<jsp:include page="../frames/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/order_on_map.js"></script>
<script>
initMapOrders("${requestScope.order.startPoint}" ,"${requestScope.order.finishPoint}" );
</script>

