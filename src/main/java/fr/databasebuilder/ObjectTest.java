package fr.databasebuilder;

import java.util.Date;

public class ObjectTest implements DatabaseBuilder {

    private String message;
    private boolean exist;
    private char letter;
    private int number;
    private Date dateBirth;
    private long size;

    public ObjectTest() {
        this.message = "Message test";
        this.exist = true;
        this.letter = 'M';
        this.number = 10;
        this.dateBirth = new Date("01/01/2000 00:00:10");
        this.size = 1000000000;
    }
}
