package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropViewGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropViewStatement;

import org.junit.Test;


public class DropViewGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropViewStatement> {

    public DropViewGeneratorHanaDBTest() throws Exception {
        this(new DropViewGeneratorHanaDB());
    }

    protected DropViewGeneratorHanaDBTest(SqlGenerator<DropViewStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropViewStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropViewStatement dropViewStatement = new DropViewStatement(null, null, "view_name");
        return dropViewStatement;
    }


    @Test
    public void testDropViewNoSchema() {
        Database database = new HanaDatabase();
        DropViewStatement statement = new DropViewStatement(null, null, "view_name");

        assertEquals("DROP VIEW \"view_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testDropViewWithSchema() {
        Database database = new HanaDatabase();
        DropViewStatement statement = new DropViewStatement(null, "schema_name", "view_name");

        assertEquals("DROP VIEW \"schema_name\".\"view_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
