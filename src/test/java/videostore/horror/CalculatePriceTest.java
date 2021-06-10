package videostore.horror;

import org.junit.Test;
import videostore.horror.Movie.Category;

public class CalculatePriceTest {
   @Test
   public void test() {
      for (Category category : Category.values()) {
         Movie movie = new Movie("X", category);
         Rental rental = new Rental(movie, 1);
         rental.calculatePrice();
      }
//       new Rental()
   }
}