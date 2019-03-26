package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.CargoTypeExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.CargoType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoTypeDao extends AbstractJdbcDao<CargoType, Integer> implements CargoTypeExpandedDao {


    private static final String SELECT_ALL_CARGO_TYPES_QUERY = "SELECT * FROM cargo_types";
    private static final String SELECT_CARGO_TYPE_BY_PK_QUERY = "SELECT * FROM cargo_types WHERE id = ?";
    private static final String INSERT_NEW_CARGO_TYPE_QUERY = "INSERT INTO cargo_types ( type ) VALUES (?)";
    private static final String UPDATE_CARGO_TYPE_QUERY = "UPDATE cargo_types SET type = ? WHERE id = ?";
    private static final String DELETE_CARGO_TYPE_QUERY = "DELETE FROM cargo_types WHERE id = ?";
    private static final String SELECT_CARGO_TYPE_BY_NAME_QUERY = "SELECT * FROM cargo_types WHERE type = ?";
    private static final String SELECT_CARGO_TYPES_BY_COURIER_ID_QUERY =
            "SELECT DISTINCT cargo_types.id, cargo_types.type FROM cargo_types " +
                    "JOIN supported_cargo_types ON cargo_types.id = supported_cargo_types.type_id " +
                    "JOIN currier_capability ON supported_cargo_types.currier_capability_id = currier_capability.id " +
                    "WHERE currier_capability.currier_id = ?";

    @Override
    public List<CargoType> getByCourierId(Integer courierId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_CARGO_TYPES_BY_COURIER_ID_QUERY)) {
            statement.setInt(1, courierId);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Problem with select by name", e);
        }
    }

    @Override
    public CargoType getByName(String name) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_CARGO_TYPE_BY_NAME_QUERY)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            List<CargoType> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;
        } catch (SQLException e) {
            throw new DaoException("Problem with select by currierId", e);
        }
    }

    @Override
    protected List<CargoType> parseResultSet(ResultSet rs) throws SQLException {
        List<CargoType> types = new ArrayList<>();
        while (rs.next()) {
            CargoType type = new CargoType();
            type.setId(rs.getInt(1));
            type.setType(rs.getString(2));
            types.add(type);
        }
        return types;

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CargoType object) throws SQLException {
        statement.setString(1, object.getType());
        if (object.getId() != null) {
            statement.setInt(2, object.getId());
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CargoType object) throws SQLException {
        prepareStatementForInsert(statement, object);
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
