package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropPrimaryKeyGenerator;
import liquibase.statement.core.DropPrimaryKeyStatement;


public class DropPrimaryKeyGeneratorHanaDB extends DropPrimaryKeyGenerator {

    @Override
    public boolean supports(DropPrimaryKeyStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public Sql[] generateSql(DropPrimaryKeyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql;

        sql = "ALTER TABLE " +
                database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) +
                " DROP PRIMARY KEY";

        return new Sql[] {
                new UnparsedSql(sql, getAffectedPrimaryKey(statement))
        };
    }
}
