package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImperativeVsDeclarativeCallable {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(8);
		try {
			Future<Integer> fut = executorService.submit(getImperativeSimpleCallable());
			System.out.println("RANDON GENERATOR ASYNC  :" + fut.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		try {
			Future<Integer> fut = executorService.submit(getDeclarativeSimpleCallable());
			System.out.println("RANDON GENERATOR ASYNC  :" + fut.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		try {
			Future<Integer> fut = executorService.submit(getImperativeSimpleILikeCallable());
			System.out.println("RANDON GENERATOR CALLABLE LIKE ASYNC  :" + fut.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		try {
			Collection<Callable<Integer>> tasks = getImperativeSimpleCallableGroup();
			Integer values = executorService.invokeAny(tasks);
			System.out.println("RANDON GENERATOR CALLABLE GROUPPED ALL INTO VALUES :" + values);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		try {
			Collection<Future<Integer>> values = executorService.invokeAll(getImperativeSimpleCallableGroup());
			values.forEach((Future<Integer> value) ->

			{
				try {
					System.out.println("Values====>" + value.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			List<Future<Integer>> tasks = executorService.invokeAll(getDeclarativeSimpleCallableGroup(), 1000,
					TimeUnit.MICROSECONDS);
			tasks.forEach((value) -> {
				try {
					System.out.println(" InvokeAll Tasks Values====>" + value.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	private static Callable<Integer> getImperativeSimpleCallable() {
		Callable<Integer> runnable = new Callable<>() {

			@Override
			public Integer call() {
				System.out.println("Inside Imperative Simple CALLABLE INTEGER " + Thread.currentThread().getName());
				int min = 200;
				int max = 900;
				return (int) (Math.random() * (max - min + 1) + min);
			}
		};
		return runnable;
	}

	private static ILikeCallable<Integer> getImperativeSimpleILikeCallable() {
		return () -> {
			System.out.println("Inside Declarative Simple LikeRunnable " + Thread.currentThread().getName());
			int min = 200;
			int max = 900;
			return (int) (Math.random() * (max - min + 1) + min);
		};
	}

	private static Callable<Integer> getDeclarativeSimpleCallable() {
		Callable<Integer> callable = () -> {
			System.out.println("Inside Declarative Simple CALLABLE INTEGER  " + Thread.currentThread().getName());
			int min = 200;
			int max = 900;
			return (int) (Math.random() * (max - min + 1) + min);
		};
		return callable;
	}

	private static Collection<Callable<Integer>> getImperativeSimpleCallableGroup() {

		List<Callable<Integer>> tasks = new ArrayList<>();

		for (int i = 0; i < 10; i++)
			tasks.add(getImperativeSimpleCallable());

		return tasks;

	}

	private static Collection<Callable<Integer>> getDeclarativeSimpleCallableGroup() {

		return IntStream.rangeClosed(1, 10).mapToObj((a) -> getDeclarativeSimpleCallable())
				.collect(Collectors.toList());

	}

}

@FunctionalInterface
interface ILikeCallable<V> extends Callable<V> {
	V call();
}
