package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {

    private final DataSource dataSource;

    // Spring automatically injects your PostgreSQL connection pool here
    public DatabaseConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔄 Checking database connection string...");
        try (Connection connection = dataSource.getConnection()) {
            String dbName = connection.getMetaData().getDatabaseProductName();
            String dbVersion = connection.getMetaData().getDatabaseProductVersion();

            System.out.println("=================================================");
            System.out.println("🚀 DATABASE CONNECTED SUCCESSFULLY!");
            System.out.println("📦 Connected to: " + dbName + " (v" + dbVersion + ")");
            System.out.println("=================================================");
        } catch (Exception e) {
            System.err.println("❌ Database connection verification failed: " + e.getMessage());
        }
    }
}