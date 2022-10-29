package features_since_java8.interfaces.defaults;

public class DefaultComplex {

	public static void main(String[] args) {

		D d = new DEFImpl();

		d.complexMethodD();

		E e = new DEFImpl();

		e.complexMethodE();

		e.complexMethodD();

		F f = new DEFImpl();

		f.complexMethodF();

		f.complexMethodD();

		f.complexMethodE();

		System.out.println();

		System.out.println("-------------------ABCImplementation instance----------------");

		System.out.println();

		DEFImpl abcImpl = new DEFImpl();

		abcImpl.complexMethodD();

		abcImpl.complexMethodE();

		abcImpl.complexMethodF();
	}

}

interface D {
	default void complexMethodD() {
		System.out.println("complexMethodD----> from interface" + D.class);
	}
}

interface E extends D {
	default void complexMethodE() {
		System.out.println("complexMethodE----> from interface" + E.class);
	}

	@Override
	default void complexMethodD() {
		System.out.println("@Override complexMethodD----> from interface" + E.class);

	}

}

interface F extends D, E {
	default void complexMethodF() {
		System.out.println("complexMethodF----> from interface" + F.class);
	}

	@Override
	default void complexMethodD() {
		System.out.println(" @Override complexMethodD----> from interface" + F.class);
	}
}

interface G {
	default void complexMethodD() {
		System.out.println("complexMethodD----> from interface" + G.class);
	}
}

class DEFImpl implements D, E, F {

}