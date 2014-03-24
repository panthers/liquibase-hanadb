package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropPrimaryKeyGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropPrimaryKeyStatement;

import org.junit.Test;


public class DropPrimaryKeyGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropPrimaryKeyStatement> {

    public DropPrimaryKeyGeneratorHanaDBTest() throws Exception {
        this(new DropPrimaryKeyGeneratorHanaDB());
    }

    protected DropPrimaryKeyGeneratorHanaDBTest(SqlGenerator<DropPrimaryKeyStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropPrimaryKeyStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropPrimaryKeyStatement dropPrimaryKeyStatement = new DropPrimaryKeyStatement(null, null, "table_name", null);
        return dropPrimaryKeyStatement;
    }


    @Test
    public void testDropPrimaryKeyNoSchema() {
        Database database = new HanaDatabase();
        DropPrimaryKeyStatement statement = new DropPrimaryKeyStatement(null, null, "table_name", null);

        assertEquals("ALTER TABLE \"table_name\" DROP PRIMARY KEY",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testDropPrimaryKeyWithSchema() {
        Database database = new HanaDatabase();
        DropPrimaryKeyStatement statement = new DropPrimaryKeyStatement(null, "schema_name", "table_name", null);

        assertEquals("ALTER TABLE \"schema_name\".\"table_name\" DROP PRIMARY KEY",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
