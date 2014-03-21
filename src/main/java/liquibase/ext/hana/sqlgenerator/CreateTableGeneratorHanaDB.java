package liquibase.ext.hana.sqlgenerator;

import java.util.Iterator;

import liquibase.database.Database;
import liquibase.datatype.DatabaseDataType;
import liquibase.ext.hana.HanaDatabase;
import liquibase.logging.LogFactory;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateTableGenerator;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.CreateTableStatement;
import liquibase.util.StringUtils;


public class CreateTableGeneratorHanaDB extends CreateTableGenerator {

    @Override
    public Sql[] generateSql(CreateTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE TABLE ").append(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName())).append(" ");
        buffer.append("(");

        boolean isSinglePrimaryKeyColumn = statement.getPrimaryKeyConstraint() != null
                && statement.getPrimaryKeyConstraint().getColumns().size() == 1;

        boolean isPrimaryKeyAutoIncrement = false;

        Iterator<String> columnIterator = statement.getColumns().iterator();
        while (columnIterator.hasNext()) {
            String column = columnIterator.next();

            DatabaseDataType columnType = statement.getColumnTypes().get(column).toDatabaseDataType(database);
            buffer.append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), column));

            buffer.append(" ").append(columnType);

            AutoIncrementConstraint autoIncrementConstraint = null;

            for (AutoIncrementConstraint currentAutoIncrementConstraint : statement.getAutoIncrementConstraints()) {
                if (column.equals(currentAutoIncrementConstraint.getColumnName())) {
                    autoIncrementConstraint = currentAutoIncrementConstraint;
                    break;
                }
            }

            boolean isAutoIncrementColumn = autoIncrementConstraint != null;
            boolean isPrimaryKeyColumn = statement.getPrimaryKeyConstraint() != null
                    && statement.getPrimaryKeyConstraint().getColumns().contains(column);
            isPrimaryKeyAutoIncrement = isPrimaryKeyAutoIncrement
                    || isPrimaryKeyColumn && isAutoIncrementColumn;

            if (statement.getDefaultValue(column) != null) {
                Object defaultValue = statement.getDefaultValue(column);
                buffer.append(" DEFAULT ");
                buffer.append(statement.getColumnTypes().get(column).objectToSql(defaultValue, database));
            }

            if (isAutoIncrementColumn) {
                LogFactory.getLogger().warning(database.getShortName()+" does not support autoincrement columns as request for "+(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName())));
            }

            if (statement.getNotNullColumns().contains(column)) {
                buffer.append(" NOT NULL");
            }

            if (columnIterator.hasNext()) {
                buffer.append(", ");
            }
        }

        buffer.append(",");

        if (statement.getPrimaryKeyConstraint() != null && statement.getPrimaryKeyConstraint().getColumns().size() > 0) {
            buffer.append(" PRIMARY KEY (");
            buffer.append(database.escapeColumnNameList(StringUtils.join(statement.getPrimaryKeyConstraint().getColumns(), ", ")));
            buffer.append(")");
            buffer.append(",");
        }

//        for (ForeignKeyConstraint fkConstraint : statement.getForeignKeyConstraints()) {
//            buffer.append(" CONSTRAINT ");
//            buffer.append(database.escapeConstraintName(fkConstraint.getForeignKeyName()));
//            String referencesString = fkConstraint.getReferences();
//            if (!referencesString.contains(".") && database.getDefaultSchemaName() != null) {
//                referencesString = database.getDefaultSchemaName()+"."+referencesString;
//            }
//            buffer.append(" FOREIGN KEY (")
//                    .append(database.escapeColumnName(statement.getSchemaName(), statement.getTableName(), fkConstraint.getColumn()))
//                    .append(") REFERENCES ")
//                    .append(referencesString);
//
//            if (fkConstraint.isDeleteCascade()) {
//                buffer.append(" ON DELETE CASCADE");
//            }
//
//            if (fkConstraint.isInitiallyDeferred()) {
//                buffer.append(" INITIALLY DEFERRED");
//            }
//            if (fkConstraint.isDeferrable()) {
//                buffer.append(" DEFERRABLE");
//            }
//            buffer.append(",");
//        }

        for (UniqueConstraint uniqueConstraint : statement.getUniqueConstraints()) {
            buffer.append(" UNIQUE (");
            buffer.append(database.escapeColumnNameList(StringUtils.join(uniqueConstraint.getColumns(), ", ")));
            buffer.append(")");
            buffer.append(",");
        }

        String sql = buffer.toString().replaceFirst(",\\s*$", "") + ")";

        if (statement.getTablespace() != null && database.supportsTablespaces()) {
            sql += " TABLESPACE " + statement.getTablespace();
        }

        return new Sql[] {
                new UnparsedSql(sql, getAffectedTable(statement))
        };
    }


    /**
     * @see liquibase.sqlgenerator.core.AbstractSqlGenerator#getPriority()
     */
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    /**
     * @see liquibase.sqlgenerator.core.AbstractSqlGenerator#supports(liquibase.statement.SqlStatement, liquibase.database.Database)
     */
    @Override
    public boolean supports(CreateTableStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

}
