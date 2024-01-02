import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;

public class CircleAlgorithm implements GraphDisplayAlgorithm{

   public edu.gwu.geometry.Pointd[] displayGraphContinuous(double[][] adjMatrix){
      edu.gwu.geometry.Pointd[] points = new edu.gwu.geometry.Pointd[adjMatrix.length];
      for(int i = 0; i < adjMatrix.length; i++){ //For each point in the matrix
         double theta = (2 * Math.PI * i) / adjMatrix.length; //Find the angle the point belongs to
         double x = 10 * Math.cos(theta); //Find X and Y of point
         double y = 10 * Math.sin(theta);
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(x, y); //Make point and add to points
         points[i] = point;
      }
      
      return points;
   }
   
   public java.util.LinkedList[][] displayGraphDiscrete(double[][] adjMatrix, int gridSize){
      return null; //Empty Implementation
   }
       
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of CircleAlgorithm";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
}