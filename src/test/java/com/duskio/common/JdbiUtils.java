package com.duskio.common;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface JdbiUtils extends SqlObject {
    
    @SqlUpdate("delete from <table>")
    int truncateTable(@Define String table);
    
    @SqlQuery("select table_name from information_schema.tables where table_name not in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK') and table_schema = 'PUBLIC'")
    List<String> getAllTableNames();
    
    @SqlUpdate("alter table <table> modify <table>_id number generated always as identity (start with 1)")
    void resetIdentity(@Define String table);
    
    default void truncateTables(String... tables) {
        for (String table : tables) {
            truncateTable(table);
        }
    }

    default void truncateAllTables() {
        for (String table : getAllTableNames()) {
            table = table.toLowerCase();
            int result = truncateTable(table);
            if (result > 0) {
                LoggerFactory.getLogger(getClass()).debug("Deleted {} row(s) from table {}.", result, table);
            }
            resetIdentity(table);
        }
    }
}
