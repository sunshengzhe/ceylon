abstract class FooBar() of foo | bar {}
object foo extends FooBar() {
    shared Float y = 2.0;
}
object bar extends FooBar() {
    shared Integer x = 1;
}

void switchFooBar() {
    FooBar fb = foo;
    switch (fb) 
    case (foo) { print(fb.y); }
    case (bar) { print(fb.x); }
    switch (fb) 
    case (foo, bar) {}
    switch (fb) 
    case (foo|bar) {}
}
