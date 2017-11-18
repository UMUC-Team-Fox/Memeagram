package Data;/*
 * Class : Main
 * Description : Driver class to instantiate and instance of memegrame applicaton
 * Revision Date : 11/17/2017
 * Revision Number: 1
 * Authors : Team Foxtrot 
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

public class DropBoxController {

	private static final String ACCESS_TOKEN = 
			"kvwwnIM6CiAAAAAAAAAAEVe9QofGejko2UsH8No2wPnZn7xiKC-c-GoJklHuMTfs";
	private DbxClientV2 client;
	
    public void connect()throws DbxException{
    	DbxRequestConfig config = new DbxRequestConfig("Memeagram/1.0", "en_US");
        client = new DbxClientV2(config, ACCESS_TOKEN);
        
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
    }
    


}
