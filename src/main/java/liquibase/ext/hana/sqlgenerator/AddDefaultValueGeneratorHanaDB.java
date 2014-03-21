package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddDefaultValueGenerator;
import liquibase.statement.core.AddDefaultValueStatement;


public class AddDefaultValueGeneratorHanaDB extends AddDefaultValueGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean supports(AddDefaultValueStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public Sql[] generateSql(AddDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Object defaultValue = statement.getDefaultValue();
        return new Sql[]{
                new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) +
                        " ALTER (" + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) +
                        " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType()).toDatabaseDataType(database) +
                        " DEFAULT " + DataTypeFactory.getInstance().fromObject(defaultValue, database).objectToSql(defaultValue, database) +
                        ")",
                        getAffectedColumn(statement))
        };
    }

}
