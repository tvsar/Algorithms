import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class MyBinarySearchTree implements TreeSearchAlgorithm{
   int len;
   int maxSize;
   edu.gwu.algtest.TreeNode root;
   java.lang.Comparable[] keys;
   java.lang.Object[] vals;
   
   public java.lang.Object insert(java.lang.Comparable key, java.lang.Object value){
      
      edu.gwu.algtest.TreeNode T = new edu.gwu.algtest.TreeNode(key, value); 
      
      if(len == 0){ //If no values, insert at root
         root = T;
         keys[len] = key;
         vals[len] = value;
         len++;
         return null;
      }
      else if(search(key) != null){ //If exists in the tree, update value
         TreeNode k = keyFinder(key, root);
         Object o = k.value;
         k.value = value;
         return o;
      }
      else{ //Otherwise, search and update value with pointers
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
      return null;
   }

   public edu.gwu.algtest.ComparableKeyValuePair search(java.lang.Comparable key){
      ComparableKeyValuePair k = keyFinder(key, root); //Call Recursive helper function
      
      return k;
   }
   
   public edu.gwu.algtest.TreeNode keyFinder(java.lang.Comparable SearchKey, edu.gwu.algtest.TreeNode curr){
      if(SearchKey == null){ //Base cases
         return null;
      }
      if(curr == null){
         return null;
      }         
      if(curr.key.equals(SearchKey)){
         return curr;
      }
      else if(curr.key.compareTo(SearchKey) < 0){ //Search Right
         if(curr.right == null){
            return null;
         } 
         else{
            return keyFinder(SearchKey, curr.right);
         }
      }
      else if(curr.key.compareTo(SearchKey) > 0){ //Search Left
         if(curr.left == null){
            return null;
         } 
         else{
            return keyFinder(SearchKey, curr.left);
         }
      }
      return null;
   }
   
   public edu.gwu.algtest.TreeNode ParentFinder(java.lang.Comparable SearchKey, edu.gwu.algtest.TreeNode curr){
      if(curr.key.compareTo(SearchKey) < 0){ //Search Right Recursively
         if(curr.right == null){
            return curr;
         } 
         else{
            return ParentFinder(SearchKey, curr.right);
         }
      }
      else if(curr.key.compareTo(SearchKey) > 0){ //Search Left Recursively
         if(curr.left == null){
            return curr;
         } 
         else{
            return ParentFinder(SearchKey, curr.left);
         }
      }
      return null;
   }

   public edu.gwu.algtest.ComparableKeyValuePair minimum(){
      edu.gwu.algtest.TreeNode pointer = root; //Keep going left until min is found
      while(pointer.left != null)
         pointer = pointer.left;
      return pointer;
   }

   public edu.gwu.algtest.ComparableKeyValuePair maximum(){
      edu.gwu.algtest.TreeNode pointer = root; //Keep going right until max is found
      while(pointer.right != null)
         pointer = pointer.right;
      return pointer;
   }
   
   public boolean isARightChild(edu.gwu.algtest.TreeNode T){
      if(T.parent == null){
         return false;
      }
      else if(T.key.compareTo(T.parent.key) > 0){ //Compare with parent value
         return true;
      }
      else{
         return false;
      }
   }
   
   public boolean isALeftChild(edu.gwu.algtest.TreeNode T){
      if(T.parent == null){
         return false;
      }
      else if(T.key.compareTo(T.parent.key) > 0){ //Compare with parent value
         return false;
      }
      else{
         return true;
      }
   }
   
   public java.lang.Object delete(java.lang.Comparable key){
      edu.gwu.algtest.TreeNode target = keyFinder(key, root);
      if(target == null){ //Catch exception
         return null;
      }
      
      for(int i = 0; i < len; i++){ //Remove node to be deleted from keys and vals
         if(keys[i].equals(target.key)){
            keys[i] = keys[len-1];
            vals[i] = vals[len-1];
            keys[len - 1] = null;
            vals[len - 1] = null;
            break;
         }
      }
      len--;
      
      if(target.left == null || target.right == null){ // CASES 1 AND 2 (1 or 0 children)
         deleteCase12(target, key);
      }
      else{ //CASE 3 (2 children)
         Comparable c = successor(key);
         edu.gwu.algtest.TreeNode suc = keyFinder(c, root);   
         deleteCase12(suc, c);
         if(target.parent != null)
            if(isALeftChild(target)){
               target.parent.left = suc;
            }
            else{
               target.parent.right = suc;
            }
         if(target.equals(root)){
            root = suc;
         }
         if(target.left != null){
            suc.left = target.left;
            target.left.parent = suc;
         }
         if(target.right != null){
            suc.right = target.right;
            target.right.parent = suc;
         }
         suc.parent = target.parent;    
      }
      return target.value;
   }
   
   public java.lang.Object deleteCase12(edu.gwu.algtest.TreeNode target, java.lang.Comparable key){
      if(target.left == null && target.right == null){ //CASE 1
         if(target.equals(root)){
            root = null;
         }
         else{
            if(isALeftChild(target)){
               target.parent.left = null;
            }
            else{
               target.parent.right = null;
            }
         }
         return target.value;
      }
      else if(target.left == null && target.right != null){ //CASE 2
         if(target.equals(root))
            root = target.right;
         else{
            target.parent.right = target.right;
            target.right.parent = target.parent;
         }
         return target.value;
      }
      else if(target.left != null && target.right == null){
         if(target.equals(root))
            root = target.left;
         else{
            target.parent.left = target.left;
            target.left.parent = target.parent;
         }
         return target.value;
      }
      return null;
   }
   
   public java.lang.Comparable successor(java.lang.Comparable key){
      if(key.equals(root.key) && root.right == null) 
         return null;
      
      edu.gwu.algtest.TreeNode pointer = keyFinder(key, root);
      edu.gwu.algtest.TreeNode node = pointer;
      
      if(pointer == null)
         return null;
      
      if(pointer.right != null){ //If right child exists, search until successor found
         while(pointer.right != null){
            pointer = pointer.right;
            if(pointer.left != null){
               while(pointer.left != null){
                  pointer = pointer.left; 
               }   
               return pointer.key;
            }
         }
         return node.right.key;        
      }
      else{ //Otherwise search for turn
         while(pointer.parent != null){
            if(isALeftChild(pointer)){
               return pointer.parent.key;
            }
            pointer = pointer.parent;
         }      
      }
      return null;
   }

   public java.lang.Comparable predecessor(java.lang.Comparable key){
      return ParentFinder(key, root).key; //Call parent recursive helper function
   } 
   public edu.gwu.algtest.TreeNode getRoot(){
      return root;
   }
   
   public int getCurrentSize(){ 
      return len;
   }

   public void initialize(int max){
      len = 0;
      maxSize = max;
      keys = new java.lang.Comparable[max];
      vals = new java.lang.Object[max]; 
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
   
      return "Tharun Saravanan's Implementation of BinarySearchTree";
   }

   public void setPropertyExtractor(int algID, edu.gwu.util.PropertyExtractor prop){
      //Empty Implementation
   }  

   public static void main (String[] argv)
   {
      MyBinarySearchTree b = new MyBinarySearchTree();
      
      b.initialize(10);
      
      b.insert(5,10); //Insertion into a balanced tree
      b.insert(2,11);
      b.insert(8,12);
      b.insert(4,13);
      b.insert(1,14);
      b.insert(6,13);
      b.insert(10,14);
   
      //int key = 4; //Test Successor Capability
      //int key = 2;
      //int key = 1;
      //int key = 5;
      //int key = 10;
      int key = 5;
      //int key = 3;
      Comparable s = b.successor(key);
      //   System.out.println("Successor of " + key + " is : " + s);
   
      //       java.util.Enumeration k = b.getKeys(); //Test Enumerations before deletion
      //       while(k.hasMoreElements()){
      //          Comparable z = (Comparable) k.nextElement();
      //          System.out.println(z);
      //       }
      //       
      //       java.util.Enumeration l = b.getValues();
      //       while(l.hasMoreElements()){
      //          Comparable y = (Comparable) l.nextElement();
      //          System.out.println(y);
      //       }
      
      
      //       System.out.println(b.root); //Check Contents before Deletion
      //       System.out.println(b.root.left + "   " + b.root.right);
      //       System.out.println(b.root.left.left + "   " + b.root.left.right + "   " + b.root.right.left + "   " + b.root.right.right);
   
      b.delete(5); //Delete elements from the tree, check for exception
      b.delete(2);
      b.delete(8);
      b.delete(4);
      b.delete(1);
      b.delete(6);
      b.delete(10);
      b.delete(5); //Try deleting null element
      
      //       k = b.getKeys(); Test Enumerations after deletion
      //       while(k.hasMoreElements()){
      //          Comparable z = (Comparable) k.nextElement();
      //          System.out.println(z);
      //       }
      //       
      //       l = b.getValues();
      //       while(l.hasMoreElements()){
      //          Comparable y = (Comparable) l.nextElement();
      //          System.out.println(y);
      //       }
      
      //       System.out.println(b.root); //Check Contents after Deletion
      //       System.out.println(b.root.left + "   " + b.root.right);
      //       System.out.println(b.root.left.left + "   " + b.root.left.right + "   " + b.root.right.left + "   " + b.root.right.right);
   }
}