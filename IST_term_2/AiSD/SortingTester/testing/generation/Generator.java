package Lista5.SortingTester.testing.generation;

import java.util.List;

public interface Generator<T> {
	List<T> generate(int size);
}
