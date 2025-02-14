-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Создание таблицы машин
CREATE TABLE IF NOT EXISTS cars (
    id SERIAL PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    year INT NOT NULL CHECK (year >= 1886 AND year <= 2100),
    price DOUBLE PRECISION NOT NULL CHECK (price > 0)
);

-- Создание таблицы сотрудников
CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2) NOT NULL CHECK (salary > 0)
);

-- Создание таблицы производственных машин
CREATE TABLE IF NOT EXISTS car_producing_machines (
    id SERIAL PRIMARY KEY,
    machine_name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL CHECK (capacity > 0),
    operational BOOLEAN NOT NULL
);

-- Создание таблицы заказов
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW()
);
