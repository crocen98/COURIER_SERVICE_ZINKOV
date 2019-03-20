<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
        type="text/javascript"></script>

<div class="container">
    <c:if test="${requestScope.order != null}">
        <div class="row">
            <div class="col-md-5">
                <h3>Your order:</h3>
                <div class="notice notice-info">
                    <strong>Price:</strong> ${requestScope.order.price}
                </div>
                <div class="notice notice-info">
                    <strong>Distance:</strong> ${requestScope.distance}
                </div>
                <div class="notice notice-info">
                    <strong>Description:</strong> <c:out value="${requestScope.order.description}"/>
                </div>
                <div class="notice notice-info">
                    <strong>Order time:</strong> ${requestScope.order.startTime}
                </div>
                <div class="notice notice-info">
                    <strong>Cargo type:</strong> ${requestScope.cargo_type.type}
                </div>
                <div class="notice notice-info">
                    <strong>Transport type:</strong> ${requestScope.transport_type.transportType}
                </div>
                <div class="notice notice-info">
                    <strong>Point A:</strong> <span id=firstPoint>loading...</span>
                </div>
                <div class="notice notice-info">
                    <strong>Point B:</strong> <span id=secondPoint>loading...</span>
                </div>
                <div class="notice notice-info">
                    <strong>Client:</strong> ${requestScope.client.firstName} ${requestScope.client.lastName}
                </div>
                <div class="notice notice-info">
                    <strong>Number:</strong> ${requestScope.client.phone}
                </div>
                <c:choose>
                    <c:when test="${requestScope.order_status == 'ORDERED'}">
                        <div class="notice notice-default">
                            <strong>Status:</strong> ORDERED
                        </div>
                    </c:when>
                    <c:when test="${requestScope.order_status == 'PERFORMED'}">
                        <div class="notice notice-success">
                            <strong>Status</strong> PERFORMING
                        </div>
                    </c:when>
                </c:choose>

            </div>
            <div class="col-md-7" id="map" style="display:inline-block;width:100%;"></div>
        </div>

        <c:if test="${requestScope.order_status == 'ORDERED'}">
            <a href="${pageContext.servletContext.contextPath}/couriers?command=start_perfoming_order_command">
                <button type="button" class="btn btn-success">Accept order</button>
            </a>
            <a href="${pageContext.servletContext.contextPath}/couriers?command=cancel_order">
                <button type="button" class="btn btn-danger">Cancel order</button>
            </a>
        </c:if>

        <c:if test="${requestScope.order_status == 'PERFORMED'}">
            <a href="${pageContext.servletContext.contextPath}/couriers?command=finish_perfoming_order_command">
                <button type="button" class="btn btn-success">Finish order</button>
            </a>
        </c:if>

    </c:if>


    <c:if test="${requestScope.order == null}">


        <div class="notice notice-warning">
            <h1> You didn't have active order!</h1>
        </div>
    </c:if>
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