<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>

<head>
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

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                        </div>
                        <%--<c:forEach var="elem" items="${requestScope.roles}" varStatus="status">--%>
                        <%--${elem} 1000--%>
                        <%--</c:forEach>--%>
                        <form class="user" method="POST"
                              action="${pageContext.servletContext.contextPath}?command=register_command">
                            <div class="form-group row">
                                <label for="sel1">Select list:</label>
                                <select class="form-control " style="border-radius: 15px;" id="sel1" name="user_role">
                                    <c:forEach var="elem" items="${user_roles}" varStatus="status">
                                        <option>${elem.role}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input required type="text" class="form-control form-control-user"
                                           pattern="(\w|\d|-){1,35}" name="first_name"
                                           placeholder="First Name">
                                </div>
                                <div class="col-sm-6">
                                    <input required type="text" class="form-control form-control-user"
                                           pattern="(\w|\d|-){1,35}" name="last_name"
                                           placeholder="Last Name">
                                </div>
                            </div>
                            <div class="form-group">
                                <input required type="email" class="form-control form-control-user" name="email"
                                       placeholder="Email Address">
                            </div>

                            <div class="form-group">
                                <input required type="phone"
                                       pattern="^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$"
                                       class="form-control form-control-user" name="phone"
                                       placeholder="Phone">
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input required type="password" pattern="(\w|\d|-){1,35}"
                                           class="form-control form-control-user"
                                           id="exampleInputPassword" placeholder="Password" name="password">
                                </div>
                                <div class="col-sm-6">
                                    <input required type="password" pattern="(\w|\d|-){1,35}"
                                           class="form-control form-control-user"
                                           id="exampleRepeatPassword" placeholder="Repeat Password">
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="form-group">
                                    <input required type="text" class="form-control form-control-user" id="location"
                                           placeholder="Location">
                                    <input required id="сoordinatesInput" name="coordinates" type="hidden" value="">
                                </div>

                            </div>
                            <div class=" d-flex justify-content-center">
                                <button class="btn btn-primary btn-user btn-block">Register Account</button>
                            </div>
                        </form>
                        <hr>
                        <div class="text-center">
                            <a class="small" href="forgot-password.html">Forgot Password?</a>
                        </div>
                        <div class="text-center">
                            <a class="small" href="login.html">Already have an account? Login!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="map" style="width: 100%; height: 400px"></div>
    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>


<script type="text/javascript">

    // Функция ymaps.ready() будет вызвана, когда
    // загрузятся все компоненты API, а также когда будет готово DOM-дерево.
    var globalCord;
    var cordName;
    var isInputAction = false;
    document.getElementById("location").addEventListener("input", inputCordForm);

    function inputCordForm(event) {
        isInputAction = true;
        console.log("INPUT");
        cordName = event.target.value;
        ymaps.ready(init);
        setTimeout(func, 1000);

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
                    document.getElementById("location").value = myPlacemark.properties._data.balloonContent;
                    document.getElementById("сoordinatesInput").value = coords;
                });
            }
        }
    }
</script>

</body>

</html>
