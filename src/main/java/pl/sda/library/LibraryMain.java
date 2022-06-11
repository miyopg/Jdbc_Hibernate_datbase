package pl.sda.library;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
class LibraryMain {

    public static void main(String[] args) {

  /*      try (var connection = ConnectionFactory.createH2Connection()) {
            log.info("Successfully connected to H2 DB");
        } catch (SQLException e) {
            log.error("Something went wrong", e);
        }
    }*/
        try (var connection = ConnectionFactory.createMySqlConnection()) {
            var booksRepository = new BooksJdbcRepository(connection);
            //TODO: tutaj wywołujemy to co chcemy przetestować, np:
            //testGetAllBooks(booksRepository);
            //testCreateBook(booksRepository)
            //itd...

            //testUpdateBook(booksRepository);
            //testGetBooksCount(booksRepository);
        } catch (SQLException e) {
            log.error("Something went wrong", e);
        }
    }

    private static void testCreateBook(BooksJdbcRepository booksRepository) throws SQLException {
        var newBook = BookDetails.builder()
                .title("Newly created book")
                .authorId(1)
                .categoryId(3)
                .publisher("Helion")
                .releaseDate(Date.valueOf(LocalDate.of(2022, 5, 29)))
                .build();
        booksRepository.createBook(newBook);
    }

    private static void testDeleteBook(BooksJdbcRepository booksRepository, int bookId) throws SQLException {
        booksRepository.deleteBookById(bookId);
    }

    private static void testGetAllBooks(BooksJdbcRepository booksRepository) throws SQLException {
        List<BookBasicInfo> allBooks = booksRepository.getAllBooks();
        allBooks.forEach(LibraryMain::logBookBasicInfo);
    }

    private static void testGetBookById(BooksJdbcRepository booksRepository, int requestedBookId) throws SQLException {
        var bookBasicInfoOpt = booksRepository.getBookBasicInfoById(requestedBookId);
        var bookBasicInfo = bookBasicInfoOpt.orElseThrow(
                () -> new IllegalStateException(String.format("Could not found book with id %s", requestedBookId)));
        logBookBasicInfo(bookBasicInfo);
    }

    private static void testUpdateBookTitle(BooksJdbcRepository booksRepository, int requestedBookId) throws SQLException {
        var newTitle = "Mistrz czystego kodu. Kodeks postępowania profesjonalnych programistów";
        booksRepository.updateBookTitle(newTitle, requestedBookId);
    }

    private static void logBookBasicInfo(BookBasicInfo bookBasicInfo) {
        log.info("Found book: id: {}, title: {}",
                bookBasicInfo.getId(),
                bookBasicInfo.getTitle());
    }

    private static void testUpdateBook(BooksJdbcRepository booksRepository) throws SQLException {
        //przykład do testu
        var bookDetails = BookDetails.builder()
                .id(1)
                .title("Nowy tutuł") //zmiana z Czysty Agile. Powrót do podstaw
                .categoryId(4) //zmiana z 3 na 4
                .authorId(2) //zmiana z 1 na 2
                .publisher("Nowe wydawnictwo") // zmiana z Helion
                .releaseDate(Date.valueOf(LocalDate.of(2020, 6, 24))) //zmiana dnia z 23 na 24
                .build();
        booksRepository.updateBook(bookDetails);
    }

    private static void testGetBooksCount(BooksRepository booksRepository) throws SQLException {
        long booksCount = booksRepository.getBooksCount();
        log.info("Number of all books: {}", booksCount);
    }
}
