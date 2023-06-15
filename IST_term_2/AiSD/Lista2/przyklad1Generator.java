package Lista2;

public class przyklad1Generator<E> implements SeriesGenerator<Integer> {
    @Override
    public Integer generate(int n) {
        return 2*n;
    }
}
