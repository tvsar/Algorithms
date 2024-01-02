class SortablePointd implements Comparable {
  // Constructor:
   edu.gwu.geometry.Pointd point;
   double x;
   double y;
   boolean isX;
  
   public SortablePointd (edu.gwu.geometry.Pointd p, boolean sortByX)
   {
      point = p;
      x = point.x;
      y = point.y;
      isX = sortByX;
   }

   public int compareTo (Object obj)
   {
      // ... Do the comparison based on whether it's by X or Y ...
      double comp;
      double objComp;
      if(isX){
         comp = point.x;
         objComp = ((SortablePointd) obj).getPoint().x;
      }
      else{
         comp = point.y;
         objComp = ((SortablePointd) obj).getPoint().y;
      }
      
      if(comp > objComp)
         return 1;
      else if(comp < objComp)
         return -1;
      else
         return 0;
   }
 
   public edu.gwu.geometry.Pointd getPoint(){
      return point;
   }
}