package features_since_java8.interfaces.defaults;

public class DefaultSimple {

	public static void main(String[] args) {
		
		A a = new ABCImpl();
		
		a.simpleMethodA();

		B b = new ABCImpl();

		b.simpleMethodB();

		C c = new ABCImpl();

		c.simpleMethodC();

		System.out.println();

		System.out.println("-------------------ABCImplementation instance----------------");

		System.out.println();
		
		ABCImpl abcImpl = new ABCImpl();

		abcImpl.simpleMethodA();

		abcImpl.simpleMethodB();

		abcImpl.simpleMethodC();

	}

}

interface A {
	default void simpleMethodA() {
		System.out.println("simpleMethodA----> from interface" + A.class);
	}
}

interface B {
	default void simpleMethodB() {
		System.out.println("simpleMethodB----> from interface" + B.class);
	}
}

interface C {
	default void simpleMethodC() {
		System.out.println("simpleMethodC----> from interface" + C.class);
	}
}

class ABCImpl implements A, B, C {

}