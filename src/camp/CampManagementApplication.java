package camp;

import camp.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class CampManagementApplication {

    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";

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
            System.out.println("3. 수강생 수정");
            System.out.println("4. 수강생 삭제");
            System.out.println("5. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> modifyStudent(); // 수강생 수정
                case 4 -> deleteStudent(); // 수강생 삭제
                case 5 -> flag = false; // 메인 화면 이동
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
        String studentStatus;
        do {
            System.out.print("수강생 상태 입력(GREEN, RED, YELLOW): ");
            studentStatus = sc.nextLine().toUpperCase();

            switch (studentStatus) {
                case "GREEN":
                case "RED":
                case "YELLOW":
                    validInput = true;
                    break;
                default:
                    validInput = false;
                    System.out.println("유효하지 않은 입력입니다. 다시 입력해주세요.");
                    break;
            }
        } while (!validInput);
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
        System.out.printf("이름 : %-5s | 과목 : %-40s | 상태 : %s\n",
                studentName,
                studentSubjects.stream()
                        .map(Subject::getSubjectName)
                        .collect(Collectors.joining(", ")),
                studentStatus);

        // 수강생 등록 여부 확인
        System.out.println("수강생을 등록 하시겠습니까?");
        System.out.println("1. 네");
        System.out.println("2. 아니오");
        int input = sc.nextInt();

        if (input == 1) {
            Student student = new Student(studentName, studentStatus);
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
        System.out.println("조회하실 방법을 선택해주세요. 1.전체조회 2.상태별조회");
        int input = sc.nextInt();
        StringBuilder subjectList = new StringBuilder();
        switch (input) {
            case 1:
                for (Student student : studentStore) {
                    subjectList = new StringBuilder();
                    for (int i = 0; i < student.getSubjectList().size(); i++) {
                        subjectList.append(student.getSubjectList().get(i).getSubjectName());
                        if (i < student.getSubjectList().size() - 1) {
                            subjectList.append(", ");
                        }
                    }
                    System.out.println( "아이디 : " + student.getStudentId() + " | 이름 : " + student.getStudentName() + " | 과목 : " + subjectList + " | 상태 : " + student.getStudentStatus() );
                }
                break;
            case 2:
                sc.nextLine();
                System.out.println("조회하실 상태를 입력하세요.(GREEN, RED, YELLOW)");
                String status = sc.nextLine().toUpperCase();
                studentStore.stream()
                        .filter(student -> student.getStudentStatus().equals(status))
                        .forEach(student -> {
                            List<String> subjectNames = student.getSubjectList().stream()
                                    .map(Subject::getSubjectName)
                                    .collect(Collectors.toList());
                            System.out.println("아이디 : " + student.getStudentId() + " | 이름 : " + student.getStudentName() + " | 과목 : " + String.join(", ", subjectNames) + " | 상태 : " + student.getStudentStatus());
                        });
                break;
            default:
                System.out.println("잘 못 입력하셨습니다.");
                return;
        }
        System.out.println("\n수강생 목록 조회 성공!");
    }
    private static void modifyStudent() {
        System.out.println("\n수강생 수정 화면입니다...");
        String studentId = getStudentId(); // 관리할 수강생 고유 번호

        // studentId를 가지는 학생 가져오기
        Student student = null;
        for (Student s : studentStore) {
            if (Objects.equals(studentId, s.getStudentId())) {
                student = s;
            }
        }
        // 존재하지 않는 studentId가 들어올 경우 처리
        if (student == null) {
            System.out.println("해당 ID를 가진 학생은 없습니다.");
            return;
        }
        studentStore.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .forEach(s -> {
                    List<String> subjectNames = s.getSubjectList().stream()
                            .map(Subject::getSubjectName)
                            .collect(Collectors.toList());
                    System.out.println("아이디 : " + s.getStudentId() + " | 이름 : " + s.getStudentName() + " | 과목 : " + String.join(", ", subjectNames) + " | 상태 : " + s.getStudentStatus());
                });//삭제할 수강생 출력
        System.out.println("수강생의 이름과 상태 중 변경하고 싶으신 것을 선택해주세요 1.이름 2.상태");
        int input = sc.nextInt();
        switch (input){
            case 1:
                System.out.print("수강생의 새로운 이름을 입력해주세요 : ");
                sc.nextLine();
                String name = sc.nextLine();
                for (Student studentList : studentStore) {
                    if (studentList.getStudentId().equals(studentId)) {
                        studentList.setStudentName(name);
                        System.out.println("수정되었습니다.");
                    }
                }
                break;
            case 2:
                System.out.print("수강생의 새로운 상태를 입력해주세요(GREEN,RED,YELLOW) : ");
                sc.nextLine();
                String status = sc.nextLine().toUpperCase();
                for (Student studentList : studentStore) {
                    if (studentList.getStudentId().equals(studentId)) {
                        studentList.setStudentStatus(status);
                        System.out.println("수정되었습니다.");
                    }
                }
                break;
            default:
                System.out.println("잘 못 선택하셨습니다.");
        }
    }
    public static void deleteStudent() {
        System.out.println("\n수강생 삭제화면입니다...");
        String studentId = getStudentId(); // 관리할 수강생 고유 번호

        // studentId를 가지는 학생 가져오기
        Student student = null;
        for (Student s : studentStore) {
            if (Objects.equals(studentId, s.getStudentId())) {
                student = s;
            }
        }
        // 존재하지 않는 studentId가 들어올 경우 처리
        if (student == null) {
            System.out.println("해당 ID를 가진 학생은 없습니다.");
            return;
        }
        studentStore.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .forEach(s -> {
                    List<String> subjectNames = s.getSubjectList().stream()
                            .map(Subject::getSubjectName)
                            .collect(Collectors.toList());
                    System.out.println("아이디 : " + s.getStudentId() + " | 이름 : " + s.getStudentName() + " | 과목 : " + String.join(", ", subjectNames) + " | 상태 : " + s.getStudentStatus());
                });//삭제할 수강생 출력
        System.out.println("수강생을 삭제 하시겠습니까?");
        System.out.println("1. 네");
        System.out.println("2. 아니오");
        int input = sc.nextInt();
        if(input == 1) {
            for (Student s : studentStore)
                if (s.getStudentId().equals(studentId)) {
                    studentStore.remove(s);
                    scoreStore.removeIf(score -> score.getStudent().equals(s.getStudentId()));
                    System.out.println("수강생이 삭제되었습니다.");
                    break;
                }
        }else{
            System.out.println("수강생 삭제를 취소하셨습니다.");
        }
    }

    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 수강생의 과목별 평균 등급을 조회");
            System.out.println("5. 특정 상태 수강생들의 필수 과목 평균 등급을 조회");
            System.out.println("6. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> CheckAverageGradeBySubject(); // 과목별 평균 등급 조회
                case 5 -> inquireAverageGradeSpecificStatusStudent(); // 특정 상태 수강생들의 필수 과목 평균 등급 조회
                case 6 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        String id = sc.next().toUpperCase();
        if (studentStore.stream().noneMatch((Student s) -> s.getStudentId().equals(id))) {
            return null;
        }
        return id;
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호

        // studentId를 가지는 학생 가져오기
        Student student = null;
        for (Student s : studentStore) {
            if (Objects.equals(studentId, s.getStudentId())) {
                student = s;
            }
        }

        // 존재하지 않는 studentId가 들어올 경우 처리
        if (student == null) {
            System.out.println("해당 ID를 가진 학생은 없습니다.");
            return;
        }

        // 해당 학생이 듣는 과목 보여주기
        List<Subject> studentSubjects = student.getSubjectList();
        System.out.printf("%-9s%-20s%n", "과목ID", "과목이름");
        System.out.println("----------------------------");
        studentSubjects.forEach(subject -> {
            System.out.printf("%-10s%-20s%n", subject.getSubjectId(), subject.getSubjectName());
        });
        System.out.println();

        System.out.print("조회할 과목의 ID 값을 입력해주세요: ");
        String subjectId = sc.next().toUpperCase();

        // 잘못된 과목 ID가 들어왔을 경우 메세지 출력 후 종료
        boolean isExist = false;
        for (Subject s : studentSubjects) {
            if (Objects.equals(s.getSubjectId(), subjectId)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            System.out.println("해당 하는 과목 ID가 없습니다.");
            return;
        }

        System.out.println(subjectId + " 회차별 등급을 조회합니다...");

        List<Score> resultScore = new ArrayList<>();
        // 기능 구현
        System.out.printf("%-9s%-20s%n", "회차", "등급");
        System.out.println("----------------------------");
        for (Score s : scoreStore) {
            if (Objects.equals(student.getStudentId(), s.getStudent().getStudentId()) && Objects.equals(subjectId, s.getSubject().getSubjectId())) {
                resultScore.add(s);
            }
        }

        // 등록되어 있는 점수가 없을 경우 메세지 출력 후 종료
        if (resultScore.size() == 0) {
            System.out.println("등록되어 있는 점수가 없습니다.");
            return;
        }

        // 회차별로 정렬해서 출력
        List<Score> sortScore = resultScore.stream()
                .sorted(Comparator.comparing(Score::getRound)).collect(Collectors.toList());
        for (Score s : sortScore) {
            System.out.printf("%-10s%-20s%n", s.getRound(), getGrade(s));
        }

        System.out.println("\n등급 조회 성공!");
    }

    /**
     * Score 객체를 매개변수로 넘기면 등급을 반환해주는 함수
     *
     * @param score: Score 객체
     * @return 등급
     */
    public static char getGrade(Score score) {
        String subjectType = score.getSubject().getSubjectType();
        int scoreValue = score.getScore();
        if (Objects.equals(subjectType, SUBJECT_TYPE_MANDATORY)) {
            if (scoreValue >= 95) return 'A';
            else if (scoreValue >= 90) return 'B';
            else if (scoreValue >= 80) return 'C';
            else if (scoreValue >= 70) return 'D';
            else if (scoreValue >= 60) return 'F';
        } else if (Objects.equals(subjectType, SUBJECT_TYPE_CHOICE)) {
            if (scoreValue >= 90) return 'A';
            else if (scoreValue >= 80) return 'B';
            else if (scoreValue >= 70) return 'C';
            else if (scoreValue >= 60) return 'D';
            else if (scoreValue >= 50) return 'F';
        }
        return 'N';
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
        String subjectId = sc.nextLine().toUpperCase();
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
        String subjectId = sc.next().toUpperCase();
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
    /**
     * 특정 상태 수강생들의 필수 과목 평균 등급을 조회하는 메서드
     */
    private static void inquireAverageGradeSpecificStatusStudent() {
        System.out.print("상태를 입력해주세요(GREEN, RED, YELLOW): ");
        String status = sc.next().toUpperCase();

        // 입력 들어온 상태와 같은 상태만 모아서 리스트를 가져오고
        // studentId로 정렬한다.
        List<Score> statusScore = scoreStore.stream()
                .filter(score -> Objects.equals(score.getStudent().getStudentStatus(), status))
                .filter(score -> Objects.equals(score.getSubject().getSubjectType(), SUBJECT_TYPE_MANDATORY))
                .sorted((s1, s2) -> s1.getStudent().getStudentId().compareTo(s2.getStudent().getStudentId()))
                .toList();

        // status 상태의 Student 객체가 존재하지 않는다면 메세지 호출 후 종료
        // 또는 해당 학생의 점수가 등록되어 있지 않을 경우
        if (statusScore.size() == 0) {
            System.out.println(status + " 상태의 학생이 존재하지 않거나 해당 학생의 등록되어 있는 필수 과목 점수가 없습니다.");
            return;
        }

        System.out.println(status + " 상태 학생의 평균등급을 조회합니다...");
        System.out.printf("%-9s%-20s%n", "이름", "평균등급");
        System.out.println("----------------------------");
        Student student = statusScore.getFirst().getStudent(); // statusScore의 첫번째 Student 객체를 가져온다.
        double sum = 0, count = 0;
        for (Score s : statusScore) {
            if (Objects.equals(s.getStudent(), student)) {
                sum += s.getScore();
                ++count;
            } else {
                System.out.printf("%-10s%-20s%n", student.getStudentName(), getAverageGrade(sum, count));
                student = s.getStudent();
                sum = 0;
                count = 0;
                if (Objects.equals(s.getScoreId(), statusScore.getLast().getScoreId())) {
                    sum += s.getScore();
                    ++count;
                }
            }
        }
        System.out.printf("%-10s%-20s%n", student.getStudentName(), getAverageGrade(sum, count));
    }

    /**
     * 합과 수를 넘기면 평균 등급을 반환해주는 메서드
     * @param sum : 점수의 합
     * @param count : 개수
     * @return : 평균 등급
     */
    private static char getAverageGrade(double sum, double count) {
        double gradeAvg = sum / count;
        char grade = 'N';
        if (gradeAvg >= 95) grade = 'A';
        else if(gradeAvg >= 90) grade = 'B';
        else if(gradeAvg >= 80) grade = 'C';
        else if(gradeAvg >= 70) grade = 'D';
        else if(gradeAvg >= 60) grade = 'F';
        return grade;
    }

    private static void CheckAverageGradeBySubject() {
        System.out.printf("%-8s%-10s%5s%n", "평균 등급", "평균 점수", "과목");
        Subject subject = new Subject();
        for (int i = 0; i < subjectStore.size(); i++) {
            double averageScore = GetSubjectAverageScore(i);
            subject.setSubjectType(subjectStore.get(i).getSubjectType());
            System.out.printf("%5s%5s%8.2f%8s%-20s%n", getGrade(new Score(subject, (int) averageScore)),
                " ", averageScore, " ", subjectStore.get(i).getSubjectName());
        }
    }

    private static double GetSubjectAverageScore(int idx) {
        return scoreStore.stream()
            .filter(s -> s.getSubject().getSubjectName().equals(subjectStore.get(idx).getSubjectName()))
            .mapToDouble(Score::getScore).average().orElse(0);
    }
}
