package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.TimestampType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name = "timestamp", aliases = {"timestamp", "java.sql.Types.TIMESTAMP", "java.sql.Timestamp"},
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class TimestampTypeHanaDB extends TimestampType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("TIMESTAMP");
        }
        return super.toDatabaseDataType(database);
    }

}
