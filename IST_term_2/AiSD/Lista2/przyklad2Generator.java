package Lista2;

public class przyklad2Generator<E> implements SeriesGenerator<String> {
    @Override
    public String generate(int n) {
        String constant = "a";
        return constant.repeat(n);
    }
}
