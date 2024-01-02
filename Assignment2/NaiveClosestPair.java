import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class NaiveClosestPair implements ClosestPairAlgorithm{
   public double findClosestPairDistance(edu.gwu.geometry.Pointd[] points){
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
   
   public double findDistance(edu.gwu.geometry.Pointd one, edu.gwu.geometry.Pointd two){
      return Math.sqrt(Math.pow(two.x-one.x, 2) + Math.pow(two.y-one.y, 2));
   }
   
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of NaiveClosestPair";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
   
   public static void main (String[] argv)
   {
      NaiveClosestPair n = new NaiveClosestPair();
      int size = 20;
      
      edu.gwu.geometry.Pointd[] points = new edu.gwu.geometry.Pointd[size]; //Points along a Diagonal
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(i, i);
         points[i] = point; 
      }
      System.out.println(n.findClosestPairDistance(points)); //Distance across (x,x) to (x+1,x+1) = 1.41...
      
      points = new edu.gwu.geometry.Pointd[4]; //Manually Placed Points
      edu.gwu.geometry.Pointd point1 = new edu.gwu.geometry.Pointd(1.0, 1.0); 
      points[0] = point1; 
      edu.gwu.geometry.Pointd point2 = new edu.gwu.geometry.Pointd(5.0, 1.0); 
      points[1] = point2; 
      edu.gwu.geometry.Pointd point3 = new edu.gwu.geometry.Pointd(5.0, 5.0); //Closest Pair 1
      points[2] = point3; 
      edu.gwu.geometry.Pointd point4 = new edu.gwu.geometry.Pointd(3.0, 3.0); //Closest Pair 2 (Distance 2.83...)
      points[3] = point4; 
      System.out.println(n.findClosestPairDistance(points));
      
      points = new edu.gwu.geometry.Pointd[size]; //Randomly Spaced Points
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(10 * i * Math.random(), 10 * i * Math.random());
         points[i] = point; 
      }
      System.out.println(n.findClosestPairDistance(points));
   }
}