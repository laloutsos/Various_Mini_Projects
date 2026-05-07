# Haskell Exercises 

This repository contains some simple yet educational Haskell functions.

---

## Exercise 1: Triangle Area Calculation

### Problem Statement

Write a Haskell function `area` that computes the area of a triangle given its three vertex coordinates on the 2D plane. The coordinates are passed as three pairs of real numbers.

### Mathematical Formula

The function uses **Heron’s formula**:

```
A = √[ t * (t - a) * (t - b) * (t - c) ]
```

* `a`, `b`, `c` are the side lengths.
* `t` is the semi-perimeter: `t = (a + b + c) / 2`.
* Distance between `(x1, y1)` and `(x2, y2)`:

```
√[ (x1 - x2)^2 + (y1 - y2)^2 ]
```

### Type Signature

```haskell
area :: (Double, Double) -> (Double, Double) -> (Double, Double) -> Double
```

### Implementation

```haskell
area (x1,y1) (x2,y2) (x3,y3) = sqrt (t * (t - a) * (t - b) * (t - c))
  where
    a = sqrt ((x1 - x2)^2 + (y1 - y2)^2)
    b = sqrt ((x1 - x3)^2 + (y1 - y3)^2)
    c = sqrt ((x3 - x2)^2 + (y3 - y2)^2)
    t = (a + b + c) / 2
```

### Example Runs

```haskell
Main> area (1.5,2.5) (3.5,2.5) (1.5,5.5)
3.0
Main> area (5.3,7.4) (1.3,4.4) (9.3,7.4)
6.0
Main> area (-1.01,-0.02) (0.99,-0.02) (-0.01,1.71)
1.73
Main> area (25.45,37.19) (-11.47,-71.08) (45.33,-64.48)
2953.032
Main> area (7.1,-1.6) (-3.3,-5.8) (17.5,2.6)
0.0
```

---

## Exercise 2: Parking Cost Calculation

### Problem Statement

Write a function `parking` that computes parking cost based on arrival and departure time `(hour, minute)`.

Rules:

* Lot operates between 6:00 and 22:00
* Charges:

  * Up to 3 hours: €8
  * 4–6 hours: +€2/hour
  * Beyond 6 hours: +€1/hour
* Round up to the next full hour

### Type Signature

```haskell
parking :: (Int, Int) -> (Int, Int) -> Int
```

### Implementation

```haskell
parking (h1, m1) (h2, m2)
  | k <= 3 && s <= 0 = 8
  | k == 3 && s > 0  = 10
  | k <= 6 && s <= 0 = 8 + (2 * (k - 3))
  | k <= 6 && s > 0  = 8 + (2 * (k - 3) + 1)
  | k > 6 && s > 0   = 9 + k
  | otherwise        = 8 + k
  where
    k = h2 - h1
    s = m2 - m1
```

### Example Runs

```haskell
Main> parking (13,59) (14,00)
8
Main> parking (15,30) (16,30)
8
Main> parking (8,45) (11,15)
8
Main> parking (6,15) (9,14)
8
Main> parking (12,22) (15,23)
10
```

---

## Exercise 3: `join`

### Problem Statement

Write a function that combines digits of two integers using:

```
ci = (13 * (ai + 5) + 19 * (bi + 3)) mod 10
```

### Type Signature

```haskell
join :: Integer -> Integer -> Integer
```

### Implementation

```haskell
join :: Integer -> Integer -> Integer
join a b
    | a < 0 || b < 0 = join (abs a) (abs b)
    | a > 0 || b > 0 = 10 * join (a `div` 10) (b `div` 10) + (13 * (ai + 5) + 19 * (bi + 3)) `mod` 10
    | otherwise = 0
  where
    ai = a `mod` 10
    bi = b `mod` 10
```

### Example Runs

```haskell
Main> join 53421 97680
84805
Main> join 97680 53421
40641
Main> join 731679 34
315005
Main> join 34 731679
591645
Main> join (-87341) (-20965)
43280
Main> join (2^62) (3^31)
4055453683197980087
```

---

## Exercise 4: `kgcd`

### Problem Statement

Find the *k*-th greatest common divisor of two integers.

### Type Signature

```haskell
kgcd :: Int -> Int -> Int -> Int
```

### Implementation

```haskell
seekGCD :: Int -> Int -> Int -> Int -> Int
seekGCD m n y s
    | y == 0 = 0
    | s /= 1 && m `mod` y == 0 && n `mod` y == 0 = seekGCD m n (y - 1) (s - 1)
    | s == 1 && m `mod` y == 0 && n `mod` y == 0 = y
    | otherwise = seekGCD m n (y - 1) s

kgcd :: Int -> Int -> Int -> Int
kgcd m n k
    | m <= n = seekGCD m n m k
    | otherwise = seekGCD n m n k
```

### Example Runs

```haskell
Main> kgcd 35 24 1
1
Main> kgcd 36 24 1
12
Main> kgcd 35 24 2
0
Main> kgcd 36 24 3
4
Main> kgcd 1001 887 1
1
Main> kgcd 648 432 1
216
Main> kgcd 648 432 5
36
```

---

## Exercise 5: `nearest`

### Problem Statement

Find the position of the closest element to a given number in a list.

### Type Signature

```haskell
nearest :: [Int] -> Int -> Int
```

### Implementation

```haskell
minIndex :: [Int] -> Int -> Int -> Int -> Int -> Int
minIndex (h:[]) dist idist i n = if abs(h - n) < dist then i else idist
minIndex (h:t) dist idist i n
    | abs(h - n) < dist = minIndex t (abs(h - n)) i (i + 1) n
    | otherwise = minIndex t dist idist (i + 1) n

nearest :: [Int] -> Int -> Int
nearest s n
    | length s == 1 = 1
    | otherwise = minIndex s 44444 1 1 n
```

### Example Runs

```haskell
Main> nearest [0] 5
1
Main> nearest [2,7] 4
1
Main> nearest [2,7] 5
2
Main> nearest [16,25,39,42,20,50,64,72,10,48] 17
1
Main> nearest [16,25,39,42,20,50,64,72,10,48] 46
10
Main> nearest [16,25,39,42,20,50,64,72,10,48] 21
5
Main> nearest [16,25,39,42,20,50,64,72,10,48] 18
1
Main> nearest [16,25,39,42,20,50,64,72,10,48] 45
4
```

---

## Exercise 6: `replace`

### Problem Statement

Replace all occurrences of string `a` in string `w` with string `b`.

### Type Signature

```haskell
replace :: String -> String -> String -> String
```

### Example Runs

```haskell
Main> replace "a" "b" "a"
"b"
Main> replace "b" "a" "a"
"a"
Main> replace "." "," "1205.45"
"1205,45"
Main> replace "x" "*" "123x567x489"
"123*567*489"
Main> replace "001" "100" ""
""
Main> replace "word" "number" "first word, second word, third word"
"first number, second number, third number"
Main> replace "001" "" "000110110010100001"
"010110100"
Main> replace "ab" "" "abababab"
""
```

---

## Exercise 7: `generating`

### Problem Statement

Generate a function defined as:

$$
g_k(z) = \sum_{i=0}^k f(i) \cdot z^i
$$

### Type Signature

```haskell
generating :: (Int -> Double) -> Int -> (Double -> Double)
```

### Implementation

```haskell
generating :: (Int -> Double) -> Int -> (Double -> Double)
generating f 0 z = f 0
generating f k z = (f k) * (z ^ k) + generating f (k - 1) z
```

### Example Runs

```haskell
Main> generating (\n -> 2.0^n) 5 0.25
1.96875
Main> generating (\n -> 2.0^n) 1000 0.25
2.0
Main> generating (\n -> fromIntegral n) 1000 0.2
0.3125
Main> generating (\n -> fromIntegral n) 1000 0.6
3.75
Main> generating (\n -> fromIntegral (n^2)) 1000 0.8
180.0
```

---

## Exercise 8: `mapi`

### Problem Statement

Map a function over a list with index awareness.

### Type Signature

```haskell
mapi :: [u] -> (u -> Int -> v) -> [v]
```

### Implementation

```haskell
mapi :: [u] -> (u -> Int -> v) -> [v]
mapi s f = go s 0
  where
    go [] _ = []
    go (x:xs) i = f x i : go xs (i + 1)
```

### Example Runs

```haskell
Main> mapi [90,80..10] (+)
[91,82,73,64,55,46,37,28,19]
Main> mapi [12,15,20,19,24,35,13,39,62,29] mod
[0,1,2,3,4,5,6,7,8,9]
Main> mapi [7,8,5,10,4,2,3,2,2,1] (^)
[7,64,125,10000,1024,64,2187,256,512,1]
Main> mapi ["island", "happiness", "stars", "surfing", "singing", "dancing"] (!!)
"spring"
Main> mapi "dream" (\x i -> toEnum (fromEnum x + i) :: Char)
"ether"
Main> mapi [[a,a+d..] | a <- [1,32,100,500], d <- [3,25,333,1040]] (!!)
[4,51,1000,4161,47,182,2363,8352,127,350,3763,12580,539,850,5495,17140]
Main> mapi [[a,a+d..] | d <- [1,32,100,500], a <- [3,25,333,1040]] (!!)
[4,27,336,1044,163,217,557,1296,903,1025,1433,2240,6503,7025,7833,9040]
```
