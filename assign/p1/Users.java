/** Import packages from course webiste.*/
import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;

/** 
 * Main function that handles user information input and processing.
 * This function prompts the user to input their name, role, and birth date,
 * then creates the corresponding user object and outputs the user information.
 */
void main() {
	CheckVersion("2025S1-3");
	String name = readln("Please input your name: "); // Prompt user to input name
	String roleStr = readln("Please input your role:");  // Prompt user to input role
	Role role = roleStr.equals("S") ? Role.STUDENT : roleStr.equals("A") ? Role.ACADEMIC : Role.PROFESSIONAL_STAFF; // Determine role type based on input 
	String year = readln("Please input your birth year (yyyy):"); // Prompt user to input birth year
	String month = readln("Please input your birth month (MM):"); // Prompt user to input birth month
	String day = readln("Please input your birth day (dd):"); // Prompt user to input birth day
	Date dateOfBirth = ParseDate(day + "/" + month + "/" + year, "dd/MM/yyyy"); // Parse birth date
	User user = new User(name, role, dateOfBirth); // Create user object
	println(toInfo(user, CurrentDate())); // Output user information
}

/** Definition record of Users*/
record User (
	String name, //user's name
	Role role, //user's role
	Date dateOfBirth //user's birthday
) {}


/** There are three types of Role in a university. */
 enum Role {
    /** Study in a university */
    STUDENT,

    /** Faculty members who directly participate in teaching, research, or academic activities. 
     * Usually associated with academic output. */
    ACADEMIC,

    /** Non-teaching and research staff.
     * Provide administrative, technical or management support for academic activities. */
    PROFESSIONAL_STAFF
}

/** Creates a new student with given name and date of birth */
User makeStudent(String name, Date dateOfBirth){
	return new User(name, Role.STUDENT, dateOfBirth);
}

/** Creates a new academic with given name and date of birth */
User makeAcademic(String name, Date dateOfBirth){
  return new User(name, Role.ACADEMIC, dateOfBirth);
}

/** Creates a new professional staff with given name and date of birth */
User makeProfessionalStaff(String name, Date dateOfBirth){
  return new User(name, Role.PROFESSIONAL_STAFF, dateOfBirth);
}

/** 
 * Returns the next date from today (excluding today) on which the
 * given user can celebrate their birthday.
 * Thought process:
 * Goal: That is, return the next birthday of the user that does not include today.
 * Steps: 
 * 1) Find the user's birthday; 
 * 2) Find today's date; 
 * 3) Compare the dates. If the user's birthday is greater than today's date, it's this year； 
 * If the user's birthday is less than or equal to today's date, it's next year.
 * @param user The user whose next birthday is being calculated.
 * @param today The current date used to determine whether the user's birthday is yet to come or has passed.
 * @return The Date of the user's next birthday.
 */
Date nextBirthday(User user, Date today){
	Date birthDate = user.dateOfBirth(); //Get user's birthday.
	int currentYear = GetYear(today); //Get the current year. GetYear(Date date)
    int birthYear = GetYear(birthDate); //Get user's birth year.
	int diff = currentYear - birthYear; // current year must > birth year, so negative numbers are not considered.
	Date currentYearBirthday = AddYears(birthDate, diff); // Calculate user's birthday this year. AddYears(T date, long years)
	boolean isCurrentYearBirthPassed = Compare(currentYearBirthday, today) <= 0; //if birthday already passed.
	return isCurrentYearBirthPassed ? AddYears(birthDate, diff + 1) : AddYears(birthDate, diff);
}

/**
 * returns the current number of full years that have elapsed since
 * the given user was born up to today
 * Thought process:
 * Goal：Calculate birthday
 * Steps:
 * 1) Calculate the year difference between currentYear and birthDate; 
 * 2) Compare whether this year's birthday has passed; 
 * 3) It has not passed -1, and it has passed unchanged
 * @param user The user whose age is being calculated.
 * @param today The current date, used to determine whether the birthday has passed.
 * @return The user's age here and now.
 */
long currentAge(User user, Date today){
  Date birthDate = user.dateOfBirth; //Get user's birthday.
  int currentYear = GetYear(today); //Get the current year. GetYear(Date date)
  int birthYear = GetYear(birthDate); //Get user's birth year.
  int diff = currentYear - birthYear; //Calculate the difference between the current year and the year of birth.
  Date currentYearBirthday = AddYears(birthDate, diff); //Calculate the birthday date of the current year.
  boolean isCurrentYearBirthPassed = Compare(currentYearBirthday, today) <= 0; 
	return isCurrentYearBirthPassed ? diff : diff - 1; // If the birthday has passed or is today, return the age. Otherwise, subtract 1 from the age.
}

/**
 * returns the next date from today (excluding today) at which the
 * given user will get a salary increase, or Nothing if they will
 * not get one.
 * Goal: 
 * The academic get a pay raise on their birthdays in odd years, 
 * while the professional staff get a pay raise on their birthdays in even years. 
 * Students don't get a pay raise.
 * Steps:
 * 1) Determine whether the birthday is odd or even; 
 * 2) Use switch to categorize and discuss.
 * @param user  The current user object.
 * @param today The current date.
 * @return A Maybe object representing the next salary increase date.
 */
Maybe<Date> nextSalaryIncrease(User user, Date today){
  Date birthDate = user.dateOfBirth(); //Get user's birthday.
  int currentYear = GetYear(today); //Get the current year. GetYear(Date date)
  int birthYear = GetYear(birthDate); //Get user's birth year.
  int diff = currentYear - birthYear; //Calculate the difference between the current year and the year of birth.
  Date currentYearBirthday = AddYears(birthDate, diff);

  return switch (user.role()){ // Use a switch expression to return the appropriate result based on the user's role. Return a value directly in the switch statement.
    case STUDENT -> new Nothing<Date>();  // Students do not receive a pay raise, return Nothing.
    case ACADEMIC -> { 
      if (currentYear % 2 != 0) { // Academics receive a pay raise on their birthdays in odd years.
        yield new Something<Date>(currentYearBirthday);
      } else {
        yield new Something<Date>(AddYears(birthDate, diff + 1)); // If the current year is even, the next pay raise is on the next year's birthday.
      }
    }
    case PROFESSIONAL_STAFF -> {
      if (currentYear % 2 == 0) { // Professional staff receive a pay raise on their birthdays in even years.
        yield new Something<Date>(currentYearBirthday);
      } else {
        yield new Something<Date>(AddYears(birthDate, diff + 1));   // If the current year is odd, the next pay raise is on the next year's birthday.
      }
    }
  };
}

/** 
 * Provides detailed information about the given user, relative to today's date.
 * This includes the user's name, role, age, next birthday, and next salary increase date.
 * @param user The user whose information is being provided.
 * @param today The current date used to calculate age and other time-dependent values.
 * @return A string containing the formatted user information.
 */
String toInfo(User user, Date today) {
	String s = "";
	s = s + "================User Info==================\n";
	s = s + "Name: " + user.name() + "\n";
	s = s + "Role: " + (user.role() == Role.STUDENT 
					? "student" : user.role() == Role.ACADEMIC 
					? "academic" : "professional staff") + "\n";
	s = s + "Age: " + currentAge(user, today) + "\n";
	s = s + "Next Birthday:" + FormatDate(nextBirthday(user, today), "dd/MM/yyyy") + "\n";
	String nextSalaryIncStr = switch(nextSalaryIncrease(user, today)){
		case Nothing<Date>() -> "Not applicable"; // Salary increase not applicable (e.g. for Students).
		case Something<Date>(Date d) -> FormatDate(d, "dd/MM/yyyy"); // Format next salary increase date.
	};
	
	s = s + "Next Salary Increase Date:" + nextSalaryIncStr + "\n";
	s = s + "===========================================";
	return s;
}

/** 
 * runAsTest
 * Runs a given function as a test. The test succeeds if all
 * assertions within the function succeed, and no errors occur.
 */
void test() {
	runAsTest(this::testCurrentAgeCorrect);
}

/** Test whether the current age calculation is correct. 
 * This test verifies whether the currentAge function returns the expected age given a given birth date and current date. 
 */
void testCurrentAgeCorrect(){
	Date birthDate = ParseDate("2000-02-01", "yyyy-MM-dd");
	User user = new User("Eric", Role.STUDENT, birthDate);
	int expectAge = 25;
	testEqual(currentAge(user, CurrentDate()), expectAge, "Age test wrong!!");
}

