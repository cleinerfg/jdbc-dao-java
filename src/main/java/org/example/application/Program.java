package org.example.application;


import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.time.LocalDate;

public class Program {

    static void main() {
        Department dep = new Department(10, "Books");
        Seller seller = new Seller(21, "Bob",
                "bob@gmail.com", LocalDate.now(),
                3000.0, dep);
        
        System.out.println(seller);
    }
}
