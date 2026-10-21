package org.example.model.dao.adapters;

import lombok.AllArgsConstructor;
import org.example.db.DB;
import org.example.db.DbException;
import org.example.model.dao.ports.DepartmentDao;
import org.example.model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection conn;

    @Override
    public void insert(Department department) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "INSERT INTO departments (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, department.getName());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    department.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error while inserting department");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "UPDATE departments SET name = ? WHERE id = ?"
            );

            pst.setString(1, department.getName());
            pst.setInt(2, department.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "DELETE FROM departments WHERE id = ?"
            );

            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(
                    "SELECT * FROM departments WHERE id = ?"
            );

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (!rs.next()) return null;

            return instantiateDepartment(rs);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(
                    "SELECT * FROM departments ORDER BY name"
            );

            rs = pst.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (rs.next()) {
                departments.add(instantiateDepartment(rs));
            }

            return departments;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return Department.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
