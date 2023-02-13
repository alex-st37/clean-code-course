package victor.training.cleancode;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SomeController {
  SomeService someService;

  @GetMapping("blue/{storeId}")
  public void blueEndpoint(@PathVariable int storeId, @RequestBody Task task) {
    someService.blueMethod(storeId, task);
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
    // TODO From my use-case #323, I call the method too, but have it do more within:
    BooleanParameters.bigUglyMethod323(2, task);
    System.out.println("Logic for cr323");
  }
}

public class BooleanParameters {
  // Note: this method might be called from multiple places in the codebase ...
  static void bigUglyMethod323(int storeId, Task task) {
    cow(storeId, task);

    System.out.println("Logic just for CR#323 : " + task);

    donkey(storeId);
  }

  static void bigUglyMethod(int storeId, Task task) {
    cow(storeId, task);
    donkey(storeId);
  }

  private static void donkey(int storeId) {
    System.out.println("Donkey Logic 1 " + storeId);
    System.out.println("Donkey Logic 2 " + storeId);
    System.out.println("Donkey Logic 3 " + storeId);
  }

  private static void cow(int storeId, Task task) {
    System.out.println("Cow Logic 1 " + task + " and " + storeId);
    System.out.println("Cow Logic 2 " + task);
    System.out.println("Cow Logic 3 " + task);
  }


  // ============== "BOSS" LEVEL: Deeply nested functions are a lot harder to break down =================

  // Lord gave us tests!
  public void bossLevel(List<Task> taskList) {
    bossStart(taskList);

    bossEnd(taskList);
  }
  public void bossLevel323(List<Task> taskList) {
    bossStart(taskList);

    for (Task task : taskList) { // optional if youwant to make it a stream.forEach()
      System.out.println("My Logic: " + task);
    }
    bossEnd(taskList);
  }

  private static void bossEnd(List<Task> taskList) {
    for (int i = 0; i < taskList.size(); i++) {
      System.out.println("Audit task #" + (i+ 1) + ": " + taskList.get(i));
    }
    //MANDATORY: if a part of a for accumulates somethign (ids here) -> Stream 100% of the cases.
    List<Integer> taskIds = taskList.stream().map(Task::getId).toList();

    // if you benchmark this code very often you will see it WORKING FASTER in JAVA?!!?!?!?!  JIT
    int taskCount = taskList.size();
    System.out.println("Logic6 " + taskCount);
    System.out.println("Task Ids: " + taskIds);
    System.out.println("Logic8");
  }

  private static void bossStart(List<Task> taskList) {
    System.out.println("Logic1");
    System.out.println("Logic3");
    for (Task task : taskList) {
      System.out.println("Validate " + task);
      task.setStarted(true);
    }
  }


  // Lord gave us tests!
  public void bossLevelNoFluff(List<Task> taskList) {
    System.out.println("Logic1");
    System.out.println("Logic7 on fluff=false " + taskList);
    System.out.println("Logic8");
  }


}


@Data
class Task {
  private final int id;
  private boolean started;
}