package sk.stuba.fei.uim.oop.assignment3.book.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepository repository;
    @Autowired
    private IAuthorService authorService;

    @Override
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Book create(BookRequest request) throws NotFoundException {
        Book b = new Book(request);
        if (request.getAuthor() != null) {
            b.setAuthor(authorService.getById(request.getAuthor()));
            this.authorService.addBookToAuthor(b);
        }
        return this.repository.save(b);
    }

    @Override
    public Book getById(Long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return b;
    }

    @Override
    public Book update(Long id, BookUpdateRequest request) throws NotFoundException {
        Book b = this.getById(id);
        if (request.getName() != null) {
            b.setName(request.getName());
        }
        if (request.getDescription() != null) {
            b.setDescription(request.getDescription());
        }
        if (request.getAuthor() != null && request.getAuthor() != 0) {
            b.setAuthor(this.authorService.getById(request.getAuthor()));
        }
        if (request.getPages() != null && request.getPages() != 0) {
            b.setPages(request.getPages());
        }
        return this.repository.save(b);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Book b = this.getById(id);
        b.getAuthor().getBooks().remove(b);
        this.repository.delete(b);
    }

    @Override
    public long getAmount(Long id) throws NotFoundException {
        return this.getById(id).getAmount();
    }

    @Override
    public long addAmount(Long id, long increment) throws NotFoundException {
        Book b = this.getById(id);
        b.setAmount(b.getAmount() + increment);
        this.repository.save(b);
        return b.getAmount();
    }

    @Override
    public long getLendCount(Long id) throws NotFoundException {
        return this.getById(id).getLendCount();
    }
}
