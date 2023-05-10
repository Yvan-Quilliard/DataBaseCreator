package fr.databasebuilder;

import fr.databasebuilder.table.TableORM;

public interface ITableORM {

    default void createTable() {
        new TableORM(this).createTable();
    }

    default void dropTable() {
        new TableORM(this).dropTable();
    }

}
