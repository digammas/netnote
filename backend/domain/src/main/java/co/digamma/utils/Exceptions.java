package co.digamma.utils;

import jakarta.annotation.Nonnull;

public class Exceptions {

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;

        @Nonnull
        default ThrowingSupplier<Void> asThrowingSupplier() {
            return () -> {
                this.run();
                return null;
            };
        }
    }

    public static <T> T wrap(@Nonnull ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void wrap(@Nonnull ThrowingRunnable runnable) {
        Exceptions.wrap(runnable.asThrowingSupplier());
    }
}
