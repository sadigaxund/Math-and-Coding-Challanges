'''
MIT License

Copyright (c) 2021 Sadig Akhund

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
'''


# from tkinter import *
# win = Tk()
# win.geometry("650x250") #Set the geometry of frame
# SCREEN_SIZE = (win.winfo_screenwidth(), win.winfo_screenheight())    # get screen size

import colorsys
import types

import Logic
from math import factorial
def nPr(n, r):
    return factorial(n)/factorial(n-r)



WINDOW_SIZE = (800, 600)    # set window size

# WINDOW_LOCATION = (SCREEN_SIZE[0] / 2 - WINDOW_SIZE[0] / 2, SCREEN_SIZE[1] / 2 - WINDOW_SIZE[1] / 2 ) # Centralize the window



import pygame

# Define some colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)

PEN_COLOR = [255, 255, 255]

FPS = 60
STROKE_WEIGHT = 1
SHAPE_FILL = False
LOGIC = Logic.LogicAdapter()
LAST_VERTEX = (-1, -1)


__DEFAULT = object()

PYGAME_INSTANCE_ = pygame
WINDOW = PYGAME_INSTANCE_.display.set_mode(WINDOW_SIZE, PYGAME_INSTANCE_.RESIZABLE)
carryOn = True # The loop will carry on until the user exit the game (e.g. clicks the close button).
clock = PYGAME_INSTANCE_.time.Clock() # The clock will be used to control how fast the screen updates


def init(title = "Program"):
    PYGAME_INSTANCE_.init()
    PYGAME_INSTANCE_.display.set_caption(title)


def setUpdateLogic(update):
    LOGIC.update = types.MethodType(update, LOGIC)
def setDrawLogic(draw):
    LOGIC.draw = types.MethodType(draw, LOGIC)

# function for linear interpolation for a coordinate
def lerp(c0, c1, t):
    return c0 + (c1 - c0) * t
# function for linear interpolation for a vector
def lerp2D(p0, p1, t):
    return (lerp(p0[0], p1[0], t), lerp(p0[1], p1[1], t))

def getMousePos():
    return PYGAME_INSTANCE_.mouse.get_pos()

###### <----------- GRAPHICS -----------> ######
def rect(p1, p2, p3, p4):
    PYGAME_INSTANCE_.draw.rect(WINDOW, PEN_COLOR, [p1, p2, p3, p4], STROKE_WEIGHT)

def line(p1, p2, color = __DEFAULT):
    if color == __DEFAULT:
        color = PEN_COLOR
    PYGAME_INSTANCE_.draw.line(WINDOW, color, p1, p2, STROKE_WEIGHT)
def point(p):
    PYGAME_INSTANCE_.draw.circle(WINDOW, PEN_COLOR, p, STROKE_WEIGHT, 0)
def fillBackground(color):
    WINDOW.fill(color)
def vertex(p):
    global LAST_VERTEX
    if LAST_VERTEX == (-1, -1) or p == (-1, -1):
        LAST_VERTEX = p
        return
    
    line(LAST_VERTEX, p)
    LAST_VERTEX = p
def breakVertex():
    global LAST_VERTEX
    LAST_VERTEX = (-1, -1)
                        ## SETTERS ##
def strokeWeight(weight):
    global STROKE_WEIGHT
    STROKE_WEIGHT = weight
def penColor(color):
    global PEN_COLOR
    color = PYGAME_INSTANCE_.Color(int(color[0] % 256), int(color[1] % 256), int(color[2] % 256)) # preventing invalid color argument
    PEN_COLOR = color
def colorOnRainbow(hue):
    (r, g, b) = colorsys.hsv_to_rgb(hue, 1.0, 1.0)
    return [int(255 * r), int(255 * g), int(255 * b)]
###### <----------- GRAPHICS -----------> ######



###### <----------- EVENTS ON -----------> ######
def startLoop():
    global carryOn, LOGIC, clock
    while carryOn:
        for event in PYGAME_INSTANCE_.event.get(): 
            if event.type == PYGAME_INSTANCE_.QUIT: 
                carryOn = False 
        LOGIC.draw()
        LOGIC.update()
        PYGAME_INSTANCE_.display.flip()
        clock.tick(FPS)
    PYGAME_INSTANCE_.quit()
###### <----------- EVENTS OFF -----------> ###### 

