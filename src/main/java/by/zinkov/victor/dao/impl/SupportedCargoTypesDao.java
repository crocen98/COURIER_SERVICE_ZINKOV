package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.domain.SupportedCargoTypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupportedCargoTypesDao extends AbstractJdbcDao<SupportedCargoTypes, Integer> implements GenericDao<SupportedCargoTypes, Integer> {

    private static final String SELECT_ALL_SUPPORTED_CARO_TYPES_QUERY = "SELECT * FROM supported_cargo_types";
    private static final String SELECT_SUPPORTED_CARO_TYPES_BY_PK_QUERY = "SELECT * FROM supported_cargo_types WHERE id = ?";
    private static final String INSERT_NEW_SUPPORTED_CARO_TYPE_QUERY =
            "INSERT INTO supported_cargo_types ( type_id , currier_capability_id) " +
                    "VALUES ( ? , ?  )";
    private static final String UPDATE_SUPPORTED_CARO_TYPES_QUERY = "UPDATE supported_cargo_types SET " +
            "type_id = ? , currier_capability_id = ?";

    private static final String DELETE_SUPPORTED_CARO_TYPES_QUERY = "DELETE FROM supported_cargo_types WHERE id = ?";

    @Override
    protected List<SupportedCargoTypes> parseResultSet(ResultSet rs) throws SQLException {
        List<SupportedCargoTypes> supportedCargoTypes = new ArrayList<>();
            int i = 1;
            while (rs.next()) {
                SupportedCargoTypes cargoTypes = new SupportedCargoTypes();
                cargoTypes.setId(rs.getInt(i++));
                cargoTypes.setTypeId( rs.getInt(i++));
                cargoTypes.setCurrierCapabilityId( rs.getInt(i));
                supportedCargoTypes.add(cargoTypes);
                i = 1;
            }
            return supportedCargoTypes;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, SupportedCargoTypes object) throws SQLException {
            int i = 1;
            statement.setInt(i++,object.getTypeId());
            statement.setInt(i++,object.getCurrierCapabilityId());
            if(object.getId() != null){
                statement.setInt(i, object.getId());
            }
    }

    @Override
    public String getSelectQuery() {
        return SELECT_ALL_SUPPORTED_CARO_TYPES_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_SUPPORTED_CARO_TYPES_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_SUPPORTED_CARO_TYPE_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_SUPPORTED_CARO_TYPES_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_SUPPORTED_CARO_TYPES_QUERY;
    }
}
