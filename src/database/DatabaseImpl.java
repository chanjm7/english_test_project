package database;

import java.io.StringReader;
import java.sql.*;
import java.util.Scanner;

public class DatabaseImpl implements Database{
    private String url = "jdbc:mysql://localhost/english?serverTimezone=UTC";
    private String id = "root";
    private String pw = "root";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    @Override
    public void categoryInsert(String category) {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            pstmt = conn.prepareStatement("INSERT INTO categories(name) VALUE(?)");
            pstmt.setString(1, category);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void sentenceInsert(String sentence) {

    }

    @Override
    public void meanInsert(String mean) {

    }

    @Override
    public void keywordInsert(String keyword) {

    }
}
