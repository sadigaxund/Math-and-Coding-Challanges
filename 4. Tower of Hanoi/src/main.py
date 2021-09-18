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
from Tower import Tower
import pygame_gui 
import configparser


config = configparser.ConfigParser()
config.read('settings.ini')
settings = config['COMPONENTS']
colors = config['COLORS']
fetchInt = lambda key : int(settings[key])

############################ <------- START GUI -------> ############################
G2D = Renderer((fetchInt('WINDOW_WIDTH'), fetchInt('WINDOW_HEIGHT')), 'Tower of Hanoi') 
manager = pygame_gui.UIManager(G2D.WINDOW_SIZE)
MARGIN = fetchInt('MARGIN')




############################## <------- END GUI -------> ############################



############################## <------- START GAME -------> #########################

towerSize = (200, 150)
y = G2D.WINDOW_SIZE[1] / 2 - towerSize[1] / 2
xMid = G2D.WINDOW_SIZE[0] / 2 - towerSize[0] / 2
someMargin = 100

towers =    [  
                Tower(G2D, G2D.Rect(xMid - towerSize[0] - someMargin, y, towerSize[0], towerSize[1])), 
                Tower(G2D, G2D.Rect(xMid, y, towerSize[0], towerSize[1])), 
                Tower(G2D, G2D.Rect(xMid + towerSize[0] + someMargin, y, towerSize[0], towerSize[1]))
            ]

def initTowers(startAmount):
    for tower in towers:
        while bool(tower.DISKS):
            tower.pop()

    for i in range(0, startAmount):
        towers[1].addDisk(8 - (startAmount - i - 1))

initTowers(8)

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
            # If player clicks on one of tower
            if towers[i].bounds.collidepoint(event.pos): 
                # If there is no disk on hand, pop one out
                if DISK_ON_HAND == None:
                    DISK_ON_HAND = towers[i].pop()
                    # If player tries to pop from the empty Tower
                    if DISK_ON_HAND != None: 
                        DISK_ON_HAND.pos[1] = towers[i].ROD.top - DISK_ON_HAND.size[1] * 2
                    break
                # If the same tower was clicked
                else:
                    diff = towers[i].bounds.x - DISK_ON_HAND.pos[0]
                    # Size difference between disk on hand and the top disk in tower
                    size_diff = DISK_ON_HAND.size[0] - towers[i].DISKS[-1].size[0] if towers[i].size() > 0 else -1 
                    # If the difference of coordinates is more than tower size then the selected tower is far
                    if abs(diff) < towers[i].bounds.width:
                        # negative means tower has bigger disk
                        if size_diff < 0:
                            towers[i].push(DISK_ON_HAND) # add current disk to the tower
                            DISK_ON_HAND = None # no more current disk
                    else: # if the different tower was clicked
                        DISK_ON_HAND.pos[0] = towers[i].bounds.left + (towers[i].base_width - DISK_ON_HAND.size[0]) / 2
                break

def handleEvent(this, event):
    manager.process_events(event)
    # For future, you can add dragging instead of clicking
    clickTower(this, event)
    
    



def update(this):
    manager.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    manager.draw_ui(G2D.WINDOW)
    pass

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()