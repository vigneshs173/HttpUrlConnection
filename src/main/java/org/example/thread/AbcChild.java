package org.example.thread;

public class AbcChild extends Abc{

    @Override
    public void display() {
        System.out.println("Child");
    }

    public static void main(String[] args) {

        AbcChild abcChild = new AbcChild();
        abcChild.test(abcChild);

    }
}
