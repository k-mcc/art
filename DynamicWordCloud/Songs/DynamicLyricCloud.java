/**

* @author katemccarthy

* December 2020

* DynamicWordCloud generates a floating word graphic based on a text file.
* The font size of each word in the GUI correlates to its frequency within the text file.

*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class DynamicLyricCloud {

	public static int wc;

    public static void main(String[] args) {
    	String fileName = "/Users/katemccarthy/Documents/GitHub/art/DynamicWordCloud/sampleText.txt";
    	Map<String, Integer> words = frequentWords(fileName);

    	Box box1 = new Box();
    	box1.run(words,wc);
    }

  /**
   * @param fileName
   *
	 * @return TreeMap with all words in text file sorted by word frequency
	 *
	 * maps each word in file to the number of times it appears
	 */
	public static Map<String, Integer> frequentWords(String fileName) {

		String[] fillers = {"i","you","me","my","your","and","but","for","the","in",
				"on","of","with","a","your","i'm","to","i'll","i've","it","that",
				"that's","it's","its","then","this","than","you'll","is","was",
				"be","am","when","get","got","go","like","will","oh","do","if","what",
        "just","into","through","there","don't","have","when","where","how","who"
        ,"they","been","take","had","want","we're","up","no","you're","cause",
        "because","so","about","day","back","why","take","give","out","over",
        "again","by","some","something","say","gotta","let","happen","think",
        "thought","make","call","somewhere","we","are","going","'cause","till",
        "only","just","can","can't","cannot","you've","everything's","everything",
        "ask","let's","from","towards","back","under","right","left","leave",
        "before","after","since","or","without","&","see","saw","an","all",
        "know","even","he","she","her","it","him","them","they","their","theirs"
        ,"there's","there","here","'til","i'd","until","yeah","ooh","na","you'd",
				"anyone","la","da","oh","ooh-ooh"};
		java.util.List<String> disregard = Arrays.asList(fillers);

		Map<String, Integer> map = new TreeMap<String, Integer>();
		wc = 0;
		try {
			Scanner input = new Scanner(new FileInputStream(fileName));
			// while loop, counts individual words
			while (input.hasNext()) {
        String word = input.next();
        word = removePunctuation(word);

				boolean repeatedWord = false;
				for (Entry<String, Integer> e : map.entrySet()) {
					if (e.getKey().toLowerCase().equals(word.toLowerCase())) {
					    e.setValue(e.getValue() + 1);
					    repeatedWord = true;
					}
				}
				if (!repeatedWord && !disregard.contains(word.toLowerCase()) && !word.equals("<|endoftext|>")) {
          map.put(word, 1);
          wc += 1;
        }
			}
		 } catch (FileNotFoundException e) {
			 System.out.println("File Not Found: " + fileName);
		 }

		Map<String, Integer> display = sortByWordCount(map);

		return display;
	}

	/**
	 * @param word	the String to be modified
	 *
	 * @return the String without punctuation
	 */
	public static String removePunctuation(String word) {
		word = word.replace(",’", ""); // remove punctuation
		word = word.replace(".’", "");
		word = word.replace("?’", "");
		word = word.replace("!’", "");

		word = word.replace(".", "");
		word = word.replace("?", "");
		word = word.replace(",", "");
		word = word.replace("‘", "");
		word = word.replace("!", "");
		word = word.replace("—", "");

		word = word.replace("\"", "");
		word = word.replace("”", "");
		word = word.replace("“", "");
		word = word.replace("(", "");
		word = word.replace(")", "");

		return word;
	}

	/**
	 * @param map	 original map sorted by keys
	 *
	 * @return map sorted by values
	 */
	public static  Map<String, Integer> sortByWordCount(Map<String, Integer> map) {
		Comparator<String> valueComparator = new Comparator<String>() {
			public int compare(String k1, String k2) {
	        int compare =  map.get(k1).compareTo(map.get(k2));
	        if (compare == 0) return 1;
	        else return compare;
	      }
	    };

	    Map<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);

	    sortedByValues.putAll(map);

	    return sortedByValues;
	}

}

class Box {

    private JFrame mainFrame;
    private DrawPanel drawPanel;
    private java.util.List<Word> words;

    private int windowWidth = 1450;
    private int windowHeight = 800;
    private String windowLabel = "Interactive Word Float";

    void run(Map<String, Integer> inputs, int wc) {

    	words = new ArrayList<>();

    	for (Entry<String, Integer> e : inputs.entrySet()) {

    		//calculate the appropriate font size.
			int fontSize = 11;

			double p = ((double)e.getValue()/(double)wc) * 100;
			/* p represents the relative frequency of the word within the text
			as a percentage of the total word count*/

	    if (p > 1) {
	    		if (p < 2)	fontSize = 30;
	    		else if (p < 2.5) fontSize = 40;
	    		else if (p < 3) fontSize = 60;
	    		else if (p < 3.5) fontSize = 70;
	    		else if (p < 4) fontSize = 80;
	    		else if (p < 4.5) fontSize = 90;
	    		else if (p < 5.5) fontSize = 110;
	    		else if (p > 5.5) fontSize = 120;

	    		int xVelocity;
	    		int yVelocity;

	    		if (fontSize < 60) {
		      		// ensure that the generated velocities are not zero (so all words are moving):
		      		xVelocity = (int) Math.floor((Math.random() * 3) - 1); // random X axis velocity
		      		while (xVelocity == 0) xVelocity = (int) Math.floor((Math.random() * 3) - 1);
		      		yVelocity = (int) Math.floor((Math.random() * 3) - 1);  // random Y axis velocity
		      		while (yVelocity == 0) yVelocity = (int) Math.floor((Math.random() * 3) - 1);
	    		} else {
		      		// ensure that the generated velocities are not zero (so all words are moving):
		      		xVelocity = (int) Math.floor((Math.random() * 10) - 5); // random X axis velocity
		      		while (xVelocity == 0) xVelocity = (int) Math.floor((Math.random() * 10) - 5);
		      		yVelocity = (int) Math.floor((Math.random() * 10) - 5);  // random Y axis velocity
		      		while (yVelocity == 0) yVelocity = (int) Math.floor((Math.random() * 10) - 5);
	    		}

	      		//generate parts of a random color:
	      		int rNum = (int) Math.floor((Math.random() * 256));
	      		int bNum = (int) Math.floor((Math.random() * 256));
	      		int gNum = (int) Math.floor((Math.random() * 256));

	        	// construct a Word object for each String in the map
	            Word word = new Word(
	            		e.getKey(), // String

	                    (int) Math.floor(Math.random() * windowWidth),  // random initial position on X axis
	                    (int) Math.floor(Math.random() * windowHeight), // random initial position on Y axis

	                    fontSize, // font size, based on word frequency
	                    //TODO: make font size relative using standard deviation

	                    rNum, // RGB color variables
	                    gNum,
	                    bNum,

	                    xVelocity,
	                    yVelocity
	            );

	            words.add(word);
	          }
        }

        mainFrame = new JFrame();
        drawPanel = new DrawPanel();
        mainFrame.getContentPane().add(drawPanel);
        mainFrame.setTitle(windowLabel);
        mainFrame.setSize(windowWidth, windowHeight);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        while (true) {
            for (Word w: words) {
                w.update();
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mainFrame.repaint();
        }

    }

    class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            this.setBackground(Color.black);

            for (Word w: words) {
            	w.paint(graphics);
            }

        }
    }

    class Word {
        private int posX, posY, size;
        //private Color color;
        //variables used to assign random colors
        private int rNum;
        private int gNum;
        private int bNum;
        private int modR;
        private int modG;
        private int modB;
        private String text;

        private int vx = 5;
        private int vy = 5;

        public Word(String text, int posX, int posY, int size, int rNum, int gNum, int bNum, int vx, int vy) { //Color color
            this.text = text;
        	this.posX = posX;
            this.posY = posY;
            this.size = size;
            //this.color = color;
          //variables used to assign random colors
            this.rNum = rNum;
            this.gNum = gNum;
            this.bNum = bNum;
            this.modR = 1;
            this.modG = 1;
            this.modB = 1;
            this.vx = vx;
            this.vy = vy;
        }

        void update() {

            if (posX > mainFrame.getWidth() || posX < 0) {
                vx *= -1;
            }

            if (posY > mainFrame.getHeight() || posY < 0) {
                vy *= -1;
            }

            if (posX > mainFrame.getWidth()) {
                posX = mainFrame.getWidth();
            }

            if (posX < 0) {
                posX = 0;
            }

            if (posY > mainFrame.getHeight()) {
                posY = mainFrame.getHeight();
            }

            if (posY < 0) {
                posY = 0;
            }

            this.posX += vx;
            this.posY += vy;

        }

        public void paint(Graphics g) {
        	g.setFont(new Font("Didot", Font.PLAIN, this.size));

        	if (this.rNum + modR >= 256 || this.rNum + modR < 0) modR *= -1;
        	this.rNum += modR;
        	if (this.gNum + modG >= 256 || this.gNum + modG < 0) modG *= -1;
        	this.gNum += modG;
        	if (this.bNum + modB >= 256 || this.bNum + modB < 0) modB *= -1;
        	this.bNum += modB;

        	g.setColor(new Color(
                    this.rNum, // random color
                    this.gNum,
                    this.bNum
            )); //this.color
        	g.drawString(this.text, this.posX, this.posY);
        }

    }

}
