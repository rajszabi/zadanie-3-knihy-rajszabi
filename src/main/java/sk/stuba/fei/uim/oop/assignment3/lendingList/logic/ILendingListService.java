package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface ILendingListService {
    LendingList create();

    LendingList getById(Long id) throws NotFoundException;

    List<LendingList> getAll();

    void deleteLendingListById(Long listId) throws NotFoundException;

    LendingList addBookToList(Long listId, Long bookId) throws NotFoundException, IllegalOperationException;

    void deleteBookFromLendingList(Long listId, Long id) throws NotFoundException;

    void lend(Long listId) throws NotFoundException, IllegalOperationException;
}
