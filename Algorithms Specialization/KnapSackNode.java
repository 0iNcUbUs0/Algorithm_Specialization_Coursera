import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

class KnapSackNode {
	private int curVal;
	private double estVal;
	private int remainCap;
	private int state;
	private int index;
	
	public KnapSackNode(int curVal, double estVal, int remainCap, int state, int index) {
		this.curVal = curVal;
		this.estVal = estVal;
		this.remainCap = remainCap;
		this.state = state;
		this.index = index;
	}

	public int getCurVal() {
		return curVal;
	}

	public void setCurVal(int curVal) {
		this.curVal = curVal;
	}

	public double getEstVal() {
		return estVal;
	}

	public void setEstVal(double estVal) {
		this.estVal = estVal;
	}

	public int getRemainCap() {
		return remainCap;
	}

	public void setRemainCap(int remainCap) {
		this.remainCap = remainCap;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}

public class KnapSackBranchBoundDepthFirst {
	private Stack<KnapSackNode> stack;
	private double curOpt;
	private double[] ratios;
	private int[] indices;
	private double greedyOpt;
	private double relaxOpt;
	private int[] vals;
	private int[] weights;
	private int capacity;
	
	public KnapSackBranchBoundDepthFirst(int[] vals, int[] weights, int capacity) {
		this.vals = vals;
		this.weights = weights;
		this.capacity = capacity;
		init();
	}

	private void init() {
		this.stack = new Stack<KnapSackNode>();
		ratios = new double[vals.length];
		indices = new int[vals.length];
		for(int i = 0; i < vals.length; i++) {
			ratios[i] = (double)vals[i] / weights[i];
			indices[i] = i;
		}
		quickSort(0,ratios.length-1,ratios,indices);
		int i = 0;
		int tempCap = capacity;
		while(tempCap - weights[indices[i]] >= 0) { 
			relaxOpt += vals[indices[i]];
			tempCap -= weights[indices[i++]];
		}
		relaxOpt += (double)tempCap/weights[indices[i]] * vals[indices[i]];
		
		tempCap = capacity;
		for(i = 0; i < indices.length; i++) {
			if(tempCap - weights[indices[i]] >= 0) {
				greedyOpt += vals[indices[i]];
				tempCap -= weights[indices[i]];
			}
		}
	}
	
	public static void quickSort(int l, int r, double[] num, int[] index) {
		if(r <= l) return;
		double pivot = num[l];
		int i = l+1, j = l+1;
		double temp;
		for(int k = l+1; k <= r; k++) {
			if(num[k] < pivot) j++;
			else {
				temp = num[i];
				num[i] = num[j];
				num[j] = temp;
				temp = index[i];
				index[i] = index[j];
				index[j] = (int)temp;
				i++; j++;
			}
		}
		temp = num[l];
		num[l] = num[i-1];
		num[i-1] = temp;
		temp = index[l];
		index[l] = index[i-1];
		index[i-1] = (int)temp;
		
		quickSort(l, i-2, num, index);
		quickSort(i, r, num, index);
	}
	
	public double relax(int index, int curCap) {
		double relaxReuslt = 0;
		int i = 0;
		for(; i < weights.length; i++) {
			if(curCap - weights[indices[i]] >= 0 && indices[i] > index) {
				relaxReuslt += vals[indices[i]];
				curCap -= weights[indices[i]];
			} else if(curCap - weights[indices[i]] < 0) break;
			else if(indices[i] <= index) continue;
		}

		if(i == weights.length) return relaxReuslt;
		else return relaxReuslt += (double)curCap/weights[indices[i]] * vals[indices[i]];
	}
	
	public double solve() {
		curOpt = greedyOpt;
		
		KnapSackNode firstNode = new KnapSackNode(0, relaxOpt, capacity, 1, 0);
		stack.add(firstNode);
		KnapSackNode tempNode;
		int curVal, remainCap, state, index;
		double estVal;
		while(!stack.isEmpty()) {
			tempNode = stack.peek();
			if(tempNode.getState() == 1) {
				if(tempNode.getRemainCap() >= weights[tempNode.getIndex()]) {
					stack.peek().setState(2);
					index = tempNode.getIndex();
					curVal = tempNode.getCurVal() + vals[index];
					remainCap = tempNode.getRemainCap() - weights[index];
					estVal = curVal + relax(index, remainCap);
					if(estVal < curOpt) continue;
					state = 1;
					index = index+1;
					if(index == weights.length) {
						if(curVal > curOpt) curOpt = curVal;
						continue;
					}
					KnapSackNode node = new KnapSackNode(curVal, estVal, remainCap, state, index);
					stack.push(node);
				} else 
					tempNode.setState(2);
			} else {
				tempNode = stack.pop();
				index = tempNode.getIndex();
				curVal = tempNode.getCurVal();
				remainCap = tempNode.getRemainCap();
				estVal = curVal + relax(index, remainCap);		
				if(estVal < curOpt) continue;
				state = 1;
				index = index+1;
				if(index == weights.length) {
					if(curVal > curOpt) curOpt = curVal;
					continue;
				}
				KnapSackNode node = new KnapSackNode(curVal, estVal, remainCap, state, index);
				stack.push(node);
			}
		}
		return curOpt;
	}
	
	public static void testQuickSort() {
		double[] test = {2,2,2,1,1,1};
		int[] index = new int[test.length];
		for(int i = 0; i < index.length; i++) {
			index[i] = i;
		}
		quickSort(0, test.length-1, test, index);
		System.out.println(Arrays.toString(test));
	}

	public static void main(String[] args) throws Exception{
        List<String> lines = new ArrayList<String>();
        String filePath = "D:\\Coursera  MAQ\\knapsack_big.txt";

        BufferedReader input =  new BufferedReader(new FileReader(filePath));
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        String[] firstLine = lines.get(0).split("\\s+");
        int capacity = Integer.parseInt(firstLine[0]);
        int num_item = Integer.parseInt(firstLine[1]);

        int[] values = new int[num_item];
        int[] weights = new int[num_item];

        for(int i=1; i < num_item+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }
        
        KnapSackBranchBoundDepthFirst bbdf = new KnapSackBranchBoundDepthFirst(values, weights, capacity);
		long start = System.currentTimeMillis();
		System.out.println("the real optimal result: " + bbdf.solve());
		long end = System.currentTimeMillis();
		System.out.println("time consuming is " + (end-start));
	}
}