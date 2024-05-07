package camp.model;
import java.util.List;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;

    private List<Subject> enrolledSubjects; // 수강과목 저장할 리스트

    private static int NO = 1;
    private final List<String> subjectList = new ArrayList<>();

    public Student(String studentName) {
        studentId = "STU" + NO;
        this.studentName = studentName;
        this.enrolledSubjects = new ArrayList<>(); // 초기화
        ++NO;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<String> getSubjectList() {
        return subjectList;
    }

    public List<Subject> getEnrolledSubjects() { // 수강과목 getter
        return enrolledSubjects;
    }
}
