### JVM 구조

* 클래스 로더 시스템
    * .class 에서 바이트코드를 읽고 메모리에 저장
    * 로딩: 클래스 읽어오는 과정
    * 링크: 레퍼런스를 연결하는 과정
    * 초기화: static 값들 초기화 및 변수에 할당
* 메모리
    * 메소드 영역에는 클래스 수준의 정보 (클래스 이름, 부모 클래스 이름, 메소드, 변수) 저장. 공유 자원이다.
        * static, instance 변수
    * 힙 영역에는 객체를 저장. 공유 자원이다.
        * 실제 인스턴스를 저장한다.
    * Stack, PC, Native Method Stack은 thread와 관련되어 있다.
    * 스택 영역에는 쓰레드 마다 런타임 스택을 만들고, 그 안에 메소드 호출을 스택 프레임(method call)이라 부르는 블럭으로 쌓는다. 쓰레드 종료하면 런타임 스택도 사라진다.
    * PC(Program Counter) 레지스터: 쓰레드 마다 쓰레드 내 현재 실행할 스택 프레임을 가리키는 포인터가 생성된
    * 네이티브 메소드 스택
        * https://javapapers.com/core-java/java-jvm-run-time-data-areas/#Program_Counter_PC_Register
* 실행 엔진
    * 인터프리터: 바이크 코드를 한줄 씩 실행.
    * JIT 컴파일러: 인터프리터 효율을 높이기 위해, 인터프리터가 반복되는 코드를 발견하면 JIT 컴파일러로 반복되는 코드를 모두 네이티브 코드로 바꿔둔다. 그 다음부터 인터프리터는 네이티브 코드로 컴파일된 코드를 바로 사용한다.
    * GC(Garbage Collector): 더이상 참조되지 않는 객체를 모아서 정리한다.
        * Throughput GC, Stop The World 를 줄이는 GC (성능이 더 좋다.)
* JNI(Java Native Interface)
    * 자바 애플리케이션에서 C, C++, 어셈블리로 작성된 함수를 사용할 수 있는 방법 제공
    * Native 키워드를 사용한 메소드 호출
    * https://medium.com/@bschlining/a-simple-java-native-interface-jni-example-in-java-andscala-68fdafe76f5f
* 네이티브 메소드 라이브러리
    * C, C++로 작성 된 라이브러리
* 클래스 로더
    * 로딩, 링크, 초기화 순으로 진행된다.
    * 로딩
        * 클래스 로더가 .class 파일을 읽고 그 내용에 따라 적절한 바이너리 데이터를 만들고 “메소드” 영역에 저장.
        * 이때 메소드 영역에 저장하는 데이터
            * FQCN
            * 클래스 | 인터페이스 | 이늄
            * 메소드와 변수
        * 로딩이 끝나면 해당 클래스 타입의 Class 객체를 생성하여 “힙" 영역에 저장.
    * 링크
        * Verify, Prepare, Reolve(optional) 세 단계로 나눠져 있다.
        * 검증: .class 파일 형식이 유효한지 체크한다.
        * Preparation: 클래스 변수(static 변수)와 기본값에 필요한 메모리
        * Resolve: 심볼릭 메모리 레퍼런스를 메소드 영역에 있는 실제 레퍼런스로 교체한다.
    * 초기화
        * Static 변수의 값을 할당한다. (static 블럭이 있다면 이때 실행된다.)
    * 클래스 로더는 계층 구조로 이뤄져 있으면 기본적으로 세가지 클래스 로더가 제공된다.
        * 부트 스트랩 클래스 로더 - JAVA_HOME\lib에 있는 코어 자바 API를 제공한다.
            * 최상위 우선순위를 가진 클래스 로더
        * 플랫폼 클래스로더 - JAVA_HOME\lib\ext 폴더 또는 java.ext.dirs 시스템 변수에 해당하는 위치에 있는 클래스를 읽는다.
        * 애플리케이션 클래스로더 - 애플리케이션 클래스패스(애플리케이션 실행할 때 주는 -classpath 옵션 또는 java.class.path 환경 변수의 값에 해당하는 위치)에서클래스를 읽는다.
* ByteCode 조작
    * 바이트코드 조작 라이브러리
        * ASM: https://asm.ow2.io/
        * Javassist: https://www.javassist.org/
        * ByteBuddy: https://bytebuddy.net/#/
    * Javaagent JAR 파일 만들기
        * https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html
        * 붙이는 방식은 시작시 붙이는 방식 premain과 런타임 중에 동적으로 붙이는 방식 agentmain이 있다.
        * Instrumentation을 사용한다.
    * Javaagent 붙여서 사용하기
        * 클래스로더가 클래스를 읽어올 때 javaagent를 거쳐서 변경된 바이트코드를 읽어들여 사용한다.
* 활용 예
    * 프로그램 분석
        * 코드에서 버그 찾는 툴
        * 코드 복잡도 계산
    * 클래스 파일 생성
        * 프록시
        * 특정 API 호출 접근 제한
        * 스칼라 같은 언어의 컴파일러
    `그밖에도 자바 소스 코드 건리지 않고 코드 변경이 필요한 여러 경우에 사용할 수 있다.`
        * 프로파일러 (newrelic)
        * 최적화
        * 로깅
    * 스프링이 컴포넌트 스캔을 하는 방법 (asm)
        * 컴포넌트 스캔으로 빈으로 등록할 후보 클래스 정보를 찾는데 사용
        * ClassPathScanningCandidateComponentProvider -> SimpleMetadataReader
        * ClassReader와 Visitor 사용해서 클래스에 있는 메타 정보를 읽어온다.
    * 참고
        * https://www.youtube.com/watch?v=39kdr1mNZ_s
        * ASM, Javassist, ByteBuddy, CGlib
### 리플렉션 
* 리플렉션 API 1부 : 클래스 정보 조회
* 리플렉션의 시작은 Class<T>
* Class<T>에 접근하는 방법
    * 모든 클래스를 로딩 한 다음 Class<T>의 인스턴스가 생긴다. "타입.class"로 접근할 수 있다.
    * 모든 인스턴스는 getClass()메소드를 가지고 있다. "인스턴스.getClass()"로 접근할 수 있다.
    * 클래스를 문자열로 읽어오는 방법
        * Class.forName("FQCN")
        * 클래스패스에 해당 클래스가 없다면 ClassNotFoundException 발생
* Class<T>를 통해 할 수 있는 것
    * 필드 (목록) 가져오기
    * 메소드 (목록) 가져오기
    * 상위 클래스 가져오기
    * 인터페이스 (목록) 가져오기
    * 애노테이션 가져오기
    * 생성자 가져오기
    * ...
* 애노테이션과 리플렉션
    * 중요 애노테이션
        * @Retention : 해당 애노테이션을 언제까지 유지할 것인가 ? 소스, 클래스, 런타임
        * @Inherit : 해당 애노테이션을 하위 클래스까지 전달할 것인가 ?
        * @Targe : 어디에 사용할 수 있는가 ?
    * 리플렉션
        * getAnnotations() : 상속받은 (@Inherit) 애노테이션까지 조회
        * getDeclaredAnnotations() : 자기 자신에만 붙어있는 애노테이션 조회 
    * Class 인스턴스 만들기
        * Class.newInstance()는 deprecated 됐으며 constructor를 사용하여 만들어야 한다.
    * 생성자로 인스턴스 만들기
        * Constructor.newInstance(params)
    * 필드 값 접근하기 / 설정하기
        * 특정 인스턴스가 가지고 있는 값을 가져오는 것이기 때문에 인스턴스가 필요하다.
        * Field.get(object)
        * Field.set(object, value)
        * Static 필드를 가져올 때는 object가 없어도 되니 null을 넘기면 된다.
    * 메소드 실행하기
        * Object.method.invoke(object, params)
    * 리플렉션 사용시 주의할 점
        * 지나친 사용은 성능 이슈를 야기할 수 있다. 반드시 필요한 경우에만 사용할 것
        * 컴파일 타임에 확인되지 않고 런타임 시에만 발생하는 문제를 만들 가능성이 있다.
        * 접근 지시자를 무시할 수 있다.
    * 스프링 
        * 의존성 주입
        * MVC 뷰에서 넘어온 데이터를 객체에 바인딩 할 때
    * 하이버네이트
        * @Entity 클래스에 Setter가 없다면 리플렉션을 사용한다.
* 다이나믹 프록시
    * 프록시 패턴
        * 프록시와 리얼 서브젝트가 공유하는 인터페이스가 있고, 클라이언트는 해당 인터페이스 타입으로 프록시를 사용한다.
        * 클라이언트는 프록시를 거쳐 리얼 서브젝트를 사용하기 때문에 프록시는 리얼 서브젝트에 대한 접근을 관리하거나 부가기능을 제공, 리턴값을 변경할 수 있다. 
        * 리얼 서브젝트는 자신이 해야할 일만 하면서(SRP) 프록시를 사용해 부가적인 기능(접근제한, 로깅, 트랜잭션)을 제공할 때 이런 패턴을 주로 사용한다.
    * 다이나믹 프록시
        * 런타임에 특정 인터페이스를 구현하는 클래스 또는 인스턴스를 만드는 기술
        * 프록시 인스턴스 만들기
            * ```java
              BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class}, new InvocationHandler(){});
              ```
            * 유연한 구조가 아니다.  Spring AOP가 다이나믹 프록시를 Spring에 사용할 수 있게 구현해둠 
