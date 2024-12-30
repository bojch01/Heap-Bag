package bojch01;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //creates a minheap bag to hold the diamonds
        HeapBag<Diamond> myDiamondHeap = new HeapBag<>();
        FileInputStream fStream = new FileInputStream("Diamonds.ser");
        ObjectInputStream inputFile = new ObjectInputStream(fStream);
        //reads file of diamonds
        DiamondArrayBag diamondArrayBag = (DiamondArrayBag) inputFile.readObject();
        //places diamonds in array then are added to the heap
        Diamond[] diamonds = diamondArrayBag.getDiamonds();
        for (Diamond diamond : diamonds) {
            if (diamond != null) {
                myDiamondHeap.add(diamond);
            }
        }
        //shows all the diamonds in heap from ascending order
        System.out.println("Diamond Heap Bag Displayed: ");
        myDiamondHeap.printAll();
        //creates a second heap bag that is a clone of the first one
        HeapBag<Diamond> myDiamondHeap2;
        myDiamondHeap2 = (HeapBag<Diamond>) myDiamondHeap.clone();
        //creates another bag that is a union of the diamond heap bag and its clone
        HeapBag<Diamond> myDiamondHeap3 = HeapBag.union(myDiamondHeap, myDiamondHeap2);
        //shows all the diamonds in the union heap bag in ascending order
        System.out.println("Diamond Union Displayed: ");
        myDiamondHeap3.printAll();
    }
}