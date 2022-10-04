package test.comparator.lambada.model;

import java.util.ArrayList;
import java.util.LinkedList;

public record MappingAnalyser(Integer count, ArrayList<Student> lexiSubList, String previous, String current,
		boolean isProofWorkValid, LinkedList<Boolean> proofOfWorkValidations, LinkedList<String> labels) {

}
