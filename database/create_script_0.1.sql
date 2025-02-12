CREATE DATABASE IF NOT EXISTS Leaf;  -- Creates the database if it doesn't exist

USE Leaf;  -- Selects the Leaf database for use

CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_role ENUM('administrator', 'doctor') NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE Disease (
    disease_id INT PRIMARY KEY AUTO_INCREMENT,
    disease_name VARCHAR(255) NOT NULL,
    description TEXT,
    causes TEXT,
    symptoms TEXT,
    diagnosis TEXT,
    treatments TEXT,
    pathogenesis TEXT,
    prevention TEXT,
    epidemiology TEXT,
    icd_10_code VARCHAR(20),
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INT,
    updated_at TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES User(user_id),
    FOREIGN KEY (updated_by) REFERENCES User(user_id)
);

CREATE TABLE Category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE Assignment (
    disease_id INT,
    category_id INT,
    FOREIGN KEY (disease_id) REFERENCES Disease(disease_id),
    FOREIGN KEY (category_id) REFERENCES Category(category_id),
    PRIMARY KEY (disease_id, category_id)
);