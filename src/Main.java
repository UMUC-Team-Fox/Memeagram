import Data.Database;
import Data.DropBoxController;
import UI.Memeagram;
import java.io.IOException;
import java.sql.SQLException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.UploadErrorException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, UploadErrorException, DbxException {
        Memeagram m = new Memeagram();
        DropBoxController dbc = new DropBoxController();
        try {dbc.connect();} catch(DbxException e) {}

        Database db = new Database();
        db.GetUsers();

    }


}
