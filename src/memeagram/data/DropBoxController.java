package memeagram.data;/*
 * Class : memeagram.Main
 * Description : Driver class to instantiate and instance of memegrame applicaton
 * Revision Date : 11/17/2017
 * Revision Number: 1
 * Authors : Team Foxtrot 
 */

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

public class DropBoxController {

	private static final String ACCESS_TOKEN = "kvwwnIM6CiAAAAAAAAAAEVe9QofGejko2UsH8No2wPnZn7xiKC-c-GoJklHuMTfs";
	public DbxClientV2 client;

	public DropBoxController() throws DbxException{
        DbxRequestConfig config = new DbxRequestConfig("memeagram/1.0", "en_US");
        client = new DbxClientV2(config, ACCESS_TOKEN);

        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
    }

}
