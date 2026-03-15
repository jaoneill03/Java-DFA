package project2problem1;

import java.util.*;

public class DFAmethods {
    
    // Nested class for parsing
    public static class DFAParser {
        
        public static DFA parse(String input) {
            DFA dfa = new DFA();
            String[] parts = input.split(",");
            
            // Parse states 
            parseStates(dfa, parts[0]);
            
            // Parse transitions 
            for (int i = 1; i < parts.length; i++) {
                parseTransition(dfa, parts[i]);
            }
            
            return dfa;
        }
        
        //helper method for parsing the states by taking the dfa and the string of states
        private static void parseStates(DFA dfa, String statesStr) {
            String[] stateTokens = statesStr.split("(?=[q])|(?<=[f])");
            String firstState = null;
            
            for (String token : stateTokens) {
                if (token.isEmpty()) continue;
                
                //checks if state ends with f and makes it a accept state
                if (token.endsWith("f")) {
                    String state = token.substring(0, token.length() - 1);
                    dfa.addAcceptState(state);
                    if (firstState == null) {
                        firstState = state;
                    }
                } else {
                    dfa.addState(token);
                    if (firstState == null) {
                        firstState = token;
                    }
                }
            }
            
            // First state is the start state
            if (firstState != null) {
                dfa.setStartState(firstState);
            }
        }
        
        //helper method that handles the transitions taking a dfa and the transition
        private static void parseTransition(DFA dfa, String transition) {
            
            // takes the starting state followed by the digits
            int i = 1;
            while (i < transition.length() && Character.isDigit(transition.charAt(i))) {
                i++;
            }
            String initial = transition.substring(0, i);
            
            // takes symbol 
            char symbol = transition.charAt(i);
            
            // takes the resulting state 
            String result = transition.substring(i + 1);
            
            dfa.addTransition(initial, symbol, result);
        }
    }
    
    // Nested class for search nodes using BFS
    public static class SearchNode {
        private String state;
        private String path;
        
        public SearchNode(String state, String path) {
            this.state = state;
            this.path = path;
        }
        
        public String getState() {
            return state;
        }
        
        public String getPath() {
            return path;
        }
    }
    
    // Nested class for language checking
    public static class DFALanguageChecker {
        
        public static String findAcceptedString(DFA dfa) {
            // BFS to find shortest path to any accept state
            Queue<SearchNode> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            
            String startState = dfa.getStartState();
            queue.offer(new SearchNode(startState, ""));
            visited.add(startState);
            
            while (!queue.isEmpty()) {
                SearchNode current = queue.poll();
                String currentState = current.getState();
                
                // Check if current state is an accept state
                if (dfa.isAcceptState(currentState)) {
                    String path = current.getPath();
                    return path.isEmpty() ? "lambda" : path;
                }
                
                // looks through transitions
                Map<String, Map<Character, String>> transitions = dfa.getTransitions();
                if (transitions.containsKey(currentState)) {
                    Map<Character, String> stateTransitions = transitions.get(currentState);
                    
                    for (Map.Entry<Character, String> entry : stateTransitions.entrySet()) {
                        char symbol = entry.getKey();
                        String nextState = entry.getValue();
                        
                        if (!visited.contains(nextState)) {
                            visited.add(nextState);
                            queue.offer(new SearchNode(nextState, current.getPath() + symbol));
                        }
                    }
                }
            }
            
            return null; // No accept state reachable
        }
        
        public static boolean isLanguageEmpty(DFA dfa) {
            return findAcceptedString(dfa) == null;
        }
    }
}