package fr.databasebuilder;

public interface DatabaseBuilder {

    default void createTable() {
        new TableORM(this).createTable();
    }

    default void dropTable() {
        new TableORM(this).dropTable();
    }

    default void insert() {
        new TableORM(this).insert();
    }

    default void update() {
        new TableORM(this).update();
    }

    default void destroy() {
        new TableORM(this).destroy();
    }

}
