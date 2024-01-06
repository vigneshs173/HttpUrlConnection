package org.example.thread;

public class Abc {

    public void test(Abc a) {
        display();
        a.display();
        put(a);

    }
    public void display() {
        System.out.println("Parent");
    }

    public void put(Abc a){
        a.display();
        call(a);
    }

    public void call(Abc a) {
        display();
    }
}


