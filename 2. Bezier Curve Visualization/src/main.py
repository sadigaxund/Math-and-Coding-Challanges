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

import graphics as G2D
from numpy import arange

VERTICIES = 25
p0 = (0, G2D.WINDOW_SIZE[1] / 2)
p1 = (G2D.WINDOW_SIZE[0] / 4, G2D.WINDOW_SIZE[1])
p2 = (G2D.WINDOW_SIZE[0] * 3 / 4, 0)
p3 = (G2D.WINDOW_SIZE[0], G2D.WINDOW_SIZE[1] / 2)

G2D.init("Bezier Visualization")


def draw(this):
    G2D.fillBackground(G2D.BLACK)
    G2D.strokeWeight(1)
    G2D.penColor([0,255,0])

    #to control points:
    # p1 = G2D.getMousePos()

    G2D.penColor(G2D.GREEN)
    # G2D.enableRainbowString()
    pts = [(0, 600), (800, 0), (0, 0), (800, 600)]
    for t in arange(0.0, 1.0001, 1.0 / VERTICIES):
        G2D.vertex(G2D.bezier(pts, t))
        

    G2D.vertex((-1, -1))


def update(this):
    pass

G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()