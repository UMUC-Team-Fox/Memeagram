package memeagram;

import memeagram.data.DatabaseAccessController;
import memeagram.data.DropBoxController;
import com.dropbox.core.DbxException;

import java.sql.SQLException;

public class Context {

    public DatabaseAccessController dac;
    public DropBoxController dbc;

    Context() throws SQLException, DbxException {
        dbc = new DropBoxController();
        dac = new DatabaseAccessController();
    }
}
