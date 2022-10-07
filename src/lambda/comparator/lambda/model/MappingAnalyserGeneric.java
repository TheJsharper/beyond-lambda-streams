package lambda.comparator.lambda.model;

import java.util.ArrayList;
import java.util.LinkedList;

public record MappingAnalyserGeneric<T>(Integer count, ArrayList<Student> lexiSubList, T previous, T current,
		boolean isProofWorkValid, LinkedList<Boolean> proofOfWorkValidations, LinkedList<T> labels) {

}
