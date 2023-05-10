package fr.databasebuilder.transorm;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class FieldDatabase {

    private String attribute;
    private String value;
    private String type;

    public FieldDatabase(String attribute, String value, String type) {
        this.attribute = attribute;
        this.value = value;
        this.type = type;
    }

    public String formatForSQL() {
        switch (type) {
            case "String":
                return attribute + " VARCHAR(500)";
            case "int":
                return attribute + " INT";
            case "boolean":
                return attribute + " BOOLEAN";
            case "char":
                return attribute + " CHAR";
            case "Date":
                return attribute + " DATETIME";
            case "long":
                return attribute + " BIGINT";
            default:
                return attribute + " VARCHAR(500)";
        }
    }

    public static ArrayList<FieldDatabase> getFieldArrayByObject(@NotNull Object object) {
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

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String toJSON() {
        return "{\"attribute\":\"" + attribute + "\",\"value\":\"" + value + "\",\"type\":\"" + type + "\"}";
    }

    @Override
    public String toString() {
        return "FieldDatabase{" + "attribute='" + attribute + '\'' + ", value='" + value + '\'' + ", type='" + type + '\'' + '}';
    }

}
