package com.iflytek.study.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 使用代理方式，代理LinkedHashMap 来实现的
 * Least recently cache，最近最少使用
 * @author : wei
 * @date : 2018/3/28
 */
public class LRUCache<K,V>{

    private final int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private LinkedHashMap<K,V> map;

    public LRUCache(int max_chache_size) {
        MAX_CACHE_SIZE = max_chache_size;
        int capacity = (int) Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1;
        //accessOrder 设置为true，自动按照
        map = new LinkedHashMap<K, V>(capacity,DEFAULT_LOAD_FACTOR,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    public synchronized void put(K key, V value){
        map.put(key,value);
    }

    public synchronized V get(K key){
        return map.get(key);
    }

    public synchronized void remove(K key){
        map.remove(key);
    }

    public synchronized Set<Map.Entry<K,V>> getAll(){
        return map.entrySet();
    }

    public synchronized int size(){
        return map.size();
    }

    public synchronized void clear(){
        map.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<K,V> entry : map.entrySet()){
            sb.append(String.format("%s:%s",entry.getKey(),entry.getValue()));
        }
        return sb.toString();
    }
}
