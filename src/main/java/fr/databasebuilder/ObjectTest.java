package fr.databasebuilder;

public class ObjectTest {

    private int id;
    private String message;
    private boolean exist;
    private char letter;

    public ObjectTest() {
        this.id = 10;
        this.message = "Message test";
        this.exist = true;
        this.letter = 'M';
    }

    @Override
    public String toString() {
        return "ObjectTest{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", exist=" + exist +
                ", letter=" + letter +
                '}';
    }
}
