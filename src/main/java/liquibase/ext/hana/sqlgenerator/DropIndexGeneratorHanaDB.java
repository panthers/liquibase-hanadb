package liquibase.ext.hana.sqlgenerator;

import java.util.List;

import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropIndexGenerator;
import liquibase.statement.core.DropIndexStatement;
import liquibase.structure.core.Index;
import liquibase.util.StringUtils;

public class DropIndexGeneratorHanaDB extends DropIndexGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean supports(DropIndexStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }


    @Override
    public Sql[] generateSql(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List<String> associatedWith = StringUtils.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null) {
            if (associatedWith.contains(Index.MARK_PRIMARY_KEY)|| associatedWith.contains(Index.MARK_UNIQUE_CONSTRAINT) ||
                    associatedWith.contains(Index.MARK_FOREIGN_KEY)) {
                return new Sql[0];
            }
        }

        String schemaName = statement.getTableSchemaName();
        
        return new Sql[] {
                new UnparsedSql(
                        "DROP INDEX " + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()),
                        getAffectedIndex(statement)
                )
        };
    }
}
