package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropTableGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropTableStatement;

import org.junit.Test;


public class DropTableGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropTableStatement> {

    public DropTableGeneratorHanaDBTest() throws Exception {
        this(new DropTableGeneratorHanaDB());
    }

    protected DropTableGeneratorHanaDBTest(SqlGenerator<DropTableStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropTableStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropTableStatement dropTableStatement = new DropTableStatement(null, null, "table_name", false);
        return dropTableStatement;
    }


    @Test
    public void testDropTableNoSchema() {
        Database database = new HanaDatabase();
        DropTableStatement statement = new DropTableStatement(null, null, "table_name", false);

        assertEquals("DROP TABLE \"table_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testDropTableAndCascadeWithSchema() {
        Database database = new HanaDatabase();
        DropTableStatement statement = new DropTableStatement(null, "schema_name", "table_name", true);

        assertEquals("DROP TABLE \"schema_name\".\"table_name\" CASCADE",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
