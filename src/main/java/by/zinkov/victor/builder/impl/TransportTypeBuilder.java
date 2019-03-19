package by.zinkov.victor.builder.impl;

import by.zinkov.victor.builder.FromMapEntityBuilder;
import by.zinkov.victor.domain.TransportType;

import java.math.BigDecimal;
import java.util.Map;

public class TransportTypeBuilder implements FromMapEntityBuilder<TransportType> {
    private static final String TRANSPORT_ID_PARAMETER = "transport_type_id";
    private static final String TRANSPORT_NAME_PARAMETER = "transport_name";
    private static final String COEFFICIENT_PARAMETER = "coefficient";

    @Override
    public TransportType build(Map<String,String> parameters) {
        TransportType transportType = new TransportType();

        String id =  parameters.get(TRANSPORT_ID_PARAMETER);
        if(id != null){
            transportType.setId(Integer.valueOf(id));
        }

        transportType.setTransportType(parameters.get(TRANSPORT_NAME_PARAMETER));
        double doubleCoefficient =  Double.parseDouble(parameters.get(COEFFICIENT_PARAMETER));
        BigDecimal coefficient = BigDecimal.valueOf(doubleCoefficient);

        transportType.setCoefficient(coefficient);

        return transportType;
    }
}
