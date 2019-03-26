package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;

import by.zinkov.victor.dao.TransportTypeExpandedDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.TransportType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportTypeDao extends AbstractJdbcDao<TransportType, Integer> implements TransportTypeExpandedDao {

    private static final String SELECT_ALL_TRANSPORT_TYPES_QUERY = "SELECT * FROM transport_type";
    private static final String SELECT_TRANSPORT_TYPE_PK_QUERY = "SELECT * FROM transport_type WHERE id = ?";
    private static final String INSERT_NEW_TRANSPORT_TYPE_QUERY = "INSERT INTO transport_type ( type , coefficient) VALUES ( ? ,?)";
    private static final String UPDATE_TRANSPORT_TYPE_QUERY = "UPDATE transport_type SET type = ? , coefficient = ? WHERE id = ?";
    private static final String DELETE_TRANSPORT_TYPE_QUERY = "DELETE FROM transport_type WHERE id = ?";
    private static final String SELECT_TRANSPORT_TYPE_BY_PK = "SELECT * FROM transport_type WHERE type = ? ";
    private static final String SELECT_TRANSPORT_TYPE_BY_COURIER_ID =
            "SELECT * FROM transport_type JOIN currier_capability ON" +
                    " transport_type.id = currier_capability.transport_id" +
                    " JOIN user ON user.id = currier_capability.currier_id" +
                    " WHERE user.id = ?";


    @Override
    public TransportType getByCourierId(Integer courierId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_TRANSPORT_TYPE_BY_COURIER_ID)) {
            statement.setInt(1, courierId);
            ResultSet resultSet = statement.executeQuery();
            List<TransportType> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;
        } catch (SQLException e) {
            throw new DaoException("Problem with select by courierID", e);
        }

    }

    @Override
    public TransportType getByName(String name) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_TRANSPORT_TYPE_BY_PK)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            List<TransportType> list = parseResultSet(resultSet);
            return !list.isEmpty() ? list.get(0) : null;

        } catch (SQLException e) {
            throw new DaoException("Problem with select by name", e);
        }
    }

    @Override
    protected List<TransportType> parseResultSet(ResultSet rs) throws SQLException {
        List<TransportType> transportTypes = new ArrayList<>();
        int i = 1;
        while (rs.next()) {
            TransportType transportType = new TransportType();
            transportType.setId(rs.getInt(i++));
            transportType.setTransportType(rs.getString(i++));
            transportType.setCoefficient(rs.getBigDecimal(i));
            transportTypes.add(transportType);
            i = 1;
        }
        return transportTypes;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, TransportType object) throws SQLException {
        int i = 1;
        statement.setString(i++, object.getTransportType());
        statement.setBigDecimal(i++, object.getCoefficient());

        if (object.getId() != null) {
            statement.setInt(i, object.getId());
        }
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_TRANSPORT_TYPES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_TRANSPORT_TYPE_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_TRANSPORT_TYPE_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_TRANSPORT_TYPE_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_TRANSPORT_TYPE_QUERY;
    }
}
