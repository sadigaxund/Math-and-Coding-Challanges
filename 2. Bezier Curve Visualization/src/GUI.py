from graphics import Renderer
from Bezier import Geometry
import pygame_gui



G2D = Renderer((800, 600), 'Bezier Curve Visualization') 
manager = pygame_gui.UIManager(G2D.WINDOW_SIZE)
# G2D.WINDOW.fill(G2D.PYGAME_INSTANCE_.Color('#001100'))

BOTTOM_MENU_HEIGHT = 40
CANVAS_BORDER = G2D.Rect(0, 0, G2D.WINDOW_SIZE[0], G2D.WINDOW_SIZE[1] - BOTTOM_MENU_HEIGHT)
BOTTOM_MENU_BORDERS = G2D.Rect(0, G2D.WINDOW_SIZE[1] - BOTTOM_MENU_HEIGHT, G2D.WINDOW_SIZE[0], BOTTOM_MENU_HEIGHT)

MARGIN = 5
SLIDER_WIDTH = 200
LABEL_WIDTH = 30
LABEL_HEIGHT = BOTTOM_MENU_HEIGHT - MARGIN * 2 
LABEL_Y = (BOTTOM_MENU_HEIGHT - LABEL_HEIGHT) / 2 - 2
SLIDER_Y = LABEL_Y - 1

bottom_panel = pygame_gui.elements.UIPanel(BOTTOM_MENU_BORDERS, 4, manager)

INFO_LABEL_BORDER =  G2D.Rect(MARGIN, LABEL_Y, LABEL_WIDTH * 3, LABEL_HEIGHT)
SLIDER_BORDER = G2D.Rect(INFO_LABEL_BORDER.right + MARGIN, SLIDER_Y, SLIDER_WIDTH, LABEL_HEIGHT)
SLIDER_LABEL_BORDER = G2D.Rect(SLIDER_BORDER.right + MARGIN, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT)
vertexLabel = pygame_gui.elements.UILabel(INFO_LABEL_BORDER, "Vertices:", manager, bottom_panel)
slider = pygame_gui.elements.UIHorizontalSlider(SLIDER_BORDER, 25.0, (1.0, 250.0), manager, bottom_panel)
slider_label = pygame_gui.elements.UILabel(SLIDER_LABEL_BORDER, str(int(slider.get_current_value())), manager, bottom_panel)


INFO_LABEL_BORDER =  G2D.Rect(SLIDER_LABEL_BORDER.right + MARGIN, LABEL_Y, INFO_LABEL_BORDER.width * 0.6, INFO_LABEL_BORDER.height)
SLIDER_BORDER = G2D.Rect(INFO_LABEL_BORDER.right + MARGIN, SLIDER_Y, SLIDER_WIDTH * 0.6, BOTTOM_MENU_HEIGHT - MARGIN * 2)
SLIDER_LABEL_BORDER = G2D.Rect(SLIDER_BORDER.right + MARGIN, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT)
strokeLabel = pygame_gui.elements.UILabel(INFO_LABEL_BORDER, "Size:", manager, bottom_panel)
strokeSlider = pygame_gui.elements.UIHorizontalSlider(SLIDER_BORDER, 3.0, (0.0, 30.0), manager, bottom_panel)
stroke_slider_label = pygame_gui.elements.UILabel(SLIDER_LABEL_BORDER, str(int(strokeSlider.get_current_value())), manager, bottom_panel)

button_width = 100
button_height = BOTTOM_MENU_HEIGHT * 0.8
BUTTON_BORDER = G2D.Rect(SLIDER_LABEL_BORDER.right + MARGIN, (BOTTOM_MENU_HEIGHT - button_height)/2 - 3, button_width, button_height)


stringBtn = pygame_gui.elements.UIButton(BUTTON_BORDER, 'String Art', manager, bottom_panel,'#scaling_button')

for variable in (INFO_LABEL_BORDER, SLIDER_BORDER, SLIDER_LABEL_BORDER, BUTTON_BORDER, CANVAS_BORDER, BOTTOM_MENU_BORDERS):
    del variable



bezierDrawer = Geometry(G2D)
STRING_ART = False
VERTICES = 25
p0 = (0, G2D.WINDOW_SIZE[1] / 2)
p1 = (G2D.WINDOW_SIZE[0] / 4, G2D.WINDOW_SIZE[1])
p2 = (G2D.WINDOW_SIZE[0] * 3 / 4, 0)
p3 = (G2D.WINDOW_SIZE[0], G2D.WINDOW_SIZE[1] / 2)



def draw(this):
    # Draw Canvas 
    G2D.PEN.drawRect(CANVAS_BORDER, 0, (21, 34, 56))
    # Draw elements
    G2D.PEN.setColor(G2D.PEN.GREEN)
    
    pts = [(0, CANVAS_BORDER.height), (CANVAS_BORDER.width, 0), (0, 0), (CANVAS_BORDER.width, CANVAS_BORDER.height)]
    for step in range(0, VERTICES + 1):
        t = step / VERTICES
        G2D.PEN.addVertex(bezierDrawer.bezier(pts, t))
    G2D.PEN.addVertex()


def handleEvent(this, event):
    manager.process_events(event)
    if event.type == G2D.PYGAME_INSTANCE_.USEREVENT:
        if event.user_type == pygame_gui.UI_BUTTON_PRESSED:
            if event.ui_element == stringBtn:
                bezierDrawer.STRING_ART =  not bezierDrawer.STRING_ART
def update(this):
    global VERTICES
    manager.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    manager.draw_ui(G2D.WINDOW)
    if slider.has_moved_recently:
        value = int(slider.get_current_value())
        slider_label.set_text(str(value))
        VERTICES = value
    if strokeSlider.has_moved_recently:
        value = int(strokeSlider.get_current_value())
        stroke_slider_label.set_text(str(value))
        G2D.PEN.strokeWeight(value)

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()