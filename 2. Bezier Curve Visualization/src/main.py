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

from Bezier import Geometry
from graphics import Renderer # Graphics 2D


windowSize = (800, 600)
G2D = Renderer(windowSize, "Bezier Curve Visualization")
bezierDrawer = Geometry(G2D)
STRING_ART = False
VERTICIES = 25
p0 = (0, G2D.WINDOW_SIZE[1] / 2)
p1 = (G2D.WINDOW_SIZE[0] / 4, G2D.WINDOW_SIZE[1])
p2 = (G2D.WINDOW_SIZE[0] * 3 / 4, 0)
p3 = (G2D.WINDOW_SIZE[0], G2D.WINDOW_SIZE[1] / 2)



def draw(this):
    global STRING_ART
    G2D.PEN.fillBackground(G2D.PEN.BLACK)
    G2D.PEN.strokeWeight(1)
    G2D.PEN.setColor([0,255,0])

    #to control points:
    # p1 = G2D.getMousePos()

    G2D.PEN.setColor(G2D.PEN.GREEN)
    # STRING_ART = True
    pts = [(0, 600), (800, 0), (0, 0), (800, 600)]
    # bezierDrawer.setStringArt(True)
    for step in range(0, VERTICIES + 1):
        t = step / VERTICIES
        G2D.PEN.addVertex(bezierDrawer.bezier(pts, t))
    G2D.PEN.breakVertex()
    for step in range(0, VERTICIES + 1):
        t = step / VERTICIES
        G2D.PEN.addVertex(bezierDrawer.bezier(pts, t))
    G2D.PEN.breakVertex()


def update(this):
    pass

G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()