# Reverse Polish Notation Expression Parser (Java Binary Tree)

## Platform Recommendation:
* This program has been run on Windows 10.0.15063 build 15063 and Mac OS X, developed using Java version 12.0.2, Intellij version 2018.3.5
Other systems have not been tested, and it is advised to have caution with untested OS.

## Note:
* This work was compiled in Intellij. To compile it for use on the same service, copy and paste all java files into an Intelij src folder, then add to the Java Library all files except RPN_Evaluator.jar, that is the executable file.

## To Start:
* Open a command line window and navigate to the folder holding the program's *.jar* file.
* Then type: java -jar RPN_Evaluator.jar, which should run the program.

## How to use the Program:
* Firstly, a welcome message will appear, indicating that the program has been initialized.
* Then, you will be prompted whether you want to enter the expression that you wish to convert to reverse polish notation format.  
**NOTE: You must input a semicolon at the end of the expression so that the parser can identify the end of the formula.**

* Once the processing of the expression is completed; the reverse polish notation will be output, the infix notation will be output & the result of the formula will be output.  
**NOTE: Double values will be rounded down (*i.e. 0.8965 = 0*).**

* Invalid formula notations will not be accepted and will output an exception, which requires the program to be resarted.
**NOTE: Upon the output of the diffrent notations, you will be prompted whether you wish to restart the program or terminate it.

* Once the game is completed you'll either be informed whether you have won, lost or drawn.

## Example Runtime
<pre>
C\...\RPN_Evaluator_jar>java -jar RPN_Evaluator.jar

Welcome to my expression evaluation program.

Please type an expression:
(5+5)+(8/2);

Post-order: 5 5 + 8 2 / +
In-order: 5 + 5 + 8 / 2
Evaluation result: 14

Another expression (y/n)?
y

Please type an expression:
5+5;

Post-order: 5 5 +
In-order: 5 + 5
Evaluation result: 10

Another expression (y/n)?
y

Please type an expression:
5*3+6*(6/2);

Post-order: 5 3 * 6 6 2 / * +
In-order: 5 * 3 + 6 * (6 / 2)
Evaluation result: 33

Another expression (y/n)?
n 
</pre>
