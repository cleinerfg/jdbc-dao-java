package org.example.model.dao;

import org.example.db.DB;
import org.example.model.dao.adapters.SellerDaoJDBC;
import org.example.model.dao.ports.SellerDao;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
}
