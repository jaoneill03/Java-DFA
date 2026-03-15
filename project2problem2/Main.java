package project2problem2;
import java.io.*;
import java.util.*;


public class Main {
    
    public static void main(String[] args) {
    	
    	//scan input file
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input file name: ");
        String fileName = scanner.nextLine().trim();
        String[] inputs = readInput(fileName);
        
        if (inputs == null || inputs.length != 2) {
            System.out.println("Error: File must contain exactly two DFAs on separate lines");
            scanner.close();
            return;
        }
        
        // Parse both DFAs
        DFA dfa1 = DFAmethods.DFAParser.parse(inputs[0].trim());
        DFA dfa2 = DFAmethods.DFAParser.parse(inputs[1].trim());
        
        // Constructs a DFA that accepts strings in exactly one of the two languages
        DFA diff = constructDifference(dfa1, dfa2);
        
        // Check if the a string exists in the constructed DFA and that the languages are equal
        String differenceString = DFAmethods.DFALanguageChecker.findAcceptedString(diff);
        
        if (differenceString == null) {
            // Languages are equal
            System.out.println("yes");
        } else {
            // Languages differ
            System.out.println("no");
            System.out.println(differenceString);
        }
        
        scanner.close();
    }
    
    /*
     * Constructs a DFA that accepts the difference of L(dfa1) and L(dfa2).
     * This DFA accepts strings that are in exactly one of the two languages.
     */
    private static DFA constructDifference(DFA dfa1, DFA dfa2) {
        DFA product = new DFA();
        char[] alphabet = {'a', 'b'};
        
        // Start state is the pair of start states
        String startState = makeStatePair(dfa1.getStartState(), dfa2.getStartState());
        product.setStartState(startState);
        
        // BFS to construct reachable states
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(startState);
        visited.add(startState);
        
        while (!queue.isEmpty()) {
            String currentPair = queue.poll();
            String[] states = parseStatePair(currentPair);
            String state1 = states[0];
            String state2 = states[1];
            
            // Determine if this is an accept state and Accept if exactly one of the component states is an accept state
            boolean accept1 = dfa1.isAcceptState(state1);
            boolean accept2 = dfa2.isAcceptState(state2);
            
            if (accept1 != accept2) {
                product.addAcceptState(currentPair);
            } else {
                product.addState(currentPair);
            }
            
            // Add transitions for each symbol in the alphabet
            for (char symbol : alphabet) {
                String next1 = dfa1.getNextState(state1, symbol);
                String next2 = dfa2.getNextState(state2, symbol);
                
                if (next1 != null && next2 != null) {
                    String nextPair = makeStatePair(next1, next2);
                    product.addTransition(currentPair, symbol, nextPair);
                    
                    if (!visited.contains(nextPair)) {
                        visited.add(nextPair);
                        queue.offer(nextPair);
                    }
                }
            }
        }
        
        return product;
    }
    
    private static String makeStatePair(String state1, String state2) {
        return "(" + state1 + "," + state2 + ")";
    }
    
    private static String[] parseStatePair(String pair) {
        // Remove parentheses
        String inner = pair.substring(1, pair.length() - 1);
        // Split by comma
        return inner.split(",");
    }
    
    private static String[] readInput(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            reader.close();
            
            if (line1 == null || line2 == null) {
                System.out.println("Error: File must contain two lines");
                return null;
            }
            
            return new String[]{line1, line2};
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}