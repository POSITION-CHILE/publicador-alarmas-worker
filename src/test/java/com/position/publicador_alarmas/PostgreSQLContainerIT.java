package com.position.publicador_alarmas;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class PostgreSQLContainerIT {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("position")
            .withUsername("position")
            .withPassword("position");

    @Test
    void postgresContainerArrancaYExponeConexion() throws Exception {
        assertThat(POSTGRES.isRunning()).isTrue();
        assertThat(POSTGRES.getJdbcUrl()).contains("jdbc:postgresql://");
        assertThat(POSTGRES.getDatabaseName()).isEqualTo("position");

        try (Connection connection = DriverManager.getConnection(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select current_database()")) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getString(1)).isEqualTo("position");
        }
    }
}
