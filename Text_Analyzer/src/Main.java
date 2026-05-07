import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static int searchCount = 0;
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        System.out.print(CYAN);
        System.out.println("Welcome to the Text Analyzer!");
        System.out.println("This program analyzes words from a text file using a Trie (prefix tree) data structure.");
        System.out.println("The Trie allows efficient insertion, search, prefix queries, pattern matching, and frequency analysis.\n");
        System.out.print(RESET);

        Scanner scan = new Scanner(System.in);


        System.out.print("Enter your username: ");
        String username = scan.nextLine().trim();
        System.out.print("Do you want to enable logging to a file? (yes/no): ");
        boolean loggingEnabled = scan.nextLine().trim().equalsIgnoreCase("yes");

        BufferedWriter logWriter = null;
        if (loggingEnabled) {
            String logFileName = username + "_log.txt";
            try {
                logWriter = new BufferedWriter(new FileWriter(logFileName, true)); // append mode
                logWriter.write(getTimestamp());
                logWriter.write("\n=== New session for user: " + username + " ===\n");
            } catch (IOException e) {
                System.out.println("Error initializing log file: " + e.getMessage());
                loggingEnabled = false;
            }
        }

        System.out.println("Please enter the name of the text file you want to analyze:");
        String input = scan.nextLine();
        System.out.println("File processing...");


        StringTrie T = new StringTrie();

        try {
            File file = new File(input);
            Scanner scanner = new Scanner(file);

            long startTime = System.currentTimeMillis();
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty()) {
                    T.insert(word);
                }
            }
            scanner.close();
            long endTime = System.currentTimeMillis();

            System.out.println(T.size() + " words loaded.");
            System.out.println("Construction time = " + (endTime - startTime) + " ms");
            System.out.println("Memory usage (KB) = " +
                    (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
            System.out.println();



            boolean running = true;
            while (running) {
                printMenu();
                int choice = getChoice(scan);

                switch (choice) {
                    case 1:
                        System.out.print("Enter word to check if it exists: ");
                        String word = scan.nextLine();
                        int exists = T.contains(word);
                        System.out.println("Number of appearances of '" + word + "':" + exists);
                        if (loggingEnabled && logWriter != null)
                            logWriter.write("Check word '" + word + "': " + exists + "\n");
                        searchCount++;
                        break;

                    case 2:
                        System.out.print("Enter prefix to count words: ");
                        String prefix = scan.nextLine();
                        int count = T.countWordsWithPrefix(prefix);
                        System.out.println("Number of words with prefix '" + prefix + "': " + count);
                        if (loggingEnabled && logWriter != null)
                            logWriter.write("Count words with prefix '" + prefix + "': " + count + "\n");
                        searchCount++;
                        break;

                    case 3:
                        System.out.print("Enter word to find longest prefix of: ");
                        String lpWord = scan.nextLine();
                        String longestPrefix = T.longestPrefixOf(lpWord);
                        System.out.println("Longest prefix of '" + lpWord + "': " + longestPrefix);
                        if (loggingEnabled && logWriter != null)
                            logWriter.write("Longest prefix of '" + lpWord + "': " + longestPrefix + "\n");
                        searchCount++;
                        break;

                    case 4:
                        System.out.print("Enter prefix to print words: ");
                        String s = scan.nextLine();
                        System.out.println("\nwords with prefix " + s + " : ");
                        T.printWords(T.wordsWithPrefix(s));
                        System.out.println("\nnumber of words with prefix " + s + " = " + T.countWordsWithPrefix(s));
                        if (loggingEnabled && logWriter != null) {
                            logWriter.write("Printed words with prefix '" + s + "' (Count: " + T.countWordsWithPrefix(s) + ")\n");
                        }
                        searchCount++;
                        break;

                    case 5:
                        System.out.print("Enter pattern (use ? as wildcard): ");
                        String pattern = scan.nextLine();
                        System.out.println("Words matching pattern '" + pattern + "':");
                        if (loggingEnabled && logWriter != null)
                            logWriter.write("Words matching pattern '" + pattern + "':\n");
                        T.printWords(T.wordsThatMatch(pattern));
                        searchCount++;
                        break;

                    case 6:
                        Item mostFreq = T.mostFrequent();
                        if (mostFreq != null) {
                            System.out.println("Most frequent word: '" + mostFreq.getS() + "' with count: " + mostFreq.getCount());
                            if (loggingEnabled && logWriter != null)
                                logWriter.write("Most frequent word: '" + mostFreq.getS() + "' with count: " + mostFreq.getCount() + "\n");
                        } else {
                            System.out.println("No words found.");
                            if (loggingEnabled && logWriter != null)
                                logWriter.write("Most frequent word: None\n");
                        }
                        break;

                    case 7:
                        System.out.print("Enter word to delete: ");
                        String delWord = scan.nextLine();
                        T.delete(delWord);
                        System.out.println("Deleted word '" + delWord + "' (if it existed).");
                        if (loggingEnabled && logWriter != null)
                            logWriter.write("Deleted word '" + delWord + "'\n");
                        break;

                    case 0:
                        running = false;
                        System.out.println("Exiting program. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
                if (loggingEnabled && logWriter != null)
                    logWriter.flush();
                System.out.println();
            }

            if (loggingEnabled && logWriter != null) {
                logWriter.write("Total searches performed: " + searchCount + "\n");
                logWriter.write("=== End of session ===\n\n");
                logWriter.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + input);
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\nSelect an option:");
        System.out.printf("%-30s %-30s %-30s %-30s%n",
                "1: Check if a word exists,",
                "2: Count words with a prefix,",
                "3: Find longest prefix of a word,",
                "4: Print words with a prefix,");

        System.out.printf("%-30s %-30s %-30s %-30s%n",
                "5: Match pattern (use ?),",
                "6: Show most frequent word,",
                "7: Delete sa word,",
                "0: Exit.");

        System.out.println(); // extra line for spacing
    }
    private static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " -";
    }

    private static int getChoice(Scanner scan) {
        int choice = -1;
        while (true) {
            System.out.print("Your choice: ");
            String line = scan.nextLine();
            try {
                choice = Integer.parseInt(line);
                if (choice < 0 || choice > 7) {
                    System.out.println("Please enter a number between 0 and 7.");
                    continue;
                }
                break; // valid choice
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }
}
