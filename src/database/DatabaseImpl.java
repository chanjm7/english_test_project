package database;

import java.io.StringReader;
import java.sql.*;
import java.util.*;

public class DatabaseImpl implements Database{
    private String url = "jdbc:mysql://localhost/english?serverTimezone=UTC";
    private String id = "root";
    private String pw = "root";
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    //insert
    public void insertCategory(String category) {
        String sql = "INSERT INTO categories(name) VALUE('"+category+"');";
        executeSql(sql);
    }

    public void insertSentence(int categoryId, String sentence, String mean, String keyWord) {
        String sql = "INSERT INTO sentences(category_id, sentence, mean, keyword) " +
                "VALUES("+categoryId+", '"+sentence+"', '"+mean+"', '"+keyWord+"');";
        executeSql(sql);
    }

    //update
    public void updateCategory(String newCategory, int categoryId) {
        String sql = "UPDATE categories SET name = '"+newCategory+"' WHERE id = "+categoryId+";";
        executeSql(sql);
    }

    public void updateSentence(String newSentence, int sentenceId) {
        update("sentence", newSentence, sentenceId);
    }

    public void updateMean(String newMean, int sentenceId) {
        update("mean", newMean, sentenceId);
    }

    public void updateKeyword(String newKeyword, int sentenceId) {
        update("keyword", newKeyword, sentenceId);
    }

    private void update(String attribute, String newDomain, int sentenceId) {
        String sql = "UPDATE sentences SET "+attribute+" = '"+newDomain+"' WHERE id = "+sentenceId+";";
        executeSql(sql);
    }

    //delete
    public void deleteCategory(int categoryId) {
        delete("categories", categoryId);
    }

    public void deleteSentence(int sentenceId) {
        delete("sentences", sentenceId);
    }

    private void delete(String table, int id) {
        String sql = "DELETE FROM "+table+" WHERE id = "+id+";";
        executeSql(sql);
    }

    public List selectSentences(int categoryId) {
        List sentences = new ArrayList();
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT id, sentence, mean, keyword FROM sentences " +
                                            "WHERE category_id = " +categoryId+";");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer sentenceId = rs.getInt(1);
                String sentence = rs.getString(2);
                String mean = rs.getString(3);
                String keyword = rs.getString(4);
                sentences.add(new Object[] {sentenceId, sentence, mean, keyword});
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return sentences;
        }
    }

    public String[] selectSentence(int sentenceId) {
        String sentence = null;
        String mean = null;
        String keyword = "";
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT sentence, mean, keyword FROM sentences WHERE id = "+sentenceId+";");
            rs = pstmt.executeQuery();
            rs.next();
            sentence = rs.getString(1);
            mean = rs.getString(2);
            keyword = rs.getString(3);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return new String[] {sentence, mean, keyword};
        }
    }

    public Map selectCategories() {
        Map categories = new HashMap();
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT id, name FROM categories;");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt(1);
                String categoryName = rs.getString(2);
                categories.put(categoryId, categoryName);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return categories;
        }
    }


    private void executeSql(String sql) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement(sql);
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