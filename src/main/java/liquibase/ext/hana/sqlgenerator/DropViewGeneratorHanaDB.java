package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropViewGenerator;
import liquibase.statement.core.DropViewStatement;


public class DropViewGeneratorHanaDB extends DropViewGenerator {

    @Override
    public boolean supports(DropViewStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public Sql[] generateSql(DropViewStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[] {
                new UnparsedSql(
                        "DROP VIEW " + database.escapeViewName(statement.getCatalogName(), statement.getSchemaName(), statement.getViewName()),
                        getAffectedView(statement)
                )
        };
    }

}
