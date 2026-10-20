package org.example;


import org.example.db.DB;

import java.sql.Connection;

public class Program {

    static void main() {
        Connection connection = DB.getConnection();
        DB.closeConnection();
    }
}
