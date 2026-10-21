package org.example.model.dao;

import org.example.db.DB;
import org.example.model.dao.adapters.DepartmentDaoJDBC;
import org.example.model.dao.adapters.SellerDaoJDBC;
import org.example.model.dao.ports.DepartmentDao;
import org.example.model.dao.ports.SellerDao;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
