package com.shumchenia.clevertec.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("LFU")
public class LFUCache<K, V> implements Cache<K, V> {

    public class Node {

        K key;
        V value;
        int frequency;
        Node prev;
        Node next;

        public Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    Node head;
    Node tail;
    Map<K, Node> map = new HashMap<>();

    @Value("${cache.capacity}")
    int capacity;

    @Override
    public void remove(K key) {
        Node item = map.get(key);
        map.remove(key);
        removeNode(item);
    }

    @Override
    public Optional<V> get(K key) {

        if (map.get(key) == null) {
            return Optional.empty();
        }

        Node item = map.get(key);
        // move to right position according to frequency
        removeNode(item);
        item.frequency = item.frequency + 1;
        addNodeWithUpdatedFrequency(item);

        return Optional.of(item.value);
    }

    @Override
    public void put(K key, V value) {

        if (map.containsKey(key)) {
            Node item = map.get(key);
            item.value = value;
            item.frequency = item.frequency + 1;
            // move to right position according to frequency
            removeNode(item);
            addNodeWithUpdatedFrequency(item);
        } else {
            if (map.size() >= capacity) {
                // delete head with least frequency and least recently used
                map.remove(head.key);
                removeNode(head);
            }

            // move to right position according to frequency
            Node node = new Node(key, value, 1);
            addNodeWithUpdatedFrequency(node);
            map.put(key, node);
        }
    }

    public void removeNode(Node node) {

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void addNodeWithUpdatedFrequency(Node node) {

        if (tail != null && head != null) {
            Node temp = head;
            while (temp != null) {
                if (temp.frequency > node.frequency) {
                    if (temp == head) {
                        node.next = temp;
                        temp.prev = node;
                        head = node;
                        break;
                    } else {
                        node.next = temp;
                        node.prev = temp.prev;
                        temp.prev.next = node;
                        temp.prev = node;
                        break;
                    }
                } else {
                    temp = temp.next;
                    if (temp == null) {
                        tail.next = node;
                        node.prev = tail;
                        node.next = null;
                        tail = node;
                        break;
                    }
                }
            }
        } else {
            tail = node;
            head = tail;
        }
    }
}
