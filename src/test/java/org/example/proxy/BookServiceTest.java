package org.example.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

public class BookServiceTest {

    /**
     * 어떤 프록시 타입을 만드는지, 인터페이스 목록, 메소드의 호출을 어떻게 처리할건지
     * 자바에서 인터페이스만 다이나믹 프록시로 만들 수 있다.
     * 클래스 기반 다이나믹 프록시는 생성 불가능
     */
    BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
            new InvocationHandler() {
                BookService bookService = new DefaultBookService();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 아래와 같이 사용하는 프록시는 유연하지 않다. Spring AOP가 다이나믹 프록시를 Spring에 사용할 수 있게 구현해둠
                    if (method.getName().equals("rent")) {
                        System.out.println("bbbb");
                        Object invoke = method.invoke(bookService, args);
                        System.out.println("aaaa");
                        return invoke;
                    }

                    return method.invoke(bookService, args);
                }
            });

    @Test
    public void dynamicProxy() {
        Book book = new Book("title");
        bookService.rent(book);

    }
}
