# Conways-Game-of-Life
The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970<sup>[1](https://en.wikipedia.org/wiki/Conways_Game_of_Life)</sup>.

The universe of this game consists of an infinite space or grid of cells. The cells have 2 state of existance, either dead or alive. Every cell can affect neigbouring eight cells similar to the game of <a href = "https://github.com/sadigaxund/Minesweeper"> `Minesweeper`</a>. The time unit in this universe is considered as one <u>step</u>. <br>There are some set of rules that each cell must follow after each step. They are:
<ol>
  <li>Any live cell with fewer than two live neighbours dies.</li>
  <li>Any live cell with two or three live neighbours lives on to the next generation.</li>
  <li>Any live cell with more than three live neighbours dies.</li>
  <li>Any dead cell with exactly three live neighbours becomes a live cell.</li>
 </ol>
 In summary, if we wanted to write it as function, it would be as following:<br>
 <pre>
 if number of live neigbours is 2 or 3:
     set cell live
 else:
     kill cell</pre>

The initial pattern that we can set is our <u>seed</u> and the the first step creates universe's <u>first generation</u>.



## Presentation
### YouTube 
<a href = "https://www.youtube.com/watch?v=Gdro5uM6_o8"> video link</a>


### ACORN Pattern
![alt text](https://github.com/sadigaxund/Math-and-Coding-Challanges/blob/main/1.%20Conway's%20Game%20of%20Life/res/pre_img/acorn_vid.gif "vid_acorn")
### GUN Pattern
![alt text](https://github.com/sadigaxund/Math-and-Coding-Challanges/blob/main/1.%20Conway's%20Game%20of%20Life/res/pre_img/gun_vid.gif "gun_vid")
### Draw Feature
![alt text](https://github.com/sadigaxund/Math-and-Coding-Challanges/blob/main/1.%20Conway's%20Game%20of%20Life/res/pre_img/draw_vid.gif "draw_vid")
### Custom Pattern from Image file
![java (1)](https://user-images.githubusercontent.com/48419889/134262949-5a6d6937-9493-487f-84e2-6f7e4ba53d0e.png)



## Attribution
  - One or more of the icons used in this project were made by <b> `Pixel perfect`</b> from www.flaticon.com
  - One or more of the icons used in this project were made by <b> `Freepik`</b> from www.flaticon.com
  - The main algorithm of this project is based on the <a href="https://pzemtsov.github.io/">`Pavel Zemtsov`</a>'s optimization experiments. <a href="https://pzemtsov.github.io/2015/04/24/game-of-life-hash-tables-and-hash-codes.html">Source</a>.
  -  <i>Conway's Game of Life</i> project was inspired by the <a href="https://youtu.be/HeQX2HjkcNo?t=60">YouTube video</a> by <a href = "https://www.youtube.com/channel/UCHnyfMqiRRG1u-2MsSQLbXA"> `Veritasium`</a>.

