package com.techelevator.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestingDatabaseConfig.class)
public abstract class BaseDaoTests {

    @Autowired
    protected DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() throws SQLException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        if (dataSource instanceof SingleConnectionDataSource) {
            ((SingleConnectionDataSource) dataSource).setAutoCommit(false);
        }
    }

    @AfterEach
    public void rollback() throws SQLException {
        if (dataSource instanceof SingleConnectionDataSource) {
            ((SingleConnectionDataSource) dataSource).getConnection().rollback();
        }
    }

    protected void executeSqlFile(String fileName) {
        String sql = getSqlFileContents(fileName);
        jdbcTemplate.execute(sql);
    }

    private String getSqlFileContents(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        try {
            if (!resource.exists()) {
                throw new RuntimeException("SQL file not found: " + fileName);
            }
            return new String(Files.readAllBytes(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read SQL script file: " + fileName, e);
        }
    }
}