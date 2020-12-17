/**

* @author katemccarthy

* 12/17/2020

* Paintbrush class defines a line made up of multiple points when mouse is pressed and dragged.

*/

import java.awt.Graphics;
import java.util.*;

public class Paintbrush {
   private List<Integer> xs;
   private List<Integer> ys;

   public Paintbrush() {
      xs = new ArrayList<Integer>();
      ys = new ArrayList<Integer>();
   }

   //adds point to the line
   public void addPoint(int x, int y) {
      xs.add(x);
      ys.add(y);
   }

   public void draw(Graphics g) {
      for (int i = 0; i < xs.size() - 1; ++i) {
         g.drawLine((int)xs.get(i), (int)ys.get(i), (int)xs.get(i + 1),
               (int)ys.get(i + 1));
      }
   }
}
