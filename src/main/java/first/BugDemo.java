package first;

public class BugDemo
{
    public static void main(String[] args)
    {
        int positions[] = new int[] { 0, 1, 3, 5 };
        int steps = positions.length;
        
        for (int i=0; i<= steps; ++i)
        {
            System.out.print("Move to ");
            System.out.println(positions[i]);
        }
    }
}
