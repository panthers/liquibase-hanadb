package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import liquibase.database.Database;
import liquibase.ext.hana.HanaDatabase;
import liquibase.ext.hana.sqlgenerator.CreateSequenceGeneratorHanaDB;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.MockSqlGeneratorChain;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.statement.core.CreateSequenceStatement;

import org.junit.Test;


public class CreateSequenceGeneratorHanaDBTest extends AbstractSqlGeneratorHanaDBTest<CreateSequenceStatement> {

    public CreateSequenceGeneratorHanaDBTest() throws Exception {
        this(new CreateSequenceGeneratorHanaDB());
    }

    protected CreateSequenceGeneratorHanaDBTest(SqlGenerator<CreateSequenceStatement> generatorUnderTest) throws Exception {
        super(generatorUnderTest);
    }

	@Override
	protected CreateSequenceStatement createSampleSqlStatement() {
        CreateSequenceStatement createSequenceStatement = new CreateSequenceStatement(null, null, "sequence_name");
//        createSequenceStatement.setMinValue(new BigInteger("100"));
        return createSequenceStatement;
    }


    @Test
    public void testCreateMinValue() throws Exception {
        super.isValid();
        CreateSequenceStatement createSequenceStatement = new CreateSequenceStatement(null, null, "sequence_name");
        createSequenceStatement.setMinValue(new BigInteger("1001"));

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertFalse(generatorUnderTest.validate(createSequenceStatement, hanadb, new MockSqlGeneratorChain()).hasErrors());
        Sql[] generatedSql = generatorUnderTest.generateSql(createSequenceStatement, hanadb, sqlGeneratorChain);
        assertTrue(generatedSql.length == 1);
        assertEquals("CREATE SEQUENCE \"sequence_name\" MINVALUE 1001", generatedSql[0].toSql());

    }

    @Test
    public void testCreateIncrementByAndMinValue() throws Exception {
        super.isValid();
        CreateSequenceStatement createSequenceStatement = new CreateSequenceStatement(null, null, "sequence_name");
        createSequenceStatement.setIncrementBy(new BigInteger("5"));
        createSequenceStatement.setMinValue(new BigInteger("2100"));

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertFalse(generatorUnderTest.validate(createSequenceStatement, hanadb, new MockSqlGeneratorChain()).hasErrors());
        Sql[] generatedSql = generatorUnderTest.generateSql(createSequenceStatement, hanadb, sqlGeneratorChain);
        assertTrue(generatedSql.length == 1);
        assertEquals("CREATE SEQUENCE \"sequence_name\" INCREMENT BY 5 MINVALUE 2100", generatedSql[0].toSql());

    }

    @Test
    public void testCreateOrdered() throws Exception {
        super.isValid();
        CreateSequenceStatement createSequenceStatement = new CreateSequenceStatement(null, null, "sequence_name");
        createSequenceStatement.setOrdered(true);

        Database hanadb = new HanaDatabase();
        SqlGeneratorChain sqlGeneratorChain = new MockSqlGeneratorChain();

        assertTrue(generatorUnderTest.validate(createSequenceStatement, hanadb, new MockSqlGeneratorChain()).hasErrors());
    }

}
