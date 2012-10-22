@echo off

REM ############################################
REM ## Written by Long Phan                   ##
REM ## License: GNU GPL                       ##
REM ## Based on L2JDP script                  ##
REM ############################################
REM ## You can change here your own DB params ##
REM ############################################
REM MYSQL BIN PATH
set mysqlBinPath=C:\Program Files\MySQL\MySQL Server 5.5\bin

set dbuser=root
set dbpass=
set dbname=vuhid-portal
set dbhost=localhost
REM ############################################

set mysqldumpPath="%mysqlBinPath%\mysqldump"
set mysqlPath="%mysqlBinPath%\mysql"

echo PLEASE EDIT THIS SCRIPT SO VALUES IN THE CONFIG SECTION MATCH YOUR DATABASE(S)
echo.
echo.
echo Making a backup of the original loginserver database.
%mysqldumpPath% --add-drop-table -h %dbhost% -u %dbuser% --password=%dbpass% %dbname% > vuhid-portal_backup.sql
:ask
set installerprompt=x
set /p installerprompt=VUHID Portal DB install type: (i) install or (q) quit? 
if /i %installerprompt%==i goto install
if /i %installerprompt%==q goto end
goto ask

:install
echo Deleting VUHID Portal tables for new content.
%mysqlPath% -h %dbhost% -u %dbuser% --password=%dbpass% < ../sql/vuhid-portal.sql
%mysqlPath% -h %dbhost% -u %dbuser% --password=%dbpass% -D %dbname% < ../sql/transactions.sql

:end
echo.
echo Script complete.
pause
