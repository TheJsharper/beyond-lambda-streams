package streams.optionals;

import java.util.NoSuchElementException;
import java.util.Optional;

public class BasicsUsingOptionalOfEmptyNullable {

	public static void main(String[] args) {

		usingOfNullable();

		usingEmpty();

		usingOf();
	}

	private static void usingOfNullable() {
		Optional<String> mayBeOfNullable = getMayBeOfNullable();

		Optional<String> ofNullable = getOfNullable();

		if (ofNullable.isPresent()) {
			System.out.println("====> text: " + ofNullable.get());
		}
		try {

			System.out.println("--->" + mayBeOfNullable.get());
		} catch (NoSuchElementException e) {
			System.err.println("----> no value present: " + e.getMessage());
		}
	}

	private static void usingEmpty() {
		Optional<String> maybeEmpty = getMaybeEmpty();

		Optional<String> empty = getEmpty();

		try {
			System.out.println("====> text: " + empty.get());
		} catch (NoSuchElementException e) {
			System.err.println("----> no value present: " + e.getMessage());
		}
		try {

			System.out.println("--->" + maybeEmpty.get());
		} catch (NoSuchElementException e) {
			System.err.println("----> no value present: " + e.getMessage());
		}
	}

	private static void usingOf() {
		Optional<String> maybeOf = getMaybeOf();

		Optional<String> of = getOf();

		try {
			System.out.println("====> text: " + maybeOf.get());
		} catch (NoSuchElementException e) {
			System.err.println("----> no value present: " + e.getMessage());
		}
		try {

			System.out.println("--->" + of.get());
		} catch (NoSuchElementException e) {
			System.err.println("----> no value present: " + e.getMessage());
		}
	}

	private static Optional<String> getMayBeOfNullable() {

		String text = "Hello World";

		if (text.contains("Hello_")) {
			return Optional.ofNullable(text);
		} else {
			return Optional.ofNullable(null);
		}

	}

	private static Optional<String> getOfNullable() {

		String text = "Hello World";
		if (text.contains("Hello")) {
			return Optional.ofNullable(text);
		} else {
			return Optional.ofNullable(null);
		}

	}

	private static Optional<String> getMaybeEmpty() {
		String text = "Hello World";
		if (text.contains("Hello_")) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(text);
		}

	}

	private static Optional<String> getEmpty() {
		String text = "Hello World";
		if (text.contains("Hello")) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(text);
		}

	}

	private static Optional<String> getMaybeOf() {
		String text = "Hello World";
		if (text.contains("Hello_")) {
			return Optional.of(text);
		} else {
			return Optional.ofNullable(null);
		}

	}

	private static Optional<String> getOf() {
		String text = "Hello World";
		if (text.contains("Hello")) {
			return Optional.of(text);
		} else {
			return Optional.ofNullable(null);
		}

	}

}
