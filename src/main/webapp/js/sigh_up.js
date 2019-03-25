var inputCoordinates = document.getElementById("coordinates");
inputCoordinates.addEventListener("input", function (eventTarget) {
    if (eventTarget.target.value != null) {
        document.getElementById("but").removeAttribute("disabled");
    }
});

var cordInput = document.getElementById("coordinates");
cordInput.style.borderColor = 'red';
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
        result.geoObjects.options.set('preset', 'islands#redCircleIcon');
        result.geoObjects.get(0).properties.set({
            balloonContentBody: 'Мое местоположение'
        });
        myMap.geoObjects.add(result.geoObjects);
    });

    var myPlacemark;
    myMap.events.add('click', function (e) {
        var coords = e.get('coords');
        if (myPlacemark) {
            myPlacemark.geometry.setCoordinates(coords);
        }
        else {
            myPlacemark = createPlacemark(coords);
            myMap.geoObjects.add(myPlacemark);
            myPlacemark.events.add('dragend', function () {
                getAddress(myPlacemark.geometry.getCoordinates());
            });
        }
        getAddress(coords);
    });


    function createPlacemark(coords) {
        return new ymaps.Placemark(coords, {
            iconCaption: 'поиск...'
        }, {
            preset: 'islands#violetDotIconWithCaption',
            draggable: true
        });
    }

    function getAddress(coords) {
        myPlacemark.properties.set('iconCaption', 'поиск...');
        ymaps.geocode(coords).then(function (res) {
            var firstGeoObject = res.geoObjects.get(0);
            myPlacemark.properties
                .set({
                    iconCaption: [
                        firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                        firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                    ].filter(Boolean).join(', '),
                    balloonContent: firstGeoObject.getAddressLine()
                });

            console.log(myPlacemark.properties._data.balloonContent);
            document.getElementById("coordinates").value = myPlacemark.properties._data.balloonContent;
            document.getElementById("сoordinatesInput").value = coords;
            cordInput.style.borderColor = 'green';
            document.getElementById("but").removeAttribute("disabled");
        });
    }

}


function loadXMLDoc(userLogin) {
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
        }
    };

    xmlhttp.open("POST", "AjaxServlet", true);
    xmlhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("command=check_login&login=" + userLogin.value);




    document.getElementById("click", function (event) {

    })
}

