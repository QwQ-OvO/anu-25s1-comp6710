/**
 * Represents an event to be handled by an event handler function.
 * Events have a kind. Kinds are case-sensitive. Thus, e.g.,
 * "KEY_PRESSED" and "key_pressed" are considered to be different
 * event kinds. Events also hold data associated to the event.
 *
 * Examples: Event("KEY_PRESSED", "F10")
 *          Event("MOUSE_LEFT_CLICK", "X:10 Y:20")
 *
 * @param kind The kind of the event
 * @param data The data associated to the event
 * @implSpec Invariants: once an event is instantiated,
 *           its kind and data cannot be modified.
 */
public record Event(String kind, String data) {} 