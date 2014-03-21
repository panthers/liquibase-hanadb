package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropUniqueConstraintGenerator;
import liquibase.statement.core.DropUniqueConstraintStatement;

public class DropUniqueConstraintGeneratorHanaDB extends DropUniqueConstraintGenerator {

    @Override
    public boolean supports(DropUniqueConstraintStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public Sql[] generateSql(DropUniqueConstraintStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) +
                " DROP CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName());

        return new Sql[] {
                new UnparsedSql(sql, getAffectedUniqueConstraint(statement))
        };
    }
}
