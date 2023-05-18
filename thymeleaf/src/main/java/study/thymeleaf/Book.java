package study.thymeleaf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class Book implements Serializable {
    private String name;
    private Author author;
    private int price;
    private boolean sale;
    private double saleAmount;

    public Book(String authorName, String bookName, int price) {
        this.author = new Author(authorName);
        this.name = bookName;
        this.price = price;
    }
    public Book(String authorName, String bookName, int price, boolean sale, double saleAmount) {
        super();
        this.author = new Author(authorName);
        this.name = bookName;
        this.price = price;
        this.sale = sale;
        this.saleAmount = saleAmount;
    }
}