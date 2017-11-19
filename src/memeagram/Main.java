package memeagram;

import memeagram.data.objects.*;
import memeagram.ui.Memeagram;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import com.dropbox.core.DbxException;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, DbxException {
        Context context = new Context();

        // create the ui
        Memeagram m = new Memeagram(context);

        // to create a new user
//        User newUser = new User(context, "username", "first", "last");
//        boolean successfullyCreated = newUser.create();

        // to retrieve a user
//        User existingUser = new User(context, "justin");

        // to create a new meme
//        Meme meme = new Meme(context);
//        meme.userId = existingUser.id;
//        meme.tags.add("tag1");
//        meme.tags.add("tag2");
//        meme.captionText = "caption text";
//        meme.memeImage = ImageIO.read(new File("assets/sample.jpeg"));
//        meme.saveMeme();
    }


}
