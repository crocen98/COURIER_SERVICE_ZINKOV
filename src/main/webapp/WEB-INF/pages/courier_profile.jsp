<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
        type="text/javascript"></script>
<div class="modal fade" id="firstSelectTransport" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold">New transport type</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body mx-3">
                <form class="md-form mb-5" method="POST" accept-charset=UTF-8"
                      action="${pageContext.servletContext.contextPath}/index?command=new_transport_type_for_courier">
                    <select class="form-control " style="border-radius: 15px;" id="sel1" name="transport_type_id">
                        <c:forEach var="elem" items="${transport_types}" varStatus="status">
                            <option value="${elem.id}">${elem.transportType}</option>
                        </c:forEach>
                    </select>
                    <div class="d-flex justify-content-center" style="margin-top: 10px;">
                        <button class="btn btn-primary">
                            <c:if test="${transport_type == null}">
                                <fmt:message key="courier_profile.add_transport" bundle="${bundle}"/>

                            </c:if>
                            <c:if test="${transport_type != null}">
                                <fmt:message key="courier_profile.change_transport" bundle="${bundle}"/>
                            </c:if>

                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="add_cargo_type" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold">
                    <fmt:message key="all_cargo_types.new_cargo" bundle="${bundle}"/>
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body mx-3">
                <form class="md-form mb-5" method="POST" accept-charset=UTF-8"
                      action="${pageContext.servletContext.contextPath}/index?command=add_cargo_type_for_courier">
                    <select class="form-control " style="border-radius: 15px;" id="sel2" name="cargo_type_id">
                        <c:forEach var="elem" items="${all_cargo_types}" varStatus="status">
                            <option value="${elem.id}">${elem.type}</option>
                        </c:forEach>
                    </select>
                    <div class="d-flex justify-content-center" style="margin-top: 10px;">
                        <button class="btn btn-primary">
                            <fmt:message key="main.add" bundle="${bundle}"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="container">
    <h3>
        <fmt:message key="main.profile" bundle="${bundle}"/>

        Profile
    </h3>
    <div class="row">
        <div class="col-md-5">
            <h3>Your order:</h3>
            <div class="notice notice-info">
                <strong>
                    <fmt:message key="courier_order.transport_type" bundle="${bundle}"/>
                </strong> ${transport_type.transportType}
                <c:if test="${transport_type == null}">
                    <div class="text-left">
                        <br>
                        <button class="btn btn-primary btn-rounded mb-4" data-toggle="modal"
                                data-target="#firstSelectTransport">
                            <fmt:message key="main.add" bundle="${bundle}"/>
                        </button>
                    </div>
                </c:if>
                <c:if test="${transport_type != null}">
                    <div class="text-left">
                        <br>
                        <button class="btn btn-primary btn-rounded mb-4" data-toggle="modal"
                                data-target="#firstSelectTransport">
                            <fmt:message key="change_password.change" bundle="${bundle}"/>
                        </button>
                    </div>
                </c:if>
            </div>
            <c:if test="${transport_type !=null}">
                <div class="notice notice-info">
                    <strong>
                        <fmt:message key="courier_order.cargo_type" bundle="${bundle}"/>
                    </strong>
                    <ul>
                        <c:forEach var="type" items="${cargo_types}">
                            <li>${type.type}

                                <form name="transport_form_${type.id}" style="display: inline-block;"
                                      action="${pageContext.servletContext.contextPath}/index?command=delete_cargo_type_for_courier"
                                      method="POST">
                                    <input name="cargo_type_id" type="hidden" value="${type.id}">
                                    <button class="btn" style="color:red;text-align: right; margin-right: -10px">
                                        <i class="fas fa-window-close"></i>
                                    </button>
                                </form>
                            </li>

                        </c:forEach>
                    </ul>
                    <div class="text-left">
                        <br>
                        <button class="btn btn-primary btn-rounded mb-4" data-toggle="modal"
                                data-target="#add_cargo_type">
                            <fmt:message key="main.add" bundle="${bundle}"/>
                        </button>
                    </div>
                </div>
            </c:if>
            <div class="notice notice-info">
                <strong>Mark:</strong> ${mark}
            </div>
        </div>
        <div class="col-md-7">
            <h3>Last location: <span id="lastCordinates"> </span></h3>
            <div id="map" style="display:inline-block;width:100%; height: 500px;"></div>
            <div class="form-group">
                <div class="form-group">
                    <form action="${pageContext.servletContext.contextPath}/index?command=new_courier_position"
                          method="POST">
                        <input required type="text" class="form-control form-control-user"
                               id="coordinates"
                               placeholder="<fmt:message key="form.location" bundle="${bundle}"/>">
                        <input required id="сoordinatesInput" name="location" type="hidden"
                               value="${user.location}">
                        <button class="btn btn-primary" style="margin-top: 10px">
                            <fmt:message key="courier_profile.change_location" bundle="${bundle}"/>
                        </button>
                    </form>
                </div>

            </div>
        </div>
    </div>


</div>

<style>
    .our-team-main {
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

<script type="text/javascript">
    var globalCord;
    var cordName;
    var isInputAction = false;
    document.getElementById("coordinates").addEventListener("input", inputCordForm);

    function inputCordForm(event) {
        isInputAction = true;
        cordName = event.target.value;
        ymaps.ready(init);
        setTimeout(func, 100);

        function func() {
            console.log(globalCord + "  globalCord");
            document.getElementById("сoordinatesInput").value = globalCord;
            isInputAction = false;
        }
    }

    ymaps.ready(init);

    function init() {

        myMap = new ymaps.Map('map', {
            center: "${user.location}".split(","),
            zoom: 10
        }, {
            searchControlProvider: 'yandex#search'
        });

        var location = ymaps.geolocation;
        location.get({
            provider: 'yandex',
            mapStateAutoApply: true
        }).then(function (result) {
            console.log(result);
            console.log("${user.location}".split(","));
            // Красным цветом пометим положение, вычисленное через ip.
            result.geoObjects.position = "${user.location}".split(",");
            console.log(result.geoObjects.position + "POSITION");
            result.geoObjects.options.set('preset', 'islands#redCircleIcon');
            result.geoObjects.get(0).properties.set({
                balloonContentBody: 'Мое местоположение'
            });
            myMap.geoObjects.add(result.geoObjects);
        });

        var myPlacemark;
        if (isInputAction) {
            console.log(isInputAction);
            console.log(cordName);
            console.log("ISINPUTACTION");
            var myGeocoder = ymaps.geocode(cordName);
            var res = myGeocoder.then(
                function (result) {
                    var coordinates = result.geoObjects.get(0).geometry.getCoordinates();

                    globalCord = coordinates;
                },
                function (err) {
                    alert('Ошибка');
                }
            );

        } else {


            myMap.events.add('click', function (e) {
                var coords = e.get('coords');
                console.log("coords");
                console.log(coords);


                // Если метка уже создана – просто передвигаем ее.
                if (myPlacemark) {
                    myPlacemark.geometry.setCoordinates(coords);
                }
                // Если нет – создаем.
                else {
                    myPlacemark = createPlacemark(coords);
                    myMap.geoObjects.add(myPlacemark);
                    // Слушаем событие окончания перетаскивания на метке.
                    myPlacemark.events.add('dragend', function () {
                        getAddress(myPlacemark.geometry.getCoordinates());
                    });
                }
                getAddress(coords);
            });


            ///////////////////////


            ///////////////////////////////


            // Создание метки.
            function createPlacemark(coords) {
                return new ymaps.Placemark(coords, {
                    iconCaption: 'поиск...'
                }, {
                    preset: 'islands#violetDotIconWithCaption',
                    draggable: true
                });
            }

            getMyAdress("${user.location}".split(","));


            function getMyAdress(coords) {
                var myPlacemark2;

                myPlacemark2 = createPlacemark(coords);
                myMap.geoObjects.add(myPlacemark2);
                // Слушаем событие окончания перетаскивания на метке.
                myPlacemark2.events.add('dragend', function () {
                    getAddress(myPlacemark2.geometry.getCoordinates());
                });
                console.log("FINAL 1");

                console.log("FINAL 2");
                myPlacemark2.properties.set('iconCaption', 'поиск...');
                ymaps.geocode(coords).then(function (res) {
                    var firstGeoObject = res.geoObjects.get(0);

                    myPlacemark2.properties
                        .set({
                            iconCaption: [
                                firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                                firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                            ].filter(Boolean).join(', '),
                            balloonContent: firstGeoObject.getAddressLine()
                        });
                    console.log("FINAL ");
                    document.getElementById("lastCordinates").innerText = myPlacemark2.properties._data.balloonContent;
                });
            }


            // Определяем адрес по координатам (обратное геокодирование).
            function getAddress(coords) {
                myPlacemark.properties.set('iconCaption', 'поиск...');
                ymaps.geocode(coords).then(function (res) {
                    var firstGeoObject = res.geoObjects.get(0);

                    myPlacemark.properties
                        .set({
                            // Формируем строку с данными об объекте.
                            iconCaption: [
                                // Название населенного пункта или вышестоящее административно-территориальное образование.
                                firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                                // Получаем путь до топонима, если метод вернул null, запрашиваем наименование здания.
                                firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                            ].filter(Boolean).join(', '),
                            // В качестве контента балуна задаем строку с адресом объекта.
                            balloonContent: firstGeoObject.getAddressLine()
                        });

                    console.log(myPlacemark.properties._data.balloonContent);
                    document.getElementById("coordinates").value = myPlacemark.properties._data.balloonContent;
                    document.getElementById("сoordinatesInput").value = coords;
                });
            }
        }
    }


</script>
