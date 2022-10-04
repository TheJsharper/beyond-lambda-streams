package lambda.comparator.declarative;

import lambda.comparator.utils.Utils;

public class DeclarativeComparator {

	public static void main(String[] args) {
		Utils.createStudentList().stream().forEach(System.out::println);;

	}

}
