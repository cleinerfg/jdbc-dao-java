package org.example.examples;

import org.example.db.DB;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBInsert {

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static void main() {
        System.out.println("--- Simple Insert ---");
        simpleInsert();

        System.out.println("\n--- Multi Insert ---");
        multiInsert();
    }

    public static void simpleInsert() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DB.getConnection();

            // Overloaded prepareStatement method: Statement.RETURN_GENERATED_KEYS is an optional parameter
            // used only when we need to retrieve the database auto-incremented primary key.
            pstmt = conn.prepareStatement(
                    "INSERT INTO sellers (name, email, birth_date, base_salary, department_id) " +
                            "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, "Jeremy");
            pstmt.setString(2, "jeremy@gmail.com");

            // setDate -> legacy since 2017 (JDBC 4.2 / Java 8+)
            // pstmt.setDate(3, Date.valueOf(LocalDate.parse("10/01/2002", fmt)));

            pstmt.setObject(3, LocalDate.parse("10/01/1995", fmt));
            pstmt.setDouble(4, 3560.65);
            pstmt.setInt(5, 4);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! Seller ID: " + id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("Insert failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(pstmt);
            DB.closeConnection();
        }
    }

    public static void multiInsert() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DB.getConnection();

            pstmt = conn.prepareStatement(
                    "INSERT INTO departments (name) VALUES (?), (?), (?), (?)",
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, "D1");
            pstmt.setString(2, "D2");
            pstmt.setString(3, "D3");
            pstmt.setString(4, "D4");

            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Affected rows: " + rowsAffected);

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Generated ID: " + id);
                }
                DB.closeResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(pstmt);
            DB.closeConnection();
        }
    }
}
