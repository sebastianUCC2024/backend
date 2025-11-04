package Jar.userservice.datastructures;

import Jar.userservice.patterns.observer.UserEvent;
import java.util.LinkedList;
import java.util.Queue;

public class AuditQueue {

    private final Queue<UserEvent> queue;
    private final int maxSize;

    public AuditQueue(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void enqueue(UserEvent event) {
        if (queue.size() >= maxSize) {
            queue.poll(); // Remover el más antiguo
        }
        queue.offer(event);
    }

    public UserEvent dequeue() {
        return queue.poll();
    }

    public UserEvent peek() {
        return queue.peek();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

