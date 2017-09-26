# Game of Life

## Introduction
I decided to create a version of the game of life that is is used via a GUI.

## How to use
To get started just download the jar from [here](bin/GameOfLife.jar)

Then launch it by double-clicking on the downloaded file or using the following command from the terminal: `java -jar GameOfLife.jar` (Make sure your working directory contains the downloaded file)


When launching from the command-line you have the option to use the following arguments:

`--verbose` -> Causes the program to output information about what the user is doing to the terminal 	|

### Field

![Field-Component](/img/FieldComponent.png)

The big grid in the upper half displays the _GameOfLife-Field_. White cells are dead, while the red ones are alive.

Right after launching, all cells are dead but you can **click on any cell to toggle its alive-state**.

### Controls

![RoundControls](/img/RoundControl.png)

Below the field you can find the controls to go through different rounds of the game. Either click the button "Next round" to go to the next round or check "Auto-Round" to **automatically go to the next round in a given frequency**. The frequency can be chosen using the slider below the button and the checkbox.

### Stats

![StatsComponent](/img/StatsComponent.png)

At the bottom are a simple visual and a text which indicate how many cells on the field are alive.

### Settings

To use more features, **right-click onto the field-grid**.

This will open a context menu, which allows you to perform the following actions:
* Kill all cells (return to default cells)
* Resize the field (height and width must be between 1 and 100)
* Export the current state of the field (to the clipboard or a file)
* Import a previously exported state (from file or via an input-prompt) or a predefined pattern
* Go through all rounds until all cells are dead. (This will print out some info about what happended or timeout after 1.5 seconds)

## More

1) I experimented with multithreading when the calculating the rounds, but decided to stick with the more simple, single-threaded version that is currently in this repo, since my experiments showed that my method of splitting the workload showed effects only when calculating fields with about 3.8 million cells or more (on amd fx-8350).
2) The JavaDoc can be found [here](https://moritzbruder.github.io/GameOfLife/)
