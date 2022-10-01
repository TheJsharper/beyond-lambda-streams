package test.comparator.lambada;

import java.util.Comparator;

public class StudentComparatorHelper {

	public static Comparator<Student> getImperativeStyleComparatorFirstName(@SuppressWarnings("exports") Direction dir) {
		return new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				if (dir.equals(Direction.ASC))
					return o1.getFirstName().compareTo(o2.getFirstName());
				else
					return o2.getFirstName().compareTo(o1.getFirstName());
			}
		};
	}
}

enum Direction {
	ASC, DESC
}