package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.ClobType;
import liquibase.ext.hana.HanaDatabase;


@DataTypeInfo(name="clob", aliases = {"longvarchar", "text", "longtext", "java.sql.Types.LONGVARCHAR", "java.sql.Types.CLOB"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class ClobTypeHanaDB extends ClobType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("NCLOB");
        }

        return super.toDatabaseDataType(database);
    }

}
