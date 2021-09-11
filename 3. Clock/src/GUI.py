from graphics import Renderer
import pygame_gui
from datetime import datetime
import pytz
import math


# current_time = datetime_NY.strftime("%H:%M:%S")



G2D = Renderer((800, 600), 'Clock') 
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
# SLIDER_BORDER = G2D.Rect(INFO_LABEL_BORDER.right + MARGIN, SLIDER_Y, SLIDER_WIDTH, LABEL_HEIGHT)
# SLIDER_LABEL_BORDER = G2D.Rect(SLIDER_BORDER.right + MARGIN, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT)
vertexLabel = pygame_gui.elements.UILabel(INFO_LABEL_BORDER, "00:00:00", manager, bottom_panel)
# slider = pygame_gui.elements.UIHorizontalSlider(SLIDER_BORDER, 25.0, (1.0, 250.0), manager, bottom_panel)
# slider_label = pygame_gui.elements.UILabel(SLIDER_LABEL_BORDER, str(int(slider.get_current_value())), manager, bottom_panel)


# INFO_LABEL_BORDER =  G2D.Rect(SLIDER_LABEL_BORDER.right + MARGIN, LABEL_Y, INFO_LABEL_BORDER.width * 0.6, INFO_LABEL_BORDER.height)
# SLIDER_BORDER = G2D.Rect(INFO_LABEL_BORDER.right + MARGIN, SLIDER_Y, SLIDER_WIDTH * 0.6, BOTTOM_MENU_HEIGHT - MARGIN * 2)
# SLIDER_LABEL_BORDER = G2D.Rect(SLIDER_BORDER.right + MARGIN, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT)
# strokeLabel = pygame_gui.elements.UILabel(INFO_LABEL_BORDER, "Size:", manager, bottom_panel)
# strokeSlider = pygame_gui.elements.UIHorizontalSlider(SLIDER_BORDER, 3.0, (0.0, 30.0), manager, bottom_panel)
# stroke_slider_label = pygame_gui.elements.UILabel(SLIDER_LABEL_BORDER, str(int(strokeSlider.get_current_value())), manager, bottom_panel)

# button_width = 100
# button_height = BOTTOM_MENU_HEIGHT * 0.8
# BUTTON_BORDER = G2D.Rect(SLIDER_LABEL_BORDER.right + MARGIN, (BOTTOM_MENU_HEIGHT - button_height)/2 - 3, button_width, button_height)


# stringBtn = pygame_gui.elements.UIButton(BUTTON_BORDER, 'String Art', manager, bottom_panel,'#scaling_button')

# for variable in (INFO_LABEL_BORDER, SLIDER_BORDER, SLIDER_LABEL_BORDER, BUTTON_BORDER, CANVAS_BORDER, BOTTOM_MENU_BORDERS):
#     del variable



CLOCK_RADIUS = 250

def draw(this):
    # Draw Canvas 
    G2D.PEN.drawRect(CANVAS_BORDER, 0, (255, 255, 255))
    MID_POINT = (G2D.WINDOW_SIZE[0] / 2, (G2D.WINDOW_SIZE[1] - BOTTOM_MENU_HEIGHT) / 2)
    
    G2D.PEN.circle(MID_POINT, CLOCK_RADIUS, (144, 168, 238), 0)
    for i in range(0, 10, 3):
        track_len = 30
        track_angle = (i / 3 * 90) * math.pi / 180
       
        x0 = MID_POINT[0] + (CLOCK_RADIUS - track_len) * math.cos(track_angle)
        y0 = MID_POINT[1] + (CLOCK_RADIUS - track_len) * math.sin(track_angle)
        x1 = MID_POINT[0] + CLOCK_RADIUS * math.cos(track_angle)
        y1 = MID_POINT[1] + CLOCK_RADIUS * math.sin(track_angle)
        G2D.PEN.strokeWeight(6)
        G2D.PEN.line((x0, y0), (x1, y1), G2D.PEN.WHITE)

    G2D.PEN.circle(MID_POINT, CLOCK_RADIUS, (25, 41, 88), 5)
    G2D.PEN.point(MID_POINT, G2D.PEN.WHITE, 8)

    MID_POINT = (MID_POINT[0] - 1, MID_POINT[1])
    G2D.PEN.strokeWeight(4)

  

    
    # tz_NY = pytz.timezone('America/New_York') 
    datetime_NY = datetime.now()
    
    sec_len = CLOCK_RADIUS * 0.7
    min_len = CLOCK_RADIUS * 0.6
    hrs_len = CLOCK_RADIUS * 0.5

    sec = datetime_NY.strftime("%S")
    min = datetime_NY.strftime("%M")
    hrs = datetime_NY.strftime("%H")
    vertexLabel.set_text(hrs + ":" + min + ":" + sec)

    sec_angle = (-90 + int(sec) * 6) * math.pi / 180
    min_angle = (90 - int(min) * 6) * math.pi / 180
    hrs_angle = (90 - int(hrs) * 30)* math.pi / 180

    sec_coords = (MID_POINT[0] + sec_len * math.cos(sec_angle), MID_POINT[1] + sec_len * math.sin(sec_angle))
    min_coords = (MID_POINT[0] + min_len * math.cos(min_angle), MID_POINT[1] + min_len * math.sin(min_angle))
    hrs_coords = (MID_POINT[0] + hrs_len * math.cos(hrs_angle), MID_POINT[1] + hrs_len * math.sin(hrs_angle))
    G2D.PEN.line(MID_POINT, sec_coords, G2D.PEN.RED)
    G2D.PEN.line(MID_POINT, min_coords, G2D.PEN.GREEN)
    G2D.PEN.line(MID_POINT, hrs_coords, G2D.PEN.BLACK)
   



def handleEvent(this, event):
    manager.process_events(event)
    # if event.type == G2D.PYGAME_INSTANCE_.USEREVENT:
    #     if event.user_type == pygame_gui.UI_BUTTON_PRESSED:
    #         if event.ui_element == stringBtn:
    #             bezierDrawer.STRING_ART =  not bezierDrawer.STRING_ART
def update(this):
    manager.update(G2D.clock.get_time()/1000.0)
    G2D.WINDOW.blit(G2D.WINDOW, (0, 0))
    manager.draw_ui(G2D.WINDOW)
    # if slider.has_moved_recently:
    #     value = int(slider.get_current_value())
    #     slider_label.set_text(str(value))
    #     VERTICES = value
    # if strokeSlider.has_moved_recently:
    #     value = int(strokeSlider.get_current_value())
    #     stroke_slider_label.set_text(str(value))
    #     G2D.PEN.strokeWeight(value)

G2D.setEventLogic(handleEvent)
G2D.setDrawLogic(draw)
G2D.setUpdateLogic(update)
G2D.startLoop()