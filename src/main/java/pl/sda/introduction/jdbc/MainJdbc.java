package pl.sda.introduction.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static pl.sda.introduction.jdbc.SQLs.DELETE_MOVIE_BY_ID;
import static pl.sda.introduction.jdbc.SQLs.GET_MOVIE_BY_ID;

@Slf4j
class MainJdbc {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainJdbc.class);
    public static void main(String[] args) {

        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","userdemo","UserDemo");
            var statement = connection.createStatement();
            var preparedStatement = connection.prepareStatement(GET_MOVIE_BY_ID)) {
            log.info("Successfully connected to database");

            String title = "Avengers";
            preparedStatement.setString(1, title);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int movieID = resultSet.getInt("id");
                String movieTitle = resultSet.getString("title");
                log.info("Movie id: {}, movie title: {}", movieID, movieTitle);
            }

        } catch (SQLException e) {
            log.error("Something went wrong", e);


        }

        }



    }

