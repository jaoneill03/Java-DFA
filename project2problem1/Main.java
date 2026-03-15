package project2problem1;
import java.io.*;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
    	
    	//scan input file
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input file name: ");
        String fileName = scanner.nextLine().trim();
        String input = readInput(fileName);
        
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Error: Empty input or file not found");
            scanner.close();
            return;
        }
        
        // Parse DFA
        DFA dfa = DFAmethods.DFAParser.parse(input.trim());
        
        // Check if language is empty
        String acceptedString = DFAmethods.DFALanguageChecker.findAcceptedString(dfa);
        
        if (acceptedString != null) {
            System.out.println("no");
            System.out.println(acceptedString);
        } else {
            System.out.println("yes");
        }
        
        scanner.close();
    }
    
    private static String readInput(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String input = reader.readLine();
                reader.close();
                return input;
            } else {
                System.out.println("File not found: " + fileName);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}