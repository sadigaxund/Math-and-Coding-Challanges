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

class Pen:
    __BLACK = (0, 0, 0)
    __WHITE = (255, 255, 255)
    __GREEN = (0, 255, 0)
    __RED = (255, 0, 0)
    
    PEN_COLOR = __WHITE
    STROKE_WEIGHT = 3
    __LAST_VERTEX = None
    
    def __init__(self, canvas, pyGame):
        self.PYGAME_INSTANCE_ = pyGame
        self.CANVAS = canvas
        # self.rect = self.PYGAME_INSTANCE_.draw.rect
        
   
    ###### <----------- GRAPHICS -----------> ######
    def drawRect(self, rect, color = None, stroke = None):
        if color == None:
            color = self.PEN_COLOR
        if stroke == None:
            stroke = self.STROKE_WEIGHT
        return self.PYGAME_INSTANCE_.draw.rect(self.CANVAS, color, rect, stroke)

    def line(self, p1, p2, color = None, stroke = None):
        if color == None:
            color = self.PEN_COLOR
        if stroke == None:
            stroke = self.STROKE_WEIGHT
        return self.PYGAME_INSTANCE_.draw.line(self.CANVAS, color, p1, p2, stroke)

    def point(self, p, color = None, stroke = None):
        if color == None:
            color = self.PEN_COLOR
        if stroke == None:
            stroke = self.STROKE_WEIGHT
        return self.PYGAME_INSTANCE_.draw.circle(self.CANVAS, color, p, stroke, 0)

    def circle(self, center, radius = 0, color = None, stroke = None):
        if color == None:
            color = self.PEN_COLOR
        if stroke == None:
            stroke = self.STROKE_WEIGHT
        return self.PYGAME_INSTANCE_.draw.circle(self.CANVAS, color, center, radius, stroke)

    def fillBackground(self, color):
        self.CANVAS.fill(color)

    def addVertex(self, p = None):
        if self.__LAST_VERTEX == None or p == None:
            self.__LAST_VERTEX = p
            return
        self.line(self.__LAST_VERTEX, p)
        self.__LAST_VERTEX = p

    def breakVertex(self):
        self.__LAST_VERTEX = None
                            ## SETTERS ##
    def strokeWeight(self, weight):
        self.STROKE_WEIGHT = weight

    def setColor(self, color):
        color = self.PYGAME_INSTANCE_.Color(int(color[0] % 256), int(color[1] % 256), int(color[2] % 256)) # preventing invalid color argument
        self.PEN_COLOR = color

    def colorOnRainbow(self, hue):
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