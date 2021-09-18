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
import math
import pygame_gui 
import configparser


config = configparser.ConfigParser()
config.read('settings.ini')
settings = config['COMPONENTS']
colors = config['COLORS']
fetchInt = lambda key : int(settings[key])

G2D = Renderer((fetchInt('WINDOW_WIDTH'), fetchInt('WINDOW_HEIGHT')), 'Tower of Hanoi') 
MANAGER = pygame_gui.UIManager(G2D.WINDOW_SIZE)

############################ <------- START GUI -------> ############################
MARGIN = 5
MENU_HEIGHT = 40
SLIDER_WIDTH = 150
LABEL_WIDTH = 50
LABEL_HEIGHT = MENU_HEIGHT - MARGIN * 2 
LABEL_Y = (MENU_HEIGHT - LABEL_HEIGHT) / 2 - 3
SLIDER_Y = LABEL_Y - 1
BUTTON_SIZE = MENU_HEIGHT * 0.65


MENU_BORDERS = G2D.Rect(0, 0, G2D.WINDOW_SIZE[0], MENU_HEIGHT)
MENU = pygame_gui.elements.UIPanel(MENU_BORDERS, 4, MANAGER)

############################## <------- END GUI -------> ############################
INFO_LABEL_BORDER =  G2D.Rect(MARGIN * 17, LABEL_Y, LABEL_WIDTH * 3, LABEL_HEIGHT)
angleLabel = pygame_gui.elements.UILabel(INFO_LABEL_BORDER, "Branch Angle:", MANAGER, MENU)

SLIDER_BORDER = G2D.Rect(INFO_LABEL_BORDER.right + MARGIN, SLIDER_Y, SLIDER_WIDTH, LABEL_HEIGHT)
slider = pygame_gui.elements.UIHorizontalSlider(SLIDER_BORDER, 30.0, (0.0, 180.0), MANAGER, MENU)



LABEL_BORDER =  G2D.Rect(SLIDER_BORDER.right + MARGIN, LABEL_Y, LABEL_WIDTH * 2, LABEL_HEIGHT)
depthLabel = pygame_gui.elements.UILabel(LABEL_BORDER, "Depth: 9", MANAGER, MENU)

BUTTON_BORDER = G2D.Rect(LABEL_BORDER.right + MARGIN, (MENU_HEIGHT - BUTTON_SIZE)/2 - 3, BUTTON_SIZE, BUTTON_SIZE)
upBtn = pygame_gui.elements.UIButton(BUTTON_BORDER, '↑', MANAGER, MENU,'Add')

BUTTON_BORDER = G2D.Rect(BUTTON_BORDER.right, (MENU_HEIGHT - BUTTON_SIZE)/2 - 3, BUTTON_SIZE, BUTTON_SIZE)
downBtn = pygame_gui.elements.UIButton(BUTTON_BORDER, '↓', MANAGER, MENU,'Remove')

LABEL_BORDER =  G2D.Rect(BUTTON_BORDER.right + MARGIN, LABEL_Y, LABEL_WIDTH * 3, LABEL_HEIGHT)
sizeLabel = pygame_gui.elements.UILabel(LABEL_BORDER, "Branch Length: 5", MANAGER, MENU)

BUTTON_BORDER = G2D.Rect(LABEL_BORDER.right + MARGIN, (MENU_HEIGHT - BUTTON_SIZE)/2 - 3, BUTTON_SIZE, BUTTON_SIZE)
upBtn2 = pygame_gui.elements.UIButton(BUTTON_BORDER, '↑', MANAGER, MENU,'Add')

BUTTON_BORDER = G2D.Rect(BUTTON_BORDER.right, (MENU_HEIGHT - BUTTON_SIZE)/2 - 3, BUTTON_SIZE, BUTTON_SIZE)
downBtn2 = pygame_gui.elements.UIButton(BUTTON_BORDER, '↓', MANAGER, MENU,'Remove')

BUTTON_BORDER = G2D.Rect(BUTTON_BORDER.right + MARGIN * 3, (MENU_HEIGHT - BUTTON_SIZE)/2 - 3, BUTTON_SIZE * 5, BUTTON_SIZE)
rainbowBtn = pygame_gui.elements.UIButton(BUTTON_BORDER, 'RGB Mode', MANAGER, MENU,'Rainbow')

############################## <------- START GAME -------> #########################
RAINBOW = True
BRANCH_ANGLE = 30
BRANCH_LEN = 10
START_DEPTH = 9
START_POS = [G2D.WINDOW_SIZE[0] / 2, G2D.WINDOW_SIZE[1]]

def drawTree(x1, y1, depth, angle = -90):
    global BRANCH_ANGLE, RAINBOW, START_DEPTH, BRANCH_LEN
    if depth > 0:
        length = BRANCH_LEN * 2 if depth == START_DEPTH else BRANCH_LEN
        x2 = x1 + int(math.cos(angle * math.pi / 180) * depth * length)
        y2 = y1 + int(math.sin(angle * math.pi / 180) * depth * length)
        t = G2D.map(depth, 0, START_DEPTH + 1, 1, 10) / 10
        color = G2D.PEN.colorOnRainbow(t) if (RAINBOW) else (255, 255, 255)
        thiccness = 2 if depth > 6 else 1

        G2D.PEN.line((x1, y1), (x2, y2), color, thiccness)
        drawTree(x2, y2, depth - 1, angle - BRANCH_ANGLE)
        drawTree(x2, y2, depth - 1, angle + BRANCH_ANGLE)

def draw(this):
    # Fill Background with the given color in config.ini file
    G2D.PEN.fillBackground(colors['BACKGROUND'])

    drawTree(START_POS[0], START_POS[1], START_DEPTH)






def handleEvent(this, event):
    global BRANCH_ANGLE, START_DEPTH, BRANCH_LEN, RAINBOW
    MANAGER.process_events(event)
    if event.type == G2D.PYGAME_INSTANCE_.USEREVENT:
        if event.user_type == pygame_gui.UI_BUTTON_PRESSED:
            if event.ui_element == rainbowBtn:
                RAINBOW = not RAINBOW
            if event.ui_element == upBtn:
                if START_DEPTH < 14:
                    START_DEPTH += 1
            if event.ui_element == downBtn:
                if START_DEPTH > 1:
                    START_DEPTH -= 1
            if event.ui_element == upBtn2:
                    BRANCH_LEN += 1
            if event.ui_element == downBtn2:
                if BRANCH_LEN > 1:
                    BRANCH_LEN -= 1
    if slider.has_moved_recently:
        BRANCH_ANGLE = int(slider.get_current_value())
        
    



def update(this):
    
    MANAGER.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    MANAGER.draw_ui(G2D.WINDOW)
    angleLabel.set_text("Branch Angle: " + str(BRANCH_ANGLE))
    sizeLabel.set_text("Branch Length: " + str(BRANCH_LEN))
    depthLabel.set_text("Depth: " + str(START_DEPTH))
    pass

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()