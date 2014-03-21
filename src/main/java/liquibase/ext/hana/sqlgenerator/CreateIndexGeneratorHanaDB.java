package liquibase.ext.hana.sqlgenerator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.structure.core.Index;
import liquibase.util.StringUtils;


public class CreateIndexGeneratorHanaDB extends CreateIndexGenerator {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean supports(CreateIndexStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        // Default filter of index creation:
        // creation of all indexes with associations are switched off.
        List<String> associatedWith = StringUtils.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null && (associatedWith.contains(Index.MARK_PRIMARY_KEY) ||
            associatedWith.contains(Index.MARK_UNIQUE_CONSTRAINT) ||
            associatedWith.contains(Index.MARK_FOREIGN_KEY))) {
            return new Sql[0];
        }

	    StringBuilder buffer = new StringBuilder();

	    buffer.append("CREATE ");
	    if (statement.isUnique() != null && statement.isUnique()) {
		    buffer.append("UNIQUE ");
	    }
	    buffer.append("INDEX ");

	    if (statement.getIndexName() != null) {
            String indexSchema = statement.getTableSchemaName();
//            buffer.append(database.escapeIndexName(null, statement.getIndexName())).append(" ");
            buffer.append(database.escapeIndexName(statement.getTableCatalogName(), indexSchema, statement.getIndexName())).append(" ");
	    }
	    buffer.append("ON ");
	    buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append(" (");
	    Iterator<String> iterator = Arrays.asList(statement.getColumns()).iterator();
	    while (iterator.hasNext()) {
		    String column = iterator.next();
		    buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column));
		    if (iterator.hasNext()) {
			    buffer.append(", ");
		    }
	    }
	    buffer.append(")");

//	    return new Sql[]{new UnparsedSql(buffer.toString())};
        return new Sql[]{new UnparsedSql(buffer.toString(), getAffectedIndex(statement))};
    }

}
