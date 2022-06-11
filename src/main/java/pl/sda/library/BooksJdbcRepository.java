package pl.sda.library;

import lombok.RequiredArgsConstructor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class BooksJdbcRepository implements BooksRepository {

    private final Connection connection;

    @Override
    public Optional<BookBasicInfo> getBookBasicInfoById(int id) throws SQLException {
        var selectBookBasicInfo = """
                select id, title
                from books b
                where b.id = ?
                """;
        var preparedStatement = connection.prepareStatement(selectBookBasicInfo);
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();
        BookBasicInfo bookBasicInfo = null;
        int counter = 0;
        while (resultSet.next()) {
            if (counter > 1) {
                throw new IllegalStateException(String.format("Found more than one book with the same id: %s", id));
            }
            bookBasicInfo = BookBasicInfoRowMapper.getFrom(resultSet);
            counter++;
        }

        return Optional.ofNullable(bookBasicInfo);
    }

    @Override
    public List<BookBasicInfo> getAllBooks() throws SQLException {
        List<BookBasicInfo> allBooks = new ArrayList<>();
        var selectAllBooks = "select b.id, b.title from books b";
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(selectAllBooks);

        while (resultSet.next()) {
            var bookBasicInfo = BookBasicInfoRowMapper.getFrom(resultSet);
            allBooks.add(bookBasicInfo);
        }

        return allBooks;
    }

    @Override
    public void deleteBookById(int id) throws SQLException {
        deleteBookByIdFromTable("delete from books b where b.id = ?", id);
        deleteBookByIdFromTable("delete from rents r where r.book = ?", id);
    }

    private void deleteBookByIdFromTable(String deleteSql, int id) throws SQLException {
        var preparedStatementBooks = connection.prepareStatement(deleteSql);
        preparedStatementBooks.setInt(1, id);
        preparedStatementBooks.executeUpdate();
    }

    @Override
    public void updateBookTitle(String newBookTitle, int bookId) throws SQLException {
        var updateBookTitle = """
                update books b
                set b.title = ?
                where b.id = ?
                """;

        var preparedStatement = connection.prepareStatement(updateBookTitle);
        preparedStatement.setString(1, newBookTitle);
        preparedStatement.setInt(2, bookId);
        preparedStatement.executeUpdate();
    }

    @Override
    public void createBook(BookDetails bookDetails) throws SQLException {
        var insertBook = """
                insert into books (title, category, author, publisher, release_date)
                values (?, ?, ?, ?, ?)
                """;
        var preparedStatement = connection.prepareStatement(insertBook);
        preparedStatement.setString(1, bookDetails.getTitle());
        preparedStatement.setInt(2, bookDetails.getCategoryId());
        preparedStatement.setInt(3, bookDetails.getAuthorId());
        preparedStatement.setString(4, bookDetails.getPublisher());
        preparedStatement.setDate(5, bookDetails.getReleaseDate());
        preparedStatement.executeUpdate();
    }

    @Override
    public long getBooksCount() throws SQLException {
        var callableStatement = connection.prepareCall("{call get_number_of_books(?)}");
        callableStatement.registerOutParameter(1, Types.BIGINT);
        callableStatement.execute();
        return callableStatement.getLong(1);
    }

    @Override
    public void updateBook(BookDetails bookDetails) throws SQLException {
        var updateBookSql = """
                update books b
                set
                b.title = ?,
                b.category = ?,
                b.author = ?,
                b.publisher = ?,
                b.release_date = ?
                where b.id = ?
                """;

        var preparedStatement = connection.prepareStatement(updateBookSql);
        preparedStatement.setString(1, bookDetails.getTitle());
        preparedStatement.setInt(2, bookDetails.getCategoryId());
        preparedStatement.setInt(3, bookDetails.getAuthorId());
        preparedStatement.setString(4, bookDetails.getPublisher());
        preparedStatement.setDate(5, bookDetails.getReleaseDate());
        preparedStatement.setInt(6, bookDetails.getId());
        preparedStatement.executeUpdate();
    }
}
