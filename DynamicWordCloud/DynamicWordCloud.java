/**

* @author katemccarthy

* 12/18/2020

* DynamicWordCloud generates a floating word graphic based on a text file.
* The font size of each word in the GUI correlates to its frequency within the text file.

*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class DynamicWordCloud {
    public static void main(String[] args) {
    	String fileName = "sampleText.txt";
    	Map<String, Integer> words = frequentWords(fileName);

    	Box box1 = new Box();
    	box1.run(words);
    }

  /**
   * @param fileName
   *
	 * @return TreeMap with all words in text file sorted by word frequency
	 *
	 * maps each word in file to the number of times it appears
	 */
	public static Map<String, Integer> frequentWords(String fileName) {
		Map<String, Integer> map = new TreeMap<String, Integer>();

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
				if (!repeatedWord) map.put(word, 1);
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

    void run(Map<String, Integer> inputs) {

    	words = new ArrayList<>();

    	for (Entry<String, Integer> e : inputs.entrySet()) {
        if (e.getValue() > 1) {
      		// ensure that the generated velocities are not zero (so all words are moving):
      		int xVelocity = (int) Math.floor((Math.random() * 4) - 2); // random X axis velocity
      		while (xVelocity == 0) xVelocity = (int) Math.floor((Math.random() * 4) - 2);
      		int yVelocity = (int) Math.floor((Math.random() * 4) - 2);  // random Y axis velocity
      		while (yVelocity == 0) yVelocity = (int) Math.floor((Math.random() * 4) - 2);

        	// construct a Word object for each String in the map
            Word word = new Word(
            		e.getKey(), // String

                    (int) Math.floor(Math.random() * windowWidth),  // random initial position on X axis
                    (int) Math.floor(Math.random() * windowHeight), // random initial position on Y axis

                    (e.getValue() * (int)Math.floor(Math.random() * 5) + 11), // font size, based on word frequency
                    //TODO: make font size relative using standard deviation

                    new Color(
                            (int) Math.floor((Math.random() * 256)), // random color
                            (int) Math.floor((Math.random() * 256)),
                            (int) Math.floor((Math.random() * 256))
                    ),

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
                Thread.sleep(10);
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

            for (Word w: words) {
            	w.paint(graphics);
            }

        }
    }

    class Word {
        private int posX, posY, size;
        private Color color;
        private String text;

        private int vx = 5;
        private int vy = 5;

        public Word(String text, int posX, int posY, int size, Color color, int vx, int vy) {
            this.text = text;
        	this.posX = posX;
            this.posY = posY;
            this.size = size;
            this.color = color;
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
        	g.setColor(this.color);
        	g.drawString(this.text, this.posX, this.posY);
        }

    }

}
