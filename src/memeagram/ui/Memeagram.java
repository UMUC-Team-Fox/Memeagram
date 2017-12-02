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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;  

public class Memeagram{

    JFrame f;
    JPanel mainPanel,browsePanel, imagePanel;
    Box createBox, createTopBox1, createTopBox2, createBottomBox1, createBottomBox2;
    JButton btnFileChooser, btnAddText, btnSubmit;
    JTextField tFieldImageText, tFieldTag;
    JTabbedPane tp;
    BufferedImage originalImage, memeImage;
    JLabel imageLabel;
    ImageController ic;
    JFileChooser jfc;
    File workingFile;
    JScrollPane browsePane, imagePane;
    ButtonGroup bg;
    JRadioButton rBtnTop, rBtnBottom;
    

    public Memeagram(Context context) throws IOException{
        f = new JFrame("Memeagram");
        ic = new ImageController();
        jfc = new JFileChooser(FileSystemView.getFileSystemView());
        File wkDir = new File(System.getProperty("user.dir"));
        jfc.setCurrentDirectory(wkDir);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      
        
        //Panels and Boxes
        mainPanel = new JPanel();
        browsePanel = new JPanel();
        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(400,400));
        imagePanel.setBackground(Color.WHITE);

        
        createBox = Box.createVerticalBox();  
        createTopBox1 = Box.createHorizontalBox();
        createTopBox2 = Box.createHorizontalBox();
        createBottomBox1 = Box.createHorizontalBox();
        createBottomBox2 = Box.createHorizontalBox();
        
        //objects and configurations
        imageLabel = new JLabel();
        imageLabel.setSize(400, 400);
        imageLabel.setBackground(Color.white);
        imageLabel.setPreferredSize(new Dimension (400,400));

   

        //Interactive objects and configurations
        btnFileChooser = new JButton("Browse");
        btnAddText = new JButton("Add Text");
        btnSubmit = new JButton("Submit");
        tFieldImageText = new JTextField(15);
        tFieldImageText.setMaximumSize(tFieldImageText.getPreferredSize());
        tFieldTag = new JTextField(15);
        tFieldTag.setMaximumSize(tFieldTag.getPreferredSize());
        
        //Adding objects to panels and Boxes
         
        JLabel fileText = new JLabel("Select and image file : ");
        createTopBox1.add(fileText);
        createTopBox1.add(Box.createHorizontalStrut(10));
        createTopBox1.add(btnFileChooser);
        
        JLabel imageAddText = new JLabel("Add Text to Image:");
        createTopBox2.add(imageAddText);
        createTopBox2.add(Box.createHorizontalStrut(10));
        createTopBox2.add(tFieldImageText);
        createTopBox2.add(Box.createHorizontalStrut(10));
        bg = new ButtonGroup();
        rBtnTop = new JRadioButton("Top");
        rBtnBottom = new JRadioButton("Bottom");
        bg.add(rBtnTop);
        bg.add(rBtnBottom);
        createTopBox2.add(rBtnTop);
        createTopBox2.add(rBtnBottom);
        createTopBox2.add(Box.createHorizontalStrut(10));
        createTopBox2.add(btnAddText); 
        
        JLabel tagsText = new JLabel("Meme tag:");
        createBottomBox1.add(tagsText);
        createBottomBox1.add(Box.createHorizontalStrut(10));
        createBottomBox1.add(tFieldTag);  
        
        JLabel submitText = new JLabel("Submit Meme to Library");
        createBottomBox2.add(submitText);
        createBottomBox2.add(Box.createHorizontalStrut(10));
        createBottomBox2.add(btnSubmit);
        
        
        
        
        imagePanel.add(imageLabel);
        createBox.add(createTopBox1);
        createBox.add(createTopBox2);
        createBox.add(imagePanel);
        createBox.add(createBottomBox1);
        createBox.add(createBottomBox2);
      
        //Listeners
        //Button listener for file chooser button
        btnFileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int returnValue = jfc.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
						
					    workingFile = jfc.getSelectedFile();
					    try{
					    originalImage = ic.getImage(workingFile);
					    originalImage = ic.resizeImage(originalImage, 400, 400);
					    imageLabel.setIcon(new ImageIcon(originalImage));
						createBox.validate();
						createBox.repaint(); // refresh the create panel
					    }catch(IOException ex) {}
				}
			}
        	
        });
        
        //Button Listener for Add Text button
        btnAddText.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				boolean onTop = false;
				if(rBtnTop.isSelected()) {
					onTop = true;
				}
				
				try{memeImage = ic.addText(originalImage, tFieldImageText.getText(), onTop);} catch(IOException ex){}
				createBox.validate();
				createBox.repaint(); // refresh the create panel
			}
        	
        });
        
        //Button Listener for submit 
        btnSubmit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					
				}catch(Exception ex) {}
				
			}
        	
        });

        //Tabbed Pane setup
        tp = new JTabbedPane();
        tp.setBounds(50, 50, 600, 600);
        tp.add(" Home ", mainPanel);
        tp.add(" Create ", createBox);
        tp.add(" Browse ", browsePanel);
        f.add(tp);
        f.setSize(700,700);
        f.setLayout(null);
        f.setVisible(true);
        

    }
    Map<String,ImageIcon> imageMap;

    public void addMemesToBrowsePanel(ArrayList<Meme> memes) {
        browsePane = new JScrollPane();
        imageMap = createImageMap(memes);
        DefaultListModel model = new DefaultListModel();
        for(Meme meme : memes) model.addElement(meme);
        JList list = new JList(model);
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