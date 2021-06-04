package videostore.horror;

import static java.util.Objects.requireNonNull;

public class Rental {
   public static final int MIN_DAYS_FOR_BONUS_FOR_NEW_RELEASE = 2;
   private final Movie movie;
   private final int daysRented;

   public Rental(Movie movie, int daysRented) {
      this.movie = requireNonNull(movie);
      this.daysRented = daysRented;
   }

   public int getDaysRented() {
      return daysRented;
   }

   public Movie getMovie() {
      return movie;
   }

   public double computePrice() {
      double result = 0;
      switch (movie.getCategory()) {
         case REGULAR:
            result += 2;
            if (daysRented > 2)
               result += (daysRented - 2) * 1.5;
            break;
         case NEW_RELEASE:
            result += daysRented * 3;
            break;
         case CHILDREN:
            result += 1.5;
            if (daysRented > 3)
               result += (daysRented - 3) * 1.5;
            break;
      }
      return result;
   }

   public int computeBonusPoints() {
      int frequentRenterPoints = 1;
      if (movie.isNewRelease() && getDaysRented() >= 2) {
         frequentRenterPoints++;
      }
      return frequentRenterPoints;
   }

}
