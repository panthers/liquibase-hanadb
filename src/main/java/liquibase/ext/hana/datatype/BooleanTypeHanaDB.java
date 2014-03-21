package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BooleanType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name = "boolean", aliases = {"java.sql.Types.BOOLEAN", "java.lang.Boolean", "bit"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class BooleanTypeHanaDB extends BooleanType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("SMALLINT");
        }

        return super.toDatabaseDataType(database);
    }

    @Override
    protected boolean isNumericBoolean(Database database) {
        if (database instanceof HanaDatabase) {
            return true;
        }
        return super.isNumericBoolean(database);
    }

}
