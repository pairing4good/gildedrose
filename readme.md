# Gilded Rose Kata

The Gilded Rose kata provides an example of the kind of code we often find in 
[legacy](https://en.wikipedia.org/wiki/Legacy_code#Modern_interpretations) codebases.  This repo provides a 
step-by-step approach to characterization testing and [refactoring](https://en.wikipedia.org/wiki/Code_refactoring) 
this code. 

> "Michael Feathers introduced a definition of legacy code as code without tests" 
https://en.wikipedia.org/wiki/Legacy_code#Modern_interpretations

## The Problem 
Before we change any code let's identify what problem we are solving. First, if you are like me the GildedRose class 
is difficult to understand. Second, there are no tests in place to protect me if I want to clean it up. 

## The Approach 
There is a [chicken or the egg](https://en.wikipedia.org/wiki/Chicken_or_the_egg) problem.  If we try to test the 
existing code without making any changes, the resulting test will be difficult to write and as 
[complex](https://en.wikipedia.org/wiki/Cyclomatic_complexity) as the existing code. On the other hand, if we make 
changes to the code without the safety net of test automation then we risk breaking existing functionality.  So where
should we start?  
1. Add high-level [characterization](https://michaelfeathers.silvrback.com/characterization-testing) tests that guard 
overall behavior 
([testing pyramid](https://martinfowler.com/articles/practical-test-pyramid.html))
1. [Extract methods](https://refactoring.com/catalog/extractFunction.html) to improve readability
1. [Extract classes](https://refactoring.com/catalog/extractClass.html) to form classes with 
[single responsibilities](https://en.wikipedia.org/wiki/Single-responsibility_principle)
1. [Inject](https://en.wikipedia.org/wiki/Dependency_inversion_principle) new classes back into the original class

## Walk-Through

### Characterization Testing
1. Write the first [characterization test](https://michaelfeathers.silvrback.com/characterization-testing) for the  
`GildedRose` class.
    ```java
    @Test
        public void updateQuality() {
            Item[] items = {new Item(null, 0, 0)};
            GildedRose gildedRose = new GildedRose(items);
    
            gildedRose.updateQuality();
        }
    ```
    - Since we most likely don't know how the method-under-test works, use the simplest values possible.  For objects 
    use `null` and for primitives use their 
    [default](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html) values.
    - This initial test with default Item values, causes a `NullPointerException` when we run the test.  Replace the 
    offending value with a value that will avoid the `Exception` and rerun the test. 
    
    ```java
    Item[] items = {new Item("", 0, 0)};
    ```
1. Add test assertions for the resulting behavior.
    ```java
        @Test
        public void updateQuality() {
            Item firstItem = new Item("", 0, 0);
            Item[] items = {firstItem};
            GildedRose gildedRose = new GildedRose(items);
    
            gildedRose.updateQuality();
    
            assertEquals("", firstItem.name);
            assertEquals(-1, firstItem.sellIn);
            assertEquals(0, firstItem.quality);
        }
    ```
    - The `updateQuality` method returns `void` so we need to test its 
    [side effect](https://en.wikipedia.org/wiki/Side_effect_(computer_science)) through the `Item` classes' 
    [data](https://en.wikipedia.org/wiki/Object-oriented_programming) or state.
    
1. In order to identify the resulting test coverage, either debug the method under test with breakpoints in each 
logical branch or use a code coverage tool to identify the impact of the test above. (IntelliJ has built in code 
coverage https://www.jetbrains.com/help/idea/running-test-with-coverage.html)
    
1. Write additional characterization tests until there are enough tests to cover all of the method-under-test's 
conditional behavior.
   
### Refactoring
>“Clean code is simple and direct. Clean code reads like well-written [prose](https://en.wikipedia.org/wiki/Prose). 
Clean code never obscures the designer’s intent but rather is full of crisp abstractions and straightforward lines 
of control." - Grady Booch

>"Refactoring is a disciplined technique for restructuring an existing body of code, altering its internal structure 
__without changing its external behavior__." - Martin Fowler

The objective of refactoring is to incrementally improve code so that it is easier to understand and maintain.  The 
only way that you can ensure that you did not change the external behavior of an application is to first constrain the
code with automated test accountability.  Once this is in place, then and only then, can you begin to refactor.
Refactoring is often applied a little bit at a time through incremental changes.  After every change, run the tests to 
ensure that the change did not break anything.  

In order to identify refactoring opportunities, follow your nose.  What doesn't smell quite right?  Martin Fowler 
captures many of these [code smells](https://martinfowler.com/bliki/CodeSmell.html) in his book 
[Refactoring](https://www.amazon.com/Refactoring-Improving-Design-Existing-Code/dp/0201485672).  (Sandi Metz's 
[talk](https://youtu.be/D4auWwMsEnY) on code smells is a great way to get your head around this idea.)

#### Code Smells
In the method-under-test `updateQuality`, the following smells pop out at me:
- **Too Long**: The method is long enough that I have to scroll to see it all
- **Too Many Responsibilities**: The method does a lot of things
- **Not [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself)**: There's a lot of duplicated concepts
- **Too Complex**: The nested if/else structure is several layers deep and difficult to follow
- **Not Clear**: Many of the conditions use `!` to invert the condition

While the first thing that pops out to me is the length of the method, I won't try to tackle that first.  I want to
make small incremental changes that transform this method into code that "reads like well-written prose"

### Incremental Refactoring
>Remember to make a small change, re-run tests, and **always** commit on green.

**Idea 1**: I wonder if the item's `name`, `quality` and `sellIn` values were extracted into temporary variabes if the code would be 
any clearer.  I used IntelliJ's `Refactor -> Extract -> Variable...` 
[feature](https://www.jetbrains.com/help/idea/extract-variable.html) to create temporary variables.

1. Extract `items[i].name` into a temporary variable and replace all 8 occurrences.  Run the tests and commit on green.
1. Extract `items[i].quality` and replace all 22 occurrences in the code. Run tests and they are red.  So what happened?
At this point you need to [revert](https://git-scm.com/docs/git-revert) your changes.
1. Extract `items[i].quality`, round two.  I realize that the temporary variable `quality` is being 
[mutated](https://en.wikipedia.org/wiki/Immutable_object) throughout the method.  So in this second round I set the 
`items[i].quality = quality;` at the end of each for loop.  I run the tests and they go green so I commit on green.
1. I apply the same pattern to `items[i].sellIn` because it is mutated too.  Run the tests and commit on green.

**Outcome 1**: Pulling out these temporary variables makes it a little more readable.  It removed visual clutter that 
makes the code harder to read.

**Idea 2**: I see a lot of duplicated concepts that could be extracted into private methods.  This could make it more 
readable and centralize each idea into a single place.  I used IntelliJ's `Refactor -> Extract -> Method...` 
[feature](https://www.jetbrains.com/help/rider/Refactorings__Extract_Method.html) to create private methods.

1. Extract `quality = quality - 1` into a private method with the following signature 
`private int decreaseByOne(int value)`.  Since this signature takes an `int` this also applied to `sellIn = sellIn - 1`.
This new private method replaced 3 `x = x - 1` patterns. Run the tests and commit on green.
1. Extract `quality = quality + 1` into a private method with the following signature 
`private int increaseByOne(int value)`.  This new private method replaced 3 `x = x + 1` patterns. Run the tests and 
commit on green.
1. Extract `quality = quality - quality` into a private method with the following signature 
`private int zeroOut(int value)`.  This new private method replaced 1 `x = x - x` pattern. Run the tests and commit 
on green.
1. Extract the condition `quality < 50` into a private method with the following signature 
`private boolean isLessThanMax(int value)`.  This new private method replaced 4 `x < 50` patterns. I named it 
`isLessThanMax` because the conditional check `isLessThanMax(quality)` is always paired with `increaseByOne(quality)` 
which prevents the quantity from increasing once max quality is reached.
Run the tests and commit on green.
1. Extract the paired structure of checking for max quality before increasing the quality.  This new private method 
replaced 3 of the following patterns:
    ```java
    private int increaseQuality(int quality) {
        if (isLessThanMax(quality)) {
            quality = increaseByOne(quality);
        }
        return quality;
    }
   ```
   By encapsulating this check before increasing quality together it makes this a single, named, reusable concept. 
   Run the tests and commit.

**Outcome 2**: Pulling out private methods increased clarity within the code and started to paint a picture of the
different responsibilities within the `GildedRose` class.  As I look through the resulting private methods I see two
possible responsibility themes emerging:
1. Quality related theme
    ```java
    private int increaseQuality(int quality) {
        if (isLessThanMax(quality)) {
            quality = increaseByOne(quality);
        }
        return quality;
    }

    private boolean isLessThanMax(int value) {
        return value < MAX_QUALITY;
    }
    ```
2. Math related theme
    ```java
    private int zeroOut(int value) {
        return value - value;
    }

    private int increaseByOne(int value) {
        return value + 1;
    }

    private int decreaseByOne(int value) {
        return value - 1;
    }
    ```
Eventually these groups of methods will be pulled out of the `GildedRose` class and organized in new classes with a 
[single responsibility](https://en.wikipedia.org/wiki/Single-responsibility_principle).

**Idea 3**: I would like to flip the conditional statements so they don't include `!` which inverts the condition.  It
is more natural to state the positive case rather than the negative case.
1. Flip the conditional statement to remove the `!`'s from the conditional statement 
`!name.equals("Aged Brie") && !name.equals("Backstage passes to a TAFKAL80ETC concert")`.  (The `&&` will need to be 
flipped to `||`.)  Run the tests and commit on green.
1. Flip the conditional statement to remove the `!`'s from the conditional statement `!name.equals("Aged Brie")`.
Run the tests and commit on green.
1. Flip the conditional statement to remove the `!`'s from the conditional statement 
`!name.equals("Backstage passes to a TAFKAL80ETC concert")`. Run the tests and commit on green.
1. I attempted to remove the `!` from `!name.equals("Sulfuras, Hand of Ragnaros")` and flip the condition but the 
resulting structure was longer and less readable than what I started with so I reverted my changes.
    ```java
                if (name.equals("Sulfuras, Hand of Ragnaros")) {
                }else{
                    sellIn = decreaseByOne(sellIn);
                }
    ```
**Outcome 3**: Inverting conditional statements in order to remove `!` and telling the positive narritive rather than
negative case increases readability.  Furthermore, the addition of `!` is difficult to see and can create confusion and 
an incorrect understanding of the code.  While I was unable to flip all of the negative case conditions to be positive, 
the overall flow of the code is easier to read.
