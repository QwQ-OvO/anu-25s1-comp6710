import comp1110.lib.*;

import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;
import comp1110.universe.*;
import static comp1110.universe.Colour.*;
import static comp1110.universe.Image.*;
import static comp1110.universe.Universe.*;

/**
 * Defines alarm behavior types
 * 
 * Examples:
 * given: RECURRING alarm at tick 5, cycle 10
 *  expect: Triggers at 5,15,25... (repeats)
 * 
 * given: ONE_TIME alarm at tick 3, cycle 10
 *  expect: Triggers at 3 only (auto-removes)
 * 
 * Design Strategy: Simple Enumeration
 * - Two constant cases without data
 * - Distinguishes repeating vs single trigger
 */
enum AlarmType{
    
    /**
     * Triggers repeatedly each cycle
     * Persists in system after triggering
     */
    RECURRING,
    
    /**
     * Triggers once then removes itself
     * Auto-deletes after single trigger
     */  
    ONE_TIME
}

/**
 * Record representing an alarm's configuration
 * Combines function with simple data fields
 * 
 * Examples:
 * given: new Alarm(5, t->"Wake!", RECURRING)
 *  expect: Recurring alarm at tick 5 displaying "Wake!"
 * 
 * given: new Alarm(3, t->"Meeting "+t, ONE_TIME) 
 *  expect: One-time alarm at tick 3 displaying "Meeting 3"
 * 
 * Design Strategy: Simple Record with Function Combination
 * - Record for immutable data structure
 * - Function field for alarm behavior
 * - Simple method applying function
 * 
 * @param triggerTime When alarm triggers (0 <= time < cycle)
 * @param alarmFunction Produces message from current tick
 * @param alarmType RECURRING or ONE_TIME
 */
record Alarm(int triggerTime, Function<Integer, String> alarmFunction, AlarmType alarmType) {
    /**
     * Prints alarm message by applying function
     * 
     * Design Strategy: Simple Expression
     * - Direct function application
     */
    void print(){
        println(this.alarmFunction.apply(this.triggerTime));
    }
}

/**
 * Record representing complete alarm system state
 * Combines tick counter, cycle size and alarm list
 * 
 * Examples:
 * given: new AlarmClock(3, 10, [(5->alarm1)])
 *  expect: System at tick 3, cycle 10, one alarm at 5
 * 
 * given: new AlarmClock(0, 5, [])
 *  expect: Empty system at tick 0, cycle 5
 * 
 * Design Strategy: Complex Record with Template Application  
 * - Record for immutable state
 * - ConsList requiring template processing
 * - Simple fields with complex structure
 * 
 * @param currentTick Current system tick (0 <= tick < cycle)
 * @param cycleSize System cycle length (positive)
 * @param alarms ConsList of (time->alarm) pairs
 */
record AlarmClock(int currentTick, int cycleSize, ConsList<Pair<Integer, Alarm>> alarms) {}

/**
 * Creates new alarm system with initial state
 * Tick counter starts at 0 and cycles before cycleSize
 * 
 * Examples:
 * given: makeAlarm(5)
 *  expect: Clock(0,5,[]) // Empty system, cycle 5
 * 
 * given: makeAlarm(24)
 *  expect: Clock(0,24,[]) // Empty system, cycle 24
 * 
 * Design Strategy: Simple Expression
 * - Direct record construction
 * - No complex processing
 * 
 * @param cycleSize System cycle length (must be positive)
 * @return New empty alarm system
 * @throws IllegalArgumentException if cycleSize <= 0
 */
AlarmClock makeAlarm(int cycleSize) {
    return new AlarmClock(0, cycleSize, new Nil<Pair<Integer, Alarm>>());
}

/**
 * Registers a recurring alarm that triggers repeatedly at specified tick time
 * 
 * Examples:
 * given: registerRecurringAlarm("Wake" at 4, Clock(2,5,[]))
 *  expect: Clock(2,5,[4->"Wake"])  // Adds new recurring alarm
 * 
 * given: registerRecurringAlarm("New" at 3, Clock(2,5,[3->"Old"]))
 *  expect: Clock(2,5,[3->"New"])   // Replaces existing alarm
 * 
 * given: registerRecurringAlarm("Invalid" at 5, Clock(2,5,[]))
 *  expect: IllegalArgumentException // Time must be < cycleSize
 * 
 * Design Strategy: Combining Functions
 * 1. Create new Alarm with RECURRING type
 * 2. Use Put to add/update alarm in list
 * 3. Construct new AlarmClock with updated list
 * Available functions:
 * - Alarm constructor 
 * - Put for ConsList
 * - AlarmClock constructor
 * 
 * @param alarm Function that produces message string from tick
 * @param time Target tick time (must be 0 <= time < cycleSize)
 * @param clock Current alarm clock state
 * @return New clock with recurring alarm added/updated
 * @throws IllegalArgumentException if time invalid
 */
AlarmClock registerRecurringAlarm(Function<Integer, String> alarm, int time, AlarmClock clock) {
   
   // Create a new pair of (time, alarm)
   Alarm newAlarm = new Alarm(time, alarm, AlarmType.RECURRING);
   
   // use map to add the new alarm to the list
   // this will automatically override the existing alarm at the same time
    Put(clock.alarms(), time, newAlarm);
    
    // same as ConsList<Pair<Integer, Alarm>> newRecurringAlarms = new Cons(newAlarmPair, clock.alarms());
    // use map to add the new alarm to the list
    return new AlarmClock(clock.currentTick(), clock.cycleSize(), Put(clock.alarms(), time, newAlarm));
}

/**
 * Registers a one-time alarm that triggers once then removes itself
 * 
 * Examples:
 * given: registerOneTimeAlarm("Meeting" at 3, Clock(1,5,[]))
 *  expect: Clock(1,5,[3->"Meeting"])  // Adds new one-time alarm
 * 
 * given: registerOneTimeAlarm("Special" at 2, Clock(1,5,[2->"Daily"]))
 *  expect: Clock(1,5,[2->"Special"])  // Replaces existing alarm
 * 
 * given: registerOneTimeAlarm("Late" at -1, Clock(1,5,[]))
 *  expect: IllegalArgumentException   // Time must be non-negative
 * 
 * Design Strategy: Combining Functions
 * 1. Create new Alarm with ONE_TIME type
 * 2. Use Put to add/update alarm in list 
 * 3. Construct new AlarmClock with updated list
 * Available functions:
 * - Alarm constructor
 * - Put for ConsList
 * - AlarmClock constructor
 * 
 * @param alarm Function that produces message string from tick
 * @param time Target tick time (must be 0 <= time < cycleSize)
 * @param clock Current alarm clock state
 * @return New clock with one-time alarm added/updated
 * @throws IllegalArgumentException if time invalid
 */
AlarmClock registerOneTimeAlarm(Function<Integer, String> alarm, int time, AlarmClock clock) {
    Alarm newAlarm = new Alarm(time, alarm, AlarmType.ONE_TIME);
    
    // use map to add the new alarm to the list
    // automatically override the existing alarm at the same time
    Put(clock.alarms(), time, newAlarm);
    
    // same as ConsList<Pair<Integer, Alarm>> newRecurringAlarms = new Cons(newAlarmPair, clock.alarms());
    // use map to add the new alarm to the list
    return new AlarmClock(clock.currentTick(), clock.cycleSize(), Put(clock.alarms(), time, newAlarm));
}

/**
 * Advances the clock by one tick and processes any triggered alarms
 * 
 * Examples:
 * given: AlarmClock(tick=2, cycle=5, recurring alarm at 3 -> "Wake")
 *  expect: Pair(Clock(3, 5, [3->"Wake"]), Nothing)
 *         Next tick, no alarm triggered
 * 
 * given: AlarmClock(tick=3, cycle=5, one-time alarm at 4 -> "Meeting") 
 *  expect: Pair(Clock(4, 5, []), Something("Meeting"))
 *         Next tick, alarm triggered and removed
 * 
 * given: AlarmClock(tick=4, cycle=5, recurring alarm at 0 -> "Daily")
 *  expect: Pair(Clock(0, 5, [0->"Daily"]), Something("Daily"))
 *         Wraps to 0, recurring alarm triggered but kept
 * 
 * Design Strategy: Apply Template - ConsList & Maybe
 * - Handles clock cycle wraparound
 * - Processes alarms at new tick time
 * - Distinguishes recurring vs one-time alarms
 * 
 * @param clock Current alarm clock state
 * @return Pair containing:
 *         - First: Updated clock state
 *         - Second: Triggered alarm message (if any)
 */
Pair<AlarmClock, Maybe<String>> tick(AlarmClock clock) {
    int newClockTick = (clock.currentTick() + 1) % clock.cycleSize();

    return switch (clock.alarms()) {
        
        // No alarms case
        case Nil() -> new Pair<>(
            new AlarmClock(newClockTick, clock.cycleSize(), clock.alarms()), 
            new Nothing<>());

        // Check for triggered alarms
        case Cons(var first, var rest) -> processTriggeredAlarms(
            newClockTick, 
            clock, 
            Get(clock.alarms(), newClockTick));
    };
}

/**
 * Processes triggered alarms at the new tick time
 * 
 * Examples:
 * given: tick=3, Clock(2,5,[3->"Wake"]), Something(Alarm("Wake",Recurring))
 *  expect: Pair(Clock(3,5,[3->"Wake"]), Something("Wake"))
 *         Recurring alarm triggered and maintained
 * 
 * given: tick=4, Clock(3,5,[4->"Once"]), Something(Alarm("Once",OneTime))
 *  expect: Pair(Clock(4,5,[]), Something("Once"))
 *         One-time alarm triggered and removed
 * 
 * Design Strategy: Case Distinction - Maybe & AlarmType
 * - Handles both presence/absence of alarms
 * - Distinguishes alarm types
 * - Maintains alarm list appropriately
 * 
 * @param newTick The advanced tick value
 * @param clock Current clock state
 * @param maybeAlarm Possible alarm at new tick
 * @return Updated clock state and any triggered message
 */
Pair<AlarmClock, Maybe<String>> processTriggeredAlarms(
    int newTick, 
    AlarmClock clock, 
    Maybe<Alarm> maybeAlarm) {
        
    return switch (maybeAlarm) {
        // No alarm at this tick
        case Nothing() -> new Pair<>(
            new AlarmClock(newTick, clock.cycleSize(), clock.alarms()),
            new Nothing<>());
            
        // Process triggered alarm
        case Something(var alarm) -> handleAlarmType(newTick, clock, alarm);
    };
}

/**
 * Handles specific alarm type processing
 * 
 * Examples:
 * given: tick=3, Clock(2,5,[3->"Daily"]), RecurringAlarm("Daily")
 *  expect: Pair(Clock(3,5,[3->"Daily"]), Something("Daily"))
 *         Keeps recurring alarm
 * 
 * given: tick=4, Clock(3,5,[4->"Single"]), OneTimeAlarm("Single")
 *  expect: Pair(Clock(4,5,[]), Something("Single"))
 *         Removes one-time alarm
 * 
 * Design Strategy: Case Distinction - AlarmType
 * - Handles recurring alarms (maintain in list)
 * - Handles one-time alarms (remove after trigger)
 * - Generates appropriate alarm messages
 * 
 * @param newTick Advanced tick value
 * @param clock Current clock state
 * @param alarm Triggered alarm to process
 * @return Updated clock state and triggered message
 */
Pair<AlarmClock, Maybe<String>> handleAlarmType(
    int newTick, 
    AlarmClock clock, 
    Alarm alarm) {
        
    String message = alarm.alarmFunction().apply(newTick);
    
    if (alarm.alarmType() == AlarmType.RECURRING) {
        // Keep recurring alarm
        return new Pair<>(
            new AlarmClock(newTick, clock.cycleSize(), clock.alarms()),
            new Something<>(message));
    } else {
        // Remove one-time alarm
        return new Pair<>(
            new AlarmClock(newTick, clock.cycleSize(), 
                          Remove(clock.alarms(), newTick)),
            new Something<>(message));
    }
}

/**
 * Calculates the number of ticks until the next alarm triggers in the clock system
 * This is the main entry point for alarm timing calculations
 * 
 * Examples:
 * given: AlarmClock(tick=2, cycle=10, alarms=[3,7])
 *  expect: 1 (next alarm at tick 3, closest future alarm)
 * given: AlarmClock(tick=8, cycle=10, alarms=[2,3]) 
 *  expect: 4 (wraps to tick 2 in next cycle, accounts for cycle wraparound)
 * given: AlarmClock(tick=5, cycle=10, alarms=[])
 *  expect: 0 (no alarms exist in the system)
 * 
 * Design Strategy: Apply Template - ConsList
 * - Uses ConsList template to traverse alarm list
 * - Delegates detailed processing to helper functions
 * - Maintains clock cycle awareness
 * 
 * @param clock Current state of the alarm clock, containing tick count and alarms
 * @return Number of ticks until the next alarm triggers, or 0 if no alarms exist
 */
int ticksUntilNextAlarm(AlarmClock clock) {
    int currentTick = clock.currentTick();
    int minAlarm = clock.cycleSize(); // Initialize to maximum possible value
    
    return switch (clock.alarms()) {
        case Nil() -> 0; // No alarms case
        case Cons(var first, var rest) -> processAlarm(first, rest, clock, minAlarm);
    };
}

/**
 * Processes a single alarm and recursively checks remaining alarms
 * Central logic for determining next alarm timing
 * 
 * Examples:
 * given: first=(3,"Wake"), rest=empty, clock(tick=2), minAlarm=10
 *  expect: 1 (3-2, immediate next alarm)
 * given: first=(2,"Past"), rest=[(7,"Next")], clock(tick=5), minAlarm=10
 *  expect: 2 (7-5, skips past alarm, finds next valid alarm)
 * given: first=(8,"Late"), rest=[(2,"Early")], clock(tick=5), minAlarm=10
 *  expect: 3 (8-5, compares multiple future alarms)
 * 
 * Design Strategy: Case Distinction
 * - Separates expired and future alarm handling
 * - Maintains minimum tick calculation
 * - Ensures proper alarm removal and recursion
 * 
 * @param first Current alarm pair being examined (tick, alarm)
 * @param rest Remaining alarms in the list to be processed
 * @param clock Current clock state including tick count
 * @param minAlarm Current minimum ticks found to next alarm
 * @return Updated minimum number of ticks until next alarm
 */
int processAlarm(Pair<Integer, Alarm> first, ConsList<Pair<Integer, Alarm>> rest, 
                AlarmClock clock, int minAlarm) {
    if (first.first() <= clock.currentTick()) {
        return handleExpiredAlarm(rest, clock);
    } else {
        return handleFutureAlarm(first, rest, clock, minAlarm);
    }
}

/**
 * Handles an expired alarm by removing it and checking remaining alarms
 * Ensures proper cleanup of past alarms while maintaining system state
 * 
 * Examples:
 * given: rest=empty, clock(tick=5)
 *  expect: 0 (no more alarms to check)
 * given: rest=[(7,"Next"),(10,"Later")], clock(tick=5)
 *  expect: 2 (continues checking remaining alarms)
 * 
 * Design Strategy: Case Distinction
 * - Handles empty and non-empty remaining alarm lists
 * - Ensures proper recursion for remaining alarms
 * 
 * @param rest Remaining alarms to be checked after removing expired alarm
 * @param clock Current clock state for timing calculations
 * @return Minimum ticks to next valid alarm, or 0 if none remain
 */
int handleExpiredAlarm(ConsList<Pair<Integer, Alarm>> rest, AlarmClock clock) {
    if (IsEmpty(rest)) {
        return 0; // No more alarms to check
    }
    return ticksUntilNextAlarm(new AlarmClock(clock.currentTick(), clock.cycleSize(), rest));
}

/**
 * Handles a future alarm by comparing it with remaining alarms
 * Core logic for finding the nearest future alarm
 * 
 * Examples:
 * given: first=(7,"Next"), rest=empty, clock(tick=5), minAlarm=10
 *  expect: 2 (7-5, single future alarm)
 * given: first=(8,"Later"), rest=[(6,"Sooner")], clock(tick=5), minAlarm=10
 *  expect: 1 (6-5, finds minimum between multiple future alarms)
 * 
 * Design Strategy: Case Distinction
 * - Calculates ticks to current alarm
 * - Compares with remaining alarms
 * - Maintains minimum tick count
 * - Handles both single and multiple future alarms
 * 
 * @param first Current future alarm being examined
 * @param rest Additional alarms to compare against
 * @param clock Current clock state
 * @param minAlarm Current minimum ticks found
 * @return Minimum ticks to nearest future alarm
 */
int handleFutureAlarm(Pair<Integer, Alarm> first, ConsList<Pair<Integer, Alarm>> rest, 
                      AlarmClock clock, int minAlarm) {

    // Calculate ticks to current alarm
    int ticksToThis = first.first() - clock.currentTick();
    if (IsEmpty(rest)) {
        // Compare with current minimum
        return Min(minAlarm, ticksToThis);
    }
    
    // Calculate and compare with remaining alarms
    int ticksToOthers = ticksUntilNextAlarm(new AlarmClock(clock.currentTick(), clock.cycleSize(), rest));
    // Return overall minimum
    return Min(ticksToThis, ticksToOthers);
}

/**
 * Calculates the next alarm tick by examining all registered alarms
 * 
 * Examples:
 * given: Clock(tick=5, cycle=10), alarms=[(7,"Wake")], minTick=Integer.MAX_VALUE
 *  expect: 2 (difference between next alarm at 7 and current tick 5)
 * given: Clock(tick=8, cycle=10), alarms=[(7,"Past"),(12,"Next")], minTick=Integer.MAX_VALUE
 *  expect: 4 (difference between next alarm at 12 and current tick 8)
 * 
 * Design Strategy: Apply Template - ConsList
 * 
 * @param clock Current state of the alarm clock
 * @param minTick Current minimum ticks until next alarm (initially Integer.MAX_VALUE)
 * @return Minimum number of ticks until the next alarm trigger
 */
int getNextTick(AlarmClock clock, int minTick){
    return switch (clock.alarms()){
        case Nil() -> minTick;
        case Cons(var first, var rest) -> helper(first, rest, clock, minTick);
    };
}

/**
 * Helper function to process each alarm in the list and find the closest upcoming alarm
 * 
 * Examples:
 * given: first=(7,"Wake"), rest=empty, clock(tick=5), minTick=MAX
 *  expect: 2 (7-5)
 * given: first=(3,"Past"), rest=[(7,"Next")], clock(tick=5), minTick=MAX
 *  expect: 2 (7-5, ignoring past alarm at 3)
 * 
 * Design Strategy: Case Distinction
 * 
 * @param first Current alarm pair being examined (tick, alarm)
 * @param rest Remaining alarms in the list
 * @param clock Current state of the alarm clock
 * @param minTick Current minimum ticks until next alarm
 * @return Updated minimum ticks until next alarm
 */
int helper(Pair<Integer, Alarm> first, ConsList<Pair<Integer, Alarm>> rest, AlarmClock clock, int minTick){
    
    // For past alarms, skip and check rest of list
    if (first.first() <= clock.currentTick()){
        return getNextTick(new AlarmClock(clock.currentTick(), clock.cycleSize(), rest), minTick);
        } else{
            // For future alarms, update minTick if this alarm is sooner
            int diff = first.first() - clock.currentTick();
            if (diff < minTick){
                minTick = diff;
            }
            return getNextTick(new AlarmClock(clock.currentTick(), clock.cycleSize(), rest), minTick);
            }
}

/**
 * Design Strategy: Simple Expression
 * Reason:
 * - Direct accessor method
 * - No computation or state modification needed
 * 
 * Returns the current tick value of the given alarm clock.
 * The tick value represents the current position in the clock cycle,
 * where 0 <= tick < cycleSize.
 * 
 * Examples:
 * - currentTick(AlarmClock(5, 10, empty list))
 *   Returns: 5
 *
 * @param clock - The alarm clock whose current tick value is requested
 * @return The current tick value of the clock
 * @throws IllegalArgumentException if clock is null
 */
int currentTick(AlarmClock clock){
    return clock.currentTick();
}

/**
 * Represents the possible states of an alarm clock
 * CLOCK_RUN: Clock is running normally
 * CLOCK_PAUSE: Clock is paused/stopped
 */
enum AlarmClockState{
    CLOCK_RUN,
    CLOCK_PAUSE
}

/**
 * A record representing the complete state of the clock world
 * 
 * @param clock The alarm clock object that keeps track of time
 * @param clockState The current state of the clock (running or paused)
 * @param fontStyle The style used for displaying text (from Universe.FontStyle)
 * @param worldAlarmTick The tick count at which the alarm should go off
 * @param lastAlarmTick The tick count when the last alarm occurred
 * @param alarmNote The message to display
 */
record ClockWorld(AlarmClock clock, AlarmClockState clockState, 
                 FontStyle fontStyle, int worldAlarmTick, int lastAlarmTick, String alarmNote){}

/** Width of the background in pixels */
int BACK_WIDTH = 500;
/** Height of the background in pixels */
int BACK_HEIGHT = 500;

/**
 * Draws the current state of the clock world
 * 
 * Examples:
 * given: ClockWorld with currentTick=5, cycleSize=10, no alarm
 * expect: Black background with "5 / 10 s" in white text centered at top
 * given: ClockWorld with currentTick=3, cycleSize=10, alarmNote="Wake up!"
 * expect: Black background with "3 / 10 s" and "Wake up!" in white text
 * 
 * Design Strategy: Combining Functions
 * 
 * @param world Current state of the clock world
 * @return An image representing the current clock state
 */
Image draw(ClockWorld world){ 
    // Create black background
    Image back = Rectangle(BACK_WIDTH, BACK_HEIGHT, BLACK);

    // Convert current tick and cycle size to strings
    String clockTime = ToString(world.clock().currentTick());
    String cycle = ToString(world.clock().cycleSize());

    // Create and position the time display text
    Image timeNote = Text(" " + clockTime + " / " + cycle + " s ", 
                          100, // font size
                          -1, // no max width
                          WHITE, // text color
                          "Arial", // font name
                          FontStyle.PLAIN); //font style
    back = PlaceXY(back, timeNote, BACK_WIDTH / 2, 120);
    
    // If there's an alarm note, display it
    if (!Equals(world.alarmNote(), "")){
        Image alarmNote = Text(world.alarmNote(), 
                               70, // font size
                               -1, // no max width
                               WHITE, // text color
                               "Arial", // font name
                               FontStyle.BOLD); //font style
        back = PlaceXY(back, alarmNote, BACK_WIDTH / 2, 280);
    }
    return back;
}

/**
 * Creates and returns the initial state of the clock world
 * 
 * Example:
 * expect: A new ClockWorld with:
 *         - A 10-cycle alarm clock
 *         - Running state
 *         - Plain font style
 *         - No alarm set (worldAlarmTick = 0)
 *         - No previous alarm (lastAlarmTick = 0)
 *         - Empty alarm note
 * 
 * Design Strategy: Simple Expression
 * 
 * @return The initial ClockWorld state
 */
ClockWorld getInitialState(){
    return new ClockWorld(makeAlarm(10), 
                          AlarmClockState.CLOCK_RUN, 
                          FontStyle.PLAIN, 
                          0, // worldAlarmTick 
                          0, // lastAlarmTick
                          ""); // alarm note
}

/**
 * Retrieves the AlarmClock object from a given ClockWorld
 * 
 * Example:
 * given: ClockWorld with clock c
 * expect: c
 * 
 * Design Strategy: Simple Expression
 * 
 * @param world The ClockWorld to get the clock from
 * @return The AlarmClock from the given world
 */
AlarmClock getClock(ClockWorld world){
    return world.clock();
}

/**
 * Updates the world state for each clock tick
 * 
 * Examples:
 * given: ClockWorld with CLOCK_RUN state, tick=29
 *  expect: ClockWorld with tick=30, potentially updated alarm state
 * given: ClockWorld with CLOCK_PAUSE state
 *  expect: Same world state unchanged
 * 
 * Design Strategy: Case Distinction
 * 
 * @param world Current state of the clock world
 * @return Updated clock world state
 */
ClockWorld step(ClockWorld world){
    AlarmClock clock = world.clock();
    if (world.clockState() == AlarmClockState.CLOCK_RUN){
        int newWorldAlarmTick = world.worldAlarmTick() + 1;
        if (newWorldAlarmTick % 30 == 0){ // Every 30 ticks = 1 second
            Pair<AlarmClock, Maybe<String>> tickResult = tick(clock);
            clock = tickResult.first();
            return handleTickResult(tickResult.second(), clock, world, newWorldAlarmTick);
        }
        return new ClockWorld(clock, world.clockState(), FontStyle.PLAIN, 
            newWorldAlarmTick, world.lastAlarmTick(), world.alarmNote());
    }
    return world;
}

/**
 * Handles the result of a clock tick, which may or may not produce an alarm
 * 
 * Examples:
 * given: Nothing, clock at 5, newTick=30
 *  expect: Updated ClockWorld with potential message clear
 * given: Something("Wake up!"), clock at 5, newTick=30
 *  expect: ClockWorld with new alarm message "Wake up!"
 * 
 * Design Strategy: Apply Template - Maybe
 * 
 * @param maybeResult Possible alarm message from tick
 * @param clock Current alarm clock
 * @param world Current world state
 * @param newWorldAlarmTick Updated tick count
 * @return Updated clock world state
 */
ClockWorld handleTickResult(Maybe<String> maybeResult, 
                           AlarmClock clock, ClockWorld world, int newWorldAlarmTick) {
    return switch(maybeResult) {
        case Nothing<String>() -> handleNothingCase(clock, world, newWorldAlarmTick);
        case Something<String>(var element) -> 
            new ClockWorld(clock, world.clockState(), FontStyle.PLAIN, 
                newWorldAlarmTick, newWorldAlarmTick, element);
    };
}

/**
 * Handles the case when no alarm was triggered
 * May clear existing alarm message if enough time has passed
 * 
 * Examples:
 * given: clock, world with lastAlarmTick=0, newTick=160
 *  expect: ClockWorld with cleared alarm message
 * given: clock, world with lastAlarmTick=0, newTick=140
 *  expect: ClockWorld with existing alarm message preserved
 * 
 * Design Strategy: Case Distinction
 * 
 * @param clock Current alarm clock
 * @param world Current world state  
 * @param newWorldAlarmTick Updated tick count
 * @return Updated clock world state
 */
ClockWorld handleNothingCase(AlarmClock clock, ClockWorld world, int newWorldAlarmTick) {
    if (newWorldAlarmTick - world.lastAlarmTick() > 150){  // Clear message after 5 seconds
        return new ClockWorld(clock, world.clockState(), FontStyle.PLAIN, 
            newWorldAlarmTick, world.lastAlarmTick(), "");
    } else {
        return new ClockWorld(clock, world.clockState(), FontStyle.PLAIN, 
            newWorldAlarmTick, world.lastAlarmTick(), world.alarmNote());
    }
}

/**
 * Handles keyboard events for the clock world
 * Currently only handles space bar to toggle clock state between run and pause
 * 
 * Examples:
 * given: ClockWorld(CLOCK_RUN), KEY_PRESSED "Space" event
 *  expect: ClockWorld with state changed to CLOCK_PAUSE
 * given: ClockWorld(CLOCK_PAUSE), KEY_PRESSED "Space" event  
 *  expect: ClockWorld with state changed to CLOCK_RUN
 * given: ClockWorld, KEY_RELEASED event
 *  expect: Original world unchanged
 * given: ClockWorld, KEY_PRESSED "A" event
 *  expect: Original world unchanged
 * 
 * Design Strategy: Case Distinction
 * 
 * @param world Current state of the clock world
 * @param keyEvent Keyboard event to process
 * @return Updated clock world state based on keyboard input
 */
ClockWorld keyEvent(ClockWorld world, KeyEvent keyEvent){
    // Only process key press events, ignore releases and types
    if (!Equals(keyEvent.kind(), KeyEventKind.KEY_PRESSED)){
        return world;
    }
    // Space bar toggles between run and pause states
    if (Equals(keyEvent.key(), "Space")){ // Keep same clock
        return new ClockWorld(world.clock(), world.clockState() == AlarmClockState.CLOCK_RUN ? // Toggle state
            AlarmClockState.CLOCK_PAUSE : AlarmClockState.CLOCK_RUN, FontStyle.PLAIN, world.worldAlarmTick(), world.lastAlarmTick(), world.alarmNote());
    }
    return world;
}

/**
 * The entry point of the alarm clock program.
 * 
 * This program creates an interactive alarm clock that:
 * - Updates every 30 ticks (1 second in real time)
 * - Allows pausing/resuming with space bar
 * - Can display alarm messages when triggered
 * - Supports one-time and recurring alarms
 * 
 * Required command line arguments:
 * args[0]: cycle size (integer) - Total ticks in one cycle
 * args[1]: alarm time (integer) - When the alarm should trigger
 * args[2]: alarm type ("R" for recurring, other for one-time)
 * args[3]: alarm message (string) - Message to display when alarm triggers
 * 
 * Examples:
 * java --enable-preview ClockAlarm.java 60 10 R "Wake up!"
 *   Creates clock with 60-tick cycle, recurring alarm at tick 10
 * java --enable-preview ClockAlarm.java 30 15 O "Meeting time!"
 *   Creates clock with 30-tick cycle, one-time alarm at tick 15
 * 
 * BigBang parameters:
 * - Window title: "Alarm Clock"
 * - Initial state: Running clock with no alarm triggered
 * - Draw: Renders current time and any alarm messages
 * - Step: Updates clock state every tick
 * - KeyEvent: Handles space bar for pause/resume
 * 
 * @param args Command line arguments [cycle, alarmTime, type, message]
 */
void main(String[] args){ 
    CheckVersion("2025S1-7");
    
    // Parse command line arguments
    String cycleString = args[0];
    Integer cycle = Integer.parseInt(cycleString);
    Integer alarmTime = Integer.parseInt(args[1]);
    AlarmType alarmType = args[2].equals("Y") ? AlarmType.RECURRING : AlarmType.ONE_TIME;
    String alarmNote = args[3];
    
    // Initialize clock and register alarm
    AlarmClock clock = makeAlarm(cycle);
    if (alarmType == AlarmType.RECURRING){
        clock = registerRecurringAlarm((i) -> alarmNote, alarmTime, clock);
    } else{
        clock = registerOneTimeAlarm((i) -> alarmNote, alarmTime, clock);
    }
    
    // Start the interactive world program
    // Example: java --enable-preview ClockAlarm.java 60 10 Y Alarm
    BigBang("Alarm Clock", new ClockWorld(clock, AlarmClockState.CLOCK_RUN, FontStyle.PLAIN, 0, 0, ""), this::draw, this::step, this::keyEvent);
}

/**
 * Simple test for basic tick advancement
 * Purpose: Verify tick increments from 0 to 1
 */
void testBasicTick() {
    // Setup initial clock at tick 0, cycle 5, no alarms
    AlarmClock clock = makeAlarm(5); // Creates clock at tick 0
    
    // Tick once
    Pair<AlarmClock, Maybe<String>> result = tick(clock);
    
    // Verify tick advanced to 1
    testEqual(1, result.first().currentTick());
}

void test()
{
    runAsTest(this::testBasicTick);
}