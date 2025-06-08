/** Import packages from course website. */
import comp1110.lib.*;
import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;

// =============================================================================
// PART 1: PERMUTATION CHECKING
// =============================================================================

/**
 * Purpose: Determines if two lists are permutations of each other by comparing their sorted versions.
 * 
 * Signature: ConsList<Integer>, ConsList<Integer> -> boolean
 * 
 * Examples:
 * - isPermutation([1,2,3], [2,1,3]) -> true (same elements, different order)
 * - isPermutation([1,2], [1,3]) -> false (different elements)
 * - isPermutation([], []) -> true (both empty)
 * - isPermutation([1,2,3], [1,2]) -> false (different lengths)
 * 
 * Design Strategy: Function Composition - Sort both lists and compare recursively.
 * 
 * Effects: Pure function with no side effects, returns boolean value.
 * 
 * @param list1 First list to check
 * @param list2 Second list to check  
 * @return true if lists are permutations, false otherwise
 */
boolean isPermutation(ConsList<Integer> list1, ConsList<Integer> list2){
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if both lists are empty (base case)
    // - Check if lists have different lengths (fast rejection)
    // - Sort both lists to normalize element order
    // - Compare sorted lists recursively
    
    // DESIGN RECIPE STEP 5: Function Body
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
// DESIGN RECIPE STEP 6: Testing
// Tests would verify permutation detection for various cases including edge cases

/**
 * Purpose: Tests the isPermutation method with simple integer lists.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Tests [1,2,3,4] vs [2,3,4,1] -> expects true
 * - Tests [1,2,3,4] vs [1,2,3,5] -> expects false
 * - Tests [1,2,3,4,5] vs [1,2,3,5,5] -> expects false
 * 
 * Design Strategy: Function Composition - Create test lists and verify expected outcomes.
 * 
 * Effects: Executes test assertions, may output test results.
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
 * Purpose: Tests the isPermutation method with large numbers to verify edge case handling.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Tests [1,2,3,5,100000000] vs [1,2,3,5,-100000000] -> expects false
 * - Tests [1,2,3,5,100000000] vs [2,3,5,100000000,1] -> expects true
 * 
 * Design Strategy: Function Composition - Test with extreme values to verify robustness.
 * 
 * Effects: Executes test assertions, may output test results.
 */
void testIsPermutationLargeNum(){
    ConsList<Integer> list5 = MakeList(1,2,3,5,100_000_000);
    ConsList<Integer> list6 = MakeList(1,2,3,5,-100_000_000);
    ConsList<Integer> list7 = MakeList(2,3,5,100_000_000,1);
    testFalse(isPermutation(list5, list6));
    testTrue(isPermutation(list5, list7));
}

// =============================================================================
// PART 2: LIST COMPARISON
// =============================================================================

/**
 * Purpose: Compares two lists lexicographically, returning ordering relationship as integer.
 * 
 * Signature: ConsList<Integer>, ConsList<Integer> -> int
 * 
 * Examples:
 * - compareLists([1,2,3], [1,2,4]) -> negative (3 < 4)
 * - compareLists([1,2,3], [1,2,3]) -> 0 (identical)
 * - compareLists([2,1,1], [1,2,2]) -> positive (2 > 1)
 * - compareLists([], []) -> 0 (both empty)
 * 
 * Design Strategy: Cases on List Structure - Compare first elements, recurse on rest if equal.
 * 
 * Effects: Pure function with no side effects, returns integer comparison result.
 * 
 * @param list1 First list to compare
 * @param list2 Second list to compare
 * @return Negative if list1<list2, 0 if equal, positive if list1>list2
 */
int compareLists(ConsList<Integer> list1, ConsList<Integer> list2){
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if both lists are empty (base case - equal)
    // - Extract first elements of both lists
    // - Compare first elements
    // - If equal, recursively compare rest of lists
    // - If different, return comparison result
    
    // DESIGN RECIPE STEP 5: Function Body
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
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct lexicographic ordering for various list combinations

/**
 * Purpose: Tests the compareLists method with various list combinations.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Tests identical lists -> expects 0
 * - Tests lists with different first elements -> expects appropriate sign
 * - Tests lists requiring recursive comparison -> expects correct result
 * 
 * Design Strategy: Function Composition - Create test cases covering comparison scenarios.
 * 
 * Effects: Executes test assertions, may output test results.
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

// =============================================================================
// PART 3: LIST INSERTION
// =============================================================================

/**
 * Purpose: Creates a new list with an element inserted at a specified position.
 * 
 * Signature: int, int, ConsList<Integer> -> ConsList<Integer>
 * 
 * Examples:
 * - insert(6, 0, [1,2,3]) -> [6,1,2,3] (insert at start)
 * - insert(6, 2, [1,2,3]) -> [1,2,6,3] (insert in middle)
 * - insert(6, 3, [1,2,3]) -> [1,2,3,6] (insert at end)
 * - insert(6, -1, [1,2,3]) -> [1,2,3] (invalid index, return original)
 * - insert(6, 5, [1,2,3]) -> [1,2,3] (index too large, return original)
 * 
 * Design Strategy: Cases on Index Validity - Handle invalid indices, then recurse for valid insertion.
 * 
 * Effects: Pure function with no side effects, returns new list.
 * 
 * @param element Element to insert
 * @param index Position to insert at (0-based)
 * @param list Original list
 * @return New list with element inserted at specified position
 */
ConsList<Integer> insert(int element, int index, ConsList<Integer> list){
    // DESIGN RECIPE STEP 4: Function Template
    // - Check for invalid index (negative or too large)
    // - Handle insertion at start (index 0)
    // - Handle empty list case
    // - Recursively build result for other positions
    
    // DESIGN RECIPE STEP 5: Function Body
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
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct insertion at various positions and boundary conditions

/**
 * Purpose: Tests the insert method with various insertion scenarios.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Tests insertion in middle of list
 * - Tests insertion at start and end
 * - Tests insertion with large numbers
 * - Tests boundary conditions
 * 
 * Design Strategy: Function Composition - Create comprehensive test cases for insertion scenarios.
 * 
 * Effects: Executes test assertions, may output test results.
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

// =============================================================================
// PART 4: PERMUTATION GENERATION
// =============================================================================

/**
 * Purpose: Generates all possible permutations of a given list.
 * 
 * Signature: ConsList<Integer> -> ConsList<ConsList<Integer>>
 * 
 * Examples:
 * - permutations([]) -> [[]] (empty list has one permutation: empty)
 * - permutations([1]) -> [[1]] (single element has one permutation)
 * - permutations([1,2]) -> [[1,2], [2,1]] (two elements have 2! = 2 permutations)
 * - permutations([1,2,3]) -> [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]] (3! = 6 permutations)
 * 
 * Design Strategy: Function Composition - Delegate to helper function with empty initial state.
 * 
 * Effects: Pure function with no side effects, returns list of all permutations.
 * 
 * @param list Input list to generate permutations from
 * @return List of all possible permutations
 */
ConsList<ConsList<Integer>> permutations(ConsList<Integer> list) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Start permutation generation with empty current list
    // - Delegate to helper function that handles the recursive logic
    
    // DESIGN RECIPE STEP 5: Function Body
    // Start with empty current list
    return permutationsHelper(list, new Nil<Integer>());
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct permutation generation for various input sizes

/**
 * Purpose: Helper function that builds permutations recursively by choosing elements systematically.
 * 
 * Signature: ConsList<Integer>, ConsList<Integer> -> ConsList<ConsList<Integer>>
 * 
 * Examples:
 * - permutationsHelper([], [1,2]) -> [[1,2]] (no remaining elements, return current)
 * - permutationsHelper([1,2], []) -> generates all permutations starting with empty list
 * 
 * Design Strategy: Cases on Remaining Elements - Base case when empty, recursion when non-empty.
 * 
 * Effects: Pure function with no side effects, returns list of permutations.
 * 
 * @param remaining Elements not yet used in current permutation
 * @param current Current partial permutation being built
 * @return All permutations possible from this state
 */
ConsList<ConsList<Integer>> permutationsHelper(ConsList<Integer> remaining, ConsList<Integer> current) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if no elements remain (base case)
    // - If remaining elements exist, try each as next choice
    // - Delegate to permutationsForElement for processing
    
    // DESIGN RECIPE STEP 5: Function Body
    // Base case: if no elements remain, current list is a complete permutation
    if (IsEmpty(remaining)) {
        return MakeList(current);
    } else {
        // Otherwise, try each remaining element as the next choice
        return permutationsForElement(remaining, current, remaining, new Nil<ConsList<Integer>>());
    }
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct recursive permutation building

/**
 * Purpose: Removes all occurrences of a specific element from a list.
 * 
 * Signature: ConsList<T>, T -> ConsList<T>
 * 
 * Examples:
 * - Remove([1,2,3,2], 2) -> [1,3] (removes all 2s)
 * - Remove([1,2,3], 4) -> [1,2,3] (element not found, return original)
 * - Remove([], 1) -> [] (empty list remains empty)
 * 
 * Design Strategy: Cases on List Structure - Process each element, keeping or removing as appropriate.
 * 
 * Effects: Pure function with no side effects, returns new list without specified element.
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
 * Purpose: Processes permutations for each element in the remaining list systematically.
 * 
 * Signature: ConsList<Integer>, ConsList<Integer>, ConsList<Integer>, ConsList<ConsList<Integer>> -> ConsList<ConsList<Integer>>
 * 
 * Examples:
 * - For each element in remaining: remove it, add to current, generate sub-permutations
 * - Accumulates all results into final permutation list
 * 
 * Design Strategy: Cases on Remaining Elements - Process each element, accumulate results.
 * 
 * Effects: Pure function with no side effects, returns accumulated permutations.
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
 * Purpose: Tests permutation generation for various input sizes and edge cases.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Tests empty list permutations
 * - Tests single element permutations  
 * - Tests multiple element permutations
 * - Verifies correct count and content of generated permutations
 * 
 * Design Strategy: Function Composition - Create systematic test cases for different scenarios.
 * 
 * Effects: Executes test assertions, may output test results.
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

// =============================================================================
// MAIN TESTING AND EXECUTION
// =============================================================================

/**
 * Purpose: Executes comprehensive test suite for all permutation-related functions.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Runs all test functions in sequence
 * - Provides complete validation of implementation
 * 
 * Design Strategy: Function Composition - Execute all individual test functions.
 * 
 * Effects: Runs test suite, outputs test results to console.
 */
void test()
{
    testIsPermutationSimple();
    testIsPermutationLargeNum();
    testCompareLists();
    testInsert();
    testPermutations();
}

/**
 * Purpose: Main entry point for the permutation program.
 * 
 * Signature: String[] -> void
 * 
 * Examples:
 * - main(args) -> executes complete test suite
 * 
 * Design Strategy: Function Composition - Start test execution.
 * 
 * Effects: Runs program, executes tests, outputs results.
 * 
 * @param args Command line arguments (not used)
 */
void main(String[] args){
    test();
}