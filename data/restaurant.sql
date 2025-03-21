-- IMPORTANT: DO NOT CHANGE THE GIVEN SCHEMA UNLESS YOU HAVE A GOOD REASON
-- IF YOU DO CHANGE IT WRITE THE JUSTIFICATION IN A COMMENT ABOVE THE CHANGE

drop database if exists restaurant;

create database restaurant;

use restaurant;

create table customers (
  username varchar(64) not null,
  password varchar(128) not null,

  -- TO use username as a foreign key in place_orders
  -- And I believe that username should be a primary key, for such an implentation. It should not be possible to have duplicate usernames.
  CONSTRAINT pk_username PRIMARY KEY(username)
);

insert into customers(username, password) values
  ('fred', sha2('fred', 224)),
  ('barney', sha2('barney', 224)),
  ('wilma', sha2('wilma', 224)),
  ('betty', sha2('betty', 224)),
  ('pebbles', sha2('pebbles', 224));

-- TODO: Task 1.2
-- Write your task 1.2 below
CREATE TABLE place_orders (
	order_id CHAR(8) NOT NULL,
	payment_id VARCHAR(128) UNIQUE NOT NULL,
	order_date DATE NOT NULL,
	total DECIMAL(8,2) NOT NULL,
	username VARCHAR(64) NOT NULL,
	
	CONSTRAINT pk_order_id PRIMARY KEY(order_id),
	CONSTRAINT fk_username FOREIGN KEY(username) REFERENCES customers(username)
)