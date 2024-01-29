package sample;

import io.vavr.Tuple;
import io.vavr.control.Try;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalCodeExample {

    private final FooService fooService;
    private final FooDao fooDao;

    private final ExecutorService executorService;

    public FunctionalCodeExample(FooService fooService, FooDao fooDao, ExecutorService executorService) {
        this.fooService = fooService;
        this.fooDao = fooDao;
        this.executorService = executorService;
    }


    public void processAll() {
        fooService.fooStream()
//                .flatMap(t -> streamTryResult(t, throwable ->
//                    logError("Streaming foos failed", throwable)))
                .flatMap(t -> logAndIgnoreFailure(t, "Streaming foos failed"))
//                .logFailure()
                .map(foo -> Try.of(() -> wrap(foo)))
                .flatMap(t -> logAndIgnoreFailure(t, "Failed wrapping foo"))
                .map(wrapped -> Tuple.of(wrapped, process(wrapped)))
                .filter(tuple -> tuple._2().isFailure())
                .forEach(tuple -> logError("Couldn't process foo that has id %s".formatted(tuple._1.foo().uuid()), tuple._2.getCause()));
    }

    private  <T> Stream<T> streamTryResult(Try<T> t, Consumer<Throwable> failure) {
        return t.map(Stream::of).getOrElseGet(throwable -> {
            failure.accept(throwable);
            return Stream.empty();
        });
    }
    private  <T> Stream<T> logAndIgnoreFailure(Try<T> t, String errorLogMessage) {
//        return t.map(Stream::of).getOrElseGet(throwable -> {
//           logError("Streaming foos failed", throwable);
//            return Stream.empty();
//        });
        if (t.isFailure()) {
            logError(errorLogMessage, t.getCause());
            return Stream.empty();
        }
        return Stream.of(t.get());
    }

    private Try<UUID> process(final FooWrapper wrapped) {
        final var foo = wrapped.foo();
        return Try.of(() -> {
            fooDao.insert(foo);
            final Set<Result> results = executorService.invokeAll(toCallable(wrapped)).stream()
                    .map(tFuture -> Try.of(tFuture::get).onFailure(throwable -> logError("Couldn't get from future", throwable)).toJavaOptional())
                    .flatMap(Optional::stream)
                    .collect(Collectors.toSet());

            results.forEach(Result::onComplete);

            if(results.stream().allMatch(Result::success)){
                //other code doing stuff like sending an event
            }
            return foo.uuid();
        });
    }

    private Set<Callable<Result>> toCallable(final FooWrapper wrapped) {
        return null;
    }

    private FooWrapper wrap(Foo foo) {
        return new FooWrapper(foo);
    }

    private void logError(final String someErrorMessage, final Throwable throwable) {

    }


    static class FooService {
        public Stream<Try<Foo>> fooStream() {
            return fooNames().get().stream().map(name -> readFoos(name).of(is -> {
                        //do some more stuff here
                        //
                        //
                        return  inputStreamToStorage(is).recoverWith(ex -> Try.failure(new RuntimeException("Throw some runtime exception here")));
                    })
                    .flatMap(Function.identity()));
        }

        private Try<Foo> inputStreamToStorage(InputStream inputStream) {
            //do some stuff and return the result
            return Try.success(new Foo());
        }

        private Try.WithResources1<InputStream> readFoos(final String name) {
            return null;
        }

        private Try<Set<String>> fooNames() {
            return null;
        }
    }


    static class Foo {
        public UUID uuid() {
            return null;
        }
    }

    //assume this is doing more stuff when it wraps foo - maybe can throw validation exception
    record FooWrapper(Foo foo) {
    }

    static class FooDao {
        public void insert(final Foo foo) {

        }
    }

    static class Result {
        private final boolean success;
        private final Runnable runOnSuccess;
        private final Runnable runOnFailure;

        Result(boolean success, Runnable runOnSuccess, Runnable runOnFailure) {
            this.success = success;
            this.runOnSuccess = runOnSuccess;
            this.runOnFailure = runOnFailure;
        }

        public void onComplete() {
            if (success) {
                runOnSuccess.run();
            } else {
                runOnFailure.run();
            }
        }

        public boolean success() {
            return success;
        }
    }
}
