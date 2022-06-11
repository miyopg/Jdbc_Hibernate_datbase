package pl.sda.library;

import java.sql.ResultSet;
import java.sql.SQLException;

class BookBasicInfoRowMapper {

    static BookBasicInfo getFrom(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        String bookTitle = resultSet.getString("title");
        return BookBasicInfo.builder()
                .id(bookId)
                .title(bookTitle)
                .build();
    }
}

