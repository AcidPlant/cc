-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
                                     password VARCHAR(50) NOT NULL,
                                     role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS cars (
                                    id SERIAL PRIMARY KEY,
                                    model VARCHAR(100) NOT NULL,
                                    category VARCHAR(50) NOT NULL,
                                    price DOUBLE PRECISION NOT NULL,
                                    year INT NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR(100) NOT NULL,
                                         position VARCHAR(100) NOT NULL,
                                         salary NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS machines (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
                                        capacity INT NOT NULL,
                                        status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
                                      id SERIAL PRIMARY KEY,
                                      user_id INT NOT NULL,
                                      order_date DATE NOT NULL,
                                      FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS order_items (
                                           id SERIAL PRIMARY KEY,
                                           order_id INT NOT NULL,
                                           product_id INT NOT NULL,
                                           quantity INT NOT NULL,
                                           price DOUBLE PRECISION NOT NULL,
                                           FOREIGN KEY (order_id) REFERENCES orders(id)
);