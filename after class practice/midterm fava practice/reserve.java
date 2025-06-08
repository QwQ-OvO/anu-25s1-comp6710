import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;

ConsList<Integer> reserve(ConsList<Integer> list) {
    return switch(list) {
        case Nil<Integer>() -> new Nil<Integer>();
        case Cons<Integer>(var first, var rest) -> 
             Append(reserve(rest), MakeList(first));
    };
}

void main() {
    ConsList<Integer> list = MakeList(1, 2, 3, 4);
    println(reserve(list));
}