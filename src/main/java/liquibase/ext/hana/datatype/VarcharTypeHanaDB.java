package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.VarcharType;
import liquibase.ext.hana.HanaDatabase;


@DataTypeInfo(name="varchar", aliases = {"java.sql.Types.VARCHAR", "java.lang.String", "varchar2"},
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class VarcharTypeHanaDB extends VarcharType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("VARCHAR", getParameters());
        }
        return super.toDatabaseDataType(database);
    }

}
