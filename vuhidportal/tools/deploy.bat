@ECHO OFF
set GlassFishPath=C:\Program Files\glassfish-3.1.2\glassfish\domains\domain1
set VUHIDPortalWar=..\vuhidportal.war

rmdir /S /Q "%GlassFishPath%\applications\"
mkdir "%GlassFishPath%\applications\"
del /S /Q "%GlassFishPath%\autodeploy\*.*"
copy ".\%VUHIDPortalWar%" "%GlassFishPath%\autodeploy\"

set VUHIDTools=..\vuhid_tools
mkdir "%GlassFishPath%\config\vuhid_tools\"
mkdir "%GlassFishPath%\config\vuhid_tools\Certificates"
mkdir "%GlassFishPath%\config\vuhid_tools\config"
copy ".\%VUHIDTools%\Certificates\*" "%GlassFishPath%\config\vuhid_tools\Certificates\"
copy ".\%VUHIDTools%\config\*" "%GlassFishPath%\config\vuhid_tools\config\"
