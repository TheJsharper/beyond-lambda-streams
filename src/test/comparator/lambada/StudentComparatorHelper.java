package test.comparator.lambada;

import java.util.Comparator;

public class StudentComparatorHelper {

	public static Comparator<Student> getImperativeStyleComparatorFirstName(
			@SuppressWarnings("exports") Direction dir) {
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

	public static Comparator<Student> getImperativeStyleComparatorLastName(@SuppressWarnings("exports") Direction dir) {
		return new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				if (dir.equals(Direction.ASC))
					return o1.getLastName().compareTo(o2.getLastName());
				else
					return o2.getLastName().compareTo(o1.getLastName());
			}
		};
	}
	
	public static IComparatorStudentProp getImperativeStyleComparatorLastNameNew( ) {

		return new IComparatorStudentProp() {

			@Override
			public Comparator<Student> compareWithDirection( Direction dir) {

				return new Comparator<Student>() {

					@Override
					public int compare(Student o1, Student o2) {
						if (dir.equals(Direction.ASC))
							return o1.getLastName().compareTo(o2.getLastName());
						else
							return o2.getLastName().compareTo(o1.getLastName());
					}
				};
			}
		};

	}
}

enum Direction {
	ASC, DESC
}