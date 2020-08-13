package org.example.proxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class BookServiceWithByteBuddy {


    @Test
    public void dynamicProxy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends DefaultBookService> proxyClass = new ByteBuddy().subclass(DefaultBookService.class)
                .method(named("rent"))
                .intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    DefaultBookService defaultBookService = new DefaultBookService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("aaa");
                        Object invoke = method.invoke(defaultBookService, args);
                        System.out.println("bbb");
                        return invoke;
                    }
                }))
                .make()
                .load(DefaultBookService.class.getClassLoader())
                .getLoaded();
        DefaultBookService bookService = proxyClass.getConstructor(null).newInstance();
        Book book = new Book("title");
        bookService.rent(book);

    }
}
