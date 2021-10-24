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

    //insert
    @Override
    public void insertCategory(String category) {
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
    public void insertSentence(String category, String sentence, String mean, String keyWord) {
        int categoryId = selectCategoryId(category);
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("INSERT INTO sentences(category_id, sentence, mean, keyword) VALUES(?, ?, ?, ?)");
            pstmt.setInt(1, categoryId);
            pstmt.setString(2, sentence);
            pstmt.setString(3, mean);
            pstmt.setString(4, keyWord);
            pstmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    //select
    @Override
    public int selectCategoryId(String category) {
        int categoryId = 0;
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT id FROM categories WHERE name = '"+ category+"'");
            rs = pstmt.executeQuery();
            rs.next();
            categoryId = rs.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return categoryId;
        }
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

    //update
    @Override
    public void updateCategory(String category, String newCategory) {
        int categoryId = selectCategoryId(category);
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("UPDATE categories SET name = '"+newCategory+
                    "' WHERE id = '"+categoryId+"';");
            pstmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
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



