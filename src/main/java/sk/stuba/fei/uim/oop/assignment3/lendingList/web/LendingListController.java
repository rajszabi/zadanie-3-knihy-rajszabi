package sk.stuba.fei.uim.oop.assignment3.lendingList.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.logic.ILendingListService;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingBook;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListResponse;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class LendingListController {

    @Autowired
    private ILendingListService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> addShoppingCart() {
        return new ResponseEntity<>(new LendingListResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LendingListResponse getListById(@PathVariable("id") Long listId) throws NotFoundException {
        return new LendingListResponse(this.service.getById(listId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LendingListResponse> getAllLists() {
        return this.service.getAll().stream().map(LendingListResponse::new).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    public void deleteListById(@PathVariable("id") Long listId) throws NotFoundException {
        this.service.deleteLendingListById(listId);
    }

    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LendingListResponse addBookToList(@PathVariable("id") Long listId, @RequestBody LendingBook body) throws NotFoundException, IllegalOperationException {
        return new LendingListResponse(this.service.addBookToList(listId, body.getId()));
    }

    @DeleteMapping(value = "/{id}/remove")
    public void deleteBookFromLendingList(@PathVariable("id") Long listId, @RequestBody LendingBook body) throws NotFoundException {
        this.service.deleteBookFromLendingList(listId, body.getId());
    }

    @GetMapping(value = "/{id}/lend")
    public void lendLendingCart(@PathVariable("id") Long listId) throws NotFoundException, IllegalOperationException {
        this.service.lend(listId);
    }
}
