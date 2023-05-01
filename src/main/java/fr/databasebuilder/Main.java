package fr.databasebuilder;

public class Main {
    public static void main(String[] args) {
        Benefit benefit = new Benefit();
        benefit.dropTable();
        benefit.createTable();
        benefit.insert();
        benefit.setLetter('F');
        benefit.update();
//        benefit.destroy();
    }
}