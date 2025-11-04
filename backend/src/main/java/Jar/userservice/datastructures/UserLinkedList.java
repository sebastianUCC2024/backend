package Jar.userservice.datastructures;

import Jar.userservice.model.User;

public class UserLinkedList {

    private Node head;
    private int size;

    private static class Node {
        User user;
        Node next;

        Node(User user) {
            this.user = user;
            this.next = null;
        }
    }

    public void add(User user) {
        Node newNode = new Node(user);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public User find(String username) {
        Node current = head;
        while (current != null) {
            if (current.user.getUsername().equals(username)) {
                return current.user;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String username) {
        if (head == null) return false;

        if (head.user.getUsername().equals(username)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.user.getUsername().equals(username)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        head = null;
        size = 0;
    }
}
