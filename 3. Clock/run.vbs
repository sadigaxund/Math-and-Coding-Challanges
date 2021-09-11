Set oShell = CreateObject ("Wscript.Shell") 
Dim strArgs
strArgs = "cmd /c  debug.bat"
oShell.Run strArgs, 0, false
