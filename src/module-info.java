module Test {
	requires guava;
	requires com.fasterxml.jackson.databind;
	requires java.desktop;
	requires java.base;
	requires java.sql;

	exports lambda.comparator.utils;
	exports lambda.comparator.declarative;
	exports lambda.comparator.lambda.model;
}