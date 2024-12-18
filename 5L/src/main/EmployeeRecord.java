public class EmployeeRecord implements Comparable<EmployeeRecord> {
    String name;
    double payRate;
    double hoursWorked;
    boolean workedThisWeek;

    public EmployeeRecord(String n, double pr, double hw, boolean wtw) {
        name = n;
        payRate = pr;
        hoursWorked = hw;
        workedThisWeek = wtw;
    }

    @Override
    public String toString() {
        if (workedThisWeek)
            return "[" + name + ": $" + payRate + " x " + hoursWorked + " hours]";
        else
            return "[" + name + "--off this week]";
    }

    @Override
    public int compareTo(EmployeeRecord o) {
        return name.compareTo(o.name);
    }
}
