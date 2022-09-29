package test;

public class ImperativeVsDeclarativeRunnable {

	public static void main(String[] args) {

		new Thread(getImperativeSimpleRunnable()).start();

		new Thread(getDeclarativeSimpleRunnable()).start();

		new Thread(getImperativeSimpleILikeRunnable()).start();

		getImperativeSimpleThreadGroup().start();

		getDeclarativeSimpleThreadGroup().start();
	}

	private static Runnable getImperativeSimpleRunnable() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				System.out.println("Inside Imperative Simple Runnable "+ Thread.currentThread().getName());

			}
		};
		return runnable;
	}

	private static ILikeRunnable getImperativeSimpleILikeRunnable() {
		return () -> System.out.println("Inside Declarative Simple LikeRunnable "+ Thread.currentThread().getName());
	}

	private static Runnable getDeclarativeSimpleRunnable() {
		Runnable runnable = () -> System.out.println("Inside Declarative Simple Runnable "+ Thread.currentThread().getName());
		return runnable;
	}

	private static Thread getImperativeSimpleThreadGroup() {
		var group = new ThreadGroup("Imperative Group Simple Group");

		return new Thread(group, new Runnable() {

			@Override
			public void run() {
				var ownName = Thread.currentThread().getName();
				var groupName = Thread.currentThread().getThreadGroup().getName();
				System.out.println("Inside Imperative Simpla ThreadGroup" + ownName + " GroupName " + groupName);

			}
		});

	}

	private static Thread getDeclarativeSimpleThreadGroup() {
		return new Thread(new ThreadGroup("Declarative Group Simple Group"), () -> {
			var ownName = Thread.currentThread().getName();
			var groupName = Thread.currentThread().getThreadGroup().getName();
			System.out.println("Inside Declaratibe Simpla ThreadGroup" + ownName + " GroupName " + groupName);
		});
	}

}

@FunctionalInterface
interface ILikeRunnable extends Runnable {
	void run();
}
