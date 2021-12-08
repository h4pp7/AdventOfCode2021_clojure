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
