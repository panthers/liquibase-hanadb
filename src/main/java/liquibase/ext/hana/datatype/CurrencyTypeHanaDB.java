package liquibase.ext.hana.datatype;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.CurrencyType;
import liquibase.ext.hana.HanaDatabase;


@DataTypeInfo(name="currency", aliases = "money",
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class CurrencyTypeHanaDB extends CurrencyType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HanaDatabase) {
            return new DatabaseDataType("DECIMAL", 15, 2);
        }
        return super.toDatabaseDataType(database);
    }
}
