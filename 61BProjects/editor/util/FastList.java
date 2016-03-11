package util;

import javax.management.relation.RoleUnresolved;
import java.util.Iterator;
import java.lang.Iterable;

/**
 * Created by henry on 3/6/16.
 */
public class FastList<Item> implements Iterable<Item> {
    private Node currentPosition;
    private Node sentinel;

    private class Node {
        public Item entry;
        public Node next;
        public Node prev;

        public Node() {

        }
        public Node(Item it) {
            this.entry = it;
        }
        public Node(Node prev, Item it, Node next) {
            this(it);
            prev.next = this;
            this.prev = prev;
            this.next = next;
            next.prev = this;
        }
    }

    private class Iter implements Iterator<Item> {
        private Node curr = sentinel;
        public boolean hasNext() {
            return curr.next != sentinel;
        }
        public Item next() {
            curr = curr.next;
            return curr.entry;
        }
    }

    public Iterator<Item> iterator() {
        return new Iter();
    }

    public FastList() {
        this.sentinel = new Node();
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
        this.currentPosition = this.sentinel;
    }

    public boolean isEmpty() {
        return this.currentPosition == this.sentinel;
    }

    public Item getCurrent() {
        return this.currentPosition.entry;
    }

    public void nextEntry() {
        setPosition(currentPosition.next);
    }

    public Item getNext() {
        return currentPosition.next.entry;
    }

    public void previousEntry() {
        setPosition(currentPosition.prev);
    }

    public Item getPrev() {
        return currentPosition.prev.entry;
    }

    public void setPosition(Node position) {
        if (position == sentinel) {
            throw new RuntimeException("No entry here");
        }
        this.currentPosition = position;
    }

    public void insertNode(Item it) {
        // Insert the newNode in front of the current node and move the position to the new node
        this.currentPosition = new Node(this.currentPosition, it, this.currentPosition.next);
    }

    public Item removeCurrent() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        // Remove the current node and move the current position to the previous node
        Node hold = this.currentPosition;
        this.currentPosition = hold.prev;
        this.currentPosition.next = hold.next;
        hold.next.prev = this.currentPosition;
        return hold.entry;
    }
}
