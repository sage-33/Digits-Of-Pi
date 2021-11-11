## Programming Project: Digits of Pi

## Clone Project

Begin by cloning the provided project and importing it into your workspace.

`git clone <url for github repository>`

After cloning this repository you want to run these commands

`git checkout -b development`

This will create a branch named development and switch you to it. The development branch is where you will make all your commits.

## Import into Eclipse

You should then go to Eclipse, which hopefully has finished installing.

In the top toolbar, click File --> Import. Double-click on General and then `Projects from Folder or Archive`. Click `Directory` and find the folder inside the repository named `DigitsOfPi` and click Open. The project should pop up in the text box Projects. Click finish and you should be good to go!

## Complete Digits Of Pi Project

This programming assignment is about pi. You know: 3.14, and all that. You probably know someone who memorized some of its digits back in seventh grade.

Think about the people you know. There are the 3.14 people - just about everyone, there are the 3.14159, people, and finally there are the elite memorizers, who go to 3.14159265, and beyond. Pi is hard to memorize because its digits are seemingly random; this assignment takes a baby step toward assessing the distribution of the digits in pi's decimal representation (Click [here](https://www2.lbl.gov/Science-Articles/Archive/pi-random.html) for an accessible discussion of the nature of the digits of pi.)

I've included a link to a text file that holds the first 100,000 digits of pi: [corruptedDigitsOfPi.txt](DigitsOfPi/corruptedDigitsOfPi.txt)

Your job for this assignment is to read through that file and then build and display a table that reports the frequency of each of the digits 0 through 9. Your table should look like this:

```
> run PiDriver
0 9999
1 10138
2 9908
3 10026
4 9970
5 10027
6 10027
7 10025
8 9978
9 9902
bad symbols: 10214
```

If you run the code with [digitsOfPi.txt](DigitsOfPi/digitsOfPi.txt) you'll get:

```
> run PiDriver
0 9999
1 10138
2 9908
3 10026
4 9970
5 10027
6 10027
7 10025
8 9978
9 9902
bad symbols: 1
```

## Notes

The file is actually slightly corrupted - it contains some symbols besides digits (spaces, for example). Your job as you read through the file is to skip over these, but also report how many non-digits (bad symbols) there are in the file.

You'll use a similar algorithm to count the digits in a String as we did to count the letters in the Scrabble projects. You'll utilize the [ASCII codes](https://www.asciitable.com/). Here, the character are the digits '0' through '9'. Yes there is an `isDigit` method in the `Character` wrapper class.

You must have a two class solution, a driver called PiDriver, and a principal class called PiCounter. PiCounter should extend TextFileAccessor.

Inside PiDriver simply read in the text file directly. Don't use Scanner and a prompt to get the file. Just say something like this:

```java
PiCounter e = new PiCounter("digitsOfPi.txt");
```

You need to comment your methods according to this [Javadoc Guide](https://github.com/jd12/liferay-portal/blob/master/readme/ADVANCED_JAVADOC_GUIDELINES.markdown).
