package liquibase.ext.hana.datatype;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.DatabaseFunctionType;

@DataTypeInfo(name="function", aliases = "liquibase.statement.DatabaseFunction",
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class DatabaseFunctionTypeHanaDB extends DatabaseFunctionType {

}
