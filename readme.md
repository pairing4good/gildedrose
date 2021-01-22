# Gilded Rose Kata

The Gilded Rose kata provides an example of the kind of code we often find in 
[legacy](https://en.wikipedia.org/wiki/Legacy_code#Modern_interpretations) codebases.  This repo provides a 
step-by-step approach to characterization testing and [refactoring](https://en.wikipedia.org/wiki/Code_refactoring) 
this code. The commit history of this repo captures incremental `readme.md` explanations with matching code changes. 
This step-by-step commit approach gives readers a view into my thought process and journey.  As you will see much of 
refactoring is experimentation rather than a clear A to Z process.  While I decided to follow the steps I committed, 
it's only one of many ways to incrementally improve this code base. Follow your own instincts and see if your changes
lead to cleaner code while remaining on green.  Enjoy the journey. 

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
**code snapshot** [code before tests](https://github.com/pairing4good/gildedrose/commit/481d82aeee33e35bb965f155001e6d6e472ea715)

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

**code snapshot** [characterization tests](https://github.com/pairing4good/gildedrose/commit/8c1e72b49c587e082fb7b496d80b006f47d6a11b)
   
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

1. Extract `items[i].name` into a temporary variable and replace all 8 occurrences.  Run the tests and commit on green.\
    **code snapshot** [extract name variable](https://github.com/pairing4good/gildedrose/commit/1cff29720767ea8d595c6c6f27a47f7f70f08122)
1. Extract `items[i].quality` and replace all 22 occurrences in the code. Run tests and they are red.  So what happened?
At this point you need to [revert](https://git-scm.com/docs/git-revert) your changes.
1. Extract `items[i].quality`, round two.  I realize that the temporary variable `quality` is being 
[mutated](https://en.wikipedia.org/wiki/Immutable_object) throughout the method.  So in this second round I set the 
`items[i].quality = quality;` at the end of each for loop.  I run the tests and they go green so I commit on green.\
    **code snapshot** [extract quality variable](https://github.com/pairing4good/gildedrose/commit/86d9e5de3ef1ab39339c27194ce7b3fee7f3e3be)
1. I apply the same pattern to `items[i].sellIn` because it is mutated too.  Run the tests and commit on green.\
    **code snapshot** [extract sellIn variable](https://github.com/pairing4good/gildedrose/commit/63c3a627ceb32bd56d99610787aa8737a14683eb)

**Outcome 1**: Pulling out these temporary variables makes it a little more readable.  It removed visual clutter that 
makes the code harder to read.

**Idea 2**: I see a lot of duplicated concepts that could be extracted into private methods.  This could make it more 
readable and centralize each idea into a single place.  I used IntelliJ's `Refactor -> Extract -> Method...` 
[feature](https://www.jetbrains.com/help/rider/Refactorings__Extract_Method.html) to create private methods.

1. Extract `quality = quality - 1` into a private method with the following signature 
`private int decreaseByOne(int value)`.  Since this signature takes an `int` this also applied to `sellIn = sellIn - 1`.
This new private method replaced 3 `x = x - 1` patterns. Run the tests and commit on green.\
    **code snapshot** [extract decreaseByOne method](https://github.com/pairing4good/gildedrose/commit/3dd82c539451626d8e3622ba73475bfb11c3a9b2)
1. Extract `quality = quality + 1` into a private method with the following signature 
`private int increaseByOne(int value)`.  This new private method replaced 3 `x = x + 1` patterns. Run the tests and 
commit on green.\
    **code snapshot** [extract increaseByOne method](https://github.com/pairing4good/gildedrose/commit/ee6cd0aa863d2de58fe3b58de7c23b5c04b93478)
1. Extract `quality = quality - quality` into a private method with the following signature 
`private int zeroOut(int value)`.  This new private method replaced 1 `x = x - x` pattern. Run the tests and commit 
on green.\
    **code snapshot** [extract zeroOut method](https://github.com/pairing4good/gildedrose/commit/f713e2cc88e70570d0a7923d0438cbe979a519a0)
1. Extract the condition `quality < 50` into a private method with the following signature 
`private boolean isLessThanMax(int value)`.  This new private method replaced 4 `x < 50` patterns. I named it 
`isLessThanMax` because the conditional check `isLessThanMax(quality)` is always paired with `increaseByOne(quality)` 
which prevents the quantity from increasing once max quality is reached. Run the tests and commit on green.\
    **code snapshot** [extract isLessThanMax method](https://github.com/pairing4good/gildedrose/commit/09fd08fd442b3b47b3d156d7cbc5f43f0750d64d)
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
   Run the tests and commit.\
   **code snapshot** [extract increaseQuality method](https://github.com/pairing4good/gildedrose/commit/ca4b4941c3d2547f4f5091a46609b4af46ec2060)

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
flipped to `||`.)  Run the tests and commit on green.\
    **code snapshot** [flip conditional statement](https://github.com/pairing4good/gildedrose/commit/f683414f4c27e5a2c98238f6b40c04e4b5f41033)
1. Flip the conditional statement to remove the `!`'s from the conditional statement `!name.equals("Aged Brie")`.
Run the tests and commit on green.\
    **code snapshot** [flip conditional statement](https://github.com/pairing4good/gildedrose/commit/20a6115ea32900a2bdc10a723406e387fc1389f2)
1. Flip the conditional statement to remove the `!`'s from the conditional statement 
`!name.equals("Backstage passes to a TAFKAL80ETC concert")`. Run the tests and commit on green.\
    **code snapshot** [flip conditional statement](https://github.com/pairing4good/gildedrose/commit/b5a4d6759028d8e0dd338166d0642c6ca40926ee)
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

**Idea 4**: I would like to collapse some of the levels of conditional nesting.  This would reduce complexity and
simplify separate logical conditions.
1. Move `if (name.equals("Backstage passes to a TAFKAL80ETC concert"))` up to the same level as 
`if (name.equals("Aged Brie") || name.equals("Backstage passes to a TAFKAL80ETC concert"))`. 
Run the tests and commit on green.\
    **code snapshot** [collapse nested conditional statement](https://github.com/pairing4good/gildedrose/commit/9c0bdeae59fb6bb3b6b83a24ae0ebc5015bb38aa)
1. Replace the `isLessThanMax(quality)` check and `quality = increaseByOne(quality)` pair with a call to the method 
`increaseQuality` instead. Run the tests and commit on green.\
    **code snapshot** [extract increaseQuality method](https://github.com/pairing4good/gildedrose/commit/c4b11148b1aff17bf2a4c15869baac120be60f0a)
1. Move `if (name.equals("Backstage passes to a TAFKAL80ETC concert"))` up to the same level as 
`if (name.equals("Aged Brie"))`. Run the tests and commit on green.\
    **code snapshot** [collapse nested conditional statement](https://github.com/pairing4good/gildedrose/commit/6f7e063f593f818c77a92f910b80baac7e04bab9)
1. Combine nested if's as `&&` conditions.  Run the tests and commit on green.\
    **code snapshot** [collapse nested conditional statement](https://github.com/pairing4good/gildedrose/commit/5f25618ed0d196b4e4b89681ef770ced3287dde2)

**Outcome 4**: Collapsing nested conditional statements increased the complexity of conditional statements and increase
logical duplication.  However the overall [cyclomatic complexity](https://en.wikipedia.org/wiki/Cyclomatic_complexity) 
of the code has dropped significantly.

**Idea 5**: I would like to extract the `if (quality > 0)` with `quality = decreaseByOne(quality)` as 
`int decreaseQuality(int quality)`.\
    **code snapshot** [extract decreaseQuality method](https://github.com/pairing4good/gildedrose/commit/d4c98b6354d62897f91d6266cf17164b9768e5a1)

**Outcome 5**: Extracting this method sigificantly simplified the code, made it more readable and uncovered additional 
opportunities to collapse nested if's.

**Idea 6**: I would like to simplify if/else structures to independent if statements.  Once this is in place I wonder 
if patterns will become evident that may lead to the next refactoring.\
    **code snapshot** [split if/else statements](https://github.com/pairing4good/gildedrose/commit/4bdfa828d25c4867bc06e766518b11e13e526a07)

**Outcome 6**: Moving to independent if statements increased duplication and made it more difficult to read.  Hopefully 
independent statements will make it easier to identify patterns.

**Idea 7**: I would like to extract string names into constants to remove duplication\
    **code snapshot** [extract constants](https://github.com/pairing4good/gildedrose/commit/b25e371cfe2967a10f40058d34f99ae4f04ec83d)

**Outcome 7**: Extracting constants increases overall readability.

**Idea 8**: I would like to encapsulate name checking into two methods in order to reduce the number of 
`name.equals` calls within conditional statements.\
    **code snapshot** [extract methods](https://github.com/pairing4good/gildedrose/commit/5638bb3af73e1201953396afb6d91d5eab530f5d)

**Outcome 8**:  Encapsulating name checking into two methods significantly increased readability and simplified code.

### Extract Class
Now that much of the code is extracted out into reusable private methods, it's time to pull these methods out into new
classes that have [single responsibilities](https://en.wikipedia.org/wiki/Single-responsibility_principle).
1. Organize methods that have similar responsibilities together.
1. Create a new class that describes this new responsibility.
1. Copy the methods that belong in this new class and paste them in the new class.
1. Inject the new class through 
[constructor injection](https://en.wikipedia.org/wiki/Dependency_injection#Constructor_injection) into the 
`GildedRose` class.
1. Cut over to use the methods from the new class.
1. Delete the unused private methods.\
    **code snapshot** [extract classes](https://github.com/pairing4good/gildedrose/commit/79ea15faf135af90b44fe253df715026dc7b9f05)

The `GildedRose` class has fewer responsibilities.  While the names of the resulting classes are still unclear without 
more business context, they contain what appear to be single responsibilities that can be tested independently from the 
`GildedRose` class.

#### Remaining Complexity
The resulting `GildedRose` `updateQuality` method is much shorter, less complex, more readable and has fewer 
responsibilities.  Nevertheless, the condition/action pairs still seem like conceptual duplication.  This 
check a condition and do something pattern is similar to a rules engine approach.  What if each of these
`if` statements could be extracted into their own classes and injected into this class as a list or multiple
lists of rules?

1. Pull out a single quality increase rule into a class.
1. Inject this rule into a list of rules used by the `GildedRose` class.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/4fef8e1eac37461fd45e7a5c0d461b970a2cdf49)
1. Pull out a second quality increase rule into a class.
1. Pull out a [common interface](https://en.wikipedia.org/wiki/Interface_segregation_principle) so that the rules can 
be [run together](https://en.wikipedia.org/wiki/Polymorphism_(computer_science)) in a single list.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/1ac3c8e4e5c63595995c02bf838d1f55b94a76bf)

    **Observation** As more and more [single responsibilities](https://en.wikipedia.org/wiki/Single-responsibility_principle) 
    classes are created and [reinjected](https://en.wikipedia.org/wiki/Dependency_injection#Constructor_injection) through 
    class constructors, class creation is getting more and more difficult.
    
    **Idea** I would like to move class creation to a [factory](https://en.wikipedia.org/wiki/Factory_method_pattern) in 
    order to encapsulate creation knowledge into a single place.
    1. Move the `Item[]` array out of the `GildedRose` constructor and add it as a parameter in the `updateQuality`.  It's 
    not clear why the `GildedRose` needs to hold the items in class level variables.  The `Item[]`
    [data](https://en.wikipedia.org/wiki/Object-oriented_programming) or state is only modified in a single method.  In 
    contrast, the utilities are stateless and should remain in the constructor.\
    **code snapshot** [move item array from constructor to method parameter](https://github.com/pairing4good/gildedrose/commit/f5d539f1f16f918e621b11c6d4934c663f50b59a)
    1. Remove the `updateQuality` methods [side effects](https://en.wikipedia.org/wiki/Side_effect_(computer_science)) and
    move toward a more [functional](https://en.wikipedia.org/wiki/Functional_programming) style programing paradigm.  
    By returning a new `Item[]` each time the `updateQuality` method is called helps to move towards this new programming 
    paradigm.\
    **code snapshot** [functional style method](https://github.com/pairing4good/gildedrose/commit/9c0470f8a50ccab5bf1543200f23c442c6c5f9f1)
    1. Create a [factory](https://en.wikipedia.org/wiki/Factory_method_pattern) that is responsible for the creation of 
    the `GildedRose` in order to encapsulate complex creation knowledge in a single place.\
    **code snapshot** [create factory class](https://github.com/pairing4good/gildedrose/commit/b57041aaf18ee23f4e45f14dc2f907cc0fa79ad2)
1. Pull out a third quality increase rule into a class.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/5e3614d18078fd933728df95ebcdbce061bb3a9e)
1. Pull out a fourth quality increase rule into a class.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/55c78995eca7f41ff80757834a5712133eddde52)
1. Pull out the first quality decrease rule.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/8956867b49c35fea67365fed6cff927a734e7b1d)
1. Pull out another quality decrease rule.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/1245aa6cd9033d7099eeac47478ad5b18837c347)
1. Pull out the first sell in rule.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/2b71b2a2c943782b70db0f92063bf664664aa2d3)
1. Pull out the final rule.\
    **code snapshot** [extract rule](https://github.com/pairing4good/gildedrose/commit/f9ac577d31fda8921f30260be16605751f05cb78)

**Outcomes** The `GildedRose` class is small and has a clear responsibility.  Each rule is now encapsulated into 
individual named classes with a clear, single responsibility.  Each rule has been independently tested.  The 
`GildedRose` class now follows the [Open Closed Principle](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle) 
which makes adding a new rule much simpler and independent from other rules.  This application is now
[loosely coupled](https://en.wikipedia.org/wiki/Loose_coupling) which makes it easier to test and change.  Tests moved
from high level, black box tests that ran the entire application, to small focused tests that test out all of the 
scenarios related to the method under test.

## Conclusion
Working with legacy code requires patience and purposeful moves that slowly improve the code base.  As Kerievsky points
out in his book [Refactoring to Patterns](https://www.amazon.com/Refactoring-Patterns-Joshua-Kerievsky/dp/0321213351) we 
often move towards a desired destination or pattern.  But as we reshape the code, while remaining on green, it often 
becomes clear that we need to move back away from a pattern too.  Often the best code does not follow any pattern 
perfectly.  Our desired outcome is clean code that reads like well-written prose, not "perfect" code that follows all 
the "right", "best" practices.  Refactoring is like peeling an onion.  As you work your way through each idea you will 
uncover new layers of insights.  

I intentionally did not have a plan before I started this journey.  I wanted to expose 
my own thought process and resulting journey.  Many of my ideas did not lead the the outcomes I expected.  Many of my 
short plays resulted in uglier code, in the short run.  I was constantly testing ideas and seeing what happened.  This 
approach is not only good for legacy code but it is also good for all code.  My first ideas are not my best ideas. Many 
things are not clear until I try them.  Follow your nose.  Try things.  Make mistakes.  Incrementally improve your 
code.  Shape your code into beautiful prose.
