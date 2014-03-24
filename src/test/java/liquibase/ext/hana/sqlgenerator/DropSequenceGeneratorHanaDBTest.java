package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropSequenceGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropSequenceStatement;

import org.junit.Test;


public class DropSequenceGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropSequenceStatement> {

    public DropSequenceGeneratorHanaDBTest() throws Exception {
        this(new DropSequenceGeneratorHanaDB());
    }

    protected DropSequenceGeneratorHanaDBTest(SqlGenerator<DropSequenceStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropSequenceStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropSequenceStatement dropSequenceStatement = new DropSequenceStatement(null, null, "sequence_name");
        return dropSequenceStatement;
    }


    @Test
    public void testDropSequenceNoSchema() {
        Database database = new HanaDatabase();
        DropSequenceStatement statement = new DropSequenceStatement(null, null, "sequence_name");

        assertEquals("DROP SEQUENCE \"sequence_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testDropSequenceWithSchema() {
        Database database = new HanaDatabase();
        DropSequenceStatement statement = new DropSequenceStatement(null, "schema_name", "sequence_name");

        assertEquals("DROP SEQUENCE \"schema_name\".\"sequence_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
