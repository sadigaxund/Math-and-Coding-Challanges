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

import colorsys
import types
from typing import Any
import Logic
import pygame

class _Pen:
    __BLACK = (0, 0, 0)
    __WHITE = (255, 255, 255)
    __GREEN = (0, 255, 0)
    __RED = (255, 0, 0)
    
    PEN_COLOR = __WHITE
    STROKE_WEIGHT = 1
    __LAST_VERTEX = (-1, -1)
    
    def __init__(self, canvas, pyGame):
        self.PYGAME_INSTANCE_ = pyGame
        self.CANVAS = canvas
    # function for linear interpolation for a coordinate
    def lerp(self, c0, c1, t):
        return c0 + (c1 - c0) * t
    # function for linear interpolation for a vector
    def lerp2D(self, p0, p1, t):
        return (self.lerp(p0[0], p1[0], t), self.lerp(p0[1], p1[1], t))
    ###### <----------- GRAPHICS -----------> ######
    def rect(self, p1, p2, p3, p4):
        self.PYGAME_INSTANCE_.draw.rect(self.CANVAS, self.PEN_COLOR, [p1, p2, p3, p4], self.STROKE_WEIGHT)

    def line(self, p1, p2, color = (-1, -1, -1)):
        if color == (-1, -1, -1):
            color = self.PEN_COLOR
        self.PYGAME_INSTANCE_.draw.line(self.CANVAS, color, p1, p2, self.STROKE_WEIGHT)

    def point(self, p):
        self.PYGAME_INSTANCE_.draw.circle(self.CANVAS, self.PEN_COLOR, p, self.STROKE_WEIGHT, 0)

    def fillBackground(self, color):
        self.CANVAS.fill(color)

    def addVertex(self, p):
        if self.__LAST_VERTEX == (-1, -1) or p == (-1, -1):
            self.__LAST_VERTEX = p
            return
        self.line(self.__LAST_VERTEX, p)
        self.__LAST_VERTEX = p

    def breakVertex(self):
        self.__LAST_VERTEX = (-1, -1)
                            ## SETTERS ##
    def strokeWeight(self, weight):
        self.STROKE_WEIGHT = weight

    def setColor(self, color):
        color = self.PYGAME_INSTANCE_.Color(int(color[0] % 256), int(color[1] % 256), int(color[2] % 256)) # preventing invalid color argument
        self.PEN_COLOR = color

    def colorOnRainbow(hue):
        (r, g, b) = colorsys.hsv_to_rgb(hue, 1.0, 1.0)
        return [int(255 * r), int(255 * g), int(255 * b)]
    ###### <----------- GRAPHICS -----------> ######
    @property
    def BLACK(self):
        return self.__BLACK

    @property
    def WHITE(self):
        return self.__WHITE

    @property
    def GREEN(self):
        return self.__GREEN
        
    @property
    def RED(self):
        return self.__RED

class Renderer:
    PYGAME_INSTANCE_ = pygame
    WINDOW_SIZE = (0,0)
    WINDOW: Any
    FPS = 60
    __LOGIC = Logic.LogicAdapter()

    def __init__(self, windowSize = (800, 600), title = "Program") :
        self.WINDOW_SIZE = windowSize
        self.PYGAME_INSTANCE_.init()
        self.PYGAME_INSTANCE_.display.set_caption(title)
        self.WINDOW = self.PYGAME_INSTANCE_.display.set_mode(self.WINDOW_SIZE, self.PYGAME_INSTANCE_.RESIZABLE)
        self.carryOn = True # The loop will carry on until the user exit the game (e.g. clicks the close button).
        self.clock = self.PYGAME_INSTANCE_.time.Clock() # The clock will be used to control how fast the screen updates
        self.PEN = _Pen(self.WINDOW, self.PYGAME_INSTANCE_)

    def setUpdateLogic(self, update):
        self.__LOGIC.update = types.MethodType(update, self.__LOGIC)
    def setDrawLogic(self, draw):
        self.__LOGIC.draw = types.MethodType(draw, self.__LOGIC)

    def getMousePos(self):
        return self.PYGAME_INSTANCE_.mouse.get_pos()

    ###### <----------- EVENTS ON -----------> ######
    def startLoop(self):
        while self.carryOn:
            for event in self.PYGAME_INSTANCE_.event.get(): 
                if event.type == self.PYGAME_INSTANCE_.QUIT: 
                    self.carryOn = False 
            self.__LOGIC.draw()
            self.__LOGIC.update()
            self.PYGAME_INSTANCE_.display.flip()
            self.clock.tick(self.FPS)
        self.PYGAME_INSTANCE_.quit()
    ###### <----------- EVENTS OFF -----------> ###### 