package cn.xx.java;

/**
 * @author xiexu
 * @create 2021-09-14 4:43 下午
 */
public class Person implements Comparable<Person> {
    private String name;

    private int boneBreak;

    public Person(String name, int boneBreak) {
        this.name = name;
        this.boneBreak = boneBreak;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoneBreak() {
        return boneBreak;
    }

    public void setBoneBreak(int boneBreak) {
        this.boneBreak = boneBreak;
    }

    @Override
    public int compareTo(Person person) {
        return this.boneBreak - person.boneBreak;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", boneBreak=" + boneBreak +
                '}';
    }
}
