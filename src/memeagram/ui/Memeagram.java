package memeagram.ui;
/*
 * Class : memeagram.Main
 * Description : Driver class to instantiate and instance of memegrame applicaton
 * Revision Date : 11/11/2017
 * Revision Number: 1
 * Authors : Team Foxtrot 
 */

import memeagram.Context;
import memeagram.data.objects.Meme;
import memeagram.data.objects.User;
import memeagram.imageManipulation.ImageController;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class Memeagram{

    JFrame f;
    JPanel mainPanel, signInPanel;
    ImagePanel imagePanel;
    BrowsePanel browsePanel;
    
    JTabbedPane tp;
    JScrollPane browsePane, imagePane;
    User user;
 

    public Memeagram(Context context) throws IOException{
        f = new JFrame("Memeagram");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Panels and Boxes
        signInPanel = new JPanel();
        
        
        Box mainBox = Box.createVerticalBox();
		
    	// new user line 1
    	Box newUser1 = Box.createHorizontalBox();
    	newUser1.add(new JLabel("Username : "));
    	JTextField newUserNameTF = new JTextField(15);
    	newUser1.add(newUserNameTF);
    	mainBox.add(Box.createVerticalStrut(30));
    	mainBox.add(newUser1);


    	
    	// new user line 2
    	Box newUser2 = Box.createHorizontalBox();
    	newUser2.add(new JLabel("First Name : "));
    	JTextField newFirstNameTF = new JTextField(15);
    	newUser2.add(newFirstNameTF);
    	mainBox.add(Box.createVerticalStrut(30));
    	mainBox.add(newUser2);
    	
    	
    	//new user line 3
    	Box newUser3 = Box.createHorizontalBox();
    	newUser3.add(new JLabel("Last Name :"));
    	JTextField newLastNameTF = new JTextField(15);
    	newUser3.add(newLastNameTF);
    	mainBox.add(Box.createVerticalStrut(30));
    	mainBox.add(newUser3);
    	
    	JButton btnCreateUser = new JButton("Create New User");
    	btnCreateUser.addActionListener(new ActionListener(){

    		public void actionPerformed(ActionEvent e){
    			try {
    			user = new User(context, newUserNameTF.getText(), newFirstNameTF.getText(), newLastNameTF.getText());
    			if(user.create()) {
    				System.out.println("new user created success " + user.userName);
    				viewFullApp();
    			}else {
    				System.out.println("Something went wrong creating user");
    			}
    			}catch (Exception ex) {}
    			
    		}
    		
    		
    	});
    	mainBox.add(Box.createVerticalStrut(10));
    	mainBox.add(btnCreateUser);
    	mainBox.add(Box.createVerticalStrut(60));
    	
    	
    	// existing user line 1
    	Box existingUser = Box.createHorizontalBox();
    	existingUser.add(new JLabel("Username : "));
    	JTextField existingUsername = new JTextField(15);
    	existingUser.add(existingUsername);
    	mainBox.add(Box.createVerticalStrut(10));
    	mainBox.add(existingUser);
    	
    	JButton btnSignIn = new JButton("Sign In");
    	btnSignIn.addActionListener(new ActionListener(){

    		public void actionPerformed(ActionEvent e) {
    			try {
    			user = new User(context, existingUsername.getText());
    			viewFullApp();
    			}catch(Exception ex) {}
    		}
    		
    	});
    	mainBox.add(Box.createVerticalStrut(10));
    	mainBox.add(btnSignIn);
    	signInPanel.add(mainBox);
        
        
        
        mainPanel = new JPanel();
        browsePanel = new BrowsePanel(context);
        imagePanel = new ImagePanel(context);
        
        
       

        //Tabbed Pane setup
        tp = new JTabbedPane();
        tp.setBounds(50, 50, 600, 600);
        tp.add(" Home ", signInPanel);
        f.add(tp);
        f.setSize(700,700);
        f.setLayout(null);
        f.setVisible(true);
        
        

    }
    
    public void viewFullApp() {
    	imagePanel.user = this.user;
    	tp.add(" Create ", imagePanel);
        tp.add(" Browse ", browsePanel);
    	f.add(tp);
    	f.validate();
    	f.repaint();
    }
   
   
    
    Map<String,ImageIcon> imageMap;

    public void addMemesToBrowsePanel(ArrayList<Meme> memes) {
        browsePane = new JScrollPane();
        imageMap = createImageMap(memes);
        DefaultListModel<Meme> model = new DefaultListModel<>();
        for(Meme meme : memes) model.addElement(meme);
        JList<Meme> list = new JList<>(model);
        list.setCellRenderer(new MemeListRenderer());
        browsePane.setPreferredSize(new Dimension (300,400));
        browsePanel.add(browsePane);
        browsePanel.repaint();
        browsePanel.validate();


    }

    public class MemeListRenderer extends DefaultListCellRenderer {
        Font font = new Font("helvatica", Font.BOLD, 24);
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }

    private Map<String, ImageIcon> createImageMap(ArrayList<Meme> list) {
        Map<String, ImageIcon> map = new HashMap<>();
        try {
            for (Meme meme : list)
            {
                if(!meme.getImage()) System.out.println("Image False");
                ImageIcon icon = new ImageIcon(meme.memeImage);
                map.put(meme.captionText, icon);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}