package sk.stuba.fei.uim.oop.assignment3.lendingList.data;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LendingList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<Book> lendinglist;

    private boolean lended;

    public LendingList() {
        this.lendinglist = new ArrayList<>();
        this.lended = false;
    }
}
