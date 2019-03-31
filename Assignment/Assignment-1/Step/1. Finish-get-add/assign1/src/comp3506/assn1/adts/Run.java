package comp3506.assn1.adts;

public class Run {

	public static void main(String[] args) {
		Cube<Object> testCube = new BoundedCube<>(5, 5, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		testCube.add(1, 1, 1, element1);
		testCube.add(2, 2, 2, element2);
		System.out.println(testCube.get(2,2,2));
	}
}