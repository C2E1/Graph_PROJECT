public class RowHeadNode{
	private int ridx;
	private int numElem;
	private ElemNode first;
	private RowHeadNode nextRow;
	
	public RowHeadNode(int row){
		ridx = row;
		numElem = 0;
		first = null;
	}
	
	public boolean isEmpty(){
		return numElem == 0;
	}
	
	
	public ElemNode getFirst(){
		return first;
	}
	public int getRowidx(){
		    return ridx;
	}
	public void setNextR(RowHeadNode nr){
		nextRow = nr;
	}
	
	public RowHeadNode getNextRow(){
		return nextRow;
	}
	public int getNumRElem(){
		return numElem;
	}
	
	public void clearElement(int c){
		if(first.getCidx() == c){//if the first element is the element we are trying to clear
			first = first.getNext(); //Change first to next element in row
			numElem--;
			return;
		}
		else {
		ElemNode t = first;
		ElemNode prev = first;
		while(t != null ){
			if(t.getCidx() == c){
				prev.setNext(t.getNext());;
				numElem--;
				return;
			}
			prev = t;
			t = t.getNext();
		 }
	   }
	}
	
	public void addElements(int r,int c ,int v){
        ElemNode n = new ElemNode(r,c,v); // creates new element node
        if(first == null){ //if row is empty
      	  first = n;
      	  first.setNext(null);
      	  numElem++;
        }
        else if(n.getCidx() < first.getCidx()){ //if cidx is less than the cidx of the first
      	  n.setNext(first);
      	  first = n;
      	  numElem++;
        }
        else{ // if row is non-empty and the new node is not less than first
      	  ElemNode t = first;
      	 
      	  while(t != null){ // while we are not at the end of the row
      		  
      	  
      		 if(t.getCidx() == n.getCidx()){ // if column indexes are equal
        		  int a = n.getValue() + t.getValue();
        		  t.setValue(a);
        		  break;
        	  }
      		 else if(t.getNext() == null){ //if next node is null
      			t.setNext(n);
      			n.setNext(null);
      			numElem++;
      			break;
      		}
      	  
      		else if(t.getNext().getCidx()  > n.getCidx()){ // if element after t has a greater column index than our new node
      		  n.setNext(t.getNext()); // putting n into list
      		  t.setNext(n);
      		  numElem++;
      		  break;
      	  }
      	  
      	  
      	  t = t.getNext(); // increment t to the next element in the row
       }
       
      }	  
        
	}	
	
	public void addElement(int r,int c ,int v){
          ElemNode n = new ElemNode(r,c,v); // creates new element node
          if(first == null){ //if row is empty
        	  first = n;
        	  first.setNext(null);
        	  numElem++;
          }
          else if(n.getCidx() < first.getCidx()){ //if cidx is less than the cidx of the first
        	  n.setNext(first);
        	  first = n;
        	  numElem++;
          }
          else{ // if row is non-empty and the new node is not less than first
        	  ElemNode t = first;
        	 
        	  while(t != null){ // while we are not at the end of the row
        		  
        	  
        		if(t.getCidx() == n.getCidx()){// if column indexes are equal
        		  t.setValue(n.getValue());
        		  break;
        	  }
        		
        		if(t.getNext() == null){ //if next node is null
        			t.setNext(n);
        			n.setNext(null);
        			numElem++;
        			break;
        		}
        	  
        		else if(t.getNext().getCidx()  > n.getCidx()){ // if element after t has a greater column index than our new node
        		  n.setNext(t.getNext()); // putting n into list
        		  t.setNext(n);
        		  numElem++;
        		  break;
        	  }
        	  
        	  
        	  t = t.getNext(); // increment t to the next element in the row
         }
         
        }	  
      }	
	
	//Computes Page rank of a certain node
	public double getPR(double [] prevRank,int [] outdegree){
		    double r = 0;
		    ElemNode t = first;
		    while(t != null){
		    	r += (double)(prevRank[t.getCidx()] / outdegree[t.getCidx()]);
		    	t = t.getNext();
		    }
		    return r;
	}
}