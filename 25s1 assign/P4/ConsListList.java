package P4;

import java.util.*;

// =============================================================================
// CONS-BASED LIST IMPLEMENTATION WITH JAVA LIST INTERFACE
// =============================================================================

/**
 * Purpose: Implements a cons-based list data structure that conforms to Java's List interface.
 * 
 * Signature: Generic class implementing List<T> using cons-based node structure.
 * 
 * Examples:
 * - new ConsListList<String>() -> creates empty list
 * - list.add("hello") -> adds element to end of list
 * - list.get(0) -> retrieves first element
 * 
 * Design Strategy: Function Composition - Use linked node structure with List interface compliance.
 * 
 * Effects: Provides mutable list operations, may throw exceptions for invalid operations.
 */
public class ConsListList<T> implements List<T> {

    // =============================================================================
    // INNER NODE CLASS
    // =============================================================================

    /**
     * Purpose: Represents a node in the cons-based list structure.
     * 
     * Signature: Static generic class containing value and next pointer.
     * 
     * Examples:
     * - new Node<String>("hello", null) -> creates terminal node with "hello"
     * - new Node<Integer>(42, nextNode) -> creates node linking to another node
     * 
     * Design Strategy: Simple Expression - Immutable node structure for cons list.
     * 
     * Effects: Creates node objects for list construction, no side effects.
     */
    public static class Node<T> {
        /** The value stored in this node */
        T value;
        /** Reference to the next node in the list, null for terminal nodes */
        Node<T> next;

        /**
         * DESIGN RECIPE STEP 1: Data Definition
         * Node represents a single element in a cons-based list
         * 
         * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
         * 
         * Purpose: Constructs a new node with specified value and next pointer.
         * 
         * Signature: T, Node<T> -> Node<T>
         * 
         * Examples:
         * - new Node<String>("test", null) -> terminal node with "test"
         * - new Node<Integer>(1, existingNode) -> node linking to existing node
         * 
         * Design Strategy: Simple Expression - Direct field assignment.
         * 
         * Effects: Creates new node object, no side effects.
         * 
         * @param value The value to store in this node
         * @param next Reference to the next node (null for terminal nodes)
         */
        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    // =============================================================================
    // DATA FIELDS
    // =============================================================================

    /** Reference to the first node in the list, null for empty lists */
    private Node<T> first;
    /** Current size of the list for efficient size operations */
    private int size;
    /** Modification count for concurrent modification detection */
    private int modCount;

    // =============================================================================
    // CONSTRUCTORS
    // =============================================================================

    /**
     * Purpose: Creates a new empty ConsListList.
     * 
     * Signature: void -> ConsListList<T>
     * 
     * Examples:
     * - new ConsListList<String>() -> empty string list
     * - new ConsListList<Integer>() -> empty integer list
     * 
     * Design Strategy: Simple Expression - Initialize empty list state.
     * 
     * Effects: Creates new empty list object, no side effects.
     */
    public ConsListList() {
        this.first = null;
        this.size = 0;
        this.modCount = 0;
    }

    // =============================================================================
    // PART 1: BASIC LIST OPERATIONS
    // =============================================================================

    /**
     * Purpose: Adds the specified element to the end of this list.
     * 
     * Signature: T -> boolean
     * 
     * Examples:
     * - list.add("hello") -> adds "hello" to end, returns true
     * - emptyList.add(42) -> adds 42 as first element, returns true
     * 
     * Design Strategy: Cases on List State - Handle empty vs non-empty list.
     * 
     * Effects: Modifies list by adding element, increases size, increments modCount.
     * 
     * @param t Element to be added to this list
     * @return true (as specified by Collection.add)
     */
    @Override
    public boolean add(T t) {
        Node<T> newNode = new Node<>(t, null);

        if (first == null) {
            first = newNode;
        } else {
            Node<T> currentNode = first;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
        
        size++;
        modCount++;
        return true;
    }

    /**
     * Purpose: Removes the first occurrence of the specified element from this list.
     * 
     * Signature: Object -> boolean
     * 
     * Examples:
     * - list.remove("hello") -> removes first "hello", returns true if found
     * - list.remove("missing") -> returns false if element not found
     * 
     * Design Strategy: Cases on Element Position - Handle first element vs middle/end elements.
     * 
     * Effects: Modifies list by removing element, decreases size if found, increments modCount.
     * 
     * @param o Element to be removed from this list
     * @return true if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        if (Objects.equals(o, first.value)) {
            first = first.next;
            size--;
            modCount++;
            return true;
        }

        Node<T> currentNode = first;
        while (currentNode.next != null) {
            if (Objects.equals(o, currentNode.next.value)) {
                currentNode.next = currentNode.next.next;
                size--;
                modCount++;
                return true;
            }
            currentNode = currentNode.next;
        }
        
        return false;
    }

    /**
     * Purpose: Returns the number of elements in this list.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - emptyList.size() -> 0
     * - listWithThreeElements.size() -> 3
     * 
     * Design Strategy: Simple Expression - Return cached size field.
     * 
     * Effects: Pure function with no side effects, returns integer.
     * 
     * @return The number of elements in this list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Purpose: Returns true if this list contains no elements.
     * 
     * Signature: void -> boolean
     * 
     * Examples:
     * - emptyList.isEmpty() -> true
     * - listWithElements.isEmpty() -> false
     * 
     * Design Strategy: Simple Expression - Check if size is zero.
     * 
     * Effects: Pure function with no side effects, returns boolean.
     * 
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Purpose: Returns the element at the specified position in this list.
     * 
     * Signature: int -> T
     * 
     * Examples:
     * - list.get(0) -> returns first element
     * - list.get(2) -> returns third element
     * - list.get(-1) -> throws IndexOutOfBoundsException
     * 
     * Design Strategy: Function Composition - Validate index, then traverse to position.
     * 
     * Effects: Pure function with no side effects, may throw IndexOutOfBoundsException.
     * 
     * @param index Index of the element to return
     * @return The element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        
        return currentNode.value;
    }

    /**
     * Purpose: Replaces the element at the specified position in this list with the specified element.
     * 
     * Signature: int, T -> T
     * 
     * Examples:
     * - list.set(0, "new") -> replaces first element, returns old value
     * - list.set(5, "test") -> throws IndexOutOfBoundsException if index 5 doesn't exist
     * 
     * Design Strategy: Function Composition - Validate index, traverse to position, replace element.
     * 
     * Effects: Modifies list element at specified position, increments modCount, returns old value.
     * 
     * @param index Index of the element to replace
     * @param element Element to be stored at the specified position
     * @return The element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        
        T previousValue = currentNode.value;
        currentNode.value = element;
        modCount++;
        
        return previousValue;
    }

    // =============================================================================
    // PART 2: EXTENDED LIST OPERATIONS
    // =============================================================================

    /**
     * Purpose: Returns true if this list contains the specified element.
     * 
     * Signature: Object -> boolean
     * 
     * Examples:
     * - list.contains("hello") -> true if "hello" is in list
     * - list.contains("missing") -> false if element not found
     * 
     * Design Strategy: Function Composition - Traverse list checking each element for equality.
     * 
     * Effects: Pure function with no side effects, returns boolean.
     * 
     * @param o Element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        if (size == 0) {
            return false;
        }
        
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (Objects.equals(o, currentNode.value)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        
        return false;
    }

    /**
     * Purpose: Returns true if this list contains all elements of the specified collection.
     * 
     * Signature: Collection<?> -> boolean
     * 
     * Examples:
     * - list.containsAll(Arrays.asList("a", "b")) -> true if both "a" and "b" are in list
     * - list.containsAll(Collections.emptyList()) -> true (empty collection always contained)
     * 
     * Design Strategy: Function Composition - Check each element of collection for containment.
     * 
     * Effects: Pure function with no side effects, returns boolean.
     * 
     * @param c Collection to be checked for containment in this list
     * @return true if this list contains all elements of the specified collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Purpose: Removes from this list all of its elements that are contained in the specified collection.
     * 
     * Signature: Collection<?> -> boolean
     * 
     * Examples:
     * - list.removeAll(Arrays.asList("a", "b")) -> removes all "a" and "b" elements, returns true if any removed
     * - list.removeAll(Collections.emptyList()) -> no change, returns false
     * 
     * Design Strategy: Function Composition - Iterate through collection and remove each element.
     * 
     * Effects: Modifies list by removing elements, may change size and modCount, returns boolean.
     * 
     * @param c Collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            while (remove(element)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Purpose: Retains only the elements in this list that are contained in the specified collection.
     * 
     * Signature: Collection<?> -> boolean
     * 
     * Examples:
     * - list.retainAll(Arrays.asList("a", "b")) -> keeps only "a" and "b" elements
     * - list.retainAll(Collections.emptyList()) -> clears entire list
     * 
     * Design Strategy: Function Composition - Traverse list and remove elements not in collection.
     * 
     * Effects: Modifies list by removing elements, may change size and modCount, returns boolean.
     * 
     * @param c Collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        
        while (first != null && !c.contains(first.value)) {
            first = first.next;
            size--;
            modified = true;
        }
        
        if (first != null) {
            Node<T> currentNode = first;
            while (currentNode.next != null) {
                if (!c.contains(currentNode.next.value)) {
                    currentNode.next = currentNode.next.next;
                    size--;
                    modified = true;
                } else {
                    currentNode = currentNode.next;
                }
            }
        }
        
        if (modified) {
            modCount++;
        }
        
        return modified;
    }

    /**
     * Purpose: Removes all elements from this list.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - list.clear() -> removes all elements, list becomes empty
     * 
     * Design Strategy: Simple Expression - Reset list to empty state.
     * 
     * Effects: Modifies list by removing all elements, sets size to 0, increments modCount.
     */
    @Override
    public void clear() {
        first = null;
        size = 0;
        modCount++;
    }

    /**
     * Purpose: Inserts the specified element at the specified position in this list.
     * 
     * Signature: int, T -> void
     * 
     * Examples:
     * - list.add(0, "first") -> inserts "first" at beginning
     * - list.add(list.size(), "last") -> appends "last" at end
     * 
     * Design Strategy: Cases on Index Position - Handle insertion at beginning, middle, or end.
     * 
     * Effects: Modifies list by inserting element, increases size, increments modCount.
     * 
     * @param index Index at which the specified element is to be inserted
     * @param element Element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void add(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            first = new Node<>(element, first);
        } else {
            Node<T> currentNode = first;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            currentNode.next = new Node<>(element, currentNode.next);
        }
        
        size++;
        modCount++;
    }

    /**
     * Purpose: Removes the element at the specified position in this list.
     * 
     * Signature: int -> T
     * 
     * Examples:
     * - list.remove(0) -> removes and returns first element
     * - list.remove(2) -> removes and returns third element
     * 
     * Design Strategy: Cases on Index Position - Handle removal from beginning vs middle/end.
     * 
     * Effects: Modifies list by removing element, decreases size, increments modCount, returns removed element.
     * 
     * @param index Index of the element to be removed
     * @return The element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T removedValue;
        
        if (index == 0) {
            removedValue = first.value;
            first = first.next;
        } else {
            Node<T> currentNode = first;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            removedValue = currentNode.next.value;
            currentNode.next = currentNode.next.next;
        }
        
        size--;
        modCount++;
        return removedValue;
    }

    /**
     * Purpose: Returns the index of the first occurrence of the specified element in this list.
     * 
     * Signature: Object -> int
     * 
     * Examples:
     * - list.indexOf("hello") -> 2 if "hello" first appears at index 2
     * - list.indexOf("missing") -> -1 if element not found
     * 
     * Design Strategy: Function Composition - Traverse list with index counter until element found.
     * 
     * Effects: Pure function with no side effects, returns integer index.
     * 
     * @param o Element to search for
     * @return The index of the first occurrence of the specified element, or -1 if not found
     */
    @Override
    public int indexOf(Object o) {
        Node<T> currentNode = first;
        for (int index = 0; index < size; index++) {
            if (Objects.equals(o, currentNode.value)) {
                return index;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    /**
     * Purpose: Returns the index of the last occurrence of the specified element in this list.
     * 
     * Signature: Object -> int
     * 
     * Examples:
     * - list.lastIndexOf("hello") -> 5 if "hello" last appears at index 5
     * - list.lastIndexOf("missing") -> -1 if element not found
     * 
     * Design Strategy: Function Composition - Traverse entire list tracking last found index.
     * 
     * Effects: Pure function with no side effects, returns integer index.
     * 
     * @param o Element to search for
     * @return The index of the last occurrence of the specified element, or -1 if not found
     */
    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        Node<T> currentNode = first;
        
        for (int index = 0; index < size; index++) {
            if (Objects.equals(o, currentNode.value)) {
                lastIndex = index;
            }
            currentNode = currentNode.next;
        }
        
        return lastIndex;
    }

    // =============================================================================
    // PART 3: ITERATOR IMPLEMENTATION
    // =============================================================================

    /**
     * Purpose: Returns an iterator over the elements in this list in proper sequence.
     * 
     * Signature: void -> Iterator<T>
     * 
     * Examples:
     * - for (T element : list) -> uses iterator to traverse list
     * - Iterator<T> it = list.iterator() -> creates iterator for manual traversal
     * 
     * Design Strategy: Simple Expression - Create and return new iterator instance.
     * 
     * Effects: Creates new iterator object, no side effects on list.
     * 
     * @return An iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        return new ConsListIterator();
    }

    /**
     * Purpose: Iterator implementation for ConsListList using cons-based traversal.
     * 
     * Signature: Inner class implementing Iterator<T> interface.
     * 
     * Examples:
     * - iterator.hasNext() -> true if more elements available
     * - iterator.next() -> returns next element and advances position
     * 
     * Design Strategy: Function Composition - Track current position and modification count.
     * 
     * Effects: Provides iteration capabilities with concurrent modification detection.
     */
    private class ConsListIterator implements Iterator<T> {
        /** Current node in iteration sequence */
        private Node<T> current;
        /** Expected modification count for concurrent modification detection */
        private int expectedModCount;

        /**
         * Purpose: Constructs iterator starting at beginning of list.
         * 
         * Signature: void -> ConsListIterator
         * 
         * Examples:
         * - new ConsListIterator() -> creates iterator at list start
         * 
         * Design Strategy: Simple Expression - Initialize iterator state.
         * 
         * Effects: Creates iterator object with initial state.
         */
        public ConsListIterator() {
            current = first;
            expectedModCount = modCount;
        }

        /**
         * Purpose: Returns true if the iteration has more elements.
         * 
         * Signature: void -> boolean
         * 
         * Examples:
         * - iterator.hasNext() -> true if current node is not null
         * - iterator.hasNext() -> false at end of list
         * 
         * Design Strategy: Simple Expression - Check if current node exists.
         * 
         * Effects: Pure function with no side effects, returns boolean.
         * 
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            checkForComodification();
            return current != null;
        }

        /**
         * Purpose: Returns the next element in the iteration.
         * 
         * Signature: void -> T
         * 
         * Examples:
         * - iterator.next() -> returns current element and advances to next
         * 
         * Design Strategy: Function Composition - Check availability, return element, advance position.
         * 
         * Effects: Advances iterator position, may throw NoSuchElementException.
         * 
         * @return The next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() throws NoSuchElementException {
            checkForComodification();
            
            if (current == null) {
                throw new NoSuchElementException();
            }
            
            T value = current.value;
            current = current.next;
            return value;
        }

        /**
         * Purpose: Checks for concurrent modification of the list during iteration.
         * 
         * Signature: void -> void
         * 
         * Examples:
         * - checkForComodification() -> throws exception if list was modified
         * 
         * Design Strategy: Simple Expression - Compare expected vs actual modification count.
         * 
         * Effects: May throw ConcurrentModificationException if list was modified.
         * 
         * @throws ConcurrentModificationException if the list was modified during iteration
         */
        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    // =============================================================================
    // PART 4: SUBLIST IMPLEMENTATION (DISTINCTION LEVEL)
    // =============================================================================

    /**
     * Purpose: Returns a view of the portion of this list between the specified fromIndex and toIndex.
     * 
     * Signature: int, int -> List<T>
     * 
     * Examples:
     * - list.subList(1, 4) -> returns view of elements from index 1 to 3
     * - list.subList(0, 0) -> returns empty sublist
     * 
     * Design Strategy: Function Composition - Create SubList view with range validation.
     * 
     * Effects: Creates new SubList view object, no side effects on original list.
     * 
     * @param fromIndex Low endpoint (inclusive) of the subList
     * @param toIndex High endpoint (exclusive) of the subList
     * @return A view of the specified range within this list
     * @throws IndexOutOfBoundsException if an endpoint index value is out of range
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", size: " + size);
        }
        
        return new SubList(fromIndex, toIndex);
    }

    /**
     * Purpose: SubList implementation providing a view of a portion of the parent ConsListList.
     * 
     * Signature: Inner class implementing List<T> interface for range views.
     * 
     * Examples:
     * - subList.get(0) -> gets first element of sublist range
     * - subList.size() -> returns size of sublist range
     * 
     * Design Strategy: Function Composition - Delegate operations to parent list with offset adjustments.
     * 
     * Effects: Provides list view operations that modify parent list within specified range.
     */
    private class SubList implements List<T> {
        /** Starting index in parent list (inclusive) */
        private int fromIndex;
        /** Ending index in parent list (exclusive) */
        private int toIndex;
        /** Expected modification count for concurrent modification detection */
        private int expectedModCount;

        /**
         * Purpose: Constructs SubList view for specified range of parent list.
         * 
         * Signature: int, int -> SubList
         * 
         * Examples:
         * - new SubList(1, 4) -> creates view of indices 1, 2, 3 from parent
         * 
         * Design Strategy: Simple Expression - Store range parameters and modification count.
         * 
         * Effects: Creates SubList view object with range specification.
         * 
         * @param fromIndex Starting index in parent list (inclusive)
         * @param toIndex Ending index in parent list (exclusive)
         */
        public SubList(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.expectedModCount = ConsListList.this.modCount;
        }

        /**
         * Purpose: Checks for concurrent modification of parent list during SubList operations.
         * 
         * Signature: void -> void
         * 
         * Examples:
         * - checkForComodification() -> throws exception if parent was modified
         * 
         * Design Strategy: Simple Expression - Compare expected vs actual modification count.
         * 
         * Effects: May throw ConcurrentModificationException if parent was modified.
         * 
         * @throws ConcurrentModificationException if the parent list was modified
         */
        private void checkForComodification() {
            if (ConsListList.this.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public int size() {
            checkForComodification();
            return toIndex - fromIndex;
        }

        @Override
        public boolean isEmpty() {
            checkForComodification();
            return toIndex == fromIndex;
        }

        @Override
        public T get(int index) throws IndexOutOfBoundsException {
            checkForComodification();
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
            }
            return ConsListList.this.get(fromIndex + index);
        }

        @Override
        public T set(int index, T element) throws IndexOutOfBoundsException {
            checkForComodification();
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
            }
            return ConsListList.this.set(fromIndex + index, element);
        }

        @Override
        public void add(int index, T element) throws IndexOutOfBoundsException {
            checkForComodification();
            if (index < 0 || index > size()) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
            }
            ConsListList.this.add(fromIndex + index, element);
            toIndex++;
            expectedModCount = ConsListList.this.modCount;
        }

        @Override
        public T remove(int index) throws IndexOutOfBoundsException {
            checkForComodification();
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
            }
            T result = ConsListList.this.remove(fromIndex + index);
            toIndex--;
            expectedModCount = ConsListList.this.modCount;
            return result;
        }

        @Override
        public boolean contains(Object o) {
            checkForComodification();
            return indexOf(o) >= 0;
        }

        @Override
        public int indexOf(Object o) {
            checkForComodification();
            for (int i = 0; i < size(); i++) {
                if (Objects.equals(o, get(i))) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            checkForComodification();
            for (int i = size() - 1; i >= 0; i--) {
                if (Objects.equals(o, get(i))) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public Iterator<T> iterator() {
            checkForComodification();
            return new SubListIterator();
        }

        /**
         * Purpose: Iterator implementation for SubList providing sequential access to range elements.
         * 
         * Signature: Inner class implementing Iterator<T> for SubList range.
         * 
         * Examples:
         * - iterator.hasNext() -> true if more elements in range
         * - iterator.next() -> returns next element in sublist range
         * 
         * Design Strategy: Function Composition - Track position within sublist range.
         * 
         * Effects: Provides iteration over sublist range with modification detection.
         */
        private class SubListIterator implements Iterator<T> {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                checkForComodification();
                return currentIndex < size();
            }

            @Override
            public T next() throws NoSuchElementException {
                checkForComodification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(currentIndex++);
            }
        }

        // Minimal implementations for remaining List interface methods
        @Override public boolean add(T t) { add(size(), t); return true; }
        @Override public boolean remove(Object o) { int index = indexOf(o); if (index >= 0) { remove(index); return true; } return false; }
        @Override public boolean containsAll(Collection<?> c) { for (Object o : c) if (!contains(o)) return false; return true; }
        @Override public boolean addAll(Collection<? extends T> c) { boolean modified = false; for (T t : c) { add(t); modified = true; } return modified; }
        @Override public boolean addAll(int index, Collection<? extends T> c) { boolean modified = false; int i = index; for (T t : c) { add(i++, t); modified = true; } return modified; }
        @Override public boolean removeAll(Collection<?> c) { boolean modified = false; for (Object o : c) while (remove(o)) modified = true; return modified; }
        @Override public boolean retainAll(Collection<?> c) { boolean modified = false; for (int i = size() - 1; i >= 0; i--) if (!c.contains(get(i))) { remove(i); modified = true; } return modified; }
        @Override public void clear() { for (int i = size() - 1; i >= 0; i--) remove(i); }
        @Override public Object[] toArray() { Object[] result = new Object[size()]; for (int i = 0; i < size(); i++) result[i] = get(i); return result; }
        @Override public <T1> T1[] toArray(T1[] a) { if (a.length < size()) a = Arrays.copyOf(a, size()); for (int i = 0; i < size(); i++) a[i] = (T1) get(i); if (a.length > size()) a[size()] = null; return a; }
        @Override public ListIterator<T> listIterator() { return listIterator(0); }
        @Override public ListIterator<T> listIterator(int index) { throw new UnsupportedOperationException(); }
        @Override public List<T> subList(int fromIndex, int toIndex) { 
            checkForComodification();
            if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) 
                throw new IndexOutOfBoundsException();
            return ConsListList.this.subList(this.fromIndex + fromIndex, this.fromIndex + toIndex);
        }
    }

    // =============================================================================
    // REMAINING LIST INTERFACE METHODS (MINIMAL IMPLEMENTATIONS)
    // =============================================================================

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            result[i] = currentNode.value;
            currentNode = currentNode.next;
        }
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            a[i] = (T1) currentNode.value;
            currentNode = currentNode.next;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T element : c) {
            add(element);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean modified = false;
        int currentIndex = index;
        for (T element : c) {
            add(currentIndex++, element);
            modified = true;
        }
        return modified;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("ListIterator not implemented");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("ListIterator not implemented");
    }
} 