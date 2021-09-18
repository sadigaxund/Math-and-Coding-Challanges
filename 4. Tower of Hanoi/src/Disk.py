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

class Disk:
    def __init__(self, G2D, bounds, index, color = None) -> None:
        self.pos = [bounds.left, bounds.top] # Location of the Disk
        self.size = [bounds.width, bounds.height] # Size of the Disk
        self.G2D = G2D  # Graphics tool
        self.index = index # index defines the color and the width of the Disk
        self.color = G2D.PEN.GREEN if color == None else color

    def draw(self):
        self.G2D.PEN.drawRoundRect(self.G2D.Rect(self.pos[0], self.pos[1],
                                     self.size[0], self.size[1]), 5, self.color, stroke = 0)

    def __str__(self) -> str:
        return "Disk: " + "{Index:" + str(self.index) + ", Color:" + str(self.color) + ", Position:" + str(self.pos) + ", Size:" + str(self.size) + "}"
