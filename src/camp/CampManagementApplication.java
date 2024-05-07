package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class CampManagementApplication {
    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static String SUBJECT_TYPE_CHOICE = "CHOICE";

//    private static Map<String,String> subjectMap = new HashMap<>();
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
//        for (Map.Entry<String, String> entry : subjectMap.entrySet()) {
//            System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue());
//        }
        try {
            displayMainView();

        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"Java");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"객체지향");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"Spring");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"JPA");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"MySQL");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"디자인 패턴");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"Spring Security");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"Redis");
//        subjectMap.put(sequence(INDEX_TYPE_SUBJECT),"MongoDB");

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
        List<String> studentsubjectList = new ArrayList<>();
        boolean validInput = true;
        sc.nextLine();
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.nextLine();
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
            if (!validInput ||  studentSubject.split(" ").length < 3) {
                System.out.println("잘못된 선택입니다.");
            }
        } while (!validInput || studentSubject.split(" ").length < 3);
        //3개 이상 작성
        Arrays.asList(studentSubject.split(" ")).forEach(subject -> {
            int subjectNumber = Integer.parseInt(subject)-1;
            studentsubjectList.add(subjectStore.get(subjectNumber).getSubjectName());
        });
        do {
            System.out.println("수강생의 선택과목 목록 중 2가지 선택해주세요. [1.디자인_패턴 2.Spring_Security 3.Redis 4.MongoDB]");
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
            if (!validInput ||  studentSubject.split(" ").length < 2) {
                System.out.println("잘못된 선택입니다.");
            }
        } while (!validInput || studentSubject.split(" ").length < 2);
        //2개 이상 작성
        Arrays.asList(studentSubject.split(" ")).forEach(subject -> {
            int subjectNumber = Integer.parseInt(subject)+4;
            studentsubjectList.add(subjectStore.get(subjectNumber).getSubjectName());
        });
        System.out.printf("이름 : %-5s | 과목 : %-40s\n", studentName, String.join(", ", studentsubjectList));
        System.out.println("수강생을 등록 하시겠습니까?");
        System.out.println("1. 네");
        System.out.println("2. 아니오");
        int input = sc.nextInt();

        if (input == 1) {
            Student student = new Student(studentName);
            for (String s : studentsubjectList) {
                student.getSubjectList().add(s);
            }
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
                System.out.println("아이디 : " + student.getStudentId() + " | 이름 : " + student.getStudentName() + " | 과목 : " + String.join(", ", student.getSubjectList()));
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
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현
        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }

}
