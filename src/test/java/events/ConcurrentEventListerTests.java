package events;

import events.impl.ConcurrentEventBus;
import events.impl.EventListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConcurrentEventListerTests {
    private EventBus<Integer> eventBus;

    @BeforeEach
    void setUp() {
        eventBus = new ConcurrentEventBus<>();
    }

    @AfterEach
    void tearDown() {
        eventBus.dispose();
    }

    @Test
    @Timeout(value = 1)
    public void test_shouldNotifyListenerFromAnotherThread() throws InterruptedException {
        EventListenerHolder eventListenerHolder = new EventListenerHolder();

        Thread thread = new Thread(() -> {
            AtomicBoolean isRunning = new AtomicBoolean(true);
            eventListenerHolder.eventListener.subscribe(integer -> isRunning.set(false));

            eventBus.registerListener(eventListenerHolder.eventListener);

            while (isRunning.get()) {
            }
        });
        thread.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                eventBus.publish(12);
            }
        }, 100);

        thread.join();
    }

    @Test
    @Timeout(value = 2)
    public void test_shouldRegisterToBusMultipleListenersFromDifferentThreads() throws InterruptedException {

        int listenersCount = 20;

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < listenersCount; i++) {
            Thread thread = new Thread(() -> {
                EventListenerHolder eventListenerHolder = new EventListenerHolder();
                eventBus.registerListener(eventListenerHolder.eventListener);

                AtomicBoolean isRunning = new AtomicBoolean(true);
                eventListenerHolder.eventListener.subscribe(integer -> {
                    eventListenerHolder.triggered = true;
                    isRunning.set(false);
                });


                while (isRunning.get()) {
                }
            });
            threads.add(thread);
        }

        for (Thread value : threads) {
            value.start();
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                eventBus.publish(12);
            }
        }, 100);

        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    @Timeout(value = 2)
    public void test_shouldReceiveMultipleEvents_whenEmittersEmitFromDifferentThreads() throws InterruptedException {

        EventListenerHolder eventListenerHolder = new EventListenerHolder();
        eventBus.registerListener(eventListenerHolder.eventListener);

        eventListenerHolder.eventListener.subscribe(integer -> eventListenerHolder.triggered = true);

        int emittersCounts = 200;

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < emittersCounts; i++) {
            Thread thread = new Thread(() -> {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        eventBus.publish(1);
                    }
                }, 100);
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    @Timeout(value = 5)
    public void test_shouldReceiveMultipleEvents_whenEmittersEmitFromDifferentThreads_andListenersOnDifferentThreads() throws InterruptedException {
        final int emittersCounts = 200;
        final int listenersCount = 400;

        List<Thread> threads = new ArrayList<>();
        List<Thread> allThreads = new ArrayList<>();

        AtomicBoolean exceptionOccurred = new AtomicBoolean(false);

        for (int i = 0; i < listenersCount; i++) {
            Thread thread = new Thread(() -> {
                try {
                    EventListenerHolder eventListenerHolder = new EventListenerHolder();
                    eventBus.registerListener(eventListenerHolder.eventListener);
                    AtomicInteger eventCount = new AtomicInteger();
                    CountDownLatch countDownLatch = new CountDownLatch(emittersCounts);

                    eventListenerHolder.eventListener.subscribe(integer -> {
                        eventCount.getAndIncrement();

                        countDownLatch.countDown();
                    });

                    countDownLatch.await();
                } catch (Exception e) {
                    exceptionOccurred.set(true);
                }
            });
            threads.add(thread);
            allThreads.add(thread);
        }
        for (Thread value : threads) {
            value.start();
        }

        threads.clear();


        AtomicInteger publishedMessagesCount = new AtomicInteger();

        for (int i = 0; i < emittersCounts; i++) {
            Thread thread = new Thread(() -> {
                eventBus.publish(1);
                publishedMessagesCount.getAndIncrement();
            });
            threads.add(thread);
            allThreads.add(thread);
        }

        for (Thread value : threads) {
            value.start();
        }

        for (Thread thread : allThreads) {
            thread.join();
        }

        if (exceptionOccurred.get()) {
            fail();
        }

        assertEquals(emittersCounts, publishedMessagesCount.get());
    }

    private static class EventListenerHolder {
        public EventListener<Integer> eventListener = new EventListener<>();

        public boolean triggered = false;
    }
}
