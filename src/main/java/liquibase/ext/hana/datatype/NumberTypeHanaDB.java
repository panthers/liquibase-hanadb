package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.NumberType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name="number", aliases = {"numeric", "java.sql.Types.NUMERIC"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class NumberTypeHanaDB extends NumberType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("DECIMAL", getParameters());
        }
        return super.toDatabaseDataType(database);
    }

}
