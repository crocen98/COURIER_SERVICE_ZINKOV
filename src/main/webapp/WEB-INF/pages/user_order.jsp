<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../frames/header.jsp"/>
<script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
        type="text/javascript"></script>


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
            <%--<div class="notice notice-info">--%>
            <%--<strong>Status:</strong> ${requestScope.order.orderStatus}--%>
            <%--</div>--%>
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

<style> .our-team-main {
    width: 100%;
    height: auto;
    background: #fff;
    text-align: center;
    border-radius: 10px;
    overflow: hidden;
    position: relative;
    transition: 0.5s;
    margin-bottom: 28px;
}

.mark_on_card {
    text-align: center;
    margin: auto;
    height: 100%;
}

#courier_cards {
    padding: 30px;
}

.our-team-main img {
    border-radius: 50%;
    margin-bottom: 20px;
    width: 90px;
}

.our-team-main h3 {
    font-size: 20px;
    font-weight: 700;
}

.our-team-main p {
    margin-bottom: 0;
}

.team-back {
    width: 100%;
    height: auto;
    position: absolute;
    top: 0;
    left: 0;
    padding: 5px 15px 0 15px;
    text-align: left;
    background: #fff;

}

.team-front {
    width: 100%;
    height: auto;
    position: relative;
    z-index: 10;
    background: #fff;
    padding: 15px;
    bottom: 0px;
    transition: all 0.5s ease;
}

.our-team-main:hover .team-front {
    bottom: -200px;
    transition: all 0.5s ease;
}

.our-team-main:hover {
    border-color: #777;
    transition: 0.5s;

}

.notice {
    padding: 15px;
    background-color: #fafafa;
    border-left: 6px solid #7f7f84;
    margin-bottom: 10px;
    -webkit-box-shadow: 0 5px 8px -6px rgba(0, 0, 0, .2);
    -moz-box-shadow: 0 5px 8px -6px rgba(0, 0, 0, .2);
    box-shadow: 0 5px 8px -6px rgba(0, 0, 0, .2);
}

.notice-sm {
    padding: 10px;
    font-size: 80%;
}

.notice-lg {
    padding: 35px;
    font-size: large;
}

.notice-success {
    border-color: #80D651;
}

.notice-success > strong {
    color: #80D651;
}

.notice-info {
    border-color: #45ABCD;
}

.notice-info > strong {
    color: #45ABCD;
}

.notice-warning {
    border-color: #FEAF20;
}

.notice-warning > strong {
    color: #FEAF20;
}

.notice-danger {
    border-color: #d73814;
}

.notice-danger > strong {
    color: #d73814;
}
</style>
<jsp:include page="../frames/footer.jsp"/>

<script>
    ymaps.ready(init);

    function init() {

        myMap = new ymaps.Map('map', {
            center: "${requestScope.order.startPoint}".split(","),
            zoom: 6
        }, {
            searchControlProvider: 'yandex#search'
        });
        getAddress("${requestScope.order.startPoint}", "firstPoint")
        getAddress("${requestScope.order.finishPoint}", "secondPoint")


        myGeoObject = new ymaps.GeoObject({
            geometry: {
                type: "Point",
                coordinates: "${requestScope.order.startPoint}".split(",")
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
                coordinates: "${requestScope.order.finishPoint}".split(",")
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