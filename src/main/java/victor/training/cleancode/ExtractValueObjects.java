package victor.training.cleancode;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

class ExtractValueObjects {

  // see tests
  public List<CarModel> filterCarModels(CarSearchCriteria criteria, List<CarModel> models) {
    List<CarModel> results = models.stream()
            .filter(model -> criteria.getYearInterval()
                    .intersects(model.getYearInterval()))
            .collect(Collectors.toList());
    System.out.println("More filtering logic");
    return results;
  }

  private void applyCapacityFilter() {
    System.out.println(new Interval(1000, 1600).intersects(
            new Interval(1250, 2000)));
  }

}

class Alta {
  private void applyCapacityFilter() {
    System.out.println(new Interval(1000, 1600).intersects(
            new Interval(1250, 2000)));
  }

}

class MathUtil {


}

@Embeddable
class Interval {
  private int start;
  private int end;
  protected Interval() {} // for Hibernate only
  public Interval(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public boolean intersects(Interval other) {
    return start <= other.end && other.start <= end;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }
}


class CarSearchCriteria { // smells like JSON ...
  private final int startYear;
  private final int endYear;
  private final String make;


  public CarSearchCriteria(int startYear, int endYear, String make) {
    this.make = make;
    if (startYear > endYear) throw new IllegalArgumentException("start larger than end");
    this.startYear = startYear;
    this.endYear = endYear;
  }

    @NotNull
    public Interval getYearInterval() {
        return new Interval(startYear, endYear);
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

@Entity
class CarModel { // the holy Entity Model. sfantul. inegalabilul.
    // model de date din BE tinut secret de API doar pentur a-ti scrie logica pe el.
    // ORM entity.
  @Id
  private Long id;
  private String make;
  private String model;
//  private int startYear;
//  private int endYear;
  @Embedded
  private Interval yearInterval;

  protected CarModel() {
  } // for Hibernate

  public CarModel(String make, String model, int startYear, int endYear) {
    this.make = make;
    this.model = model;
    if (startYear > endYear) throw new IllegalArgumentException("start larger than end");
    yearInterval = new Interval(startYear, endYear);
  }

  public Long getId() {
    return id;
  }

  @NotNull
  public Interval getYearInterval() {
    return yearInterval;
  }

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
    dto.startYear = carModel.getYearInterval().getStart();
    dto.startYear = carModel.getYearInterval().getStart();
    dto.startYear = carModel.getYearInterval().getStart();
    dto.startYear = carModel.getYearInterval().getStart();
    dto.startYear = carModel.getYearInterval().getStart();
    dto.endYear = carModel.getYearInterval().getEnd();
    return dto;
  }

  public CarModel fromDto(CarModelDto dto) {
    return new CarModel(dto.make, dto.model, dto.startYear, dto.endYear);
  }
}

class CarModelDto {
  public String make;
  public String model;
  public int startYear;
  public int endYear;
}