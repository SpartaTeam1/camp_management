package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
//import java.util.stream.Collectors;

/**
 * Notification Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다. main 메서드를 실행하면 프로그램이 실행됩니다. model 의 클래스들과
 * 아래 (// 기능 구현...) 주석 부분을 완성해주세요! 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다! 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게
 * 이용해주세요!
 */
public class CampManagementApplication {

  // 데이터 저장소
  private static List<Student> studentStore;
  private static List<Subject> subjectStore;
  private static List<Score> scoreStore;

  // 과목 타입
  private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
  private static String SUBJECT_TYPE_CHOICE = "CHOICE";

  // index 관리 필드
  private static int studentIndex;
  private static final String INDEX_TYPE_STUDENT = "ST";
  private static int subjectIndex;
  private static final String INDEX_TYPE_SUBJECT = "SU";
  private static int scoreIndex;
  private static final String INDEX_TYPE_SCORE = "SC";

  // 스캐너
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    setInitData();
    try {
      displayMainView();

    } catch (Exception e) {
      System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
    }
  }

  // 초기 데이터 생성
  private static void setInitData() {
    studentStore = new ArrayList<>();

    subjectStore = List.of(
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "Java",
            SUBJECT_TYPE_MANDATORY
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "객체지향",
            SUBJECT_TYPE_MANDATORY
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "Spring",
            SUBJECT_TYPE_MANDATORY
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "JPA",
            SUBJECT_TYPE_MANDATORY
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "MySQL",
            SUBJECT_TYPE_MANDATORY
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "디자인 패턴",
            SUBJECT_TYPE_CHOICE
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "Spring Security",
            SUBJECT_TYPE_CHOICE
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "Redis",
            SUBJECT_TYPE_CHOICE
        ),
        new Subject(
            sequence(INDEX_TYPE_SUBJECT),
            "MongoDB",
            SUBJECT_TYPE_CHOICE
        )
    );
    scoreStore = new ArrayList<>();
  }

  // index 자동 증가
  private static String sequence(String type) {
    switch (type) {
      case INDEX_TYPE_STUDENT -> {
        studentIndex++;
        return INDEX_TYPE_STUDENT + studentIndex;
      }
      case INDEX_TYPE_SUBJECT -> {
        subjectIndex++;
        return INDEX_TYPE_SUBJECT + subjectIndex;
      }
      default -> {
        scoreIndex++;
        return INDEX_TYPE_SCORE + scoreIndex;
      }
    }
  }

  private static void displayMainView() throws InterruptedException {
    boolean flag = true;
    while (flag) {
      System.out.println("\n==================================");
      System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
      System.out.println("1. 수강생 관리");
      System.out.println("2. 점수 관리");
      System.out.println("3. 프로그램 종료");
      System.out.print("관리 항목을 선택하세요...");
      int input = sc.nextInt();

      switch (input) {
        case 1 -> displayStudentView(); // 수강생 관리
        case 2 -> displayScoreView(); // 점수 관리
        case 3 -> flag = false; // 프로그램 종료
        default -> {
          System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
          Thread.sleep(2000);
        }
      }
    }
    System.out.println("프로그램을 종료합니다.");
  }

  private static void displayStudentView() {
    boolean flag = true;
    while (flag) {
      System.out.println("==================================");
      System.out.println("수강생 관리 실행 중...");
      System.out.println("1. 수강생 등록");
      System.out.println("2. 수강생 목록 조회");
      System.out.println("3. 메인 화면 이동");
      System.out.print("관리 항목을 선택하세요...");
      int input = sc.nextInt();

      switch (input) {
        case 1 -> createStudent(); // 수강생 등록
        case 2 -> inquireStudent(); // 수강생 목록 조회
        case 3 -> flag = false; // 메인 화면 이동
        default -> {
          System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
          flag = false;
        }
      }
    }
  }

  // 수강생 등록
  private static void createStudent() {
    String studentSubject;
    List<Subject> studentSubjects = new ArrayList<>();
    boolean validInput = true;
    sc.nextLine();
    System.out.println("\n수강생을 등록합니다...");
    System.out.print("수강생 이름 입력: ");
    String studentName = sc.nextLine();
    // 필수 과목 입력
    do {
      System.out.println("수강생의 필수과목 목록 중 3가지 선택해주세요. [1.Java 2.객체지향 3.Spring 4.JPA 5.MySQL]");
      studentSubject = sc.nextLine();
      validInput = true;
      // 입력값을 공백으로 분할하여 확인
      String[] subjects = studentSubject.split(" ");

      // 각 입력값이 1~5사이 값으로만 이루어져 있는지 확인
      for (String subject : subjects) {
        if (!subject.matches("[1-5]+")) {
          validInput = false;
          break;
        }
      }
      // 유효하지 않은 입력일 경우 오류 메시지 출력
      if (!validInput || studentSubject.split(" ").length < 3) {
        System.out.println("잘못된 선택입니다.");
      }
    } while (!validInput || studentSubject.split(" ").length < 3); //3개이상작성
    // 선택한 과목을 Subject 객체로 변환하여 리스트에 추가
    Arrays.asList(studentSubject.split(" ")).forEach(subject -> {
      int subjectNumber = Integer.parseInt(subject) - 1;
      studentSubjects.add(subjectStore.get(subjectNumber));
    });
    // 선택 과목 입력
    do {
      System.out.println(
          "수강생의 선택과목 목록 중 2가지 선택해주세요. [1.디자인_패턴 2.Spring_Security 3.Redis 4.MongoDB]");
      studentSubject = sc.nextLine();
      validInput = true;
      // 입력값을 공백으로 분할하여 확인
      String[] subjects = studentSubject.split(" ");

      // 각 입력값이 1~4사이 값으로만 이루어져 있는지 확인
      for (String subject : subjects) {
        if (!subject.matches("[1-4]+")) {
          validInput = false;
          break;
        }
      }
      // 유효하지 않은 입력일 경우 오류 메시지 출력
      if (!validInput || studentSubject.split(" ").length < 2) {
        System.out.println("잘못된 선택입니다.");
      }
    } while (!validInput || studentSubject.split(" ").length < 2); //2개이상 작성
    // 선택한 과목을 Subject 객체로 변환하여 리스트에 추가
    Arrays.asList(studentSubject.split(" ")).forEach(subject -> {
      int subjectNumber = Integer.parseInt(subject) + 4;
      studentSubjects.add(subjectStore.get(subjectNumber));
    });
    // 수강생 정보 출력
    System.out.printf("이름 : %-5s | 과목 : %-40s\n", studentName, studentSubjects.stream()
        .map(Subject::getSubjectName)
        .collect(Collectors.joining(", ")));

    // 수강생 등록 여부 확인
    System.out.println("수강생을 등록 하시겠습니까?");
    System.out.println("1. 네");
    System.out.println("2. 아니오");
    int input = sc.nextInt();

    if (input == 1) {
      Student student = new Student(studentName);
      student.setSubjectList(studentSubjects); // 수강생의 과목 리스트 설정
      studentStore.add(student);
      System.out.println("수강생 등록 성공!\n");
    } else {
      System.out.println("수강생 등록을 취소하셨습니다.\n");
    }
  }

  // 수강생 목록 조회
  private static void inquireStudent() {
    System.out.println("\n수강생 목록을 조회합니다...");
    for (Student student : studentStore) {
      String subjectList = "";
      for (int i = 0; i < student.getSubjectList().size(); i++) {
        subjectList += student.getSubjectList().get(i).getSubjectName();
        if (i < student.getSubjectList().size() - 1) {
          subjectList += ", ";
        }
      }
      System.out.println(
          "아이디 : " + student.getStudentId() + " | 이름 : " + student.getStudentName() + " | 과목 : "
              + subjectList);
    }
    System.out.println("\n수강생 목록 조회 성공!");
  }

  private static void displayScoreView() {
    boolean flag = true;
    while (flag) {
      System.out.println("==================================");
      System.out.println("점수 관리 실행 중...");
      System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
      System.out.println("2. 수강생의 과목별 회차 점수 수정");
      System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
      System.out.println("4. 메인 화면 이동");
      System.out.print("관리 항목을 선택하세요...");
      int input = sc.nextInt();

      switch (input) {
        case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
        case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
        case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
        case 4 -> flag = false; // 메인 화면 이동
        default -> {
          System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
          flag = false;
        }
      }
    }
  }

  private static String getStudentId() {
    System.out.print("\n관리할 수강생의 번호를 입력하시오...");
    String id = sc.next();
    if (studentStore.stream().noneMatch((Student s) -> s.getStudentId().equals(id))) {
      return null;
    }
    return id;
  }

  // 수강생의 과목별 시험 회차 및 점수 등록
  private static void createScore() {
    System.out.println("시험 점수를 등록합니다...");
    String studentId = getStudentId(); // 관리할 수강생 고유 번호
    if (studentId == null) {
      System.out.println("존재하지 않는 수강생입니다.");
      return;
    }
    sc.nextLine();

    Student student = studentStore.stream()
        .filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0); // 수강중인 과목 필터링
    List<Subject> enrolledSubject = student.getSubjectList();
    System.out.println("\n다음은 " + student.getStudentName() + " 학생의 수강 과목입니다.");
    System.out.printf("%-9s%-20s%n", "과목ID", "과목이름");
    System.out.println("----------------------------");
    enrolledSubject.forEach(subject -> {
      System.out.printf("%-10s%-20s%n", subject.getSubjectId(), subject.getSubjectName());
    });
    System.out.println();
    System.out.println("과목의 번호를 입력하시오");
    String subjectId = sc.nextLine();
    if (subjectStore.stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
      System.out.println("존재하지 않는 과목입니다.");
      return;
    }
    System.out.println("점수를 입력하시오");
    int score = sc.nextInt();
    if (score > 100 || score < 0) {
      System.out.println("1부터 100 까지의 점수를 입력하세요");
      return;
    }

    System.out.println("회차를 입력하시오");
    int round = sc.nextInt();
    if (round > 10 || round < 0) {
      System.out.println("1부터 10까지의 회차를 입력하세요");
      return;
    }

    Student resultStudent = studentStore.stream()
        .filter((Student s) -> s.getStudentId().equals(studentId)).toList()
        .get(0); // score객체에 저장할 student 객체 생성
    Subject resultSubject = subjectStore.stream()
        .filter((Subject s) -> s.getSubjectId().equals(subjectId)).toList()
        .get(0); // score객체에 저장할 subject 객체 생성
    if (scoreStore.stream().anyMatch((Score s) -> {
      return
          s.getStudent().getStudentId().equals(studentId) &&
              s.getSubject().getSubjectId().equals(subjectId) &&
              s.getRound() == round;
    }) // 이름, 과목, 회차 셋 모두 검사해서 중복여부 확인
    ) {
      System.out.println("중복된 회차가 있습니다.");
      return;
    }

    Score scoreObject = new Score(sequence(INDEX_TYPE_SCORE), resultStudent, resultSubject, round,
        score);
    scoreStore.add(scoreObject);

    System.out.println("\n점수 등록 성공!");
  }

  // 수강생의 과목별 회차 점수 수정
  private static void updateRoundScoreBySubject() {
    String studentId = getStudentId(); // 관리할 수강생 고유 번호
    // 기능 구현 (수정할 과목 및 회차, 점수)
    System.out.println("수정할 과목 선택해주세요.");
    // 과목 조회
    getSubjectsByStudent(studentId);
    String subjectId = sc.next();
    if (subjectStore.stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
      System.out.println("존재하지 않는 과목입니다.");
      return;
    }
    getRoundScores(studentId, subjectId);
    System.out.println();
    System.out.println("변경 할 회차 입력하시오.");
    int changeRound = sc.nextInt();
    if (scoreStore.stream().noneMatch(
        s -> s.getRound() == changeRound && s.getSubject().getSubjectId().equals(subjectId))) {
      System.out.println("회차가 존재하지 않습니다.");
      return;
    }
    System.out.println("변경 할 점수를 입력하시오");
    int changeScore = sc.nextInt();
    if (changeScore > 100 || changeScore < 0) {
      System.out.println("1부터 100 까지의 점수를 입력하세요");
      return;
    }
    System.out.println("시험 점수를 수정합니다...");
    // 기능 구현
    scoreStore.stream().peek(s -> {
      if (s.getStudent().getStudentId().equals(studentId) && s.getSubject().getSubjectId()
          .equals(subjectId) && s.getRound() == changeRound) {
        s.setScore(changeScore);
      }
    }).toList();
    System.out.println("\n점수 수정 성공!");
    getRoundScores(studentId, subjectId);
  }

  // 수강생의 특정 과목 회차별 등급 조회
  private static void inquireRoundGradeBySubject() {
    String studentId = getStudentId(); // 관리할 수강생 고유 번호
    // 기능 구현 (조회할 특정 과목)
    System.out.println("회차별 등급을 조회합니다...");
    // 기능 구현
    System.out.println("\n등급 조회 성공!");
  }

  private static void getSubjectsByStudent(String studentId) {
    Student student = studentStore.stream()
        .filter((Student s) -> s.getStudentId().equals(studentId)).findFirst().get();
    List<Subject> enrolledSubject = student.getSubjectList();
    System.out.println("\n다음은 " + student.getStudentName() + " 학생의 수강 과목입니다.");
    System.out.printf("%-9s%-20s%n", "과목ID", "과목이름");
    System.out.println("----------------------------");
    enrolledSubject.forEach(subject -> {
      System.out.printf("%-10s%-20s%n", subject.getSubjectId(), subject.getSubjectName());
    });
    System.out.println();
    System.out.println("과목의 번호를 입력하시오");
  }

  private static void getRoundScores(String studentId, String subjectId) {
    System.out.printf("%-9s%-20s%n", "회차", "점수");
    System.out.println("----------------------------");
    scoreStore.stream().filter(
        s -> s.getStudent().getStudentId().equals(studentId) && s.getSubject().getSubjectId()
            .equals(subjectId)).forEach(s -> {
      System.out.printf("%-10d%-20d%n", s.getRound(), s.getScore());
    });
  }
}
