
class Geometry:
    def __init__(self, G2D) -> None:
        self.__STRING_ART = False
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
        print(t)
        self.stringify(v1, v2, t)
        return self.__G2D.PEN.lerp2D(v1, v2, t)

    def stringify(self, v1, v2, t):
        if self.__STRING_ART:
            self.__G2D.PEN.line(v1, v2, self.__G2D.PEN.colorOnRainbow(t))
    
    def setStringArt(self, flag):
        self.__STRING_ART = flag