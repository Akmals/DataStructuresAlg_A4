package a4;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;

/**
* Template code for assignment 4, CS146
* Name: Akmal Shaikh
* Student ID: 016450382
* Description of solution: This solution utilized the template provided from Canvas in A4 files
* For this assignment I am implementing Insertion Sort, Selection Sort, Merge Sort, 
* Heap Sort, Quick Sort, and Bucket Sort and then Testing their efficiencies Then find their
* runtimes, I will then be implementing a BFS from my A3 Assignment to 
* Search of the people in the network who are reachable from person id 3980. 
* 
* @author andreopo
*/

public class Sort {
/**
* Build a random array
* @param rand a Random object
* @param LENGTH The range of the integers in the array
* will be from 0 to LENGTH-1
* @return
*/
	private static int[] build_random_array(Random rand, int LENGTH) {
	int[] array = new int[LENGTH];
	
	//set index 0 to 0 for consistency with CLRS, where sorting starts at index 1
	array[0] = 0;

	for (int i = 1; i < LENGTH; i++) {
		// Generate random integers in range 0 to 999
		int rand_int = rand.nextInt(LENGTH);
		array[i] = rand_int;
		}

	return array;
	}
/**
* Assert the array is sorted correctly
* @param array_unsorted The original unsorted array
* @param array_sorted The sorted array
*/
public static void assert_array_sorted(int[] array_unsorted, int[] array_sorted) {
	int a_sum = array_unsorted[0];
	int b_sum = array_sorted[0];
	for (int i = 1; i < array_unsorted.length; i++) {
		a_sum += array_unsorted[i];
	}
	
	for (int i = 1; i < array_sorted.length; i++) {
		b_sum += array_sorted[i];
		assertTrue(array_sorted[i-1] <= array_sorted[i]);
		}
		
	assertEquals(a_sum, b_sum);

	}

//implements the Insertion sort
public static void insertionSort(int[] array) {
	for (int i = 1; i < array.length; i++) {
        int key = array[i];
        int j = i - 1;

        while (j >= 0 && array[j] > key) {
            array[j + 1] = array[j];
            j = j - 1;
        }
        array[j + 1] = key;
    }
}
//implement Selection sort
public static void selectionSort(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < array.length; j++) {
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
        }
        int temp = array[minIndex];
        array[minIndex] = array[i];
        array[i] = temp;
    }
}
//implementing Heap sort
public static void heapSort(int[] array) {
    int n = array.length;

    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(array, n, i);
    }

    for (int i = n - 1; i > 0; i--) {
        int temp = array[0];
        array[0] = array[i];
        array[i] = temp;

        heapify(array, i, 0);
    }
}
private static void heapify(int[] array, int n, int i) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    if (left < n && array[left] > array[largest]) {
        largest = left;
    }

    if (right < n && array[right] > array[largest]) array[largest] = array[right];
    if (largest != i) {
        int swap = array[i];
        array[i] = array[largest];
        array[largest] = swap;

        heapify(array, n, largest);
    }
}

//implement merge sort
public static void mergeSort(int[] array) {
    if (array.length < 2) return;

    int mid = array.length / 2;
    int[] left = new int[mid];
    int[] right = new int[array.length - mid];

    System.arraycopy(array, 0, left, 0, mid);
    System.arraycopy(array, mid, right, 0, array.length - mid);

    mergeSort(left);
    mergeSort(right);

    mergeSort(array, left, right);
}

private static void mergeSort(int[] array, int[] left, int[] right) {
    int i = 0, j = 0, k = 0;
    while (i < left.length && j < right.length) {
        if (left[i] <= right[j]) {
            array[k++] = left[i++];
        } else {
            array[k++] = right[j++];
        }
    }
    while (i < left.length) {
        array[k++] = left[i++];
    }
    while (j < right.length) {
        array[k++] = right[j++];
    }
}

//implement quicksort
public static void quickSort(int[] array) {
    quickSort(array, 0, array.length - 1);
}

private static void quickSort(int[] array, int low, int high) {
    if (low < high) {
        int pi = partition(array, low, high);

        quickSort(array, low, pi - 1);
        quickSort(array, pi + 1, high);
    }
}

private static int partition(int[] array, int low, int high) {
    int pivot = array[high];
    int i = (low - 1);

    for (int j = low; j < high; j++) {
        if (array[j] < pivot) {
            i++;
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    int temp = array[i + 1];
    array[i + 1] = array[high];
    array[high] = temp;

    return i + 1;
}


//implementing bucket
public static void bucketSort(int[] array) {
	int bucketCount = array.length/2;
	int minIntValue = 0;
	int maxIntValue = array.length - 1;

	// Create bucket array
	List<Integer>[] buckets = new List[bucketCount];

	// Associate a list with each index in the bucket array
	for(int i = 0; i < bucketCount; i++){
		buckets[i] = new LinkedList<>();
	}

// Assign integers from array to the proper bucket
	for (int value : array) {
        // Normalize the value to the bucket index range
        int bucketIndex = (value - minIntValue) * (bucketCount - 1) / (maxIntValue - minIntValue);
        buckets[bucketIndex].add(value);
    }

	// sort buckets
	for(List<Integer> bucket : buckets){
	Collections.sort(bucket);
		}
	int i = 0;
	// Merge buckets to get sorted array
	for(List<Integer> bucket : buckets){
		for(int num : bucket){
			array[i++] = num;
		}
	}
}

public static void main(String[] args) {
	int NUM_RUNS = 3;

	// create instance of Random class
	Random rand = new Random();
	/////////////////////////////////////////
	int LENGTH=100;
	System.out.println("_____________INPUT "+LENGTH+"_____________");
	int[] array_100 = build_random_array(rand, LENGTH);

	//For runtime computations
	long startTime, endTime;
	double duration = 0;
	for (int t = 0 ; t < NUM_RUNS ; t++) {

		int[] array_100_c = array_100.clone();
		startTime = System.currentTimeMillis();
		insertionSort(array_100_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
		assert_array_sorted(array_100, array_100_c);
	}
	
	duration = duration / (double) NUM_RUNS;
	System.out.println("InsertionSort mean runtime over " + NUM_RUNS + "runs is " + duration);
			duration = 0;
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100_c = array_100.clone();
		startTime = System.currentTimeMillis();
		selectionSort(array_100_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
		assert_array_sorted(array_100, array_100_c);
	}
	
	duration = duration / (double) NUM_RUNS;
	System.out.println("SelectionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
			duration = 0;
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100_c = array_100.clone();
		startTime = System.currentTimeMillis();
		heapSort(array_100_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
		assert_array_sorted(array_100, array_100_c);
	}	

	duration = duration / (double) NUM_RUNS;
	System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is" + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100_c = array_100.clone();
		startTime = System.currentTimeMillis();
		mergeSort(array_100_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
		assert_array_sorted(array_100, array_100_c);
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100_c = array_100.clone();
		startTime = System.currentTimeMillis();
		quickSort(array_100_c);
		endTime = System.currentTimeMillis();
	duration += ((double) (endTime - startTime));
	assert_array_sorted(array_100, array_100_c);
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100_c = array_100.clone();
		startTime = System.currentTimeMillis();
		bucketSort(array_100_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
		assert_array_sorted(array_100, array_100_c);
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);

	/////////////////////////////////////////
	LENGTH=1000;
	System.out.println("_____________INPUT "+LENGTH+"_____________");
	
	int[] array_1000 = build_random_array(rand, LENGTH);
	duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_1000_c = array_1000.clone();
		startTime = System.currentTimeMillis();
		insertionSort(array_1000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_1000_c = array_1000.clone();
		startTime = System.currentTimeMillis();
		selectionSort(array_1000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("SelectionSort mean runtime over " + NUM_RUNS + "runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_1000_c = array_1000.clone();
		startTime = System.currentTimeMillis();
		heapSort(array_1000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is" + duration);
	duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_1000_c = array_1000.clone();
		startTime = System.currentTimeMillis();
		mergeSort(array_1000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_1000_c = array_1000.clone();
		startTime = System.currentTimeMillis();
	quickSort(array_1000_c);
	endTime = System.currentTimeMillis();
	duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_1000_c = array_1000.clone();
		startTime = System.currentTimeMillis();
		bucketSort(array_1000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);

	/////////////////////////////////////////
	LENGTH=10000;
	System.out.println("_____________INPUT "+LENGTH+"_____________");

	int[] array_10000 = build_random_array(rand, LENGTH);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_10000_c = array_10000.clone();
		startTime = System.currentTimeMillis();
		insertionSort(array_10000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_10000_c = array_10000.clone();
		startTime = System.currentTimeMillis();
		selectionSort(array_10000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("SelectionSort mean runtime over " + NUM_RUNS + "runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_10000_c = array_10000.clone();
		startTime = System.currentTimeMillis();
		heapSort(array_10000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_10000_c = array_10000.clone();
		startTime = System.currentTimeMillis();
		mergeSort(array_10000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_10000_c = array_10000.clone();
		startTime = System.currentTimeMillis();
		quickSort(array_10000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}	

	duration = duration / (double) NUM_RUNS;
	System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_10000_c = array_10000.clone();
		startTime = System.currentTimeMillis();
		bucketSort(array_10000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);
	
	/////////////////////////////////////////
	LENGTH=100000;
	System.out.println("_____________INPUT "+LENGTH+"_____________");
	
	int[] array_100000 = build_random_array(rand, LENGTH);
	duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100000_c = array_100000.clone();
		startTime = System.currentTimeMillis();
		insertionSort(array_100000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100000_c = array_100000.clone();
		startTime = System.currentTimeMillis();
		selectionSort(array_100000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("SelectionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100000_c = array_100000.clone();
		startTime = System.currentTimeMillis();
		heapSort(array_100000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100000_c = array_100000.clone();
		startTime = System.currentTimeMillis();
		mergeSort(array_100000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;

	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100000_c = array_100000.clone();
		startTime = System.currentTimeMillis();
		quickSort(array_100000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		duration = 0;
	
	for (int t = 0 ; t < NUM_RUNS ; t++) {
		int[] array_100000_c = array_100000.clone();
		startTime = System.currentTimeMillis();
		bucketSort(array_100000_c);
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
	}

	duration = duration / (double) NUM_RUNS;
	System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);
	}	
}
