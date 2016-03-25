
public class SLLSparseM implements SparseM {
    private int nrows;
    private int ncols;
    private int numMatrixElem = 0;
    private RowHeadNode firstRow;
    
    
    public SLLSparseM(int nr,int nc){
    	if(nr <= 0) nr = 1;	// if zero or negative nr, set nr = 1;
		if(nc <= 0) nc = 1;	// if zero or negative nc, set nc = 1;	
		nrows = nr;	
		ncols = nc;
		firstRow = null;
    }
	
    @Override
	public int nrows() {
		// TODO Auto-generated method stub
		
		return nrows;
	}

	@Override
	public int ncols() {
		// TODO Auto-generated method stub
		return ncols;
	}

	@Override
	public int numElements() {
		// TODO Auto-generated method stub
		return numMatrixElem;
	}

	@Override
	public int getElement(int ridx, int cidx) {
		// TODO Auto-generated method stub
		if(numMatrixElem <=0)
			return 0;
		RowHeadNode rowit = firstRow;
		if(ridx < firstRow.getRowidx())
			return 0;
		while((rowit != null) && (rowit.getRowidx() <= ridx)){
			ElemNode n;
			if(rowit.getRowidx() == ridx){
				n = rowit.getFirst();
				while(n != null && n.getCidx() <= cidx){
					if(cidx == n.getCidx())
						return n.getValue();
					n = n.getNext();
				}
				
			}
			rowit = rowit.getNextRow();
		}
		return 0;
	}
	
	public RowHeadNode getfirstRow(){
		return firstRow;
	}
	

	@Override
	public void clearElement(int ridx, int cidx) {
		// TODO Auto-generated method stub
		if(outOfBounds(ridx,cidx))
        	return;
		if(numElements() == 0){
			return;
		}
		
		if(ridx < firstRow.getRowidx())
			return;
		
		RowHeadNode t = firstRow;
		RowHeadNode prev = firstRow;
		if(ridx == firstRow.getRowidx()){ //if what we are trying to clear is in the first row
			int d = firstRow.getNumRElem();
			firstRow.clearElement(cidx);
			if(d != firstRow.getNumRElem())//if the number of elements changed
				numMatrixElem--;
			if(firstRow.isEmpty()){//if row is now empty
				firstRow = firstRow.getNextRow();//set firstrow to the next row
				prev = firstRow;
			}
			return;
		}
		else{
		
		while(t !=null ){
			if(t.getRowidx() == ridx){//if the next ridx is equal to our ridx
				int a =t.getNumRElem();
				t.clearElement(cidx);//calls clear element function
				
			if(a != t.getNumRElem()){
				numMatrixElem--;				
				}
			
				if(t.isEmpty()){//if row we cleared element from is now empty
					prev.setNextR(t.getNextRow());//set row we are currently in to point to row after the empty row.
				}
				break;
			 }
			prev=t;
			t=t.getNextRow();
		  }
		return;
		
	    }
	 }

	@Override
	public void setElement(int ridx, int cidx, int val) {
		// TODO Auto-generated method stub
		
        if(outOfBounds(ridx,cidx))
        	return;
        else if(firstRow == null){ // if Matrix is empty
        	firstRow = new RowHeadNode(ridx);
        	firstRow.setNextR(null);
        	firstRow.addElement(ridx,cidx,val);
        	numMatrixElem++;
        }
        
        else if(ridx < firstRow.getRowidx()){// if row index is smaller than  our first row
         RowHeadNode smallestrow = new RowHeadNode(ridx);
    	 smallestrow.setNextR(firstRow);
    	 firstRow = smallestrow;
    	 smallestrow.addElement(ridx,cidx,val);
    	 numMatrixElem++;
        }
        
        else{
        	RowHeadNode t = firstRow;
        	while(t != null){
        		if(t.getRowidx() == ridx){ // if same row index
        			int a = t.getNumRElem();
        			t.addElement(ridx,cidx,val);// add element to row
        			int b = t.getNumRElem();
        			if(b != a){ // if number of elements changed add 1 to total number of elements
        				numMatrixElem++;
        			}
        			break;
        		}
        		else if(t.getNextRow() == null){//if we at the end of list
        		     RowHeadNode largestrow = new RowHeadNode(ridx);
        		     t.setNextR(largestrow);
        		     largestrow.setNextR(null);
        		     largestrow.addElement(ridx,cidx,val);
        			numMatrixElem++;
        			break;
        		}
        		else if(t.getNextRow().getRowidx() > ridx){//if the next row index is greater
        			RowHeadNode newRow = new RowHeadNode(ridx);
        			newRow.setNextR(t.getNextRow());
        			t.setNextR(newRow);
        			newRow.addElement(ridx,cidx,val);
        			numMatrixElem++;
        			break;
        		}
        		
        		t = t.getNextRow();
        		
            }
          }
        }
        
        
        
        
        
        
        
        public void getAllElements(int[] ridx, int[] cidx, int[] val) {
		// TODO Auto-generated method stub
		if(numMatrixElem == 0)
			return;
		RowHeadNode t = firstRow; // Set a row head node that will go through whole list 
		int c = 0;//counter for putting data into array arguments
		while(t != null){ 
			ElemNode t2 = t.getFirst();//get first node of row
			while(t2 != null){//while we are not at the the end of row
				ridx[c] = t.getRowidx();
				cidx[c] = t2.getCidx();
				val[c] = t2.getValue();
				c++;
				t2 = t2.getNext();//go to next element of row
			}
			t = t.getNextRow();//go to next row
		}
	}
		

	@Override
	public void addition(SparseM otherM) {
		// TODO Auto-generated method stub
		if(ncols != otherM.ncols() || nrows != otherM.nrows() ){//Checks if other matrix has same row and column parameters
			return;
		}
		if(firstRow == null){//if first matrix is empty
			RowHeadNode CurrHeadNodeV2 = ((SLLSparseM)otherM).getfirstRow();//gets first row of otherM
			RowHeadNode f = new RowHeadNode(CurrHeadNodeV2.getRowidx());//new head node that will be added to matrix 1
			firstRow = f;//make it the new first row
			f.setNextR(null);
		}
	   RowHeadNode CurrHeadNode = firstRow;
	   RowHeadNode prev = firstRow;
	   RowHeadNode CurrHeadNodeV2 = ((SLLSparseM)otherM).getfirstRow();//get first row of otherM
	   while(CurrHeadNodeV2 != null){//WHILE Otherm row is not equal to null
		   
		   if(CurrHeadNode == null){//if we hit null row in our Matrix
			   RowHeadNode R1 = prev;//go to the node before null in our matrix
			   ElemNode RT = CurrHeadNodeV2.getFirst();//this will iterate the otherM row
			   RowHeadNode R2 = new RowHeadNode(CurrHeadNodeV2.getRowidx());//make new row head that will be added to end of list
			   while(RT != null){
				   R2.addElements(RT.getRidx(), RT.getCidx(), RT.getValue());//adds element to row
				   numMatrixElem++;
				   RT = RT.getNext();//increment column of R2
				   
				}//while
			   R2.setNextR(null);
			   R1.setNextR(R2);//SETS prev next to our newly added in row
			   prev = prev.getNextRow();
			   CurrHeadNodeV2 = CurrHeadNodeV2.getNextRow();//increments otherM row
			   continue;
			}
		   else if(CurrHeadNode.getRowidx() > CurrHeadNodeV2.getRowidx()){//if row idx is greater than OtherM ridx
		   RowHeadNode newFirst = new RowHeadNode(CurrHeadNodeV2.getRowidx());//creates a new rowheadnode
		   if(CurrHeadNode == firstRow){//if we are try to add this row before our firstrow
		   newFirst.setNextR(firstRow);//set the new rows next to first
		   firstRow = newFirst;//make the row the our new firstrow
		   prev = firstRow;
		   }
		   else{//if we are trying to add it before a row that is not the first row
			   newFirst.setNextR(CurrHeadNode);//set our new rows next to current
			   prev.setNextR(newFirst);//set prev next to our new row
		   }
		   ElemNode RT = CurrHeadNodeV2.getFirst();
		   while(RT != null){//this loop will add the elements to our newly inserted row
			  newFirst.addElements(RT.getRidx(), RT.getCidx(), RT.getValue());
			  numMatrixElem++;
			  RT = RT.getNext();
		   }//while
		   CurrHeadNodeV2 = CurrHeadNodeV2.getNextRow();//increments otherM row
		   continue;
	    }
		   else if(CurrHeadNode.getRowidx() < CurrHeadNodeV2.getRowidx()){//if our matrix ridx is less than OtherM rix
		   prev = CurrHeadNode;
		   CurrHeadNode = CurrHeadNode.getNextRow();
		   continue;
	   }
		   else if(CurrHeadNode.getRowidx() == CurrHeadNodeV2.getRowidx()){//if the ridx are equal
		   ElemNode RT = CurrHeadNodeV2.getFirst();//get first elem of OtherM node
		   int a = CurrHeadNode.getNumRElem();
		   while(RT != null){//iterate through otherM row
			   CurrHeadNode.addElements(RT.getRidx(), RT.getCidx(), RT.getValue());
			   RT = RT.getNext();
		   }
		   int b = CurrHeadNode.getNumRElem();
		   numMatrixElem = (numMatrixElem + (b-a));//adds the number of elements that were added to the row
		    prev = CurrHeadNode;
		   	CurrHeadNode = CurrHeadNode.getNextRow();
		   	CurrHeadNodeV2 = CurrHeadNodeV2.getNextRow();
		   	continue;
	       }
	    }//while 
	  }
	

	
	public boolean outOfBounds(int ridx, int cidx){
		return((ridx < 0) || (ridx >= nrows) || (cidx < 0) || (cidx >= ncols));
	}

}
