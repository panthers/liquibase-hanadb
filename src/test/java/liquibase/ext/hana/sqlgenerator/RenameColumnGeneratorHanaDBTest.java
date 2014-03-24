package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.RenameColumnGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.RenameColumnStatement;

import org.junit.Test;


public class RenameColumnGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<RenameColumnStatement> {

    public RenameColumnGeneratorHanaDBTest() throws Exception {
        this(new RenameColumnGeneratorHanaDB());
    }

    protected RenameColumnGeneratorHanaDBTest(SqlGenerator<RenameColumnStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected RenameColumnStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        RenameColumnStatement renameColumnStatement = new
                RenameColumnStatement(null, null, "table_name", "old_column_name", "new_column_name", null);
        return renameColumnStatement;
    }


    @Test
    public void testRenameColumnNoSchema() {
        Database database = new HanaDatabase();
        RenameColumnStatement statement = new
                RenameColumnStatement(null, null, "table_name", "old_column_name", "new_column_name", null);

        assertEquals("RENAME COLUMN \"table_name\".\"old_column_name\" TO \"new_column_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testRenameColumnWithSchema() {
        Database database = new HanaDatabase();
        RenameColumnStatement statement = new
                RenameColumnStatement(null, "schema_name", "table_name", "old_column_name", "new_column_name", "int(11) unsigned");

        assertEquals("RENAME COLUMN \"schema_name\".\"table_name\".\"old_column_name\" TO \"new_column_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
