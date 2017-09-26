# Game of Life

## Introduction
I decided to create a version of the game of life that is is used via a GUI.

## How to use
To get started just download the jar from [here](/bin/GameOfLive.jar)

Then launch it by double-clicking on the downloaded file or using the following command from the terminal: `java -jar GameOfLife.jar` (Make sure your working directory contains the downloaded file)


When launching from the terminal you have the option to use the following arguments:

`--verbose` -> Causes the program to output information about what the user is doing to the terminal 	|

&nbsp;

![Field-Component](/img/FieldComponent.png)
_img1_

The big grid in the upper half (see img1) displays the GameOfLife-Field. White cells are dead, while the red ones are alive.

Right after launching, all cells are dead but you can click on any cell to toggle its alive-state.

![RoundControls](/img/RoundControl.png)

Below the field you can find the controls to go through different rounds of the game. Either click the button "Next round" to go to the next round or check "Auto-Round" to automatically go to the next round in the desired frequency. The frequency can be chosen using the slider below the button and the checkbox.

![StatsComponent](/img/StatsComponent.png)

At the bottom are a simple visual and a text which indicate how many cells on the field are alive.
