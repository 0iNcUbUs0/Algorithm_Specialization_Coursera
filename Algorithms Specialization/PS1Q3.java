import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  
  
  
public class PS1Q3 {  
	static int tree[];
    static int[][] graph;  
    static int numNodes;    
    public static void primsSlow(){  
        int weight = 0;  
        boolean[] status = new boolean[numNodes];  
        for (boolean i : status)  
            i = false;  
        status[0] = true;  
  
        int minw = Integer.MAX_VALUE;  
        int mini =0 ;  
        int minj = 0;  
        while(!isComplete(status)){  
            for(int i =0;i<numNodes;i++){  
                if (status[i]) continue;  
                for(int j =0;j<numNodes;j++){  
                    if (!status[j]) continue;  
                    if (minw > graph[i][j]){  
                        minw = graph[i][j];  
                        mini=i;  
                        minj=j; 
                    }                     
                }  
            }  
            status[mini] = true;  
            tree[minj] = mini;  
            weight += graph[mini][minj];  
            minw = Integer.MAX_VALUE;  
        }  
        System.out.println("weight "+weight);  
  
    }  
  
    public static boolean isComplete(boolean[] status){  
        for (boolean b : status){  
            if (!b) return false;  
        }  
        return true;  
  
    }  
    public static void main(String[] args) {  
        try {  
            FileInputStream f = new FileInputStream("edges.txt");  
            DataInputStream d = new DataInputStream(f);  
            BufferedReader b = new BufferedReader(new InputStreamReader(f));  
            numNodes = Integer.parseInt(b.readLine().split(" ")[0]);  
            graph = new int[numNodes][numNodes];  
            for (int i =0;i<numNodes;i++)  
                for(int j=0; j < numNodes; j++)  
                    graph[i][j] =Integer.MAX_VALUE;  
            String str;  
            while((str=b.readLine())!=null){  
                int i = Integer.parseInt(str.split(" ")[0])-1;  
                int j = Integer.parseInt(str.split(" ")[1])-1;  
                graph[i][j] = Integer.parseInt(str.split(" ")[2]);  
                graph[j][i] = Integer.parseInt(str.split(" ")[2]);  
            }  
            tree = new int[numNodes];  
            for (int i : tree)  
                i=-1;  
            primsSlow();  
        } catch (NumberFormatException | IOException e) {  
            e.printStackTrace();  
        }  
  
    }  
} 