# Exceptions

In computing, exceptional behavior is a fact of life. This is true both for computer users, who are forever entering their age in a name field of an online form, and for programmers, who need to handle such unexpected behavior. Through its exception mechanism, Java provides substantial machinery for dealing with programs that behave in unexpected ways.

As we've emphasized repeatedly, Java's fundamental currency is the object, and so it shouldn't be surprising to learn that exceptional behavior too is captured using classes and objects. Thus the Exception class and its derived classes, such as `ArrayOutOfBoundsException`, `IOException`, and so forth, will be our primary focus as we look at Java's exception-handling machinery.

## Intro to Exceptions

We have seen examples of the exception mechanism already even though we haven't yet addressed it explicitly. For example, consider this simple program:

```java
1 import java.io.*;
2
3 public class Except0{
4
5   public static void main(String[] args) {
6     int k; int a = 3; ; int b = 0;
7     k = a/b;
8   }
9 }
```

Although program Except0 compiles, there is definitely a problem at runtime: on line 7 we're dividing by 0. Indeed, when we run the program, here's what Java says:

```
Exception in thread "main" java.lang.ArithmeticException: / by zero
 at Except0.main(Except0.java:7)
```

That is, Java reports an `ArithmeticException` on line 7, just where we would expect to find trouble. The Java library class `ArithmeticException`, which is derived from the class `Exception`, is designed to give precise information about the source and nature of the exceptional behavior we see.

If we anticipate trouble in a particular block of code, we can use the exception mechanism to trap for a specific kind of error, as we do below, in the program Except1.

```java
import java.io.*;

public class Except1 {

  public static void main(String[] args) {
    int k; int a = 3;  int b = 0;
    try {
      k = a/b;
      System.out.println("this statement will not execute");
    }
    catch(ArithmeticException e) {
      System.out.println("reached here directly from k=a/b: " + e);
    }
  }
}
```

Here we've explicitly indicated that we're looking for an `ArithmeticException`, and that's what Java reports. In the code above, we've wrapped a try-catch harness around the code we're interested in. That is, we've placed the code we suspect of exceptional behavior in a try block. If exceptional behavior happens, execution jumps immediately to the associated catch clause or clauses. And this is what happens in Except1. When division by 0 is encountered on the line that calculates a/b, the "will not execute" print statement is skipped over, and execution moves to the catch statement. That statement is looking for an `ArithmeticException` object ( to associate with the formal parameter named e), and that is what's delivered by the faulty division operation. As a result, this is what the program prints:

```
reached here directly from k=a/b: java.lang.ArithmeticException: / by zero
```

The first part of the line, up to the colon, is the text in quotes in the catch print statement; the second part of the line is generated from the e after the + and refers to the `ArithmeticException` object that's generated when the faulty division is applied. This object generates the message `"java.lang.ArithmeticException: / by zero"`.

The general form of the try/catch construction is:

```
try
{
	statements
}
catch(Exception1 e)
{
	action 1
}
catch(Exception2 e)
{
	action 2
}
...
catch(Exceptionn e)
{
	action n
}
```

This works as follows. We place code we wish to monitor for exceptional behavior in the try block. If there is exceptional behavior, execution jumps immediately to the first of the trailing catch alternatives. These alternatives are considered in order. If the kind of `Exception` named in a catch statement matches the exception encountered, that action and only that action is taken. (Note: an optional finally clause can also be included at the end of the catch clauses).

The code in the try block is typical code, and the statements in the block are executed in order, in the normal way, until an exception is encountered. Then try block processing abruptly ends, and execution shifts to the catch clause or clauses that trail the try block. Because of this abrupt shift in the way that statement execution is sequenced, we say that an exception is thrown, to be caught by an appropriate catch clause. We'll see shortly how to use the Java keyword throws explicitly.

Here is a another simple example.

```java
 1 public class Except2{
 2
 3   public static void main(String[] args){
 4     String s = "98.6";
 5     int n;
 6     try {
 7       n = Integer.parseInt(s);
 8       System.out.println(n*n);
 9     }
10     catch(Exception e)
11     {
12       e.printStackTrace();
13     }
14   }
15 }
```

Program Except2 starts with a String that represents a decimal number. It then attempts to copy this to an int variable using `Integer.parseInt`. The parseInt method accepts a String consisting of digits only, and so an `Exception` is caught, and this is the resulting display:

```
java.lang.NumberFormatException: For input string: "98.6"
 at java.lang.NumberFormatException.forInputString(NumberFormatException.java:48)
 at java.lang.Integer.parseInt(Integer.java:456)
 at java.lang.Integer.parseInt(Integer.java:497)
 at Except2.main(Except2.java:7)
```

The catch statement on line 10 is looking for the most general kind of `Exception`; however, because other, more specific kinds of exceptions, such as `NumberFormatExceptions` are also `Exceptions` (in the same way that a used car is a car), a catch clause that seeks to match `Exception` will in fact match an object from any of its subclasses as well. And that's the case here: Java has encountered a more specific `Exception` object, namely a `NumberFormatException` (since 98.6 has the wrong format) -- and this is what's reported.

## Exception Methods

Exception objects and their derived classes come with various useful methods. Thus there is a `getMessage()` method, since we want our programs to tell us, loud and clear, what's gone wrong. Another valuable method is `printStackTrace()`, which reports the chain of method calls that are triggered by the exceptional behavior. We see in the generated output above that the problem began on line 8 of main, where `Integer.parseInt `was called. Then another call was made to the `parseInt` method, followed by a call to the `forInputString` method in the `NumberFormatException` class. Finally we see the `NumberFormatException` message, which identifies the offending String.

Here is a more interesting try-catch construction.

```java
 1 import java.io.*;
 2 import java.util.*;
 3
 4 public class IntegerInput{
 5
 6   public static void main(String[] args){
 7     int n = -1;
 8     Scanner scan = new Scanner(System.in);
 9     while (n < 0) {
10       System.out.println("enter your age");
11       try {
12         if (scan.hasNextInt())
13           n = scan.nextInt();
14         else {  // non integer submitted
15           String userInput = scan.next();
16           throw new Exception("Bad input. "+ userInput +
17                               " is not an integer.  You must input an integer");
18         }
19       }
20       catch (Exception e)
21       {
22         System.out.println(e.getMessage());
23       }
24     }
25     System.out.println("next year you will be " + (n + 1));
26   }
27 }
```

Program IntegerInput uses a `Scanner` object to read in an integer value - your age - from the keyboard. The program then reports how old you will be next year. We want to make the input capture mechanism bullet-proof: we want to be sure that if you enter a bad input, the system doesn't crash, but instead prompts you to provide a proper value. On line 12 the program asks if the next item in the input stream contains another int using the `Scanner` method `hasNextInt`; if it does - if you enter your actual age - line 13 reads this input as an int and copies it to n. No exceptions are generated, and because the while test is now false, execution proceeds to line 25, where your age next year is reported.

On the other hand, if you enter a non-integer, say 17r, `scan.hasNextInt()` will be false. The else part of the conditional is therefore invoked, and `scan.next()` is executed on line 15. This means that the input is read in as a String, and the result is copied to userInput. An `Exception` is thrown on line 16. This means that execution jumps to another location, namely a catch location, and this target location is presented with the thrown `Exception` object. Because this throw happened inside a try block, execution jumps to the associated catch code beginning on line 20. There is only one `Exception` clause, and it prints the message associated with `Exception e`. This message is the argument to the `Exception` constructor call on line 16, and you will see printed to the console

```
Bad input. 17r is not an integer. You must input an integer
```

Then execution returns to the while control line. Since n remains at -1, the try block is entered again. In this way, the while loop executes repeatedly until you "get it right" by providing an integer.

Notice that an explicit `throws` clause is necessary here because line 15 can read anything (any String entry) without causing an error. Moreover we want to save the errant submission from line 15, so that we can use it as part of the message associated with the newly created `Exception` object. In fact, the `Exception` constructor we use on line 16 takes one argument, a String message involving the bad input, and this is what's reported on line 22.

This is an effective way to get input. However, if you indicate that you're age is -12, you've also provided bad input, but the program handles this very poorly. You won't get an error message, but - because n is still less than 0 - the while loop will continue, and will ask you again, without comment, to "enter your age". Program PositiveInput, below, does a better job of handling this situation.

```java
 1 import java.io.*;
 2 import java.util.*;
 3
 4 public class PositiveInput{
 5   public static void main(String[] args)
 6   {
 7     int n = -1;
 8     Scanner scan = new Scanner(System.in);
 9     while (n < 0) {
10       System.out.println("enter your age");
11       try {
12         if (scan.hasNextInt())
13           n = scan.nextInt();
14         else {  // non integer submitted
15           String userInput = scan.next();
16           throw new Exception("Bad input. "+ userInput +
17                               "is not an integer.  You must input an integer");
18         }
19         if (n < 0) throw new NegativeException();
20       }
21       catch (NegativeException e)
22       {        System.out.println("age must be >= 0");      }
23       catch (Exception e)
24       {        System.out.println(e.getMessage());      }
25     }   // end while
26     System.out.println("next year you will be " + (n + 1));
27   }
28 }
```

PositiveInput works as before unless a negative age is supplied. If this happens, a separate throw statement is invoked, at line 19: a new `NegativeException` object is created and thrown. The throw statement abruptly ends the try execution and control passes to the first catch statement, the `NegativeException` clause. There, on line 22, the statement

```
age must be >= 0
```

is printed to the console.

## Exception Inheritance

First of all, since Exception is a class, we can extend, or specialize it, to make a new kind of Exception object that fits our needs more precisely. We've done this as follows below:

```java
public class NegativeException extends Exception{

  public NegativeException() { };

  public  NegativeException(String msg) {
    super(msg);
  }
}
```

This simple class has two constructors, the second of which allows you to provide a message as a parameter. We use the first constructor on line 18 of PositiveInput. When we detect a negative input, the catch statements are immediately considered. Since the more specific NegativeException clause is encountered first, and since it matches the thrown exception, it will handle the case when a negative integer input is supplied. If the catch statements had been reversed, the more general

```java
catch(Exception e) {...}
```

would apply, and the more general "bad input" message would appear. It's always important to order catch statements from most specific to least specific so that the most precise exception-handling code can be invoked. Finally, notice also that with input -12, we will have read into variable n a negative integer input, and so after the user is scolded for providing a negative age, the while loop resumes, since with the new value of n, the while test is still true.

Let's look next at a file handling example. Suppose we are examining numerical data from a space probe (perhaps temperature data), and we want to calculate the average value of the numbers in the file. We assume for simplicity that there is one whole number per line. Because transmission from space often contains errors, we simply want to skip over entries that are non-numerical or non-integral. We do this using the NumberReader class below:

```java
 1 import java.io.*;
 2 import java.util.*;
 3
 4 public class NumberReader extends TextFileAccessor
 5 {
 6
 7   private int sum;
 8   private int count;
 9
10   public NumberReader(String f) throws IOException
11   {
12     super(f);
13     sum = 0;
14     count = 0;
15   }
16
17   public void processLine(String line)
18   {
19     try
20     {
21       int num = Integer.parseInt(line);
22       sum += num;
23       count++;
24     }
25     catch(Exception e)
26     {
27 	     System.out.println("Bad Entry: " + line);
28 	   }
29   }
30
31   public void printResult()
32   {
33     System.out.println((double)sum/count);
34   }
35 }
```

NumberReader is an extension of the TextFileAccesor class we saw in the Scrabble projects. Now, however, notice that processLine includes a try-catch construction. If a non-integer is encountered, it will show up on line 19, when we try to convert line into an integer. Under these circumstances we'll skip over the entry, and then report "Bad Entry: " along with the troublesome input as part of the catch statement. Program execution will not terminate, however: we will have issued a "bad input" report, but the full program will then simply proceed to the next line. On the other hand when the file entry value is a valid integer, we'll increment count and increase sum, in preparation for calculating the average. Notice that if we had not placed the body of processLine in a try-catch harness, and if a non-integer had been encountered, Java 's built-in exception machinery will take over, and the program will stop with a `NumberFormatException` thrown at the offending location in processLine.

## Unchecked vs Checked Exceptions

Line 10 raises an entirely new point with regard to Java and exception handling. Java distinguishes between two kinds of exceptions. An unchecked exception is an exception that happens because of logical errors on your part in program text, e.g., an array out of bounds error, a division by 0 error, and so forth. Sometimes we trap for these (as we've seen), but if we tried to account for all such possibilities our code would become completely unreadable.

Java also identifies checked exceptions. These are exceptions that you must handle. Frequently these are exceptions that involve interactions outside the standard scope of your the program text. For example, if you're reading from an external file, then you're in the province of IOExceptions, and these involve unusual behavior that can arise from your computer's file system, and not from Java per se. Thus, on line 10 of NumberReader, above, we throw an IOException as required, because the body of the constructor involves reading from an external file, via the super call, and IOExceptions are checked exceptions.

However we don't handle the exception here. By throwing the exception, we pass the exception on to a calling method, which here happens to be in a different class. The code below, for the application NumDriver, makes the call to the NumberReader constructor and prints the result.

```java
public class NumDriver{
  public static void main(String[] args){
    try {
      NumberReader num = new NumberReader("nums.txt");
      num.readLines();
      num.printResult();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
  }
}
```

Since the call to the NumberReader constructor in NumDriver takes place inside the try block, and since the associated catch statement catches any Exception, we've "handled" in particular any potential IOException that is thrown from inside the NumberReader constructor. This is so because any IOException is after all an Exception. Notice that the stack trace we print in the catch clause in NumDriver will give us ample information about the origin of any bug we encounter.

## Final Words

Why is Java's exception mechanism so complex? The answer is that Java has been designed with large, evolving programs in mind. Java applications in the real world often make use of thousands of classes, and are developed by large, shifting teams of software engineers. In such settings, having a comprehensive, systematic way of tracking down bugs and making user interactions bullet-proof is not an optional side feature of the language. It's an absolutely central part of object-oriented software development.
