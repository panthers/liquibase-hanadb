package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.RenameTableGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.RenameTableStatement;

import org.junit.Test;


public class RenameTableGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<RenameTableStatement> {

    public RenameTableGeneratorHanaDBTest() throws Exception {
        this(new RenameTableGeneratorHanaDB());
    }

    protected RenameTableGeneratorHanaDBTest(SqlGenerator<RenameTableStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected RenameTableStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        RenameTableStatement renameTableStatement = new RenameTableStatement(null, null, "old_table_name", "new_table_name");
        return renameTableStatement;
    }


    @Test
    public void testRenameTableNoSchema() {
        Database database = new HanaDatabase();
        RenameTableStatement statement = new RenameTableStatement(null, null, "old_table_name", "new_table_name");

        assertEquals("RENAME TABLE \"old_table_name\" TO \"new_table_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testRenameTableWithSchema() {
        Database database = new HanaDatabase();
        RenameTableStatement statement = new RenameTableStatement(null, "schema_name", "old_table_name", "new_table_name");

        assertEquals("RENAME TABLE \"schema_name\".\"old_table_name\" TO \"new_table_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
