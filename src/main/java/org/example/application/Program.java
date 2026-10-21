package org.example.application;


import org.example.model.dao.DaoFactory;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.List;

public class Program {

    static void main() {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("==== Seller: findById ===");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);

        System.out.println("\n==== Seller: findByDepartment ===");
        Department department = Department.builder().id(2).build();
        List<Seller> sellersByDepartment = sellerDao.findByDepartment(department);
        sellersByDepartment.forEach(System.out::println);

        System.out.println("\n==== Seller: findAll ===");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);
    }
}
