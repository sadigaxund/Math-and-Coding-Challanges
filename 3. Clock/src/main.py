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

from pygame.mixer import stop
from graphics import Renderer
from datetime import datetime
from tzlocal import get_localzone
import pygame_gui 
import pytz
import math
import time
import configparser
config = configparser.ConfigParser()
config.read('settings.ini')
settings = config['COMPONENTS']
colors = config['COLORS']
fetchInt = lambda key : int(settings[key])

G2D = Renderer((fetchInt('WINDOW_WIDTH'), fetchInt('WINDOW_HEIGHT')), 'Clock') 
manager = pygame_gui.UIManager(G2D.WINDOW_SIZE)
CURRENT_TIMEZONE = str(get_localzone())

MARGIN = fetchInt('MARGIN')
COMPONENT_HEIGHT = fetchInt('COMPONENT_HEIGHT')
PANEL_WIDTH = fetchInt('PANEL_WIDTH')
DIGIT_LABEL_WIDTH = fetchInt('DIGIT_LABEL_WIDTH')
TIMEZONE_LABEL_WIDTH = fetchInt('TIMEZONE_LABEL_WIDTH')
CLOCK_RADIUS = fetchInt('CLOCK_RADIUS')

LABEL_HEIGHT = COMPONENT_HEIGHT - MARGIN * 2 
BUTTON_WIDTH = PANEL_WIDTH / 2 - MARGIN * 2


CANVAS_BORDER = G2D.Rect(PANEL_WIDTH, 0, G2D.WINDOW_SIZE[0] - PANEL_WIDTH, G2D.WINDOW_SIZE[1])
BOTTOM_MENU_BORDERS = G2D.Rect(0, 0, PANEL_WIDTH, G2D.WINDOW_SIZE[1])
bottom_panel = pygame_gui.elements.UIPanel(BOTTOM_MENU_BORDERS, 4, manager)
TEMPO =  G2D.Rect(-MARGIN, MARGIN, PANEL_WIDTH - TIMEZONE_LABEL_WIDTH - MARGIN, LABEL_HEIGHT)
timezoneLabel = pygame_gui.elements.UILabel(TEMPO, 
                                            str(CURRENT_TIMEZONE), 
                                            manager, 
                                            bottom_panel)
TEMPO = G2D.Rect(BOTTOM_MENU_BORDERS.right - TIMEZONE_LABEL_WIDTH - MARGIN * 4, 
                                        MARGIN, 
                                        TIMEZONE_LABEL_WIDTH - MARGIN, 
                                        LABEL_HEIGHT)
vertexLabel = pygame_gui.elements.UILabel(TEMPO, 
                                        "00:00:00", 
                                        manager, 
                                        bottom_panel)
TEMPO = G2D.Rect(MARGIN, TEMPO.bottom + MARGIN, BOTTOM_MENU_BORDERS.width - MARGIN * 3 , COMPONENT_HEIGHT)
dropDownMenu = pygame_gui.elements.UIDropDownMenu(pytz.common_timezones,
                                    CURRENT_TIMEZONE,
                                    TEMPO,
                                    manager = manager,
                                    container = bottom_panel)

snap_button = pygame_gui.elements.UIButton(G2D.Rect(MARGIN , COMPONENT_HEIGHT * 2 + MARGIN, BUTTON_WIDTH, COMPONENT_HEIGHT),
                                      'Snapping',
                                      manager,
                                      object_id='#snapping_button',
                                      container = bottom_panel)
rgb_button = pygame_gui.elements.UIButton(G2D.Rect(TEMPO.right - BUTTON_WIDTH, COMPONENT_HEIGHT * 2 + MARGIN, BUTTON_WIDTH, COMPONENT_HEIGHT),
                                      'RGB',
                                      manager,
                                      object_id='#snapping_button',
                                      container = bottom_panel) 
MID_POINT = (0, 0)
def updatePivotPoint():
    global MID_POINT
    MID_POINT = (CANVAS_BORDER.left + CANVAS_BORDER.width / 2, CANVAS_BORDER.top + CANVAS_BORDER.height / 2)

updatePivotPoint()

'''
Dictionary contains 2 values:
    1. angle from the origin (mirrored by x-axis)
    2. small offset for design purposes (you may tweak a bit)
'''
label_map = {
        "3"     : [ 0,           (-DIGIT_LABEL_WIDTH * 2  , -DIGIT_LABEL_WIDTH / 2) ],
        "6"     : [ math.pi / 2, (-DIGIT_LABEL_WIDTH / 2  , -DIGIT_LABEL_WIDTH * 2) ],
        "9"     : [ math.pi,     (+DIGIT_LABEL_WIDTH      , -DIGIT_LABEL_WIDTH / 2) ],
        "12"    : [-math.pi/2,   (-DIGIT_LABEL_WIDTH / 2  , +DIGIT_LABEL_WIDTH) ],
        }
'''
Method for updating digit labels' positions when resized
'''
def updateLabel(origin, id):
    value  = label_map[id]
    angle  = value[0]
    offset = value[1]
    x = origin[0] + CLOCK_RADIUS * math.cos(angle)
    y = origin[1] + CLOCK_RADIUS * math.sin(angle)
    BORDER = G2D.Rect(x + offset[0], y + offset[1], DIGIT_LABEL_WIDTH, DIGIT_LABEL_WIDTH)
    return pygame_gui.elements.UILabel(BORDER, id, manager)

label3 = updateLabel(MID_POINT, "3")
label6 = updateLabel(MID_POINT, "6")  
label9 = updateLabel(MID_POINT, "9")
label12 = updateLabel(MID_POINT, "12")

'''
Function that maps a value to anther provided an initial and a desired range
'''


RAINBOW_MODE = False    # if True: colors are of rainbow pattern, else: normal color
SNAP_MODE = True        # if True: clock's hands are snapping to the next track, else: smooth transition
'''
Note: Since pygame draws as user calls the function, the order in which clock is drawn is important.  
'''
def draw(this):
    # Draw Canvas 
    updatePivotPoint()
    G2D.PEN.fillBackground(colors['BACKGROUND'])
    G2D.PEN.circle(MID_POINT, CLOCK_RADIUS, colors['INNER_RING'], 0)
    trackColors = (None, None) if RAINBOW_MODE else (colors['SMALL_TRACKS'], colors['BIG_TRACKS'])
    drawTracks(MID_POINT, fetchInt('SMALL_TRACKS'), range(0, 60), 4, trackColors[0])
    drawTracks(MID_POINT, fetchInt('BIG_TRACKS'), range(0, 12, 3), 6, trackColors[1])   
    G2D.PEN.circle(MID_POINT, CLOCK_RADIUS, colors['OUTER_RING'], 5)
    G2D.PEN.point(MID_POINT, colors['INNER_PIVOT'], 10)
    tickClock()
    G2D.PEN.point(MID_POINT, colors['OUTER_PIVOT'], 6)

def tickClock():
    datetime_NY = datetime.now(pytz.timezone(CURRENT_TIMEZONE))
    sec = datetime_NY.strftime("%S")
    min = datetime_NY.strftime("%M")
    hrs = datetime_NY.strftime("%H")

    timezoneLabel.set_text(CURRENT_TIMEZONE)
    vertexLabel.set_text(hrs + ":" + min + ":" + sec)

    if(SNAP_MODE):
        sec = str(float(sec) + round(time.time() * 1000) % 1000 / 1000)
        min = str(float(min) + float(sec) / 60.0)
        hrs = str(float(hrs) + float(min) / 60.0)
   
    drawHand((MID_POINT[0] - 1, MID_POINT[1] - 1), sec, CLOCK_RADIUS * 0.7, 0, RAINBOW_MODE)
    drawHand((MID_POINT[0] - 1, MID_POINT[1] - 1), min, CLOCK_RADIUS * 0.6, 1, RAINBOW_MODE)
    drawHand((MID_POINT[0] - 1, MID_POINT[1] - 1), hrs, CLOCK_RADIUS * 0.5, 2, RAINBOW_MODE)
    pass
'''
id = 0 : seconds
id = 1 : minutes
id = 2 : hours
'''
def drawHand(origin, time, len, id =  0, rainbowMode = True):
    coef = 30 if id == 2 else 6
    angle = (-90 + float(time) * coef) * math.pi / 180
    coordinates = (origin[0] + len * math.cos(angle), origin[1] + len * math.sin(angle))
    max = 12 if id == 2 else 60
    color = G2D.PEN.colorOnRainbow(G2D.map(float(time), 0, max, 0, 100) / 100) if rainbowMode else (G2D.PEN.RED, G2D.PEN.GREEN, G2D.PEN.BLACK)[id]
    G2D.PEN.line(origin, coordinates, color, 4 + id)

def drawTracks(origin, len, iter, stroke = 4, color = None):
    int_div = lambda n, d: (n + d // 2) // d
    size = int_div(iter.stop - iter.start, iter.step)
    for i in iter:
        track_angle = (-90 + i * (360 / size)) * math.pi / 180
        x0 = origin[0] + (CLOCK_RADIUS - len) * math.cos(track_angle)
        y0 = origin[1] + (CLOCK_RADIUS - len) * math.sin(track_angle)
        x1 = origin[0] + (CLOCK_RADIUS - 3) * math.cos(track_angle) # -3 is need because tracks are being drawn out of clock
        y1 = origin[1] + (CLOCK_RADIUS - 3) * math.sin(track_angle)
        t = G2D.map(i, 0, size, 0, 100) / 100
        rgb = G2D.PEN.colorOnRainbow(t) if color == None else color
        G2D.PEN.line((x0, y0), (x1, y1), rgb, stroke)

def handleEvent(this, event):
    global CURRENT_TIMEZONE
    manager.process_events(event)
    if event.type == G2D.PYGAME_INSTANCE_.USEREVENT and event.user_type == pygame_gui.UI_BUTTON_PRESSED:
        if event.ui_element == snap_button:
            global SNAP_MODE
            SNAP_MODE = not SNAP_MODE
        if event.ui_element == rgb_button:
            global RAINBOW_MODE
            RAINBOW_MODE = not RAINBOW_MODE
    if event.type == G2D.PYGAME_INSTANCE_.USEREVENT:
         if event.user_type == pygame_gui.UI_DROP_DOWN_MENU_CHANGED:
            CURRENT_TIMEZONE = event.text
    if event.type == G2D.PYGAME_INSTANCE_.VIDEORESIZE:
        global label3, label6, label9, label12
        updatePivotPoint()

        label3.kill()
        label3 = updateLabel(MID_POINT, "3")
        label6.kill()
        label6 = updateLabel(MID_POINT, "6")
        label9.kill()
        label9 = updateLabel(MID_POINT, "9")
        label12.kill()
        label12 = updateLabel(MID_POINT, "12")
    pass


def update(this):
    global CANVAS_BORDER, BOTTOM_MENU_BORDERS, bottom_panel
    manager.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    manager.draw_ui(G2D.WINDOW)
    pass

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()