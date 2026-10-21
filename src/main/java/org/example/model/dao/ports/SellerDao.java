package org.example.model.dao.ports;

import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.List;

public interface SellerDao extends BaseDao<Seller> {

    List<Seller> findByDepartment(Department department);
}
