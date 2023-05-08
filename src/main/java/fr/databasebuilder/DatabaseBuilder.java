package fr.databasebuilder;

public interface DatabaseBuilder {

    default void createTable() {
        new TableORM(this).createTable();
    }

    default void dropTable() {
        new TableORM(this).dropTable();
    }

    default void insert() {
        new CrudORM(this).insert();
    }

    default void update() {
        new CrudORM(this).update();
    }

    default void destroy() {
        new CrudORM(this).destroy();
    }

    default String show(final int id) {
        return new CrudORM(this).show(id);
    }

    default String getAll() {
        return new CrudORM(this).getAll();
    }

}
