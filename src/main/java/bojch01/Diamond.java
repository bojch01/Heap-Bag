package bojch01;

import java.io.Serializable;

public class Diamond implements Cloneable, Comparable<Diamond>, Serializable {
    private String stockNumber;
    private double size;
    private String clarityGrade;
    private char colorGrade;
    private String cut;
    private static final long serialVersionUID = 1208;

    public Diamond(String number, double size, String clarity, char color, String cut) {
        this.stockNumber = number;
        this.size = size;
        this.clarityGrade = clarity;
        this.colorGrade = color;
        this.cut = cut;
    }
    //displays diamond and its attributes
    public String toString() {
        return String.format("\nStock number: " + stockNumber
                + "\nSize: " + size
                + "\nClarity Grade: " + clarityGrade
                + "\nColor Grade: " + colorGrade
                + "\nCut: " + cut);
    }
    //creates copy of diamond
    public Diamond clone() {
        try {
            return (Diamond) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("This class does not implement Cloneable.");
        }

    }

    //ensures both diamonds have exactly the same attributes
    public boolean equals(Object j) {
        if (j.getClass() != this.getClass()) {
            return false;
        }

        Diamond d = (Diamond) j;

        return stockNumber.equals(d.stockNumber)
                && Double.compare(size, d.size) == 0
                && clarityGrade.equals(d.clarityGrade)
                && colorGrade == d.colorGrade
                && cut.equals(d.cut);
    }

    //compares two diamonds by size, clarity, and color
    public int compareTo(Diamond j) {
        if (j == null) {
            throw new NullPointerException();
        }
        if (j.getClass() != this.getClass()) {
            throw new ClassCastException();
        }
        //first compares sizes
        //if sizes are the same, the method will continue
        if (Double.compare(j.size, this.size) != 0) {
            return Double.compare(this.size, j.size);
        }
        String[] clarityGradeOrder = {"FL", "IF", "VVS1", "VVS2", "VS1", "VS2", "SI1", "SI2", "I1", "I2", "I3"};
        int thisIndex = -1;
        int jIndex = -1;
        //compares clarity grade based on array
        for (int i = 0; i < clarityGradeOrder.length; i++) {
            //matches value with an index in array
            if (clarityGradeOrder[i].equals(this.clarityGrade)) {
                thisIndex = i;
            }
            if (clarityGradeOrder[i].equals(j.clarityGrade)) {
                jIndex = i;
            }
        }
        //compares indexes
        //if indexes are the same, method continues
        if (thisIndex != jIndex) {
            return Integer.compare(thisIndex, jIndex);
        }
        //finally compares color grade
        if (Character.compare(j.colorGrade, this.colorGrade) < 0) {
            return -1;
        }
        if (Character.compare(j.colorGrade, this.colorGrade) > 0) {
            return 1;
        }
        //will return 0 if attributes are the same
        return 0;
    }
}
class DiamondArrayBag implements Cloneable, Serializable {
    private Diamond[] bag;
    private int manyItems;
    private static final long serialVersionUID = 1208;

    public DiamondArrayBag(int size) {
        bag = new Diamond[size];
    }

    public void add(Diamond jewel) {
        if (manyItems == bag.length) {
            ensureCapacity((manyItems + 1) * 2);
        }
        bag[manyItems] = jewel;
        manyItems++;
    }
    //ensures the bag does not run out of room
    public void ensureCapacity(int minimumCapacity) {
        Diamond[] biggerBag;
        if (minimumCapacity > bag.length) {
            biggerBag = new Diamond[minimumCapacity];
            System.arraycopy(bag, 0, biggerBag, 0, manyItems);
            bag = biggerBag;
        }
    }
    //checks if certain diamond is in the array
    public boolean contains(Diamond d) {
        for (int i = 0; i < manyItems; i++) {
            if (d.equals(bag[i])) {
                return true;
            }
        }
        return false;
    }
    //removes certain diamond from bag
    public boolean remove(Diamond d) {
        if(d == null){
            return false;
        }
        for (int i = 0; i < manyItems; i++) {
            if (d.equals(bag[i])) {
                bag[i] = null;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return manyItems;
    }
    //shortens the bag space if there are null diamonds
    public void trimToSize() {
        Diamond[] trimmedBag;
        if (bag.length != manyItems) {
            trimmedBag = new Diamond[manyItems];
            System.arraycopy(bag, 0, trimmedBag, 0, manyItems);
            bag = trimmedBag;
        }
    }
    //creates a bag that holds the intersection of two bags
    public static DiamondArrayBag intersection(DiamondArrayBag bag1, DiamondArrayBag bag2) {
        DiamondArrayBag bag3 = new DiamondArrayBag(Math.min(bag1.manyItems, bag2.manyItems));
        //checks if both bags have the same diamond
        for (int i = 0; i < bag1.manyItems; i++){
            Diamond current = bag1.bag[i];
            if(bag2.contains(current) && current != null){
                bag3.add(current);
            }
        }

        return bag3;
    }
    //creates a deep copy of a bag
    public DiamondArrayBag clone() {
        DiamondArrayBag newBag = new DiamondArrayBag(this.bag.length);
        for (int i = 0; i < manyItems; i++) {
            newBag.add(bag[i].clone());
        }

        return newBag;
    }
    //shows every diamond attribute in bag
    public String toString() {
        StringBuilder strings = new StringBuilder();
        for(Diamond d : bag){
            if(d != null){
                strings.append(d.toString());
            }
        }
        return strings.toString();
    }

    public Diamond getDiamond(int index){
        if(index >= 0) {
            return bag[index];
        }
        return null;
    }
    //counts the amount of times a certain diamond is in a bag
    public int countOccurrences(Diamond target){
        int answer =0, index;
        if(target!=null) {
            for (index = 0; index < manyItems; index++) {
                if (target.equals(bag[index])&&bag[index]!=null)
                    answer++;
            }
        }
        return answer;
    }
    public Diamond[] getDiamonds() {
        return bag;
    }

}

