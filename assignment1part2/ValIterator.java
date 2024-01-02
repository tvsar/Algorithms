import java.util.*;

class ValIterator implements Enumeration {

   java.lang.Object[] val;
   int index = 0;

   public ValIterator(java.lang.Object[] k){
      val = k;
   }

   public boolean hasMoreElements ()
   {
      if (index < val.length) {
         return true;
      }
      else {
         return false;
      }
   }
   

   public Object nextElement ()
   {
      java.lang.Object s = val[index];
      index ++;
      return s;
   }
   
   
   public Enumeration getEnumeration ()
   {
      index = 0;
      return this;
   }

}