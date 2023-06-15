package Lista4;

public class Student extends Osoba{
    public String nrIndexu;
    public int yearOfUniversity;
    public int avgScore;
    public int key;

    public Student(String name, String surname, int yearOfUniversity) {
        super(name, surname);
        this.yearOfUniversity = yearOfUniversity;
    }

    public String getNrIndexu() {
        return nrIndexu;
    }

    public void setNrIndexu(String nrIndexu) {
        this.nrIndexu = nrIndexu;
    }

    public int getYearOfUniversity() {
        return yearOfUniversity;
    }

    public void setYearOfUniversity(int yearOfUniversity) {
        this.yearOfUniversity = yearOfUniversity;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    @Override
    public String toString() {
        return this.getName()+" "+this.getSurname()+" "+this.getYearOfUniversity();
    }
}
