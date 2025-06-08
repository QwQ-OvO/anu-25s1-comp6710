import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;

Maybe<String> last(ConsList<String> list) {
    return switch(list) {
        case Nil<String>() -> new Nothing<String>();
        case Cons<String>(var first, var rest) -> {
            if (rest == new Nil<String>()) {
                yield new Something<String>(first);
            } else {
                yield last(rest);
            }
        }
    };
}



void main() {}

