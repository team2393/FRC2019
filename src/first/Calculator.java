package first;

import java.util.Scanner;

public class Calculator
{
    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        
        System.out.println(   "a + b = " + (a+b)     );
    }
}
