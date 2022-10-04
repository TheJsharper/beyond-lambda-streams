module Test {
	requires guava;
	requires com.fasterxml.jackson.databind;

	exports test.comparator.lambada;
	exports test.comparator.imperative;
	exports test.comparator.lambada.model;
}