import java.util.Arrays;

public class DirectedGraph {
    SLLSparseM dGraph;
    SLLSparseM dGraphin;
    int numofvert;
    int [] outdegree;
    int [] indegree;
	// constructor initialize an undirected graph, n is the number of nodes
	public DirectedGraph(int n){
		dGraph = new SLLSparseM(n,n); //sparse M for outnodes
		dGraphin = new SLLSparseM(n,n);//Sparse M for in nodes
		numofvert = n;
		outdegree = new int [n];//array to count out degrees
		indegree = new int [n];//array to count in degrees
		Arrays.fill(outdegree,0);
		Arrays.fill(indegree, 0);
	}

	// check if the given node id is out of bounds
	private boolean outOfBounds(int nidx1){
		return (nidx1 < 0 || nidx1 >= numofvert);
	}

	// set an edge (n1,n2)
	// beware of repeatingly setting a same edge and out-of-bound node ids
	public void setEdge(int n1, int n2){
		if(outOfBounds(n1) || outOfBounds(n2))// if node indexes are bad return
			return ;
		
      if((dGraph.getElement(n1, n2)) == 1){
			return;
	}
		dGraph.setElement(n1, n2, 1); //shows that there is an edge going from n1 to n2
		dGraphin.setElement(n2, n1, 1);// show the edges going into n2
		outdegree[n1]++;
		indegree[n2]++;
		
	}  

	
	// compute page rank after num_iters iterations
	// then print them in a monotonically decreasing order
	 void computePageRank(int num_iters){
		 if(num_iters < 0) return;
		 double rank[] = new double[numofvert]; // array that will hold the rank number
		 double prevrank[] = new double[numofvert];
		 int node[] = new int [numofvert];
		 Arrays.fill(rank, 0);
		 Arrays.fill(prevrank,1);
		 RowHeadNode prevrow = dGraphin.getfirstRow();
		 for(int i = 0; i < node.length;i++){//making node array
			 node[i] = i;
		 }
		 if(num_iters == 0){
			 Arrays.fill(rank,1);//return an array filled with ones
			 print(rank,node);
			 return;
		 }
		 for(int i = 0; i < num_iters;i++){
			 RowHeadNode currentrow = dGraphin.getfirstRow();
			 while (currentrow != null){
				 rank[currentrow.getRowidx()] = currentrow.getPR(prevrank,outdegree);//pagerank function
				 prevrow = currentrow;
				 currentrow = currentrow.getNextRow();//increment row
			 }
			 for(int c = 0; c < numofvert;c++)// put rank values into the prevrank array
				 prevrank[c] = rank[c];
		 }
		 
		 selectionSort(prevrank,node,numofvert);// sort arrays based on calculated pagerank
		 print(prevrank,node);
		 return;
	 }
	 
	 public void print(double [] prevr,int [] n){
		 for(int i = 0; i < numofvert;i++){
			 System.out.println("Node: " +n[i]+ " Rank: "+prevr[i]);
		 }
	 }
	     
	 
	 private static void selectionSort (double[] arrayR, int [] arrayN, int length) { 
		      for ( int i = 0; i < length - 1; i++ ) { 
		         int indexLowest = i; 
		         for ( int j = i + 1; j < length; j++ ) 
		            if ( arrayR[j] > arrayR[indexLowest] ) 
		               indexLowest = j;
		         if ( arrayR[indexLowest] != arrayR[i] ) { 
		            double temp = arrayR[indexLowest];
		            arrayR[indexLowest] = arrayR[i]; 
		            arrayR[i] = temp; 
		        
		            int tempN = arrayN[indexLowest];
		            arrayN[indexLowest] = arrayN[i];
		            arrayN[i] = tempN;	         
		         }  
		      }  
		  }
}
