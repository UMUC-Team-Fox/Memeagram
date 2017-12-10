package memeagram.ui;

import memeagram.Context;
import memeagram.data.objects.Meme;
import memeagram.data.objects.User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class BrowsePanel extends JPanel {
	
	Meme meme;
	ArrayList<Meme> memeList;
	Integer currentMemeNum = 0;
	Integer totalNumOfMemes = 0;
	Integer memeLikes = 0;
	Integer memeDislikes = 0;
	Context context;
	JLabel imageLabel;
	
	BrowsePanel(Context context){
		setPreferredSize(new Dimension(400,400));
        setBackground(Color.WHITE);
        
        this.context = context;
        
        Box mainBox = Box.createVerticalBox();
        Box topBox1 = Box.createHorizontalBox();
        topBox1.add(new JLabel("Tags Search : "));
        JTextField tagsTF = new JTextField(15);
        tagsTF.setMaximumSize(tagsTF.getPreferredSize());
        topBox1.add(tagsTF);
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				memeList = Meme.getMemesByTag(context,tagsTF.getText());
				if(!memeList.isEmpty()) {
					currentMemeNum = 1;
					totalNumOfMemes = memeList.size();
				}
				}catch(Exception ex) {}
			}
        	
        });
        topBox1.add(btnSearch);
        mainBox.add(topBox1);
        
        Box topBox2 = Box.createHorizontalBox();
        topBox2.add(new JLabel("Likes Search : "));
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rBtnHighest = new JRadioButton("Highest Rated");
        JRadioButton rBtnLowest = new JRadioButton("Lowest Rated");
        bg.add(rBtnHighest);
        bg.add(rBtnLowest);
        topBox2.add(rBtnHighest);
        topBox2.add(rBtnLowest);
        JButton btnSearchRating = new JButton("Search Rated");
        btnSearchRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				    memeList = Meme.getAllMemes(context);
				    updateMemeImage(0);
				}
				catch(Exception ex) {
				    ex.printStackTrace();
                }

				if(memeList.isEmpty()) {
					System.out.println("Search was unsuccessful");
				}else {
					System.out.println("Search was successful");
				}
			}
        	
        });
        topBox2.add(btnSearchRating);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(topBox2);
        
        imageLabel = new JLabel();
        imageLabel.setSize(400, 400);
        imageLabel.setBackground(Color.white);
        imageLabel.setPreferredSize(new Dimension (400,400));
        
        Box imageBox = Box.createVerticalBox();
        imageBox.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        mainBox.add(imageLabel);
        
        Box bottomBox1 = Box.createHorizontalBox();
        JButton btnPrev = new JButton("Previous");
        btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentMemeNum -= 1;
				try{updateMemeImage(currentMemeNum);}catch (Exception ex) {}
				
			}
        	
        });
        bottomBox1.add(btnPrev);
        bottomBox1.add(Box.createHorizontalStrut(30));
        bottomBox1.add(new JLabel(Integer.toString(currentMemeNum) + " of " + Integer.toString(totalNumOfMemes)));
        bottomBox1.add(Box.createHorizontalStrut(30));
        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentMemeNum += 1;
				try{updateMemeImage(currentMemeNum);}catch (Exception ex) {}
				
			}
        	
        });
        bottomBox1.add(btnNext);
        mainBox.add(bottomBox1); 
        
        Box bottomBox2 = Box.createHorizontalBox();
        JButton btnLike = new JButton("Like");
        btnLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				//Like button stuff here
			}
        	
        });
        bottomBox2.add(btnLike);
        bottomBox2.add(Box.createHorizontalStrut(30));
        bottomBox2.add(new JLabel("Likes : " +Integer.toString(memeLikes) + "  Dislikes : " + Integer.toString(memeDislikes)));
        JButton btnDislike = new JButton("Dislike");
        btnDislike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// dislike button stuff here
				
			}
        	
        });
        bottomBox2.add(Box.createHorizontalStrut(30));
        bottomBox2.add(btnDislike);
        mainBox.add(Box.createVerticalStrut(30));
        mainBox.add(bottomBox2);
        
        
        this.add(mainBox);
	}
	
	public void updateMemeImage(Integer i) throws SQLException {
	  meme = memeList.get(i);
      meme.getImage();
      imageLabel.setIcon(new ImageIcon(meme.memeImage));
      this.validate();
      this.repaint();
	}




}
