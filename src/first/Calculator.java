package first;

import java.util.Scanner;

public class Calculator
{
    public static void main(String[] args) throws Exception
    {
        // Wrap the basic System.in into a Scanner so we can read numbers
        Scanner input = new Scanner(System.in);
        
        // Print "Enter a: " WITHOUT then going to the next line
        System.out.print("Enter a: ");
        // Waits for you to type a number on the console and then press "Enter".
        double a = input.nextDouble();
        
        // Do the same for another number
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        
        // Show the sum
        // Note how we first compute (a + b) to get the sum,
        // i.e. we add two numbers.
        // Then we add the text "a + b = " to that number,
        // which gives a string like "a + b = 14",
        // and that's what we print.
        System.out.println(   "a + b = " + (a+b)     );
    }
}
