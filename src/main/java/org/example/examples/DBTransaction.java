package org.example.examples;

import org.example.db.DB;
import org.example.db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTransaction {

    static void main() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DB.getConnection();

            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            int rowsAffected1 = stmt.executeUpdate("UPDATE sellers " +
                    "SET base_salary = 2090" +
                    " WHERE department_id = 1");

            if (true) throw new SQLException("Fake error");

            int rowsAffected2 = stmt.executeUpdate("UPDATE sellers " +
                    "SET base_salary = 3090" +
                    " WHERE department_id = 2");

            conn.commit();

            System.out.println("rowsAffected1: " + rowsAffected1);
            System.out.println("rowsAffected2: " + rowsAffected2);

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transaction rolled back, caused by: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Error trying to rollback:  " + ex.getMessage());
            }
        } finally {
            DB.closeStatement(stmt);
            DB.closeConnection();
        }
    }

}
