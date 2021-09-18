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


from util.graphics import Renderer
import pygame_gui 
import configparser

config = configparser.ConfigParser()
config.read('settings.ini')
settings = config['COMPONENTS']
colors = config['COLORS']
fetchInt = lambda key : int(settings[key])

G2D = Renderer((fetchInt('WINDOW_WIDTH'), fetchInt('WINDOW_HEIGHT')), 'Clock') 
manager = pygame_gui.UIManager(G2D.WINDOW_SIZE)
MARGIN = fetchInt('MARGIN')



class Disk:
    def __init__(self, G2D, bounds, index, color = None) -> None:
        self.pos = [bounds.left, bounds.top]
        self.size = [bounds.width, bounds.height]
        self.G2D = G2D
        self.index = index # index defines the color and the width of the Disk
        self.color = G2D.PEN.GREEN if color == None else color
        pass
    def draw(self):
        self.G2D.PEN.drawRoundRect(G2D.Rect(self.pos[0], self.pos[1],
                                     self.size[0], self.size[1]), 5, self.color, stroke = 0)
        pass
    def __str__(self) -> str:
        return "Disk: " + "{Index:" + str(self.index) + ", Color:" + str(self.color) + ", Position:" + str(self.pos) + ", Size:" + str(self.size) + "}"

class Tower:
 
    def __init__(self, G2D, bounds) -> None:
        self.rod_width = int(bounds.width * 0.06)
        self.rod_height = int(bounds.height*1.35)
        self.base_width = int(bounds.width)
        self.base_height = int(bounds.height* 0.17)
        self.disk_height = int(bounds.height * 0.15)
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
        print(disk)
        self.addDisk(size = disk.size, color = disk.color)
    
    
    
    def draw(self):
        # Draw Rod
        self.ROD = G2D.Rect(self.bounds.left + (self.bounds.width - self.rod_width)/2 - 1, self.bounds.bottom - self.rod_height - self.base_height + 5,
                                    self.rod_width, self.rod_height)
        self.G2D.PEN.drawRoundRect(self.ROD, 2, "#FFFFFF", 0)
        # Draw Disks
        for disk in self.DISKS:
            disk.draw()
        # Draw Base
        self.G2D.PEN.drawRoundRect(G2D.Rect(self.bounds.left, self.bounds.bottom - self.base_height, self.bounds.width, self.base_height), 2, "#964B00", 0)

        pass
    def disksToString(self):
        retval = "[ ----- " 
        for disk in self.DISKS:
            retval += str(disk) + ", "

        return retval[:-2] + " ----- ]" 

    def __str__(self) -> str:
        return "Tower: {Size: "+ str(len(self.DISKS)) + ", Disks:" + self.disksToString() + "}"
    
    

tower_size = (200, 100)

y = G2D.WINDOW_SIZE[1] / 2 - tower_size[1] / 2
xMid = G2D.WINDOW_SIZE[0] / 2 - tower_size[0] / 2
someMargin = 100



towers =    [  
                Tower(G2D, G2D.Rect(xMid - tower_size[0] - someMargin, y, tower_size[0], tower_size[1])), 
                Tower(G2D, G2D.Rect(xMid, y, tower_size[0], tower_size[1])), 
                Tower(G2D, G2D.Rect(xMid + tower_size[0] + someMargin, y, tower_size[0], tower_size[1]))
            ]

def initTowers(startAmount):
    for tower in towers:
        while bool(tower.DISKS):
            tower.pop()

    for i in range(0, startAmount):
        towers[1].addDisk(8 - (startAmount - i - 1))

initTowers(3)

DISK_ON_HAND = None

def draw(this):
    G2D.PEN.fillBackground(colors['BACKGROUND'])
    for tower in towers:
        tower.draw()
    
    if DISK_ON_HAND != None:
        DISK_ON_HAND.draw()
        pass
    # x = self.bounds.left + (self.base_width - w) / 2



def clickTower(this, event):
    global DISK_ON_HAND
    if event.type == G2D.PYGAME_INSTANCE_.MOUSEBUTTONDOWN and event.button == 1:
        l = len(towers)
        for i in range(0, l):
            # if player clicks on one of tower
            if towers[i].bounds.collidepoint(event.pos):
                if DISK_ON_HAND == None:
                    DISK_ON_HAND = towers[i].pop()
                    # If player tries to pop from the empty Tower
                    if DISK_ON_HAND != None:
                        DISK_ON_HAND.pos[1] = towers[i].ROD.top - DISK_ON_HAND.size[1] * 2
                    break
                else:
                    # if the same tower was clicked
                    diff = towers[i].bounds.x - DISK_ON_HAND.pos[0]
                    if abs(diff) < towers[i].bounds.width:
                        towers[i].push(DISK_ON_HAND)
                        DISK_ON_HAND = None
                    # if the different tower was clicked
                    else:
                        DISK_ON_HAND.pos[0] = towers[i].bounds.left + (towers[i].base_width - DISK_ON_HAND.size[0]) / 2
                break

                   
                    

def handleEvent(this, event):
    manager.process_events(event)
    clickTower(this, event)
    
    if event.type == G2D.PYGAME_INSTANCE_.MOUSEBUTTONUP:
            global DRAGING
            if event.button == 1:            
                DRAGING = False
    if event.type == G2D.PYGAME_INSTANCE_.MOUSEMOTION:
        
        pass



def update(this):
    manager.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    manager.draw_ui(G2D.WINDOW)
    pass

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()