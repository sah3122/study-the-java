package org.example.reflection;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<Book> bookClass = Book.class;

        Book book = new Book();
        Class<? extends Book> aClass = book.getClass();

        Class<?> aClass1 = Class.forName("org.example.reflection.Book");

        //getFields 는 public 만 가지고 온다.
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);

        //getDeclaredFields 는 모든 필드에 접근 가능하다.
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        Arrays.stream(bookClass.getDeclaredFields())
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        System.out.printf("%s %s", f, f.get(book)) ;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

        Arrays.stream(bookClass.getDeclaredFields())
                .forEach(f -> {
                    int modifiers = f.getModifiers();
                    System.out.println(f);
                    System.out.println(Modifier.isStatic(modifiers));
                });

        Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println); // MyBook에 선언된 애노테이션만 가지고 오는 메소드.


    }
}
