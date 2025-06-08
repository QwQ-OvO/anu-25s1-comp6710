package ws7b;
import java.util.Collection;    // Collection interface
import java.util.List;          // List interface
import java.util.Iterator;      // Iterator interface
import java.util.ListIterator;  // ListIterator interface
import java.util.NoSuchElementException;


public class DoublyLinkedList<E> implements List<E> {
    Node<E> first;

    /** Represents a node in the doubly-linked list.
     *  The class is parameterized by the type of the element
     *  that the node holds inside. The class is private as
     *  we want to avoid exposing this implementation
     *  detail (i.e. Node<E>) to the users of the DoublyLinkedList<E> class
     *  Examples: Given the list [-1,2,1], the node holding element 2 points
     *            to the (previous) node holding the element -1, and to the
     *            (next) node holding the element 1.
     *  @param value The element stored within the node
     *  @param previous A reference to the previous node in the list
     *  @param next A reference to the next node in the list
     */
    private static class Node<E> {
        E value;
        Node<E> previous;
        Node<E> next;
        Node(E value, Node<E> previous, Node<E> next)
        {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    private static class DoublyLinkedListIterator<E> implements Iterator<E> {
        DoublyLinkedList<E> list;
        Node<E> next;
        boolean visitedFirst;
        DoublyLinkedListIterator(DoublyLinkedList<E> list)
        {
            this.list = list;
            this.next = list.first;
            visitedFirst = false;
        }


        @Override
        public boolean hasNext() {
            if (this.list.isEmpty()) {
                return false;
            }
            else if (!visitedFirst) {
                return true;
            }
            else {
                return !(this.next==this.list.first);
            }
        }


        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (!visitedFirst) visitedFirst=true;
            E value = this.next.value;
            this.next = this.next.next;
            return value;
        }
    }
    /* .. CODE TEMPLATE goes here .. */

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return first==null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator<E>(this);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (isEmpty())
        {
            Node<E> newNode = new Node<E>(e, null, null);
            newNode.previous = newNode;
            newNode.next = newNode;
            first = newNode;
        }
        else
        {
            Node<E> last = first.previous;
            Node<E> newNode = new Node<E>(e, last, first);
            last.next = newNode;
            first.previous = newNode;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }
}
/* .. CODE TEMPLATE goes here .. */
