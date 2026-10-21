package org.example.application;


import org.example.model.dao.DaoFactory;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Seller;

public class Program {

    static void main() {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("==== Seller: findById ===");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
    }
}
