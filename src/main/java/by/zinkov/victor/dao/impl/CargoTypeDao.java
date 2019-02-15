package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.domain.CargoType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoTypeDao  extends AbstractJdbcDao<CargoType, Integer> implements GenericDao<CargoType, Integer> {


    private static final String SELECT_ALL_CARGO_TYPES_QUERY = "SELECT * FROM cargo_types";
    private static final String SELECT_CARGO_TYPE_BY_PK_QUERY = "SELECT * FROM cargo_types WHERE id = ?";
    private static final String INSERT_NEW_CARGO_TYPE_QUERY = "INSERT INTO cargo_types ( type ) VALUES (?)";
    private static final String UPDATE_CARGO_TYPE_QUERY = "UPDATE cargo_types SET type = ? WHERE id = ?";
    private static final String DELETE_CARGO_TYPE_QUERY = "DELETE FROM cargo_types WHERE id = ?";

    @Override
    protected List<CargoType> parseResultSet(ResultSet rs) throws SQLException {
        List<CargoType> types = new ArrayList<>();
            while (rs.next()) {
                CargoType type = new CargoType();
                type.setId(rs.getInt(1));
                type.setCargoType(rs.getString(2));
                types.add(type);
            }
            return types;

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CargoType object) throws SQLException {
            statement.setString(1,object.getCargoType());
            if(object.getId() != null){
                statement.setInt(2, object.getId());
            }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CargoType object) throws SQLException {
        prepareStatementForInsert(statement,object);
    }



    @Override
    public String getSelectQuery() {
        return SELECT_ALL_CARGO_TYPES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_CARGO_TYPE_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_CARGO_TYPE_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_CARGO_TYPE_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_CARGO_TYPE_QUERY;
    }
}
