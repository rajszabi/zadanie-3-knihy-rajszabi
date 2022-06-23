package sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LendingListResponse {
    private Long id;
    private List<BookResponse> lendingList;
    private boolean lended;

    public LendingListResponse(LendingList shoppingCart) {
        this.id = shoppingCart.getId();
        this.lendingList = shoppingCart.getLendinglist().stream().map(BookResponse::new).collect(Collectors.toList());
        this.lended = shoppingCart.isLended();
    }
}
