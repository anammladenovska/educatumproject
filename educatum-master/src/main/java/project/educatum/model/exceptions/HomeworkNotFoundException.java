package project.educatum.model.exceptions;

public class HomeworkNotFoundException extends RuntimeException {
    public HomeworkNotFoundException() {
        super("Homework not found");
    }
}
