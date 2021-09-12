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
class Geometry:
    def __init__(self, G2D) -> None:
        self.STRING_ART = False
        self.__G2D = G2D

    def bezier(self, pts, t):
        order = len(pts) - 1
        if order == 2:
            return self.quadratic(pts[0], pts[1], pts[2], t)
        v1 = self.bezier(pts[:-1], t)
        v2 = self.bezier(pts[1:], t)
        self.stringify(v1, v2, t)
        return self.__G2D.PEN.lerp2D(v1, v2, t)
        # return (x, y)
    def cubic(self, p0, p1, p2, p3, t):
        v1 = self.quadratic(p0, p1, p2, t)
        v2 = self.quadratic(p1, p2, p3, t)
        self.stringify(v1, v2, t)
        return self.__G2D.PEN.lerp2D(v1, v2, t)

    def quadratic(self, p0, p1, p2, t):
        v1 = self.__G2D.PEN.lerp2D(p0, p1, t)
        v2 = self.__G2D.PEN.lerp2D(p1, p2, t)
        self.stringify(v1, v2, t)
        return self.__G2D.PEN.lerp2D(v1, v2, t)

    def stringify(self, v1, v2, t):
        w = self.__G2D.PEN.STROKE_WEIGHT
        self.__G2D.PEN.STROKE_WEIGHT = int(self.__G2D.PEN.STROKE_WEIGHT * 0.7)
        if self.STRING_ART:
            self.__G2D.PEN.line(v1, v2, self.__G2D.PEN.colorOnRainbow(t))
        self.__G2D.PEN.STROKE_WEIGHT = w