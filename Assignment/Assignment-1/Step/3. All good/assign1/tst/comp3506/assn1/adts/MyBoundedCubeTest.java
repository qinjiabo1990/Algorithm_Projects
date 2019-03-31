package comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import org.junit.Test;


public class MyBoundedCubeTest {

	@Test(timeout=500)
	public void testElementOutOfBounds() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element = new Object();
		testCube.add(1, 1, 1, element);
		assertThat("No element in this position", testCube.get(2, 2, 2), is(equalTo(null)));
	}

	@Test(timeout=500)
	public void testGetWithMultipleElements() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(1, 1, 1, element2);
		testCube.add(2, 2, 2, element1);
		testCube.add(2, 2, 2, element2);
		testCube.add(3, 3, 3, element1);
		testCube.add(3, 3, 3, element2);
		assertThat("First element added at a position was not returned.", testCube.get(2, 2, 2), is(equalTo(element1)));
	}
	
	@Test(timeout=500)
	public void testGetWithMultipleElementsA() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(2, 2, 2, element2);
		assertThat("First element added at a position was not returned.", testCube.get(1, 1, 1), is(equalTo(element1)));
	}

	@Test(timeout=500)
	public void testGetWithMultipleElementsB() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(2, 2, 2, element2);
		assertThat("First element added at a position was not returned.", testCube.get(2, 2, 2), is(equalTo(element2)));
	}
	
	@Test(timeout=500)
	public void testIsMultipleElementsAtWithTwoElement() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(1, 1, 1, element2);
		assertThat("One element at a position indicates it is multiple.", 
				   testCube.isMultipleElementsAt(1, 1, 1), is(equalTo(true)));
	}
	
	@Test(timeout=500)
	public void testGetAllWithMultipleElementsSize() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		Object element3 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(1, 1, 1, element2);
		testCube.add(2, 2, 2, element1);
		testCube.add(2, 2, 2, element2);
		testCube.add(2, 2, 2, element3);
		IterableQueue<Object> queue = testCube.getAll(2, 2, 2);
		assertThat("Returned queue was wrong size.", queue.size(), is(equalTo(3)));
	}
	
	@Test(timeout=500, expected = NoSuchElementException.class)
	public void testRemoveAll() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(1, 1, 1, element2);
		testCube.add(2, 2, 2, element1);
		testCube.add(2, 2, 2, element2);
		testCube.removeAll(2, 2, 2);
		IterableQueue<Object> queue = testCube.getAll(2, 2, 2);
		assertThat("Returned queue was wrong size.", queue.size(), is(equalTo(2)));
	}
	
	@Test(timeout=500)
	public void testRemoveExistElement() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		Object element3 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(1, 1, 1, element2);
		testCube.add(2, 2, 2, element1);
		testCube.add(2, 2, 2, element2);
		assertThat("Returned queue was wrong size.", testCube.remove(2, 2, 2,element2), is(equalTo(true)));
	}
	
	@Test(timeout=500)
	public void testRemoveUnExistElement() {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		Object element3 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(1, 1, 1, element2);
		testCube.add(2, 2, 2, element1);
		testCube.add(2, 2, 2, element2);
		assertThat("Returned queue was wrong size.", testCube.remove(2, 2, 2,element3), is(equalTo(false)));
	}
}