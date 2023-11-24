package victor.training.cleancode;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

class SomeController {
  private SomeService someService;

  @GetMapping("blue/{storeId}")
  public void blueEndpoint(@PathVariable int storeId, @RequestBody Task task) {
    someService.blueMethod(storeId, task);
  }

  @GetMapping("red/{storeId}")
  public void redEndpoint(@PathVariable int storeId, @RequestBody Task task) {
    someService.redMethod(storeId, task);
  }
}

class SomeService {
  public void blueMethod(int id, Task task) {
    BooleanParameters.bigUglyMethod(id, task);
  }

  public void greenMethod(int id, Task task) {
    BooleanParameters.bigUglyMethod(id, task);
  }

  public void yellowMethod(int id, Task task) {
    BooleanParameters.bigUglyMethod(id, task);
  }

  public void redMethod(int id, Task task) {
    BooleanParameters.bigUglyMethod(id, task);
  }
}

class MyService {
  public void useCase323(int id, Task task) {
    // TODO The shared called method must execute logic specific for my use-case #323
    BooleanParameters.bigUglyMethod(id, task);
  }
}

public class BooleanParameters {

  // Warning⚠️: this method might be called from multiple places in the codebase ...
  public static void bigUglyMethod(int storeId, Task task) {
    cow(storeId, task);

    System.out.println("Logic just for CR#323 : " + task);

    donkey(storeId);
  }

  private static void donkey(int storeId) {
    System.out.println("Donkey Logic 1 " + storeId);
    System.out.println("Donkey Logic 2 " + storeId);
    System.out.println("Donkey Logic 3 " + storeId);
  }

  private static void cow(int storeId, Task task) {
    System.out.println("Cow Logic 1 " + task + " and " + storeId);
    System.out.println(task);
    System.out.println("Cow Logic 3 " + task);
  }


  // ============== "BOSS" LEVEL: Deeply nested functions are a lot harder to break down =================

  // Lord gave us tests! 👌 TODO run the tests
  public void bossLevel(List<Task> tasks, boolean cr323) {
    System.out.println("Logic1");
    System.out.println("Logic3");
    for (Task task : tasks) {
      System.out.println("Starting " + task);
      task.setStarted(true);
    }

    List<Integer> taskIds = tasks.stream().map(Task::getId).collect(Collectors.toList());

    if (cr323) { // TODO task = remove the boolean
      for (Task task : tasks) {
        System.out.println("My Logic: " + task);
      }
    }
int    a =1;
    int index = 0;
    for (Task task : tasks) {
      index++;
      a=2;
      System.out.println("Audit task #" + index + ": " + task);
    }
    System.out.println(a);
    int size = tasks.size();
    System.out.println("Logic6 " + size);
    System.out.println("Task Ids: " + taskIds);
    System.out.println("Logic8");
  }

  public void bossLevelNoFluff(List<Task> tasks) {
    System.out.println("Logic1");
    System.out.println("Logic7 on fluff=false " + tasks);
    System.out.println("Logic8");
  }

  private void innocent(List<Task> tasks) {
    tasks.clear();

  }

}


@Data
class Task {
  private final int id;
  private boolean started;
}