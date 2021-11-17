package victor.training.cleancode;

import lombok.Getter;
import lombok.Value;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExtractValueObjects {
   // Ford Focus Mk2:     [2012 ---- 2016]
   // Search:                 [2014 ---- 2018]
   public static void main(String[] args) {
      // can't afford a 2021 car
      CarSearchCriteria criteria = new CarSearchCriteria(2014, 2018, "Ford");
      CarModel fordFocusMk2 = new CarModel("Ford", "Focus", new Interval(2012, 2016));
      List<CarModel> models = new SearchEngine().filterCarModels(criteria, Arrays.asList(fordFocusMk2));
      System.out.println(models);
   }
}


class SearchEngine {

   public List<CarModel> filterCarModels(CarSearchCriteria criteria, List<CarModel> models) {

      Interval criteriaInterval = new Interval(criteria.getStartYear(), criteria.getEndYear());
      List<CarModel> results = models.stream()
          .filter(model -> criteriaInterval.intersects(model.getYearInterval()))
          .collect(toList());
      System.out.println("More filtering logic");
      return results;
   }
   // Tarzan ; got hurt
   // baby steps
   // INLINE STATIC function to push in all code the body of our function
   // discovered Interval class from a large singature
   // we added logic to it!


   private void applyCapacityFilter() {
      System.out.println(new Interval(1000, 1600).intersects(new Interval(1250, 2000)));
   }

}

class Alta {
   private void applyCapacityFilter() {
      System.out.println(new Interval(1000, 1600).intersects(new Interval(1250, 2000)));
   }

}

class MathUtil {
}

//@Data // i hate data
@Value // i love this
class Interval {
   int start;
   int end;

   public Interval(int start, int end) {
      if (start > end) { // makes the Interval less reusable
         throw new IllegalArgumentException("start larger than end"); // self-validating model
         // <50% of teams (best of teams) Domain Driven Desing
      }
      this.start = start;
      this.end = end;
   }

   public boolean intersects(Interval other) {
      return start <= other.end && other.start <= end;
   }
}
//record Interval(int start, int end) {} java 17


class CarSearchCriteria {
   private final int startYear;
   private final int endYear;
   private final String make;

   public CarSearchCriteria(int startYear, int endYear, String make) {
      this.make = make;
      if (startYear > endYear) throw new IllegalArgumentException("start larger than end");
      this.startYear = startYear;
      this.endYear = endYear;
   }

   public int getStartYear() {
      return startYear;
   }

   public int getEndYear() {
      return endYear;
   }

   public String getMake() {
      return make;
   }
}

// Holy Enty Model
//@Entity // CAR_MODEL(ID,MAKE,MODEL,START_YEAR,END_YEAR,
@Getter
class CarModel {
      @Id
   private Long id;
   private String make;
   private String model;
//   @Embedded //@AttributeOverrides
   private Interval yearInterval;
   // -2+1  == -1 field for my entity =>> lighter entities

   private CarModel() {
   } // for Hibernate

   public CarModel(String make, String model, Interval yearInterval) {
      this.make = make;
      this.model = model;
      this.yearInterval = yearInterval;
   }


   public Interval getYearInterval() {
      return yearInterval;
   }

   public Long getId() {
      return id;
   }

   // "Man in the middle"

   public String getMake() {
      return make;
   }

   public String getModel() {
      return model;
   }


   @Override
   public String toString() {
      return "CarModel{" +
             "make='" + make + '\'' +
             ", model='" + model + '\'' +
             '}';
   }
}

class CarModelMapper {
   public CarModelDto toDto(CarModel carModel) {
      CarModelDto dto = new CarModelDto();
      dto.make = carModel.getMake();
      dto.model = carModel.getModel();
      dto.startYear = carModel.getYearInterval().getStart();
      dto.startYear = carModel.getYearInterval().getStart();
      dto.endYear = carModel.getYearInterval().getEnd();
      return dto;
   }

   public CarModel fromDto(CarModelDto dto) {
      return new CarModel(dto.make, dto.model, new Interval(dto.startYear, dto.endYear));
   }
}

class CarModelDto {
   public String make;
   public String model;
   public int startYear;
   public int endYear;
}