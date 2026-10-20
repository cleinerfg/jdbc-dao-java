package org.example.examples;

import org.example.db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBFetching {

    static void main() {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM departments");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
    }
}
