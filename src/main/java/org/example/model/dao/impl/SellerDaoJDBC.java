package org.example.model.dao.impl;

import lombok.AllArgsConstructor;
import org.example.db.DB;
import org.example.db.DbException;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

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

            Department dep = Department.builder()
                    .id(rs.getInt("department_id"))
                    .name(rs.getString("dep_name"))
                    .build();

            return Seller.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .baseSalary(rs.getDouble("base_salary"))
                    .birthDate(rs.getObject("birth_date", LocalDate.class))
                    .department(dep)
                    .build();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
