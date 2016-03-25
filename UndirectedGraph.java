import java.util.Arrays;
import java.util.ArrayList;
public class UndirectedGraph {
	int [][] UndGraph;
	int numofedges;
	int numofnodes;
	ArrayList<Integer> lowerboundArray = new ArrayList<Integer>();
	// constructor initialize an undirected graph, n is the number of nodes
	public UndirectedGraph(int n){
		UndGraph = new int[n][n];
		for(int i = 0; i<n;i++){
		Arrays.fill(UndGraph[i],0);
		}
		numofnodes = n;
		numofedges = 0;ArrayList<Integer> lowerboundArray = new ArrayList<Integer>();
		

	}

	// check if the given node id is out of bounds
	private boolean outOfBounds(int nidx){
		return (nidx < 0 || nidx >= numofnodes);
	}

	// set an edge (n1,n2).
	// Since this is an undirected graph, (n2,n1) is also set to one
	public void setEdge(int n1, int n2){
		if(outOfBounds(n1) || outOfBounds(n2))
			return;
		if(UndGraph[n1][n2] == 0 ){
        UndGraph[n1][n2] = 1;
        UndGraph[n2][n1] = 1;
        numofedges++;
	 }
	}
	// print an output soft clique in one line
	 public void printClique(ArrayList<Integer> nlist){
	   for(int i = 0; i < nlist.size(); ++i)
	     System.out.print(nlist.get(i) + " ");
	   System.out.println("");
	 }

	 // compute maximal soft clique
	 // cliquesize_lower_bd: k
	 // num_missing_edges: l
	 public void findMaxSoftClique(int cliquesize_lower_bd, int num_missing_edges){
		 ArrayList<Integer> Node = new ArrayList();
		 ArrayList<Integer> Node1 = new ArrayList();
		 for(int i = 0;i < numofnodes;i++){
			 Node.add(i);
			 Node1.add(i);
		 }
		 ArrayList<ArrayList<Integer>> Subsets = PowerSet(Node);
		 ArrayList<ArrayList<Integer>>MxCliq = MaximalClique(Subsets,num_missing_edges,cliquesize_lower_bd,UndGraph);
		 for(int i = 0; i < MxCliq.size();i++){
			 printClique(MxCliq.get(i));
		 }
		 
		 
		 
	 }

	 // compute maximal soft clique by using recursion 
	 // to compute all (k,l) soft cliques using recursion
	 // you should check the partial subset during generation 
	 // rather than checking the whole subset
	 // cliquesize_lower_bd: k
	 // num_missing_edges: l
	 public void findMaxSoftCliqueAdvanced(int cliquesize_lower_bd, int num_missing_edges){
		 ArrayList<Integer> Node = new ArrayList();
		 for(int i = 0;i < numofnodes;i++){
			 Node.add(i);
		 }
		 
		 ArrayList<ArrayList<Integer>> Subsets = PowerSetadvanced(Node,num_missing_edges);
		 ArrayList<ArrayList<Integer>> klCliques = new ArrayList();
		 
		 selectionSort(Subsets);
		 for(int i = 0;i < Subsets.size();i++){
			 if(Subsets.get(i).size() <  cliquesize_lower_bd){
				 break;
			 }
			 if( !(isSubsetof(klCliques,Subsets.get(i)))){
			 klCliques.add(Subsets.get(i));
			 }
		 }
		 for(int i = 0; i < klCliques.size();i++){
			 printClique(klCliques.get(i));
		 }
	 }
	 
	 
	 
	 public ArrayList<ArrayList<Integer>> PowerSet(ArrayList<Integer> Node){
		 if(Node.size() == 0){ //if empty return
			 ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
			 empty.add(new ArrayList<Integer>());
			 return empty;
		 }
		 else{
			 int elt = Node.remove(0);//take out first element
			 Node.trimToSize();
			 ArrayList<ArrayList<Integer>> smaller = PowerSet(Node);//call on power set without first element
			 ArrayList<ArrayList<Integer>> smallerwithelt = new ArrayList<ArrayList<Integer>>();
			      
			 //this loop will take the subsets without elt and make a new subsets with elt added in
			       for(int i = 0; i < smaller.size();i++){
			    	  smallerwithelt.add(new ArrayList<Integer>());//make a new array list
			    	  smallerwithelt.get(i).add(elt);//add elt
			    	  for(int j = 0; j < smaller.get(i).size();j++){
			    			  smallerwithelt.get(i).add(smaller.get(i).get(j));
			    			  }
			      }
			       
			       //combines subsets with and without elt
			      for(ArrayList iter: smallerwithelt){ 
			    	  smaller.add(iter);
			      }
			      
			      selectionSort(smaller); //sort subsets by size
			      return smaller;
		  }//else
		 
	   }//Powerset
	 
	 public void selectionSort (ArrayList<ArrayList<Integer>> subsets) { 
	      for ( int i = 0; i < subsets.size() - 1; i++ ) { 
	         int indexLowest = i; 
	         for ( int j = i + 1; j < subsets.size(); j++ ) 
	            if ( subsets.get(j).size() > subsets.get(indexLowest).size() ) 
	               indexLowest = j;
	         if ( subsets.get(i).size() != subsets.get(indexLowest).size() ) { 
	            ArrayList<Integer> temp = subsets.get(i);
	            ArrayList<Integer> lowest = subsets.get(indexLowest);
	            subsets.set(i,lowest);
	            subsets.set(indexLowest, temp);
	         }  
	      }  
	  }
	 
    public ArrayList<ArrayList<Integer>> MaximalClique(ArrayList<ArrayList<Integer>> Subs,int missedges,int lowbound,int [][] UdGraph){
    	ArrayList<ArrayList<Integer>> Mxcliques = new ArrayList<ArrayList <Integer>>(); //max clique array
    	for(ArrayList<Integer> iter : Subs ){
    		if(iter.size() < lowbound){
    			break;
    		}
    		int missed = missingedges(iter,UdGraph); //find number of missing edges 
    		if(missed <= missedges &&(!isSubsetof(Mxcliques,iter))){ // if missing edges less than L and is not a subset of Max Clique
    			ArrayList<Integer> newly = new ArrayList<Integer>();
    			for(int i = 0; i < iter.size();i++){
    					newly.add(iter.get(i));
    			}
    			     Mxcliques.add(newly);
    			
    		}
    		
       }
    	
    	return Mxcliques;
    	
    	
    }
    
    
    /*couldn't figure out how to take out any subset of a softclique(k,l) using recursion
     * This method removes a subset once it cannot fufill the edges quota , never extending it further
     */
    public ArrayList<ArrayList<Integer>> PowerSetadvanced(ArrayList<Integer> Node,int num_of_missing_egdes){
   	 if(Node.size() == 0){ //if empty return
   		 
   		 ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
   		 empty.add(new ArrayList<Integer>());
   		 return empty;
   	 }
   	 else{
   		 int elt = Node.remove(0);//take out first element
   		 Node.trimToSize();
   		 ArrayList<ArrayList<Integer>> smaller = PowerSetadvanced(Node,num_of_missing_egdes);//call on power set without first element
   		 ArrayList<ArrayList<Integer>> smallerwithelt = new ArrayList<ArrayList<Integer>>();
   		      
   		 //this loop will take the subsets without elt and make a new subsets with elt added in
   		       for(int i = 0; i < smaller.size();i++){
   		    	  smallerwithelt.add(new ArrayList<Integer>());//make a new array list
   		    	  smallerwithelt.get(i).add(elt);//add elt
   		    	  for(int j = 0; j < smaller.get(i).size();j++){
   		    			  smallerwithelt.get(i).add(smaller.get(i).get(j));
   		    			 
   		    			  
   		    			  
   		    		 }
   		    	if(missingedges(smallerwithelt.get(i),UndGraph) > num_of_missing_egdes){ //remove partial subset that is not a clique
	    				  smallerwithelt.get(i).clear();
	    			  }
   		    	  
   		      }
   		       
   		       //combines subsets with and without elt
   		      for(ArrayList iter: smallerwithelt){ 
   		    	  smaller.add(iter);
   		      }
   		      
   		   
   		      return smaller;
   	  }//else
   	 
     }//Powerset
    
   public int missingedges(ArrayList<Integer> Sub,int [][] UdGraph){
	   int missedges = 0;// int to hold missing edges
	   for(int a : Sub){ // checking a subset
		   for(int i = 0; i < Sub.size();i++){ // while we are less than thats subset's length
			   if(UdGraph[a][Sub.get(i)] == 0 && (a != Sub.get(i))) // if there is a missing edge(0) and if index is not same
				   missedges++;
		  }	   
	   }
	   return (missedges/2);	
   }
   
   public boolean isSubsetof(ArrayList<ArrayList<Integer>> MCS, ArrayList<Integer> candidate){
	      if(MCS.size() <= 0){
	    	  return false;
	      }
          for(ArrayList<Integer> iter :MCS){// go through maximal clique array
        	  if(iter.containsAll(candidate)){//if any maximal cliques contain candidate
        		  return true;
        	  }
          }
          return false;
	   
   }
   
   
	 

}
