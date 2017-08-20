import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
 
class Graph
{
    private int V;
    private LinkedList<Integer> adj[]; 
 public static ArrayList<Integer> arrr=new ArrayList<>();
static int tmp=0;
    Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }
    void addEdge(int v, int w)  { adj[v].add(w); }
    void DFSUtil(int v,boolean visited[])
    {
        visited[v] = true;
        tmp++;
 
        int n;
        Iterator<Integer> i =adj[v].iterator();
        while (i.hasNext())
        {
            n = i.next();
            if (!visited[n])
                DFSUtil(n,visited);
        }
    }
    Graph getTranspose()
    {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++)
        {
            Iterator<Integer> i =adj[v].listIterator();
            while(i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }
 
    void fillOrder(int v, boolean visited[], Stack stack)
    {
        visited[v] = true;
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext())
        {
            int n = i.next();
            if(!visited[n])
                fillOrder(n, visited, stack);
        }
        stack.push(new Integer(v));
    }
    void printSCCs()
    {
        Stack stack = new Stack();
        boolean visited[] = new boolean[V];
        for(int i = 0; i < V; i++)
            visited[i] = false;
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                fillOrder(i, visited, stack);
        Graph gr = getTranspose();
        for (int i = 0; i < V; i++)
            visited[i] = false;
        while (stack.empty() == false)
        {
            int v = (int)stack.pop();
            if (visited[v] == false)
            {
                gr.DFSUtil(v, visited);
                arrr.add(tmp);
		tmp=0;
            }
        }
    }
    public static void main(String args[]) throws IOException
    {
Scanner in= new Scanner(new File("D:\\Coursera  MAQ\\Algorithms-2\\NEW.txt"));
Graph g = new Graph(875714);
while(in.hasNextInt())
{
int a=in.nextInt();
int b=in.nextInt();
g.addEdge(a,b);
}
 
        System.out.println("Following are strongly connected components "+
                           "in given graph ");
        g.printSCCs();
Collections.sort(arrr);
System.out.println(arrr);
    }
}
