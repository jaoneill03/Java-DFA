package project2problem2;

import java.util.*;

/*DFA class that stores the states,the start state
 * what states are accept or reject, and stores
 * the transitions between states in a map
*/
public class DFA {
    private Set<String> states;
    private String startState;
    private Set<String> acceptStates;
    private Map<String, Map<Character, String>> transitions;
    
    //constructs a DFA initializing hashsets for states, accept states, and transitions
    public DFA() {
        states = new HashSet<>();
        acceptStates = new HashSet<>();
        transitions = new HashMap<>();
    }
    
    //adds a state to DFA
    public void addState(String state) {
        states.add(state);
    }
    
    // sets which state is the start state
    public void setStartState(String state) {
        startState = state;
    }
    
    //adds accept state to set of all states and separate set of accept states
    public void addAcceptState(String state) {
        states.add(state);
        acceptStates.add(state);
    }
    
    //adds a transition using initial state, transition symbol, and resulting state
    public void addTransition(String initial, char symbol, String result) {
        transitions.putIfAbsent(initial, new HashMap<>());
        transitions.get(initial).put(symbol, result);
    } 
    
    //returns the start state
    public String getStartState() {
        return startState;
    }
    
    //returns the map of transitions
    public Map<String, Map<Character, String>> getTransitions() {
        return transitions;
    }
    
    //checks if a state is an accept state
    public boolean isAcceptState(String state) {
        return acceptStates.contains(state);
    }
    
    //gets the next state and transition symbol 
    public String getNextState(String currentState, char symbol) {
        if (transitions.containsKey(currentState)) {
            return transitions.get(currentState).get(symbol);
        }
        return null;
    }
}