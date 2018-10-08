package com.shorturl.shared.cache.LRUCache;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LocalCache<T1, T2> {

  private final ReentrantLock lock = new ReentrantLock();

  private final Map<T1, T2> map;

  private final Deque<T1> queue;

  private final int limit;

  public LocalCache(int limit) {
    this.map = new ConcurrentHashMap<>();
    this.queue = new LinkedList<>();
    this.limit = limit;
  }

  public void put(T1 key, T2 value) {
    T2 oldValue = this.map.put(key, value);
    if (oldValue != null) {
      this.removeThenAddKey(key);
    } else {
      this.addKey(key);
    }
    //Check map size
    if (this.map.size() > this.limit) {
      this.map.remove(this.removeLast());
    }
  }

  public T2 get(T1 key) {
    T2 value = this.map.get(key);
    if (value != null) {
      this.removeThenAddKey(key);
    }
    return value;
  }

  private void removeThenAddKey(T1 key) {
    this.lock.lock();
    try {
      this.queue.removeFirstOccurrence(key);
      this.queue.addFirst(key);
    } finally {
      this.lock.unlock();
    }
  }

  private void addKey(T1 key) {
    this.lock.lock();
    try {
      this.queue.addFirst(key);
    } finally {
      this.lock.unlock();
    }
  }

  private T1 removeLast() {
    this.lock.lock();
    T1 var2;
    try {
      var2 = this.queue.removeLast();

    } finally {
      this.lock.unlock();
    }
    return var2;
  }

}