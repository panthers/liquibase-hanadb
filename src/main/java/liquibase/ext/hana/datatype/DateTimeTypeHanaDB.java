package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.DateTimeType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name = "datetime", aliases = {"java.sql.Types.DATETIME", "java.util.Date"},
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class DateTimeTypeHanaDB extends DateTimeType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("TIMESTAMP");
        }

        return super.toDatabaseDataType(database);
    }

}
