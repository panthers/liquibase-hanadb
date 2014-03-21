package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.ValidationErrors;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetNullableGenerator;
import liquibase.statement.core.SetNullableStatement;

public class SetNullableGeneratorHanaDB extends SetNullableGenerator {

    @Override
    public boolean supports(SetNullableStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public ValidationErrors validate(SetNullableStatement setNullableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();

        validationErrors.checkRequiredField("tableName", setNullableStatement.getTableName());
        validationErrors.checkRequiredField("columnName", setNullableStatement.getColumnName());
        validationErrors.checkRequiredField("columnDataType", setNullableStatement.getColumnDataType());

        return validationErrors;
    }

    @Override
    public Sql[] generateSql(SetNullableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql;

        String nullableString;
        if (statement.isNullable()) {
//            nullableString = " DEFAULT NULL";
            nullableString = " NULL";
        } else {
            nullableString = " NOT NULL";
        }

        sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) +
                " ALTER (" + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) +
                " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType()).toDatabaseDataType(database) +
                nullableString + ")";

        return new Sql[] {
                new UnparsedSql(sql, getAffectedColumn(statement))
        };
    }

}
