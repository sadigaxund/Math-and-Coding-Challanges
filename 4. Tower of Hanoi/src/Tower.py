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

from Disk import Disk
class Tower:
    def __init__(self, G2D, bounds) -> None:
        self.base_width = int(bounds.width) # Width of the Base of a Tower
        self.base_height = int(bounds.height * 0.13) # Height of the Base of a Tower
        self.rod_width = int(bounds.width * 0.06) # Width of the Rod of a Tower
        self.rod_height = int(bounds.height - self.base_height) # Height of the Rod of a Tower
        self.disk_height = int(bounds.height * 0.09) # Height of the Disks
        # Width is going to be determined individually
        self.hitbox = bounds # Hitbox of the Tower
        self.G2D = G2D # Graphics tool
        self.DISKS = [] # List of Disks

    def addDisk(self, index = None, size = None, color = None):
        disks_amount = len(self.DISKS) + 1 # the number of disks after adding the disk, needed for calculation
        index = disks_amount + 1 if index == None else index + 1 
        t =  (10 - index + 1) / 15 # coefficient that defines the width and the color of the disk

        w = self.base_width * t
        h = self.disk_height
        (w, h) = (w, h) if size == None else size # if the size is given earlier use that

        x = self.hitbox.left + (self.base_width - w) / 2
        y = self.hitbox.bottom - self.base_height - self.disk_height * disks_amount
        
        color = self.G2D.PEN.colorOnRainbow(t * 3 / 2 + 0.1) if color == None else color # if the color is given earlier use that
        self.DISKS.append(Disk(self.G2D, self.G2D.Rect(x, y, w, h), index, color)) # add to list
    
    # Method to pop a disk from the stack of the tower
    def pop(self):
        if(not bool(self.DISKS)):
            return None
        return self.DISKS.pop()

    # Method to push a disk to the stack of the tower
    def push(self, disk):
        self.addDisk(size = disk.size, color = disk.color)
    
    def draw(self):
        # Draw Rod
        rodX = self.hitbox.left + (self.hitbox.width - self.rod_width)/2 - 1
        rodY = self.hitbox.bottom - self.rod_height - self.base_height + 5
        self.ROD = self.G2D.Rect(rodX, rodY, self.rod_width, self.rod_height) # Rod hitbox
        self.G2D.PEN.drawRoundRect(self.ROD, 2, "#FFFFFF", 0)
        # Draw Disks
        for disk in self.DISKS:
            disk.draw()
        # Draw Base
        self.G2D.PEN.drawRoundRect(self.G2D.Rect(self.hitbox.left, self.hitbox.bottom - self.base_height, 
                                    self.hitbox.width, self.base_height), 2, "#964B00", 0)

    def size(self):
        return len(self.DISKS)
        
    def disksToString(self):
        retval = "[ ----- " 
        for disk in self.DISKS:
            retval += str(disk) + ", "
        return retval[:-2] + " ----- ]" 

    def __str__(self) -> str:
        return "Tower: {Size: "+ str(len(self.DISKS)) + ", Disks:" + self.disksToString() + "}"