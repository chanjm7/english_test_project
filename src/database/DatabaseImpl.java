package database;

import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseImpl implements Database{
    private String url = "jdbc:mysql://localhost/english?serverTimezone=UTC";
    private String id = "root";
    private String pw = "root";
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;


    @Override
    public void InsertCategory(String category) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("INSERT INTO categories(name) VALUE(?)");
            pstmt.setString(1, category);
            pstmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public int selectCategoryId(String category) {
        int categoryId = 0;
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT id FROM categories WHERE name = "+ category);
            rs = pstmt.executeQuery();
            categoryId = rs.getInt("id");
            System.out.println(categoryId);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return categoryId;
        }
    }

    @Override
    public void sentenceInsert(String sentence, String category) {
    }

    @Override
    public void meanInsert(String mean) {

    }

    @Override
    public void keywordInsert(String keyword) {

    }

    @Override
    public List selectCategories() {
        List categories = new ArrayList();
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT name FROM categories;");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return categories;
        }

    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



