package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingListRepository;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class LendingListService implements ILendingListService {

    @Autowired
    private LendingListRepository repository;

    @Autowired
    private IBookService bookService;

    @Override
    public LendingList create() {
        return this.repository.save(new LendingList());
    }

    @Override
    public LendingList getById(Long id) throws NotFoundException {
        LendingList lc = this.repository.findLendingListById(id);
        if (lc == null) {
            throw new NotFoundException();
        }
        return lc;
    }

    @Override
    public List<LendingList> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void deleteLendingListById(Long listId) throws NotFoundException {
        LendingList lc = this.getById(listId);
        if (lc.isLended()) {
            for (Book b: lc.getLendinglist()) {
                b.setLendCount(b.getLendCount()-1);
            }
        }
        this.repository.delete(lc);
    }

    @Override
    public LendingList addBookToList(Long listId, Long bookId) throws NotFoundException, IllegalOperationException {
        LendingList lc = this.getById(listId);
        Book b = this.bookService.getById(bookId);
        if (lc.isLended() || lc.getLendinglist().contains(b)) {
            throw new IllegalOperationException();
        }
        lc.getLendinglist().add(b);
        return this.repository.save(lc);
    }

    @Override
    public void deleteBookFromLendingList(Long listId, Long bookId) throws NotFoundException {
        LendingList lc = this.getById(listId);
        Book b = bookService.getById(bookId);
        lc.getLendinglist().remove(b);
    }

    @Override
    public void lend(Long listId) throws NotFoundException, IllegalOperationException {
        LendingList lc = this.getById(listId);
        if (lc.isLended()) {
            throw new IllegalOperationException();
        }
        for (Book b: lc.getLendinglist()) {
            b.setLendCount(b.getLendCount()+1);
        }
        lc.setLended(true);
        this.repository.save(lc);
    }

}
