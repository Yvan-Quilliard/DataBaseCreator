package fr.databasebuilder;

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
