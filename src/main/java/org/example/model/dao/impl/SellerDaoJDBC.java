package org.example.model.dao.impl;

import lombok.AllArgsConstructor;
import org.example.db.DB;
import org.example.db.DbException;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

    @Override
    public void insert(Seller seller) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "INSERT INTO sellers (name, email, birth_date, base_salary, department_id) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, seller.getName());
            pst.setString(2, seller.getEmail());
            pst.setObject(3, seller.getBirthDate());
            pst.setDouble(4, seller.getBaseSalary());
            pst.setInt(5, seller.getDepartment().getId());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error while inserting seller");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "UPDATE sellers " +
                            "SET name = ?, email = ?, birth_date = ?, " +
                            "base_salary = ?, department_id = ? " +
                            "WHERE id = ?"
            );

            pst.setString(1, seller.getName());
            pst.setString(2, seller.getEmail());
            pst.setObject(3, seller.getBirthDate());
            pst.setDouble(4, seller.getBaseSalary());
            pst.setInt(5, seller.getDepartment().getId());
            pst.setInt(6, seller.getId());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(
                    "SELECT sellers.*, departments.name as dep_name " +
                            "FROM sellers INNER JOIN departments " +
                            "ON sellers.department_id = departments.id " +
                            "WHERE sellers.id = ?"
            );

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (!rs.next()) return null;

            Department dep = instantiateDepartment(rs);
            return instantiateSeller(rs, dep);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement(
                    "SELECT sellers.*, departments.name AS dep_name " +
                            "FROM sellers INNER JOIN departments " +
                            "ON sellers.department_id = departments.id " +
                            "ORDER BY sellers.name"
            );

            rs = pst.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = instantiateDepartment(rs, map);
                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }

            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("SELECT sellers.*, departments.name AS dep_name " +
                    "FROM sellers INNER JOIN departments " +
                    "ON sellers.department_id = departments.id " +
                    "WHERE departments.id = ? " +
                    "ORDER BY sellers.name"
            );

            pst.setInt(1, department.getId());
            rs = pst.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = instantiateDepartment(rs, map);
                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }

            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return Department.builder()
                .id(rs.getInt("department_id"))
                .name(rs.getString("dep_name"))
                .build();
    }

    private Department instantiateDepartment(ResultSet rs, Map<Integer, Department> map) throws SQLException {
        int depId = rs.getInt("department_id");

        // Suppress Sonar warning S3824: Map.computeIfAbsent is avoided here because
        // calling a method throwing checked SQLException inside the lambda requires verbose try-catch wrappers.
        @SuppressWarnings("squid:S3824")
        Department dep = map.get(depId);
        if (dep == null) {
            dep = instantiateDepartment(rs);
            map.put(depId, dep);
        }

        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        return Seller.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .baseSalary(rs.getDouble("base_salary"))
                .birthDate(rs.getObject("birth_date", LocalDate.class))
                .department(dep)
                .build();
    }
}
