package bojch01;

public class HeapBag<E extends Comparable<E>> implements Cloneable {
    private E[] heap;
    private int manyItems;

    //creates an empty heap
    public HeapBag(){
        heap = (E[]) new Comparable[0];
        manyItems = 0;
    }
    //adds new element to heap
    public void add(E element){
        //increases the capacity if the array is full
        if(manyItems == heap.length){
            ensureCapacity(manyItems*2 + 1);
        }
        sort(element);
        manyItems++;
    }
    //sorts the elements in the array from least to greatest
    public void sort(E element){
        //if the heap has no elements, add the element to the first spot
        if(manyItems == 0){
            heap[0] = element;
        }else {
            int i = 0;
            //finds the where the element should go in the array
            while (i < manyItems && heap[i] != null && heap[i].compareTo(element) <= 0) {
                i++;
            }
            //shifts the positions of the other elements to have room for the new element
            for (int j = manyItems; j > i; j--) {
                heap[j] = heap[j-1];
            }
            //adds the new element into its rightful position
            heap[i] = element;
        }
    }
    //increases the space of the array
    public void ensureCapacity(int minimumCapacity){
        E[] biggerHeap;
        if (minimumCapacity > heap.length) {
            biggerHeap = (E[]) new Comparable[minimumCapacity];
            System.arraycopy(heap, 0, biggerHeap, 0, manyItems);
            heap = biggerHeap;
        }
    }
    public int size(){
        return manyItems;
    }
    //O(n) time complexity
    //combines two heap bags
    public static <E extends Comparable<E>> HeapBag<E> union(HeapBag<E> heap1, HeapBag<E> heap2){
        HeapBag<E> heap3 = new HeapBag<>();
        //adds the elements of the bag
        for (int i = 0; i < heap1.manyItems; i++){
            E current = heap1.heap[i];
            heap3.add(current);
        }
        //then adds the elements of the second bag
        for (int i = 0; i < heap2.manyItems; i++){
            E current = heap2.heap[i];
            heap3.add(current);
        }
        return heap3;
    }
    //creates a deep copy of the heap bag
    public Object clone(){
        try {
            HeapBag<E> newBag = (HeapBag<E>) super.clone();
            newBag.heap = (E[]) new Comparable[heap.length];

            //deep copies each element in the heap
            for (int i = 0; i < manyItems; i++) {
                if (heap[i] instanceof Cloneable) {
                    try {
                        newBag.heap[i] = (E) heap[i].getClass().getMethod("clone").invoke(heap[i]);
                    } catch (Exception e) {
                        throw new AssertionError("Clone not supported at index " + i, e);
                    }
                }
            }
            //updates manyItems to be the same as the bag being cloned
            newBag.manyItems = manyItems;
            return newBag;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
    //displays all the elements in the bag in ascending order
    public void printAll(){
        for(int i = 0 ; i < manyItems; i++){
            System.out.println(heap[i] + " ");
        }
        System.out.println();
    }
}
