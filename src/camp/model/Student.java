package camp.model;
import java.util.List;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private String studentStatus;
    private List<Subject> subjectList; // 수강과목 저장할 리스트

    private static int NO = 1;

    public Student(String studentName, String studentStatus) {
        studentId = "ST" + NO;
        this.studentName = studentName;
        this.subjectList = new ArrayList<>(); // 초기화
        this.studentStatus = studentStatus;
        ++NO;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

}