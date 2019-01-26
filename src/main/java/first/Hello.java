package first;

import java.util.Scanner;

/** A first and second example.
 *  Eclipse helps to create something like this.
 *  Right-click on the "src" folder in the Package Explorer,
 *  select New, Class,
 *  enter a name like "Hello" or "Welcome",
 *  check the option to create a stub for main().
 */
@SuppressWarnings("all")
public class Hello
{
    public static void main(String[] args) throws Exception
    {
        // As you type "System.", Eclipse will show a list of things to use
        // in "System". Select or type "out", press "." again,
        // and then select or type "println".
        // Enter the text you want to print in quotes.
        System.out.println("Hello!");
        
        // If you check carefully, this uses "print" instead of "println".
        // That means it will NOT move on to the next line but leave the
        // cursor after the "... name? " text in the console, where you
        // can then enter your name.
        System.out.print("What's your name? ");

        // When you type "System.in.", Eclipse will show that there's a "read()"
        // method that looks like it would be useful for reading things.
        // But it's a very bare-bones way of reading.
        // If you want to read a whole line of text, i.e. everything you type
        // until you press "Enter" in the console,
        // you wrap that System.in into a "Scanner" like this:
        Scanner input = new Scanner(System.in);
        
        // Now we can read the name that you entered in the console...
        String name = input.nextLine();
        
        // .. and print it with a message.
        System.out.println("Nice to meet you, " + name);
    }
}
