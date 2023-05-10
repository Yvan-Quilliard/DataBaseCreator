package fr.databasebuilder;

import fr.databasebuilder.model.ModelORM;

public interface IModelORM {

    default void insert() {
        new ModelORM(this).insert();
    }

    default void update() {
        new ModelORM(this).update();
    }

    default void destroy() {
        new ModelORM(this).destroy();
    }

    default String get(final int id) {
        return new ModelORM(this).get(id);
    }

    default String getAll() {
        return new ModelORM(this).getAll();
    }

}
