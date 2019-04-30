package bretzj;

import java.util.*;

public class LookupTable<K extends Comparable<K>, V> implements Map {

    private List<Entry<K, V>> entries;
    private Set<K> keys = new HashSet<>();
    private ArrayList<V> values = new ArrayList<>();

    public LookupTable() {
        entries = new ArrayList<>();
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return findIndex((K) key) >= 0;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(Object key) {
        if (containsKey(key)) {
            int index = findIndex((K) key);
            return entries.get(index).getValue();
        }
        return null;
    }

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

    @Override
    public Object remove(Object key) {
        V removed = null;
        int index = findIndex((K) key);

        if (index >= 0) {
            removed = entries.remove(index).getValue();
        }
        return removed;
    }

    @Override
    public void clear() {
        entries = new ArrayList<>();
    }

    @Override
    public void putAll(Map m) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Collection<String[]> c) {
        for (String[] s : c) {
            put(s[0], s[1]);
        }
    }

    @Override
    public Set keySet() {
        return keys;
    }

    @Override
    public Collection values() {
        return values;
    }

    @Override
    public Set<Entry> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Entry e : entries) {
            sb.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
        }

        return sb.toString();
    }

    private int findIndex(K key) {
        return Collections.binarySearch(entries, new AbstractMap.SimpleEntry<>(key, null), Entry.comparingByKey());
    }
}
