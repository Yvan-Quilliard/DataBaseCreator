package fr.databasebuilder;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreateORM {

    public void createTable(@NotNull Object typeObjectTable) throws IllegalAccessException, SQLException {
        Object obj = typeObjectTable;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                System.out.println("{attribute = \"name:" + field.getName() + "\", \"value:" + field.get(obj) + "\", \"type:" + field.getType().getSimpleName() + "\"}");
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS");
            }
        }
    }

}
