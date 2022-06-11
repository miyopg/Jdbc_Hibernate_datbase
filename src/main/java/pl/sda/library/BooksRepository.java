package pl.sda.library;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

interface BooksRepository {

    Optional<BookBasicInfo> getBookBasicInfoById(int id) throws SQLException;

    List<BookBasicInfo> getAllBooks() throws SQLException;

    void deleteBookById(int id) throws SQLException;

    void updateBookTitle(String newBookTitle, int bookId) throws SQLException;

    void createBook(BookDetails bookDetails) throws SQLException;

    long getBooksCount() throws SQLException;

    void updateBook(BookDetails bookDetails) throws SQLException;
}
