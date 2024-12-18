import java.util.*;

import static java.util.Arrays.asList;

public class LambdaFun {

      //--------Set-oriented methods----------
      public static void print(String label, Set<?> s) {
            System.out.print(label + ": ");
            //Task 1 Step 1
            s.forEach(value -> System.out.print(" " + value));
            System.out.println();
      }

      public static Set<Integer> returnNegative(Set<Integer> s) {
            //Task 1 Step 2
            Set<Integer> negatives = new TreeSet<>();
            s.forEach(value -> {
                  if (value < 0) {
                        negatives.add(value);
                  }
            });
            return negatives;
      }

      public static Set<Integer> multiplyCombos(Set<Integer> s1, Set<Integer> s2) {
            //Task 1 Step 3
            Set<Integer> result = new TreeSet<>();
            s1.forEach(a -> s2.forEach(b -> result.add( a * b)) );
            return result;
      }

      public static void giveHolidayHours(Set<EmployeeRecord> workers, double hours) {
            //Task 1 Step 4
            workers.forEach(worker -> {
                  worker.hoursWorked += hours;
                  worker.workedThisWeek = true;
            });
      }

      public static Set<EmployeeRecord> collectWorkingWorkers(Set<EmployeeRecord> workers) {
            //Task 1 Step 5
            Set<EmployeeRecord> workingWorkers = new TreeSet<>();
            workers.forEach(worker -> {
                  if (worker.workedThisWeek) {
                        workingWorkers.add(worker);
                  }
            });
            return workingWorkers;
      }

      public static Map<String, Double> createPayroll(Set<EmployeeRecord> workers) {
            //Task 1 Step 6
            Map<String, Double> result = new TreeMap<>();
            workers.forEach(worker -> {
                  if (worker.workedThisWeek) {
                        double pay = worker.hoursWorked * worker.payRate;
                        result.put(worker.name, pay);
                  }
            });
            return result;
      }
      //--------Map-oriented methods----------
      public static void print(String label, Map<?, ?> m) {
            System.out.print(label + ": ");
            //Task 2 Step 1
            m.forEach((k,v) -> System.out.print("(" + k + ", " + v + ") "));
            System.out.println();
      }

      public static Set<Integer> addKeysAndValuesIntoSet(Map<Integer, Integer> m) {
            //Task 2 Step 2
            Set<Integer> result = new TreeSet<>();
            m.forEach((k,v) -> result.add(k+v));
            return result;
      }

      public static void addKeysToValues(Map<Integer, Integer> m) {
            //Task 2 Step 3
            m.replaceAll((k,v) -> (k + v));
      }

      public static void updateDepartmentCount(Map<String, Integer> headCounts,
                                               Map<String, Set<EmployeeRecord>> newEmployees) {
            //Task 2 Step 4
            headCounts.replaceAll((department, count) -> count + newEmployees.getOrDefault(department, Collections.emptySet()).size());
      }

      public static void updateBudget(Map<String, Double> budget,
                                      Map<String, Set<EmployeeRecord>> departmentEmployees) {
            //Task 2 Step 5
            budget.replaceAll((department, availableBudget) -> {
                  Set<EmployeeRecord> employees = departmentEmployees.getOrDefault(department, Collections.emptySet());
                  double totalPay = 0;
                  for (EmployeeRecord employee: employees) {
                  totalPay +=employee.hoursWorked * employee.payRate;
            }
            return availableBudget - totalPay;
            });
      }
}
