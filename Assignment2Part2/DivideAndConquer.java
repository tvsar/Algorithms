import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class DivideAndConquer implements ClosestPairAlgorithm{
   java.util.HashMap<Integer, Integer> bandL = new java.util.HashMap<Integer, Integer>();

   public double findClosestPairDistance(edu.gwu.geometry.Pointd[] points){
      double mindist = findDistance(points[0], points[1]);
      
      SortablePointd[] pointsSortedByX = new SortablePointd [points.length];
      SortablePointd[] pointsSortedByY = new SortablePointd [points.length];
      int counter = 0;
      for(edu.gwu.geometry.Pointd p: points){
         pointsSortedByX[counter] = new SortablePointd(p, true);
         pointsSortedByY[counter] = new SortablePointd(p, false);
         counter++;
      }
      java.util.Arrays.sort(pointsSortedByX);
      java.util.Arrays.sort(pointsSortedByY);
   
      return recursiveClosestPair(pointsSortedByX, 0, pointsSortedByX.length-1, pointsSortedByY);
   }
   
   public double recursiveClosestPair (SortablePointd[] pX, int L, int R, SortablePointd[] pY){    
      //Base Cases
      if(L == R){ //If only 1 point, cannot search
         
      }
      else if(Math.abs(R - L) < 3){ //2-3 points, search the dataset
         return bruteForce(pY);
      }
      
      
      int median = (int) (L + R)/2; //Find median
      SortablePointd[] lyl = new SortablePointd[pY.length]; //Left Y List
      SortablePointd[] ryl = new SortablePointd[pY.length]; //Right Y list
      int lcounter = 0;
      int rcounter = 0;
      for(SortablePointd p: pY){ //Otherwise add to respective list
         if(p == null){
            break;
         }
         else if(p.x > pX[median].x){
            ryl[rcounter] = p;
            rcounter++;
         }
         else{
            lyl[lcounter] = p;
            lcounter++;
         }
      }
      double leftDist = findDistance(pX[0], pX[1]); //Recurse to check left half
      double rightDist = findDistance(pX[0], pX[1]); //Recurse to check right half
      
      if(lyl[0] != null){ //Don't make empty calls
         leftDist = recursiveClosestPair (pX, L, median, lyl);
      }
      if(ryl[0] != null){
         rightDist = recursiveClosestPair (pX, median+1, R, ryl); 
      }
      double minDist = Math.min(leftDist, rightDist); //Take min between both calls
      
            
      //Now use band
      double midX = pX[median].x;
      double midY = pX[median].y;
      SortablePointd[] band = new SortablePointd[pY.length];
      int count = 0;
      boolean newP = false;
      int pHash;
      for(SortablePointd p: pY)
         if(p == null)
            break;
         else{
            if((midY + 0.5 * minDist <= p.y || midY - 0.5 * minDist >= p.y) && (midX + minDist >= p.x || midX - minDist <= p.x )){ //Check if within valid points of strip
               band[count] = p; //Add to band
               count++;
               newP = true;
               pHash = p.hashCode();
               if(bandL.get(pHash) == null){ //Add to global band
                  bandL.put(p.hashCode(), 0);
                  newP = true;
               }
            }
         }
         
      if(!newP){ //If no new band points, return
         return minDist;
      }
      else{ //Otherwise scan 7 closest points within the band
         for(int i = 0; i < band.length; i++){
            for(int j = 1; j <= 7; j++){
               if(i + j >= band.length) //If we've reached the end, then break
                  break;
               else if(band[i+j] == null) 
                  break;
               minDist = Math.min(minDist, findDistance(band[i], band[i + j])); //Return min
            }
            if(band[i] == null) //If we've reached the end, then break
               break;
         }
      }
      return minDist;
   }
   
   public double bruteForce(SortablePointd[] points){
      double mindist = findDistance(points[0], points[1]);
      for(SortablePointd p: points){
         for(SortablePointd d: points){
            if(p == null){
               return mindist;
            }
            else if(d == null){
               break;
            }
            else{
               double min = findDistance(p, d);
               if(min < mindist && !p.equals(d)){
                  mindist = min;
               }
            }
         }
      }
      return mindist;
   }
   
   public double findDistance(SortablePointd one, SortablePointd two){
      return Math.sqrt(Math.pow(two.x-one.x, 2) + Math.pow(two.y-one.y, 2));
   }
   
   public double findDistance(edu.gwu.geometry.Pointd one, edu.gwu.geometry.Pointd two){
      return Math.sqrt(Math.pow(two.x-one.x, 2) + Math.pow(two.y-one.y, 2));
   }
   
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of DivideAndConquer";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
   
   public static void main (String[] argv)
   {
      DivideAndConquer d = new DivideAndConquer();
      NaiveClosestPair n = new NaiveClosestPair();
      int size = 10000;
      
      edu.gwu.geometry.Pointd[] points = new edu.gwu.geometry.Pointd[size]; //Points along a Diagonal
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(i, i);
         points[i] = point; 
      }
      System.out.println("DivideAndConquer Result: " + d.findClosestPairDistance(points)); //Distance across (x,x) to (x+1,x+1) = 1.41...
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
      System.out.println("DivideAndConquer Result: " + d.findClosestPairDistance(points)); 
      System.out.println("Naive Result: " + n.findClosestPairDistance(points));
      
      points = new edu.gwu.geometry.Pointd[size]; //Randomly Spaced Points
      for(int i = 0; i < size; i++){
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(10000 * Math.random(), 10000 * Math.random());
         points[i] = point; 
      }
      
      System.out.println("DivideAndConquer Result: " + d.findClosestPairDistance(points)); 
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
   
      System.out.println("DivideAndConquer Result: " + d.findClosestPairDistance(points)); 
      System.out.println("Naive Result: " + n.findClosestPairDistance(points));
   }
}