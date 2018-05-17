package ru.geekbrain.s3.e2;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {
    private String url;
    private Connection conn;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;

    public ProductRepository(String url) {
        this.url = url;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver problem");
            e.printStackTrace();
        }
    }

    public void createPackOfProduct(int count) {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            pst = conn.prepareStatement("INSERT INTO product (prodid, title, cost) VALUES(?, ?, ?)");
            for (int i = 0; i < count; i++) {
                pst.setInt(1, i + 1);
                pst.setString(2, "product_" + (i + 1));
                pst.setInt(3, 10 * (i + 1));
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearAll() {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            st = conn.createStatement();
            st.executeUpdate("DELETE FROM product");
            st.executeUpdate("DELETE FROM SQLITE_SEQUENCE WHERE NAME='product'");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCostByTitle(String title) throws SQLException {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            pst = conn.prepareStatement("SELECT * FROM product WHERE title = ?");
            pst.setString(1, title);
            rs = pst.executeQuery();
            System.out.println(rs.getInt("cost"));
        } catch (SQLException e) {
//            e.printStackTrace();
            throw e;
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCostFrom(int cost1, int cost2) {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM product");
            int count = 1;
            while(rs.next()) {
                if (rs.getInt("cost") >= Math.min(cost1, cost2) && rs.getInt("cost")<= Math.max(cost1, cost2)) {
                    System.out.println(count + ". " + rs.getString("title"));
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNewCostByTitle(String title, int cost) {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            pst = conn.prepareStatement("UPDATE product SET cost = ? WHERE title = ?");
            pst.setInt(1, cost);
            pst.setString(2, title);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void showAll() {
        emptyConn();
        try {
            conn = DriverManager.getConnection(url);
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM product");
            while(rs.next()) {
                int id = rs.getInt("id");
                int prodid = rs.getInt("prodid");
                String title = rs.getString("title");
                int cost = rs.getInt("cost");
                System.out.println("ID " + id + " - Type: " + prodid + ", Title: " + title + ", Cost: " + cost);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void emptyConn() {
        conn = null;
        st = null;
        pst = null;
        rs = null;
    }

}
