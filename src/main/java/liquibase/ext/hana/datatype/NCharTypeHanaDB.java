package liquibase.ext.hana.datatype;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.NCharType;

@DataTypeInfo(name="nchar", aliases = "java.sql.Types.NCHAR",
        minParameters = 0, maxParameters = 1, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class NCharTypeHanaDB extends NCharType {


}