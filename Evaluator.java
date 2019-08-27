public class Evaluator {
    public static void main(String args[]) {
        System.out.println("Welcome to my expression evaluation program.");

        Parser p = new Parser();
        do {
            System.out.println("Please type an expression:");
            ExpTree myTree = p.parseLine();
            try {
                myTree.postOrder(); // Calls the post order function
            }
            catch (Exception e) {
                System.out.println("Unexpected error: " + e);
                break;
            }
            try {
                myTree.toSting(); // Calls the toString function
            }
            catch (Exception e) {
                System.out.println("Unexpected error: " + e);
                break;
            }
            try {
                myTree.evaluate();// Calls the evaluate function
            }
            catch (Exception e) {
                System.out.println("Unexpected error: " + e);
                break;
            }
            try {
                myTree.letTree();// Calls the let tree function
            }
            catch (Exception e) {
                System.out.println("Unexpected error: " + e);
                break;
            }
            System.out.println("Another expression (y/n)?"); // checks if the user wants to repeat the program
        }
        while (p.getLine().equals("y"));

    }
}
