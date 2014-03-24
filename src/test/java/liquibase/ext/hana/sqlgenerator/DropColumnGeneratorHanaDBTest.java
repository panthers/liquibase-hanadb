package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropColumnGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropColumnStatement;

import org.junit.Test;


public class DropColumnGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropColumnStatement> {

    public DropColumnGeneratorHanaDBTest() throws Exception {
        this(new DropColumnGeneratorHanaDB());
    }

    protected DropColumnGeneratorHanaDBTest(SqlGenerator<DropColumnStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropColumnStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropColumnStatement dropColumnStatement = new DropColumnStatement(null, null, "table_name", "column_name");
        return dropColumnStatement;
    }


    @Test
    public void testSingleColumnDropNoSchema() {
        Database database = new HanaDatabase();
        DropColumnStatement statement = new DropColumnStatement(null, null, "table_name", "column_name");

        assertEquals("ALTER TABLE \"table_name\" DROP (\"column_name\")",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testSingleColumnDropWithSchema() {
        Database database = new HanaDatabase();
        DropColumnStatement statement = new DropColumnStatement(null, "schema_name", "table_name", "column_name");

        assertEquals("ALTER TABLE \"schema_name\".\"table_name\" DROP (\"column_name\")",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
