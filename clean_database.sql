-- Clean Database for Wearify
-- This will remove duplicate users and reset the database

USE wearify_db;

-- Drop all tables to start fresh
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;

-- Database is now clean
SELECT 'Database cleaned! Restart application to create fresh tables.' AS status;
