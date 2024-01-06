package org.example.thread;

public class ChildThread extends Thread{
    public static void main(String[] args) {
        ChildThread childThread = new ChildThread();
        childThread.start();

        System.out.println("HI");
        System.out.println("HI");
        System.out.println("HI");
        System.out.println("HI");

    }
    public void run(){
        System.out.println("HELLO");
        System.out.println("HELLO");
        System.out.println("HELLO");
        System.out.println("HELLO");
    }
}
