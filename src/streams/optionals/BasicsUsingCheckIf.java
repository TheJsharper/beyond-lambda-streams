package streams.optionals;

import java.util.Optional;
import java.util.function.Consumer;

public class BasicsUsingCheckIf {

	public static void main(String[] args) {

		usingOfNullableWithIsPresent();

		usingOfNullableWithIfPresentOrElse();
	}

	private static void usingOfNullableWithIsPresent() {

		Optional<String> ofNullable = getOfNullable();

		Optional<String> maybeOfNullable = getMaybeOfNullable();

		if (ofNullable.isPresent()) {
			System.out.println("====> ofNullable of text: " + ofNullable.get());
		} else {
			System.err.println("----> no value present: ");
		}

		if (maybeOfNullable.isPresent()) {
			System.out.println("====> maybeOfNullable text: " + maybeOfNullable.get());
		} else {
			System.err.println("----> no value present: ");
		}
	}

	private static void usingOfNullableWithIfPresentOrElse() {

		Optional<String> ofNullable = getOfNullable();

		Optional<String> maybeOfNullable = getMaybeOfNullable();

		Consumer<String> consumer = (String text) -> System.out.println("====> ofNullable of text: " + text);

		ofNullable.ifPresent(consumer);

		if (!ofNullable.isPresent()) {
			System.err.println("----> no value present: ");
		}

		maybeOfNullable.ifPresentOrElse(consumer, () -> System.err.println("NO PRESENT"));
	}

	private static Optional<String> getOfNullable() {

		return Optional.ofNullable("Hello World");
	}

	private static Optional<String> getMaybeOfNullable() {

		return Optional.ofNullable(null);
	}

}
