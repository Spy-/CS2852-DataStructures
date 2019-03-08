package weekOne;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PseudoArrayList<E> implements List<E> {

    private E[] array;

    public PseudoArrayList() {
        array = (E[]) new Object[0];
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E e : array) {
            if (e.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public E[] toArray() {
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E element) {
        E[] tmp = (E[]) new Object[size() + 1];
        for (int i = 0; i < size(); i++) {
            tmp[i] = array[i];
        }
        tmp[size()] = element;
        array = tmp;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        E[] tmp = (E[]) new Object[size() - 1];

        for (int i = 0; i < index; i++) {
            tmp[i] = array[i];
        }

        for (int i = index+1; i < size(); i++) {
            tmp[i-1] = array[i];
        }

        array = tmp;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        array = (E[]) new Object[0];
    }

    @Override
    public E get(int index) {
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        array[index] = element;
        return element;
    }

    @Override
    public void add(int index, E element) {
        E[] tmp = (E[]) new Object[size() + 1];

        for (int i = 0; i < index; i++) {
            tmp[i] = array[i];
        }
        for (int i = index + 1; i < size(); i++) {
            tmp[i] = array[i];
        }
        tmp[index] = element;
        array = tmp;
    }

    @Override
    public E remove(int index) {
        E[] tmp = (E[]) new Object[size() - 1];

        for (int i = 0; i < index; i++) {
            tmp[i] = array[i];
        }
        for (int i = index + 1; i < size(); i++) {
            tmp[i] = array[i];
        }

        array = tmp;
        return array[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
