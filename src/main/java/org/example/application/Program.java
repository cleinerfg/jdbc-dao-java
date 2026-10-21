package org.example.application;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.ports.DepartmentDao;
import org.example.model.dao.ports.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Program {

    static void main() {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        // --- SELLER ---
        testSellerFindById(sellerDao);
        testSellerFindByDepartment(sellerDao);
        testSellerFindAll(sellerDao);
        testSellerInsert(sellerDao);
        testSellerUpdate(sellerDao);
        testSellerDelete(sellerDao);

        // --- DEPARTMENT ---
        testDepartmentFindById(departmentDao);
        testDepartmentFindAll(departmentDao);
        testDepartmentInsert(departmentDao);
        testDepartmentUpdate(departmentDao);
        testDepartmentDelete(departmentDao);
    }

    private static void testSellerFindById(SellerDao sellerDao) {
        System.out.println("==== Seller: findById ===");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
    }

    private static void testSellerFindByDepartment(SellerDao sellerDao) {
        System.out.println("\n==== Seller: findByDepartment ===");
        Department department = Department.builder().id(2).build();
        List<Seller> sellersByDepartment = sellerDao.findByDepartment(department);
        sellersByDepartment.forEach(System.out::println);
    }

    private static void testSellerFindAll(SellerDao sellerDao) {
        System.out.println("\n==== Seller: findAll ===");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);
    }

    private static void testSellerInsert(SellerDao sellerDao) {
        System.out.println("\n==== Seller: insert ===");
        Department department = Department.builder().id(2).build();
        Seller newSeller = Seller.builder()
                .name("Stefan")
                .email("stefan@email.com")
                .birthDate(LocalDate.now())
                .baseSalary(4500.6)
                .department(department)
                .build();

        sellerDao.insert(newSeller);
        System.out.println("Inserted new seller with id " + newSeller.getId());
    }

    private static void testSellerUpdate(SellerDao sellerDao) {
        System.out.println("\n==== Seller: update ===");
        Seller seller = sellerDao.findById(1);
        if (seller != null) {
            seller.setName("Martha Waine");
            sellerDao.update(seller);
            System.out.println("Updated seller with id " + seller.getId());
        }
    }

    private static void testSellerDelete(SellerDao sellerDao) {
        System.out.println("\n==== Seller: delete ===");
        IO.print("Enter the seller id to delete: ");
        int id = Integer.parseInt(IO.readln());
        sellerDao.deleteById(id);
        System.out.println("Deleted seller with id " + id);
    }

    private static void testDepartmentFindById(DepartmentDao departmentDao) {
        System.out.println("\n==== Department: findById ===");
        Department department = departmentDao.findById(1);
        System.out.println(department);
    }

    private static void testDepartmentFindAll(DepartmentDao departmentDao) {
        System.out.println("\n==== Department: findAll ===");
        List<Department> departments = departmentDao.findAll();
        departments.forEach(System.out::println);
    }

    private static void testDepartmentInsert(DepartmentDao departmentDao) {
        System.out.println("\n==== Department: insert ===");
        Department newDepartment = Department.builder()
                .name("Music")
                .build();

        departmentDao.insert(newDepartment);
        System.out.println("Inserted new department with id " + newDepartment.getId());
    }

    private static void testDepartmentUpdate(DepartmentDao departmentDao) {
        System.out.println("\n==== Department: update ===");
        Department department = departmentDao.findById(1);
        if (department != null) {
            department.setName("Computers & Tech");
            departmentDao.update(department);
            System.out.println("Updated department with id " + department.getId());
        }
    }

    private static void testDepartmentDelete(DepartmentDao departmentDao) {
        System.out.println("\n==== Department: delete ===");
        IO.print("Enter the department id to delete: ");
        int id = Integer.parseInt(IO.readln());
        departmentDao.deleteById(id);
        System.out.println("Deleted department with id " + id);
    }
}
