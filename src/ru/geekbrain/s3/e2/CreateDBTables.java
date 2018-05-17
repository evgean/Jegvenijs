package ru.geekbrain.s3.e2;

import java.sql.*;

public class CreateDBTables {

    private Connection conn;
    private Statement st;
    private String url;

    public CreateDBTables(String url) {
        this.url = url;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver problem");
            e.printStackTrace();
        }
    }

    public void createProductTable() {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            st = conn.createStatement();
            st.execute("CREATE TABLE product("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                    "prodid INTEGER ,"+
                    "title CHAR(50) ,"+
                    "cost INTEGER)");
        } catch (SQLException e) {
            System.out.println("Problem with creating table: Product");
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection");
                e.printStackTrace();
            }
        }
    }

    public void showMeta() {
        emptyConn();
        try{
            conn = DriverManager.getConnection(url);
            ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            while(rs.next())
            {
                //Print
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    private void emptyConn() {
        conn = null;
        st = null;
    }
}
