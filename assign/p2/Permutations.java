import comp1110.lib.*;
import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;

/**
 * Design Strategy: Combining Functions
 * 
 * Purpose:
 * Determines if two lists are permutations of each other by:
 * 1. Sorting both lists (using Sort function)
 * 2. Comparing elements recursively (using First, Rest, Equals)
 * 3. Checking lengths (using Length)
 *
 * Template:
 * - Uses ConsList operations: IsEmpty, First, Rest
 * - Combines with helper functions: Sort, Length, Equals
 * 
 * Base Cases:
 * 1. Both lists empty -> true
 * 2. Different lengths -> false
 * 3. Different first elements -> false
 * 
 * Properties:
 * - Commutative: isPermutation(a,b) == isPermutation(b,a) 
 * - Reflexive: isPermutation(a,a) == true
 * - Transitive: if isPermutation(a,b) && isPermutation(b,c), then isPermutation(a,c)
 * 
 * Examples:
 * - isPermutation([1,2,3], [2,1,3]) -> true 
 * - isPermutation([1,2], [1,3]) -> false
 * - isPermutation([], []) -> true
 *
 * @param list1 First list to check
 * @param list2 Second list to check  
 * @return true if lists are permutations, false otherwise
 */
boolean isPermutation(ConsList<Integer> list1, ConsList<Integer> list2){
    // Base case: if both lists are empty, they are permutations
    if (IsEmpty(list1) && IsEmpty(list2)){
        return true;
    }

    // Lists of different lengths cannot be permutations
    if (Length(list1) != Length(list2)){
        return false;
    }

    // Sort both lists to normalize element order
    ConsList<Integer> sortedList1 = Sort(list1);
    ConsList<Integer> sortedList2 = Sort(list2);

    // Compare the first elements of both sorted lists
    if (Equals(First(sortedList1),First(sortedList2))){
        // If first elements match, recursively check remaining elements
        return isPermutation(Rest(sortedList1), Rest(sortedList2));
    } else {
        // If any elements don't match, lists are not permutations
        return false;
    }
}

/** 
 * Tests the isPermutation method with simple cases
 *
 * Examples:
 * given: [1,2,3,4], [2,3,4,1]
 *  expect: true
 * given: [1,2,3,4], [1,2,3,5]
 *  expect: false
 * given: [1,2,3,4,5], [1,2,3,5,5]
 *  expect: false
 */
void testIsPermutationSimple(){
    ConsList<Integer> list1 = MakeList(1,2,3,4);
    ConsList<Integer> list2 = MakeList(2,3,4,1);
    ConsList<Integer> list3 = MakeList(1,2,3,4,5);
    ConsList<Integer> list4 = MakeList(1,2,3,5,5);
    testTrue(isPermutation(list1, list2));
    testFalse(isPermutation(list1, list3));
    testFalse(isPermutation(list3, list4));
}

/** 
 * Tests the isPermutation method with large numbers
 *
 * Examples:
 * given: [1,2,3,5,100000000], [1,2,3,5,-100000000]
 *  expect: false
 * given: [1,2,3,5,100000000], [2,3,5,100000000,1]
 *  expect: true
 */
void testIsPermutationLargeNum(){
    ConsList<Integer> list5 = MakeList(1,2,3,5,100_000_000);
    ConsList<Integer> list6 = MakeList(1,2,3,5,-100_000_000);
    ConsList<Integer> list7 = MakeList(2,3,5,100_000_000,1);
    testFalse(isPermutation(list5, list6));
    testTrue(isPermutation(list5, list7));
}

/**
 * Design Strategy: Case Distinction
 * 
 * Purpose:  
 * Compares two lists lexicographically by examining elements from left to right.
 * Returns an integer indicating relative ordering.
 * 
 * Cases:
 * 1. Both lists empty -> equal (0)
 * 2. First elements different -> compare first elements
 * 3. First elements same -> compare rest of lists
 * 
 * Base Cases/Edge Cases:
 * - Empty lists handling
 * - Different length lists
 * - Large number comparisons
 * 
 * Guarantees:
 * - Transitive: if a<b and b<c then a<c
 * - Antisymmetric: if a<b then not b<a
 * - Total ordering: exactly one of a<b, a=b, or a>b is true
 * 
 * Example:
 * - ([1,2,3], [1,2,4]) -> negative (3 < 4)
 * - ([1,2,3], [1,2,3]) -> 0 (identical)
 * - ([2,1,1], [1,2,2]) -> positive (2 > 1)
 *
 * @param list1 First list to compare
 * @param list2 Second list to compare
 * @return Negative if list1<list2, 0 if equal, positive if list1>list2
 */
int compareLists(ConsList<Integer> list1, ConsList<Integer> list2){
    // Base case check
    if (IsEmpty(list1) && IsEmpty(list2)){
        return 0;
    }

    // Main case distinction
    Integer firstOfList1 = First(list1);
    Integer firstOfList2 = First(list2);
    if (!Equals(firstOfList1, firstOfList2)){
        return Compare(firstOfList1, firstOfList2);
    } else{
        return compareLists(Rest(list1), Rest(list2));
    }

}

/** 
 * Tests the compareLists method
 *
 * Examples:
 * given: [1,2,3,5,100000000], [1,2,3,5,100000000]
 *  expect: 0
 * given: [1,2,3,5,100000000], [2,3,5,100000000,1]
 *  expect: -1
 * given: [2,3,5,100000000,1], [1,2,3,5,100000000]
 *  expect: 1
 */
void testCompareLists(){
    ConsList<Integer> list1 = MakeList(1,2,3,5,100_000_000);
    ConsList<Integer> list2 = MakeList(1,2,3,5,100_000_000);
    ConsList<Integer> list3 = MakeList(2,3,5,100_000_000,1);
    testTrue(compareLists(list1, list2) == 0);
    testFalse(compareLists(list1, list3) == 0);
    testTrue(compareLists(list1, list3) < 0);
    testTrue(compareLists(list3, list2) > 0);
}

/**
 * Design Strategy: Case Distinction
 * 
 * Purpose:
 * Creates a new list with an element inserted at a specified position.
 * Preserves the original list while building the result.
 *
 * Cases:
 * 1. Invalid index -> return original list
 * 2. Insert at start (index = 0) -> prepend element
 * 3. Empty list -> return original list
 * 4. Regular case -> recursively build with element inserted
 *
 * Base Cases/Edge Cases:
 * - index < 0: return original list
 * - index > length: return original list 
 * - Empty list: return list if index != 0
 * - Insert at start: create new list with element as first
 *
 * Examples:
 * Given list [1,2,3]:
 * - insert(6, 0, [1,2,3]) -> [6,1,2,3]
 * - insert(6, 2, [1,2,3]) -> [1,2,6,3]
 * - insert(6, 3, [1,2,3]) -> [1,2,3,6]
 * - insert(6, -1, [1,2,3]) -> [1,2,3]
 *
 * @param element Element to insert
 * @param index Position to insert at (0-based)
 * @param list Original list
 * @return New list with element inserted at specified position
 */
ConsList<Integer> insert(int element, int index, ConsList<Integer> list){
    // Case distinction on index validity
    if(index < 0 || index > Length(list)){
        return list;
    }

    // Case distinction for insertion at start
    if(index == 0){
        ConsList<Integer> prefix = MakeList(element);
        return Append(prefix, list);
    }

    // Case distinction for empty list
    if(IsEmpty(list)){
        return list;
    }

    // Recursive case: build result by inserting at correct position
    return Append(MakeList(First(list)), insert(element, index - 1, Rest(list)));
}

/** 
 * Tests the insertTest method
 *
 * Examples:
 * given: 3, 2, [1,2,3]
 *  expect: [1,2,3,3]
 * given: 6, 0, [1,2,3,4,5]
 *  expect: [6,1,2,3,4,5]
 * given: 0, 5, [1,2,3]
 *  expect: [1,2,3,0]
 */
void testInsert(){
    // Original test
    ConsList<Integer> list1 = MakeList(1,2,3);
    ConsList<Integer> list2 = MakeList(1,2,3,3);
    testEqual(list2, insert(3,2,list1));

    // Test case 1: Insert large number in middle
    ConsList<Integer> largeList1 = MakeList(1,2,3,4,5);
    ConsList<Integer> expectedLarge1 = MakeList(1,2,100_000_000,3,4,5);
    testEqual(expectedLarge1, insert(100_000_000, 2, largeList1));

    // Test case 2: Insert at start of list with large numbers
    ConsList<Integer> largeList2 = MakeList(100_000_000,200_000_000,300_000_000);
    ConsList<Integer> expectedLarge2 = MakeList(1,100_000_000,200_000_000,300_000_000);
    testEqual(expectedLarge2, insert(1, 0, largeList2));

    // Test case 3: Insert large number before another large number
    ConsList<Integer> largeList3 = MakeList(1,2,3,100_000_000);
    ConsList<Integer> expectedLarge3 = MakeList(1,2,3,999_999_999,100_000_000);
    testEqual(expectedLarge3, insert(999_999_999, 3, largeList3));
}

/**
 * Design Strategy: Case Distinction with Template Application
 * 
 * Purpose:
 * Generates all possible permutations of a given list by:
 * 1. Starting with empty result
 * 2. Systematically selecting elements one at a time
 * 3. Building permutations recursively
 *
 * Cases:
 * 1. Empty list -> return empty permutation
 * 2. Non-empty list -> delegate to helper function
 *
 * Template Used:
 * - ConsList operations: IsEmpty, First, Rest
 * - List building: MakeList, Append
 *
 * Example Process:
 * Input: [1,2]
 * Steps:
 * 1. [] + [1,2] remaining
 * 2. [1] + [2] remaining -> [1,2]
 * 3. [2] + [1] remaining -> [2,1]
 * Result: [[1,2], [2,1]]
 *
 * @param list Input list to generate permutations from
 * @return List of all possible permutations
 */
ConsList<ConsList<Integer>> permutations(ConsList<Integer> list) {
    // Start with empty current list
    return permutationsHelper(list, new Nil<Integer>());
}

/**
 * Design Strategy: Case Distinction
 * 
 * Purpose:
 * Helper function that builds permutations recursively
 *
 * Cases:
 * 1. No remaining elements -> return current permutation
 * 2. Has remaining elements -> try each as next element
 *
 * Base Cases:
 * - Empty remaining list -> current list is complete permutation
 * - Non-empty -> generate permutations for each remaining element
 *
 * @param remaining Elements not yet used
 * @param current Current partial permutation
 * @return All permutations possible from this state
 */
ConsList<ConsList<Integer>> permutationsHelper(ConsList<Integer> remaining, ConsList<Integer> current) {
     // Base case: if no elements remain, current list is a complete permutation
    if (IsEmpty(remaining)) {
        return MakeList(current);
    } else {
        // Otherwise, try each remaining element as the next choice
        return permutationsForElement(remaining, current, remaining, new Nil<ConsList<Integer>>());
    }
}

/**
 * Design Strategy: Recursive List Processing with Case Distinction
 * 
 * Purpose: Removes all occurrences of a specific element from a list
 * 
 * Template Used:
 * - List operations: Cons, Nil
 * - Element comparison: Equals
 * 
 * Example:
 * Input: list=[1,2,3,2], element=2
 * Output: [1,3]
 * 
 * @param <T> Generic type of list elements
 * @param list Input list to remove from
 * @param element Element to remove
 * @return New list with element removed
 */
<T> ConsList<T> Remove(ConsList<T> list, T element) {
    return switch (list) {
    
    // Base case: empty list
    case Nil<T>() -> list;
    
    // Recursive case: non-empty list
    case Cons<T>(T first, ConsList<T> rest) -> {
        
        // If current element matches element to remove
        if (Equals(first, element)) { 
            yield rest; // Skip the current element and return directly to the remainder
        } else {
            
            // Keep the current element and recursively process the rest
            yield new Cons<T>(first, Remove(rest, element));
            }
        }
    };
}

/**
 * Processes permutations for each element in the remaining list
 * Strategy: For each element:
 * 1. Remove it from available elements
 * 2. Add it to current permutation
 * 3. Recursively process remaining elements
 * 4. Accumulate all results
 *
 * @param original Original list for element removal reference
 * @param current Current partial permutation
 * @param remaining Elements to be processed in current iteration
 * @param result Accumulated permutations
 * @return Complete list of permutations for current branch
 */
ConsList<ConsList<Integer>> permutationsForElement(ConsList<Integer> original, ConsList<Integer> current,
        ConsList<Integer> remaining, ConsList<ConsList<Integer>> result) {
    // Base case: if no elements remain to process
    if (IsEmpty(remaining)) {
        return result;
    } else {
        // Get the first available element
        Integer element = First(remaining);

        // Remove selected element from remaining elements
        ConsList<Integer> newRemaining = Remove(original, element);

        // Add selected element to current permutation
        ConsList<Integer> newCurrent = Append(current, MakeList(element));

        // Generate permutations with current element and add to results
        ConsList<ConsList<Integer>> newResults = Append(result, permutationsHelper(newRemaining, newCurrent));

        // Continue with next element in remaining list
        return permutationsForElement(original, current, Rest(remaining), newResults);
    }
}

/**
 * Tests for permutations function
 * 
 * Strategy: Test different cases:
 * 1. Empty list
 * 2. Single element list
 * 3. Two element list
 * 4. Three element list
 */
void testPermutations() {
    // Test case 1: Empty list
    // Purpose: Verify base case handling of empty list
    // Expected: Single empty list as permutation result
    ConsList<Integer> emptyList = new Nil<>();
    ConsList<ConsList<Integer>> emptyResult = MakeList(emptyList);
    testEqual(emptyResult, permutations(emptyList));

    // Test case 2: Single element
    // Purpose: Verify handling of simplest non-empty case
    // Expected: Single permutation containing just that element
    ConsList<Integer> singleList = MakeList(1);
    ConsList<ConsList<Integer>> singleResult = MakeList(MakeList(1));
    testEqual(singleResult, permutations(singleList));

    // Test case 3: Two elements
    // Purpose: Verify basic permutation generation
    // Expected: Two permutations - original order and swapped order
    ConsList<Integer> twoList = MakeList(1, 2);
    ConsList<ConsList<Integer>> twoResult = MakeList(
        MakeList(1, 2),
        MakeList(2, 1)
    );
    testEqual(twoResult, permutations(twoList));

    // Test case 4: Three elements
    // Purpose: Verify complex permutation generation
    // Expected: All 6 possible orderings (3! = 6 permutations)
    ConsList<Integer> threeList = MakeList(1, 2, 3);
    ConsList<ConsList<Integer>> threeResult = MakeList(
        MakeList(1, 2, 3),
        MakeList(1, 3, 2),
        MakeList(2, 1, 3),
        MakeList(2, 3, 1),
        MakeList(3, 1, 2),
        MakeList(3, 2, 1)
    );
    testEqual(threeResult, permutations(threeList));
}


/**
 * Design Strategy: Comprehensive Testing
 * Reason:
 * - Validates all core functionality
 * - Covers edge cases and normal cases
 * - Uses test runner for consistent execution
 * 
 * Test Coverage:
 * - Basic functionality
 * - Edge cases
 * - Large number handling
 * - Error conditions
 */
void test()
{
    runAsTest(this::testIsPermutationSimple);
    runAsTest(this::testIsPermutationLargeNum);
    runAsTest(this::testCompareLists);
    runAsTest(this::testInsert);
    runAsTest(this::testPermutations);
}

/**
 * Main entry point for the Permutations program.
 * Performs version check to ensure compatibility with course requirements.
 */
void main(String[] args){
    CheckVersion("2025S1-7");
}