# IoC , DI , 스프링 컨테이너

생성일: 2023년 3월 7일 오후 4:45

# 구성 영역과 사용 영역을 나누자

- 나누기 전에는 역할의 구현체에서 의존관계까지 신경썼다. → ( 배우가 같이 연기하는 역할의 배우를 선택하는 상황 : 연기에 집중할 수 없다. )
    - 연극 기획자에게 해당 책임을 넘기자

- 구성 영역 ( 객체 생성, 조립 )
- 사용 영역 ( 객체의 역할(기능)에 집중 )
- DIP 만족 , OCP 만족 , SRP 만족
- 사용영역에 클라이언트 코드는 변경되지 않는 것이 핵심

# 관심사 분리로 인해 객체지향 설계 원칙 보기

### OCP

다형성 사용, 클라이언트 DIP 지킴으로써 클라이언트 코드 변경 X, 새로운 기능으로 확장할 수 있게 됨

새로운 기능에 대한 것은 `외부`에서 의존관계를 변경해줌으로써 해결

### DIP

**프로그래머는 추상화의 의존해야지 구체화에 의존하면 안된다.**

DI(의존성 주입)은 DIP 원칙을 따르는 방법 

클라이언트 코드가 역할에만 의존, 구체화는 알지 못함 

### SRP

- 한 클래스의 하나의 책임
- 클라이언트가 구성 책임과 실행 책임 두가지를 갖고 있었는데, 구성 책임을 외부로 넘겼다.
- 실행 책임만 갖게 되었다.

# IoC , DI

### IoC

내가 사용할 객체 호출하는 것이 아닌 프레임워크가 호출해준다.

프레임워크가 객체 간의 관계를 제어해주는 것 

- 개발자 스스로가 제어하는 것이 자연스러운 흐름인데 이걸을 프레임워크가 제어하면서 **자연스러운 흐름이 역전**되었다고 해서 `제어의 역전`
- 사용영역 실행 코드만 봐서 제어 흐름을 알 수 없다.
- 제어의 영역은 구성영역이 담당

<aside>
💻 `프레임워크` vs `라이브러리` 

`프레임워크` : 개발자가 작성한 코드를 제어하고 대신 실행해준다. 
ex : JUnit ( @Test 만 붙이면 테스트를 알아서 실행해준다. 라이프사이클도 알아서 제어 )
`라이브러리` : 개발자가 객체를 직접 제어하고 사용

</aside>

### DI(의존관계 주입)

의존관계 

- 정적인 클래스 의존관계 → 클래스 다이어그램
    - 실행전 코드를 보고 분석 가능 (인텔리제이 안에 툴도 있다.)
    - 어떤 객체가 실행 시 주입될진 알 수 없음
- 동적인 객체 의존관계 → 객체 다이어그램
    - 실제 실행 시점에서 설정된 의존관계
        
        ⇒ 외부에서 구현객체 생성 후 클라이언트에 전달 ( 의존관계 주입 ) 
        
        정적인 클래스 의존관계 변경(기존 코드 변경) 없이 동적인 객체 의존관계를 변경할 수 있다.
        

# IoC 컨테이너 , DI 컨테이너

객체 생성, 관리, 의존관계 연결해주는 것을 IoC 혹은 DI 컨테이너라고 한다. 

- 현재는 의존관계 주입에 초점을 맞춰서 DI 컨테이너라 부르는 추세
- 다른 말로는 어셈블러(조립 시켜주는 역할자), 오브젝트 팩토리 라고도 불린다.

# 스프링에서 DI 를 구현하는 방법

- 구성역할 클래스에 @Configuration 어노테이션 등록
    
    → 설정 파일 의미
    
- 각 메서드에 @Bean 어노테이션 등록
    
    → 스프링 컨테이너에 해당 객체 등록 
    

- 스프링 컨테이너를 사용 시

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
// 애플리케이션 문맥 -> 스프링 컨테이너라고 보면 된다. ( 모든 객체들을 관리 )
// -> AnnotationConfigApplicationContext ( @Configuration 기반 스프링 컨테이너 -> 파라미터에 있는 환경 설정 정보를 가지고 스프링 컨테이너에다가 객체 생성 후 관리 )
MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
// applicationContext 에서 해당 객체를 가져온다. ( 이름은 @Bean 메서드 이름 , 타입 : 해당 클래스.class )
// 실행시 나오는 로그 Creating shared instance of singleton bean -> 해당 빈들이 스프링 컨테이너에 등록
```

- 애플리케이션 문맥 -> 스프링 컨테이너라고 보면 된다. (모든 객체들을 관리) *
- AnnotationConfigApplicationContext ( @Configuration 기반 스프링 컨테이너 )
- applicationContext 에서 해당 객체를 가져온다. ( 이름은 @Bean 메서드 이름 , 타입 : 해당 클래스.class )
- 실행시 나오는 로그 **Creating shared instance of singleton bean** : 해당 빈들이 스프링 컨테이너에 등록되었다는 내용

# 스프링 컨테이너와 스프링 빈

## 스프링 컨테이너 어떻게 생성되는가?

- ApplicationContext는 여러 구현체 클래스가 존재한다.
- 해당 구현체 클래스가 생성될 때 컨테이너 안에 빈 저장소가 생성
- 설정 정보를 보고 빈 저장소에 빈들을 등록한다.
- 빈 등록과 의존관계 주입 단계로 나눠서 설정 (자동 의존관계 주입 시)

## 스프링 빈 상속관계

- 부모 Bean이 조회시 자식 Bean 들은 다 조회
    
    ⇒ Object타입을 조회하면 모든 스프링 빈들이 조회된다.
    

## BeanFactory 와 ApplicationContext

- 둘다 스프링 컨테이너로 부를 수 있다.

- BeanFactory : 빈 생성, 관리를 담당하는 인터페이스 ( 스프링 컨테이너의 최상위 인터페이스 )
- ApplicationContext :BeanFactory를 상속 + 여러 가지 부가기능을 담당하는 인터페이스들도 상속
    - 빈 생성, 관리 뿐만아니라 + 부가 기능들까지 담당하는 스프링 빈
    - 사용자가 사용할 땐 ApplicationContext를 사용한다.

## 빈 생성 방법 XML , Java 코드

- XML 생성방법은 스프링 부트에선 사용X , 레거시 프로젝트를 진행할 때 필요 → 필요 시 스프링 레퍼런스 확인

## 스프링 빈 설정 메타 정보 - BeanDefinition

- 스프링 컨테이너에서 .class , .xml , .xxx 여러 설정정보를 등록할 수 있게 만들었다.
- BeanDefinition은 추상화되어있는 인터페이스
- 스프링 컨테이너 안에 각각 설정정보 타입에 따른 DefinitionReader로 해당 리소스들을 읽고 스프링 빈을 생성한다.

### BeanDefinition 정보

- BeanClassName : 생성할 빈 클래스명 ( 팩토리 역할 빈을 사용하면 없다 )
- factoryBeanName : 팩토리 빈 이름 ( ex : AppConfig )
- factoryMethodName : 빈을 생성할 팩토리 메서드
- Scope : 싱글톤 (기본값)
- lazyInit : 생성 지연 처리 여부 (스프링 컨테이너를 생성할 때 생성할지 , 실제 해당 빈을 사용할 때 생성할지)
- InitMethodName  : 빈 생성 , 의존관계 적용 후 호출되는 초기화 메서드명
- DestroyMethodName : 빈의 생명주기가 끝나고 제거하기 직전에 호출되는 메서드 명
    
    ⇒ InitMethodName, DestroyMethodName 는 빈 생명주기 때 다시 배움
    

# Q&A

- 질문 게시판에 질문들에 대한 답

### **ApplicationContext에 의존성 주입은 어떻게 가능?**

![ioc1.png](https://github.com/yeongsik/Spring-Study/blob/master/src/main/resources/image/ioc1.png)

- 댓글에 해당 질문에 답이 등록되어 있지 않아서(서포터즈에 답글이 있지만 내부 초기화가 복잡하다는 답변) chatGPT에서 물어봤습니다.
- 우선 의존성 주입은 스프링 컨테이너가 생성되고 의존관계가 설정된 후에야 사용하는 개념
- ApplicationContext의 생성자 파라미터에 어떤 파일형태가 들어가냐에 따라 BeanDefinitionReader 구현체가 결정된다.
- chatGPT 답변
    
    ![ioc2.png](https://github.com/yeongsik/Spring-Study/blob/master/src/main/resources/image/ioc2.png)
    
    ![ioc3.png](https://github.com/yeongsik/Spring-Study/blob/master/src/main/resources/image/ioc3.png)
    

# 정리

변경의 유연성을 위한 조건(좋은 객체지향 설계)들을 달성하기 위해, 객체 관리에 대한 부분은 외부에서 진행

스프링 컨테이너 , 스프링 빈은 해당 흐름을 이해하자 , 실제로 개발 시 사용할 일이 적을 것 같다.