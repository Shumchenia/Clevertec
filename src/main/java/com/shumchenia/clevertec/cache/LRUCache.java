package com.shumchenia.clevertec.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component("LRU")
public final class LRUCache<K, V> implements Cache<K, V> {

    private class Node {
        private V value;
        private K key;
        private Node next, prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    @Value("${cache.capacity}")
    private int maxCapacity;
    private ConcurrentHashMap<K, Node> map = new ConcurrentHashMap<>();
    private Node head, tail;

    @Override
    public Optional<V> get(K key) {
        Node node = map.get(key);
        removeNode(node);
        offerNode(node);
        return node != null ? Optional.of(node.value) : Optional.empty();
    }

    @Override
    public void remove(K key) {
        Node node = map.get(key);
        map.remove(key);
        removeNode(node);
    }

    @Override
    public void put(K key, V value) {
        if (map.contains(key)) {
            Node node = map.get(key);
            node.value = value;
            removeNode(node);
            offerNode(node);
        } else {
            if (map.size() == maxCapacity) {
                System.out.println("maxCapacity of cache reached");
                map.remove(head.key);
                removeNode(head);
            }

            Node node = new Node(key, value);
            offerNode(node);
            map.put(key, node);
        }
    }


    public void removeNode(Node node) {
        if (node == null)
            return;

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

    private void offerNode(Node node) {
        if (node == null)
            return;
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
        }
    }

}