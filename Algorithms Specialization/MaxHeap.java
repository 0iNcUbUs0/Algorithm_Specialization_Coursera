import java.util.*;
public class MaxHeap {
	private ArrayList<Integer> A = new ArrayList<Integer>();
	private int heapsize = 0;
	public MaxHeap(){
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
	
	public void maxHeapify(int i){
		int l = Left(i);
		int r = Right(i);
		int largest;
		if (l <= heapsize && A.get(l) > A.get(i)) {
			largest = l;
		} else {
			largest = i;
		}
		if(r <= heapsize && A.get(r) > A.get(largest)){
			largest = r;
		}
		if(largest != i){
			swap(i, largest);
			maxHeapify(largest);
		}
	}
	
	public void buildMaxHeap(){
		heapsize = A.size() - 1;
		for(int i = (A.size() - 1)/2; i > 0; i--){
			maxHeapify(i);
		}
	}
	
	public int getMax(){
		if(heapsize < 1){
			return Integer.MIN_VALUE; // heap underflow
		}
		return A.get(1);
	}
	
	public int extractMax(){
		if(heapsize < 1){
			System.out.println("heap underflow");
		}
		int max = A.get(1);
		A.set(1, A.get(heapsize)); 
		A.remove(heapsize); 
		heapsize--;
		maxHeapify(1);
		return max;
	}
	
	public void heapIncreaseKey(int i, int key){
		if(key < A.get(i)){
			System.out.println("new key is smaller than current key;");
		}
		A.set(i, key);
		while(i > 1 && A.get(Parent(i)) < A.get(i)){
			swap(i, Parent(i));
			i = Parent(i);
		}
	}
	
	public void insert(int key){
		heapsize++;
		A.add(Integer.MIN_VALUE);
		heapIncreaseKey(heapsize, key);
	}
	
	private void swap(int i, int j){
		int tmp = A.get(i);
		A.set(i, A.get(j));
		A.set(j, tmp);
	}
	
		public String toString() {
			String maxHeapString = "";
			for(int i = 1; i < A.size(); i++){
				maxHeapString += A.get(i);
				maxHeapString += " ";
			}
			return ("the max heap is: " + " {" + maxHeapString + "}");
}
}