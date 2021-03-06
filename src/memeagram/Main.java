package memeagram;

import memeagram.data.objects.*;
import memeagram.ui.Memeagram;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

        // to create a new meme
//        Meme meme = new Meme(context);
//        meme.userId = 1;
//        meme.tags.add("tag1");
//        meme.tags.add("tag2");
//        meme.captionText = "caption text";
//        meme.memeImage = ImageIO.read(new File("assets/sample.jpeg"));
//        meme.saveMeme();

        // to retreive a meme
//        Meme meme = Meme.getMemeById(context,20);

        // to retreive a meme image
//        Meme meme2 = Meme.getMemeById(context,20);
//        meme2.getImage();
//        System.out.println(meme2);

        // to get memes by a tag.
        //ArrayList<Meme> list = Meme.getMemesByTag(context,"tag1");
        //m.addMemesToBrowsePanel(list);

    }


}
