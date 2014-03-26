package liquibase.ext.hana.sqlgenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sqlgenerator.MockSqlGeneratorChain;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateTableStatement;

import org.junit.Test;

public abstract class AbstractSqlGeneratorHanaDBTest<T extends SqlStatement> {

    protected SqlGenerator<T> generatorUnderTest;

    public AbstractSqlGeneratorHanaDBTest(SqlGenerator<T> generatorUnderTest) throws Exception {
        this.generatorUnderTest = generatorUnderTest;
    }

    protected abstract T createSampleSqlStatement();

    protected void dropAndCreateTable(CreateTableStatement statement, Database database) throws SQLException, DatabaseException {
        ExecutorService.getInstance().getExecutor(database).execute(statement);

        if (!database.getAutoCommitMode()) {
            database.getConnection().commit();
        }

    }

    @Test
    public void isImplementation() throws Exception {
//        for (Database database : TestContextHanaDB.getInstance().getAllDatabases()) {
//            boolean isImpl = generatorUnderTest.supports(createSampleSqlStatement(), database);
//            if (shouldBeImplementation(database)) {
//                assertTrue("Unexpected false supports for " + database.getTypeName(), isImpl);
//            } else {
//                assertFalse("Unexpected true supports for " + database.getTypeName(), isImpl);
//            }
//        }
        Database database = new HanaDatabase();
        boolean isImpl = generatorUnderTest.supports(createSampleSqlStatement(), database);
        if (shouldBeImplementation(database)) {
            assertTrue("Unexpected false supports for " + database.getShortName(), isImpl);
        } else {
            assertFalse("Unexpected true supports for " + database.getShortName(), isImpl);
        }
    }

    @Test
    public void isValid() throws Exception {
//        for (Database database : TestContextHanaDB.getInstance().getAllDatabases()) {
//        	if (shouldBeImplementation(database)) {
//            	if (waitForException(database)) {
//            		assertTrue("The validation should be failed for " + database, generatorUnderTest.validate(createSampleSqlStatement(), database, new MockSqlGeneratorChain()).hasErrors());
//            	} else {
//            		assertFalse("isValid failed against " + database, generatorUnderTest.validate(createSampleSqlStatement(), database, new MockSqlGeneratorChain()).hasErrors());
//            	}
//
//        	}
//        }
        Database database = new HanaDatabase();
        if (shouldBeImplementation(database)) {
            if (waitForException(database)) {
                assertTrue("The validation should be failed for " + database, generatorUnderTest.validate(createSampleSqlStatement(), database, new MockSqlGeneratorChain()).hasErrors());
            } else {
                assertFalse("isValid failed against " + database, generatorUnderTest.validate(createSampleSqlStatement(), database, new MockSqlGeneratorChain()).hasErrors());
            }
        }
    }

    @Test
    public void checkExpectedGenerator() {
        assertEquals(this.getClass().getName().replaceFirst("Test$", ""), generatorUnderTest.getClass().getName());
    }

    protected boolean waitForException(Database database) {
        return false;
    }

    protected boolean shouldBeImplementation(Database database) {
        return true;
    }

}