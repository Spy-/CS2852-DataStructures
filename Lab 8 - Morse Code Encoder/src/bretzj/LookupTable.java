/*
 * Course: CS2852
 * Spring 2019
 * Lab 8 - Morse Code Encoder
 * Name: John Bretz
 * Created: 4/26/2019
 */
package bretzj;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for a lookup table map
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LookupTable<K extends Comparable<K>, V> implements Map {

    private List<Entry<K, V>> entries;
    private Set<K> keys = new HashSet<>();
    private ArrayList<V> values = new ArrayList<>();

    /**
     * Constructor
     */
    public LookupTable() {
        entries = new ArrayList<>();
    }

    /**
     * The size of the map
     *
     * @return the size of the map
     */
    @Override
    public int size() {
        return entries.size();
    }

    /**
     * is the map empty
     *
     * @return true if the map is empty
     */
    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Does the map have the key
     *
     * @param key the key to search for
     * @return true if the map has the key
     */
    @Override
    public boolean containsKey(Object key) {
        return findIndex((K) key) >= 0;
    }

    /**
     * Does the map have the value
     *
     * @param value the value to search for
     * @return true if the map has the value
     */
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value associated to the given key
     *
     * @param key the key
     * @return the value of the key else null
     */
    @Override
    public Object get(Object key) {
        if (containsKey(key)) {
            int index = findIndex((K) key);
            return entries.get(index).getValue();
        }
        return null;
    }

    /**
     * Adds a value based on the key. If the key exists replace the current
     * value with the new value
     *
     * @param key   the key
     * @param value the value
     * @return value if insertion was successful
     */
    @Override
    public Object put(Object key, Object value) {
        V previousValue = null;
        int index = findIndex((K) key);

        if (index >= 0) {
            // key already exists
            previousValue = entries.get(index).setValue((V) value);
        } else {
            // add a new key
            entries.add(-index - 1, new AbstractMap.SimpleEntry<>((K) key, (V) value));
        }

        keys.add((K) key);
        values.add((V) value);

        return previousValue;
    }

    /**
     * Removes the entry with the given key
     *
     * @param key the key to remove
     * @return key if removal was successful
     */
    @Override
    public Object remove(Object key) {
        V removed = null;
        int index = findIndex((K) key);

        if (index >= 0) {
            removed = entries.remove(index).getValue();
        }
        return removed;
    }

    /**
     * Clears the map of all entries
     */
    @Override
    public void clear() {
        entries = new ArrayList<>();
    }

    /**
     * Add multiple entries to this
     *
     * @param m another map
     */
    @Override
    public void putAll(Map m) {
        throw new UnsupportedOperationException();
    }

    /**
     * Add a collection of key value pairs
     *
     * @param c some collection of key, value pairs
     */
    public void putAll(Collection<String[]> c) {
        for (String[] s : c) {
            put(s[0], s[1]);
        }
    }

    /**
     * A set of all keys
     *
     * @return the keys
     */
    @Override
    public Set keySet() {
        return keys;
    }

    /**
     * A collection of all values
     *
     * @return the values
     */
    @Override
    public Collection values() {
        return values;
    }

    /**
     * A set of all entries from the map
     *
     * @return the set of entries
     */
    @Override
    public Set<Entry> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts this to a readable String
     *
     * @return a readable String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Entry e : entries) {
            sb.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Find the index where the key exists
     *
     * @param key the key
     * @return the index else the location where the key should be added
     */
    private int findIndex(K key) {
        return Collections.binarySearch(
                entries,
                new AbstractMap.SimpleEntry<>(key, null),
                Entry.comparingByKey()
        );
    }
}
