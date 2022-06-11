package pl.sda.library;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory {

    static Connection createMySqlConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "userdemo", "UserDemo");
    }

    static Connection createH2Connection() throws SQLException {
        var jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:tes_db");
        jdbcDataSource.setUser("user");
        jdbcDataSource.setPassword("");
        return jdbcDataSource.getConnection();
    }
    //TODO: dodać metodę, która twotrzy połączenie z bazą H2 (użyć opcji z DataSource zamianst DriverManager)
}
