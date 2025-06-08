package ws10b;

public class Person {
    public final String firstName;
    public final String lastName;
    private int hashCode=0;
    public Person(String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Person p) {
            return firstName.equals(p.firstName) && lastName.equals(p.lastName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if(hashCode==0) {
            hashCode = firstName.hashCode();
            hashCode = hashCode * 31 + lastName.hashCode();
        }
        return hashCode;
    }
}

