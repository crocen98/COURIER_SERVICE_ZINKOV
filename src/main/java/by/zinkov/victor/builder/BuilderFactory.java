package by.zinkov.victor.builder;

import by.zinkov.victor.builder.impl.CargoTypeBuilder;
import by.zinkov.victor.builder.impl.OrderBuilder;
import by.zinkov.victor.builder.impl.TransportTypeBuilder;
import by.zinkov.victor.builder.impl.UserBuilder;

public class BuilderFactory {
    private static BuilderFactory ourInstance = new BuilderFactory();

    public static BuilderFactory getInstance() {
        return ourInstance;
    }

    private BuilderFactory() {
    }

    public CargoTypeBuilder getCargoTypeBuilder() {
        return new CargoTypeBuilder();
    }

    public UserBuilder getUserBuilder() {
        return new UserBuilder();
    }

    public OrderBuilder getOrderBuilder() {
        return new OrderBuilder();
    }

    public TransportTypeBuilder getTransportTypeBuilder() {
        return new TransportTypeBuilder();
    }

}
