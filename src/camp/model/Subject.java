package camp.model;

public class Subject {
  private String subjectId;
  private String subjectName;
  private String subjectType;

  public Subject(){
  }
  
  public Subject(String seq, String subjectName, String subjectType) {
    this.subjectId = seq;
    this.subjectName = subjectName;
    this.subjectType = subjectType;
  }
  // Setter
  public void setSubjectType(String subjectType) {
    this.subjectType = subjectType;
  }
  
  // Getter
  public String getSubjectId() {
    return subjectId;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public String getSubjectType() {
    return subjectType;
  }
}
