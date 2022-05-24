package br.com.marquescleiton.springaop.controller;

public interface MyRunnable extends Runnable{
    
	@Override
    default void run() {
        try {
            tryRun();
        } catch (final Throwable t) {
            throwUnchecked(t);
        }
    }

    @SuppressWarnings("unchecked")
	private static <E extends Exception> void throwUnchecked(Throwable t) throws E {
        throw (E) t;
    }

    void tryRun() throws Throwable;
}
