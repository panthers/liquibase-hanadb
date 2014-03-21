package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.UUIDType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name="uuid", aliases = {"uniqueidentifier"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class UUIDTypeHanaDB extends UUIDType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("VARCHAR", 36);
        }
        return super.toDatabaseDataType(database);
    }

}
