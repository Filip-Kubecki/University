package Lista5.SortingTester;

import java.util.Comparator;
import java.util.List;

import Lista5.SortingTester.core.SortingAlgorithm;
import Lista5.SortingTester.testing.*;
import Lista5.SortingTester.testing.generation.*;
import Lista5.SortingTester.testing.generation.array.*;

public class Main {

	public static void main(String[] args) {
		Comparator<Integer> comparator = null;
		
		Comparator<MarkedValue<Integer>> markedComparator = new MarkedValueComparator<Integer>(comparator);
		Generator<MarkedValue<Integer>> generator = new OrderedMarkedIntegerArrayGenerator();

		SortingAlgorithm<MarkedValue<Integer>> algorithm = new BubbleSort<MarkedValue<Integer>>(markedComparator);
		
		Result result = Tester.runNTimes(algorithm, generator, 10, 10);
		
		System.out.println("time [ms]: " + result.averageTimeInMilliseconds() + " +- " + result.timeStandardDeviation());
		System.out.println("comparisons: " + result.averageComparisons() + " +- " + result.comparisonsStandardDeviation());
		System.out.println("swaps: " + result.averageSwaps() + " +- " + result.swapsStandardDeviation());
		System.out.println("always sorted: " + result.sorted());
		System.out.println("always stable: " + result.stable());
	}

}
