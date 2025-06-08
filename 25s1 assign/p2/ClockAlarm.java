/** Import packages from course website. */
import comp1110.lib.*;

import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;
import comp1110.universe.*;
import static comp1110.universe.Colour.*;
import static comp1110.universe.Image.*;
import static comp1110.universe.Universe.*;

// =============================================================================
// DATA DEFINITIONS
// =============================================================================

/**
 * Purpose: Represents the different behaviors an alarm can have in the system.
 * 
 * Signature: Enumeration with two values: RECURRING, ONE_TIME.
 * 
 * Examples:
 * - AlarmType.RECURRING: Triggers repeatedly each cycle, persists after triggering
 * - AlarmType.ONE_TIME: Triggers once then removes itself automatically
 * 
 * Design Strategy: Simple Expression - Enumeration to represent distinct alarm behaviors.
 * 
 * Effects: Immutable enumeration with no side effects.
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
 * Purpose: Represents an alarm's configuration with trigger time, message function, and behavior type.
 * 
 * Signature: Record containing int, Function<Integer, String>, and AlarmType fields.
 * 
 * Examples:
 * - new Alarm(5, t->"Wake!", RECURRING) -> recurring alarm at tick 5 displaying "Wake!"
 * - new Alarm(3, t->"Meeting "+t, ONE_TIME) -> one-time alarm at tick 3 displaying "Meeting 3"
 * 
 * Design Strategy: Simple Expression - Immutable record combining data with function.
 * 
 * Effects: Creates immutable alarm object with no side effects.
 * 
 * @param triggerTime When alarm triggers (0 <= time < cycle)
 * @param alarmFunction Produces message from current tick
 * @param alarmType RECURRING or ONE_TIME
 */
record Alarm(int triggerTime, Function<Integer, String> alarmFunction, AlarmType alarmType) {
    /**
     * Purpose: Prints the alarm message by applying the alarm function to trigger time.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - alarm with function t->"Wake!" -> prints "Wake!"
     * - alarm with function t->"Time: "+t -> prints "Time: 5" if triggerTime is 5
     * 
     * Design Strategy: Simple Expression - Direct function application.
     * 
     * Effects: Prints message to console output.
     */
    void print(){
        println(this.alarmFunction.apply(this.triggerTime));
    }
}

/**
 * Purpose: Represents complete alarm system state with current tick, cycle size, and alarm list.
 * 
 * Signature: Record containing int, int, and ConsList<Pair<Integer, Alarm>> fields.
 * 
 * Examples:
 * - new AlarmClock(3, 10, [(5->alarm1)]) -> system at tick 3, cycle 10, one alarm at tick 5
 * - new AlarmClock(0, 24, []) -> empty system at tick 0, 24-hour cycle
 * 
 * Design Strategy: Simple Expression - Immutable record combining timing and alarm data.
 * 
 * Effects: Creates immutable alarm clock state with no side effects.
 * 
 * @param currentTick Current system tick (0 <= tick < cycle)
 * @param cycleSize System cycle length (positive integer)
 * @param alarms ConsList of (time->alarm) pairs
 */
record AlarmClock(int currentTick, int cycleSize, ConsList<Pair<Integer, Alarm>> alarms) {}

// =============================================================================
// ALARM SYSTEM CREATION AND REGISTRATION
// =============================================================================

/**
 * Purpose: Creates a new empty alarm system with specified cycle size and tick counter starting at 0.
 * 
 * Signature: int -> AlarmClock
 * 
 * Examples:
 * - makeAlarm(5) -> AlarmClock(0, 5, []) (empty system, 5-tick cycle)
 * - makeAlarm(24) -> AlarmClock(0, 24, []) (empty system, 24-hour cycle)
 * 
 * Design Strategy: Simple Expression - Direct record construction with initial values.
 * 
 * Effects: Pure function with no side effects, returns new AlarmClock object.
 * 
 * @param cycleSize System cycle length (must be positive)
 * @return New empty alarm system
 */
AlarmClock makeAlarm(int cycleSize) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Create new AlarmClock with specified cycle size
    // - Initialize tick counter to 0
    // - Initialize alarm list to empty
    
    // DESIGN RECIPE STEP 5: Function Body
    return new AlarmClock(0, cycleSize, new Nil<Pair<Integer, Alarm>>());
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct initialization of alarm system

/**
 * Purpose: Registers a recurring alarm that triggers repeatedly at specified tick time.
 * 
 * Signature: Function<Integer, String>, int, AlarmClock -> AlarmClock
 * 
 * Examples:
 * - registerRecurringAlarm(t->"Wake", 4, clock) -> adds recurring alarm at tick 4
 * - registerRecurringAlarm(t->"New", 3, clock_with_alarm_at_3) -> replaces existing alarm at tick 3
 * 
 * Design Strategy: Function Composition - Create alarm object, use Put to update alarm list.
 * 
 * Effects: Pure function with no side effects, returns new AlarmClock with updated alarms.
 * 
 * @param alarm Function that produces message string from tick
 * @param time Target tick time (must be 0 <= time < cycleSize)
 * @param clock Current alarm clock state
 * @return New clock with recurring alarm added/updated
 */
AlarmClock registerRecurringAlarm(Function<Integer, String> alarm, int time, AlarmClock clock) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Create new Alarm with RECURRING type
    // - Use Put to add/update alarm in list (automatically handles replacement)
    // - Construct new AlarmClock with updated alarm list
    
    // DESIGN RECIPE STEP 5: Function Body
    // Create a new alarm with recurring behavior
    Alarm newAlarm = new Alarm(time, alarm, AlarmType.RECURRING);
    
    // Add the new alarm to the list, automatically overriding existing alarm at same time
    return new AlarmClock(clock.currentTick(), clock.cycleSize(), Put(clock.alarms(), time, newAlarm));
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct recurring alarm registration and replacement

/**
 * Purpose: Registers a one-time alarm that triggers once then removes itself automatically.
 * 
 * Signature: Function<Integer, String>, int, AlarmClock -> AlarmClock
 * 
 * Examples:
 * - registerOneTimeAlarm(t->"Meeting", 3, clock) -> adds one-time alarm at tick 3
 * - registerOneTimeAlarm(t->"Special", 2, clock_with_alarm_at_2) -> replaces existing alarm at tick 2
 * 
 * Design Strategy: Function Composition - Create alarm object, use Put to update alarm list.
 * 
 * Effects: Pure function with no side effects, returns new AlarmClock with updated alarms.
 * 
 * @param alarm Function that produces message string from tick
 * @param time Target tick time (must be 0 <= time < cycleSize)
 * @param clock Current alarm clock state
 * @return New clock with one-time alarm added/updated
 */
AlarmClock registerOneTimeAlarm(Function<Integer, String> alarm, int time, AlarmClock clock) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Create new Alarm with ONE_TIME type
    // - Use Put to add/update alarm in list (automatically handles replacement)
    // - Construct new AlarmClock with updated alarm list
    
    // DESIGN RECIPE STEP 5: Function Body
    // Create a new alarm with one-time behavior
    Alarm newAlarm = new Alarm(time, alarm, AlarmType.ONE_TIME);
    
    // Add the new alarm to the list, automatically overriding existing alarm at same time
    return new AlarmClock(clock.currentTick(), clock.cycleSize(), Put(clock.alarms(), time, newAlarm));
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct one-time alarm registration and replacement

// =============================================================================
// ALARM SYSTEM PROCESSING
// =============================================================================

/**
 * Purpose: Advances the clock by one tick and processes any triggered alarms.
 * 
 * Signature: AlarmClock -> Pair<AlarmClock, Maybe<String>>
 * 
 * Examples:
 * - tick(clock at 4, alarm at 5) -> (clock at 5, Nothing) (no alarm triggered)
 * - tick(clock at 4, alarm at 5) -> (clock at 5, Something("message")) (alarm triggered)
 * - tick(clock at 9, cycle 10) -> (clock at 0, result) (wrap around cycle)
 * 
 * Design Strategy: Function Composition - Calculate new tick, check for alarms, process results.
 * 
 * Effects: Pure function with no side effects, returns updated clock and optional message.
 * 
 * @param clock Current alarm clock state
 * @return Pair containing updated clock and optional alarm message
 */
Pair<AlarmClock, Maybe<String>> tick(AlarmClock clock) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Calculate new tick (with wraparound)
    // - Check if alarm exists at new tick
    // - Process any triggered alarms
    // - Return updated clock and message
    
    // DESIGN RECIPE STEP 5: Function Body
    int newTick = (clock.currentTick() + 1) % clock.cycleSize();
    Maybe<Alarm> maybeAlarm = Get(clock.alarms(), newTick);
    
    return processTriggeredAlarms(newTick, clock, maybeAlarm);
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct tick advancement and alarm processing

/**
 * Purpose: Processes alarms that are triggered at the new tick time.
 * 
 * Signature: int, AlarmClock, Maybe<Alarm> -> Pair<AlarmClock, Maybe<String>>
 * 
 * Examples:
 * - processTriggeredAlarms(5, clock, Nothing) -> (updated_clock, Nothing) (no alarm)
 * - processTriggeredAlarms(5, clock, Something(alarm)) -> (updated_clock, Some(message)) (alarm triggered)
 * 
 * Design Strategy: Cases on Maybe - Handle presence or absence of alarm at tick.
 * 
 * Effects: Pure function with no side effects, returns updated state and message.
 * 
 * @param newTick The new current tick
 * @param clock Current clock state
 * @param maybeAlarm Optional alarm at new tick
 * @return Updated clock and optional message
 */
Pair<AlarmClock, Maybe<String>> processTriggeredAlarms(
    int newTick, 
    AlarmClock clock, 
    Maybe<Alarm> maybeAlarm) {
        
    return switch (maybeAlarm) {
        case Nothing<Alarm>() -> {
            // No alarm at this tick, just update tick
            AlarmClock updatedClock = new AlarmClock(newTick, clock.cycleSize(), clock.alarms());
            yield new Pair<AlarmClock, Maybe<String>>(updatedClock, new Nothing<String>());
        }
        case Something<Alarm>(Alarm alarm) -> {
            // Alarm exists, handle based on type
            yield handleAlarmType(newTick, clock, alarm);
        }
    };
}

/**
 * Purpose: Handles alarm triggering based on alarm type (recurring vs one-time).
 * 
 * Signature: int, AlarmClock, Alarm -> Pair<AlarmClock, Maybe<String>>
 * 
 * Examples:
 * - handleAlarmType(5, clock, recurring_alarm) -> (clock_with_alarm, Some(message))
 * - handleAlarmType(5, clock, onetime_alarm) -> (clock_without_alarm, Some(message))
 * 
 * Design Strategy: Cases on AlarmType - Handle recurring vs one-time behavior.
 * 
 * Effects: Pure function with no side effects, returns updated state and message.
 * 
 * @param newTick The current tick
 * @param clock Current clock state  
 * @param alarm The triggered alarm
 * @return Updated clock and alarm message
 */
Pair<AlarmClock, Maybe<String>> handleAlarmType(
    int newTick, 
    AlarmClock clock, 
    Alarm alarm) {
        
    String message = alarm.alarmFunction().apply(newTick);
    
    return switch (alarm.alarmType()) {
        case RECURRING -> {
            // Recurring alarm stays in system
            AlarmClock updatedClock = new AlarmClock(newTick, clock.cycleSize(), clock.alarms());
            yield new Pair<AlarmClock, Maybe<String>>(updatedClock, new Something<String>(message));
        }
        case ONE_TIME -> {
            // One-time alarm removes itself
            ConsList<Pair<Integer, Alarm>> updatedAlarms = Remove(clock.alarms(), new Pair<Integer, Alarm>(newTick, alarm));
            AlarmClock updatedClock = new AlarmClock(newTick, clock.cycleSize(), updatedAlarms);
            yield new Pair<AlarmClock, Maybe<String>>(updatedClock, new Something<String>(message));
        }
    };
}

// =============================================================================
// ALARM PREDICTION AND ANALYSIS
// =============================================================================

/**
 * Purpose: Returns the number of ticks until the next alarm will trigger from current position.
 * 
 * Signature: AlarmClock -> int
 * 
 * Examples:
 * - ticksUntilNextAlarm(clock at tick 2, alarm at tick 5) -> 3
 * - ticksUntilNextAlarm(clock at tick 8, alarm at tick 3, cycle 10) -> 5 (wraps around)
 * - ticksUntilNextAlarm(clock with no alarms) -> cycleSize (full cycle)
 * 
 * Design Strategy: Function Composition - Find minimum alarm time, calculate distance with wraparound.
 * 
 * Effects: Pure function with no side effects, returns integer tick count.
 * 
 * @param clock Current alarm clock state
 * @return Number of ticks until next alarm (or cycle size if no alarms)
 */
int ticksUntilNextAlarm(AlarmClock clock) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if alarm list is empty
    // - Find the minimum alarm time from all alarms
    // - Calculate distance considering cycle wraparound
    
    // DESIGN RECIPE STEP 5: Function Body
    if (IsEmpty(clock.alarms())) {
        return clock.cycleSize(); // No alarms, full cycle
    }
    
    // Find the minimum alarm time
    Pair<Integer, Alarm> first = First(clock.alarms());
    ConsList<Pair<Integer, Alarm>> rest = Rest(clock.alarms());
    int minAlarmTime = processAlarm(first, rest, clock, first.first());
    
    // Calculate ticks until that alarm
    return getNextTick(clock, minAlarmTime);
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct prediction of next alarm timing

/**
 * Purpose: Processes alarm list to find the minimum alarm time that will trigger next.
 * 
 * Signature: Pair<Integer, Alarm>, ConsList<Pair<Integer, Alarm>>, AlarmClock, int -> int
 * 
 * Examples:
 * - processAlarm((5, alarm), [], clock_at_2, 5) -> handles single alarm case
 * - processAlarm((5, alarm), [(3, alarm2)], clock_at_2, 5) -> compares and recurses
 * 
 * Design Strategy: Cases on List Structure - Process current alarm, recurse on rest.
 * 
 * Effects: Pure function with no side effects, returns minimum next alarm time.
 * 
 * @param first Current alarm being processed
 * @param rest Remaining alarms to process
 * @param clock Current clock state
 * @param minAlarm Current minimum alarm time found
 * @return Minimum alarm time that will trigger next
 */
int processAlarm(Pair<Integer, Alarm> first, ConsList<Pair<Integer, Alarm>> rest, 
                AlarmClock clock, int minAlarm) {
    int alarmTime = first.first();
    
    if (alarmTime <= clock.currentTick()) {
        return handleExpiredAlarm(rest, clock);
    } else {
        return handleFutureAlarm(first, rest, clock, minAlarm);
    }
}

/**
 * Purpose: Handles alarms that have already passed in current cycle.
 * 
 * Signature: ConsList<Pair<Integer, Alarm>>, AlarmClock -> int
 * 
 * Examples:
 * - handleExpiredAlarm([], clock) -> cycleSize (no more alarms)
 * - handleExpiredAlarm([alarm_list], clock) -> continues processing remaining alarms
 * 
 * Design Strategy: Cases on List Structure - Continue processing or return cycle size.
 * 
 * Effects: Pure function with no side effects, returns next alarm time.
 * 
 * @param rest Remaining alarms to process
 * @param clock Current clock state
 * @return Next alarm time or cycle size if none
 */
int handleExpiredAlarm(ConsList<Pair<Integer, Alarm>> rest, AlarmClock clock) {
    if (IsEmpty(rest)) {
        return clock.cycleSize();
    } else {
        Pair<Integer, Alarm> nextFirst = First(rest);
        ConsList<Pair<Integer, Alarm>> nextRest = Rest(rest);
        return processAlarm(nextFirst, nextRest, clock, nextFirst.first());
    }
}

/**
 * Purpose: Handles alarms that will trigger in the future within current cycle.
 * 
 * Signature: Pair<Integer, Alarm>, ConsList<Pair<Integer, Alarm>>, AlarmClock, int -> int
 * 
 * Examples:
 * - handleFutureAlarm((5, alarm), [], clock_at_2, 5) -> 5 (this alarm is next)
 * - handleFutureAlarm((5, alarm), [(3, alarm2)], clock_at_2, 5) -> 3 (earlier alarm found)
 * 
 * Design Strategy: Cases on List Structure - Compare with minimum, continue processing.
 * 
 * Effects: Pure function with no side effects, returns minimum alarm time.
 * 
 * @param first Current alarm being processed
 * @param rest Remaining alarms to process
 * @param clock Current clock state
 * @param minAlarm Current minimum alarm time
 * @return Updated minimum alarm time
 */
int handleFutureAlarm(Pair<Integer, Alarm> first, ConsList<Pair<Integer, Alarm>> rest, 
                      AlarmClock clock, int minAlarm) {
    int currentMin = Min(minAlarm, first.first());
    
    if (IsEmpty(rest)) {
        return currentMin;
    } else {
        return helper(First(rest), Rest(rest), clock, currentMin);
    }
}

/**
 * Purpose: Calculates the number of ticks from current position to target tick.
 * 
 * Signature: AlarmClock, int -> int
 * 
 * Examples:
 * - getNextTick(clock_at_2, 5) -> 3 (5 - 2)
 * - getNextTick(clock_at_8, 3, cycle_10) -> 5 (wraparound: 10 - 8 + 3)
 * 
 * Design Strategy: Simple Expression - Calculate distance with cycle consideration.
 * 
 * Effects: Pure function with no side effects, returns tick distance.
 * 
 * @param clock Current clock state
 * @param minTick Target tick time
 * @return Number of ticks to reach target
 */
int getNextTick(AlarmClock clock, int minTick){
    if (minTick > clock.currentTick()) {
        return minTick - clock.currentTick();
    } else {
        // Wrap around the cycle
        return clock.cycleSize() - clock.currentTick() + minTick;
    }
}

/**
 * Purpose: Helper function for processing remaining alarms in the list.
 * 
 * Signature: Pair<Integer, Alarm>, ConsList<Pair<Integer, Alarm>>, AlarmClock, int -> int
 * 
 * Examples:
 * - helper((3, alarm), [], clock, 5) -> 3 (new minimum found)
 * - helper((7, alarm), [(2, alarm2)], clock, 5) -> continues processing
 * 
 * Design Strategy: Function Composition - Delegate to processAlarm for consistent handling.
 * 
 * Effects: Pure function with no side effects, returns minimum alarm time.
 * 
 * @param first Current alarm to process
 * @param rest Remaining alarms
 * @param clock Current clock state
 * @param minTick Current minimum tick time
 * @return Updated minimum alarm time
 */
int helper(Pair<Integer, Alarm> first, ConsList<Pair<Integer, Alarm>> rest, AlarmClock clock, int minTick){
    return processAlarm(first, rest, clock, minTick);
}

// =============================================================================
// TESTING INTERFACE FUNCTIONS
// =============================================================================

/**
 * Purpose: Returns the current tick value from an alarm clock for testing purposes.
 * 
 * Signature: AlarmClock -> int
 * 
 * Examples:
 * - currentTick(clock_at_5) -> 5
 * - currentTick(new_clock) -> 0
 * 
 * Design Strategy: Simple Expression - Extract field from record.
 * 
 * Effects: Pure function with no side effects, returns primitive int value.
 * 
 * @param clock Alarm clock to query
 * @return Current tick value
 */
int currentTick(AlarmClock clock){
    return clock.currentTick();
}

// =============================================================================
// WORLD PROGRAM DATA DEFINITIONS
// =============================================================================

/**
 * Purpose: Represents the state of the world program's clock display.
 * 
 * Signature: Enumeration with two values: CLOCK_RUN, CLOCK_PAUSE.
 * 
 * Examples:
 * - AlarmClockState.CLOCK_RUN: Clock is actively ticking and processing
 * - AlarmClockState.CLOCK_PAUSE: Clock is paused and not advancing
 * 
 * Design Strategy: Simple Expression - Enumeration for distinct display states.
 * 
 * Effects: Immutable enumeration with no side effects.
 */
enum AlarmClockState{
    CLOCK_RUN,
    CLOCK_PAUSE
}

/**
 * Purpose: Represents the complete state of the interactive clock world program.
 * 
 * Signature: Record containing AlarmClock, AlarmClockState, FontStyle, int, int, and String fields.
 * 
 * Examples:
 * - ClockWorld with running clock, normal font, no recent alarms
 * - ClockWorld with paused clock, bold font, recent alarm message
 * 
 * Design Strategy: Simple Expression - Immutable record combining all world state.
 * 
 * Effects: Creates immutable world state with no side effects.
 * 
 * @param clock The underlying alarm clock system
 * @param clockState Whether clock is running or paused
 * @param fontStyle Font style for display
 * @param worldAlarmTick Tick when alarm was triggered in world
 * @param lastAlarmTick Last tick when alarm occurred
 * @param alarmNote Current alarm message to display
 */
record ClockWorld(AlarmClock clock, AlarmClockState clockState, 
                 FontStyle fontStyle, int worldAlarmTick, int lastAlarmTick, String alarmNote){}

// =============================================================================
// WORLD PROGRAM FUNCTIONS
// =============================================================================

/**
 * Purpose: Renders the complete clock world display including time, alarms, and status information.
 * 
 * Signature: ClockWorld -> Image
 * 
 * Examples:
 * - draw(world_with_running_clock) -> display with current time and next alarm
 * - draw(world_with_alarm_message) -> display with alarm notification
 * 
 * Design Strategy: Function Composition - Combine multiple text elements into single image.
 * 
 * Effects: Pure function with no side effects, returns composed Image object.
 * 
 * @param world Current state of the clock world
 * @return Complete rendered display as Image
 */
Image draw(ClockWorld world){
    // DESIGN RECIPE STEP 4: Function Template
    // - Extract clock information (current tick, next alarm)
    // - Create text elements for display
    // - Compose all elements into single image
    // - Handle different display states (running/paused)
    
    // DESIGN RECIPE STEP 5: Function Body
    AlarmClock clock = world.clock();
    String currentTimeText = "Current Time: " + clock.currentTick();
    String nextAlarmText = "Next Alarm In: " + ticksUntilNextAlarm(clock) + " ticks";
    String statusText = world.clockState() == AlarmClockState.CLOCK_RUN ? "Running" : "Paused";
    String controlsText = "Controls: SPACE=pause/resume, R=register alarm, T=register one-time alarm";
    
    // Create text images
    Image currentTimeImg = Text(currentTimeText, 20, BLACK, world.fontStyle());
    Image nextAlarmImg = Text(nextAlarmText, 16, BLUE, world.fontStyle());
    Image statusImg = Text("Status: " + statusText, 14, 
                          world.clockState() == AlarmClockState.CLOCK_RUN ? GREEN : RED, world.fontStyle());
    Image controlsImg = Text(controlsText, 12, GRAY, world.fontStyle());
    
    // Compose final image
    Image result = Above(currentTimeImg, 
                        Above(nextAlarmImg,
                             Above(statusImg, controlsImg)));
    
    // Add alarm message if present
    if (!Equals(world.alarmNote(), "")) {
        Image alarmImg = Text("ALARM: " + world.alarmNote(), 18, RED, FontStyle.BOLD);
        result = Above(alarmImg, result);
    }
    
    return result;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct rendering for different world states

/**
 * Purpose: Creates the initial state of the clock world program.
 * 
 * Signature: void -> ClockWorld
 * 
 * Examples:
 * - getInitialState() -> ClockWorld with new 10-tick alarm system, running state
 * 
 * Design Strategy: Simple Expression - Create ClockWorld with default initial values.
 * 
 * Effects: Creates new ClockWorld object with no side effects.
 * 
 * @return Initial world state for the program
 */
ClockWorld getInitialState(){
    AlarmClock initialClock = makeAlarm(10);
    // Add some sample alarms for demonstration
    initialClock = registerRecurringAlarm(t -> "Time to work!", 3, initialClock);
    initialClock = registerOneTimeAlarm(t -> "Special event at tick " + t, 7, initialClock);
    
    return new ClockWorld(initialClock, AlarmClockState.CLOCK_RUN, 
                         FontStyle.NORMAL, -1, -1, "");
}

/**
 * Purpose: Extracts the alarm clock from a clock world for testing purposes.
 * 
 * Signature: ClockWorld -> AlarmClock
 * 
 * Examples:
 * - getClock(world_with_clock) -> the underlying AlarmClock object
 * 
 * Design Strategy: Simple Expression - Extract field from record.
 * 
 * Effects: Pure function with no side effects, returns AlarmClock object.
 * 
 * @param world Clock world to extract from
 * @return The underlying alarm clock
 */
AlarmClock getClock(ClockWorld world){
    return world.clock();
}

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Updates the clock world state each animation frame when clock is running.
 * 
 * Signature: ClockWorld -> ClockWorld
 * 
 * Examples:
 * - step(world_with_running_clock) -> world_with_advanced_tick
 * - step(world_with_paused_clock) -> same_world (no changes)
 * - step(world_at_alarm_time) -> world_with_alarm_message
 * 
 * Design Strategy: Cases on ClockState - Handle running vs paused behavior.
 * 
 * Effects: Pure function with no side effects, returns new ClockWorld object.
 * 
 * @param world Current state of the clock world
 * @return Updated world state after step processing
 */
ClockWorld step(ClockWorld world){
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if clock is running or paused
    // - If running, advance tick and process alarms
    // - If paused, return unchanged world
    // - Handle alarm message display timing
    
    // DESIGN RECIPE STEP 5: Function Body
    return switch (world.clockState()) {
        case CLOCK_PAUSE -> world; // No changes when paused
        case CLOCK_RUN -> {
            // Advance the clock and process any alarms
            Pair<AlarmClock, Maybe<String>> tickResult = tick(world.clock());
            AlarmClock newClock = tickResult.first();
            Maybe<String> maybeResult = tickResult.second();
            
            int newWorldAlarmTick = newClock.currentTick();
            yield handleTickResult(maybeResult, newClock, world, newWorldAlarmTick);
        }
    };
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct step behavior for running and paused states

/**
 * Purpose: Handles the result of a tick operation, updating world state based on alarm outcomes.
 * 
 * Signature: Maybe<String>, AlarmClock, ClockWorld, int -> ClockWorld
 * 
 * Examples:
 * - handleTickResult(Nothing, clock, world, tick) -> world with no alarm message
 * - handleTickResult(Some("Wake up!"), clock, world, tick) -> world with alarm message
 * 
 * Design Strategy: Cases on Maybe - Handle presence or absence of alarm message.
 * 
 * Effects: Pure function with no side effects, returns updated ClockWorld.
 * 
 * @param maybeResult Optional alarm message from tick
 * @param clock Updated alarm clock
 * @param world Current world state
 * @param newWorldAlarmTick Current tick value
 * @return Updated world state
 */
ClockWorld handleTickResult(Maybe<String> maybeResult, 
                           AlarmClock clock, ClockWorld world, int newWorldAlarmTick) {
    return switch (maybeResult) {
        case Nothing<String>() -> handleNothingCase(clock, world, newWorldAlarmTick);
        case Something<String>(String result) -> {
            // Alarm triggered, display message
            yield new ClockWorld(clock, world.clockState(), FontStyle.BOLD, 
                               newWorldAlarmTick, newWorldAlarmTick, result);
        }
    };
}

/**
 * Purpose: Handles the case when no alarm is triggered, managing alarm message display timing.
 * 
 * Signature: AlarmClock, ClockWorld, int -> ClockWorld
 * 
 * Examples:
 * - handleNothingCase(clock, world_with_old_alarm, tick) -> clears alarm if enough time passed
 * - handleNothingCase(clock, world_without_alarm, tick) -> simple state update
 * 
 * Design Strategy: Cases on Alarm Message Age - Clear old messages, preserve recent ones.
 * 
 * Effects: Pure function with no side effects, returns updated ClockWorld.
 * 
 * @param clock Updated alarm clock
 * @param world Current world state
 * @param newWorldAlarmTick Current tick value
 * @return Updated world state
 */
ClockWorld handleNothingCase(AlarmClock clock, ClockWorld world, int newWorldAlarmTick) {
    if (world.lastAlarmTick() != -1 && 
        (newWorldAlarmTick - world.lastAlarmTick() + clock.cycleSize()) % clock.cycleSize() >= 3) {
        // Clear alarm message after 3 ticks
        return new ClockWorld(clock, world.clockState(), FontStyle.NORMAL, 
                             newWorldAlarmTick, -1, "");
    } else {
        // Keep current alarm message
        return new ClockWorld(clock, world.clockState(), world.fontStyle(), 
                             newWorldAlarmTick, world.lastAlarmTick(), world.alarmNote());
    }
}

/**
 * Purpose: Handles keyboard events to control the clock world (pause/resume, register alarms).
 * 
 * Signature: ClockWorld, KeyEvent -> ClockWorld
 * 
 * Examples:
 * - keyEvent(world, Space_key) -> toggles pause/resume state
 * - keyEvent(world, R_key) -> registers new recurring alarm at next tick
 * - keyEvent(world, T_key) -> registers new one-time alarm at next tick
 * 
 * Design Strategy: Cases on KeyEvent - Handle different key presses with appropriate actions.
 * 
 * Effects: Pure function with no side effects, returns updated ClockWorld object.
 * 
 * @param world Current state of the clock world
 * @param keyEvent Keyboard event triggered by user
 * @return Updated world state after processing key event
 */
ClockWorld keyEvent(ClockWorld world, KeyEvent keyEvent){
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if key is pressed (not released)
    // - Handle Space key: toggle pause/resume
    // - Handle R key: register recurring alarm
    // - Handle T key: register one-time alarm
    // - Return updated world state
    
    // DESIGN RECIPE STEP 5: Function Body
    if (keyEvent.kind() == KeyEventKind.KEY_PRESSED) {
        return switch (keyEvent.key()) {
            case "Space" -> {
                // Toggle pause/resume
                AlarmClockState newState = world.clockState() == AlarmClockState.CLOCK_RUN ? 
                                          AlarmClockState.CLOCK_PAUSE : AlarmClockState.CLOCK_RUN;
                yield new ClockWorld(world.clock(), newState, world.fontStyle(),
                                   world.worldAlarmTick(), world.lastAlarmTick(), world.alarmNote());
            }
            case "r", "R" -> {
                // Register recurring alarm at next tick
                int nextTick = (world.clock().currentTick() + 1) % world.clock().cycleSize();
                AlarmClock newClock = registerRecurringAlarm(
                    t -> "Recurring alarm at tick " + t, nextTick, world.clock());
                yield new ClockWorld(newClock, world.clockState(), world.fontStyle(),
                                   world.worldAlarmTick(), world.lastAlarmTick(), world.alarmNote());
            }
            case "t", "T" -> {
                // Register one-time alarm at next tick
                int nextTick = (world.clock().currentTick() + 1) % world.clock().cycleSize();
                AlarmClock newClock = registerOneTimeAlarm(
                    t -> "One-time alarm at tick " + t, nextTick, world.clock());
                yield new ClockWorld(newClock, world.clockState(), world.fontStyle(),
                                   world.worldAlarmTick(), world.lastAlarmTick(), world.alarmNote());
            }
            default -> world;
        };
    }
    return world;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct responses to all key combinations

// =============================================================================
// MAIN PROGRAM AND TESTING
// =============================================================================

/**
 * Purpose: Main entry point for the interactive clock world program.
 * 
 * Signature: String[] -> void
 * 
 * Examples:
 * - main(args) -> starts interactive clock program with title "Alarm Clock"
 * 
 * Design Strategy: Function Composition - Create initial world state and start BigBang universe.
 * 
 * Effects: Starts interactive program, creates GUI window, handles user input and animation.
 * 
 * @param args Command line arguments (not used)
 */
void main(String[] args){ 
    ClockWorld world = getInitialState();
    BigBang("Alarm Clock", world, this::draw, this::step, this::keyEvent);
}

/**
 * Purpose: Tests basic tick functionality of the alarm system.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Tests tick advancement and wraparound
 * - Tests alarm triggering and message generation
 * 
 * Design Strategy: Function Composition - Create test cases and verify expected outcomes.
 * 
 * Effects: Executes test assertions, may output test results.
 */
void testBasicTick() {
    AlarmClock clock = makeAlarm(5);
    clock = registerRecurringAlarm(t -> "Test alarm", 2, clock);
    
    Pair<AlarmClock, Maybe<String>> result = tick(clock);
    testEqual(1, result.first().currentTick());
    testTrue(result.second() instanceof Nothing);
    
    result = tick(result.first());
    testEqual(2, result.first().currentTick());
    testTrue(result.second() instanceof Something);
}

/**
 * Purpose: Executes comprehensive test suite for all alarm clock functions.
 * 
 * Signature: void -> void
 * 
 * Examples:
 * - Runs all test functions in sequence
 * - Provides complete validation of implementation
 * 
 * Design Strategy: Function Composition - Execute all individual test functions.
 * 
 * Effects: Runs test suite, outputs test results to console.
 */
void test()
{
    testBasicTick();
}