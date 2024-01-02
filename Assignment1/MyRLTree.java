import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class MyRLTree implements TreeSearchAlgorithm{
   int len;
   int maxSize;
   edu.gwu.algtest.TreeNode root;
   java.lang.Comparable[] keys;
   java.lang.Object[] vals;
   
   boolean FLAG = false;

   public java.lang.Object insert(java.lang.Comparable key, java.lang.Object value){              
      edu.gwu.algtest.TreeNode T = new edu.gwu.algtest.TreeNode(key, value); //Insert via binary search first
   
      Object o = null;
      if(len == 0){
         UniformRandom gen = new UniformRandom();
         double u = gen.uniform();
         T.numericLabel = u;
         root = T;
         keys[len] = key;
         vals[len] = value;
         len++;
      }
      else if(search(key) != null){
         TreeNode k = keyFinder(key, root);
         o = k.value;
         k.value = value;
         return o;
      }
      else{
         UniformRandom gen = new UniformRandom();
         double u = gen.uniform();
         T.numericLabel = u;
         keys[len] = key;
         vals[len] = value;
         len++;         
         edu.gwu.algtest.TreeNode parent = ParentFinder(key, root);
         T.parent = parent;
         if(parent.key.compareTo(key) < 0){
            parent.right = T;
         }
         else{
            parent.left = T;
         }            
      }   
      
      sortIntoPlace(T); //After insertion, then sort into place        
            
      return null;
      
   }
   
   public void sortIntoPlace(edu.gwu.algtest.TreeNode T){
   
      if(T.parent == null || T.parent.numericLabel < T.numericLabel) //Base Case
         return;
         
      if(T.parent.numericLabel > T.numericLabel){ //Otherwise if heap not satisfied, rotate
         rotateUp(T);
      }
      sortIntoPlace(T); //Recursive call until base case
   }

   public void rotateUp(edu.gwu.algtest.TreeNode T){      
      if(isARightChild(T)){ //Rotate depending on orientation
         rotateLeft(T);
      }
      else{
         rotateRight(T);
      }  
         
   }

   public void rotateLeft(edu.gwu.algtest.TreeNode T){ //Rotate Left, updating pointers
      edu.gwu.algtest.TreeNode Temp = T.parent;
      if(T.left != null){
         T.left.parent = T.parent;
         T.parent.right = T.left;
      }
      else{
         T.parent.right = null;
      }
      T.parent = Temp.parent;
      T.left = Temp;
      
         
      if(Temp.equals(root)){
         root = T;
         Temp.parent = T;
      }
      else{
         if(isALeftChild(Temp)){
            Temp.parent = T;
            T.parent.left = T; 
         } 
         else{
            Temp.parent = T;
            T.parent.right = T; 
         }
      
      }
      
   }
   
   public void rotateRight(edu.gwu.algtest.TreeNode T){ //Rotate right, updating pointers
      edu.gwu.algtest.TreeNode Temp = T.parent;
      if(T.right != null){
         T.right.parent = T.parent;
         T.parent.left = T.right;
      }
      else{
         T.parent.left = null;
      }
      T.parent = T.parent.parent;
      T.right = Temp;
      
      if(Temp.equals(root)){
         root = T;
         Temp.parent = T;
      }
      else{
         if(isARightChild(Temp)){
            Temp.parent = T;
            T.parent.right = T;
         }
         else{
            Temp.parent = T;
            T.parent.left = T;  
         }
      }
   }

   public boolean isARightChild(edu.gwu.algtest.TreeNode T){ //Same as in Binary Search
      if(T.parent == null){
         return false;
      }
      else if(T.key.compareTo(T.parent.key) > 0){
         return true;
      }
      else{
         return false;
      }
   }
   
   public boolean isALeftChild(edu.gwu.algtest.TreeNode T){ //Same as in Binary Search
      if(T.parent == null){
         return false;
      }
      else if(T.key.compareTo(T.parent.key) > 0){
         return false;
      }
      else{
         return true;
      }
   }

   public edu.gwu.algtest.ComparableKeyValuePair search(java.lang.Comparable key){ //Same as in Binary Search
      ComparableKeyValuePair k = keyFinder(key, root); 
      
      return k;
   }
   
   public edu.gwu.algtest.TreeNode keyFinder(java.lang.Comparable SearchKey, edu.gwu.algtest.TreeNode curr){ //Same as in Binary Search
      if(SearchKey == null){
         return null;
      }      
      if(curr.key.equals(SearchKey)){
         return curr;
      }
      else if(curr.key.compareTo(SearchKey) < 0){
         if(curr.right == null){
            return null;
         } 
         else{
            return keyFinder(SearchKey, curr.right);
         }
      }
      else if(curr.key.compareTo(SearchKey) > 0){
         if(curr.left == null){
            return null;
         } 
         else{
            return keyFinder(SearchKey, curr.left);
         }
      }
      return null;
   }

   public edu.gwu.algtest.TreeNode ParentFinder(java.lang.Comparable SearchKey, edu.gwu.algtest.TreeNode curr){ //Same as in Binary Search
      if(curr.key.compareTo(SearchKey) < 0){
         if(curr.right == null){
            return curr;
         } 
         else{
            return ParentFinder(SearchKey, curr.right);
         }
      }
      else if(curr.key.compareTo(SearchKey) > 0){
         if(curr.left == null){
            return curr;
         } 
         else{
            return ParentFinder(SearchKey, curr.left);
         }
      }
      return null;
   }
 
   public edu.gwu.algtest.ComparableKeyValuePair minimum(){ //Same as in Binary Search
      edu.gwu.algtest.TreeNode pointer = root;
      while(pointer.left != null)
         pointer = pointer.left;
      return pointer;
   }

   public edu.gwu.algtest.ComparableKeyValuePair maximum(){ //Same as in Binary Search
      edu.gwu.algtest.TreeNode pointer = root;
      while(pointer.right != null)
         pointer = pointer.right;
      return pointer;
   }

   public java.lang.Object delete(java.lang.Comparable key){
      return null; //Empty Implementation
   }

   public java.lang.Comparable successor(java.lang.Comparable key){
      return null; //Empty Implementation
   }

   public java.lang.Comparable predecessor(java.lang.Comparable key){
      return null; //Empty Implementation
   }

   public edu.gwu.algtest.TreeNode getRoot(){
      return root;
   }
   
   public void initialize(int max){
      len = 0;
      maxSize = max;
      keys = new java.lang.Comparable[max];
      vals = new java.lang.Object[max];
   }

   public int getCurrentSize(){
      return len;
   }
   
   public java.util.Enumeration getKeys(){ 
      KeyIterator k = new KeyIterator(keys);
      return k.getEnumeration();
   }

   public java.util.Enumeration getValues(){ 
      ValIterator v = new ValIterator(vals);
      return v.getEnumeration();
   }
   
   public java.lang.String getName(){
   
      return "Tharun Saravanan's Implementation of RLSearchTree";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
   
   }  
   
   public static void main (String[] argv)
   {
      MyRLTree ex = new MyRLTree(); //Test example shown
      ex.initialize(5);
      ex.insert(1,0.7);
      ex.insert(3,0.3);
      ex.insert(5,0.2);
      ex.insert(7,0.8);
      ex.insert(6,0.1);
   
      MyRLTree b = new MyRLTree();
      
      b.initialize(10);
      b.insert("HW","1"); //Test Insertion of Nodes
      b.insert("NT","2");
      b.insert("IC","3");
      b.insert("PN","4");
      b.insert("XQ","5");
      b.insert("CQ","6");
      b.insert("BM","7");
      b.insert("IA","8");
      b.insert("SL","9");
      b.insert("LE","10");
      
      //       System.out.println(b.root); //Check node distribution
      //       System.out.println(b.root.left);
      //       System.out.println(b.root.right);   
      
      b.search("IA"); //Search for node in tree
   
      //       java.util.Enumeration k = b.getKeys(); //Test Enumeration
      //       while(k.hasMoreElements()){
      //          Comparable s = (Comparable) k.nextElement();
      //          System.out.println(s);
      //       }
   
   }
}