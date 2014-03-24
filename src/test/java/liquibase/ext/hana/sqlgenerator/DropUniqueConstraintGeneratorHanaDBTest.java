package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.DropUniqueConstraintGeneratorHanaDB;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.core.DropUniqueConstraintStatement;

import org.junit.Test;


public class DropUniqueConstraintGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<DropUniqueConstraintStatement> {

    public DropUniqueConstraintGeneratorHanaDBTest() throws Exception {
        this(new DropUniqueConstraintGeneratorHanaDB());
    }

    protected DropUniqueConstraintGeneratorHanaDBTest(SqlGenerator<DropUniqueConstraintStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected DropUniqueConstraintStatement createSampleSqlStatement() {
        Database database = new HanaDatabase();
        DropUniqueConstraintStatement dropUniqueConstraintStatement = new DropUniqueConstraintStatement(null, null, "table_name", "constraint_name");
        return dropUniqueConstraintStatement;
    }


    @Test
    public void testDropUniqueConstraintNoSchema() {
        Database database = new HanaDatabase();
        DropUniqueConstraintStatement statement =
                new DropUniqueConstraintStatement(null, null, "table_name", "constraint_name");

        assertEquals("ALTER TABLE \"table_name\" DROP CONSTRAINT \"constraint_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

    @Test
    public void testDropUniqueConstraintWithSchema() {
        Database database = new HanaDatabase();
        DropUniqueConstraintStatement statement =
                new DropUniqueConstraintStatement(null, "schema_name", "table_name", "constraint_name");

        assertEquals("ALTER TABLE \"schema_name\".\"table_name\" DROP CONSTRAINT \"constraint_name\"",
                this.generatorUnderTest.generateSql(statement, database, null)[0].toSql());
    }

}
