package fr.databasebuilder.model;

import fr.databasebuilder.transorm.FieldDatabase;
import fr.databasebuilder.transorm.TransformValueSQL;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class ModelORM {

    private Object object;
    private ArrayList<FieldDatabase> fieldDatabases;

    public ModelORM(@NotNull Object object) {
        this.object = object;
        this.fieldDatabases = FieldDatabase.getFieldArrayByObject(object);
    }

    public ModelORM insert() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO ").append(this.object.getClass().getSimpleName()).append(" (");
            for (FieldDatabase fieldDatabase : this.fieldDatabases) {
                queryBuilder.append(" ").append(fieldDatabase.getAttribute()).append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append(") VALUES (");

            for (FieldDatabase fieldDatabase : this.fieldDatabases) {
                queryBuilder.append("'").append(TransformValueSQL.trasformValue(fieldDatabase.getType(), fieldDatabase.getValue())).append("', ");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append(");");

            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.executeUpdate();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Row inserted in table " + this.object.getClass().getSimpleName());
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public ModelORM update() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE ").append(this.object.getClass().getSimpleName()).append(" SET ");
            for (FieldDatabase fieldDatabase : this.fieldDatabases) {
                queryBuilder.append(fieldDatabase.getAttribute()).append(" = '").append(TransformValueSQL.trasformValue(fieldDatabase.getType(), fieldDatabase.getValue())).append("', ");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append(" WHERE id = ").append(this.getId()).append(";");

            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.executeUpdate();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Row updated in table " + this.object.getClass().getSimpleName());
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public ModelORM destroy() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("DELETE FROM ").append(this.object.getClass().getSimpleName()).append(" WHERE id = ").append(this.getId()).append(";");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.executeUpdate();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Row deleted in table " + this.object.getClass().getSimpleName());
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public String get(int id) {
        String json = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM ").append(this.object.getClass().getSimpleName()).append(" WHERE id = ").append(id).append(";");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Row selected in table " + this.object.getClass().getSimpleName());

            while (resultSet.next()) {
                json = "{";
                for (FieldDatabase fieldDatabase : this.fieldDatabases) {
                    json += "\"" + fieldDatabase.getAttribute() + "\":\"" + resultSet.getString(fieldDatabase.getAttribute()) + "\",";
                }
                json = json.substring(0, json.length() - 1);
                json += "}";
            }

            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return json.isEmpty() ? "Data not found" : json;
    }

    public String getAll() {
        String json = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM ").append(this.object.getClass().getSimpleName()).append(";");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Row selected in table " + this.object.getClass().getSimpleName());

            json = "[";
            while (resultSet.next()) {
                json += "{";
                for (FieldDatabase fieldDatabase : this.fieldDatabases) {
                    json += "\"" + fieldDatabase.getAttribute() + "\":\"" + resultSet.getString(fieldDatabase.getAttribute()) + "\",";
                }
                json = json.substring(0, json.length() - 1);
                json += "},";
            }
            json = json.substring(0, json.length() - 1);
            json += "]";

            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return json.isEmpty() ? "Data not found" : json;
    }

    public int getId() {
        int id = -1;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbuilder", "dbbuilder", "dbbuilder");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT id FROM ").append(this.object.getClass().getSimpleName()).append(" WHERE ");
            for (FieldDatabase fieldDatabase : FieldDatabase.getFieldArrayByObject(this.object)) {
                queryBuilder.append(fieldDatabase.getAttribute()).append(" = '").append(TransformValueSQL.trasformValue(fieldDatabase.getType(), fieldDatabase.getValue())).append("' AND ");
            }
            queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());
            queryBuilder.append(";");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            } else {
                System.out.println("Request: " + queryBuilder.toString());
                System.out.println("Id not found in table " + this.object.getClass().getSimpleName());
                connection.close();
                return id;
            }

            System.out.println("Request: " + queryBuilder.toString());
            System.out.println("Id found in table " + this.object.getClass().getSimpleName());
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return id;
    }

}
