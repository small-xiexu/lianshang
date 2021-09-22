package cn.xx.java;

/**
 * @author xiexu
 * @create 2021-07-12 5:09 下午
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(10, "jack"));
        persons.add(new Person(20, "james"));
        persons.add(new Person(30, "rose"));
        System.out.println(persons);
    }
}
