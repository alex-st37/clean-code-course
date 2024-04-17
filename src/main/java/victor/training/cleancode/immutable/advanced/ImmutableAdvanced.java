package victor.training.cleancode.immutable.advanced;

import com.google.common.collect.ImmutableList;

import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class ImmutableAdvanced {
  public static void main(String[] args) {
    ImmutableList<Integer> numbers = Stream.of(1, 2, 3).collect(toImmutableList()); // ArrayList

    Immutable blue = new Immutable(1, 2, numbers, new Other(15));
    System.out.println("Before: " + blue);

    blue = wilderness(blue); // Confused Variable code smell
    // this practice, of reassigning the variable can lead to temporal coupling and confusion in the reader

    System.out.println("Moved object:  " + blue);
  }

  private static Immutable wilderness(Immutable immutable) {
    // imagine 1500 lines of code working with immutable object

    // dark, deep logic not expected to change the immutable object x,y

    // quickfix BUG-123124  Fri evening hack
//    immutable.getNumbers().clear();

    // CR: in this wilderness, we need to 'relocate the position of the object on the screen'  like this:
//    immutable.setX(immutable.getX()+1);
//    immutable.setY(immutable.getY()+1);

    return new Immutable(immutable.getX() + 1, immutable.getY() + 1, immutable.getNumbers(), immutable.getOther());
  }
}

// DEEP immutable now: all its object graph is unchangeable after instantiation
class Immutable {
  private final Integer x;
  private final Integer y;
  private final ImmutableList<Integer> numbers;
  private final Other other;

  Immutable(Integer x, Integer y, ImmutableList<Integer> numbers, Other other) {
    this.x = x;
    this.y = y;
    this.numbers = numbers;
    this.other = other;
  }

  public ImmutableList<Integer> getNumbers() {
//    return Collections.unmodifiableList(numbers); // decorating the original list to block mutations
    return numbers;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  public Other getOther() {
    return other;
  }

  @Override
  public String toString() {
    return "Immutable{x=%d, y=%d, numbers=%s, other=%s}".formatted(x, y, numbers, other);
  }
}

class Other {
  private final int a;

  public Other(int a) {
    this.a = a;
  }

  public int getA() {
    return a;
  }

}
