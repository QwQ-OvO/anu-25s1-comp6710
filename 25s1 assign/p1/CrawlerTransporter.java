/** Import packages from course website. */
import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;
import comp1110.universe.*;
import static comp1110.universe.Colour.*;
import static comp1110.universe.Image.*;
import static comp1110.universe.Universe.*;

/**
 * Purpose: Represents the complete state of the crawler-transporter world at any given moment.
 * 
 * Signature: Record containing int width, int height, int crawler_X, int rocket_X, int rocket_Y, RocketState rocket_State.
 * 
 * Examples:
 * - Initial: World(800, 500, 40, 40, 420, RocketState.NOT_LOADED)
 * - Loaded: World(800, 500, 200, 200, 420, RocketState.HAS_LOADED)
 * - Launched: World(800, 500, 760, 760, 300, RocketState.HAS_LAUNCHED)
 * 
 * Design Strategy: Simple Expression - Immutable record to capture world state.
 * 
 * Effects: Immutable data structure representing complete world state.
 * 
 * @param width The width of the world (800 pixels)
 * @param height The height of the world (500 pixels)
 * @param crawler_X The X coordinate of the crawler transporter center
 * @param rocket_X The X coordinate of the rocket center
 * @param rocket_Y The Y coordinate of the rocket center
 * @param rocket_State The rocket's current state (NOT_LOADED, HAS_LOADED, or HAS_LAUNCHED)
 */
record World(int width, int height, int crawler_X, int rocket_X, int rocket_Y, RocketState rocket_State){}

/** The width of the world */
int WORLD_WIDTH = 800;
/** The height of the world */
int WORLD_HEIGHT = 500;
/** The width of the crawler-transporter */
int CRAWLER_WIDTH = 80;
/** The height of the crawler-transporter */
int CRAWLER_HEIGHT = 20;
/** The initial crawler transporter horizontal center's X position */
int CRAWLER_X = 40;
/** The permanent crawler transporter horizontal center's Y position */
int CRAWLER_Y = 480;
/** The width of the rocket */
int ROCKET_WIDTH = 30;
/** The height of the rocket */
int ROCKET_HEIGHT = 100;
/** 
 * The initial rocket horizontal center's X position
 * Its horizontal center aligns with the crawler's horizontal center 
 */
int ROCKET_X = 40;
/** 
 * The initial rocket center's Y position
 * Its bottom touches the top of the crawler
 */
int ROCKET_Y = 420; 

/**
 * Purpose: Represents the different states a rocket can be in during the simulation.
 * 
 * Signature: Enumeration with three values: NOT_LOADED, HAS_LOADED, HAS_LAUNCHED.
 * 
 * Examples:
 * - NOT_LOADED: Rocket is not present, waiting to be loaded
 * - HAS_LOADED: Rocket is on the crawler, moves with crawler
 * - HAS_LAUNCHED: Rocket has been launched and is moving upward
 * 
 * Design Strategy: Simple Expression - Enumeration to represent distinct rocket states.
 * 
 * Effects: Immutable enumeration with no side effects.
 */
enum RocketState {
    NOT_LOADED,
    HAS_LOADED,
    HAS_LAUNCHED
}

/**
 * Purpose: Renders the complete world scene including background, crawler, and rocket based on current world state.
 * 
 * Signature: World -> Image
 * 
 * Examples:
 * - draw(world with NOT_LOADED) -> background with crawler only
 * - draw(world with HAS_LOADED) -> background with crawler and rocket at same X position
 * - draw(world with HAS_LAUNCHED) -> background with crawler and rocket at different positions
 * 
 * Design Strategy: Cases on RocketState - Render different elements based on rocket state.
 * 
 * Effects: Pure function that creates and returns Image object, no side effects.
 * 
 * @param world The current state of the world
 * @return The complete rendered world scene as an Image
 */
Image draw(World world) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Create background image
    // - Place crawler at current position
    // - Based on rocket state, conditionally place rocket
    // - Return composed image
    
    // DESIGN RECIPE STEP 5: Function Body
    Image background = Rectangle(800, 500, LIGHT_BLUE); 
    Image crawler = Rectangle(80, 20, RED); 
    background = PlaceXY(background, crawler, world.crawler_X(), 480);
    
    // Based on the rocket's state, decide whether and where to draw the rocket
    Image rocket = Rectangle(30, 100, GREEN);
    switch (world.rocket_State()) {
        case HAS_LOADED:
            rocket = Rectangle(30, 100, GREEN);
            background = PlaceXY(background, rocket, world.rocket_X(), world.rocket_Y());
            break;
        case HAS_LAUNCHED:
            rocket = Rectangle(30, 100, GREEN);
            background = PlaceXY(background, rocket, world.rocket_X(), world.rocket_Y());
            break;
        case NOT_LOADED:
            // No rocket to draw when not loaded
            break;
    }
    return background;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct rendering for each rocket state

/**
 * Purpose: Returns the rocket's horizontal center X position if rocket exists, Nothing otherwise.
 *
 * Signature: World -> Maybe<Integer>
 *
 * Examples:
 * - getRocketX(world with NOT_LOADED) -> Nothing
 * - getRocketX(world with HAS_LOADED at X=200) -> Something(200)
 * - getRocketX(world with HAS_LAUNCHED at X=300) -> Something(300)
 *
 * Design Strategy: Cases on RocketState - Return position only when rocket exists.
 *
 * Effects: Pure function with no side effects, returns Maybe<Integer> object.
 *
 * @param world The current world state
 * @return Maybe containing rocket's X position, or Nothing if no rocket
 */
Maybe<Integer> getRocketX(World world){
    return switch (world.rocket_State()) {
        case NOT_LOADED -> new Nothing<Integer>(); //No position available when rocket is not loaded
        case HAS_LOADED -> new Something<Integer>(world.rocket_X());
        case HAS_LAUNCHED -> new Something<Integer>(world.rocket_X());
    };
}

/**
 * Purpose: Returns the rocket's vertical center Y position if rocket is launched, Nothing otherwise.
 *
 * Signature: World -> Maybe<Integer>
 *
 * Examples:
 * - getRocketY(world with NOT_LOADED) -> Nothing
 * - getRocketY(world with HAS_LOADED) -> Nothing (rocket position not relevant when loaded)
 * - getRocketY(world with HAS_LAUNCHED at Y=300) -> Something(300)
 *
 * Design Strategy: Cases on RocketState - Return Y position only when rocket is launched.
 *
 * Effects: Pure function with no side effects, returns Maybe<Integer> object.
 *
 * @param world The current world state
 * @return Maybe containing rocket's Y position when launched, or Nothing otherwise
 */
Maybe<Integer> getRocketY(World world){
    return switch (world.rocket_State()) {
        case NOT_LOADED -> new Nothing<Integer>();
        case HAS_LOADED -> new Nothing<Integer>();
        case HAS_LAUNCHED -> new Something<Integer>(world.rocket_Y());
    };
}

/**
 * Purpose: Creates and returns the initial state of the world when the program starts.
 * 
 * Signature: void -> World
 * 
 * Examples:
 * - getInitialState() -> World(800, 500, 40, 40, 420, NOT_LOADED)
 * 
 * Design Strategy: Simple Expression - Create World with predefined initial values.
 * 
 * Effects: Creates new World object with initial state, no side effects.
 * 
 * @return The initial world state with crawler at left position and no rocket loaded
 */
World getInitialState(){
    return new World(WORLD_WIDTH, WORLD_HEIGHT, CRAWLER_X, ROCKET_X, ROCKET_Y, RocketState.NOT_LOADED);
}

/**
 * Purpose: Updates the world state each animation frame, handling rocket movement and state transitions.
 * 
 * Signature: World -> World
 * 
 * Examples:
 * - step(world with HAS_LAUNCHED, rocket at Y=300) -> world with rocket at Y=290
 * - step(world with HAS_LAUNCHED, rocket at Y=-60) -> world reset to NOT_LOADED state
 * - step(world with NOT_LOADED or HAS_LOADED) -> same world (no changes)
 * 
 * Design Strategy: Cases on RocketState - Handle different behaviors based on rocket state.
 * 
 * Effects: Pure function that returns new World object, no side effects.
 * 
 * @param world The current state of the world
 * @return The updated world state after applying step changes
 */
World step(World world){
    // DESIGN RECIPE STEP 4: Function Template
    // - Check rocket state
    // - If launched, move rocket upward
    // - If rocket moves off screen, reset to initial state
    // - Otherwise, return unchanged world
    
    // DESIGN RECIPE STEP 5: Function Body
    switch (world.rocket_State()) {
        case NOT_LOADED:
            // Rocket is not loaded, no changes needed
            break;
        case  HAS_LOADED:
            // Rocket is loaded but not launched, no changes needed
            break;
        case HAS_LAUNCHED:
            // Rocket is launched, move it upwards by decreasing its Y coordinate
            world = new World(world.width(), world.height(), world.crawler_X(), world.rocket_X(), world.rocket_Y() - 10, world.rocket_State());
            break;
    }
    // If the rocket moves beyond the background, reset it to NOT_LOADED state
    if (world.rocket_Y() < -50) {
        return new World(world.width(), world.height(), world.crawler_X(), ROCKET_X, ROCKET_Y, RocketState.NOT_LOADED);
    }
    // Return the updated world state
    return world;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct state transitions and rocket movement

/**
 * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
 * 
 * Purpose: Handles keyboard events to control the crawler and rocket in the world.
 *          Left/Right arrows move the crawler, Space key loads/launches rockets.
 * 
 * Signature: World, KeyEvent -> World
 * 
 * Examples:
 * - keyEvent(world, Left arrow) -> world with crawler moved 5 pixels left (within bounds)
 * - keyEvent(world at left, Space) -> world with rocket loaded
 * - keyEvent(world at right with loaded rocket, Space) -> world with rocket launched
 * 
 * Design Strategy: Cases on KeyEvent - Handle different key presses with appropriate actions.
 * 
 * Effects: Pure function that returns new World object, no side effects.
 * 
 * @param world The current state of the world
 * @param event The keyboard event triggered by user input
 * @return The updated state of the world after processing the keyboard event
 */
World keyEvent(World world, KeyEvent event) {
    // DESIGN RECIPE STEP 4: Function Template
    // - Check if key is pressed (not released)
    // - Handle Left arrow: move crawler and rocket left within bounds
    // - Handle Right arrow: move crawler and rocket right within bounds  
    // - Handle Space: load rocket (when at left) or launch rocket (when at right)
    // - Return updated world state
    
    // DESIGN RECIPE STEP 5: Function Body
    println(event);
    // Check if a key is pressed
    if (event.kind() == KeyEventKind.KEY_PRESSED) {
        // Move crawler and rocket to left
        if (event.key() == "Left") {
            // Ensure the crawler does not move beyond the left boundary
            int moveCrawlerX = Max(40, world.crawler_X() - 5); 
            // Update the rocket position based on its current state
            int moverocket_X = switch (world.rocket_State()) {
                case NOT_LOADED -> world.rocket_X(); // Rocket remains stationary when not loaded
                case HAS_LOADED -> Max(40, world.rocket_X() - 5); // If has loaded, moves with the crawler
                case HAS_LAUNCHED -> world.rocket_X(); // Remains stationary after launched
            };
            // Return the new world state with updated positions
            return new World(world.width(), world.height(), moveCrawlerX, moverocket_X, world.rocket_Y(), world.rocket_State());
        }
        // Move crawler and rocket right
        if (event.key() == "Right") {
            // Ensure the crawler does not move beyond the right boundary
            int moveCrawlerX = Min(world.width() - 40, world.crawler_X() + 5);
            // Update the rocket position based on its current state
            int moverocket_X = switch (world.rocket_State()) {
                case NOT_LOADED -> world.rocket_X(); // Rocket remains stationary when not loaded
                case HAS_LOADED -> Min(world.width() - 40, world.rocket_X() + 5); // Moves with the crawler when loaded
                case HAS_LAUNCHED -> world.rocket_X(); // Remains stationary once launched
            };
            // Return the new world state with updated positions
            return new World(world.width(), world.height(), moveCrawlerX, moverocket_X, world.rocket_Y(), world.rocket_State());
        }
        // Load the rocket (when crawler is at leftmost position)
        if (event.key() == "Space" && world.rocket_State() == RocketState.NOT_LOADED && world.crawler_X() == CRAWLER_X) {
            return new World(world.width(), world.height(), world.crawler_X(), world.crawler_X(), world.rocket_Y(), RocketState.HAS_LOADED);
        }
        // Launch the rocket (when crawler is at rightmost position)
        if (event.key() == "Space" && world.rocket_State() == RocketState.HAS_LOADED && world.crawler_X() == 760) {
            return new World(world.width(), world.height(), world.crawler_X(), world.rocket_X(), world.rocket_Y(), RocketState.HAS_LAUNCHED);
        }
    }
    return world;
}
// DESIGN RECIPE STEP 6: Testing
// Tests would verify correct responses to all key combinations and boundary conditions

/**
 * Purpose: Returns the crawler transporter's horizontal center X position for testing.
 * 
 * Signature: World -> int
 * 
 * Examples:
 * - getCrawlerX(world with crawler at position 100) -> 100
 * - getCrawlerX(initial world) -> 40
 * 
 * Design Strategy: Simple Expression - Extract crawler X coordinate from world state.
 * 
 * Effects: Pure function with no side effects, returns primitive int value.
 * 
 * @param world The current world state
 * @return The X coordinate of the crawler's center
 */
int getCrawlerX(World world) {
    return world.crawler_X();
}

/**
 * Purpose: Main function that initializes and starts the crawler-transporter world simulation.
 * 
 * Signature: String[] -> void
 * 
 * Examples:
 * - main(args) -> starts interactive world program with title "Crawler Transporter"
 * 
 * Design Strategy: Function Composition - Create initial world state and start BigBang universe.
 * 
 * Effects: Starts interactive program, creates GUI window, handles user input and animation.
 * 
 * @param args Command line arguments (not used)
 */
void main(String[] args) {
    World world = getInitialState();
    BigBang("Crawler Transporter", world, this::draw, this::step, this::keyEvent);
}
