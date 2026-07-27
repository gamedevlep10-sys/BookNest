package com.booknest.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Smart DataSource configuration
 * Attempts MySQL connection first. If MySQL service is not running locally,
 * automatically falls back to persistent file-based H2 database (./data/booknestdb)
 * so the web app stays online at http://localhost:8080 and user accounts are preserved.
 */
@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username:root}")
    private String username;

    @Value("${spring.datasource.password:password}")
    private String password;

    @Value("${spring.datasource.driver-class-name:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;

    @Bean
    @Primary
    public DataSource dataSource() {
        if (dbUrl != null && dbUrl.contains("mysql")) {
            try {
                Class.forName(driverClassName);
                DriverManager.setLoginTimeout(2);
                try (Connection conn = DriverManager.getConnection(dbUrl, username, password)) {
                    log.info("✓ Successfully connected to MySQL database at {}", dbUrl);
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl(dbUrl);
                    config.setUsername(username);
                    config.setPassword(password);
                    config.setDriverClassName(driverClassName);
                    config.setInitializationFailTimeout(3000);
                    return new HikariDataSource(config);
                }
            } catch (Exception e) {
                log.warn("⚠️ MySQL server at {} is not accessible ({})", dbUrl, e.getMessage());
                log.info("🔄 Falling back to persistent file database (./data/booknestdb). User accounts will persist across restarts.");
            }
        }

        return createH2DataSource();
    }

    private DataSource createH2DataSource() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            log.warn("Unable to ensure the data directory exists", e);
        }

        cleanupStaleH2Files();

        String[] candidateUrls = {
            "jdbc:h2:file:./data/booknestdb;DB_CLOSE_DELAY=-1;MODE=MySQL;FILE_LOCK=NO",
            "jdbc:h2:mem:booknest;DB_CLOSE_DELAY=-1;MODE=MySQL"
        };

        for (String jdbcUrl : candidateUrls) {
            try {
                HikariConfig h2Config = new HikariConfig();
                h2Config.setJdbcUrl(jdbcUrl);
                h2Config.setDriverClassName("org.h2.Driver");
                h2Config.setUsername("sa");
                h2Config.setPassword("");
                h2Config.setMaximumPoolSize(1);
                return new HikariDataSource(h2Config);
            } catch (Exception e) {
                log.warn("⚠️ H2 datasource initialization failed for {}: {}", jdbcUrl, e.getMessage());
                if (jdbcUrl.contains("file:")) {
                    cleanupStaleH2Files();
                }
            }
        }

        throw new IllegalStateException("Unable to initialize the fallback H2 datasource");
    }

    private void cleanupStaleH2Files() {
        try {
            Path dataDir = Paths.get("data");
            Files.createDirectories(dataDir);
            for (String fileName : new String[]{"booknestdb.lock.db", "booknestdb.trace.db"}) {
                Path file = dataDir.resolve(fileName);
                if (Files.exists(file)) {
                    Files.deleteIfExists(file);
                    log.info("Removed stale H2 file {}", file);
                }
            }
        } catch (IOException e) {
            log.warn("Unable to clean stale H2 files", e);
        }
    }
}
