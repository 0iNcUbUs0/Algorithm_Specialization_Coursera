 import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Test
{
    public static void main(String[] args) throws IOException
    {
Scanner in= new Scanner(new File("inversion.txt"));

int arr[]=new int[100000];
int n=0;
        while(in.hasNextLine())
{
String art=in.nextLine();
art=art.trim();
		arr[n]=Integer.parseInt(art);
n++;
}
long inv_count=0;
for (int i = 0; i < n - 1; i++)
        for (int j = i+1; j < n; j++)
          if (arr[i] > arr[j])
            inv_count++;
        System.out.println(inv_count);
     
    }
}