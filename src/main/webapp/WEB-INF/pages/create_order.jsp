<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
        type="text/javascript">
</script>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<div class="container">
    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <div class="row ">
                <div class="col-lg-7 text-center" style="margin: auto;">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">
                                <fmt:message key="create_order.new_order" bundle="${bundle}"/>
                            </h1>
                        </div>
                        <form class="user" method="POST"
                              action="${pageContext.servletContext.contextPath}/index?command=create_order_page_second_stage">
                            <div class="form-group">
                                <textarea id="descrField" required type="text" class="form-control " name="description"
                                          placeholder="description"></textarea>
                            </div>

                            <div class="form-group row">
                                <label for="sel1">
                                    <fmt:message key="transport_type" bundle="${bundle}"/>
                                    Transport type
                                </label>
                                <select class="form-control " style="border-radius: 15px;" id="sel1"
                                        name="transport_type">
                                    <c:forEach var="transport" items="${transport_types}" varStatus="status">
                                        <option>${transport.transportType}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group row">
                                <label for="sel2">
                                    <fmt:message key="cargo_type" bundle="${bundle}"/>

                                </label>
                                <select class="form-control " style="border-radius: 15px;" id="sel2" name="cargo_type">
                                    <c:forEach var="cargo" items="${cargo_types}" varStatus="status">
                                        <option>${cargo.type}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input id="date" required type="date" pattern="^\d{1,2}\.\d{1,2}\.\d{4}$"
                                           class="form-control" placeholder="First Name">
                                </div>
                                <div class="col-sm-6 input-group date">
                                    <input id="time" required type="time" class="form-control" placeholder="Last Name"
                                           pattern="^\d{2}:\d{2}">
                                </div>
                                <input required id="date_time" name="start_time" type="hidden" value="">
                            </div>


                            <div id="radio_cord" class="form-group" style="text-align: left">
                                <div class="radio">
                                    <label><input id="radio_1" type="radio" name="point_type" checked>
                                        <fmt:message key="courier_order.point_a" bundle="${bundle}"/>
                                    </label>
                                </div>
                                <div class="radio">
                                    <label><input type="radio" id="radio_2" name="point_type">
                                        <fmt:message key="courier_order.point_b" bundle="${bundle}"/>
                                    </label>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="form-group">
                                    <input required type="text" class="form-control form-control-user" disabled
                                           id="coordinates"
                                           placeholder="Point A">
                                    <input required id="сoordinatesInput" name="start_point" type="hidden">
                                </div>

                                <div class="form-group">
                                    <input required type="text" class="form-control form-control-user" disabled
                                           id="coordinatesone"
                                           placeholder="Point B">
                                    <input required id="сoordinatesInputone" name="finish_point" type="hidden">
                                </div>
                            </div>


                            <div id="map" style="width: 100%; height: 400px"></div>
                            <div class=" d-flex justify-content-center" style="margin-top: 20px">
                                <button disabled class="btn btn-primary btn-user btn-block" id="but">
                                    <fmt:message key="courier_order.continue" bundle="${bundle}"/>
                                </button>
                            </div>
                        </form>
                        <hr>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../frames/footer.jsp"/>
       <script src="${pageContext.request.contextPath}/js/create_order_script.js"></script>