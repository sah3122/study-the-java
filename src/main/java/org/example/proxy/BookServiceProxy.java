package org.example.proxy;

public class BookServiceProxy implements BookService {
    BookService bookService;

    public BookServiceProxy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void rent(String title) {
        System.out.println("aaa");
        bookService.rent(title);
        System.out.println("bbb");
    }
}
