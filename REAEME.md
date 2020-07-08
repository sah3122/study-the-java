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
