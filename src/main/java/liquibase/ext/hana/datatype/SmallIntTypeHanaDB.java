package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.SmallIntType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name="smallint", aliases = "java.sql.Types.SMALLINT",
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class SmallIntTypeHanaDB extends SmallIntType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("SMALLINT"); //always smallint regardless of parameters passed
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || value.toString().equalsIgnoreCase("null")) {
            return null;
        }
        if (value instanceof Boolean)
            return Boolean.TRUE.equals(value) ? "1" : "0";
        else
            return value.toString();
    }

}
