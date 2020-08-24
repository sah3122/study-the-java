package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // inferface, class, enum
// Class 는 바이트 코드에서 사용한다는것, 애노테이션 프로세서는 소스 까지만 유지되면 왼다.
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {
}
