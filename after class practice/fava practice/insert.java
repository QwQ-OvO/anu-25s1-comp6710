import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;

ConsList<Integer> insert(int element, int index, ConsList<Integer> list) {
    return switch(list) {
        case Nil<Integer>() -> MakeList(element);
        case Cons<Integer>(var first, var rest) -> {
            if (Equals(index, 0)) {
                yield new Cons<Integer>(element, list);
            } else {
                yield new Cons<Integer>(first, insert(element, index - 1, rest));
            }
        }
    };
}

void main() {
    ConsList<Integer> list = MakeList(1, 2, 4);
    int element = 3;
    int index = 2;
    println(insert(element, index, list));
}