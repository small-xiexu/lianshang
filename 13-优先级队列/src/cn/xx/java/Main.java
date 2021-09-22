package cn.xx.java;

import cn.xx.java.queue.PriorityQueue;

/**
 * @author xiexu
 * @create 2021-09-14 4:33 下午
 */
public class Main {

    public static void main(String[] args) {
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person("jack", 2));
        queue.enQueue(new Person("rose", 10));
        queue.enQueue(new Person("jake", 5));
        queue.enQueue(new Person("james", 15));

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

}
