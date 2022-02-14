package project.educatum.service;

import java.util.List;

public interface PaymentService {

    List<Object[]> getPaymentsQuery();

    Integer studentTeacherLoan(Integer studentId, Integer teacherId);

    List<Object[]> getListenedClassesQuery();

    Integer numListenedClasses(Integer idStudent, Integer idTeacher);
}
