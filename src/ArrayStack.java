public class ArrayStack<T> implements Stack<T> {
    private T[] items;
    private int n;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        items = (T[]) new Object[1000];
        n = 0;
    }

    @Override
    public void push(T item) {
        // TODO: If the array is full, double its capacity; then store item at index n and increment n.
        if (n < 1000) {
            items[n] = item;
        } else {
            T[] replace = (T[]) new Object[items.length * 2];
            for (int i = 0; i < 1000; ++i) {
                replace[i] = items[i];
            }
            replace[n] = item;
            items = replace;
        }
        ++n;
    }

    @Override
    public T pop() {
        // TODO: Decrement n, save and return that item, and set the popped slot to null to avoid loitering.
        if (n == 0) return null;
        --n;
        T old = items[n];
        items[n] = null;
        return old;
    }

    @Override
    public boolean isEmpty() {
        // TODO: Return true when n is zero.
        return n == 0;
    }

    @Override
    public int size() {
        // TODO: Return the number of items currently in the stack.
        return n;
    }

    /**
     * Challenge: Given a string containing '(', ')', '{', '}', '[', and ']'
     * return true if every opening bracket is closed by the same type in the correct order.
     * The empty string is valid.
     */
    public static boolean isValid(String s) {
        // TODO: Use an ArrayStack<Character> to check matching brackets. Push opening brackets;
        // for each closer, check that the most recent opener has the matching type.
        ArrayStack<Character> brackets = new ArrayStack<>();
        if (s.length() == 0) return true;
        for (int i = 0; i < s.length(); ++i) {
            Character curr = s.charAt(i);
            if (curr == '(' || curr == '[' || curr == '{') {
                brackets.push(curr);
            } else {
                Character check = brackets.pop();
                if (check == '(' && curr == ')') return true;
                if (check == '[' && curr == ']') return true;
                if (check == '{' && curr == '}') return true;
            }
        }
        return false;
    }

    static void check(String label, Object actual, Object expected) {
        if (actual.equals(expected))
            System.out.println("PASS | " + label);
        else
            System.out.println("FAIL | " + label + " | expected: " + expected + " | got: " + actual);
    }

    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        check("size after 3 pushes", stack.size(), 3);
        check("isEmpty after pushes", stack.isEmpty(), false);
        check("pop 1 (LIFO)", stack.pop(), "third");
        check("pop 2 (LIFO)", stack.pop(), "second");
        check("pop 3 (LIFO)", stack.pop(), "first");
        check("isEmpty after all pops", stack.isEmpty(), true);

        check("isValid: \"[]\"", isValid("[]"), true);
        check("isValid: \"([{}])\"", isValid("([{}])"), true);
        check("isValid: \"[(])\"", isValid("[(])"), false);
        check("isValid: empty string", isValid(""), true);
        check("isValid: \"({[]})\"", isValid("({[]})"), true);
        check("isValid: \"(((\"", isValid("((("), false);
    }
}
