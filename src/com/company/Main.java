package com.company;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/lol8";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "1111";

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            try {
                // create connection
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                initDB();

                while (true) {
                    System.out.println("1: add client");
                    System.out.println("2: add products");
                    System.out.println("3: add order");
                    System.out.println("4: view tables");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addCustomer(sc);
                            break;
                        case "2":
                            addProduct(sc);
                            break;
                        case "3":
                            addOrders(sc);
                            break;
                        case "4":
                            viewClients();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void initDB() throws SQLException {
        Statement st = conn.createStatement();
        try {
            st.execute("CREATE TABLE IF NOT EXISTS Customers (cust_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  first_name VARCHAR(128), last_name VARCHAR(128), phone VARCHAR(128), adress VARCHAR(128));");
            st.execute("ALTER TABLE Customers AUTO_INCREMENT = 1001;");

            st.execute("CREATE TABLE IF NOT EXISTS Products(prod_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(128)NOT NULL, price INT NOT NULL,guantity INT);");
            st.execute("ALTER TABLE Products AUTO_INCREMENT = 2001;");

            st.execute("CREATE TABLE IF NOT EXISTS Orders (ord_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, cost_of_delivery INT NOT NULL, ordDate DATE NOT NULL, customerId INT REFERENCES Customers (cast_Id), productId INT REFERENCES Products (prod_Id));");
            st.execute("ALTER TABLE Orders AUTO_INCREMENT = 3001;");
        } finally {
            st.close();
        }
    }

    private static void addCustomer(Scanner sc) throws SQLException {
        System.out.print("Enter client first_name: ");
        String first_name = sc.nextLine();
        System.out.print("Enter client last_name: ");
        String last_name = sc.nextLine();
        System.out.print("Enter client phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter client adress: ");
        String adress = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Customers (first_name, last_name, phone, adress) VALUES(?, ?,?, ?)");
        try {
            ps.setString(1, first_name);
            ps.setString(2, last_name);

            ps.setString(3, phone);
            ps.setString(4, adress);

            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    private static void addProduct(Scanner sc) throws SQLException {
        System.out.print("Enter Product name: ");
        String name = sc.nextLine();
        System.out.print("Enter Product price: ");
        int price = sc.nextInt();
        System.out.print("Enter Product guantity: ");
        int guantity = sc.nextInt();


        PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (name, price, guantity) VALUES(?, ?,?)");
        try {
            ps.setString(1, name);
            ps.setInt(2, price);

            ps.setInt(3, guantity);

            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    private static void addOrders(Scanner sc) throws SQLException {
        System.out.print("Enter cost of delivery: ");
        int cost_of_delivery = sc.nextInt();
        System.out.print("Enter Product ordDate: ");
        int ordDate = sc.nextInt();
        System.out.print("Enter Product customerId: ");
        int customerId = sc.nextInt();
        System.out.print("Enter Product productsId: ");
        int productId = sc.nextInt();


        PreparedStatement ps = conn.prepareStatement("INSERT INTO Orders (cost_of_delivery, ordDate, customerId, productId ) VALUES(?, ?,?,?)");
        try {
            ps.setInt(1, cost_of_delivery);
            ps.setInt(2, ordDate);

            ps.setInt(3, customerId);
            ps.setInt(4, productId);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }




    private static void viewClients() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customers");
        try {
            // table of data representing a database result set,
            ResultSet rs = ps.executeQuery();
            try {
                // can be used to get information about the types and properties of the columns in a ResultSet object
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
        System.out.println();

        PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Products");
        try {
            // table of data representing a database result set,
            ResultSet rs1 = ps1.executeQuery();
            try {
                // can be used to get information about the types and properties of the columns in a ResultSet object
                ResultSetMetaData md1 = rs1.getMetaData();

                for (int i = 1; i <= md1.getColumnCount(); i++)
                    System.out.print(md1.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs1.next()) {
                    for (int i = 1; i <= md1.getColumnCount(); i++) {
                        System.out.print(rs1.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs1.close(); // rs can't be null according to the docs
            }
        } finally {
            ps1.close();
        }
        System.out.println();

        PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM Orders");
        try {
            // table of data representing a database result set,
            ResultSet rs2 = ps2.executeQuery();
            try {
                // can be used to get information about the types and properties of the columns in a ResultSet object
                ResultSetMetaData md2 = rs2.getMetaData();

                for (int i = 1; i <= md2.getColumnCount(); i++)
                    System.out.print(md2.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs2.next()) {
                    for (int i = 1; i <= md2.getColumnCount(); i++) {
                        System.out.print(rs2.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs2.close(); // rs can't be null according to the docs
            }
        } finally {
            ps2.close();
        }
    }

}
