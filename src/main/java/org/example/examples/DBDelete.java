package org.example.examples;

import org.example.db.DB;
import org.example.db.DBIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDelete {

    static void main() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DB.getConnection();

            pstmt = conn.prepareStatement(
                    "DELETE FROM departments " +
                            "WHERE id = ?"
            );

            pstmt.setInt(1, 2); // contains records with an FK

            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DBIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(pstmt);
            DB.closeConnection();
        }
    }

}
