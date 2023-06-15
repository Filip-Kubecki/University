package Lista2;

public interface SeriesGenerator<E>
{
    default E generate(int n) {
        return null;
    }
}