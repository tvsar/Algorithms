import java.util.*;

class KeyIterator implements Enumeration {

   java.lang.Comparable[] key;
   int index = 0;

   public KeyIterator(java.lang.Comparable[] k){
      key = k;
   }

   public boolean hasMoreElements ()
   {
      if (index < key.length) {
         return true;
      }
      else {
         return false;
      }
   }
   

   public Object nextElement ()
   {
      java.lang.Comparable s = key[index];
      index++;
      return s;
   }
   
   
   public Enumeration getEnumeration ()
   {
      index = 0;
      return this;
   }

}