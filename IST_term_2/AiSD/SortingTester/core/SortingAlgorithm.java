package Lista5.SortingTester.core;

import java.util.Comparator;
import java.util.List;

import Lista5.SortingTester.counting.CountingComparator;
import Lista5.SortingTester.counting.CountingSwapper;

public abstract class SortingAlgorithm<T> {
	
	protected CountingComparator<T> comparator;
	protected CountingSwapper swapper;
	
	public SortingAlgorithm(Comparator<? super T> comparator) {
		this.comparator = new CountingComparator<T>(comparator);
		swapper = new CountingSwapper();
	}
	
	public void reset() {
		comparator.reset();
		swapper.reset();
	}
	
	public long comparisons() {
		return comparator.count();
	}
	
	public long swaps() {
		return swapper.count();
	}
	
	public Comparator<? super T> baseComparator() {
		return comparator.baseComparator();
	}
	
	public abstract List<T> sort(List<T> list);

}
