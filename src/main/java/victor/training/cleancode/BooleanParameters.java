package victor.training.cleancode;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Implement the variation required for CR323 without adding a boolean parameter
 */

public class BooleanParameters {

   public static void main(String[] args) {
      // The big method is called from various foreign places in the codebase
      //@GetMapping
      bigUglyMethod(1, new Task(5), task -> System.out.println("Logic just for UC1 : " + task));

      bigUglyMethod(2, new Task(4), task -> System.out.println("Lgic just foor UC2 : " + task));

      bigUglyMethod(3, new Task(3), task -> System.out.println("Lgic just foor UC2 : " + task));

      bigUglyMethod(4, new Task(2), task -> System.out.println("Lgic just foor UC2 : " + task));

      bigUglyMethod(5, new Task(1), task -> System.out.println("Lgic just foor UC2 : " + task));

      // TODO From my use-case #323, I call it too, to do more within:. + task.setDetail(15)
      Task task = new Task(1);
      task.setDetail(13);
      bigUglyMethod(2, task, t -> System.out.println("Lgic just foor CR323 : " + t));

   }

   static void bigUglyMethod(int b, Task task, Consumer<Task> consumer) {
      beforeLogic(b, task);

      consumer.accept(task);

      afterLogic(b);
   }

   private static void afterLogic(int b) {
      System.out.println("More Complex Logic " + b);
      System.out.println("More Complex Logic " + b);
      System.out.println("More Complex Logic " + b);
   }

   private static void beforeLogic(int b, Task task) {
      System.out.println("Complex Logic 1 " + task + " and " + b);
      System.out.println("Complex Logic 2 " + task);
      System.out.println("Complex Logic 3 " + task);
   }


   // ============== "BOSS" LEVEL: Deeply nested functions are a lot harder to break down =================

   // see the tests
   public void bossLevel(boolean fluff, List<Task> tasks, boolean cr323) {
      beforeBoss(tasks);
      afterBoss(tasks);
   }
   public void bossLevel323(boolean fluff, List<Task> tasks, boolean cr323) {
      beforeBoss(tasks);
      for (Task task : tasks) {
         System.out.println("My Logic: " + task);
      }
      afterBoss(tasks);
   }

   private void afterBoss(List<Task> tasks) {
      int index = 0;
      for (Task task : tasks) {
         index++;
         System.out.println("Logic5 " + index + " on " + task.isRunning());
      }
      System.out.println("Logic6 " + tasks.size());
      List<Integer> taskIds = tasks.stream().map(Task::getId).collect(Collectors.toList());
      System.out.println("Task Ids: " + taskIds);
      System.out.println("Logic7");
   }

   private void beforeBoss(List<Task> tasks) {
      System.out.println("Logic1");
      System.out.println("Logic2");
      System.out.println("Logic3");

      for (Task task : tasks) {
         System.out.println("Logic4: Validate " + task);
         task.setRunning();
      }
   }

}


@ToString
class Task {
   private final int id;
   private boolean running;
   private int detail = 0;

   Task(int id) {
      this.id = id;
   }

   public void setDetail(int detail) {
      this.detail = detail;
   }

   public int getDetail() {
      return detail;
   }

   public void setRunning() {
      running = true;
   }

   public boolean isRunning() {
      return running;
   }

   public int getId() {
      return id;
   }
}