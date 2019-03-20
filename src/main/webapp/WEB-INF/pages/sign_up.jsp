<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:requestEncoding value="UTF-8"/>

<c:choose>
    <c:when test="${not empty requestScope.lang}">
        <fmt:setLocale value="${requestScope.lang}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="language" var="bundle" scope="application"/>


<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
            type="text/javascript">
    </script>
    <title>SB Admin 2 - Register</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/css/fontawesome-free/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">
<style>

    input:invalid {
        border-color: red;
    }

    input:valid {
        border-color: green;
    }

    .typesTransport {
        height: 200px;
    }
</style>
<div class="container">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            <strong>
                <fmt:message key="error.tag.letdown" bundle="${bundle}"/>
            </strong><fmt:message key="${param.error}" bundle="${bundle}"/>
        </div>
    </c:if>
    <tag:error errorMap="errors"/>
    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4"><fmt:message key="signup.formname"
                                                                           bundle="${bundle}"/></h1>
                        </div>
                        <form class="user" method="POST"
                              action="${pageContext.servletContext.contextPath}/couriers?command=register_command">
                            <div class="form-group row">
                                <label for="sel1">
                                    <fmt:message key="signup.rolecheck" bundle="${bundle}"/>
                                </label>
                                <select class="form-control " style="border-radius: 15px;" id="sel1" name="user_role">
                                    <c:forEach var="elem" items="${user_roles}" varStatus="status">
                                        <option>${elem.role}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <div id="label_login">
                                    <label for="logininput" id="logininputlabel">
                                        <fmt:message key="signup.logincheck" bundle="${bundle}"/>
                                    </label>
                                </div>
                                <input id="logininput" oninput="loadXMLDoc(this)" required type="text"
                                       class="form-control form-control-user"
                                       pattern="(\w|\d|-|_){1,35}" name="login"
                                       placeholder="<fmt:message key="form.login" bundle="${bundle}"/>">
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input required type="text" class="form-control form-control-user"
                                           pattern="^(\w|\d|-|[a-яА-Я]){1,35}$" name="first_name"
                                           placeholder="<fmt:message key="form.firstname" bundle="${bundle}"/>">
                                </div>
                                <div class="col-sm-6">
                                    <input required type="text" class="form-control form-control-user"
                                           pattern="^(\w|\d|-|[a-яА-Я]){1,35}$" name="last_name"
                                           placeholder="<fmt:message key="form.lastnmae" bundle="${bundle}"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <input required type="email" class="form-control form-control-user" name="email"
                                       placeholder="<fmt:message key="form.email" bundle="${bundle}"/>">
                            </div>

                            <div class="form-group">
                                <input required type="phone"
                                       pattern="^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$"
                                       class="form-control form-control-user" name="phone"
                                       placeholder="<fmt:message key="form.phone" bundle="${bundle}"/>">
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input required type="password" pattern="(\w|\d|-|_){1,35}"
                                           class="form-control form-control-user"
                                           id="exampleInputPassword" placeholder="<fmt:message key="form.password" bundle="${bundle}"/>" name="password">
                                </div>
                                <div class="col-sm-6">
                                    <input required type="password" pattern="(\w|\d|-|_){1,35}"
                                           class="form-control form-control-user"
                                           id="exampleRepeatPassword" placeholder="<fmt:message key="form.repeatpassword" bundle="${bundle}"/>">
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="form-group">
                                    <input required type="text" class="form-control form-control-user" disabled
                                           id="coordinates"
                                           placeholder="<fmt:message key="form.location" bundle="${bundle}"/>">
                                    <input required id="сoordinatesInput" name="location" type="hidden"
                                           value="53.8620412579027,27.66345453515624">
                                </div>

                            </div>
                            <div class=" d-flex justify-content-center">
                                <button id="but_sign_up" class="btn btn-primary btn-user btn-block"><fmt:message key="signup.formname" bundle="${bundle}"/>
                                </button>
                            </div>
                        </form>
                        <hr>
                        <div class="text-center">
                            <a class="small"
                               href="${pageContext.servletContext.contextPath}/couriers?command=to_password_recovery_page"><fmt:message key="signup.forgotpassword" bundle="${bundle}"/></a>
                        </div>
                        <div class="text-center">
                            <a class="small"
                               href="${pageContext.servletContext.contextPath}/couriers?command=to_log_in_page"><fmt:message key="signup.alreadyhaveaccount" bundle="${bundle}"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="map" style="width: 100%; height: 400px"></div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/js/jquery/jquery.easing.min.js"></script>

<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>


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

            var myCircle = ymaps.Circle([
                // Координаты центра круга.
                [53.91071905554657, 27.861208441406248],
                // Радиус круга в метрах.
                10000
            ], {
                // Описываем свойства круга.
                // Содержимое балуна.
                balloonContent: "Радиус круга - 10 км",
                // Содержимое хинта.
                hintContent: "Подвинь меня"
            }, {
                // Задаем опции круга.
                // Включаем возможность перетаскивания круга.
                draggable: true,
                // Цвет заливки.
                // Последний байт (77) определяет прозрачность.
                // Прозрачность заливки также можно задать используя опцию "fillOpacity".
                fillColor: "#DB709377",
                // Цвет обводки.
                strokeColor: "#990066",
                // Прозрачность обводки.
                strokeOpacity: 0.8,
                // Ширина обводки в пикселях.
                strokeWidth: 5
            });
            myMap.geoObjects.add(myCircle);

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

                    console.log(myPlacemark.properties._data.balloonContent);
                    document.getElementById("coordinates").value = myPlacemark.properties._data.balloonContent;
                    document.getElementById("сoordinatesInput").value = coords;
                });
            }
        }
    }


    function loadXMLDoc(userLogin) {
        console.log("ACTIONNNNNNNNNNN");
        var xmlhttp;

        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        } else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.status == 200) {
                var isValidLogin = xmlhttp.responseText;
                var labelItem = document.getElementById("label_login");
                console.log(isValidLogin);
                labelItem.innerHTML = xmlhttp.responseText;
                if (document.getElementById("logininputlabel").getAttribute("class") == "disabled") {
                    document.getElementById("but_sign_up").disabled = true;
                } else {
                    document.getElementById("but_sign_up").disabled = false;
                }
            } else {

            }
        };

        xmlhttp.open("POST", "AjaxServlet", true);
        xmlhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("command=check_login&login=" + userLogin.value);
    }

</script>

</body>

</html>
