package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.domain.CurrierCapability;
import by.zinkov.victor.domain.CustomerReviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReviewsDao extends AbstractJdbcDao<CustomerReviews, Integer> implements GenericDao<CustomerReviews, Integer> {

    private static final String SELECT_ALL_CUSTOMER_REVIEWS_QUERY = "SELECT * FROM customer_reviews";
    private static final String SELECT_CUSTOMER_REVIEWS_BY_PK_QUERY = "SELECT * FROM customer_reviews WHERE id = ?";
    private static final String INSERT_NEW_CUSTOMER_REVIEW_QUERY =
            "INSERT INTO customer_reviews ( cutomer_id , courier_id , mark) " +
                    "VALUES ( ? , ? , ? )";
    private static final String UPDATE__CUSTOMER_REVIEW_QUERY = "UPDATE customer_reviews SET " +
            "cutomer_id = ? , courier_id = ? , mark = ? WHERE id = ?";

    private static final String DELETE_CUSTOMER_REVIEW_QUERY = "DELETE FROM customer_reviews WHERE id = ?";


    @Override
    protected List<CustomerReviews> parseResultSet(ResultSet rs) throws SQLException {
        List<CustomerReviews> reviews = new ArrayList<>();

            while (rs.next()) {
                CustomerReviews review = new CustomerReviews();
                review.setId(rs.getInt(1));
                review.setCustomerId(rs.getInt(2));
                review.setCourierId(rs.getInt(3));
                review.setMark(rs.getByte(4));
                reviews.add(review);
            }
            return reviews;

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CustomerReviews object) throws SQLException {
            statement.setInt(1,object.getCustomerId());
            statement.setInt(2,object.getCourierId());
            statement.setByte(3,object.getMark());
            if(object.getId() != 0){
                statement.setInt(4, object.getId());
            }
            System.out.println("WORCK ");

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CustomerReviews object) throws SQLException {
        prepareStatementForInsert(statement,object);
    }



    @Override
    public String getSelectQuery() {
        return SELECT_ALL_CUSTOMER_REVIEWS_QUERY;
    }

    @Override
    public String getSelectQueryForPK() {
        return SELECT_CUSTOMER_REVIEWS_BY_PK_QUERY;
    }

    @Override
    public String getCreateQuery() {
        return INSERT_NEW_CUSTOMER_REVIEW_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE__CUSTOMER_REVIEW_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_CUSTOMER_REVIEW_QUERY;
    }
}
