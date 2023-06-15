package Lista4;

public class main {
    public static void main(String[] args) {
//        oneway();
//        twoway();
//        onewaycycled();
        twowaycycled();
    }

    public static void oneway(){
        OneWayLinkedListStudent list = new OneWayLinkedListStudent<>();
        list.add(new Student("Jacek","nazwisko",2));
        list.add(new Student("Jacek","nazwisko",3));
        list.add(new Student("Jacek","nazwisko",1));

        System.out.println("Lista");
        for (Object o : list) {
            System.out.println(((Student)o).toString());
        }

        System.out.println("\n"+((Student)list.returnOnKey(1)).toString());
    }
    public static void twoway(){
        TwoWayLinkedListStudent list = new TwoWayLinkedListStudent<>();
        list.add(new Student("Jacek","nazwisko",2));
        list.add(new Student("Jacek","nazwisko",2));
        list.add(new Student("Jacek","nazwisko",1));

        for (Object o : list) {
            System.out.println(((Student)o).toString());
        }
    }

    public static void onewaycycled(){
        OneWayCycledListStudent list = new OneWayCycledListStudent<>();
        list.add(new Student("Jacek","nazwisko",2));
        list.add(new Student("Jacek","nazwisko",1));
        list.add(new Student("Jacek","nazwisko",2));
        System.out.println(list.removeOnKey(2));
        for (Object o : list) {
            System.out.println(((Student)o).toString());
        }
    }
    public static void twowaycycled(){
        TwoWayCycledListStudent list = new TwoWayCycledListStudent<>();
        list.add(new Student("Jacek","nazwisko",2));
        list.add(new Student("Jacek","nazwisko",2));
        list.add(new Student("Jacek","nazwisko",1));

        for (Object o : list) {
            System.out.println(((Student)o).toString());
        }
    }
}
