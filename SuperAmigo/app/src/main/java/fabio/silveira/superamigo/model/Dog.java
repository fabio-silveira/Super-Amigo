package fabio.silveira.superamigo.model;

import java.io.Serializable;

public class Dog {

    public String name;
    public String age;
    public String size;
    public String gender;
    public String key;

    public Dog() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return  "\nName: " + name +
                "\nAge: " + age +
                "\nSize: " + size +
                "\nGender: " + gender;
    }
}
