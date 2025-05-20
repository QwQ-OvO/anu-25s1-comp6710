/** Import packages from course webiste. */
import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;
import comp1110.universe.*;
import static comp1110.universe.Colour.*;
import static comp1110.universe.Image.*;
import static comp1110.universe.Universe.*;

/**Define all items in the world.
 * @param width The width of the world
 * @param height The height of the world
 * @param crawler_X The X coordinate of the rawler Transporter
 * @param rocket_X The X coordinate of the rocket
 * @param rocket_Y The Y coordinate of the rocket
 * @param rocket_State The rocket's current state
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
 * Its horizontal center aligns with the crawler’s horizontal center 
 */
int ROCKET_X = 40;
/** 
 * The initial rocket center's Y position
 * Its bottom touches the top of the crawler
 */
int ROCKET_Y = 420; 

/** Rocket state enumeration */
enum RocketState {
    NOT_LOADED,
    HAS_LOADED,
    HAS_LAUNCHED
}

/** Draw the world
 * @param world The situation of the world
 * @return The world scene with all items state
*/
Image draw(World world) {
    Image background = Rectangle(800, 500, LIGHT_BLUE); 
    Image crawler = Rectangle(80, 20, RED); 
    background = PlaceXY(background, crawler, world.crawler_X(), 480);
    /** Base the rocket's state classify*/
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
            break;
    }
    return background; // draw the rocket and crawler
}

/** Get initial world state*/
World initialState(){
    return new World(WORLD_WIDTH, WORLD_HEIGHT, CRAWLER_X, ROCKET_X, ROCKET_Y, RocketState.NOT_LOADED);
}

/** Update world state based on the rocket state
 * If the rocket is NOT_LOADED or HAS_LOADED, no state change occurs.
 * If the rocket is HAS_LAUNCHED, it moves upward by decreasing its Y coordinate.
 * If the rocket moves beyond a Y position of -50 (out of view), resets to its initial state.
 * 
 * @param world The current state of the world
 * @return The updated world state after applying changes
*/
World step(World world){
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

/**
 * Handles keyboard events to control the crawler and rocket in the world
 * @param world The current state of the world
 * @param event The keyboard event triggered by user input
 * @return The updated state of the world after processing the keyboard event
 */
World KeyEvent(World world, KeyEvent event) {
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
        // Load the rocket
        if (event.key() == "Space" && world.rocket_State() == RocketState.NOT_LOADED && world.crawler_X() == CRAWLER_X) {
            return new World(world.width(), world.height(), world.crawler_X(), world.rocket_X(), world.rocket_Y(), RocketState.HAS_LOADED);
        }
        // Launch the rocket
        if (event.key() == "Space" && world.rocket_State() == RocketState.HAS_LOADED && world.crawler_X() == 760) {
            return new World(world.width(), world.height(), world.crawler_X(), world.rocket_X(), world.rocket_Y(), RocketState.HAS_LAUNCHED);
        }
    }
    return world;
}

/** Require the crawler transporter horizontal center's X position*/
int requireCrawlerX(World world) {
    return world.crawler_X();
    }

/** Require the rocket horizontal center's X position*/
Maybe<Integer> requirerocketX(World world){
    return switch (world.rocket_State()) {
        case NOT_LOADED -> new Nothing<Integer>(); //No position available when rocket is not loaded
        case HAS_LOADED -> new Something<Integer>(world.rocket_X()); 
        case HAS_LAUNCHED -> new Something<Integer>(world.rocket_X()); 
    };
}

/** Require the rocket horizontal center's Y position*/
Maybe<Integer> requireRocketY(World world){
    return switch (world.rocket_State()) {
        case NOT_LOADED -> new Nothing<Integer>(); 
        case HAS_LOADED -> new Nothing<Integer>(); 
        case HAS_LAUNCHED -> new Something<Integer>(world.rocket_Y());
    };
}

/** Main funcion*/
void main(String[] args) {
World world = initialState();
    BigBang("Crawler Transporter", world, this::draw, this::step, this::KeyEvent);
}
