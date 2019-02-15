package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.ConnectionPool;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.exception.PersistException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.dao.impl.pool.ConnectionPoolImpl;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolImplTest {
    private static final ConnectionPool POOL = ConnectionPoolImpl.getInstance();

    GenericDao<User,Integer> userDao;

    {
        try {
            userDao =  JdbcDaoFactory.getInstance().getDao(User.class);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private static final String DB_PROPERTIES_FILE = "config_db_property.properties";

    public ConnectionPoolImplTest() throws ConnectionPoolException {
    }

    @BeforeClass
    public static void changePropertyFileForWorckWithTestDB() throws IOException {
//        ClassLoader classLoader = ConnectionPoolImplTest.class.getClassLoader();
//        Properties properties = new Properties();
//        try (InputStream inputStreamDbType = classLoader.getResourceAsStream(DB_PROPERTIES_FILE)) {
//            properties.load(inputStreamDbType);
//            properties.setProperty("dbProperties", "db.properties");
//        }
    }


    @AfterClass
    public static void changePropertyFileForWorckWithCommonDB() throws IOException {
//        ClassLoader classLoader = ConnectionPoolImplTest.class.getClassLoader();
//        Properties properties = new Properties();
//        try (InputStream inputStreamDbType = classLoader.getResourceAsStream(DB_PROPERTIES_FILE)) {
//            properties.load(inputStreamDbType);
//            properties.setProperty("dbProperties", "db.properties");
//        }
    }


    @Test
    public void parseResultSet() throws PersistException, DaoException {
        User user = new User();
        List<User> usersTest = new ArrayList<>();
        usersTest.add(user);
        user.setUserRole(UserRole.CLIENT);
        user.setPassword("pass");
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPhone("+375291052630");
        user.setEmail("email");
        user.setLastName("lastName");
        user.setFirstName("firstName");
        user.setLogin("login");
        user.setLocation("location");

        userDao.persist(user);
        List<User> users = userDao.getAll();
        user.setId(1);
        Assert.assertEquals(usersTest,users);



    }


    @Test
    public void prepareStatementForInsert() {
    }

    @Test
    public void prepareStatementForUpdate() {
    }

    @Test
    public void getSelectQuery() {
    }

    @Test
    public void getCreateQuery() {
    }

    @Test
    public void getUpdateQuery() {
    }

    @Test
    public void getDeleteQuery() {
    }

    @Test
    public void create() {
    }

}