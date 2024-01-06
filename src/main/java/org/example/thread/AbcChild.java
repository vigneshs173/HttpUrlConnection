package org.example.thread;

public class AbcChild extends Abc{

    public void display() {
        System.out.println("This is Abc Child");
    }

    public static void main(String[] args) {

        AbcChild abcChild = new AbcChild();
        abcChild.test();
    }
}
