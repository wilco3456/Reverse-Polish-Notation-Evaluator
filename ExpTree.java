import java.util.HashMap;
import java.util.Map;

public class ExpTree {
    private ParseTree root;
    private boolean evalFlag = false; // Used to determine whether the part 4 function is running or the part 6 function
    private Map<String, Integer> map = new HashMap<>(); // Stores the identifier values for part 4
    private Map<String, Integer> map2 = new HashMap<>(); // Stores the identifier values for part 6

    public ExpTree(int kind, Object o) // ExpTree constructors
    {   root = new ParseTree(kind, o);
    }

    public ExpTree(int kind, Object o, ExpTree l, ExpTree r) // ExpTree constructors
    {   root = new ParseTree(kind, o, l.root, r.root);
    }

    public void postOrder() {
        if (root == null)
            System.out.println("Tree empty."); // If the tree is empty print tree empty
        if (root.getKind() == 3) {
            System.out.print("Post-order: ");
            System.out.println(postOrderFunct(root.getrChild())); // Calls the Post order function for the let tree
        }
        else {
            System.out.print("Post-order: ");
            System.out.println(postOrderFunct(root)); // Calls the Post order function for the operator tree
        }
    }

    public String postOrderFunct(ParseTree node){
        if (node.getlChild() != null)
            System.out.print(postOrderFunct(node.getlChild()) + " "); // Uses recursion to traverse the left nodes

        if (node.getrChild() != null)
            System.out.print(postOrderFunct(node.getrChild()) + " "); // Uses recursion to traverse the right nodes

        return node.getValue().toString(); // Returns the value stored in the node
    }

    public void toSting() {
        if (root == null)
            System.out.println("Tree empty."); // If the tree is empty print tree empty
        else {
            String s=inOrderFunct(root,false);
            System.out.println("In-order: "+s);// Calls the toString function for the let tree
        }
    }

    public String inOrderFunct(ParseTree node, boolean bracketFlag){
        String output = "";

        if (node.getlChild() != null) {
            if (node.getKind() == 2 && node.getlChild().getKind() == 2) // Checks if the current node and the left child are operators
                output += inOrderFunct(node.getlChild(),includeBracket(node));  // Calls the bracket function to check if brackets are needed
            else
                output += inOrderFunct(node.getlChild(),false); // Uses recursion the store the values of the left child into a string
        }

        if (node.getKind() == 3 || node.getKind() == 4 || node.getKind() == 5)
            output += " " + node.getValue().toString() + " "; // Adds spaces to the 'let', 'and' and 'equals' functions
        else
            output += node.getValue().toString(); // Stores the values of the current node into a string

        if (node.getrChild() != null) {
            if (node.getKind() == 2 && node.getrChild().getKind() == 2) // Checks if the current node and the right child are operators
                output += inOrderFunct(node.getrChild(),includeBracket(node)); // Calls the bracket function to check if brackets are needed
            else
                output += inOrderFunct(node.getrChild(), false); // Uses recursion the store the values of the right child into a string
        }

        if (bracketFlag){
            output = "(" + output + ")"; // Inserts parenthesis into the string if the current node value is an operator
        }

        return output;
    }

    public boolean includeBracket(ParseTree node){ // Determines whether to use brackets on the child, depending on the current node and the left or right child
        char leftN = node.getlChild().getValue().toString().charAt(0); // Left node value
        char rightN = node.getrChild().getValue().toString().charAt(0); // Right node value
        char currentN = node.getValue().toString().charAt(0); // Current node value

        if (currentN == '*' || currentN == '/' || currentN == '%' || currentN == '^') {
            if (leftN == '+' || rightN == '+' || leftN == '-' || rightN == '-')
                return true;
        }
        if (currentN == '^') {
            if (leftN == '*' || rightN == '*' || leftN == '/' || rightN == '/' || leftN == '%' || rightN == '%')
                return true;
        }
        if (currentN == '+' || currentN == '-'){
            if (rightN == '+' || rightN == '-')
                return true;
        }
        if (currentN == '*' || currentN == '/' || currentN == '%') {
            if (rightN == '*' || rightN == '/' || rightN == '%')
                return true;
        }
        if (currentN == '^') {
            if (leftN == '^')
                return true;
        }
        return false;
    }

    public void evaluate() {
        if (root == null)
            System.out.println("Tree empty."); // If the tree is empty return null
        else {
            if (root.getKind() == 2) {
                evalFlag = false;
                System.out.println("Evaluation result: "+evaluationFunct(root)); // Calls the evaluation function
            }
        }
    }

    public Object evaluationFunct(ParseTree node){
        int calc = 0;
        if (node.getlChild() != null)
            evaluationFunct(node.getlChild()); // Uses recursion to traverse the left child of the current node

        if (node.getrChild() != null)
            evaluationFunct(node.getrChild()); // Uses recursion to traverse the right child of the current node

        if (node.getKind() == 2){
            alphaValMap(); // Calls the alphaValMap function, which assigns values to identifiers
            int lVal;
            int rVal;

            if (!evalFlag) {
                if (node.getlChild().getKind() == 1)
                    lVal = map.get(node.getlChild().getValue().toString()); // Uses the map value if the left child is an identifier
                else
                    lVal = (int) node.getlChild().getValue(); // If the left child is a number, store that value

                if (node.getrChild().getKind() == 1)
                    rVal = map.get(node.getrChild().getValue().toString()); // Uses the map value if the right child is an identifier
                else
                    rVal = (int) node.getrChild().getValue(); // If the right child is a number, store that value
            }else {
                if (node.getlChild().getKind() == 1) {
                    if (map2.get(node.getlChild().getValue().toString()) == null) { // If the identifier isn't in the map, print an error
                        System.out.println("Warning: Identifier '" + node.getlChild().getValue().toString() + "' has not been set.");
                        lVal = 0; // Use '0' as the value instead
                    }
                    else
                        lVal = map2.get(node.getlChild().getValue().toString()); // Retrieves the key value from the map
                }
                else
                    lVal = (int) node.getlChild().getValue();

                if (node.getrChild().getKind() == 1) {
                    if (map2.get(node.getrChild().getValue().toString()) == null) { // If the identifier isn't in the map, print an error
                        System.out.println("Warning: Identifier '" + node.getrChild().getValue().toString() + "' has not been set.");
                        rVal = 0; // Use '0' as the value instead
                    }
                    else
                        rVal = map2.get(node.getrChild().getValue().toString()); // Retrieves the key value from the map
                }
                else
                    rVal = (int) node.getrChild().getValue();
            }

            if (node.getValue().toString().charAt(0)=='+') { // If the current node is a plus
                calc = lVal + rVal; // Add the left child to the right child
                node.setValue(calc); // Set the value of the current node to the calculated value
                return node.getValue();
            }else if (node.getValue().toString().charAt(0)=='-') {
                calc = lVal - rVal;
                node.setValue(calc);
                return node.getValue();
            }else if (node.getValue().toString().charAt(0)=='*') {
                calc = lVal * rVal;
                node.setValue(calc);
                return node.getValue();
            }else if (node.getValue().toString().charAt(0)=='/') {
                calc = lVal / rVal;
                node.setValue(calc);
                return node.getValue();
            }else if (node.getValue().toString().charAt(0)=='%') {
                calc = lVal % rVal;
                node.setValue(calc);
                return node.getValue();
            }else if (node.getValue().toString().charAt(0)=='^') {
                if (rVal < 0){ // When powering by a negative, throw an exception
                    throw new ArithmeticException("Powering a negative");
                }
                else{
                    calc = (int) Math.pow(lVal, rVal);
                    node.setValue(calc);
                    return node.getValue();
                }
            }
        }
        return node.getValue();
    }

    public void letTree(){
        if (root == null)
            System.out.println("Tree empty."); // If the tree is empty return null
        else {
            if (root.getKind() == 3) {
                evalFlag = true;
                letTreeFunct(root.getlChild()); // Inserts the left child into the let tree functions
                System.out.println("Evaluation result: "+evaluationFunct(root.getrChild())); // inserts the right child into the evaluation function
            }
        }
    }

    public void letTreeFunct(ParseTree node){
        if (node.getlChild() != null)
            letTreeFunct(node.getlChild()); // Uses recursion to traverse the left nodes

        if (node.getrChild() != null)
            letTreeFunct(node.getrChild()); // Uses recursion to traverse the left nodes

        if (node.getKind() == 5){ // If the current node is an equals sign, carry out the actions
            evalFlag = true;
            map2.put(node.getlChild().getValue().toString(), (int)evaluationFunct(node.getrChild())); // Inserts the identifiers into the map
        }
    }

    private void alphaValMap(){
        map.put("A",26); // Inserts the value of the identifier into the map
        map.put("B",25);
        map.put("C",24);
        map.put("D",23);
        map.put("E",22);
        map.put("F",21);
        map.put("G",20);
        map.put("H",19);
        map.put("I",18);
        map.put("J",17);
        map.put("K",16);
        map.put("L",15);
        map.put("M",14);
        map.put("N",13);
        map.put("O",12);
        map.put("P",11);
        map.put("Q",10);
        map.put("R",9);
        map.put("S",8);
        map.put("T",7);
        map.put("U",6);
        map.put("V",5);
        map.put("W",4);
        map.put("X",3);
        map.put("Y",2);
        map.put("Z",1);
    }
}

class ParseTree {
    private int kind;
    private Object value;
    private ParseTree lChild, rChild;
    public static final int numNode = 0, idNode = 1, opNode = 2, letNode = 3, andNode = 4, eqNode = 5;
    public ParseTree(int knd, Object val)
    {   kind = knd;
        value = val;
        lChild = null;
        rChild = null;
    }
    public ParseTree(int knd, Object val, ParseTree l, ParseTree r)
    {   kind = knd;
        value = val;
        lChild = l;
        rChild = r;
    }

    public ParseTree getlChild() {
        return lChild; // returns the left child of the current node
    }

    public ParseTree getrChild() {
        return rChild; // returns the right child of the current node
    }

    public int getKind() {
        return kind; // Return the kind of the current node
    }

    public Object getValue() {
        return value; // Return the current node value
    }

    public void setValue(Object value) {
        this.value = value; // Sets the value of the current node
    }
}

