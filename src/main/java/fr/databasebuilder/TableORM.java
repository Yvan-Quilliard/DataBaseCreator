package fr.databasebuilder;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class TableORM {

    private Object object;
    private ArrayList<FieldDatabase> fieldDatabases;

    public TableORM(@NotNull Object object) {
        this.object = object;
        this.fieldDatabases = new ArrayList<>();
        this.initFieldTable(object);
    }

    private void initFieldTable(@NotNull Object typeObjectTable) {
        Object obj = typeObjectTable;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().equals(String.class) || field.getType().equals(Date.class) || field.getType().equals(int.class) || field.getType().equals(boolean.class) || field.getType().equals(char.class) || field.getType().equals(long.class)) {
                try {
                    this.fieldDatabases.add(new FieldDatabase(field.getName(), field.get(obj).toString(), field.getType().getSimpleName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public TableORM createTable() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(this.object.getClass().getSimpleName()).append(" (id INT PRIMARY KEY AUTO_INCREMENT");

            for (FieldDatabase fieldDatabase : this.fieldDatabases) {
                queryBuilder.append(", ").append(fieldDatabase.formatForSQL());
            }
            queryBuilder.append(");");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.executeUpdate();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Table " + this.object.getClass().getSimpleName() + " created");
            connection.close();

            return this;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public TableORM dropTable() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            String query = "DROP TABLE IF EXISTS " + this.object.getClass().getSimpleName() + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            System.out.println("Request: " + query);
            System.out.println("Table " + this.object.getClass().getSimpleName() + " dropped");
            connection.close();

            return this;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }
}
