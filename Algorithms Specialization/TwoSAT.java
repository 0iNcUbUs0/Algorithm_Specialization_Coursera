import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class vertex {
	private char color;
	private long d;
	private long f;
	private int p;
	private int label;
	
	public vertex(char c, long d, long f, int p, int l) {
		this.color = c;
		this.d = d;
		this.f = f;
		this.p = p;
		this.label = l;
	}
	
	@Override
	public String toString() {
		return " " + color + " " + d + " " + f + " " + p + "\n";
	}

	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}

	public long getD() {
		return d;
	}

	public void setD(long d) {
		this.d = d;
	}

	public long getF() {
		return f;
	}

	public void setF(long f) {
		this.f = f;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}	
	
}

public class TwoSAT {
	private static Map<Integer,List<Integer>> edges;
	private static Map<Integer,List<Integer>> reverseEdges;
	private static Map<Integer,vertex> vertices;
	private static Map<Integer,vertex> vertices_copy;
	private static long time = 0L;
	private Set<Integer> set = new HashSet<Integer>();
	private boolean flag;
	private Set<Integer> processed;
	
	public void createGraph() throws Exception {
		String path = "D:\\Coursera  MAQ\\2sat6.txt";
		File f = new File(path);
		Scanner sc = new Scanner(f);
		sc.nextLine();
		String[] strs;
		String line, regex = "\\s";
		int label1, label2;
		
		processed = new HashSet<Integer>();
		vertices = new HashMap<Integer,vertex>();
		vertices_copy = new HashMap<Integer,vertex>();
		edges = new HashMap<Integer,List<Integer>>();
		reverseEdges = new HashMap<Integer,List<Integer>>();
		
		while(sc.hasNext()) {
			line = sc.nextLine();
			strs = line.split(regex);
			label1 = Integer.parseInt(strs[0]);
			label2 = Integer.parseInt(strs[1]);
			if(!processed.contains(label1)) {
				vertex v1 = new vertex('w', 0L, 0L, Integer.MIN_VALUE, label1);
				vertex v1_minus = new vertex('w', 0, 0, -Integer.MIN_VALUE, -label1);
				vertices.put(label1,v1);
				vertices.put(-label1,v1_minus);
				vertex v1_again = new vertex('w', 0L, 0L, Integer.MIN_VALUE, label1);
				vertex v1_minus_again = new vertex('w', 0L, 0L, Integer.MIN_VALUE, -label1);
				vertices_copy.put(label1, v1_again);
				vertices_copy.put(-label1,v1_minus_again);
				
				processed.add(label1);
				processed.add(-label1);
			}
			if(!processed.contains(label2)) {
				vertex v2 = new vertex('w', 0L, 0L, Integer.MIN_VALUE, label2);
				vertex v2_minus = new vertex('w', 0L, 0L, Integer.MIN_VALUE, -label2);
				vertices.put(label2,v2);
				vertices.put(-label2,v2_minus);
				vertex v2_again = new vertex('w', 0L, 0L, Integer.MIN_VALUE, label2);
				vertex v2_minus_again = new vertex('w', 0L, 0L, Integer.MIN_VALUE, -label2);
				vertices_copy.put(label2,v2_again);
				vertices_copy.put(-label2,v2_minus_again);
				
				processed.add(label2);
				processed.add(-label2);
			}
			if(!edges.keySet().contains(-label1)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(label2);
				edges.put(-label1, list);
			} else {
				edges.get(-label1).add(label2);
			}
			if(!edges.keySet().contains(-label2)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(label1);
				edges.put(-label2, list);
			} else {
				edges.get(-label2).add(label1);
			}
			
			if(!reverseEdges.keySet().contains(label2)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(-label1);
				reverseEdges.put(label2, list);
			} else {
				reverseEdges.get(label2).add(-label1);
			}
			if(!reverseEdges.keySet().contains(label1)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(-label2);
				reverseEdges.put(label1, list);
			} else {
				reverseEdges.get(label1).add(-label2);
			}
		}	
		
		sc.close();
	}
	

	public void DFS() {
		time = 0;
		for(int label : vertices_copy.keySet()) {
			vertex v = vertices_copy.get(label);
			if(v.getColor() == 'w')
				DFS_VISIT(v);
		}
	}
	
	public void DFS_VISIT(vertex v) {
		time = time+1;
		v.setD(time);
		v.setColor('g');
		
		int label = v.getLabel();
		if(reverseEdges.get(label) == null) {
			//do nothing
		} else {
			for(int neighbour : reverseEdges.get(label)) {
				if(vertices_copy.get(neighbour).getColor() == 'w') {
					vertices_copy.get(neighbour).setP(label);
					DFS_VISIT(vertices_copy.get(neighbour));
				}
			}
		}

		v.setColor('b');
		time = time + 1;
		v.setF(time);
	}

	public void SECOND_DFS_VISIT(vertex v) {
		if(!flag)
			return;
		time = time+1;
		v.setD(time);
		v.setColor('g');
		int label = v.getLabel();
		if(edges.get(label) == null) {
			//do nothing
		} else {
			for(int neighbour : edges.get(label)) {
				if(vertices.get(neighbour).getColor() == 'w') {
					vertices.get(neighbour).setP(label);
					SECOND_DFS_VISIT(vertices.get(neighbour));
				}
			}
		}

		v.setColor('b');
		time = time + 1;
		v.setF(time);
		if(!set.contains(-label))
			set.add(label);
		else
			flag = false;
	}
	
	public void checkValid() {
		long maxF;
		int maxLabel;
		time = 0;
		Map<Long,Integer> info = new HashMap<Long,Integer>();
		List<Long> Fs = new ArrayList<Long>(); 
		for(int label : vertices_copy.keySet()) {
			vertex v = vertices_copy.get(label);
			info.put(v.getF(),label);
			Fs.add(v.getF());
		}
		Collections.sort(Fs, Collections.reverseOrder());
		
		for(int i = 0; i < Fs.size(); i++) {
			maxF = Fs.get(i);
			maxLabel = info.get(maxF);
			if(vertices.get(maxLabel).getColor() == 'b') continue;
			else {
				set.clear();
				flag = true;
				vertex v = vertices.get(maxLabel);
				SECOND_DFS_VISIT(v);
				if(!flag) {
					System.out.println("This 2-sat instance is unsatisfiable!");
					return;
				}
			}
		}
		System.out.println("This 2-sat instance is satisfiable!");
	}
	
	public static void main(String[] args) throws Exception{
		TwoSAT twosat = new TwoSAT();
		twosat.createGraph();
		twosat.DFS();
		twosat.checkValid();
	}
}