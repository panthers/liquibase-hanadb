package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import liquibase.database.Database;
import liquibase.database.core.H2Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.AddColumnGeneratorHanaDB;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.MockSqlGeneratorChain;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.PrimaryKeyConstraint;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.AddColumnStatement;

import org.junit.Test;


public class AddColumnGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<AddColumnStatement> {

    public AddColumnGeneratorHanaDBTest() throws Exception {
        this(new AddColumnGeneratorHanaDB());
    } 

    protected AddColumnGeneratorHanaDBTest(SqlGenerator<AddColumnStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected AddColumnStatement createSampleSqlStatement() {
		return new AddColumnStatement(null, null, "table_name", "column_name", "column_type", null);
	}


	@Override
    public void isValid() throws Exception {
        super.isValid();
        AddColumnStatement addPKColumn = new AddColumnStatement(null, null, "table_name", "column_name", "column_type", null, new PrimaryKeyConstraint("pk_name"));

        assertFalse(generatorUnderTest.validate(addPKColumn, new HanaDatabase(), new MockSqlGeneratorChain()).hasErrors());
        assertTrue(generatorUnderTest.validate(addPKColumn, new H2Database(), new MockSqlGeneratorChain()).getErrorMessages().contains("Cannot add a primary key column"));

        // "catalog_name", "schema_name", "table_name", "column_name", "column_type" ...
        assertTrue(generatorUnderTest.validate(new AddColumnStatement(null, null, null, null, null, null, new AutoIncrementConstraint()), new MySQLDatabase(), new MockSqlGeneratorChain()).getErrorMessages().contains("Cannot add a non-primary key identity column"));
    }

    @Test
    public void testAddVarcharColumnWithDefaultValue() throws Exception {
        super.isValid();
        AddColumnStatement addDefaultColumn = new AddColumnStatement(null, null, "table_name", "column_name", "VARCHAR", "default_value", null);

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertFalse(generatorUnderTest.validate(addDefaultColumn, hanadb, new MockSqlGeneratorChain()).hasErrors());
        Sql[] generatedSql = generatorUnderTest.generateSql(addDefaultColumn, hanadb, sqlGeneratorChain);
        assertTrue(generatedSql.length == 1);
        assertEquals("ALTER TABLE \"table_name\" ADD (\"column_name\" VARCHAR DEFAULT 'default_value')", generatedSql[0].toSql());

    }

    @Test
    public void testAddIntegerColumnWithDefaultValue() throws Exception {
        super.isValid();
        AddColumnStatement addDefaultColumn = new AddColumnStatement(null, null, "table_name", "column_name", "INT", new Integer(10), null);

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertFalse(generatorUnderTest.validate(addDefaultColumn, hanadb, new MockSqlGeneratorChain()).hasErrors());
        Sql[] generatedSql = generatorUnderTest.generateSql(addDefaultColumn, hanadb, sqlGeneratorChain);
        assertTrue(generatedSql.length == 1);
        assertEquals("ALTER TABLE \"table_name\" ADD (\"column_name\" INTEGER DEFAULT 10)", generatedSql[0].toSql());

    }

    @Test
    public void testAddBooleanColumnWithDefaultValue() throws Exception {
        super.isValid();
        AddColumnStatement addDefaultColumn = new AddColumnStatement(null, null, "table_name", "column_name", "BOOLEAN", new Boolean(false), null);

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertFalse(generatorUnderTest.validate(addDefaultColumn, hanadb, new MockSqlGeneratorChain()).hasErrors());
        Sql[] generatedSql = generatorUnderTest.generateSql(addDefaultColumn, hanadb, sqlGeneratorChain);
        assertTrue(generatedSql.length == 1);
        assertEquals("ALTER TABLE \"table_name\" ADD (\"column_name\" SMALLINT DEFAULT 0)", generatedSql[0].toSql());

    }

    // "ALTER TABLE ADD COLUMN ..." does not support unique constraints in Hana
    @Test
    public void testAddBooleanColumnWithDefaultValueAndUniqueConstraint() throws Exception {
        super.isValid();

        UniqueConstraint uniqueConstraint = new UniqueConstraint();
        uniqueConstraint.setConstraintName("COLUMN1_UNIQUE");
        uniqueConstraint.addColumns("column_name");
        AddColumnStatement addDefaultColumn =
                new AddColumnStatement(null, null, "table_name", "column_name", "BOOLEAN", new Boolean(false), uniqueConstraint);

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertFalse(generatorUnderTest.validate(addDefaultColumn, hanadb, new MockSqlGeneratorChain()).hasErrors());
        Sql[] generatedSql = generatorUnderTest.generateSql(addDefaultColumn, hanadb, sqlGeneratorChain);
        assertTrue(generatedSql.length == 1);
        assertEquals("ALTER TABLE \"table_name\" ADD (\"column_name\" SMALLINT DEFAULT 0)", generatedSql[0].toSql());
    }

}
