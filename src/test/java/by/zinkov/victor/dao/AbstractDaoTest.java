package by.zinkov.victor.dao;

import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.impl.pool.ConnectionPoolImpl;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoTest {

    private Statement statement;
    private Connection connection;

    @Before
    public void initDB() throws ConnectionPoolException, SQLException {
        connection = ConnectionPoolImpl.getInstance().retrieveConnection();


            statement = connection.createStatement();
            statement.execute("CREATE TABLE   user_role (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  role varchar(100) NOT NULL,\n" +
                    "  UNIQUE(role)\n" +
                    "  );\n");
            statement.execute("INSERT INTO  user_role(role) VALUES('ADMINISTRATOR');\n");
            statement.execute("INSERT INTO  user_role(role) VALUES('COURIER');\n");
            statement.execute("INSERT INTO  user_role(role) VALUES('CLIENT');\n");
            statement.execute("CREATE TABLE   user_status (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  status varchar(100) NOT NULL,\n" +
                    "  UNIQUE(status)\n" +
                    "  );\n" +
                    "  \n");

            statement.execute("INSERT INTO  user_status(status) VALUES('BLOCKED');\n");
            statement.execute("INSERT INTO  user_status(status) VALUES('ACTIVE');\n");
            statement.execute("INSERT INTO  user_status(status) VALUES('WAITING_CONFIRMATION');\n");
            statement.execute("CREATE TABLE  user ( \n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  login VARCHAR(45) NOT NULL,\n" +
                    "  password CHAR(45) NOT NULL,\n" +
                    "  first_name VARCHAR(45) NOT NULL,\n" +
                    "  last_name VARCHAR(45) NOT NULL,\n" +
                    "  email VARCHAR(45) NOT NULL,\n" +
                    "  phone VARCHAR(45) NOT NULL,\n" +
                    "  status_id INT NOT NULL,\n" +
                    "  role_id INT NOT NULL,\n" +
                    "  location VARCHAR(45) NULL,\n" +
                    "  CONSTRAINT fk_user_user_role1\n" +
                    "    FOREIGN KEY (role_id)\n" +
                    "    REFERENCES  user_role (id),\n" +
                    "  CONSTRAINT fk_user_status1\n" +
                    "    FOREIGN KEY (status_id)\n" +
                    "    REFERENCES  user_status (id),\n" +
                    "      UNIQUE(login)\n" +
                    "\n" +
                    ");\n");
            statement.execute("CREATE TABLE   order_status (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  status varchar(100) NOT NULL,\n" +
                    "  UNIQUE(status)\n" +
                    "  );\n");
            statement.execute("INSERT INTO  order_status(status) VALUES('PERFORMED');\n");
            statement.execute("INSERT INTO  order_status(status) VALUES('ORDERED');\n");
            statement.execute("INSERT INTO  order_status(status) VALUES('READY');\n");
            statement.execute("INSERT INTO  order_status(status) VALUES('CANCELED');\n");
            statement.execute("CREATE TABLE   delivery_order (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  id_customer INT NOT NULL,\n" +
                    "  id_courier INT NOT NULL,\n" +
                    "  price DECIMAL NULL,\n" +
                    "  id_status INT NOT NULL,\n" +
                    "  start_point VARCHAR(45) NOT NULL,\n" +
                    "  finish_point VARCHAR(45) NOT NULL,\n" +
                    "  description VARCHAR(150) NULL,\n" +
                    "  start_time DATETIME NOT NULL,\n" +
                    "  finish_time DATETIME NOT NULL,\n" +
                    "  expected_time DATETIME NULL,\n" +
                    "  CONSTRAINT fk_order_user2\n" +
                    "    FOREIGN KEY (id_customer)\n" +
                    "    REFERENCES  user (id),\n" +
                    "  CONSTRAINT fk_order_order_status1\n" +
                    "    FOREIGN KEY (id_status)\n" +
                    "    REFERENCES  order_status (id),\n" +
                    "  CONSTRAINT fk_delivery_order_user1\n" +
                    "    FOREIGN KEY (id_courier)\n" +
                    "    REFERENCES  user (id)\n" +
                    "  );\n");
            statement.execute("CREATE TABLE   customer_reviews (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  customer_id INT NOT NULL,\n" +
                    "  courier_id INT NOT NULL,\n" +
                    "  mark TINYINT,\n" +
                    "  CONSTRAINT fk_customer_reviews_user1\n" +
                    "    FOREIGN KEY (customer_id)\n" +
                    "    REFERENCES  user(id),\n" +
                    "  CONSTRAINT fk_customer_reviews_user2\n" +
                    "    FOREIGN KEY (courier_id)\n" +
                    "    REFERENCES  user (id),\n" +
                    "  UNIQUE(customer_id,courier_id)\n" +
                    " );\n");
            statement.execute(
                    "CREATE TABLE   transport_type (\n" +
                            "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                            "  type VARCHAR(45) NOT NULL\n" +
                            ");\n");
            statement.execute("CREATE TABLE   cargo_types (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  type VARCHAR(45) NOT NULL\n" +
                    " );\n");
            statement.execute("CREATE TABLE   currier_capability (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  currier_id INT NOT NULL,\n" +
                    "  transport_id INT NOT NULL,\n" +
                    "  is_work BOOLEAN NOT NULL,\n" +
                    "  CONSTRAINT fk_currier_capability_user1\n" +
                    "    FOREIGN KEY (currier_id)\n" +
                    "    REFERENCES  user (id),\n" +
                    "  CONSTRAINT fk_currier_capability_transport_type1\n" +
                    "    FOREIGN KEY (transport_id)\n" +
                    "    REFERENCES  transport_type (id)\n" +
                    "   );\n");
            statement.execute("CREATE TABLE   supported_cargo_types (\n" +
                    "  id INT NOT NULL PRIMARY KEY IDENTITY,\n" +
                    "  type_id INT NOT NULL,\n" +
                    "  currier_capability_id INT NOT NULL,\n" +
                    "  CONSTRAINT fk_supported_cargo_types_cargo_types1\n" +
                    "    FOREIGN KEY (type_id)\n" +
                    "    REFERENCES  cargo_types (id),\n" +
                    "  CONSTRAINT fk_supported_cargo_types_currier_capability1\n" +
                    "    FOREIGN KEY (currier_capability_id)\n" +
                    "    REFERENCES  currier_capability (id)\n" +
                    "  );\n");
    }

    @After
    public void shutDown() throws SQLException {
        try {
            statement.execute("SHUTDOWN");
        } finally {
            statement.close();
            connection.close();
        }
    }
}
