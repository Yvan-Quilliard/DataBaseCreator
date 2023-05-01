package fr.databasebuilder;

import java.util.Date;

public class Benefit implements DatabaseBuilder {

    private String message;
    private boolean exist;
    private char letter;
    private int number;
    private Date dateBirth;
    private long size;

    public Benefit() {
        this.message = "Message test";
        this.exist = true;
        this.letter = 'M';
        this.number = 10;
        this.dateBirth = new Date("01/12/2000 00:00:50");
        this.size = 1000000000;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
