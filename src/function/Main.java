package function;

import lambda.comparator.utils.Utils;

public class Main {

	public static void main(String[] args) {

		Utils.createStudentListWithAddresses().forEach((s) -> {
			System.out.println("===>" + s);
			s.getAddresses().stream().forEach(System.out::println);
		});
	}

}
