function initMapOrders(firstPoint, SecondPoint) {
    ymaps.ready(init);

    function init() {

        myMap = new ymaps.Map('map', {
            center: firstPoint.split(","),
            zoom: 6
        }, {
            searchControlProvider: 'yandex#search'
        });
        getAddress(firstPoint, "firstPoint")
        getAddress(SecondPoint, "secondPoint")


        myGeoObject = new ymaps.GeoObject({
            geometry: {
                type: "Point",
                coordinates: firstPoint.split(",")
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
                coordinates: SecondPoint.split(",")
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
}