/** Import packages from course website. */
import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;

/**
 * DESIGN RECIPE STEP 1: Data Definition for Role
 * 
 * Purpose: Represents the different roles a user can have in the university system.
 * 
 * Signature: Enumeration with three values: STUDENT, ACADEMIC, PROFESSIONAL_STAFF.
 * 
 * Examples:
 * - Role.STUDENT: A student user who doesn't receive salary increases
 * - Role.ACADEMIC: An academic staff member who gets salary increases in odd years
 * - Role.PROFESSIONAL_STAFF: A professional staff member who gets salary increases in even years
 * 
 * Design Strategy: Simple Expression - Enumeration to represent distinct user types.
 * 
 * Effects: Immutable enumeration with no side effects.
 */
enum Role {
	STUDENT,
	ACADEMIC,
	PROFESSIONAL_STAFF
}

/**
 * Purpose: Represents a university user with name, role, and birth date information.
 * 
 * Signature: Record containing String, Role, and Date fields.
 * 
 * Examples:
 * - new Users("Alice Johnson", Role.STUDENT, ParseDate("15/06/2002", "dd/MM/yyyy"))
 * - new Users("Prof. Smith", Role.ACADEMIC, ParseDate("12/08/1970", "dd/MM/yyyy"))
 * 
 * Design Strategy: Simple Expression - Immutable record for user data storage.
 * 
 * Effects: Creates immutable user object with no side effects.
 */
record Users(String name, Role role, Date dateOfBirth) {}

// =============================================================================
// TESTING INTERFACE FUNCTIONS
// =============================================================================

/**
 * Purpose: Creates a new student user with given name and date of birth.
 * 
 * Signature: String, Date -> Users
 * 
 * Examples:
 * - makeStudent("John Doe", ParseDate("15/03/2000", "dd/MM/yyyy")) 
 *   -> Users("John Doe", Role.STUDENT, 2000-03-15)
 * 
 * Design Strategy: Simple Expression - Constructor wrapper for student creation.
 * 
 * Effects: Creates new Users object with STUDENT role, no side effects.
 * 
 * @param name the student's name
 * @param dateOfBirth the student's birth date
 * @return a Users record representing a student
 */
// testing interface
Users makeStudent(String name, Date dateOfBirth) {
	return new Users(name, Role.STUDENT, dateOfBirth);
}

/**
 * Purpose: Creates a new academic user with given name and date of birth.
 * 
 * Signature: String, Date -> Users
 * 
 * Examples:
 * - makeAcademic("Dr. Smith", ParseDate("22/07/1975", "dd/MM/yyyy"))
 *   -> Users("Dr. Smith", Role.ACADEMIC, 1975-07-22)
 * 
 * Design Strategy: Simple Expression - Constructor wrapper for academic creation.
 * 
 * Effects: Creates new Users object with ACADEMIC role, no side effects.
 * 
 * @param name the academic's name
 * @param dateOfBirth the academic's birth date
 * @return a Users record representing an academic
 */
// testing interface  
Users makeAcademic(String name, Date dateOfBirth) {
	return new Users(name, Role.ACADEMIC, dateOfBirth);
}

/**
 * Purpose: Creates a new professional staff user with given name and date of birth.
 * 
 * Signature: String, Date -> Users
 * 
 * Examples:
 * - makeProfessionalStaff("Bob Wilson", ParseDate("10/12/1980", "dd/MM/yyyy"))
 *   -> Users("Bob Wilson", Role.PROFESSIONAL_STAFF, 1980-12-10)
 * 
 * Design Strategy: Simple Expression - Constructor wrapper for professional staff creation.
 * 
 * Effects: Creates new Users object with PROFESSIONAL_STAFF role, no side effects.
 * 
 * @param name the professional staff's name
 * @param dateOfBirth the professional staff's birth date
 * @return a Users record representing professional staff
 */
// testing interface
Users makeProfessionalStaff(String name, Date dateOfBirth) {
	return new Users(name, Role.PROFESSIONAL_STAFF, dateOfBirth);
}

// =============================================================================
// PART 1: NEXT BIRTHDAY CALCULATION
// =============================================================================

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Returns the next date from today (excluding today) on which the given user can celebrate their birthday.
 * 
 * Signature: Users, Date -> Date
 * 
 * Examples:
 * - nextBirthday(user born March 10, today March 15) -> March 10 next year
 * - nextBirthday(user born March 20, today March 15) -> March 20 this year
 * - nextBirthday(user born March 15, today March 15) -> March 15 next year
 * 
 * Design Strategy: Function Composition - Calculate this year's birthday, compare with today,
 *                  return this year's or next year's birthday accordingly.
 * 
 * Effects: Pure function with no side effects, returns new Date object.
 * 
 * @param users the user whose next birthday we want to find
 * @param today the current date
 * @return the next birthday date after today
 */
Date nextBirthday(Users users, Date today) {
	// DESIGN RECIPE STEP 4: Function Template
	// - Extract user's birth date: users.dateOfBirth()
	// - Get current year from today: GetYear(today)
	// - Calculate this year's birthday
	// - Compare with today to determine if we need this year or next year
	
	// DESIGN RECIPE STEP 5: Function Body
	Date birthday = users.dateOfBirth();
	int currentYear = GetYear(today);
	int birthYear = GetYear(users.dateOfBirth());
	int yearDiff = (currentYear - birthYear);
	
	// Calculate this year's version of the birthday
	Date thisYearBirth = AddYears(birthday, yearDiff);
	
	// Check if this year's birthday has already passed
	long daysBetween = DaysBetween(thisYearBirth, today);
	
	// If birthday has passed or is today, return next year's birthday
	// Otherwise return this year's birthday
	return (daysBetween <= 0) ? AddYears(birthday, yearDiff + 1) : thisYearBirth;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct calculation for birthdays in past, future, and edge cases

// =============================================================================
// PART 1: CURRENT AGE CALCULATION  
// =============================================================================

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Returns the current number of full years that have elapsed since the given user was born up to today.
 * 
 * Signature: Users, Date -> long
 * 
 * Examples:
 * - currentAge(user born 2000-03-10, today 2025-03-15) -> 25 (birthday has passed)
 * - currentAge(user born 2000-03-20, today 2025-03-15) -> 24 (birthday hasn't occurred yet)
 * - currentAge(user born 2000-03-15, today 2025-03-15) -> 25 (birthday is today)
 * 
 * Design Strategy: Function Composition - Calculate year difference, check if birthday has occurred
 *                  this year, adjust age accordingly.
 * 
 * Effects: Pure function with no side effects, returns primitive long value.
 * 
 * @param users the user whose age we want to calculate
 * @param today the current date
 * @return the user's age in full years
 */
long currentAge(Users users, Date today) {
	// DESIGN RECIPE STEP 4: Function Template
	// - Calculate year difference between today and birth year
	// - Check if this year's birthday has occurred yet
	// - Adjust age accordingly
	
	// DESIGN RECIPE STEP 5: Function Body
	Date birthday = users.dateOfBirth();
	int currentYear = GetYear(today);
	int birthYear = GetYear(users.dateOfBirth());
	int yearDiff = (currentYear - birthYear);
	
	// Calculate this year's version of the birthday
	Date thisYearBirth = AddYears(birthday, yearDiff);
	
	// Check if this year's birthday has already occurred
	long daysBetween = DaysBetween(thisYearBirth, today);
	
	// If birthday hasn't occurred yet this year, subtract 1 from age
	// The logic here seems incorrect - should be: daysBetween > 0 ? yearDiff : yearDiff - 1
	return (daysBetween <= 0) ? yearDiff : yearDiff - 1;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct age calculation for various birth dates

// =============================================================================
// PART 2: SALARY INCREASE CALCULATION
// =============================================================================

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Returns the next date from today (excluding today) at which the given user will get a salary increase,
 *          or Nothing if they will not get one. Rule: Academics get salary increases on birthdays in odd years,
 *          Professional staff get increases on birthdays in even years, Students don't get salary increases.
 * 
 * Signature: Users, Date -> Maybe<Date>
 * 
 * Examples:
 * - nextSalaryIncrease(academic born 2000, today 2025) -> Something(birthday 2025) [25 is odd]
 * - nextSalaryIncrease(professional staff born 2000, today 2024) -> Something(birthday 2024) [24 is even]
 * - nextSalaryIncrease(student, any date) -> Nothing
 * 
 * Design Strategy: Cases on Role - Handle each role type separately according to salary increase rules.
 * 
 * Effects: Pure function with no side effects, returns Maybe<Date> object.
 * 
 * @param users the user whose next salary increase we want to find
 * @param today the current date
 * @return Maybe containing the next salary increase date, or Nothing if no increase
 */
Maybe<Date> nextSalaryIncrease(Users users, Date today) {
	// DESIGN RECIPE STEP 4: Function Template
	// - Check user's role
	// - If student, return Nothing
	// - If academic or professional staff, calculate next applicable birthday
	// - Consider odd/even year rules
	
	// DESIGN RECIPE STEP 5: Function Body
	Date birthday = users.dateOfBirth();
	int currentYear = GetYear(today);
	int birthYear = GetYear(users.dateOfBirth());
	int age = (currentYear - birthYear);
	
	// Calculate this year's birthday
	Date thisYearBirth = AddYears(birthday, age);
	long daysBetween = DaysBetween(thisYearBirth, today);

	// Students don't get salary increases
	if (Equals(users.role(), Role.STUDENT)) {
		return new Nothing<Date>();
	}
	// Academics get increases on odd-year birthdays
	else if (Equals(users.role(), Role.ACADEMIC)) {
		if (age % 2 != 0) { // Current age is odd
			// If birthday hasn't passed, use this year; otherwise skip to next odd year
			return (daysBetween <= 0) ? new Something<Date>(AddYears(birthday, age + 2))
					: new Something<Date>(thisYearBirth);
		} else { // Current age is even, wait for next odd year
			return new Something<Date>(AddYears(birthday, age + 1));
		}
	} 
	// Professional staff get increases on even-year birthdays
	else {
		if (age % 2 == 0) { // Current age is even
			// If birthday hasn't passed, use this year; otherwise skip to next even year
			return (daysBetween <= 0) ? new Something<Date>(AddYears(birthday, age + 2))
					: new Something<Date>(thisYearBirth);
		} else { // Current age is odd, wait for next even year
			return new Something<Date>(AddYears(birthday, age + 1));
		}
	}
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct salary increase dates for each role and various scenarios

// =============================================================================
// PART 3: USER INFORMATION DISPLAY
// =============================================================================

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Provides information about the given user, including name, role (spelled out),
 *          age in full years, next birthday in d/m/yyyy format, and next salary increase date in d/m/yyyy format.
 * 
 * Signature: Users, Date -> String
 * 
 * Examples:
 * - toInfo(student "John", today) -> "Name: John\nRole: student\nAge: 22 years\nNext Birthday: 15/3/2026\nNext Salary Increase: No salary increases"
 * - toInfo(academic "Dr. Smith", today) -> "Name: Dr. Smith\nRole: academic\nAge: 49 years\nNext Birthday: 22/7/2025\nNext Salary Increase: 22/7/2025"
 * 
 * Design Strategy: Function Composition - Use helper functions to calculate age, birthday, and salary increase,
 *                  then format all information into a readable string.
 * 
 * Effects: Pure function with no side effects, returns formatted string.
 * 
 * @param users the user whose information to display
 * @param today the current date for calculations
 * @return formatted string containing user information
 */
String toInfo(Users users, Date today) {
	// DESIGN RECIPE STEP 4: Function Template
	// - Extract user's name
	// - Convert role enum to readable string
	// - Calculate age using currentAge function
	// - Calculate next birthday using nextBirthday function
	// - Calculate next salary increase using nextSalaryIncrease function
	// - Format all information appropriately
	
	// DESIGN RECIPE STEP 5: Function Body
	String name = users.name();
	
	// Convert role to readable format
	String roleStr = switch(users.role()) {
		case STUDENT -> "student";
		case ACADEMIC -> "academic"; 
		case PROFESSIONAL_STAFF -> "professional staff";
	};
	
	// Calculate user information
	long age = currentAge(users, today);
	Date nextBirthday = nextBirthday(users, today);
	Maybe<Date> salaryIncrease = nextSalaryIncrease(users, today);
	
	// Format dates as d/m/yyyy
	String birthdayStr = FormatDate(nextBirthday, "d/M/yyyy");
	String salaryStr = switch(salaryIncrease) {
		case Nothing<Date> n -> "No salary increases";
		case Something<Date> s -> FormatDate(s.value(), "d/M/yyyy");
	};
	
	// Build result string
	StringBuilder result = new StringBuilder();
	result.append("Name: ").append(name).append("\n");
	result.append("Role: ").append(roleStr).append("\n"); 
	result.append("Age: ").append(age).append(" years\n");
	result.append("Next Birthday: ").append(birthdayStr).append("\n");
	result.append("Next Salary Increase: ").append(salaryStr);
	
	return result.toString();
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct formatting and information display

// =============================================================================
// PART 3: MAIN FUNCTION - INTERACTIVE INTERFACE
// =============================================================================

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Main function that provides an interactive interface for calculating user information.
 *          Prompts user for name, role, and birth date, then displays calculated information.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - User enters "John", "S", "2000", "3", "15" -> displays student information
 * - User enters "Dr. Smith", "A", "1975", "7", "22" -> displays academic information
 * 
 * Design Strategy: Function Composition - Prompt for input, validate data, create Users object,
 *                  then display information using toInfo function.
 * 
 * Effects: Reads from console input, writes to console output, creates temporary objects.
 */
void main(){
	// DESIGN RECIPE STEP 4: Function Template
	// - Prompt for user input (name, role, birth year/month/day)
	// - Validate and convert role input
	// - Create Date object from birth information
	// - Create Users object
	// - Display information using toInfo function
	
	// DESIGN RECIPE STEP 5: Function Body
	// Prompt for user input
	String name = readln("User name: ");
	String birthYear = readln("Birth Year: ");
	String birthMonth = readln("Birth Month: ");
	String birthDay = readln("Birth Day: "); // Fixed typo: was "Birth Year" 
	String roleString = readln("User Role (S/A/P): ");

	// Convert role string to Role enum
	Role role;
	if (Equals(roleString, "S")) {
		role = Role.STUDENT;
	} else if (Equals(roleString, "A")) {
		role = Role.ACADEMIC;
	} else {
		role = Role.PROFESSIONAL_STAFF;
	}
	
	// Create date of birth from input
	Date dateOfBirth = ParseDate(birthDay + "/" + birthMonth + "/" + birthYear, "dd/MM/yyyy");
	
	// Create user and display information
	Users user = new Users(name, role, dateOfBirth);
	println(toInfo(user, CurrentDate()));
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct user interaction and output formatting