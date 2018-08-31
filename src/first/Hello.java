package first;

import java.util.Scanner;

public class Hello
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello!");
        System.out.print("What's your name? ");

        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        
        System.out.println("Nice to meet you, " + name);
    }
}
