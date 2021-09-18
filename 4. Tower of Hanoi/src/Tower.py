from Disk import Disk
class Tower:
    def __init__(self, G2D, bounds) -> None:
        self.rod_width = int(bounds.width * 0.06)
        self.rod_height = int(bounds.height)
        self.base_width = int(bounds.width)
        self.base_height = int(bounds.height * 0.15)
        self.disk_height = int(bounds.height * 0.13)
        self.bounds = bounds
        self.G2D = G2D
        # self.DISK_AMOUNT = 0
        self.DISKS = []

    def addDisk(self, index = None, size = None, color = None):
        disks_amount = len(self.DISKS) + 1 # the number of disks after adding the disk, needed for calculation
        index = disks_amount + 1 if index == None else index + 1 
        t =  (10 - index + 1) / 15 # coefficient that defines the width and the color of the disk

        w = self.base_width * t
        h = self.disk_height
        (w, h) = (w, h) if size == None else size

        x = self.bounds.left + (self.base_width - w) / 2
        y = self.bounds.bottom - self.base_height - self.disk_height * disks_amount
        
        color = self.G2D.PEN.colorOnRainbow(t * 3 / 2 + 0.1) if color == None else color
        self.DISKS.append(Disk(self.G2D, self.G2D.Rect(x, y, w, h), index, color))
        
    def pop(self):
        if(not bool(self.DISKS)):
            return None
        return self.DISKS.pop()

    def push(self, disk):
        self.addDisk(size = disk.size, color = disk.color)
    
    def draw(self):
        # Draw Rod
        rodX = self.bounds.left + (self.bounds.width - self.rod_width)/2 - 1
        rodY = self.bounds.bottom - self.rod_height - self.base_height + 5
        self.ROD = self.G2D.Rect(rodX, rodY, self.rod_width, self.rod_height)
        self.G2D.PEN.drawRoundRect(self.ROD, 2, "#FFFFFF", 0)
        # Draw Disks
        for disk in self.DISKS:
            disk.draw()
        # Draw Base
        self.G2D.PEN.drawRoundRect(self.G2D.Rect(self.bounds.left, self.bounds.bottom - self.base_height, 
                                    self.bounds.width, self.base_height), 2, "#964B00", 0)

    def size(self):
        return len(self.DISKS)
    def disksToString(self):
        retval = "[ ----- " 
        for disk in self.DISKS:
            retval += str(disk) + ", "
        return retval[:-2] + " ----- ]" 

    def __str__(self) -> str:
        return "Tower: {Size: "+ str(len(self.DISKS)) + ", Disks:" + self.disksToString() + "}"