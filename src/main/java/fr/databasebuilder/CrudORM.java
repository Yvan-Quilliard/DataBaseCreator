package fr.databasebuilder;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class CrudORM {

    private Object object;
    private ArrayList<FieldDatabase> fieldDatabases;

    public CrudORM(@NotNull Object object) {
        this.object = object;
        this.fieldDatabases = getFieldArrayByObject(object);
    }

    private ArrayList<FieldDatabase> getFieldArrayByObject(@NotNull Object object) {
        ArrayList<FieldDatabase> fieldDatabases = new ArrayList<>();
        Object obj = object;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().equals(String.class) || field.getType().equals(Date.class) || field.getType().equals(int.class) || field.getType().equals(boolean.class) || field.getType().equals(char.class) || field.getType().equals(long.class)) {
                try {
                    fieldDatabases.add(new FieldDatabase(field.getName(), field.get(obj).toString(), field.getType().getSimpleName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return fieldDatabases;
    }

    public CrudORM insert() {
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

    public CrudORM update() {
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

    public CrudORM destroy() {
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

    public String show(int id) {
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
            for (FieldDatabase fieldDatabase : getFieldArrayByObject(this.object)) {
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
