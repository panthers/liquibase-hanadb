package liquibase.ext.hana.datatype;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.DateType;

@DataTypeInfo(name="date", aliases = {"java.sql.Types.DATE", "java.sql.Date", "smalldatetime"},
        minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class DateTypeHanaDB extends DateType {

}
