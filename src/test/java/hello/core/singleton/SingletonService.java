package hello.core.singleton;

public class SingletonService {

    //1. static 영역에 객체를 1개만 생성
    private static final SingletonService instance = new SingletonService();


    //2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용
    public static SingletonService getInstance() {
        return instance; // 조회 할 때 getInstance를 사용
    }

    //3. 생성자를 private으로 선언해서 외부에서 new 사용 금지
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출출");
    }
}


