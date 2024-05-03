package camp.model;
import java.util.List;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private static int NO = 1;
    private final List<String> subjectList = new ArrayList<>();

    public Student(String studentName) {
        studentId = "STU" + NO;
        this.studentName = studentName;
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
}
