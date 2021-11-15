package victor.training.cleancode;

public class ParameterObjects {
   public static void main(String[] args) {
      new ParameterObjects().placeOrder("John", "Doe", "St. Albergue", "Paris", 99);
   }

   public void placeOrder(String firstName, String lName, String city, String streetName, Integer streetNumber) {
      if (firstName == null || lName == null) throw new IllegalArgumentException();

      System.out.println("Some Logic");
      System.out.println("Shipping to " + city + " on St. " + streetName + " " + streetNumber);

   }
}

class AnotherClass {
   public void otherMethod(String firstName, String lastName, int x) {
      if (firstName == null || lastName == null) throw new IllegalArgumentException();

      System.out.println("Another distant Logic " + x);
      System.out.println("Person: " + lastName);
   }
}

class Person {
   private Long id;
   private String firstName;
   private String lastName;
   private String phone;

   public Person(String firstName, String lastName) {
      if (firstName == null || lastName == null) throw new IllegalArgumentException();
      this.firstName = firstName;
      this.lastName = lastName;
   }

   // TODO hard-core: implement setter
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }
}

class PersonService {
   public void f(Person person) {
      System.out.println("Hi there, " + person.getFirstName());

      String fullName = formatFullName(person);
      // pure function:  also typically very fast since they do no network.
      // 1  NO side effects, eg>
      //   a) File read/write, DB, HTTP call (any network);
      //    b) modifying some state (eg fields in Person class)
      // 2   Same output for same inputs:: eg DONT:
      //    a) if the return of formatFullName depends on some state besides the Person param
               //
      //    b) current time/random
      System.out.println("Record for " + fullName);
      System.out.println("Record for " + fullName);
      System.out.println("Record for " + fullName);
   }

int n; // state outside of the params involved in the result
   private String formatFullName(Person person) {
      //prefix = run("SELECT aaa FROM ..."); // NOT pure because a COMMIT might occur in between 2 calls of tihs function

      // anotherImpureFunction=()   BAD
      //
      return  /*n + *//*prefix + */person.getFirstName() + " " + person.getLastName().toUpperCase();
   }

   public void p(String streetName, String city, Integer streetNumber) {
      System.out.println("Living in " + city + " on St. " + streetName + " " + streetNumber);
   }

   public void pcaller() {
       p("Dristor", "Bucharest", 91);
   }
}