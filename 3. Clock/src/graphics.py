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


import types
from typing import Any
import Logic
import pygame
from Pen import Pen


class Renderer:
    PYGAME_INSTANCE_ = pygame
    WINDOW_SIZE = (0,0)
    WINDOW: Any
    FPS = 60
    __LOGIC = Logic.LogicAdapter()

    def __init__(self, windowSize = (800, 600), title = "") :
        self.WINDOW_SIZE = windowSize
        self.PYGAME_INSTANCE_.init()
        self.PYGAME_INSTANCE_.display.set_caption(title)
        self.WINDOW = self.PYGAME_INSTANCE_.display.set_mode(self.WINDOW_SIZE, self.PYGAME_INSTANCE_.RESIZABLE)
        self.carryOn = True # The loop will carry on until the user exit the game (e.g. clicks the close button).
        self.clock = self.PYGAME_INSTANCE_.time.Clock() # The clock will be used to control how fast the screen updates
        self.PEN = Pen(self.WINDOW, self.PYGAME_INSTANCE_)
        self.Rect = self.PYGAME_INSTANCE_.Rect

    def setUpdateLogic(self, update):
        self.__LOGIC.update = types.MethodType(update, self.__LOGIC)
    def setDrawLogic(self, draw):
        self.__LOGIC.draw = types.MethodType(draw, self.__LOGIC)
    def setEventLogic(self, eventHandler):
        self.__LOGIC.handleEvent = types.MethodType(eventHandler, self.__LOGIC)

    def getMousePos(self):
        return self.PYGAME_INSTANCE_.mouse.get_pos()
    def getEdges(self, origin, width, height):
        p1 = (origin[0], origin[1])
        p2 = (origin[0] + width, origin[1])
        p3 = (origin[0], origin[1] + height)
        p4 = (origin[0] + width, origin[1] + height)
        return [p1, p2, p3, p4]
    ###### <----------- EVENTS ON -----------> ######
    def startLoop(self):
        while self.carryOn:
            self.clock.tick(self.FPS)
            for event in self.PYGAME_INSTANCE_.event.get(): 
                if event.type == self.PYGAME_INSTANCE_.QUIT: 
                    self.carryOn = False 
                self.__LOGIC.handleEvent(event)
            self.__LOGIC.draw()
            self.__LOGIC.update()
            self.PYGAME_INSTANCE_.display.update()
            
        self.PYGAME_INSTANCE_.quit()
    ###### <----------- EVENTS OFF -----------> ###### 