CREATE DATABASE IF NOT EXISTS rmi;
CREATE USER 'Admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL ON rmi.* TO 'Admin'@'localhost';
