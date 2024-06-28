// 21.3
import java.io.*;
import java.util.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords <filename>");
            System.exit(1);
        }

        String filename = args[0];
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File " + filename + " does not exist");
            System.exit(2);
        }

        System.out.println("The number of keywords in " + filename + " is " + countKeywords(file));
    }

    public static int countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false and null
        String[] keywordString = {"abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;
        boolean inComment = false;
        boolean inString = false;

        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String word = input.next().trim();

            // Check for comments and string literals
            if (inComment) {
                if (word.contains("*/")) {
                    inComment = false;
                    // Remove the rest of the comment
                    word = word.substring(word.indexOf("*/") + 2).trim();
                } else {
                    continue; // Skip current word if inside a comment
                }
            } else if (inString) {
                if (word.contains("\"")) {
                    inString = false;
                    // Remove the rest of the string literal
                    word = word.substring(word.indexOf("\"") + 1).trim();
                } else {
                    continue; // Skip current word if inside a string literal
                }
            }

            // Check if the word starts a comment or string literal
            if (word.startsWith("//")) {
                continue; // Skip line comment
            } else if (word.startsWith("/*")) {
                inComment = true;
                continue; // Skip block comment
            } else if (word.startsWith("\"")) {
                inString = true;
                continue; // Skip string literal
            }

            // Now check if the word is a keyword
            if (keywordSet.contains(word)) {
                count++;
            }
        }

        return count;
    }
}
