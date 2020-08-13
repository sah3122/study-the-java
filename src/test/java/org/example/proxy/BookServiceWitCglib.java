package org.example.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookServiceWitCglib {


    @Test
    public void dynamicProxy() {
        MethodInterceptor handler = new MethodInterceptor() {
            DefaultBookService defaultBookService = new DefaultBookService();
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return method.invoke(defaultBookService, objects);
            }
        };

        DefaultBookService bookService = (DefaultBookService) Enhancer.create(DefaultBookService.class, handler);
        Book book = new Book("title");
        bookService.rent(book);

    }
}
