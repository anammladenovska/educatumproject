package project.educatum.model.exceptions;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(){
        super("Teacher not found");
    }
}