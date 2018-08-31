package first;

import java.util.Scanner;

public class Quadratic
{
    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Try a, b, c = 5, 6, 1.. ");

        
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        
        System.out.print("Enter b: ");
        double b = input.nextDouble();

        System.out.print("Enter c: ");
        double c = input.nextDouble();

        double D = b*b - 4*a*c;
        
        if (D < 0)
            System.out.println("No solution");
        else
        {
            System.out.println("   x = " + (-b + Math.sqrt(D)) / (2*a));
            System.out.println("or x = " + (-b - Math.sqrt(D)) / (2*a));
        }
    }
}
