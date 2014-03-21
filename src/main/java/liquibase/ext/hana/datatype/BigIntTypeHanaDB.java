package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BigIntType;
import liquibase.ext.hana.HanaDatabase;


@DataTypeInfo(name="bigint", aliases = {"java.sql.Types.BIGINT", "java.math.BigInteger", "java.lang.Long", "integer8", "bigserial"},
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class BigIntTypeHanaDB extends BigIntType {

    private boolean autoIncrement;

    @Override
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    @Override
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("BIGINT");
        }
        return super.toDatabaseDataType(database);
    }

}
