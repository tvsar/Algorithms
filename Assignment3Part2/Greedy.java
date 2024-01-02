import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class Greedy implements MTSPAlgorithm{

   public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
      int[][] Tours = new int[m][]; //Tracks all tours
      String[] div = new String[m]; //Stores all tours temorarily, strings used as there is no size limit when adding indices
      int[] currPoint = new int[m]; //Stores most recently added point to each tour
      
      if(m == 0) //Zero Case
         return Tours;
      
      java.util.LinkedList<Integer> pointsTBA = new java.util.LinkedList<Integer>(); //Tracks which points are yet to be added to a tour
      int len = points.length;
      for(int i = 0; i < points.length; i++) //Add all points to pool (no points have been added to a tour yet)
         pointsTBA.add(i);               
      
      int counter = 0; //Tracks which tour to add point to next
      while(pointsTBA.size() > 0){ //While there are points to add, add using greedy routine
         if(div[counter] == null){ //If no points in tour, simply add the first item in pool
            int head = pointsTBA.remove(); //Remove from pool of points
            div[counter] = head + " "; //Add to tour
            currPoint[counter] = head; //Set most recently added point of salesperson   
         }
         if(pointsTBA.size() > 0 && m < len){ //If points remain, use "greedy" approach, add the closest point to the most recently added point (in currPoint[])
            double dist = distance(points[counter], points[pointsTBA.peek()]);
            Integer closest = pointsTBA.peek();
            for(Integer I: pointsTBA){ //Parse through and find closest point to most recently added point
               double nextPtDist = distance(points[counter], points[I]);
               if(nextPtDist < dist){
                  dist = nextPtDist;
                  closest = I;
               }
            }
            pointsTBA.remove(closest); //Remove from pool of points
            div[counter] += closest + " ";   //Add to tour
            currPoint[counter] = closest; //Set most recently added point of salesperson   
         }       
         counter++; //Increment pointer of where to add next point
         if(counter >= div.length){ //Loop back to first salesperson
            counter = 0;
         }
      }
      //Convert all of the tours stored as strings back into an int array to be returned      
      for(int j = 0; j < div.length; j++){ 
         if(div[j] == null)
            continue;
         String[] StrDiv = div[j].split(" "); //Split by " " which was used to divide indices
         Tours[j] = new int[StrDiv.length]; //Now parse the strings back into the int array by casting
         for(int k = 0; k < StrDiv.length; k++){
            Tours[j][k] = Integer.parseInt(StrDiv[k]);
         }
      }
      
      return Tours; //Return all tours
   }
   
   double distance (edu.gwu.geometry.Pointd p1, edu.gwu.geometry.Pointd p2) //Computes distance (taken from Module 10)
   {
      return Math.sqrt ( sqr(p1.x - p2.x) + sqr (p1.y - p2.y) );
   }  
   
   double sqr (double a) //Computes square (taken from Module 10)
   {
      return a*a;
   }
       
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of Greedy Algorithm";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
   
   public static void main(String[] argv){
      //Use debugger to check values of tour
   
      Greedy d = new Greedy();
      edu.gwu.geometry.Pointd[] points = new edu.gwu.geometry.Pointd[7]; //Sporadically placed points
      edu.gwu.geometry.Pointd point1 = new edu.gwu.geometry.Pointd(0.0,0.0);
      edu.gwu.geometry.Pointd point2 = new edu.gwu.geometry.Pointd(3.0,0.0);
      edu.gwu.geometry.Pointd point3 = new edu.gwu.geometry.Pointd(3.0,4.0);
      edu.gwu.geometry.Pointd point4 = new edu.gwu.geometry.Pointd(20.0,0.0);
      edu.gwu.geometry.Pointd point5 = new edu.gwu.geometry.Pointd(28.0,0.0);
      edu.gwu.geometry.Pointd point6 = new edu.gwu.geometry.Pointd(28.0,6.0);
      edu.gwu.geometry.Pointd point7 = new edu.gwu.geometry.Pointd(28.0,3.0);
      points[0] = point1;
      points[1] = point2;
      points[2] = point3;
      points[3] = point4;
      points[4] = point5;
      points[5] = point6;
      points[6] = point7;
      d.computeTours(2, points);
   
      points = new edu.gwu.geometry.Pointd[15]; //Sporadically placed points
      point1 = new edu.gwu.geometry.Pointd(0.0,0.0);
      point2 = new edu.gwu.geometry.Pointd(2.0,0.0);
      point3 = new edu.gwu.geometry.Pointd(5.0,0.0);
      point4 = new edu.gwu.geometry.Pointd(8.0,0.0);
      point5 = new edu.gwu.geometry.Pointd(10.0,0.0);
      point6 = new edu.gwu.geometry.Pointd(4.0,4.0);
      point7 = new edu.gwu.geometry.Pointd(4.0,6.0);
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
      d.computeTours(3, points);
      d.computeTours(0, points); //Zero Salesmen
      
      points = new edu.gwu.geometry.Pointd[0]; //Null Tour
      d.computeTours(3,points);
      
      int size = 10;
      points = new edu.gwu.geometry.Pointd[size]; //Randomly Spaced Points
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(10 * i * Math.random(), 10 * i * Math.random());
         points[i] = point; 
      }
      d.computeTours(5, points);
   }
}