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
            <!-- Nested Row within Card Body -->
            <div class="row ">
                <%--<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>--%>
                <div class="col-lg-7 text-center" style="margin: auto;">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">
                                <fmt:message key="create_order.new_order" bundle="${bundle}"/>
                            </h1>
                        </div>
                        <%--<c:forEach var="elem" items="${requestScope.roles}" varStatus="status">--%>
                        <%--${elem} 1000--%>
                        <%--</c:forEach>--%>
                        <form class="user" method="POST"
                              action="${pageContext.servletContext.contextPath}/index?command=create_order_page_second_stage">
                            <div class="form-group">
                                <textarea required type="text" class="form-control " name="description"
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
                                    <input id="date" required type="date" pattern="^\d{1,2}\.\d{1,2}\.\d{4}$" class="form-control" placeholder="First Name">
                                </div>
                                <div class="col-sm-6 input-group date">
                                    <input  id="time" required type="time" class="form-control" placeholder="Last Name" pattern="^\d{2}:\d{2}">
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
                                    <input required id="сoordinatesInput" name="start_point" type="hidden"
                                           value="53.8620412579027,27.66345453515624">
                                </div>

                                <div class="form-group">
                                    <input required type="text" class="form-control form-control-user" disabled
                                           id="coordinatesone"
                                           placeholder="Point B">
                                    <input required id="сoordinatesInputone" name="finish_point" type="hidden"
                                           value="53.8620412579027,27.66345453515624">
                                </div>
                            </div>


                            <div id="map" style="width: 100%; height: 400px"></div>
                            <div class=" d-flex justify-content-center" style="margin-top: 20px">
                                <button class="btn btn-primary btn-user btn-block">
                                    <fmt:message key="courier_order.continue" bundle="${bundle}"/>
                                </button>
                            </div>
                        </form>
                        <hr>
                    </div>
                    <%--</div>--%>
                </div>
                <%--</div>--%>
            </div>
        </div>


        <jsp:include page="../frames/footer.jsp"/>

        <script type="text/javascript">

            // Функция ymaps.ready() будет вызвана, когда
            // загрузятся все компоненты API, а также когда будет готово DOM-дерево.
            var globalCord;
            var cordName;
            var isInputAction = false;

            var checked_rad_but_id;

            var date;
            var time;

            document.getElementById("date").addEventListener("input", function (ev) {
                date = ev.target.value;

                document.getElementById("date_time").setAttribute("value", date + ', ' + time);
            });

            document.getElementById("time").addEventListener("input", function (ev) {
                time = ev.target.value;
                document.getElementById("date_time").setAttribute("value", date + ', ' + time);
            });

            document.getElementById("radio_cord").addEventListener("click", function (ev) {
                if (ev.target.getAttribute("id") == 'radio_1') {
                    checked_rad_but_id = 'radio_1';
                } else if (ev.target.getAttribute("id") == 'radio_2') {
                    checked_rad_but_id = 'radio_2';
                }
            });

            var rad2 = document.getElementsByName('point_type');
            for (var i = 0; i < rad2.length; i++) {
                if (rad2[i].checked) {
                    checked_rad_but_id = rad2[i].getAttribute("id");
                    break;
                }
            }

            console.log(checked_rad_but_id);
            console.log("checked_rad_but_id");
            if (checked_rad_but_id == "radio_1") {
                document.getElementById("coordinates").addEventListener("input", inputCordForm);
            }
            {
                document.getElementById("coordinatesone").addEventListener("input", inputCordForm);

            }

            function inputCordForm(event) {
                isInputAction = true;
                console.log("INPUT");
                cordName = event.target.value;
                ymaps.ready(init);
                setTimeout(func, 1000);

                function func() {
                    console.log(globalCord + "  globalCord");
                    var rad = document.getElementsByName('point_type');
                    for (var i = 0; i < rad.length; i++) {
                        if (rad[i].checked) {
                            checked_rad_but_id = rad[i].getAttribute("id");
                            break;
                        }
                    }
                    console.log(checked_rad_but_id);
                    console.log("checked_rad_but_id");
                    if (checked_rad_but_id == "radio_1") {
                        document.getElementById("сoordinatesInput").value = globalCord;
                    } else {
                        document.getElementById("сoordinatesInputone").value = globalCord;

                    }
                    isInputAction = false;
                }
            }

            ymaps.ready(init);

            function init() {

                myMap = new ymaps.Map('map', {
                    center: [53.54588147535851, 28.113893988281244],
                    zoom: 10
                }, {
                    searchControlProvider: 'yandex#search'
                });

                var location = ymaps.geolocation;
                location.get({
                    provider: 'yandex',
                    mapStateAutoApply: true
                }).then(function (result) {
                    // Красным цветом пометим положение, вычисленное через ip.
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

                            if (checked_rad_but_id == "radio_1") {
                                document.getElementById("coordinates").value = myPlacemark.properties._data.balloonContent;
                                document.getElementById("сoordinatesInput").value = coords;
                            } else {
                                document.getElementById("coordinatesone").value = myPlacemark.properties._data.balloonContent;
                                document.getElementById("сoordinatesInputone").value = coords;
                            }
                        });
                    }
                }
            }
        </script>