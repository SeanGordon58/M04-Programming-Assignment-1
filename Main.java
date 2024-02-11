import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <source-code-file>");
            return;
        }

        String filename = args[0];
        try {
            boolean result = checkGroupingSymbols(filename);
            if (result) {
                System.out.println("The source-code file has correct pairs of grouping symbols.");
            } else {
                System.out.println("The source-code file has incorrect pairs of grouping symbols.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static boolean checkGroupingSymbols(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Stack<Character> stack = new Stack<>();
            int lineNumber = 1;

            String line;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if (c == '(' || c == '{' || c == '[') {
                        stack.push(c);
                    } else if (c == ')' || c == '}' || c == ']') {
                        if (stack.isEmpty()) {
                            System.err.println("Error: Unmatched closing symbol " + c + " at line " + lineNumber);
                            return false;
                        }

                        char openingSymbol = stack.pop();
                        if ((c == ')' && openingSymbol != '(') ||
                            (c == '}' && openingSymbol != '{') ||
                            (c == ']' && openingSymbol != '[')) {
                            System.err.println("Error: Mismatched symbols " + openingSymbol + " and " + c + " at line " + lineNumber);
                            return false;
                        }
                    }
                }
                lineNumber++;
            }

            if (!stack.isEmpty()) {
                System.err.println("Error: Unmatched opening symbol(s) at the end of file.");
                return false;
            }

            return true;
        }
    }
}