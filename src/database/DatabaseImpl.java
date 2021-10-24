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
    @Override
    public void insertCategory(String category) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("INSERT INTO categories(name) VALUE(?)");
            pstmt.setString(1, category);
            pstmt.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalStateException("중복 입력");
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
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalStateException("중복 입력");
        }
        catch (SQLException e) {
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
    public int selectSentenceId(String sentence) {
        return 0;
    }

    @Override
    public Map selectSentences(String category) {
        int categoryId = selectCategoryId(category);
        Map sentences = new HashMap();
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("SELECT id, sentence, mean, keyword FROM sentences " +
                                            "WHERE category_id = " +categoryId+";");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int sentenceId = rs.getInt(1);
                String sentence = rs.getString(2);
                String mean = rs.getString(3);
                String keyword = rs.getString(4);
                sentences.put(sentenceId, "문장:"+sentence+" 뜻:"+mean+" 키워드:"+keyword);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return sentences;
        }
    }

    @Override
    public String selectSentence(int sentenceId) {
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
            return "문장:"+sentence+" 뜻:"+mean+" 키워드:"+keyword;
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

    @Override
    public void updateSentence(int sentenceId, String newSentence) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("UPDATE sentences SET sentence = '"+newSentence+"' " +
                                            "WHERE id = "+sentenceId+";");
            pstmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public void updateMean(int sentenceId, String newMean) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("UPDATE sentences SET mean = '"+newMean+"' " +
                    "WHERE id = "+sentenceId+";");
            pstmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void updateKeyword(int sentenceId, String newKeyword) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("UPDATE sentences SET keyword = '"+newKeyword+"' " +
                    "WHERE id = "+sentenceId+";");
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



