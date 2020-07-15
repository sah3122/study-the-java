package org.example.reflection;

import java.lang.reflect.*;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class<Book> bookClass = Book.class;
//        Constructor<Book> constructor = bookClass.getConstructor(null);
//        Book book = constructor.newInstance();
//        System.out.println(book);

        Constructor<Book> constructor = bookClass.getConstructor(String.class);
        Book myBook = constructor.newInstance("myBook");
        System.out.println(myBook);

        Field a = Book.class.getDeclaredField("A");
        System.out.println(a.get(null)); // static한 field라서 object파라미터에 null을 넘겨준다.

        a.set(null, "AAAA");
        System.out.println(a.get(null));

        Field b = Book.class.getDeclaredField("B");
        b.setAccessible(true);
        System.out.println(b.get(myBook)); // static field가 아니라 객체를 넘겨주어야 한다.
        b.set(myBook, "BBBBBBBBBB");

        Method c = Book.class.getDeclaredMethod("c");
        c.invoke(myBook); // 메소드 실행

        Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) c.invoke(myBook, 1, 2);
        System.out.println(invoke);
    }
}
