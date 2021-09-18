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
