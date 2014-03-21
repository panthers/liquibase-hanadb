package liquibase.ext.hana.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.ext.hana.HanaDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateSequenceGenerator;
import liquibase.statement.core.CreateSequenceStatement;


public class CreateSequenceGeneratorHanaDB extends CreateSequenceGenerator {

    @Override
    public boolean supports(CreateSequenceStatement statement, Database database) {
        return database instanceof HanaDatabase;
    }

    @Override
    public ValidationErrors validate(CreateSequenceStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();

        validationErrors.checkRequiredField("sequenceName", statement.getSequenceName());

        validationErrors.checkDisallowedField("ordered", statement.getOrdered(), database, HanaDatabase.class);

        return validationErrors;
    }

    @Override
    public Sql[] generateSql(CreateSequenceStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE SEQUENCE ");
        buffer.append(database.escapeSequenceName(statement.getCatalogName(), statement.getSchemaName(), statement.getSequenceName()));
        if (statement.getStartValue() != null) {
            buffer.append(" START WITH ").append(statement.getStartValue());
        }
        if (statement.getIncrementBy() != null) {
            buffer.append(" INCREMENT BY ").append(statement.getIncrementBy());
        }
        if (statement.getMinValue() != null) {
            buffer.append(" MINVALUE ").append(statement.getMinValue());
        }
        if (statement.getMaxValue() != null) {
            buffer.append(" MAXVALUE ").append(statement.getMaxValue());
        }

        if (statement.getCycle() != null) {
            if (statement.getCycle()) {
                buffer.append(" CYCLE");
            }
            else {
                buffer.append(" NO CYCLE");
            }
        }

//        return new Sql[]{new UnparsedSql(buffer.toString())};
        return new Sql[]{new UnparsedSql(buffer.toString(), getAffectedSequence(statement))};
    }

}
