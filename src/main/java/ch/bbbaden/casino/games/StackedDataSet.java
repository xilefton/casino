package ch.bbbaden.casino.games;

import org.omg.CORBA.Object;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The <code>StackedDataSet</code> allows too safe big amounts of Data while staying efficient. Data gets added on
 * top of the stack and can't be removed afterwards
 *
 * @author XelaaleX1234
 */

//Todo update all java doc Comments implement unfinished methods and check if everything corresponds to the java docs

public class StackedDataSet<E> implements Set<E> {
    /**
     * The <code>stack</code> Array is used to save the actual Data.
     */
    private E[] stack;
    /**
     * The <code>cache</code> Array caches a
     * {@link StackedDataSet#toArray() stripped} version of the
     * {@link StackedDataSet#stack stack}.
     */
    private E[] cache;
    /**
     * knows if the {@link StackedDataSet#cache cache}. is up to date
     */
    private boolean changed = true;
    /*
     * knows the current index of the stack
     */
    private int index = 0;

    private int maxSize;

    private Class<E> genClass;

    private int cacheIndex = 0;

    /**
     * The stack is set to it's default value: <code>200'000</code>.
     */
    public StackedDataSet() {
        //More or less memory safe size
        this.maxSize = 200000;
    }

    /**
     * @param maxSize maxSize sets a limit of how much the Dataset can store usually this is {@code Integer.MAX_VALUE -8}
     *                since this is the highest considered safe length for a regular Array. Setting a lower Value
     *                might improve cpu performance marginally and ram usage a bit if you already know
     */
    public StackedDataSet(int maxSize) {
        this.maxSize = maxSize;
        @SuppressWarnings("unchecked") final E[] stack = (E[]) new Object[maxSize];
        this.stack = stack;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < index; i++) {
            if (stack[i].equals(o)) {
                for (int j = i; j < index; j++) {
                    stack[j] = stack[j + 1];
                    changed = true;
                    cacheIndex = i;
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean addAll(Collection c) {
        //Todo
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        //Todo
        return false;
    }

    /**
     * @param index the index of the Evaluable object.
     * @return returns the Evaluable object at a given index.
     */
    public E get(int index) {
        return stack[index];
    }

    /**
     * Returns the stack array stripped if you're looping through it afterwards you might consider using the
     * {@link StackedDataSet#toArray(boolean) non-stripped version}.
     *
     * @return Calls {@code toArray(true)} for ease of use.
     */
    public E[] toArray() {
        return toArray(true);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) stack.clone();
    }

    /**
     * Adds a the value on top of the stack
     *
     * @param e the <code>Generified Object</code> is stored in the {@link StackedDataSet#stack stack}.
     */
    @Override
    public boolean add(java.lang.Object e) {
        stack[index] = (E) e;
        changed = true;
        index++;
        return true;
    }

    @Override
    public boolean remove(java.lang.Object o) {
        return false;
    }


    @Override
    public void clear() {
        @SuppressWarnings("unchecked") final E[] stack = (E[]) new Object[maxSize];
        this.stack = stack;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }


    @Override
    public boolean containsAll(Collection c) {
        @SuppressWarnings("unchecked") final E[] tempArray = (E[]) c.toArray();
        for (int i = 0; i < tempArray.length; i++) {
            if (!this.contains(tempArray[i])) return false;
        }
        return true;
    }

    /**
     * @param strip Decides whether the stripped or not stripped version of the Array is returned. The stripped version
     *              gets rid of the empty positions at the end. In some cases the non stripped version can be more
     *              efficient, eg. if you directly loop through the evaluableSet afterwards because you want too print it on
     *              the console you can actually safe a loop of writing the Evaluable too a new Array.
     * @return returns the Array that the Dataset uses to store all Evaluable
     */
    public E[] toArray(boolean strip) {
        if (strip) {
            if (changed) {
                @SuppressWarnings("unchecked") final E[] cache = (E[]) new Object[maxSize];
                this.cache = stack;
                for (int i = cacheIndex; i < cache.length; i++) {
                    cache[i] = stack[i];
                }
                this.cache = cache;
                cacheIndex = index;
            }
            return cache.clone();
        }
        return stack.clone();
    }

    /**
     * Adds an Array to the StackedDataSet
     *
     * @param eArray The Evaluable that gets added on top of the existing Array it won't be evaluated in any kind of manner.
     *               If you just want to override the existing Evaluable in the set {@link StackedDataSet#setAll setAll()}
     *               is way more efficient though.
     */
    public void addAll(E[] eArray) {
        for (int i = 0; i < eArray.length; i++) {
            if (eArray[i] == null) break;
            this.stack[i + index] = eArray[i];
        }
        index += eArray.length;
        changed = true;
    }

    /**
     * Replaces the Dataset with the given one.
     *
     * @param set the set this stacked set will be overwritten with.
     */
    public void setAll(Set<E> set) {
        this.stack = set.toArray(stack);
        changed = true;
    }

    public int size() {
        return index;
    }

    @Override
    public boolean isEmpty() {
        return index == 0;
    }

    @Override
    public boolean contains(java.lang.Object o) {
        for (int i = 0; i < index; i++) {
            if (stack[i].equals(o)) return true;
        }
        return false;
    }

    public Iterator<E> iterator() throws NoSuchElementException {
        return new Iterator<E>() {
            int iteratorIndex;

            @Override
            public boolean hasNext() {
                return stack[iteratorIndex + 1] != null;
            }

            @Override
            public E next() {
                if (stack[iteratorIndex + 1] == null) throw new NoSuchElementException();
                iteratorIndex++;
                return stack[iteratorIndex];
            }
        };
    }

    public void set(int index, E e) {
        if (index <= maxSize) {
            if (stack[this.index - 1] != null) {
                stack[index] = e;
            } else {
                throw new NoSuchElementException("Value before Index is null");
            }
        } else {
            throw new IndexOutOfBoundsException("Index is bigger than max size");
        }
    }
}
