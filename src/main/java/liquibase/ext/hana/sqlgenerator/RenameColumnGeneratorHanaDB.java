package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.RenameColumnGenerator;
import liquibase.statement.core.RenameColumnStatement;

public class RenameColumnGeneratorHanaDB extends RenameColumnGenerator {

    @Override
    public boolean supports(RenameColumnStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public ValidationErrors validate(RenameColumnStatement renameColumnStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", renameColumnStatement.getTableName());
        validationErrors.checkRequiredField("oldColumnName", renameColumnStatement.getOldColumnName());
        validationErrors.checkRequiredField("newColumnName", renameColumnStatement.getNewColumnName());

        return validationErrors;
    }

    @Override
    public Sql[] generateSql(RenameColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql;

        sql = "RENAME COLUMN " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) +
                "." + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) +
                " TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName());

        return new Sql[] {
                new UnparsedSql(sql, getAffectedOldColumn(statement), getAffectedNewColumn(statement))
        };
    }
}
