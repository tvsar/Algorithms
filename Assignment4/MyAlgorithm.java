//Consider for bonus point competition
import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;

public class MyAlgorithm implements GraphDisplayAlgorithm{

   public edu.gwu.geometry.Pointd[] displayGraphContinuous(double[][] adjMatrix){
      edu.gwu.geometry.Pointd[] points = new edu.gwu.geometry.Pointd[adjMatrix.length]; //Tracks point assignment
      
      int most_connected = findMostConnected(adjMatrix); //Find the point with the most edges
      edu.gwu.geometry.Pointd origin = new edu.gwu.geometry.Pointd(0, 0); //Place the point with the most edges at the center (origin)
      points[most_connected] = origin;
      
      String remComponents = ""; //Take the remaining components and add them to an array
      for(int i = 0; i < points.length; i++){
         if(points[i] == null)
            remComponents += i + " ";
      }
      
      String[] comps = remComponents.split(" ");
      int[] remPoints = new int[comps.length];
      for(int j = 0; j < comps.length; j++){
         if(comps[j].compareTo("") == 0 || comps[j] == null)
            continue;
         remPoints[j] = Integer.parseInt(comps[j]);
      }
      
      addConnectedCircle(points, remPoints, 100); //Create a circle with the remaining points
      
      for(int k = 0; k < 1000; k++){ //Attempt shifting remaining points 1000 times
         attemptShift(points, adjMatrix, most_connected);
      }
            
      return points;
   }
   
   //Take a random point and attempt shifting, update original if better total cost
   public void attemptShift(edu.gwu.geometry.Pointd[] points, double[][] adjMatrix, int origin){
      int a = (int)(Math.random()*(points.length));  
      double cost = computeTotalCost(points, adjMatrix);
      
      for(int i = 0; i < 10; i++){
            edu.gwu.geometry.Pointd p = new edu.gwu.geometry.Pointd();
            p.x = generatePosNeg() * Math.random() * 50;
            p.y = generatePosNeg() * Math.random() * 50;
            
            edu.gwu.geometry.Pointd temp = points[a];
            points[a] = p;
            
            double newCost = computeTotalCost(points, adjMatrix);
            if(newCost < cost){
               cost = newCost;
            }
            else{
               points[a] = temp;
            }
      }
   }
   
   //Compute total cost of a point assignment
   public int computeTotalCost(edu.gwu.geometry.Pointd[] points, double[][] adjMatrix){
      int count = 0;
      java.util.LinkedList<int[]> edges = new java.util.LinkedList(); //Add all edges to linkedlist
      for(int i = 0; i < adjMatrix.length; i++){
         for(int j = i; j < adjMatrix.length; j++){
            if(adjMatrix[i][j] == 1){
               int[] edge = {i, j};
               edges.add(edge);
            }
         }
      }
      
      //Compare all possible edges and count intersections
      for(int[] edge1: edges){
         for(int[] edge2: edges){
            if(!edge1.equals(edge2) && Geometry.lineSegmentIntersection(points[edge1[0]], points[edge1[1]], points[edge2[0]], points[edge2[1]]))
               count++;
         }
      } 
      
      //Return the count
      return count;
   }
   
   //Generates either -1 or 1
   int generatePosNeg(){
      if(Math.random() > 0.5)
         return 1;
      return -1;
   }
   
   //Creates a circle with specified points around the origin with specified radius
   public void addConnectedCircle(edu.gwu.geometry.Pointd[] points, int[] pointsTBA, int radius){ 
      for(int i = 0; i < pointsTBA.length; i++){
         double theta = (2 * Math.PI * i) / pointsTBA.length;
         double x = radius * Math.cos(theta);
         double y = radius * Math.sin(theta);
         edu.gwu.geometry.Pointd point = new edu.gwu.geometry.Pointd(x, y);
         points[pointsTBA[i]] = point;
      }
   }
   
   public int findMostConnected(double[][] adjMatrix){ //Find the most connected node
      int maxIndex = 0;
      int maxEdges = 0;
      
      for(int i = 0; i < adjMatrix.length; i++){
         int numEdges = countEdges(adjMatrix[i]);
         if(numEdges > maxEdges){
            maxIndex = i;
            maxEdges = numEdges;
         }
      }
      
      return maxIndex;
   }
   
   public int countEdges(double[] node){ //Count the number of edges a node has
      int count = 0;
      for(double edge: node)
         if(edge > 0)
            count++;
      return count;
   }

   public java.util.LinkedList[][] displayGraphDiscrete(double[][] adjMatrix, int gridSize){
      return null; //Empty Implementation
   }
       
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of MyAlgorithm";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   }
   
   public static void main (String[] argv){
      MyAlgorithm m = new MyAlgorithm();
      double[][] adjMatrix = {{0.0,1.0,1.0,1.0},
                              {1.0,0.0,1.0,1.0},
                              {1.0,1.0,0.0,1.0},
                              {1.0,1.0,1.0,0.0}};
                              
      m.displayGraphContinuous(adjMatrix);
   } 
}