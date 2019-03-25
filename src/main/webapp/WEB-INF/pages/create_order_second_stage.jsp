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
                </strong> ${sessionScope.order.price}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.distance" bundle="${bundle}"/>

                </strong> ${requestScope.distance}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.description" bundle="${bundle}"/>
                </strong> <c:out value="${sessionScope.order.description}"/>
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.order_time" bundle="${bundle}"/>
                </strong> ${sessionScope.order.startTime}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.cargo_type" bundle="${bundle}"/>
                </strong> ${requestScope.cargo_type}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.transport_type" bundle="${bundle}"/>
                </strong> ${requestScope.transport_type}
            </div>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.point_a" bundle="${bundle}"/>

                </strong>
                     <span id=firstPoint>
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
        </div>
        <div class="col-md-7" id="map" style="display:inline-block;width:100%;"></div>
    </div>
    <hr style="margin-top: 50px;">

    <h3>
        <fmt:message key="couriers" bundle="${bundle}"/>:
    </h3>
    <div class="row" id="courier_cards">
        <c:forEach var="elem" items="${requestScope.couriers}">
            <div class="col-lg-4">
                <div class="our-team-main">

                    <div class="team-front">
                        <img src="http://placehold.it/110x110/9c27b0/fff?text=Dilip" class="img-fluid"/>
                        <h3>${elem.key.login}</h3>
                        <p>${elem.key.firstName} ${elem.key.lastName}</p>
                    </div>

                    <div class="team-back">
                        <div style="display: flex;   align-items: center; justify-content: center;">
                            <h1 class="mark_on_card" style="height: 100px;">
                                <fmt:message key="client_couriers.mark" bundle="${bundle}"/>: ${elem.value}</h1>
                        </div>

                        <form name="transport_form_${elem.key.id}"
                              action="${pageContext.servletContext.contextPath}/index?command=finish_creating_order"
                              method="POST">
                            <input name="courier_id" type="hidden" value="${elem.key.id}">
                            <button class="btn" style="color:red;text-align: right; margin-right: -10px">
                                <i class="fa fa-plus fa-2x "></i>
                                <h2 style="display: inline">Select</h2>
                            </button>
                        </form>
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>


</div>

<jsp:include page="../frames/footer.jsp"/>

<script>
    ymaps.ready(init);

    function init() {

        myMap = new ymaps.Map('map', {
            center: "${sessionScope.order.startPoint}".split(","),
            zoom: 6
        }, {
            searchControlProvider: 'yandex#search'
        });
        getAddress("${sessionScope.order.startPoint}", "firstPoint")
        getAddress("${sessionScope.order.finishPoint}", "secondPoint")


        myGeoObject = new ymaps.GeoObject({
            geometry: {
                type: "Point",
                coordinates: "${sessionScope.order.startPoint}".split(",")
            },
            properties: {
                iconContent: 'Point A',
            }
        }, {
            preset: 'islands#blackStretchyIcon',
            draggable: false
        });

        myGeoObject2 = new ymaps.GeoObject({
            geometry: {
                type: "Point",
                coordinates: "${sessionScope.order.finishPoint}".split(",")
            },
            properties: {
                iconContent: 'Point B',
            }
        }, {
            preset: 'islands#blackStretchyIcon',
            draggable: false
        });


        myMap.geoObjects
            .add(myGeoObject)
            .add(myGeoObject2)
    }


    function getAddress(coords, idElement) {
        var cordArr = coords.split(',');
        ymaps.geocode(cordArr).then(function (res) {
            var firstGeoObject = res.geoObjects.get(0);
            document.getElementById(idElement).textContent = firstGeoObject.getAddressLine();
        });
    }


</script>