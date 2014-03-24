package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.GetViewDefinitionGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;

import org.junit.Test;


public class GetViewDefinitionGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<GetViewDefinitionStatement> {

    public GetViewDefinitionGeneratorHanaDBTest() throws Exception {
        this(new GetViewDefinitionGeneratorHanaDB());
    }

    protected GetViewDefinitionGeneratorHanaDBTest(SqlGenerator<GetViewDefinitionStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected GetViewDefinitionStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        GetViewDefinitionStatement getViewDefinitionStatement = new GetViewDefinitionStatement(null, null, "view_name");
        return getViewDefinitionStatement;
    }


    @Test
    public void testGetViewNoSchema() {
        Database database = new HanaDatabase();
        GetViewDefinitionStatement statement = new GetViewDefinitionStatement(null, null, "actual_view_name");

        assertEquals("SELECT DEFINITION FROM VIEWS WHERE upper(VIEW_NAME)='ACTUAL_VIEW_NAME'",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testGetViewWithSchema() {
        Database database = new HanaDatabase();
        GetViewDefinitionStatement statement = new GetViewDefinitionStatement(null, "schema_name", "actual_view_name");

        assertEquals("SELECT DEFINITION FROM VIEWS WHERE upper(VIEW_NAME)='ACTUAL_VIEW_NAME'",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
