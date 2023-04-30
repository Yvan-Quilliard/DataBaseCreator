package fr.databasebuilder;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, SQLException {
        ObjectTest objectTest = new ObjectTest();
        new TableCreateORM().createTable(objectTest);
    }
}