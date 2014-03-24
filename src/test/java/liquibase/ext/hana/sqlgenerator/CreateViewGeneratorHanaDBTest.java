package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.CreateViewGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.CreateViewStatement;

import org.junit.Test;


public class CreateViewGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<CreateViewStatement> {

    public CreateViewGeneratorHanaDBTest() throws Exception {
        this(new CreateViewGeneratorHanaDB());
    }

    protected CreateViewGeneratorHanaDBTest(SqlGenerator<CreateViewStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

    @Override
    protected CreateViewStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        CreateViewStatement createViewStatement = new CreateViewStatement(null, null, "view_name", "SELECT * FROM A", false);
        return createViewStatement;
    }


    @Test
    public void testWithBasicSelect() {
        Database database = new HanaDatabase();
        CreateViewStatement statement = new CreateViewStatement(null, null, "view_name", "SELECT * FROM A", false);

        assertEquals("CREATE VIEW \"view_name\" AS SELECT * FROM A",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
