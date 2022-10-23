package streams.operation.terminal;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;
import static lambda.comparator.utils.Utils.print;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingGroupingByExtend {
	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());
		getGroupingStudentAges(s.get()).forEach((k, v) -> {
			System.out.println(k);
			print(v, "---------------Values---------------------");
		});

	}

	private static Map<String, Map<String, List<Student>>> getGroupingStudentAges(Stream<Student> students) {
		return students.collect(Collectors.groupingBy(Utils.keyMapperString(),
				Collectors.groupingBy((s) -> s.getGender(),
						Collectors.collectingAndThen(Collectors.toList(), (list) -> list.stream()
								.sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList())))));

	}

	private static Map<String, Map<String, List<Student>>> getGroupingStudentAges2(Stream<Student> students) {
		
	var i=	students.collect(Collectors.flatMapping((s)-> s.getAddresses().stream(), Collectors.collectingAndThen(Collectors.toList(), (list)-> {
			return list;
		})));
	var ii =students.collect(Collectors.filtering((s)->s.getAge() >18, Collectors.groupingBy(Student::getGender)));
	var zz =students.collect(Collectors.toMap((s)->s.getFirstName(), (s)-> s.getAddresses()));
		
		
		return students.collect(Collectors.groupingBy(Utils.keyMapperString(),
				Collectors.groupingBy((s) -> s.getGender(),
						Collectors.collectingAndThen(Collectors.toList(), (list) -> list.stream()
								.sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList())))));
		
	}
}
