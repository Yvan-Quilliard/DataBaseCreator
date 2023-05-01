package fr.databasebuilder;

public interface DatabaseBuilder {

    default void createTable() {
        new TableORM(this).createTable();
    }

    default void dropTable() {
        new TableORM(this).dropTable();
    }

}
