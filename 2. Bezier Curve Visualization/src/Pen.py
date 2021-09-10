import colorsys

class Pen:
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
        # self.rect = self.PYGAME_INSTANCE_.draw.rect
        
    # function for linear interpolation for a coordinate
    def lerp(self, c0, c1, t):
        return c0 + (c1 - c0) * t
    # function for linear interpolation for a vector
    def lerp2D(self, p0, p1, t):
        return (self.lerp(p0[0], p1[0], t), self.lerp(p0[1], p1[1], t))
    ###### <----------- GRAPHICS -----------> ######
    def drawRect(self, rect):
        return self.PYGAME_INSTANCE_.draw.rect(self.CANVAS, self.PEN_COLOR, rect, self.STROKE_WEIGHT)

    def line(self, p1, p2, color = (-1, -1, -1)):
        if color == (-1, -1, -1):
            color = self.PEN_COLOR
        return self.PYGAME_INSTANCE_.draw.line(self.CANVAS, color, p1, p2, self.STROKE_WEIGHT)

    def point(self, p):
       return self.PYGAME_INSTANCE_.draw.circle(self.CANVAS, self.PEN_COLOR, p, self.STROKE_WEIGHT, 0)

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