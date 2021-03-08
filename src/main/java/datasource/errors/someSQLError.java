package datasource.errors;

import java.sql.SQLException;

public class someSQLError extends RuntimeException {
    public someSQLError(SQLException error) {
        error.printStackTrace();
    }

}
