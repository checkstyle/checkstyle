/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class InputVariableDeclarationUsageDistanceInitializationFalsePositive1 {

    static class RequestHandler<T> {
        final State<T> state = new State<>();
        final NotificationLite<T> notifier = NotificationLite.instance();
        volatile long wip;
        static final AtomicLongFieldUpdater<RequestHandler> WIP =
            AtomicLongFieldUpdater.newUpdater(RequestHandler.class, "wip");

        void requestMoreAfterEmission(int emitted) {}

        public void drainQueue(OriginSubscriber<T> originSubscriber) {
            if (WIP.getAndIncrement(this) == 0) {
                State<T> localState = state;
                // violation below, false positive, 'Distance .* is 5.'
                Map<Subscriber<? super T>, AtomicLong> localMap = localState.ss;
                RxRingBuffer localBuffer = originSubscriber.buffer;
                NotificationLite<T> nl = notifier;  // violation 'Distance .* is 5.'

                int emitted = 0;
                do {
                    WIP.set(this, 1);
                    while (true) {
                        if (localState.hasNoSubscriber()) {
                            if (localBuffer.poll() == null) {
                                break;
                            } else {
                                continue;
                            }
                        }

                        boolean shouldEmit = localState.canEmitWithDecrement();
                        if (!shouldEmit) {
                            break;
                        }
                        Object o = localBuffer.poll();
                        if (o == null) {
                            localState.incrementOutstandingAfterFailedEmit();
                            break;
                        }

                        for (Subscriber<? super T> s : localState.getSubscribers()) {
                            AtomicLong req = localMap.get(s);
                            if (req != null) {
                                nl.accept(s, o);
                                req.decrementAndGet();
                            }
                        }
                        emitted++;
                    }
                } while (WIP.decrementAndGet(this) > 0);
                requestMoreAfterEmission(emitted);
            }
        }
    }

    static class Subscriber<T> { }

    static class NotificationLite<T> {
        static <T> NotificationLite<T> instance() {
            return new NotificationLite<>();
        }
        void accept(Subscriber<? super T> s, Object o) {}
    }

    static class RxRingBuffer {
        static final int SIZE = 256;
        Object poll() {
            return null;
        }
        static RxRingBuffer getSpmcInstance() {
            return new RxRingBuffer();
        }
    }

    static class State<T> {
        final Map<Subscriber<? super T>, AtomicLong> ss = new LinkedHashMap<>();
        boolean hasNoSubscriber() {
            return ss.isEmpty();
        }
        boolean canEmitWithDecrement() {
            return false;
        }
        void incrementOutstandingAfterFailedEmit() {}
        Subscriber<? super T>[] getSubscribers() {
            @SuppressWarnings("unchecked")
            Subscriber<? super T>[] arr = new Subscriber[0];
            return arr;
        }
    }

    static class OriginSubscriber<T> {
        final RxRingBuffer buffer = RxRingBuffer.getSpmcInstance();
    }

    public void rxJavaCase() {
        OriginSubscriber<Object> originSubscriber = new OriginSubscriber<>();
        RequestHandler<Object> handler = new RequestHandler<>();
        handler.drainQueue(originSubscriber);
    }
}
