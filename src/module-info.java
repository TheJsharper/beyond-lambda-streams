module Test {
	requires guava;
	requires com.fasterxml.jackson.databind;

	exports lambda.comparator.utils;
	exports lambda.comparator.declarative;
	exports lambda.comparator.lambda.model;
}