package lambda.comparator.lambda.model;

import java.util.TreeMap;


public record MappingResultGeneric<T>(TreeMap<String, MappingAnalyserGeneric<T>> analyser) {

}
