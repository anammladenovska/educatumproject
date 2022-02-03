package project.educatum.model.exceptions;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException(){
        super("Subject not found");
    }
}