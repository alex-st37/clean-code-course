package victor.training.cleancode.immutable.advanced;

import com.google.common.collect.ImmutableList;

import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class ImmutableAdvanced {
  public static void main(String[] args) {
    ImmutableList<Integer> numbers = Stream.of(1, 2, 3).collect(toImmutableList()); // ArrayList

    Immutable blue = new Immutable(new Point(1, 2), numbers, new Other(15));
    System.out.println("Before: " + blue);

    // Confused Variable code smell
    // this practice, of reassigning the variable can lead to temporal coupling and confusion in the reader
    Immutable moved = blue.withPoint(wilderness(blue)); // wither returns a changed clone

    System.out.println("Moved object:  " + moved);
  }

  private static Point wilderness(Immutable immutable) {
    // imagine 1500 lines of code working with immutable object

    // dark, deep logic not expected to change the immutable object x,y

    // quickfix BUG-123124  Fri evening hack
//    immutable.getNumbers().clear();

    // CR: in this wilderness, we need to 'relocate the position of the object on the screen'  like this:
//    immutable.setX(immutable.getX()+1);
//    immutable.setY(immutable.getY()+1);

    // if X and Y change together, they probably belong together
//    return new Immutable(immutable.getPoint().moveBy(1,1),
//        immutable.getNumbers(),
//        immutable.getOther());
    return immutable.getPoint().moveBy(1, 1);
  }
}

record Point(int x, int y) {
  public Point moveBy(int dx, int dy) {
    return new Point(x + dx, y + dy);
  }
}

// DEEP immutable now: all its object graph is unchangeable after instantiation
class Immutable {
  //  private final Integer x;
//  private final Integer y;
  private final Point point;
  private final ImmutableList<Integer> numbers;
  private final Other other;

  Immutable(Point point, ImmutableList<Integer> numbers, Other other) {
    this.point = point;
    this.numbers = numbers;
    this.other = other;
  }

  // WITH-er
  public Immutable withPoint(Point movedPoint) {
    return new Immutable(movedPoint, numbers, other);
  }

  public Point getPoint() {
    return point;
  }

  public ImmutableList<Integer> getNumbers() {
//    return Collections.unmodifiableList(numbers); // decorating the original list to block mutations
    return numbers;
  }

  public Other getOther() {
    return other;
  }

  @Override
  public String toString() {
    return "Immutable{" +
           "point=" + point +
           ", numbers=" + numbers +
           ", other=" + other +
           '}';
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
