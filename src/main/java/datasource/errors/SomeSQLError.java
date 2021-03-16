package datasource.errors;

import java.sql.SQLException;

public class SomeSQLError extends RuntimeException {
    public SomeSQLError(SQLException error) {
        error.printStackTrace();
    }

}
