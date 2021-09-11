from datetime import datetime
import pytz


tz_NY = pytz.timezone('America/New_York') 
datetime_NY = datetime.now(tz_NY)
current_time = datetime_NY.strftime("%H")
print("Current Time =", current_time)