public class LinkedListStack<T> implements Stack<T> {
    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int n;

    public LinkedListStack() {
        first = null;
        n = 0;
    }

    @Override
    public void push(T item) {
        // TODO: Create a node, link it to the old first node, and make it the new first node.
        Node oldFirst = first;
        Node newFirst = new Node();
        newFirst.next = oldFirst;
        newFirst.item = item;
        first = newFirst;
        ++n;
    }

    @Override
    public T pop() {
        // TODO: Save the first item's value, move first to first.next, and return the saved value.
        if (n == 0) return null;
        --n;
        Node removed = new Node();
        removed.item = first.item;
        removed.next = first.next;
        first = first.next;
        return removed.item;
    }

    @Override
    public boolean isEmpty() {
        // TODO: Return true when the first node is null.
        return first == null;
    }

    @Override
    public int size() {
        // TODO: Return the number of items currently in the stack.
        return n;
    }

    /**
     * Challenge: Evaluate a valid arithmetic expression in Reverse Polish Notation.
     * The operators are '+', '-', '*', and '/'; division truncates toward zero.
     */
    public static int evalRPN(String[] tokens) {
        // TODO: Use a LinkedListStack<Integer> to evaluate the tokens. For an operator,
        // pop the right operand first, then the left operand; apply the operator and push the result.
        if (tokens.length == 0) return 0;
        LinkedListStack<Integer> numbers = new LinkedListStack<>();
        for (String s : tokens) {
            if (!(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))) {
                int uh = Integer.parseInt(s);
                numbers.push(uh);
            } else {
                int i = numbers.pop();
                int j = numbers.pop();
                int result = operate(i, j, s);
                numbers.push(result);
            }
        }
        int total = numbers.pop();
        return total;
    }

    public static int operate (int i, int j, String operator) {
        if (operator.equals("+")) return i + j;
        else if (operator.equals("-")) return j -i;
        else if (operator.equals("*")) return i * j;
        else return j / i;
    }

    static void check(String label, Object actual, Object expected) {
        if (actual.equals(expected))
            System.out.println("PASS | " + label);
        else
            System.out.println("FAIL | " + label + " | expected: " + expected + " | got: " + actual);
    }

    public static void main(String[] args) {
        LinkedListStack<String> stack = new LinkedListStack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        check("size after 3 pushes", stack.size(), 3);
        check("isEmpty after pushes", stack.isEmpty(), false);
        check("pop 1 (LIFO)", stack.pop(), "third");
        check("pop 2 (LIFO)", stack.pop(), "second");
        check("pop 3 (LIFO)", stack.pop(), "first");
        check("isEmpty after all pops", stack.isEmpty(), true);

        check("evalRPN: ((1+2)*3)-4", evalRPN(new String[]{"1", "2", "+", "3", "*", "4", "-"}), 5);
        check("evalRPN: (2+1)*3", evalRPN(new String[]{"2", "1", "+", "3", "*"}), 9);
        check("evalRPN: 4+(13/5)", evalRPN(new String[]{"4", "13", "5", "/", "+"}), 6);
        check("evalRPN: complex", evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}), 22);
    }
}
