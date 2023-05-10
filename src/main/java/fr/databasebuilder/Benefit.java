package fr.databasebuilder;

import java.util.Date;

public class Benefit implements IModelORM, ITableORM {

    private String message;
    private boolean exist;
    private char letter;
    private int number;
    private Date dateBirth;
    private long size;

    public Benefit(String message, boolean exist, char letter, int number, Date dateBirth, long size) {
        this.message = message;
        this.exist = exist;
        this.letter = letter;
        this.number = number;
        this.dateBirth = dateBirth;
        this.size = size;
    }

    public Benefit() {
        this.message = "Message test";
        this.exist = true;
        this.letter = 'M';
        this.number = 10;
        this.dateBirth = new Date("01/12/2000 00:00:50");
        this.size = 1000000000;
    }

}
