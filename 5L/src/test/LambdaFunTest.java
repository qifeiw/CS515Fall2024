import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;

public class LambdaFunTest {
    Set<Integer> intSet1 = new TreeSet<>(asList(1, -4, 6, 2, -7));
    Set<Integer> intSet2 = new TreeSet<>(asList(-9, 5, -3, 8, 0));

    EmployeeRecord Cory = new EmployeeRecord("Cory", 15.00, 38, true);
    EmployeeRecord Leeroy =new EmployeeRecord("Leeroy", 17.00, 0, false);
    EmployeeRecord Doug =new EmployeeRecord("Doug", 17.40, 43, true);
    EmployeeRecord Bob =new EmployeeRecord("Bob", 16.50, 24, false);
    EmployeeRecord Alice =new EmployeeRecord("Alice", 18.00, 40, true);

    Set<EmployeeRecord> workers = new TreeSet<>(asList(Cory, Leeroy, Doug, Bob, Alice));

    private TreeMap<Integer, Integer> zip(Set<Integer> s1, Set<Integer> s2) {
        Iterator<Integer> i1 = s1.iterator();
        Iterator<Integer> i2 = s2.iterator();
        TreeMap<Integer, Integer> map= new TreeMap<>();
        while(i1.hasNext() && i2.hasNext()) {
            map.put(i1.next(), i2.next());
        }
        return map;
    }

    private Set<Integer> add(Set<Integer> s1, Set<Integer> s2) {
        Iterator<Integer> i1 = s1.iterator();
        Iterator<Integer> i2 = s2.iterator();
        TreeSet<Integer> set = new TreeSet<>();
        while(i1.hasNext() && i2.hasNext()) {
            set.add(i1.next() + i2.next());
        }
        return set;
    }

    @Test
    public void testReturnNegative1() {
        LambdaFun.print("Initial set", intSet1);
        Set<Integer> result = LambdaFun.returnNegative(intSet1);
        LambdaFun.print("Negative values", result);
        assertEquals(new TreeSet<>(asList(-4, -7)), result);
    }

    @Test
    public void testReturnNegative2() {
        LambdaFun.print("Initial set", intSet2);
        Set<Integer> result = LambdaFun.returnNegative(intSet2);
        LambdaFun.print("Negative values", result);
        assertEquals(new TreeSet<>(asList(-9, -3)), result);
    }

    @Test
    public void testMultiplyCombos() {
        assertEquals(new TreeSet<>(
                asList(-56, -54, -35, -32, -20, -18, -9, -6, -3, 0, 5, 8, 10, 12, 16, 21, 30, 36, 48, 63)),
                LambdaFun.multiplyCombos(intSet1, intSet2));
    }

    @Test
    public void testGiveHolidayHours() {
        Set<EmployeeRecord> workerCopy = new TreeSet<>(workers);
        Set<Double> goal = new TreeSet<>();
        double hours = 4.0;
        for (EmployeeRecord w : workerCopy)
            goal.add(w.hoursWorked + hours);
        LambdaFun.print("Initial set", workerCopy);
        LambdaFun.giveHolidayHours(workerCopy, hours);
        LambdaFun.print("Working workers", workerCopy);
        Set<Double> result = new TreeSet<>();
        for (EmployeeRecord w : workerCopy)
            result.add(w.hoursWorked);
        assertEquals(goal, result);
        for (EmployeeRecord w : workerCopy)
            assertTrue(w.workedThisWeek);
    }

    @Test
    public void testCollectWorkingWorkers() {
        LambdaFun.print("Initial set", workers);
        Set<EmployeeRecord> result = LambdaFun.collectWorkingWorkers(workers);
        LambdaFun.print("Working workers", result);
        Set<EmployeeRecord> goal = new TreeSet<>(workers);
        goal.remove(Leeroy);
        goal.remove(Bob);
        assertEquals(goal, result);
    }

    @Test
    public void testCreatePayroll() {
        Map<String, Double> result = LambdaFun.createPayroll(workers);
        LambdaFun.print("Payroll", result);
        Map<String, Double> goal = new TreeMap<>();
        goal.put("Alice", 720.0);
        goal.put("Cory", 570.0);
        goal.put("Doug", 17.4 * 43);
        assertEquals(goal, result);
    }

    @Test
    public void testAddKeysAndValuesIntoSet() {
        Map<Integer, Integer> map = zip(intSet1, intSet2);
        Set<Integer> setGoal = add(intSet1, intSet2);
        LambdaFun.print("Input map", map);
        Set<Integer> result = LambdaFun.addKeysAndValuesIntoSet(map);
        LambdaFun.print("Result set", result);
        assertEquals(setGoal, result);
    }

    @Test
    public void testAddKeysToValues() {
        Map<Integer, Integer> map = zip(intSet1, intSet2);
        Map<Integer, Integer> mapGoal = zip(intSet1, add(intSet1, intSet2));
        LambdaFun.print("Initial map", map);
        LambdaFun.addKeysToValues(map);
        LambdaFun.print("Map with keys added to values", map);
        assertEquals(mapGoal, map);
    }

    @Test
    public void testUpdateDepartmentCount() {
        Map<String, Set<EmployeeRecord>> departments = new TreeMap<>();
        departments.put("Finance", new TreeSet<>(asList(Alice, Doug)));
        departments.put("Procurement", new TreeSet<>(asList(Cory, Bob)));
        departments.put("Shipping", new TreeSet<>(asList(Leeroy)));
        Map<String, Integer> headCount = new TreeMap<>();
        headCount.put("Finance", 3);
        headCount.put("Maintenance", 6);
        headCount.put("Procurement", 5);
        headCount.put("Shipping", 4);
        LambdaFun.print("Initial Headcount", headCount);
        LambdaFun.updateDepartmentCount(headCount, departments);
        LambdaFun.print("Final Budget", headCount);
        Map<String, Integer> goalHeadcount = new TreeMap<>();
        goalHeadcount.put("Finance", 5);
        goalHeadcount.put("Maintenance", 6);
        goalHeadcount.put("Procurement", 7);
        goalHeadcount.put("Shipping", 5);
        assertEquals(goalHeadcount, headCount);
    }

    @Test
    public void testUpdateBudget() {
        Map<String, Set<EmployeeRecord>> departments = new TreeMap<>();
        departments.put("Finance", new TreeSet<>(asList(Alice, Doug)));
        departments.put("Procurement", new TreeSet<>(asList(Cory, Bob)));
        departments.put("Shipping", new TreeSet<>(asList(Leeroy)));
        Map<String, Double> budget = new TreeMap<>();
        budget.put("Finance", 20000.0);
        budget.put("Maintenance", 10000.0);
        budget.put("Procurement", 18000.0);
        budget.put("Shipping", 15000.0);
        LambdaFun.print("Initial Budget", budget);
        LambdaFun.updateBudget(budget, departments);
        LambdaFun.print("Final Budget", budget);
        Map<String, Double> goalBudget = new TreeMap<>();
        goalBudget.put("Finance", 18531.80);
        goalBudget.put("Maintenance", 10000.0);
        goalBudget.put("Procurement", 17034.0);
        goalBudget.put("Shipping", 15000.0);
        assertEquals(goalBudget, budget);
    }
}
