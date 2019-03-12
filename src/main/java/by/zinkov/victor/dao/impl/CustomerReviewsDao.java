package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.CustomerReviewsExpandedDao;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.domain.CargoType;
import by.zinkov.victor.domain.CustomerReviews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReviewsDao extends AbstractJdbcDao<CustomerReviews, Integer> implements GenericDao<CustomerReviews, Integer>, CustomerReviewsExpandedDao {

    private static final String SELECT_ALL_CUSTOMER_REVIEWS_QUERY = "SELECT * FROM customer_reviews";
    private static final String SELECT_CUSTOMER_REVIEWS_BY_PK_QUERY = "SELECT * FROM customer_reviews WHERE id = ?";
    private static final String INSERT_NEW_CUSTOMER_REVIEW_QUERY =
            "INSERT INTO customer_reviews ( customer_id , courier_id , mark) " +
                    "VALUES ( ? , ? , ? )";
    private static final String UPDATE_CUSTOMER_REVIEW_QUERY = "UPDATE customer_reviews SET " +
            "customer_id = ? , courier_id = ? , mark = ? WHERE id = ?";

    private static final String DELETE_CUSTOMER_REVIEW_QUERY = "DELETE FROM customer_reviews WHERE id = ?";
    private static final String SELECT_COURIER_MARK_BY_ID_QUERY =
            "SELECT AVG(mark) FROM customer_reviews " +
                    "WHERE customer_reviews.courier_id = ?";

    private static final String SELECT_COURIER_MARK_BY_CLIENT_ID_QUERY =
            "SELECT * FROM customer_reviews " +
                    "WHERE customer_reviews.courier_id = ? AND customer_reviews.customer_id = ?";


    @Override
    public CustomerReviews getByCourierUserId(Integer courierId, Integer userId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_COURIER_MARK_BY_CLIENT_ID_QUERY)) {
            statement.setInt(1, courierId);
            statement.setInt(2, userId);

            ResultSet resultSet = statement.executeQuery();
            List<CustomerReviews> customerReview = parseResultSet(resultSet);
            return customerReview.isEmpty() ? null : customerReview.get(0);
        } catch (SQLException e) {
            throw new DaoException("Problem with get CustomerReview by currierId and userId", e);
        }    }

    @Override
    public boolean haveMark(Integer courierId, Integer userId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_COURIER_MARK_BY_CLIENT_ID_QUERY)) {
            statement.setInt(1, courierId);
            statement.setInt(2, userId);

            ResultSet resultSet = statement.executeQuery();
            List<CustomerReviews> customerReview = parseResultSet(resultSet);
            return customerReview.isEmpty() ? false : true;
        } catch (SQLException e) {
            throw new DaoException("Problem with get CustomerReview by currierId and userId", e);
        }
    }

    @Override
    public Double getCourierMark(Integer courierId) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_COURIER_MARK_BY_ID_QUERY)) {
            statement.setInt(1, courierId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Problem with calculate mark by currierId", e);
        }
    }

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
        statement.setInt(1, object.getCustomerId());
        statement.setInt(2, object.getCourierId());
        statement.setByte(3, object.getMark());
        if (object.getId() != null) {
            statement.setInt(4, object.getId());
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CustomerReviews object) throws SQLException {
        prepareStatementForInsert(statement, object);
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
        return UPDATE_CUSTOMER_REVIEW_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_CUSTOMER_REVIEW_QUERY;
    }
}
