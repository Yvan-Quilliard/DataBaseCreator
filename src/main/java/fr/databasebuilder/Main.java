package fr.databasebuilder;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Benefit benefit = new Benefit();
        Benefit benefit1 = new Benefit("Message test1", true, 'M', 10, new Date("01/12/2000 00:00:50"), 10);
        Benefit benefit2 = new Benefit("Message test2", true, 'F', 20, new Date("01/12/2000 00:00:50"), 20);
        Benefit benefit3 = new Benefit("Message test3", true, 'N', 30, new Date("01/12/2000 00:00:50"), 30);

        benefit.dropTable();
        benefit.createTable();

        benefit1.insert();
        benefit2.insert();
        benefit3.insert();

        System.out.println(benefit.getOne(1));
        System.out.println(benefit.getOne(2));
        System.out.println(benefit.getOne(3));
        System.out.println(benefit.getAll());

        benefit1.destroy();
        benefit2.destroy();
        benefit3.destroy();
    }
}