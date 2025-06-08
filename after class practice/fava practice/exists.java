import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;

Boolean exist(ConsList<String> list, String string) {
    return switch(list) {
        case Nil<String>() -> false;
        case Cons<String>(var first, var rest) -> {
            if (string == first) {
                yield true;
            } else {
                yield exist(rest, string);
            }
        }
    };
}

void main() {
    ConsList<String> list = MakeList("Hello", "World");
    String string1 = "Hello";
    String string2 = "Fabian";
    println(exist(list, string1));
    println(exist(list, string2));
}