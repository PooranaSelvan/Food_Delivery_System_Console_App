DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS order_delivery_agents;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS hotels;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS deliveryAgents;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS users;


CREATE TABLE users(userId int primary key auto_increment NOT NULL, 
                    name varchar(100) NOT NULL, 
                    password varchar(255) NOT NULL, 
                    phone varchar(15) UNIQUE NOT NULL, 
                    email varchar(50) UNIQUE NOT NULL);


CREATE TABLE customers(customerId INT PRIMARY KEY NOT NULL,
                    address varchar(255) NOT NULL,

                    FOREIGN KEY(customerId) REFERENCES users(userId) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE deliveryAgents(agentId INT PRIMARY KEY NOT NULL,
                    totalEarnings int DEFAULT 0 NOT NULL,

                    FOREIGN KEY(agentId) REFERENCES users(userId) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE admins(adminId INT PRIMARY KEY NOT NULL,

                    FOREIGN KEY(adminId) REFERENCES users(userId) ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE hotels(hotelId int primary key auto_increment, 
                    name varchar(100) NOT NULL,
                    location varchar(50) NOT NULL,
                    rating decimal(2, 1) NOT NULL,
                    isOpen boolean NOT NULL);


CREATE TABLE items(itemId int primary key auto_increment NOT NULL, 
                    hotelId int NOT NULL,
                    name varchar(100) NOT NULL,
                    price int NOT NULL,
                    category ENUM('SNACKS','DRINKS','VEG','NON-VEG') NOT NULL,
                    description varchar(100) NOT NULL,

                    FOREIGN KEY(hotelId) REFERENCES hotels(hotelId) ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE orders(orderId int primary key auto_increment NOT NULL,
                    customerId int NOT NULL,
                    hotelId int NOT NULL,
                    totalAmount int NOT NULL,
                    createdAt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                    
                    FOREIGN KEY(customerId) REFERENCES customers(customerId) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(hotelId) REFERENCES hotels(hotelId) ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE order_delivery_agents(id int primary key auto_increment NOT NULL,
                    agentId int NOT NULL,
                    orderId int NOT NULL,
                    orderStatus ENUM('ORDERED','ON_THE_WAY','DELIVERED') NOT NULL,

                    FOREIGN KEY(agentId) REFERENCES deliveryAgents(agentId) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(orderId) REFERENCES orders(orderId) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE order_items(id int primary key auto_increment NOT NULL, 
                    orderId int NOT NULL,
                    itemId int NOT NULL,
                    quantity int NOT NULL,
                    price int NOT NULL,

                    FOREIGN KEY(orderId) REFERENCES orders(orderId) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(itemId) REFERENCES items(itemId) ON DELETE CASCADE ON UPDATE CASCADE);