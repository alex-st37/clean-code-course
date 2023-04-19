package victor.training.cleancode;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public class MicroTypes {

    //<editor-fold desc="Unknown source of data">
    public Map<CustomerId, List<ProductCount>> extremeFP() {
        Long customerId = 1L;
        Integer product1Count = 2;
        Integer product2Count = 4;
        return Map.of(new CustomerId(customerId), List.of(
                new ProductCount("Table", product1Count),
                new ProductCount("Chair", product2Count)
        ));
    }

    @Test
    void lackOfAbstractions() {
        Map<CustomerId, List<ProductCount>> map = extremeFP();

        for (CustomerId cid : map.keySet()) {
            String pl = map.get(cid).stream()
                    .map(productCount -> productCount.count() + " pcs. of " + productCount.name())
                    .collect(joining(", "));
            System.out.println("cid=" + cid + " got " + pl);
        }
    }

    //</editor-fold>
    record CustomerId(Long id) {
    }

    record ProductCount(String name, Integer count) {
    }
}