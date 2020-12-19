/**

* @author katemccarthy

* 12/18/2020

* Interactive class is an interactive version of WordFloat in which user inputs are converted into floating words in the GUI.

*/

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.border.EmptyBorder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Interactive {
    public static void main(String[] args) {

      int numWords = 5; // number of words to be entered and displayed

    	Scanner console = new Scanner(System.in);
    	String input = console.next();
    	ArrayList<String> inputs = new ArrayList<String>();
    	inputs.add(input);

    	for (int i = 0; i < numWords; i++) {
    		inputs.add(console.next());
    	}

    	Program program = new Program();
        program.run(inputs);
    }
}

class Program {

    private JFrame mainFrame;
    private DrawPanel drawPanel;
    private java.util.List<Word> words;

    private int windowWidth = 640;
    private int windowHeight = 480;
    private String windowLabel = "Interactive Word Float";

    void run(ArrayList<String> inputs) {

    	words = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            Word word = new Word(
            		inputs.get(i),
                    (int) Math.floor(Math.random() * windowWidth),
                    (int) Math.floor(Math.random() * windowHeight),

                    (int) Math.floor(Math.random() * 20) + 10,

                    new Color(
                            (int) Math.floor((Math.random() * 256)),
                            (int) Math.floor((Math.random() * 256)),
                            (int) Math.floor((Math.random() * 256))
                    ),

                    (int) Math.floor((Math.random() * 8) - 4),
                    (int) Math.floor((Math.random() * 8) - 4)
            );

            words.add(word);
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

            this.setBackground(Color.black);

            for (Word w: words) {
                //w.draw(graphics);
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

        //public Word(int posX, int posY, int size, Color color, int vx, int vy) {
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
        	g.setFont(new Font("Dialog", Font.PLAIN, 40));
        	g.setColor(this.color);
        	g.drawString(this.text, this.posX, this.posY);
        }

    }

}
