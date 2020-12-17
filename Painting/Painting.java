/**

* @author katemccarthy

* 12/17/2020

* Painting class uses the Paintbrush class to paint with different colors on a canvas.

*/

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Painting extends JFrame {
   public static final int CANVAS_WIDTH = 1000;
   public static final int CANVAS_HEIGHT = 600;
   protected Color lineColor;
   private final ButtonGroup buttonGroup = new ButtonGroup();


   private List<Element> lines = new ArrayList<Element>();
   private Element currentLine;
   private List<Color> colors = new ArrayList<Color>();

   protected class ColorListener implements ActionListener{
	   public void actionPerformed(ActionEvent e) {
		   System.out.print("ActionEvent received: ");

		   Color color;
		   try {
		       java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(e.getActionCommand().toUpperCase());
		       color = (Color)field.get(null);
		   } catch (Exception e1) {
		       color = Color.GRAY;
		   }
		   setColor(color);
		}
   }

   public Painting() {
      DrawCanvas canvas = new DrawCanvas();
      canvas.setBackground(Color.BLACK);
      this.lineColor = Color.CYAN;
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      canvas.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent evt) {
            currentLine = new Element();
            lines.add(currentLine);
            colors.add(lineColor);
            currentLine.addPoint(evt.getX(), evt.getY());
         }
      });
      canvas.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent evt) {
            currentLine.addPoint(evt.getX(), evt.getY());
            repaint();  // paintComponent()
         }
      });

      setContentPane(canvas);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("Paint");
      pack();
      setVisible(true);

      ArrayList<String> colorButtons = new ArrayList<String>();
      colorButtons.add("Red");
      colorButtons.add("Orange");
      colorButtons.add("Yellow");
      colorButtons.add("Green");
      colorButtons.add("Cyan");
      colorButtons.add("Blue");
      colorButtons.add("Magenta");
      colorButtons.add("Pink");
      colorButtons.add("White");
      colorButtons.add("Gray");


      for (int i = 0; i < colorButtons.size(); i++) {
    	  	if (i == 0) {
    	  		addColorButton(colorButtons.get(i), true);
    	  	}
    	  	else addColorButton(colorButtons.get(i), false);
      }

   }

   private class DrawCanvas extends JPanel {
      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         for (int i = 0; i < lines.size(); i++) {
        	 	Color c = colors.get(i);
        	 	Element line = lines.get(i);
        	 	g.setColor(c);
        	 	line.draw(g);
         }
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new Painting();
         }
      });
   }

   public void setColor(Color c) {
		this.lineColor = c;
	}

   public void addColorButton(String label, boolean selected) {
	   JRadioButton button = new JRadioButton(label);
	   Color color;
	   try {
	       java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(label.toUpperCase());
	       color = (Color)field.get(null);
	   } catch (Exception e1) {
	       color = Color.GRAY;
	   }

	   if (selected) {
		   button.setSelected(true);
		   setColor(color);
	   }

	   button.setForeground(color);
	   buttonGroup.add(button);
	   button.setBounds(351, 87, 118, 23);
	   button.addActionListener(new ColorListener());
	   getContentPane().add(button);
   }

}
