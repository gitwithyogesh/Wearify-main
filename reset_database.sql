-- Reset Wearify Database
-- Run this script in MySQL to fix database schema issues

-- Drop database and recreate
DROP DATABASE IF EXISTS wearify_db;
CREATE DATABASE wearify_db;
USE wearify_db;

-- Database is now clean and ready for Hibernate to create tables
SELECT 'Database reset complete!' AS status;
