package camp.model;
import java.util.List;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;

    private List<Subject> subjectList; // 수강과목 저장할 리스트

    private static int NO = 1;

    public Student(String studentName) {
        studentId = "ST" + NO;
        this.studentName = studentName;
        this.subjectList = new ArrayList<>(); // 초기화
        ++NO;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

}
