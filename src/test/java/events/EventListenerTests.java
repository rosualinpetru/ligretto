package events;

import events.impl.ConcurrentEventBus;
import events.impl.EventListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class EventListenerTests {

    private ConcurrentEventBus<Integer> eventBus;

    @BeforeEach
    void setUp() {
        eventBus = new ConcurrentEventBus<>();
    }

    @AfterEach
    void tearDown() {
        eventBus.dispose();
    }

    @Test
    void listen_shouldGetNotified_whenEmitterSendsValue() {
        IntegerEventListener integerEventListener = new IntegerEventListener();
        eventBus.registerListener(integerEventListener);

        assertFalse(integerEventListener.received());

        integerEventListener.subscribe(x -> {
            assertTrue(integerEventListener.received());
            assertEquals(1234, x);
        });

        assertFalse(integerEventListener.received());

        eventBus.publish(1234);
    }

    @Test
    void listen_shouldGetNotified_whenEmitterSendsTheSameValueMultipleTimes() {
        CountableIntegerEventListener countableIntegerEventListener = new CountableIntegerEventListener();
        eventBus.registerListener(countableIntegerEventListener);

        AtomicInteger count = new AtomicInteger(0);

        assertEquals(0, countableIntegerEventListener.count);

        countableIntegerEventListener.subscribe(x -> {
            assertEquals(1234, x);
            count.getAndIncrement();
            assertEquals(count.get(), countableIntegerEventListener.count);
        });

        assertEquals(0, countableIntegerEventListener.count);

        eventBus.publish(1234);

        eventBus.publish(1234);

        eventBus.publish(1234);
    }

    @Test
    void listen_shouldGetNotified_whenEmitterSendsDifferentValuesMultipleTimes() {

        CountableIntegerEventListener countableIntegerEventListener = new CountableIntegerEventListener();
        eventBus.registerListener(countableIntegerEventListener);

        AtomicInteger count = new AtomicInteger(0);

        assertEquals(0, countableIntegerEventListener.count);

        countableIntegerEventListener.subscribe(x -> {
            if (x != 1234 && x != 3412 && x != 551) {
                fail();
            }
            count.getAndIncrement();
            assertEquals(count.get(), countableIntegerEventListener.count);
        });

        assertEquals(0, countableIntegerEventListener.count);

        eventBus.publish(1234);

        eventBus.publish(3412);

        eventBus.publish(551);
    }

    @Test
    void listen_shouldGetNotified_whenEmitterSendsDifferentValuesMultipleTimes_andTheListenersReceivesThemInTheSameOrder() {

        CountableIntegerEventListener countableIntegerEventListener = new CountableIntegerEventListener();
        eventBus.registerListener(countableIntegerEventListener);

        AtomicInteger lastNumber = new AtomicInteger(0);

        assertEquals(0, countableIntegerEventListener.count);

        countableIntegerEventListener.subscribe(x -> {
            switch (lastNumber.get()) {
                case 0 -> {
                    assertEquals(1234, x);
                    assertEquals(1, countableIntegerEventListener.count);
                }
                case 1234 -> {
                    assertEquals(3412, x);
                    assertEquals(2, countableIntegerEventListener.count);
                }
                case 3412 -> {
                    assertEquals(551, x);
                    assertEquals(3, countableIntegerEventListener.count);
                }
                default -> fail();
            }

            lastNumber.set(x);
        });

        assertEquals(0, countableIntegerEventListener.count);

        eventBus.publish(1234);

        eventBus.publish(3412);

        eventBus.publish(551);
    }

    @Test
    void listen_shouldGetNotifiedAllEventListeners_whenEmitterSendsValue() {
        IntegerEventListener integerEventListener1 = new IntegerEventListener();
        eventBus.registerListener(integerEventListener1);
        IntegerEventListener integerEventListener2 = new IntegerEventListener();
        eventBus.registerListener(integerEventListener2);
        IntegerEventListener integerEventListener3 = new IntegerEventListener();
        eventBus.registerListener(integerEventListener3);

        AtomicInteger count = new AtomicInteger(0);

        assertFalse(integerEventListener1.received());
        assertFalse(integerEventListener2.received());
        assertFalse(integerEventListener3.received());


        integerEventListener1.subscribe(x -> {
            assertTrue(integerEventListener1.received());
            assertEquals(1, x);

            count.getAndIncrement();
            if (count.get() == 3) {
                assertTrue(integerEventListener1.received());
                assertTrue(integerEventListener2.received());
                assertTrue(integerEventListener3.received());
            }
        });
        integerEventListener2.subscribe(x -> {
            assertTrue(integerEventListener2.received());
            assertEquals(1, x);
            count.getAndIncrement();
            if (count.get() == 3) {
                assertTrue(integerEventListener1.received());
                assertTrue(integerEventListener2.received());
                assertTrue(integerEventListener3.received());
            }
        });
        integerEventListener3.subscribe(x -> {
            assertTrue(integerEventListener3.received());
            assertEquals(1, x);
            count.getAndIncrement();
            if (count.get() == 3) {
                assertTrue(integerEventListener1.received());
                assertTrue(integerEventListener2.received());
                assertTrue(integerEventListener3.received());
            }
        });

        assertFalse(integerEventListener1.received());
        assertFalse(integerEventListener2.received());
        assertFalse(integerEventListener3.received());

        eventBus.publish(1);

    }

    @Test
    void listen_shouldGetNotified_whenMultipleEmitterSendsValue() {
        CountableIntegerEventListener countableIntegerEventListener = new CountableIntegerEventListener();
        eventBus.registerListener(countableIntegerEventListener);

        AtomicInteger lastElement = new AtomicInteger(0);

        assertEquals(0, countableIntegerEventListener.count);

        countableIntegerEventListener.subscribe(x -> {
            if (x != 61 && x != 9) {
                fail();
            }
            if (lastElement.get() == 0) {
                assertEquals(1, countableIntegerEventListener.count);
            } else {
                assertEquals(2, countableIntegerEventListener.count);
            }
            lastElement.set(x);
        });

        assertEquals(0, countableIntegerEventListener.count);

        eventBus.publish(61);

        eventBus.publish(9);
    }

//    @Test
//    void listen_shouldGetNotified_whenEmittersOfDifferentTypesSendsValue() {
//        IntegerEventListener integerEventListener = new IntegerEventListener();
//        eventBus.registerListener(integerEventListener);
//
//        assertFalse(integerEventListener.received);
//        eventBus.publish("hello");
//
//        integerEventListener.subscribe(x -> {
//            assertTrue(integerEventListener.received);
//            assertEquals(1, x);
//        });
//
//
//        assertFalse(integerEventListener.received);
//        eventBus.publish("more");
//        assertFalse(integerEventListener.received);
//
//        eventBus.publish(1);
//
//        assertTrue(integerEventListener.received);
//    }

    private static class IntegerEventListener extends EventListener<Integer> {

        public boolean received() {
            return receivedBool.get();
        }

        private final AtomicBoolean receivedBool = new AtomicBoolean(false);

        @Override
        public void subscribe(Consumer<Integer> callback) {
            super.subscribe(((Consumer<Integer>) integer -> receivedBool.set(true)).andThen(callback));
        }
    }

    private static class CountableIntegerEventListener extends EventListener<Integer> {

        public int count = 0;

        @Override
        public void subscribe(Consumer<Integer> consumer) {
            super.subscribe(((Consumer<Integer>) integer -> count++).andThen(consumer));
        }
    }
}
