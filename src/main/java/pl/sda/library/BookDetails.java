package pl.sda.library;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
class BookDetails {
    private int id;
    private String title;
    private int categoryId;
    private int authorId;
    private String publisher;
    private Date releaseDate;
}
