DROP DATABASE IF EXISTS couriers;
CREATE DATABASE  couriers;
USE couriers;


CREATE TABLE  couriers.user_role (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role ENUM('ADMINISTRATOR', 'COURIER', 'CLIENT') NOT NULL,
  UNIQUE(role)
  );
  
INSERT INTO couriers.user_role(role) VALUES('ADMINISTRATOR');
INSERT INTO couriers.user_role(role) VALUES('COURIER');
INSERT INTO couriers.user_role(role) VALUES('CLIENT');


CREATE TABLE  couriers.user_status (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status ENUM('BLOCKED', 'ACTIVE','WAITING_CONFIRMATION') NOT NULL,
  UNIQUE(status)
  );
  
INSERT INTO couriers.user_status(status) VALUES('BLOCKED');
INSERT INTO couriers.user_status(status) VALUES('ACTIVE');
INSERT INTO couriers.user_status(status) VALUES('WAITING_CONFIRMATION');

CREATE TABLE couriers.user ( 
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(45) NOT NULL,
  password CHAR(45) NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  phone VARCHAR(45) NOT NULL,
  status_id INT NOT NULL,
  role_id INT NOT NULL,
  location VARCHAR(45) NULL,
  CONSTRAINT fk_user_user_role1
    FOREIGN KEY (role_id)
    REFERENCES couriers.user_role (id),
  CONSTRAINT fk_user_status1
    FOREIGN KEY (status_id)
    REFERENCES couriers.user_status (id),
      UNIQUE(login)

);


CREATE TABLE  couriers.order_status (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status ENUM('PERFORMED', 'ORDERED', 'READY', 'CANCELED') NOT NULL,
  UNIQUE(status)
  );

INSERT INTO couriers.order_status(status) VALUES('PERFORMED');
INSERT INTO couriers.order_status(status) VALUES('ORDERED');
INSERT INTO couriers.order_status(status) VALUES('READY');
INSERT INTO couriers.order_status(status) VALUES('CANCELED');



CREATE TABLE  couriers.delivery_order (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_customer INT NOT NULL,
  id_courier INT NOT NULL,
  price DECIMAL(14,2) NULL,
  id_status INT NOT NULL,
  start_point VARCHAR(45) NOT NULL,
  finish_point VARCHAR(45) NOT NULL,
  description VARCHAR(150) NULL,
  start_time  TIMESTAMP NOT NULL,
  finish_time  TIMESTAMP  NULL,
  expected_time  TIMESTAMP NULL,
  id_cargo_type INT NOT NULL,
  id_transport_type INT NOT NULL,
  CONSTRAINT fk_order_user2
    FOREIGN KEY (id_customer)
    REFERENCES couriers.user (id),
  CONSTRAINT fk_order_order_status1
    FOREIGN KEY (id_status)
    REFERENCES couriers.order_status (id),
  CONSTRAINT fk_delivery_order_user1
    FOREIGN KEY (id_courier)
    REFERENCES couriers.user (id),
       CONSTRAINT fk_order_cargo_type
     FOREIGN KEY (id_cargo_type)
     REFERENCES couriers.cargo_types (id),
       CONSTRAINT fk_order_transport_type
     FOREIGN KEY (id_transport_type)
     REFERENCES couriers.transport_type (id)
  );
  
  

CREATE TABLE  couriers.customer_reviews (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  customer_id INT NOT NULL,
  courier_id INT NOT NULL,
  mark TINYINT,
  CONSTRAINT fk_customer_reviews_user1
    FOREIGN KEY (customer_id)
    REFERENCES couriers.user(id),
  CONSTRAINT fk_customer_reviews_user2
    FOREIGN KEY (courier_id)
    REFERENCES couriers.user (id),
  UNIQUE(customer_id,courier_id)
 );


CREATE TABLE  couriers.transport_type (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(45) NOT NULL,
  UNIQUE(type)
);


CREATE TABLE  couriers.cargo_types (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(45) NOT NULL,
   UNIQUE(type)
 );




CREATE TABLE  couriers.currier_capability (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  currier_id INT NOT NULL,
  transport_id INT NOT NULL,
  is_work BOOLEAN NOT NULL DEFAULT false,
  CONSTRAINT fk_currier_capability_user1
    FOREIGN KEY (currier_id)
    REFERENCES couriers.user (id),
  CONSTRAINT fk_currier_capability_transport_type1
    FOREIGN KEY (transport_id)
    REFERENCES couriers.transport_type (id)
   );



CREATE TABLE  couriers.supported_cargo_types (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type_id INT NOT NULL,
  currier_capability_id INT NOT NULL,
  CONSTRAINT fk_supported_cargo_types_cargo_types1
    FOREIGN KEY (type_id)
    REFERENCES couriers.cargo_types (id),
  CONSTRAINT fk_supported_cargo_types_currier_capability1
    FOREIGN KEY (currier_capability_id)
    REFERENCES couriers.currier_capability (id)
  );


  CREATE TABLE  couriers.registration_keys(
    user_id INT PRIMARY KEY ,
    registration_key CHAR(32) NOT NULL
  );



