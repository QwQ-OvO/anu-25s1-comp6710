import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;

String get(ConsList<String> list, int index) {
    return switch(list) {
        case Nil<String>() -> "";
        case Cons<String>(var first, var rest) -> {
            if (index == 0) {
                yield first;
            } else {
                yield get(rest, index-1);
            }
        }
    };
}



void main() {
    ConsList<String> list = MakeList("A", "B", "C");
    int index = 1;
    println(get(list, index));
}