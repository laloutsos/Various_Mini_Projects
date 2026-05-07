# Robots Collision Detection in Prolog

## Overview
This Prolog project simulates the movement of two robots on a grid-based environment. Each robot can move in four directions (north, south, east, and west) and follows a predefined sequence of moves. The goal of the program is to determine whether the two robots will collide at any point during their movements.

## Problem Definition
- The grid is divided into rectangular cells, each represented using a term `sec(X, Y)`, where:
  - `X` increases from left to right (eastward).
  - `Y` increases from bottom to top (northward).
- Each robot starts at a given position and follows a list of moves (`e`, `w`, `n`, `s`).
- A collision occurs if:
  1. Both robots occupy the same cell after executing the same number of moves.
  2. They swap positions between consecutive moves.
  3. One robot reaches a cell while the other is already there.

## Implementation Details
- The movement rules are implemented using the `move/3` predicate.
- The main predicate `robots/4` recursively checks if the two robots collide based on their movement sequences.
- The program strictly adheres to term-based numerical representation, avoiding Prolog’s built-in arithmetic operations.

## Usage
To check if two robots starting from positions `S1` and `S2` with movement sequences `L1` and `L2` will collide, run the query:
```prolog
robots(S1, L1, S2, L2).
```
Example:
```prolog
?- robots(sec(p(0), s(0)), [e, n, e, n], sec(0, 0), [n, e, n, e]).
```
This will return `true` if the robots collide and `false` otherwise.

## Constraints
- The program does not allow conversion between term-based numbers and built-in numerical representations.
- Movement lists must only contain the elements `{e, w, n, s}`.



