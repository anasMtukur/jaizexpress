package com.skylabng.jaizexpress.payload;

import lombok.*;
import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagedPayload<T> {
    private List<T> items;
    private long totalPages;
    private long totalElements;
}
