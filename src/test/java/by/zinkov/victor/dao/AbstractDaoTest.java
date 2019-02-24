package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.pool.ConnectionPoolImpl;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoTest {

    private static Statement statement;
    private  static Connection connection;



    @Before
    public void initDB() throws ConnectionPoolException, SQLException {
        connection = ConnectionPoolImpl.getInstance().retrieveConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE   user_role ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  role varchar(100) NOT NULL, " +
                    "  UNIQUE(role) " +
                    "  ); ");
            statement.execute("INSERT INTO  user_role(role) VALUES('ADMINISTRATOR'); ");
            statement.execute("INSERT INTO  user_role(role) VALUES('COURIER'); ");
            statement.execute("INSERT INTO  user_role(role) VALUES('CLIENT'); ");
            statement.execute("CREATE TABLE   user_status ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  status varchar(100) NOT NULL, " +
                    "  UNIQUE(status) " +
                    "  ); " +
                    "   ");

            statement.execute("INSERT INTO  user_status(status) VALUES('BLOCKED'); ");
            statement.execute("INSERT INTO  user_status(status) VALUES('ACTIVE'); ");
            statement.execute("INSERT INTO  user_status(status) VALUES('WAITING_CONFIRMATION'); ");
            statement.execute("CREATE TABLE  user (  " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  login VARCHAR(45) NOT NULL, " +
                    "  password CHAR(45) NOT NULL, " +
                    "  first_name VARCHAR(45) NOT NULL, " +
                    "  last_name VARCHAR(45) NOT NULL, " +
                    "  email VARCHAR(45) NOT NULL, " +
                    "  phone VARCHAR(45) NOT NULL, " +
                    "  status_id INT NOT NULL, " +
                    "  role_id INT NOT NULL, " +
                    "  location VARCHAR(45) NULL, " +
                    "  CONSTRAINT fk_user_user_role1 " +
                    "    FOREIGN KEY (role_id) " +
                    "    REFERENCES  user_role (id), " +
                    "  CONSTRAINT fk_user_status1 " +
                    "    FOREIGN KEY (status_id) " +
                    "    REFERENCES  user_status (id), " +
                    "      UNIQUE(login) " +
                    " " +
                    "); ");
            statement.execute("CREATE TABLE   order_status ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  status varchar(100) NOT NULL, " +
                    "  UNIQUE(status) " +
                    "  ); ");
            statement.execute("INSERT INTO  order_status(status) VALUES('PERFORMED'); ");
            statement.execute("INSERT INTO  order_status(status) VALUES('ORDERED'); ");
            statement.execute("INSERT INTO  order_status(status) VALUES('READY'); ");
            statement.execute("INSERT INTO  order_status(status) VALUES('CANCELED'); ");
            statement.execute("CREATE TABLE   delivery_order ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  id_customer INT NOT NULL, " +
                    "  id_courier INT NOT NULL, " +
                    "  price DECIMAL(12,2) NULL, " +
                    "  id_status INT NOT NULL, " +
                    "  start_point VARCHAR(45) NOT NULL, " +
                    "  finish_point VARCHAR(45) NOT NULL, " +
                    "  description VARCHAR(150) NULL, " +
                    "  start_time TIMESTAMP NOT NULL, " +
                    "  finish_time TIMESTAMP NOT NULL, " +
                    "  expected_time TIMESTAMP NULL, " +
                    "  CONSTRAINT fk_order_user2 " +
                    "    FOREIGN KEY (id_customer) " +
                    "    REFERENCES  user (id), " +
                    "  CONSTRAINT fk_order_order_status1 " +
                    "    FOREIGN KEY (id_status) " +
                    "    REFERENCES  order_status (id), " +
                    "  CONSTRAINT fk_delivery_order_user1 " +
                    "    FOREIGN KEY (id_courier) " +
                    "    REFERENCES  user (id) " +
                    "  ); ");
            statement.execute("CREATE TABLE   customer_reviews ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  customer_id INT NOT NULL, " +
                    "  courier_id INT NOT NULL, " +
                    "  mark TINYINT, " +
                    "  CONSTRAINT fk_customer_reviews_user1 " +
                    "    FOREIGN KEY (customer_id) " +
                    "    REFERENCES  user(id), " +
                    "  CONSTRAINT fk_customer_reviews_user2 " +
                    "    FOREIGN KEY (courier_id) " +
                    "    REFERENCES  user (id), " +
                    "  UNIQUE(customer_id,courier_id) " +
                    " ); ");
            statement.execute(
                    "CREATE TABLE   transport_type ( " +
                            "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                            "  type VARCHAR(45) NOT NULL " +
                            "); ");
            statement.execute("CREATE TABLE   cargo_types ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  type VARCHAR(45) NOT NULL " +
                    " ); ");
            statement.execute("CREATE TABLE   currier_capability ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  currier_id INT NOT NULL, " +
                    "  transport_id INT NOT NULL, " +
                    "  is_work BOOLEAN NOT NULL, " +
                    "  CONSTRAINT fk_currier_capability_user1 " +
                    "    FOREIGN KEY (currier_id) " +
                    "    REFERENCES  user (id), " +
                    "  CONSTRAINT fk_currier_capability_transport_type1 " +
                    "    FOREIGN KEY (transport_id) " +
                    "    REFERENCES  transport_type (id) " +
                    "   ); ");
            statement.execute("CREATE TABLE   supported_cargo_types ( " +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY, " +
                    "  type_id INT NOT NULL, " +
                    "  currier_capability_id INT NOT NULL, " +
                    "  CONSTRAINT fk_supported_cargo_types_cargo_types1 " +
                    "    FOREIGN KEY (type_id) " +
                    "    REFERENCES  cargo_types (id), " +
                    "  CONSTRAINT fk_supported_cargo_types_currier_capability1 " +
                    "    FOREIGN KEY (currier_capability_id) " +
                    "    REFERENCES  currier_capability (id) " +
                    "  ); ");

            statement.execute(" CREATE TABLE  registration_keys(\n" +
                    "    user_id INT PRIMARY KEY,\n" +
                    "    registration_key CHAR(32) NOT NULL\n" +
                    "  );\n");



    }

    @After
    public  void shutDown() throws SQLException {
        try {
           // statement.execute("SHUTDOWN");


            statement.execute("DROP TABLE customer_reviews;");
            statement.execute("DROP TABLE delivery_order;");
            statement.execute("DROP TABLE order_status;");
            statement.execute("DROP TABLE supported_cargo_types;");
            statement.execute("DROP TABLE cargo_types;");
            statement.execute("DROP TABLE currier_capability;");
            statement.execute("DROP TABLE transport_type;");
            statement.execute("DROP TABLE user;");
            statement.execute("DROP TABLE user_role;");
            statement.execute("DROP TABLE user_status;");
            statement.execute("DROP TABLE registration_keys;");
        } finally {
            statement.close();
            connection.close();
        }
    }
}
