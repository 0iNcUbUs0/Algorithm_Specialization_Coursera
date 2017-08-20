import java.util.*;
public class MinHeap {
	private ArrayList<Integer> A = new ArrayList<Integer>();
	private int heapsize = 0;
	public MinHeap(){
		A.add(0); 
	}
	public int getSize(){
		return A.size() - 1;
	}
	
	private int Parent(int i){
		return i/2;
	}
	
	private int Left(int i){
		return 2*i;
	}
	
	private int Right(int i){
		return 2*i + 1;
	}
	
	public void minHeapify(int i){
		int l = Left(i);
		int r = Right(i);
		int smallest;
		if (l <= heapsize && A.get(l) < A.get(i)) {
			smallest = l;
		} else {
			smallest = i;
		}
		if(r <= heapsize && A.get(r) < A.get(smallest)){
			smallest = r;
		}
		if(smallest != i){
			swap(i, smallest);
			minHeapify(smallest);
		}
	}
	
	public void buildMinHeap(){
		heapsize = A.size() - 1;
		for(int i = (A.size() - 1)/2; i > 0; i--){
			minHeapify(i);
		}
	}
	
	public int getMin(){
		if(heapsize < 1){
			return Integer.MAX_VALUE;
		}
		return A.get(1);
	}
	
	public int extractMin(){
		if(heapsize < 1){
			System.out.println("heap underflow");
		}
		int min = A.get(1);
		A.set(1, A.get(heapsize));
		A.remove(heapsize);
		heapsize--;
		minHeapify(1);
		return min;
	}
	
	public void heapDecreaseKey(int i, int key){
		if(key > A.get(i)){
			System.out.println("new key is larger than current key;");
		}
		A.set(i, key);
		while(i > 1 && A.get(Parent(i)) > A.get(i)){
			swap(i, Parent(i));
			i = Parent(i);
		}
	}
	public void insert(int key){
		heapsize++;
		A.add(Integer.MAX_VALUE);
		heapDecreaseKey(heapsize, key);
	}
	private void swap(int i, int j){
		int tmp = A.get(i);
		A.set(i, A.get(j));
		A.set(j, tmp);
	}

		public String toString() {
			String minHeapString = "";
			for(int i = 1; i < A.size(); i++){
				minHeapString += A.get(i);
				minHeapString += " ";
			}
			return ("the min heap is: " + " {" + minHeapString + "}");
		}

}