# Java DFA Language Checker

A Java program that analyzes Deterministic Finite Automata (DFAs) to
determine properties of their languages. Contains two problems that
each read a DFA definition from an input file.

## How to compile & run

**Compile:**
```bash
javac project2problem1/*.java
javac project2problem2/*.java
```

**Run Problem 1:**
```bash
java project2problem1.Main
```

**Run Problem 2:**
```bash
java project2problem2.Main
```

When prompted, enter the name of your input file.

## Input format

DFAs are defined on a single line using the following format:
- States are written as `q0`, `q1`, etc.
- Accept states are marked with an `f` suffix (e.g. `q1f`)
- The first state listed is the start state
- Transitions follow the states, separated by commas
- Each transition is written as `currentStatesymbolnextState`
  (e.g. `q00q1` means: from q0, on symbol 0, go to q1)

**Example:**
```
q0,q1f,q00q1,q01q0,q10q1,q11q0
```

## Problem 1 — Language emptiness check

Takes one DFA and determines whether its language is empty.

**Input:** A file containing one DFA definition

**Output:**
- `yes` — the language is empty
- `no` followed by a shortest accepted string (or `lambda` if the
  empty string is accepted)

**Example output:**
```
no
ab
```

## Problem 2 — Language equality check

Takes two DFAs and determines whether they accept the same language.
Constructs a product DFA that accepts strings in exactly one of the
two languages, then checks if that language is empty.

**Input:** A file containing two DFA definitions on separate lines

**Output:**
- `yes` — the languages are equal
- `no` followed by a string that one DFA accepts and the other does not

**Example output:**
```
no
ab
```

## How it works

- `DFA.java` — stores states, transitions, and accept states using
  HashSets and HashMaps
- `DFAmethods.java` — contains a parser to build a DFA from the input
  string, and a BFS-based language checker to find the shortest
  accepted string
- `Main.java` — reads the input file, runs the checks, and prints results

## Files
```
project2problem1/
  ├── DFA.java
  ├── DFAmethods.java
  └── Main.java

project2problem2/
  ├── DFA.java
  ├── DFAmethods.java
  └── Main.java
```

## Technologies
- Java
- Deterministic finite automata
- BFS (breadth-first search)
- HashMap / HashSet
- Product DFA construction
