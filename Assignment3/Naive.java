import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class Naive implements MTSPAlgorithm{
   public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
      String[] div = new String[m]; //Stores all tours temorarily, strings used as there is no size limit when adding indices
      int[][] Tours = new int[m][]; //Tracks all tours
      
      if(m == 0) //Zero Case
         return Tours;
      
      int counter = 0; //Tracks which tour to add to next
      for(int i = 0; i < points.length; i++){ //Parse through all points
         if(div[counter] == null) //Initialize if not added to yet
            div[counter] = "";
         div[counter] += i + " "; //Add next point to tour
         counter++;
         if(counter >= div.length){ //If last salesperson reached, then return to first
            counter = 0;
         }
      }
      
      for(int j = 0; j < div.length; j++){ //Convert all of the string tours to int[] index tours through casting
         if(div[j] == null)
            continue;
         String[] StrDiv = div[j].split(" ");
         Tours[j] = new int[StrDiv.length];
         for(int k = 0; k < StrDiv.length; k++){
            Tours[j][k] = Integer.parseInt(StrDiv[k]);
         }
      }
      
      return Tours;
   }
       
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of Naive Algorithm";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
   
   public static void main (String[] argv)
   {
      //Use debugger to check values of tour
      
      Naive d = new Naive();
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