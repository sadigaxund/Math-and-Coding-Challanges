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





from tkinter import *
import os
import colorsys
import types

from pygame import math
import Logic
from math import factorial
from math import pow
def nPr(n, r):
    return factorial(n)/factorial(n-r)


win = Tk()
win.geometry("650x250") #Set the geometry of frame

WINDOW_SIZE = (800, 600)    # set window size
SCREEN_SIZE = (win.winfo_screenwidth(), win.winfo_screenheight())    # get screen size
WINDOW_LOCATION = (SCREEN_SIZE[0] / 2 - WINDOW_SIZE[0] / 2, SCREEN_SIZE[1] / 2 - WINDOW_SIZE[1] / 2 ) # Centralize the window

os.environ['SDL_VIDEO_WINDOW_POS'] = "%d,%d" % WINDOW_LOCATION

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
STRING_ART = False

__DEFAULT = object()


WINDOW = pygame.display.set_mode(WINDOW_SIZE, pygame.RESIZABLE)
carryOn = True # The loop will carry on until the user exit the game (e.g. clicks the close button).
clock = pygame.time.Clock() # The clock will be used to control how fast the screen updates


def init(title = "Program"):
    pygame.init()
    pygame.display.set_caption(title)


def setUpdateLogic(update):
    LOGIC.update = types.MethodType(update, LOGIC)
def setDrawLogic(draw):
    LOGIC.draw = types.MethodType(draw, LOGIC)

def enableRainbowString():
    global STRING_ART
    STRING_ART = True
def disableRainbowString():
    global STRING_ART
    STRING_ART = False


def bezier(pts, t):
    global STRING_ART
    order = len(pts) - 1
    if order == 2:
        return quadratic(pts[0], pts[1], pts[2], t)
    
    v1 = bezier(pts[:-1], t)
    v2 = bezier(pts[1:], t)

    if STRING_ART:
        line((v1[0], v1[1]), (v2[0], v2[1]), RED)

    x = lerp(v1[0], v2[0], t)
    y = lerp(v1[1], v2[1], t)
    return (x, y)



def cubic(p0, p1, p2, p3, t):
        global STRING_ART
        v1 = quadratic(p0, p1, p2, t)
        v2 = quadratic(p1, p2, p3, t)

        if STRING_ART:
            line((v1[0], v1[1]), (v2[0], v2[1]), colorOnRainbow(t))

        x = lerp(v1[0], v2[0], t)
        y = lerp(v1[1], v2[1], t)
        return (x, y)
def quadratic(p0, p1, p2, t):
    global STRING_ART
    x1 = lerp(p0[0], p1[0], t)
    y1 = lerp(p0[1], p1[1], t)
    x2 = lerp(p1[0], p2[0], t)
    y2 = lerp(p1[1], p2[1], t)

    # if STRING_ART:
    #     line((x1, y1), (x2, y2), colorOnRainbow(t))
    
    x = lerp(x1, x2, t)
    y = lerp(y1, y2, t)
    return (x, y)

# function for linear interpolation for a coordinate
def lerp(c0, c1, t):
    return c0 + (c1 - c0) * t
# function for linear interpolation for a vector
def lerp2D(p0, p1, t):
    return (lerp(p0[0], p1[0], t), lerp(p0[1], p1[1], t))


def getMousePos():
    return pygame.mouse.get_pos()

###### <----------- GRAPHICS -----------> ######
def rect(p1, p2, p3, p4):
    pygame.draw.rect(WINDOW, PEN_COLOR, [p1, p2, p3, p4], STROKE_WEIGHT)

def line(p1, p2, color = __DEFAULT):
    if color == __DEFAULT:
        color = PEN_COLOR
    pygame.draw.line(WINDOW, color, p1, p2, STROKE_WEIGHT)
def point(p):
    pygame.draw.circle(WINDOW, PEN_COLOR, p, STROKE_WEIGHT, 0)
def fillBackground(color):
    WINDOW.fill(color)
def vertex(p):
    global LAST_VERTEX
    if LAST_VERTEX == (-1, -1) or p == (-1, -1):
        LAST_VERTEX = p
        return
    
    line(LAST_VERTEX, p)
    LAST_VERTEX = p
                        ## SETTERS ##
def strokeWeight(weight):
    global STROKE_WEIGHT
    STROKE_WEIGHT = weight
def penColor(color):
    global PEN_COLOR
    color = pygame.Color(int(color[0] % 256), int(color[1] % 256), int(color[2] % 256)) # preventing invalid color argument
    PEN_COLOR = color
def colorOnRainbow(hue):
    (r, g, b) = colorsys.hsv_to_rgb(hue, 1.0, 1.0)
    return [int(255 * r), int(255 * g), int(255 * b)]
###### <----------- GRAPHICS -----------> ######

###### <----------- EVENTS ON -----------> ######
def startLoop():
    global carryOn, LOGIC, clock
    while carryOn:
        for event in pygame.event.get(): 
            if event.type == pygame.QUIT: 
                carryOn = False 
        LOGIC.draw()
        LOGIC.update()
        pygame.display.flip()
        clock.tick(FPS)
    pygame.quit()
###### <----------- EVENTS OFF -----------> ###### 

