# Gilded Rose Kata

The Gilded Rose kata provides an example of the kind of code we often find in 
[legacy](https://en.wikipedia.org/wiki/Legacy_code#Modern_interpretations) codebases.  The repo provides a 
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
    - Since we most likely don't know how the method-under-test works so use the simplest values possible.  For objects 
    use `null` and for primitives use their default values (int = 0, boolean = false, etc.).
    - This causes a `NullPointerException` when we run the test.  Replace the offending value with a value that will avoid
    the `Exception` and rerun the test. 
    
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
    [side effect](https://en.wikipedia.org/wiki/Side_effect_(computer_science\)) through the `Item` classes' state.
    
1. Identify test coverage
    Either debug the method under test with breakpoints in each logical branch or use a code overage tool to identify
    the impact of the test above. (IntelliJ has built in code coverage 
    https://www.jetbrains.com/help/idea/running-test-with-coverage.html)
    
1. Write Additional Characterization Tests
   Write enough tests to cover all of the method-under-test's conditional behavior
   
### Refactoring

