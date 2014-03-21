package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BlobType;
import liquibase.ext.hana.HanaDatabase;


@DataTypeInfo(name="blob", aliases = {"longblob", "longvarbinary", "java.sql.Types.BLOB", "java.sql.Types.LONGBLOB", "java.sql.Types.LONGVARBINARY", "java.sql.Types.VARBINARY", "varbinary"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class BlobTypeHanaDB extends BlobType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("BLOB");
        }
        return super.toDatabaseDataType(database);
    }

}
