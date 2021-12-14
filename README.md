# Advent of Code 2021 in Clojure

My solutions for [Advent of Code](https://www.adventofcode.com) 2021 using Clojure.
Template copied from [Advent of Code Clojure Starter](https://github.com/mhanberg/advent-of-code-clojure-starter).

## Notes
### Day 1

### Day 5
#### Part 1
Consider only straight (horizontal or vertical) lines.

* remove diagonal lines
* Create all points as 2D vectors `[x y]`
* filter out all points with frequency < 2 and count them

The points get created by iterating over the range x1-x2 and y1-y2 (input for
range needs the first element smaller, so we sort).

At first I used `reduce` to concat the vectors with points for a line, but of
course I can just use `mapcat` for that.

#### Part 2
Consider also diagonal lines. All diagonals have an angle of 45 degrees.

Keep the general plan, but change how the points get created. We can't iterate
over the range x1-x2 and y1-y2, because we get a rectangle for the diagonal
inputs.

The iteration to create the points will be something like: Figure out the
direction of the x and y component of a line (+1 or -1). Start with the
starting x and y value and generate the next value for x and y by applying the
direction. Stop when you reached the end point.

I couldn't figure out how to do this in a more non-declarative way, but got an
idea from reddit: Just build an iterating function, that applies the direction.
Then `iterate` it and `take-while` the end point hasn't been reached.

### Day 6
Part 2 is just going to be "now do the same, but for 800 days", isn't it.

When would that be a problem?
- list where the indices = days and the values how many new new fish will spawn that day
- go through the list and for every new fish add 1 every 6 steps from there, plus 2 days for maturity

With the test input "3,4,3,1,2" we want the following initial state: [0 1 1 2
1]. No new fish will be born on the first day, one new fish each the next two
days etc. In other words, for every number in the input, we put +1 into the
list at index = number + 1.

But we don't need an vector with 80 days. We only need a window of 8 days.

Map over the range of 0 8 and put the frequency of that number at the
respective index (with default to 0).

```clojure
(mapv #(get (frequencies [3 4 3 1 2]) % 0) (range 0 9))
```

Now we have this vector: `[0 1 1 2 1 0 0 0 0]`

At every step, we take the first value of that vector and move it to the last
position. That represents the new fish, that every fish at this step will
produce in 8 days. We take that same value and add it 6 to the value 6 days
later.

So we need a way to rotate the vector.

```clojure
(concat (subvec v n) (subvec v 0 n))
```

This only works if n is not bigger than the vectors lenght. I could improve
just letting it rotate as much as you want.

And then:

```clojure
(defn step
  [fish-list]
  (let [new-fish (first fish-list)]
    (update (rotate-left fish-list 1) 6 + new-fish)))
```

On my machine part 1 takes about 500 µs, part 2 700 µs.

### Day 7
#### Part 1
Isn't this just getting the median of all the numbers, get the difference to that median for every number and sum that?
Median of the example input is 2, so that checks out.
Get the median, then reduce all the numbers with a functon that sums the (absolute) distance between every number and the median.

#### Part 2
Just a hunch: if we just do the same thing but with the mean instead of the median?

With the test input `(/ (reduce + positions) (count positions)` gives 4.9 and
with quot we get 4. Do we just need to round this? Let's try using
`Math/round`.

Ok, that gives us 5 as the target position for the example input.

To calculate the cost: Get the difference between target and positions, make a
range of that length, starting at 1 and sum that?

I came up with a goofy way to get the triangular number: `(reduce + (range (inc distance)))`.
But `(/ (* distance (inc distance)) 2)` is the math I didn't know, that does the same.

For rounding the mean to an integer, there is no way to figure out which of the
next two is the correct one, apart from minimizing the cost. I can't determine
that before calculating the cost.

That's why I my code either worked for the test input or for the real input
(one needs to get rounded up, the other down to minimize the cost).

Quick solution is to check the total cost for both points next to the mean and
take the minimum of that. But maybe there's a smarter way to do that.

### Day 8

How do I determine, which letters in the input (signals) correspond to which segment?
Let's take the example input:

```
acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab
```

Before we start each letter has 7 possibilites.
But we can identify 1, 4, 7 and 8 by the length of the word. 
The word with length 1 must be 1. That reduces the possibilites to only two for the two contained letters.
In the example, a is either c or f and b is the other of these two options.

So we can remove a and b from the word for 7, which we know because it is the only one with length 3.

Now far we know:
- a = c/f 
- b = c/f
- d = a

---

Actually, I don't need to identify the segments. I just need enough sub-shapes
to differentiate the 10 numbers!

- 1: length two
- 4: length four
- 7: length three
- 8: length seven

Three numbers with five segments, that can be excluded with these steps:

- 5: only five-segment number, that contains the diff of 1 and 4
- 3: contains the segments of 1
- 2: the last five-segment number

And three numbers with six segments:
- 6: does not contain all of 1
- 0: does not contain the diff of 1 and 4
- 9: last number

---

The smart way that alot of others came up with is the following:
This is the correct configuration:

```
0: abcefg 1: cf 2: acdeg 3: acdfg 4: bcdf 5: abdfg 6: abdefg 7: acf 8: abcdefg 9: abcdfg
```

The frequency for the segments over the whole configuration is:

```clojure
{\c 8, \f 9, \a 8, \b 6, \d 7, \e 4, \g 7}
```
Sadly, it's not unique. But: replace every character with it's frequency and (e.g "bcdf" is [6 8 7 f]) and take the sum.  
Now we have a unique number for every word.

```
42 17 34 39 30 37 41 25 49 45
```

We can use these as keys mapping to the correct number.

Then, for every line in the input:
1. get the frequency of the segments in the signal patterns
2. for ever output word, get the sum in the described way
3. lookup the correct number with that sum as key

### Day 9
#### Part 1
I can reuse the technique of my solution for AoC 2020 day 7:
Keep the input as one sequence and get vertical neighbours with an index offset. The offset is the index of the first newline character.
But we need to pad the string in the beginning and at the end, otherwise we'll get out of bounds errors.
This time we'll not leave it as a string though, but convert it to a number sequence of numbers (and newline characters). Padding with 9 so that the numbers at the edge are always smaller than it.

Padding function from last year, adjusted:

```clojure
(defn padded
  [input]
  (let [width (first (keep-indexed #(when (not (number? %2)) %1) input))
        padding (repeat (inc width) 9)]
    (concat padding input padding)))
```

I could do the padding on the string like last year with
`clojure.string/index-of`, but now I learned to find the index of an occurence
in a sequence with `keep-indexed`.

Now we just need function that finds all the von Neumann neighbours. I'll generalize to Manhattan neighours, maybe we need to consider numbers further away for part 2.

Wait a minute: I don't need to pad! Since I'll use a vector, I can just use `get` with a default value of 9. D'oh.

#### Part 2
To find the basins, start at a lowest point and spread in every direction until you hit a 9 (or the edge).

First thing I can think of is doing it recursively:
- Start with the lowest point
- Get the indices of its neighbors, that are not 9
- Then repeat that for every of those, but also ignore indices already visited.

---

Or implement one of the [flood fill algorithms](https://en.m.wikipedia.org/wiki/Flood_fill) from wikipedia.

---

The alternative to the heightmap as one vector is to have a vector of the rows and access the coordinates with `get-in`.

```clojure
(get-in [[0 1 2]
         [3 4 5]
         [6 7 8]] [1 2])
```

returns the 5 at the end of row two. It also can return not-found value, if the
index does not exist. I think I like that more.

### Day 10
#### Part 1
Find corrupted lines.
Find the first closing bracket in a line that doesn't have corresponding open bracket and score it.
Ignore incomplete lines (where there's open brackets that are not closed (probably part 2)).

The pairs are: `() [] {} <>`

Strategy: go through the characters, if it's an opening bracket, put it on the stack, if it's a closing bracket pop things off the stack until you find a matching open bracket.
If you can't find one, score it.

Although, since the input is only brackets it should be enough to look at the top of the stack. It must be the matching open bracket.

Implemented it with `loop`.
Not sure this is the best way to check if it's a closing bracket:

```clojure 
(some #{c} '(\( \[ \{ \<))
```

I check if the top of the stack is the matching opening bracket with this:

```clojure
(= ({\) \( \] \[ \} \{ \> \<} c)
   (peek stack))
```

#### Part 2
Complete the incomplete lines with closing brackets in the right order.
I think the complicated scoring is to ensure that you actually complete in the right order.

This should work like this:
- go through the line as before, popping things of the stack if they match
- at the end, get the matching bracket for the top of the stack, pop it, repeat until it's empty

To combine the work for part 1 and part 2, the main loop returns a map with the key `:illegal` and the illegal bracket. If it went through the whole line without finding an illegal character, it returns a map with the key `{:stack}` and the stack.

### Day 11
#### Part 1
I can do this similarily to day 9 with nested vectors.
Increase the energy levels of the octopodes with

```clojure
(update-in [[0 1 2] [2 4 6]] [1 2] inc)
```

Unlike other Game of Life like challenges last year, we **do** have chain reactions here.
The challenge is that an octopus only flashes once per step.
How do we mark, that an octopus already flashed?

Maybe the cells should be in a map, mapping from coordinates to their values.
As a nested vector, I find myself generating the coordinates for all of them over and over.

I tried to come up with a `map-kv` function, based on `reduce-kv` that would work with two-dimensional coordinates, but didn't manage to do so.

### Day 13
#### Part 1
We get a list with coordinates for dots on transparent paper and instructions for folding the paper along certain rows/columns.
Dots that overlap after a fold count as one dot.
How many dots are there after all the foldings?

Is it a bad idea to just apply the linear transformation to every vectory?
Can I shortcut that somehow?

For every fold instruction generate the transformation matrix, then multiply each vector (dot) with that matrix.

Quick and dirty vector matrix multiplication:

```clojure
(defn dot-product [a b]
  (reduce + (map * a b))) 

(defn vec-mat-mult [v m]
  (mapv #(dot-product v %) m))
```

--- 
I did it completely differently in the end: TODO:

### Day 14
#### Part 1
We get a string like `NNCB` and insertion rules like `CH -> B`.
This rules says: insert `B` wherever `C` and `H` are next to each other.

They don't need to be in that order, do they? That rule would trigger on `CH` and on `HC`, presumably?

Ah no, they don't! The example has different rules for `NC -> B` and `CN -> C`.

What if I put the rules in a map, mapping from sequences to sequences like this:

```clojure
{'(\N \C) '(\N \B \C)}
```

The overlapping part is tricky. Because I can't just partition the string into pairs, then look up the rule in a map like this.
For the example the following would happen:

The pairs `((\N \N) (\N \C) (\C \B))` would turn into `((\N \C \N) (\N \B \C) (\C \H \B))`, which doubles the outer members.
And if the rule lookup doesn't return the rightmost member of the pair/triple?
We would get `((\N \C) (\N \B) (\C \H))` and that's almost right, except that the last character is missing.
Actually because these are sequences handed around, I'm going to do it the other way around and `conj` the first character to the front of the sequence.

#### Part 2
I suspected that it would be impossible to generate all the strings.
Obviously my part 1 solution doesn't work for part 2 here.
I could try to improve the brute force method, maybe with memoization and also smarter choice of the data models.
But there has to be a smarter solution.

Better idea (maybe):
Start with a map like this:
```clojure
{"nn" 1 "nc" 1 "cb" 1}
```

At every step, 
- set every value to 0
- look up the keys in the rule-map and add the respective pairs with the value of the old keys.

So for example, the key "NN" is 1, so it adds/updates the keys "NC" and "CN" with 1

```clojure
{"nc" 1 "cn" 1 "nb" 1 "bc" 1 "cb" 0 "ch" 1 "hb" 1}
```
