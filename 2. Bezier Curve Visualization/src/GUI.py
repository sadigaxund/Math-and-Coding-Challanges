from graphics import Renderer
import pygame_gui
from pygame_gui.elements import UIHorizontalSlider
from pygame_gui.elements import UILabel
from main import *

G2D = Renderer((800, 600), 'Quick Start') 
G2D.WINDOW.fill(G2D.PYGAME_INSTANCE_.Color('#001100'))

BOTTOM_MENU_HEIGHT = 40
BOTTOM_MENU_BORDERS = G2D.Rect(0, G2D.WINDOW_SIZE[1] - BOTTOM_MENU_HEIGHT, G2D.WINDOW_SIZE[0], BOTTOM_MENU_HEIGHT)

manager = pygame_gui.UIManager(G2D.WINDOW_SIZE)
SLIDER_WIDTH = 200
SLIDER_LABEL_WIDTH = 50
MARGIN = 5
SLIDER_BORDER = G2D.Rect(0, G2D.WINDOW_SIZE[1] - BOTTOM_MENU_HEIGHT + MARGIN, SLIDER_WIDTH, BOTTOM_MENU_HEIGHT - MARGIN*2)
SLIDER_LABEL_BORDER = G2D.Rect(SLIDER_BORDER[0] + SLIDER_WIDTH + MARGIN, SLIDER_BORDER[1], SLIDER_LABEL_WIDTH ,BOTTOM_MENU_HEIGHT - MARGIN * 2)
slider = UIHorizontalSlider(SLIDER_BORDER, 25.0, (0.0, 250.0), manager)
slider_label = UILabel(SLIDER_LABEL_BORDER, str(int(slider.get_current_value())), manager)


def draw(this):
    global STRING_ART
    G2D.PEN.fillBackground((21, 34, 56))
    G2D.PEN.setColor((112, 128, 144))
    G2D.PEN.strokeWeight(0)
    G2D.PEN.drawRect(BOTTOM_MENU_BORDERS)

    G2D.PEN.fillBackground(G2D.PEN.BLACK)
    G2D.PEN.strokeWeight(1)
    G2D.PEN.setColor([0,255,0])

    #to control points:
    # p1 = G2D.getMousePos()

    G2D.PEN.setColor(G2D.PEN.GREEN)
    # STRING_ART = True
    pts = [(0, 600), (800, 0), (0, 0), (800, 600)]
    for step in range(0, VERTICIES + 1):
        t = step / VERTICIES
        G2D.PEN.addVertex(bezier(pts, t))
    G2D.PEN.breakVertex()
def handleEvent(this, event):
    manager.process_events(event)
def update(this):
    manager.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    manager.draw_ui(G2D.WINDOW)
    if slider.has_moved_recently:
        slider_label.set_text(str(int(slider.get_current_value())))

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()