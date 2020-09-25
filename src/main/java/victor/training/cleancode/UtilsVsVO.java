package victor.training.cleancode;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

public class UtilsVsVO {
    public List<CarModel> filterCarModels(CarSearchCriteria criteria, List<CarModel> models) {
        List<CarModel> results = new ArrayList<>(models);
        Interval criteriaInterval = new Interval(criteria.getStartYear(), criteria.getEndYear());
        results.removeIf(model -> ! model.getYearInterval().intersects(criteriaInterval));
        // TODO tema results.removeIf(model -> ! model.getYearInterval().intersects(criteria.getYearInterval()));
        System.out.println("More filtering logic");
        return results;
    }

}

class AltColeg {
    {

        boolean b = new Interval(1, 3).intersects(new Interval(2, 4));
        System.out.println(b);
    }
}
@Embeddable
class Interval {
    private int start;
    private int end;

    protected Interval() {}
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean intersects(Interval other) {
        return start <= other.end && other.start <= end;
    }

}

class MathUtil {

}
















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

@Entity
class CarModel {
    private final String make;
    private final String model;
    private final int startYear;
    private final int endYear;
    @Embedded
    private Interval yearInterval;

    public CarModel(String make, String model, int startYear, int endYear) {
        this.make = make;
        this.model = model;
        if (startYear > endYear) throw new IllegalArgumentException("start larger than end");
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Interval getYearInterval() {
        return new Interval(startYear, endYear);
    }
}