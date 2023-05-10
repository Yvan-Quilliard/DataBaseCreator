package fr.databasebuilder.table;

import fr.databasebuilder.transorm.FieldDatabase;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class TableORM {

    private Object object;
    private ArrayList<FieldDatabase> fieldDatabases;

    public TableORM(@NotNull Object object) {
        this.object = object;
        this.fieldDatabases = FieldDatabase.getFieldArrayByObject(object);
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
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("DROP TABLE IF EXISTS ").append(this.object.getClass().getSimpleName()).append(";");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.executeUpdate();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Table " + this.object.getClass().getSimpleName() + " dropped");
            connection.close();

            return this;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }

}
