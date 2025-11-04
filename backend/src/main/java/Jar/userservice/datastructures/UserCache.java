package Jar.userservice.datastructures;

import Jar.userservice.model.User;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserCache {

    private final int capacity;
    private final Map<String, User> cache;

    public UserCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<String, User>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, User> eldest) {
                return size() > capacity;
            }
        };
    }

    public void put(String key, User user) {
        cache.put(key, user);
    }

    public User get(String key) {
        return cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }
}

