package by.zinkov.victor.builder.impl;

import by.zinkov.victor.builder.FromMapEntityBuilder;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.Order;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.service.CargoTypeService;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.TransportTypeService;
import by.zinkov.victor.service.factory.ServiceFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class OrderBuilder implements FromMapEntityBuilder<Order> {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd, HH:mm";


    private static final String CARGO_TYPE_PARAMETER = "cargo_type";
    private static final String TRANSPORT_TYPE_PARAMETER = "transport_type";
    private static final String START_POINT_PARAMETER = "start_point";
    private static final String FINISH_POINT_PARAMETER = "finish_point";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String START_TIME_PARAMETER = "start_time";

    @Override
    public Order build(Map<String, String> requestParams) throws ServiceException {

        Order order = new Order();

        ServiceFactory factory = ServiceFactory.getInstance();
        TransportTypeService transportTypeService = factory.getTransportTypeService();
        CargoTypeService cargoTypeService = factory.getCargoTypeService();

        TransportType transportType = transportTypeService.getByName(requestParams.get(TRANSPORT_TYPE_PARAMETER));
        CargoType cargoType = cargoTypeService.getByName(requestParams.get(CARGO_TYPE_PARAMETER));
        order.setIdCargoType(cargoType.getId());
        order.setIdTransportType(transportType.getId());
        order.setStartPoint(requestParams.get(START_POINT_PARAMETER));
        order.setFinishPoint(requestParams.get(FINISH_POINT_PARAMETER));
        order.setDescription(requestParams.get(DESCRIPTION_PARAMETER));

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        Timestamp startTime;
        try {
            Date date = dateFormat.parse(requestParams.get(START_TIME_PARAMETER));
            startTime = new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw new IllegalStateException("In validator this mistake should be cached!", e);
        }
        order.setStartTime(startTime);

        return order;
    }
}
