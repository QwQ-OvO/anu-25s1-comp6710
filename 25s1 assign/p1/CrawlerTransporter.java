/**
 * Design a World Program which simulates a crawler-transporter.
 * The world should 800 pixels wide and 500 pixels high.
 * The crawler-transporter should have a bounding box that is 80 pixels wide and 20 pixels high
 * (you can use an image to fit into that, draw something of roughly that shape, or just draw a rectangle).
 * It should move between the left and right edges of the window using the left-arrow and right-arrow keys,
 * with neither its left nor right edge ever leaving the window. The bottom edge of the crawler must be 10 pixels
 * above the bottom edge of the world. The crawler starts all the way to the left side of the window.
 *
 * When the crawler is all the way on the left side of the window,
 * the user can press the “Space” key to load a rocket onto it. The crawler can carry only one rocket,
 * which should have a bounding box that is 30 pixels wide and 100 pixels high (you can use an image to fit into that,
 * draw something of roughly that shape, or just draw a rectangle).
 * When it is loaded on to the crawler, its bottom touches the top of the crawler,
 * its horizontal center aligns with the crawler’s horizontal center, and it moves with the crawler.
 *
 * When the crawler is all the way on the right side of window,
 * the user can press the “Space” key to launch the rocket.
 * A launched rocket is not loaded onto the crawler anymore, and keeps accelerating upward until it leaves the window.
 * The speeds and acceleration of the crawler and rocket should be such that it is impossible for the crawler
 * to reach the left side of the window before a launched rocket has left the window.
 * This implies that the crawler cannot load a new rocket until the last launched rocket is forever out of sight.
 *
 * You are free to draw the background of the world however you like,
 * and additional effects like the exhaust of a starting rocket,
 * so long as the rocket and the crawler are always clearly visible.
 * Follow the Design Recipe!
 */