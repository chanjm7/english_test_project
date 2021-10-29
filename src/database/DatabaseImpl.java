package database;

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

    public void insertSentenceSet(int categoryId, String sentence, String mean, String keyWord) {
        String sql = "INSERT INTO sentences(category_id, sentence, mean, keyword) " +
                "VALUES("+categoryId+", \""+sentence+"\", \""+mean+"\", \""+keyWord+"\");";
        executeSql(sql);
    }

    public void insertWrongAnswer(int sentenceId) {
        String sql = "INSERT INTO wrong_answer(sentence_id) value("+sentenceId+");";
        executeSql(sql);
    }

    //update
    public void updateWrongNum(int sentenceId) {
        String sql = "UPDATE wrong_answer SET wrong_num = wrong_num + 1 WHERE sentence_id = "+sentenceId+";";
        executeSql(sql);
    }

    public void updateCategory(String newCategory, int categoryId) {
        String sql = "UPDATE categories SET name = '"+newCategory+"' WHERE id = "+categoryId+";";
        executeSql(sql);
    }

    private void updateSentenceSet(String attribute, String newDomain, int sentenceId) {
        String sql = "UPDATE sentences SET "+attribute+" = \""+newDomain+"\" WHERE id = "+sentenceId+";";
        executeSql(sql);
    }
    public void updateSentence(String newSentence, int sentenceId) {
        updateSentenceSet("sentence", newSentence, sentenceId);
    }
    public void updateMean(String newMean, int sentenceId) {
        updateSentenceSet("mean", newMean, sentenceId);
    }

    public void updateKeyword(String newKeyword, int sentenceId) {
        updateSentenceSet("keyword", newKeyword, sentenceId);
    }

    //delete
    public void deleteCategory(int categoryId) {
        delete("categories", categoryId);
    }
    public void deleteSentence(int sentenceId) {
        delete("sentences", sentenceId);
    }

    public void deleteWrongAnswer(int sentenceId) {
        String sql = "DELETE FROM wrong_answer WHERE sentence_id = "+sentenceId+";";
        executeSql(sql);
    }

    private void delete(String table, int id) {
        String sql = "DELETE FROM '"+table+"' WHERE id = "+id+";";
        executeSql(sql);
    }

    //select
    public List selectSentenceSets(int categoryId) {
        List sentenceSets = new ArrayList();
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
                sentenceSets.add(new Object[] {sentenceId, sentence, mean, keyword});
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return sentenceSets;
        }
    }

    public String[] selectSentenceSet(int sentenceId) {
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

    public List selectWrongAnswers() {
        String sql = "SELECT id, sentence, mean, keyword, wrong_num " +
                     "FROM sentences, wrong_answer " +
                     "WHERE id = sentence_id;";

        List wrongAnswers = new ArrayList();
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int sentenceId = rs.getInt(1);
                String sentence = rs.getString(2);
                String mean = rs.getString(3);
                String keyword = rs.getString(4);
                int wrongNum = rs.getInt(5);
                wrongAnswers.add(new Object[]{sentenceId, sentence, mean, keyword, wrongNum});
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return wrongAnswers;
        }
    }

    //check
    public boolean checkDuplicateWrongAnswer(int sentenceId) {
        int result = 0;
        String sql = "SELECT EXISTS (SELECT sentence_id FROM wrong_answer WHERE sentence_id ="+sentenceId+")as success;";
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            return result == 1;
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