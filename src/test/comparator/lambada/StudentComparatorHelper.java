package test.comparator.lambada;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import test.comparator.lambada.model.DIRECTION;
import test.comparator.lambada.model.MappingAnalyser;
import test.comparator.lambada.model.MappingResult;
import test.comparator.lambada.model.Student;

public class StudentComparatorHelper {

	public static Comparator<Student> getImperativeStyleComparatorFirstName(DIRECTION dir) {
		return new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				if (dir.equals(DIRECTION.ASC))
					return o1.getFirstName().compareTo(o2.getFirstName());
				else
					return o2.getFirstName().compareTo(o1.getFirstName());
			}
		};
	}

	public static Comparator<Student> getImperativeStyleComparatorLastName(DIRECTION dir) {
		return new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				if (dir.equals(DIRECTION.ASC))
					return o1.getLastName().compareTo(o2.getLastName());
				else
					return o2.getLastName().compareTo(o1.getLastName());
			}
		};
	}

	public static MappingResult getAnalyserSortingByFirstName(List<Student> students, DIRECTION dir) {

		var result = students.stream().reduce(
				new MappingResult(new TreeMap<>(StudentComparatorHelper.getTreeMapComparator(dir))),
				(MappingResult mappingResult, Student student) -> {
					String firstName = student.getFirstName();
					if (firstName != null && !firstName.equals("") && firstName.length() >= 1) {

						firstName = firstName.substring(0, 1).toLowerCase();

						if (!mappingResult.analyser().keySet().contains(firstName)) {
							var mapping = mappingResult.analyser().get(firstName);
							if (mapping == null) {
								var list = new ArrayList<Student>();
								list.add(student);
								mapping = new MappingAnalyser(1, list, "", student.getFirstName(), false,
										new LinkedList<Boolean>(), new LinkedList<String>());
								mappingResult.analyser().put(firstName, mapping);
							}
						} else {
							var mapping = mappingResult.analyser().get(firstName);
							mapping.lexiSubList().add(student);
							var count = mapping.count();
							count++;
							boolean isProofOfWorkValid = dir.equals(DIRECTION.ASC)
									? mapping.current().compareTo(student.getFirstName()) <= 0
									: mapping.current().compareTo(student.getFirstName()) >= 0;
							String label = "===> Proof of Work: " + isProofOfWorkValid + " Current: "
									+ mapping.current() + " Previuos: " + student.getFirstName();
							mapping.proofOfWorkValidations().add(isProofOfWorkValid);
							mapping.labels().add(label);
							var newMapping = new MappingAnalyser(count, mapping.lexiSubList(), mapping.current(),
									student.getFirstName(), isProofOfWorkValid, mapping.proofOfWorkValidations(),
									mapping.labels());
							mappingResult.analyser().put(firstName, newMapping);
						}
					}
					return mappingResult;
				}, (MappingResult a, MappingResult b) -> b);

		return result;
	}

	public static Comparator<String> getTreeMapComparator(DIRECTION dir) {

		return new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {

				return dir.equals(DIRECTION.ASC) ? o1.compareTo(o2) : o2.compareTo(o1);
			}

		};
	}

}
