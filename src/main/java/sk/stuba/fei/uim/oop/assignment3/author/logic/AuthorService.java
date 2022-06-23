package sk.stuba.fei.uim.oop.assignment3.author.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private IBookService bookService;

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author create(AuthorRequest request) {
        return this.repository.save(new Author(request));
    }

    @Override
    public void addBookToAuthor(Book b) throws NotFoundException {
        Author a = b.getAuthor();
        a.getBooks().add(b);
    }

    @Override
    public Author getById(Long id) throws NotFoundException {
        Author a = this.repository.findAuthorById(id);
        if (a == null) {
            throw new NotFoundException();
        }
        return a;
    }

    @Override
    public Author update(Long id, AuthorRequest request) throws NotFoundException {
        Author a = this.getById(id);
        if (request.getName() != null) {
            a.setName(request.getName());
        }
        if (request.getSurname() != null) {
            a.setSurname(request.getSurname());
        }
        return this.repository.save(a);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }
}
