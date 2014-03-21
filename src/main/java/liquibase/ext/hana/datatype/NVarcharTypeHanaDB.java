package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.NVarcharType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name="nvarchar", aliases = {"java.sql.Types.NVARCHAR", "nvarchar2"},
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class NVarcharTypeHanaDB extends NVarcharType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("NVARCHAR", getParameters());
        }
        return super.toDatabaseDataType(database);
    }

}