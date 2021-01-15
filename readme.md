# Gilded Rose Kata

The Gilded Rose kata provides an example of the kind of code we often find in 
[legacy](https://en.wikipedia.org/wiki/Legacy_code#Modern_interpretations) codebases.  The repo provides a 
step-by-step approach to restructuring, testing and [refactoring](https://en.wikipedia.org/wiki/Code_refactoring) 
this code. 

## The Problem 
Before we change any code let's identify what problem we are solving. First, if you are like me the GildedRose class 
is difficult to understand. Second, there are no tests in place to protect me if I want to clean it up. 

## The Approach 
There is a [chicken or the egg](https://en.wikipedia.org/wiki/Chicken_or_the_egg) problem.  If we try to test the 
existing code without making any changes, the resulting test will be difficult to write and as 
[complex](https://en.wikipedia.org/wiki/Cyclomatic_complexity) as the existing code. On the other hand, if we make 
changes to the code without the safety net of test automation then we risk breaking existing functionality.  So where
should we start?  
1. Add a few high-level tests that guard overall behavior 
([testing pyramid](https://martinfowler.com/articles/practical-test-pyramid.html))
2. [Extract methods](https://refactoring.com/catalog/extractFunction.html) to improve readability
3. [Extract classes](https://refactoring.com/catalog/extractClass.html) to form classes with 
[single responsibilities](https://en.wikipedia.org/wiki/Single-responsibility_principle)
4. [Inject](https://en.wikipedia.org/wiki/Dependency_inversion_principle) new classes back into the original class

## Walk-Through
