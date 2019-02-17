package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;

import by.zinkov.victor.domain.TransportType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportTypeDao extends AbstractJdbcDao<TransportType, Integer> implements GenericDao<TransportType, Integer> {

    private static final String SELECT_ALL_TRANSPORT_TYPES_QUERY = "SELECT * FROM transport_type";
    private static final String SELECT_TRANSPORT_TYPE_PK_QUERY = "SELECT * FROM transport_type WHERE id = ?";
    private static final String INSERT_NEW_TRANSPORT_TYPE_QUERY = "INSERT INTO transport_type ( type) VALUES ( ? )";
    private static final String UPDATE_TRANSPORT_TYPE_QUERY = "UPDATE transport_type SET type = ? WHERE id = ?";
    private static final String DELETE_TRANSPORT_TYPE_QUERY = "DELETE FROM transport_type WHERE id = ?";

    @Override
    protected List<TransportType> parseResultSet(ResultSet rs) throws SQLException {
        List<TransportType> transportTypes = new ArrayList<>();
            int i = 1;
            while (rs.next()) {
                TransportType transportType = new TransportType();
                transportType.setId(rs.getInt(i++));
                transportType.setTransportType(rs.getString(i++));
                transportTypes.add(transportType);
                i=1;
            }
            return transportTypes;
    }
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, TransportType object) throws SQLException {
            int i = 1;
            statement.setString(i++,object.getTransportType());
            if(object.getId() != null){
                statement.setInt(i++, object.getId());
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
