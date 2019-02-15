package by.zinkov.victor.dao.impl;


import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.domain.CurrierCapability;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrierCapabilityDao extends AbstractJdbcDao<CurrierCapability, Integer> implements GenericDao<CurrierCapability, Integer> {
    private static final String SELECT_ALL_CAPABILITIES_QUERY = "SELECT * FROM currier_capability";
    private static final String SELECT__CAPABILITY_BY_PK_QUERY = "SELECT * FROM currier_capability WHERE id = ?";
    private static final String INSERT_NEW_CAPABILITY_QUERY = "INSERT INTO currier_capability ( currier_id , transport_id , is_work) VALUES ( ? , ? , ? )";
    private static final String UPDATE__CAPABILITY_QUERY = "UPDATE currier_capability SET currier_id = ? , transport_id = ? , is_work = ? WHERE id = ?";
    private static final String DELETE_CAPABILITY_QUERY = "DELETE FROM currier_capability WHERE id = ?";


    @Override
    protected List<CurrierCapability> parseResultSet(ResultSet rs) throws SQLException {
        List<CurrierCapability> capabilities = new ArrayList<>();

            while (rs.next()) {
                CurrierCapability capability = new CurrierCapability();
                capability.setId(rs.getInt(1));
                capability.setCurrierId(rs.getInt(2));
                capability.setTransportId(rs.getInt(3));
                capability.setWork(rs.getBoolean(4));
                capabilities.add(capability);
            }
            return capabilities;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CurrierCapability object) throws SQLException {

            statement.setInt(1,object.getCurrierId());
            statement.setInt(2,object.getTransportId());
            statement.setBoolean(3,object.isWork());
            if(object.getId() != null){
                statement.setInt(4, object.getId());
            }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CurrierCapability object) throws SQLException {
        prepareStatementForInsert(statement,object);
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_CAPABILITIES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT__CAPABILITY_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_CAPABILITY_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE__CAPABILITY_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_CAPABILITY_QUERY;
    }
}
