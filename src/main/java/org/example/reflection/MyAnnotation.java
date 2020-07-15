package org.example.reflection;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited // 상속이 가능한 애노테이션 선언
public @interface MyAnnotation {

    String value(); // 애노테이션을 생성할 때 name을 명시 하지 않아도 된다.

    String name() default "dongchul"; // 기본값 선언

    int number() default 100;
}
