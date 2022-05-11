package project.educatum.model.exceptions;

public class ClassNotFoundException extends RuntimeException{
    public ClassNotFoundException() {
        super("Class not found");
    }
}
