package memeagram.ui;

import memeagram.Context;
import memeagram.data.objects.Meme;
import memeagram.imageManipulation.ImageController;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// todo add jtextfield for adding a caption
//todo add ability for multiple tags

class ImagePanel extends JPanel{

    Meme meme;

    ImagePanel(Context context) {
        setPreferredSize(new Dimension(400,400));
        setBackground(Color.WHITE);

        File wkDir = new File(System.getProperty("user.dir"));

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
        jfc.setCurrentDirectory(wkDir);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        JButton btnFileChooser = new JButton("Browse");
        JButton btnAddText = new JButton("Add Text");
        JButton btnSubmit = new JButton("Submit");

        JLabel imageLabel = new JLabel();
        imageLabel.setSize(400, 400);
        imageLabel.setBackground(Color.white);
        imageLabel.setPreferredSize(new Dimension (400,400));

        JTextField tFieldImageText = new JTextField(15);
        tFieldImageText.setMaximumSize(tFieldImageText.getPreferredSize());

        JTextField tFieldTag = new JTextField(15);
        tFieldTag.setMaximumSize(tFieldTag.getPreferredSize());

        Box createTopBox1 = Box.createHorizontalBox();
        JLabel fileText = new JLabel("Select and image file : ");
        createTopBox1.add(fileText);
        createTopBox1.add(Box.createHorizontalStrut(10));
        createTopBox1.add(btnFileChooser);
        this.add(createTopBox1);

        Box createTopBox2 = Box.createHorizontalBox();
        JLabel imageAddText = new JLabel("Add Text to Image:");
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rBtnTop = new JRadioButton("Top");
        JRadioButton rBtnBottom = new JRadioButton("Bottom");
        bg.add(rBtnTop);
        bg.add(rBtnBottom);
        createTopBox2.add(imageAddText);
        createTopBox2.add(Box.createHorizontalStrut(10));
        createTopBox2.add(tFieldImageText);
        createTopBox2.add(Box.createHorizontalStrut(10));
        createTopBox2.add(rBtnTop);
        createTopBox2.add(rBtnBottom);
        createTopBox2.add(Box.createHorizontalStrut(10));
        createTopBox2.add(btnAddText);
        this.add(createTopBox2);

        Box imageBox = Box.createVerticalBox();
        imageBox.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        this.add(imageLabel);

        Box createBottomBox1 = Box.createHorizontalBox();
        JLabel tagsText = new JLabel("Meme tag:");
        createBottomBox1.add(tagsText);
        createBottomBox1.add(Box.createHorizontalStrut(10));
        createBottomBox1.add(tFieldTag);
        this.add(createBottomBox1);

        Box createBottomBox2 = Box.createHorizontalBox();
        JLabel submitText = new JLabel("Submit Meme to Library");
        createBottomBox2.add(submitText);
        createBottomBox2.add(Box.createHorizontalStrut(10));
        createBottomBox2.add(btnSubmit);
        this.add(createBottomBox2);

        //Listeners
        //Button listener for file chooser button
        btnFileChooser.addActionListener(e -> {
            int returnValue = jfc.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION) {

                File workingFile = jfc.getSelectedFile();
                meme = new Meme(context);

                try{
                    meme.originalImage = ImageController.getImage(workingFile);
                }catch(IOException ex) {
                    ex.printStackTrace();
                }

                meme.originalImage = ImageController.resizeImage(meme.originalImage, 400, 400);
                meme.memeImage = meme.originalImage;
                imageLabel.setIcon(new ImageIcon(meme.memeImage));
                imageLabel.validate();
                imageLabel.repaint();

            }
        });

        //Button Listener for Add Text button
        btnAddText.addActionListener(e -> {
            boolean onTop = false;
            if(rBtnTop.isSelected()) {
                onTop = true;
            }

            meme.memeImage = meme.originalImage;
            meme.memeImage = ImageController.addText(meme.originalImage, tFieldImageText.getText(), onTop);
            imageLabel.setIcon(new ImageIcon(meme.memeImage));
            imageLabel.validate();
            imageLabel.repaint();

        });

        //Button Listener for submit
        btnSubmit.addActionListener(e -> {
            // todo create user and use that id instead of this hardcoded value.
            meme.userId = 1;
            meme.tags.add(tagsText.getText());

            meme.saveMeme();

        });
    }

}
