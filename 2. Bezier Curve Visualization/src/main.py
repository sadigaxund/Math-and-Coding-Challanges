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

from graphics import Renderer # Graphics 2D

windowSize = (800, 600)
G2D = Renderer(windowSize, "Bezier Curve Visualization")
STRING_ART = False
VERTICIES = 25
p0 = (0, G2D.WINDOW_SIZE[1] / 2)
p1 = (G2D.WINDOW_SIZE[0] / 4, G2D.WINDOW_SIZE[1])
p2 = (G2D.WINDOW_SIZE[0] * 3 / 4, 0)
p3 = (G2D.WINDOW_SIZE[0], G2D.WINDOW_SIZE[1] / 2)

def stringify(v1, v2, t):
    global STRING_ART
    if STRING_ART:
        G2D.PEN.line((v1[0], v1[1]), (v2[0], v2[1]), G2D.PEN.colorOnRainbow(t))

def bezier(pts, t):
    order = len(pts) - 1
    if order == 2:
        return quadratic(pts[0], pts[1], pts[2], t)
    v1 = bezier(pts[:-1], t)
    v2 = bezier(pts[1:], t)
    stringify(v1, v2, t)
    return G2D.PEN.lerp2D(v1, v2, t)
    # return (x, y)

def cubic(p0, p1, p2, p3, t):
    v1 = quadratic(p0, p1, p2, t)
    v2 = quadratic(p1, p2, p3, t)
    stringify(v1, v2, t)
    return G2D.PEN.lerp2D(v1, v2, t)

def quadratic(p0, p1, p2, t):
    v1 = G2D.PEN.lerp2D(p0, p1, t)
    v2 = G2D.PEN.lerp2D(p1, p2, t)
    stringify(v1, v2, t)
    return G2D.PEN.lerp2D(v1, v2, t)

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
    for step in range(0, VERTICIES + 1):
        t = step / VERTICIES
        G2D.PEN.addVertex(bezier(pts, t))
    G2D.PEN.breakVertex()


def update(this):
    pass

G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()