class Point:
    def __init__(self, x, y) -> None:
        self.x = x
        self.y = y
        pass
    def convert2Point(tuple):
        return Point(tuple[0], tuple[1])
    def __add__(self, pt):
        return Point(self.x + pt.x, self.y + pt.y)
    def __sub__(self, pt):
        return Point(self.x - pt.x, self.y - pt.y)
    def __neg__(self):
        return Point(-self.x, -self.y)
        
    def __getitem__(self, i):
        return (self.x, self.y)[i]
    def __str__(self):
        return "Point(" + str(self.x) + ", " + str(self.y) + ")"