public class ElemNode{
	private int ridx, cidx;
	private int value;
	private ElemNode next;
	public ElemNode(int r,int c,int v){
		ridx = r;
		cidx = c;
		value = v;
		
	}
	public int getRidx(){
		return ridx;
	}
	
	public void setNext(ElemNode nxt){
		next = nxt;
	}
	
	public int getCidx(){
		return cidx;
	}
	
	public ElemNode getNext(){
		return next;
	}
	
	public void setValue(int v){
		value = v;
	}
	
	public int getValue(){
		return value;
	}
}