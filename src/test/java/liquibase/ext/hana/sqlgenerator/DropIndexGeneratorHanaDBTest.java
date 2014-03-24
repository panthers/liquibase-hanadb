package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropIndexGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropIndexStatement;

import org.junit.Test;


public class DropIndexGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropIndexStatement> {

    public DropIndexGeneratorHanaDBTest() throws Exception {
        this(new DropIndexGeneratorHanaDB());
    }

    protected DropIndexGeneratorHanaDBTest(SqlGenerator<DropIndexStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropIndexStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropIndexStatement dropIndexStatement = new DropIndexStatement("index_name", null, null, "table_name", null);
        return dropIndexStatement;
    }


    @Test
    public void testDropIndexNoSchema() {
        Database database = new HanaDatabase();
        DropIndexStatement statement = new DropIndexStatement("index_name", null, null, "table_name", null);

        assertEquals("DROP INDEX \"index_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testDropIndexWithSchema() {
        Database database = new HanaDatabase();
        DropIndexStatement statement = new DropIndexStatement("index_name", null, "schema_name", "table_name", null);

        assertEquals("DROP INDEX \"schema_name\".\"index_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
