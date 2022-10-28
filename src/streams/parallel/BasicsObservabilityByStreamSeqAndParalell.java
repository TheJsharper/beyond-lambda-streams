package streams.parallel;

import static lambda.comparator.utils.Utils.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;

public class BasicsObservabilityByStreamSeqAndParalell {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		Supplier<List<Address>> sequential = () -> getSequentialUsingFlatMapping(s.get());

		Supplier<List<Address>> parallel = () -> getParalellUsingFlatMapping(s.get());

		long durationSequential = getObservabilityRunTime(sequential);

		long durationParallel = getObservabilityRunTime(parallel);

		long countProcessors = Runtime.getRuntime().availableProcessors();

		System.out.printf("Duration runtime Sequential :%d[ms] with Processors: %d \n", durationSequential,
				countProcessors);

		System.out.printf("Duration runtime Paralell :%d[ms] with Processors: %d \n", durationParallel,
				countProcessors);
		if (durationParallel > durationSequential) {

			System.out.printf("Sequential is faster or equal %.3f%% than paralell : ",
					getDistanceDiff(durationParallel, durationSequential));
		} else {
			System.out.printf("Paralell is faster %.3f%% than sequential ",
					getDistanceDiff(durationParallel, durationSequential));

		}
	}

	private static List<Address> getSequentialUsingFlatMapping(Stream<Student> students) {
		return students.flatMap((Student s) -> s.getAddresses().stream()).collect(Collectors.toList());
	}

	private static List<Address> getParalellUsingFlatMapping(Stream<Student> students) {
		return students.parallel().flatMap((Student s) -> s.getAddresses().stream()).collect(Collectors.toList());
	}

	private static long getObservabilityRunTime(Supplier<List<Address>> s) {
		long start = System.currentTimeMillis();

		s.get().forEach((Address a) -> {
		});

		return System.currentTimeMillis() - start;
	}

}
