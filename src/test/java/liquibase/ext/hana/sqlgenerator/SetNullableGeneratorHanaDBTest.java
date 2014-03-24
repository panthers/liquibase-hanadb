package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.SetNullableGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.SetNullableStatement;

import org.junit.Test;


public class SetNullableGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<SetNullableStatement> {

    public SetNullableGeneratorHanaDBTest() throws Exception {
        this(new SetNullableGeneratorHanaDB());
    }

    protected SetNullableGeneratorHanaDBTest(SqlGenerator<SetNullableStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected SetNullableStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        SetNullableStatement setNullableStatement = new SetNullableStatement(null, null, "table_name", "column_name", "int unsigned", false);
        return setNullableStatement;
    }


    @Test
    public void testSetNullableNoSchema() {
        Database database = new HanaDatabase();
        SetNullableStatement statement = new SetNullableStatement(null, null, "table_name", "column_name", "int", false);

        assertEquals("ALTER TABLE \"table_name\" ALTER (\"column_name\" INTEGER NOT NULL)",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testSetNullableWithSchema() {
        Database database = new HanaDatabase();
        SetNullableStatement statement = new SetNullableStatement(null, "schema_name", "table_name", "column_name", "int", true);

        assertEquals("ALTER TABLE \"schema_name\".\"table_name\" ALTER (\"column_name\" INTEGER NULL)",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
