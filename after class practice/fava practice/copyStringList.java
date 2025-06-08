import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;

ConsList<String> copyStringList (ConsList<String> list) {
    return switch(list) {
        case Nil<String>() -> new Nil<String>();
        case Cons<String>(var first, var rest) ->
            new Cons<String>(first, copyStringList(rest));
    };
}

void main() {
    ConsList<String> list = MakeList("Hello", "World");
    println(copyStringList(list));
}