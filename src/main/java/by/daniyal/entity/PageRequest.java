package by.daniyal.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(staticName = "of")
@ToString
@Getter
public class PageRequest {
    private final int page;
    private final int size;
    private final int totalElements;

    public int getPreviousPage() {
        return (page > 0) ? page - 1 : 0;
    }

    public int getNextPage() {
        return page + 1;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public boolean hasNext() {
        return page < getTotalPages() - 1;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalElements / size);
    }
}