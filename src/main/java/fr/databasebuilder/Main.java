package fr.databasebuilder;

public class Main {
    public static void main(String[] args) {
        ObjectTest objectTest = new ObjectTest();
        objectTest.dropTable();
        objectTest.createTable();
    }
}