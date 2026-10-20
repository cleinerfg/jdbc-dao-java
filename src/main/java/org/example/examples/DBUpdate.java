package org.example.examples;

import org.example.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUpdate {

    static void main() {
        System.out.println("--- Simple Update ---");
        simpleUpdate();
    }

    public static void simpleUpdate() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DB.getConnection();

            pstmt = conn.prepareStatement(
                    "UPDATE sellers" +
                            " SET base_salary = base_salary + ? " +
                            "WHERE department_id = ?"
            );

            pstmt.setDouble(1, 200.0);
            pstmt.setInt(2, 4);

            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(pstmt);
            DB.closeConnection();
        }
    }

}
