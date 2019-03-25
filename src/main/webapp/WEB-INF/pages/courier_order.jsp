<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
        type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<div class="container">
    <c:if test="${requestScope.order != null}">
        <div class="row">
            <div class="col-md-5">
                <h3>
                    <fmt:message key="courier_order.your_order" bundle="${bundle}"/>
                </h3>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.price" bundle="${bundle}"/>
                    </strong>
                        <c:out value="${requestScope.order.price}"/>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.distance" bundle="${bundle}"/>
                    </strong> <c:out value="${requestScope.distance}"/>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.description" bundle="${bundle}"/>
                    </strong>
                    <c:out value="${requestScope.order.description}"/>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.order_time" bundle="${bundle}"/>
                    </strong>
                    <c:out value="${requestScope.order.startTime}"/>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.cargo_type" bundle="${bundle}"/>
                    </strong> <c:out value="${requestScope.cargo_type.type}"/>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.transport_type" bundle="${bundle}"/>
                    </strong> <c:out value="${requestScope.transport_type.transportType}"/>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.point_a" bundle="${bundle}"/>
                    </strong> <span id=firstPoint>
                    <fmt:message key="courier_order.loading" bundle="${bundle}"/>
                </span>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.point_b" bundle="${bundle}"/>
                    </strong> <span id=secondPoint>
                    <fmt:message key="courier_order.loading" bundle="${bundle}"/>
                    </span>
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.client" bundle="${bundle}"/>
                    </strong> ${requestScope.client.firstName} ${requestScope.client.lastName}
                </div>
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.number" bundle="${bundle}"/>
                    </strong> ${requestScope.client.phone}
                </div>
                <c:choose>
                    <c:when test="${requestScope.order_status == 'ORDERED'}">
                        <div class="notice notice-default">
                            <strong>
                                <fmt:message key="courier_order.status_word" bundle="${bundle}"/>
                            </strong>
                            <fmt:message key="courier_order.status_ordered" bundle="${bundle}"/>

                        </div>
                    </c:when>
                    <c:when test="${requestScope.order_status == 'PERFORMED'}">
                        <div class="notice notice-success">
                            <strong>
                                <fmt:message key="courier_order.status_word" bundle="${bundle}"/>
                            </strong>
                            <fmt:message key="courier_order.status_performing" bundle="${bundle}"/>
                        </div>
                    </c:when>
                </c:choose>

            </div>
            <div class="col-md-7" id="map" style="display:inline-block;width:100%;"></div>
        </div>

        <c:if test="${requestScope.order_status == 'ORDERED'}">
            <a href="${pageContext.servletContext.contextPath}/index?command=start_perfoming_order_command">
                <button type="button" class="btn btn-success">
                    <fmt:message key="courier_order.accept_order" bundle="${bundle}"/>
                </button>
            </a>
            <a href="${pageContext.servletContext.contextPath}/index?command=cancel_order">
                <button type="button" class="btn btn-danger">
                    <fmt:message key="courier_order.cancel_order" bundle="${bundle}"/>
                </button>
            </a>
        </c:if>

        <c:if test="${requestScope.order_status == 'PERFORMED'}">
            <a href="${pageContext.servletContext.contextPath}/index?command=finish_perfoming_order_command">
                <button type="button" class="btn btn-success">
                    <fmt:message key="courier_order.finish_order" bundle="${bundle}"/>
                </button>
            </a>
        </c:if>

    </c:if>


    <c:if test="${requestScope.order == null}">


        <div class="notice notice-warning">
            <h1>
                <fmt:message key="courier_order.not_order" bundle="${bundle}"/>
            </h1>
        </div>
    </c:if>
</div>

<jsp:include page="../frames/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/order_on_map.js"></script>
<script>
    initMapOrders("${requestScope.order.startPoint}" ,"${requestScope.order.finishPoint}" );
</script>