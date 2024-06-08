package com.duskio.common;

import com.duskio.configuration.AutoRowMapperFactory;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.spring5.SpringConnectionFactory;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDaoTest extends BaseTest {

    private Jdbi jdbi;
    private final JdbiUtils jdbiUtils = getJdbi().onDemand(JdbiUtils.class);
    
    @BeforeAll
    public void baseDaoTestBeforeAll() {
        jdbi = getJdbi();
        updateWithLiquibase();
    }

    @AfterEach
    public void baseDaoTestAfterEach() {
        getJdbiUtils().truncateAllTables();
    }
    
    protected DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;MODE=oracle;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
    
    protected Jdbi getJdbi() {
        if (jdbi == null) {
            ConnectionFactory connectionFactory = new SpringConnectionFactory(getDataSource());
            jdbi = Jdbi.create(connectionFactory)
                       .setSqlLogger(new Slf4JSqlLogger(LoggerFactory.getLogger(getClass())))
                       .installPlugin(new SqlObjectPlugin())
                       .registerRowMapper(new AutoRowMapperFactory());
        }
        return jdbi;
    }
    
    protected JdbiUtils getJdbiUtils() {
        return jdbiUtils;
    }
    
    protected String getChangelog() {
        return "db/changelog/db.changelog-test.yaml";
    }
    
    protected void updateWithLiquibase() {
        ConnectionFactory connectionFactory = new SpringConnectionFactory(getDataSource());
        try (Connection connection = connectionFactory.openConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, getChangelog());
            updateCommand.execute();
        } catch (SQLException | DatabaseException | CommandExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
