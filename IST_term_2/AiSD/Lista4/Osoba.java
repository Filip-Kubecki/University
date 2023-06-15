package Lista4;

public class Osoba {
    private String name;
    private String surname;
    private String PESEL;
    private int age;

    public Osoba(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
