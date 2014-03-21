package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.DoubleType;
import liquibase.ext.hana.HanaDatabase;

@DataTypeInfo(name="double", aliases = {"java.sql.Types.DOUBLE", "java.lang.Double"},
        minParameters = 0, maxParameters = 2, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class DoubleTypeHanaDB extends DoubleType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("DOUBLE");
        }
        return super.toDatabaseDataType(database);
    }

}
