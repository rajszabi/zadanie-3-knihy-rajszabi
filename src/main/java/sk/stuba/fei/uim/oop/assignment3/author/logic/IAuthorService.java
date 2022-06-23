package sk.stuba.fei.uim.oop.assignment3.author.logic;

import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface IAuthorService {
    List<Author> getAll();

    Author create(AuthorRequest request);

    void addBookToAuthor(Book b) throws NotFoundException;

    Author getById(Long id) throws NotFoundException;

    Author update(Long id, AuthorRequest request) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
