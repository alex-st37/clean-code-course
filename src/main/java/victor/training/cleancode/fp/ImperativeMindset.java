package victor.training.cleancode.fp;

import victor.training.cleancode.fp.support.Order;

import java.util.ArrayList;
import java.util.List;

public class ImperativeMindset {
  public int totalOrderPrice(List<Order> orders) {
    // RAU:
    // .reduce(0, Integer::sum);

    // var ref = new Object() {
    //      int sum = 0;
    //    };

    //    final int[] sum = {0};

    //     AtomicInteger sum = new AtomicInteger();

    int sum = orders.stream()
        .filter(Order::isActive)
        .mapToInt(Order::getPrice)
        .sum();

    return sum;
  }


  public List<Integer> getOrderPrices(List<Order> orders) {
//    List<Integer> prices = new ArrayList<>();
//    orders.stream()
//        .filter(order -> order.isActive())
//        .forEach(order -> {
//          prices.add(order.getPrice());
//        });
    List<Integer> prices = orders.stream()
            .filter(Order::isActive)
            .map(Order::getPrice)
            .toList();
    return prices;
  }
}
