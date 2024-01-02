import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class Annealing implements MTSPAlgorithm{
   
   public int[][] computeTours(int m, edu.gwu.geometry.Pointd[] points){
      
      //Use naive algorithm to distribute points first
      
      String[] div = new String[m]; //Stores all tours temorarily, strings used as there is no size limit when adding indices
      Naive n = new Naive();
      int[][] Tours = n.computeTours(m, points);
      
      if(m == 0) //Zero Case
         return Tours;
      
      //Now that initial tours have been set, proceed with simulated annealing
      
      // Initial temperature.
      double T = -1; 
      int numIterations = points.length; //Iterate points.length number of times
      int[] tour = {};
      int[] bestTour = {};
      boolean flag = true;
      for(int i = 0; i < m; i++){
         if(Tours[i] != null){
            if(Tours[i].length > 1){
               tour = Tours[i];   
               flag = true; //Tracks whether T needs to be set
               bestTour = new int[Tours[i].length];
               copy (tour, bestTour);  // Current tour is current best tour:
               double bestCost = computeCost (bestTour, points); //Current cost is current best cost
               int iteration = 0;
               while (iteration < numIterations) { // Start iterations.
                  int[] nextTour = computeNextState(tour); // Get next tour by calling computeNextState. Then,
                  double cost = computeCost(nextTour, points); // compute its cost.
                  double currCost = computeCost(tour, points); 
                  if(flag){ //Use first two tours to set initial T such that roughly 95% chance of acceptance initially
                     T = Math.abs((-(cost - currCost))) / Math.abs(Math.log(0.95));
                     flag = false;
                  }
                  if(cost < currCost){ // If cost is better, accept new tour and check whether its the best one so far.
                     tour = nextTour;
                     if(cost < bestCost){
                        bestCost = cost;
                        bestTour = nextTour;
                     }
                  }         
                  // Otherwise, see if we are going to accept higher-cost tour.
                  else if (expCoinFlip (tour, nextTour, T, points)) //Flip coin
                     tour = nextTour;
                  T = computeNewTemperature (T); // Get next temperature.
                  iteration ++; // Increment number of iterations.
               }
            }
         }
         //Finally set the final tour equal to the new tour
         Tours[i] = bestTour;
      } 
      
      return Tours;
   }
   
   //random next state
   int[] computeNextState (int[] tour) 
   {      
      //Find 2 indices to swap
      int x = (int)UniformRandom.uniform(0, tour.length-1); 
      int y = (int)UniformRandom.uniform(0, tour.length-1);
      
      while(x == y && tour.length > 1) //Repeat until unique indices (Ignore if only 1 point)
         y = (int) UniformRandom.uniform(0, tour.length-1);
     
      // Perform a random-swap
      int[] nextTour = new int[tour.length]; //Copy of tour
      for(int i = 0; i < tour.length; i++) 
         nextTour[i] = tour[i]; //Clone tour into nextTour
   
      swap(nextTour, x, y); //Swap values
      return nextTour;   
   }
   
   boolean expCoinFlip(int[] t1, int[] t2, double T, edu.gwu.geometry.Pointd[] pts){
      double p = Math.exp( -(computeCost(t2, pts) - computeCost(t1, pts)) / T);
      double u = UniformRandom.uniform(0.0, 1.0);
      //System.out.println(p + " " + u);
      //if(p > 10)
      //   System.out.print("");
      if(u < p)
         return true;
      else
         return false;
   }
   
   //Compute new temperature
   double computeNewTemperature(double T){ 
      return T * 0.99;
   }
   
   double computeCost (int[] tour, edu.gwu.geometry.Pointd[] points) //Computes tour cost
   {
      // Now, the cost.
      double tourCost = 0;
      for (int i=0; i<tour.length-1; i++) { //Tour sizes are not the same as points.length due to the division
      // Distance from i to i+1
         tourCost += distance (points[tour[i]], points[tour[i+1]]);
      }
      // Last one.
      tourCost += distance (points[tour[tour.length-1]], points[tour[0]]);
   
      return tourCost;
   }
   
   void copy (int[] tour1, int[] tour2) //Copies tour (taken from Module 10)
   {
      for (int i=0; i<tour1.length; i++)
         tour2[i] = tour1[i];
   }
  
   double distance (edu.gwu.geometry.Pointd p1, edu.gwu.geometry.Pointd p2) //Computes distance (taken from Module 10)
   {
      return Math.sqrt ( sqr(p1.x - p2.x) + sqr (p1.y - p2.y) );
   }  
   
   double sqr (double a) //Computes square (taken from Module 10)
   {
      return a*a;
   }
   
   void swap (int[] tour, int i, int j) //Swaps values (taken from Module 10)
   {
      int temp = tour[i];
      tour[i] = tour[j];
      tour[j] = temp;
   }
       
   public java.lang.String getName(){
      return "Tharun Saravanan's Implementation of Annealing Algorithm";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   } 
   
   public static void main(String[] argv){
      
      //Use debugger to check values of tour
      
      Annealing d = new Annealing();
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