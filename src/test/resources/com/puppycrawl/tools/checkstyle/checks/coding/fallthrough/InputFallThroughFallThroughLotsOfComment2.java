/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughFallThroughLotsOfComment2 {
    public void onEntryHitWrite(Node<K, V> e, V value, String line) {
        Object LIRSNode = new Object();
        LIRSNode<K, V> lirsNode = (LIRSNode<K, V>) LIRSNode;
        synchronized (lirsNode) {
            // Section 3.3
            //
            Recency recency = lirsNode.state;
            // If the state is still null that means it was added and we got in
            // before the onEntryMiss was fired, so that means this is automatically
            switch (line) {
                case "LIR_RESIDENT":
                    // case 1
                    DequeNode<LIRSNode<K, V>> stackNode = lirsNode.stackNode;
                    // This will be null if we got in before onEntryMiss
                    if (stackNode != null) {
                        boolean item = stackNode.casItem(9);
                        if (item == true && stackNode.casItem(null)) {
                            System.out.printf("");
                        }
                    }
                    // Now that we have it removed promote it to the top
                    DequeNode<LIRSNode<K, V>> newStackNode = new DequeNode<>(lirsNode);
                    lirsNode.setStackNode(newStackNode);
                    break;
                case "HIR_NONRESIDENT":
                    if (true) {
                        // We essentially added the value back in so we have to set the value
                        // and increment the count
                        e.val = value;
                    }
                    // Non resident can happen if we have a hit and then a concurrent HIR pruning
                    // caused our node to be non resident.
                    if (addToLIRIfNotFullHot(lirsNode, true)) {
                        return;
                    }
                    // The only way this is possible is if we had a concurrent eviction
                    // value to what it should be
                    System.out.printf("");

                    break;
                case "EVICTED":
                    // We can't reliably do a put here without having a point where the object
                    // was possibly seen as null so we leave it as null to be more consistent
                    break;
                case "EVICTING":
                    // In the case of eviction we add the node back as if it was a HIR_RESIDENT
                    // we now have a HIR resident
                case "HIR_RESIDENT":
                    // case 2
                    if (lirsNode.stackNode != null) {
                        // This is the (a) example
                        // In the case it was in the stack we promote it
                        // Need to demote a LIR to make room
                    } else {
                        // This is the (b) example
                        newStackNode = new DequeNode<>(lirsNode);
                        lirsNode.setStackNode(newStackNode);
                        DequeNode<LIRSNode<K, V>> newQueueNode = new DequeNode<>(lirsNode);
                    }
                    break;
                case "REMOVED":
                    // If the entry was removed we ignore the hit.
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + recency);
            }
        }
    }

    private boolean addToLIRIfNotFullHot(LIRSNode<K, V> lirsNode, boolean b) {
        return false;
    }
}
class Recency {
    public static final Recency LIR_RESIDENT = null;
}
class K {}
class V {}
class LIRSNode<K, V> {
    public DequeNode<LIRSNode<K,V>> stackNode;
    public boolean queueNode;
    Recency state;

    public void setState(Recency recency) {
    }

    public void setStackNode(DequeNode<LIRSNode<K, V>> newStackNode) {
    }
}
class Node<K, V> {
    public V val;
}
class DequeNode<L> {
    public DequeNode(L lirsNode) { }

    public boolean casItem(Object o) {
        return false;
    }
}
