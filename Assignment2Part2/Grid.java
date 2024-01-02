import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class Grid implements ClosestPairAlgorithm{
   public double findClosestPairDistance(edu.gwu.geometry.Pointd[] points){
      double mindist = findDistance(points[0], points[1]);
      /////////////////////////////
      java.util.HashMap<Integer, String> compared = new java.util.HashMap<Integer, String>(); // Use to keep track of all comparisons done
      
      double minx = points[0].x; //First find the x and y ranges of the data (takes O(n) time)
      double maxx = points[0].x;
      double miny = points[0].y;
      double maxy = points[0].y;
      for(edu.gwu.geometry.Pointd p: points){
         if(p.x < minx)
            minx = p.x;
         else if(p.x > maxx)
            maxx = p.x;
         
         if(p.y < miny)
            miny = p.y;
         else if(p.y > maxy)
            maxy = p.y;
      }
      
      double maxxdist = maxx - minx;
      double maxydist = maxy - miny;
      int size = (int) Math.sqrt(points.length); //Divide into grid of size by size depending on root of no. points 
      //int size = 10;
      java.util.LinkedList<edu.gwu.geometry.Pointd>[][] grid = new java.util.LinkedList[size][size]; //Initialize matrix
      double xlen = maxxdist / size; //Find the space between grid cells
      double ylen = maxydist / size;
      
      for(edu.gwu.geometry.Pointd p: points){ //Now iterate through all points and add them to the matrix
         int c = (int) ((p.x - minx) / (xlen+1));
         int r = (int) ((p.y - miny) / (ylen+1));
         if(grid[r][c] == null)
            grid[r][c] = new java.util.LinkedList();
         grid[r][c].add(p);
      }
      
      for(int row = 0; row < grid.length; row++){ //Check all cells of the matrix
         for(int col = 0; col < grid.length; col++){
            if(grid[row][col] != null){ //If valid cell
               if(grid[row][col].size() > 1){ //If comparisons to be made
                  double selfmin = selfMin(grid[row][col], mindist); //Find min within the cell and update if smaller
                  if(selfmin < mindist)
                     mindist = selfmin;
               }
               int[] xshift = {-1,0,1}; //After checking within the cell, now check around the cell
               int[] yshift = {-1,0,1};
               for(int xs: xshift){ //Check all of the surrounding cells
                  for(int ys: yshift){
                     int comprow = row + ys;
                     int compcol = col + xs;
                     
                     int hash =  (comprow + "," + compcol + " " + row + "," + col).hashCode(); //Use hashtable to track which comparisons made
                     if(compared.get(hash) == null){ //If we haven't made this comparison before, continue                     
                        if(inRange(compcol, size) && inRange(comprow, size)){ //If this is a valid comparison, then continue
                           if(grid[comprow][compcol] != null && !(xs == 0 && ys ==0)){
                              double crossmin = twoCellMin(grid[row][col], grid[comprow][compcol]); //Find min between the cells 
                              if(crossmin < mindist){ //Update if new min
                                 mindist = crossmin;
                              }
                           }
                           int comparison =  (row + "," + col + " " + comprow + "," + compcol).hashCode();
                           compared.put(comparison, "True"); //Add comparison values to hashtable
                           compared.put(hash, "True");
                        }
                     }
                  }
               }
            }
         }
      }   
      return mindist;
   }
   
   public double findDistance(edu.gwu.geometry.Pointd one, edu.gwu.geometry.Pointd two){
      return Math.sqrt(Math.pow(two.x-one.x, 2) + Math.pow(two.y-one.y, 2));
   }
   
   public boolean inRange(int num, int len){
      return 0 <= num && num < len;
   }
   
   public double twoCellMin(java.util.LinkedList<edu.gwu.geometry.Pointd> a, java.util.LinkedList<edu.gwu.geometry.Pointd> b){
      double mindist = findDistance(a.peek(), b.peek());
      for(edu.gwu.geometry.Pointd p: a){
         for(edu.gwu.geometry.Pointd d: b){
            double min = findDistance(p, d);
            if(min < mindist && !p.equals(d)){
               mindist = min;
            }
         }
      }
      return mindist;
   }
   
   public double naiveFindClosestPairDistance(edu.gwu.geometry.Pointd[] points){
      double mindist = findDistance(points[0], points[1]);
      for(edu.gwu.geometry.Pointd p: points){
         for(edu.gwu.geometry.Pointd d: points){
            double min = findDistance(p, d);
            if(min < mindist && !p.equals(d)){
               mindist = min;
            }
         }
      }
      return mindist;
   }
   
   public double selfMin(java.util.LinkedList<edu.gwu.geometry.Pointd> points, double mindist){
      for(edu.gwu.geometry.Pointd p: points){
         for(edu.gwu.geometry.Pointd d: points){
            double min = findDistance(p, d);
            if(min < mindist && !p.equals(d)){
               mindist = min;
            }
         }
      }
      return mindist;
   }
   
   public java.util.LinkedList<Integer> findNeighbors(int x, int y, int size){
      return null;
   }
   
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of Grid";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
   
   public static void main (String[] argv)
   {
      Grid d = new Grid();
      NaiveClosestPair n = new NaiveClosestPair();
      int size = 10000;
      
      edu.gwu.geometry.Pointd[] points = new edu.gwu.geometry.Pointd[size]; //Points along a Diagonal
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(i, i);
         points[i] = point; 
      }
      System.out.println("Grid Result: " + d.findClosestPairDistance(points)); //Distance across (x,x) to (x+1,x+1) = 1.41...
      System.out.println("Naive Result: " + n.findClosestPairDistance(points));
         
      points = new edu.gwu.geometry.Pointd[4]; //Manually Placed Points
      edu.gwu.geometry.Pointd p1 = new edu.gwu.geometry.Pointd(1.0, 1.0); 
      points[0] = p1; 
      edu.gwu.geometry.Pointd p2 = new edu.gwu.geometry.Pointd(5.0, 1.0); 
      points[1] = p2; 
      edu.gwu.geometry.Pointd p3 = new edu.gwu.geometry.Pointd(5.0, 5.0); //Closest Pair 1
      points[2] = p3; 
      edu.gwu.geometry.Pointd p4 = new edu.gwu.geometry.Pointd(3.0, 3.0); //Closest Pair 2 (Distance 2.83...)
      points[3] = p4; 
      System.out.println("Grid Result: " + d.findClosestPairDistance(points)); 
      System.out.println("Naive Result: " + n.findClosestPairDistance(points));
      
      points = new edu.gwu.geometry.Pointd[size]; //Randomly Spaced Points
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(10000 * Math.random(), 10000 * Math.random());
         points[i] = point; 
      }
      
      System.out.println("Grid Result: " + d.findClosestPairDistance(points)); 
      System.out.println("Naive Result: " + n.findClosestPairDistance(points));
   
      points = new edu.gwu.geometry.Pointd[15]; //Sporadically placed points
      edu.gwu.geometry.Pointd point1 = new edu.gwu.geometry.Pointd(0.0,0.0);
      edu.gwu.geometry.Pointd point2 = new edu.gwu.geometry.Pointd(2.0,0.0);
      edu.gwu.geometry.Pointd point3 = new edu.gwu.geometry.Pointd(5.0,0.0);
      edu.gwu.geometry.Pointd point4 = new edu.gwu.geometry.Pointd(8.0,0.0);
      edu.gwu.geometry.Pointd point5 = new edu.gwu.geometry.Pointd(10.0,0.0);
      edu.gwu.geometry.Pointd point6 = new edu.gwu.geometry.Pointd(4.0,4.0);
      edu.gwu.geometry.Pointd point7 = new edu.gwu.geometry.Pointd(4.0,6.0);
      edu.gwu.geometry.Pointd point8 = new edu.gwu.geometry.Pointd(4.0,8.0);
      edu.gwu.geometry.Pointd point9 = new edu.gwu.geometry.Pointd(4.0,10.0);
      edu.gwu.geometry.Pointd point10 = new edu.gwu.geometry.Pointd(6.0,4.0);
      edu.gwu.geometry.Pointd point11 = new edu.gwu.geometry.Pointd(6.0,6.0);
      edu.gwu.geometry.Pointd point12 = new edu.gwu.geometry.Pointd(6.0,8.0);
      edu.gwu.geometry.Pointd point13 = new edu.gwu.geometry.Pointd(6.0,10.0);
      edu.gwu.geometry.Pointd point14 = new edu.gwu.geometry.Pointd(5.0,6.0);
      edu.gwu.geometry.Pointd point15 = new edu.gwu.geometry.Pointd(1.0,1.0);
      points[0] = point1;
      points[1] = point2;
      points[2] = point3;
      points[3] = point4;
      points[4] = point5;
      points[5] = point6;
      points[6] = point7;
      points[7] = point8;
      points[8] = point9;
      points[9] = point10;
      points[10] = point11;
      points[11] = point12;
      points[12] = point13;
      points[13] = point14;
      points[14] = point15;
   
      System.out.println("Grid Result: " + d.findClosestPairDistance(points)); 
      System.out.println("Naive Result: " + n.findClosestPairDistance(points));
   }
}