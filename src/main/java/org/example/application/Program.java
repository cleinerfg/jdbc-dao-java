package org.example.application;


import org.example.model.dao.DaoFactory;
import org.example.model.dao.ports.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.time.LocalDate;
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

        System.out.println("\n==== Seller: insert ===");
        Seller newSeller = Seller.builder()
                .name("Stefan")
                .email("stefan@email.com")
                .birthDate(LocalDate.now())
                .baseSalary(4500.6)
                .department(department)
                .build();

        sellerDao.insert(newSeller);
        System.out.println("Inserted new seller with id " + newSeller.getId());

        System.out.println("\n==== Seller: update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Updated seller with id " + seller.getId());

        System.out.println("\n==== Seller: delete ===");
        IO.print("Enter the seller id to delete: ");
        int id = Integer.parseInt(IO.readln());
        sellerDao.deleteById(id);
        System.out.println("Deleted seller with id " + id);
    }
}
