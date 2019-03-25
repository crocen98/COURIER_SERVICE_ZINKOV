document.getElementById("descrField").style.borderColor = "red";
document.getElementById("descrField").addEventListener("input", function (ev) {
    if (ev.target.value != null && ev.target.value != "") {
        ev.target.style.borderColor = "green";
    } else {
        ev.target.style.borderColor = "red";
    }
})

var activeOne = false;
var activeTwo = false;
document.getElementById("coordinatesone").style.borderColor = 'red';
document.getElementById("coordinates").style.borderColor = 'red';


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


    function func() {
        console.log(globalCord + "  globalCord");
        var rad = document.getElementsByName('point_type');
        for (var i = 0; i < rad.length; i++) {
            if (rad[i].checked) {
                checked_rad_but_id = rad[i].getAttribute("id");
                break;
            }
        }
        func();
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
        result.geoObjects.options.set('preset', 'islands#redCircleIcon');
        result.geoObjects.get(0).properties.set({
            balloonContentBody: 'Мое местоположение'
        });
        myMap.geoObjects.add(result.geoObjects);
    });

    var myPlacemark;
    myMap.events.add('click', function (e) {
        var coords = e.get('coords');
        console.log("coords");
        console.log(coords);


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

            if (checked_rad_but_id == "radio_1") {
                document.getElementById("coordinates").value = myPlacemark.properties._data.balloonContent;
                document.getElementById("сoordinatesInput").value = coords;
                document.getElementById("coordinates").style.borderColor = 'green';
                activeOne = true
            } else {
                document.getElementById("coordinatesone").value = myPlacemark.properties._data.balloonContent;
                document.getElementById("сoordinatesInputone").value = coords;
                document.getElementById("coordinatesone").style.borderColor = 'green';
                activeTwo = true

            }
            if (activeOne && activeTwo) {
                document.getElementById("but").removeAttribute("disabled");
            }
        });
    }
}
