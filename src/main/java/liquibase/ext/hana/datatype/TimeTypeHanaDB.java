package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.TimeType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name="time", aliases = {"java.sql.Types.TIME", "java.sql.Time"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class TimeTypeHanaDB extends TimeType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("TIME");
        }
        return new DatabaseDataType(getName());
    }

}
