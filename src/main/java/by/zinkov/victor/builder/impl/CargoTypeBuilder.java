package by.zinkov.victor.builder.impl;

import by.zinkov.victor.builder.FromMapEntityBuilder;
import by.zinkov.victor.domain.CargoType;

import java.util.Map;

public class CargoTypeBuilder implements FromMapEntityBuilder<CargoType> {
    private static final String CARGO_ID_PARAMETER = "cargo_type_id";
    private static final String CARGO_TYPE_PARAMETER = "cargo_type";

    @Override
    public CargoType build(Map<String, String> parameters) {
        CargoType cargoType = new CargoType();

        String id = parameters.get(CARGO_ID_PARAMETER);
        if (id != null) {
            cargoType.setId(Integer.valueOf(id));
        }
        cargoType.setType(parameters.get(CARGO_TYPE_PARAMETER));
        return cargoType;
    }
}
