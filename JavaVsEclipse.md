Java vs. Eclipse
================

We're using Eclipse to type the Java code, debug, and run it.
That's fine, but note that Eclipse is just a convenient development environment, it's not essential.
What _is_ essential is the `javac` compiler and the `java` runtime, which we could use from a basic command line.

Open command prompt, go to the project directory
------------------------------------------------
 * Start `cmd`
 * `cd git\2393First`
 
 
Edit one of the source files
----------------------------
You can use any text editor to edit the Java code:

 * `write src\first\Quadratic.java`

A generic text editor, however, won't understand the Java code.
It won't show syntax errors.
When you type "System.", it help you by suggesting `System.in`, `System.out`, ...
 
 
Compile & run it
----------------
In Eclipse, you just select "Run".

If you do that by hand, it actually involves two steps.
 
1. Compile the *.java into a *.class file:   
   `"\Program Files\Java\jdk1.8.0_171\bin\javac.exe" -d bin src\first\Primes.java`   
   
2. Execute the *.class file:  
   `java -cp bin first.Primes`   

IDE
---

Eclipse is an "Integrated Development Environment", IDE.
In addition to automatically compiling the source code and running it,
i.e. invoking `javac` and `java` for you, you get

 * An Editor that understands the source code, shows JavaDoc, errors, lists available classes, methods etc.
 * A Debugger that can single-step through the code
 * Support for GIT
 * Many more tools to handle the code: Rename a class and update all the source code that uses it,
   find all the places that call some piece of code etc.

In fact the downside of IDEs is that they do so __many__ things.
It's mindboggling, and you can get lost.

If you ask N people "Which is the best IDE", you'll get N different answers.
First robotics has been using Java for quite some time, but in 2019 that might change.
Don't worry: The Java code itself stays the same, and most IDEs offer similar benefits.
