=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 51294188
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections: I will utilize collections to store lists of segments that
  represent the snake object in the game. By using collections, I can efficiently
  manage a mutable list for the snake to grow, allowing for dynamic updates during
  gameplay.

  2. File I/O:  will implement File I/O to enable the player to save and exit the
  game. The game state will be written into a text file, allowing for easy retrieval
  and resumption of the game when the user chooses to continue playing.

  3. Inheritance/Subtyping: I will leverage inheritance/subtyping to streamline the
  object hierarchy in my game. By creating a parent GameObj class, I can avoid
  redundant methods in my specific object classes, such as object location, leading
  to more efficient and maintainable code.

  4. Testable Component:  I will incorporate JUnit Tests for each section of my game
  to ensure comprehensive testing of all scenarios, including edge cases. This will
  ensure that the game meets expected behavior and quality standards, leading to a more
  robust and reliable final product.


===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  GameObj: Represents a game object in the game court. Game objects have a position,
  velocity, size, and bounds. They can move, check for intersection with other objects,
  and determine if they hit a wall. The class also provides methods for getting and
  setting the object's attributes such as position, velocity, width, height, and bounds.

  SnakeSegment: a subclass of GameObj and represents one segment of the snake in the Snake
  game. It has a constructor that takes in parameters for velocity in the x and y
  directions, as well as initial position in x and y coordinates. It calls the constructor
  of its superclass GameObj with these parameters along with fixed width and height values
  of 20, and the court width and height from GameCourt class.

  Snake: represents the snake object in the Snake game. It is a subclass of GameObj and
  contains a list of SnakeSegment objects, which represent the segments of the snake's
  body. The Snake class handles methods related to the snake, such as growing, moving,
  changing direction, checking for collision with itself, and drawing itself on the game
  court.

  Food: a subclass of GameObj and represents a food object in the game of Snake. The
  spawnFood() method generates random coordinates for the food object within the court
  boundaries, and checks if it overlaps with any segment of the snake. If it does not
  overlap with the snake, it sets the food object's position to the generated coordinates,
  and marks foodSpawned as true to exit the loop.

  GameCourt: the main logic class that holds the primary game logic for the Snake game.
  It extends JPanel, which is a Swing component used for drawing graphics on the screen.
  The GameCourt class handles the game mechanics, such as updating the game state,
  detecting collisions, handling user input, and managing game objects like the Snake
  and Food. It provides methods for saving and loading game state objects to and from a
  file, allowing players to save their progress and resume the game later.

  RunSnake: the RunSnake class sets up the GUI for the Snake game, manages user input
  from buttons, manages file I/O operations for saving and loading game state, and starts
  the game loop. It serves as the entry point for the Snake game and coordinates the
  interactions between the GUI, game state, and user input.

  Game:  the entry point for starting and running the Snake game. It contains a main method
  that serves as the entry point of the Java application.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  While implementing my design, I had trouble figuring out the logistics of one of my
  design concepts (File I/O). This was the concept I tackled last after getting the
  regular functionality of the game down. I knew that I had to save the important
  game state variables to a file, but I had difficulty with saving the exact position
  of each SnakeSegment present on the GameBoard. I realized I had to restructure my
  Snake to have a static list of all segments that could be written as an object
  into a file.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Overall, I think my design is very strong. The functionality of the game works
  completely according to the traditional style of the game including the ability
  to save your place in the game and resume at a later time. I believe my design
  has very good separation of functionality as each major component was divided
  into individual classes that handle any related methods. My private state is
  encapsulated well and the vast majority of my game state variables are private
  with getter and setter functions that are public. I don't believe I would
  refactor anything given the chance as I feel using subclasses and inheritance
  has allowed me to minimize my code for simplistic purposes.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  N/A
