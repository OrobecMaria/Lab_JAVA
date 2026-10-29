@echo off
chcp 65001 >nul
set JAVA=C:\Program Files\Axiom\AxiomJDK-25\bin\javac.exe
set FXLIB=C:\ринх\razrabotka(java)\javafx-sdk-25.0.4\lib
if exist out rmdir /s /q out
mkdir out
%JAVA% -d out --module-path "%FXLIB%" --add-modules javafx.controls,javafx.fxml -encoding UTF-8 ^
  src/regionapp/model/Region.java ^
  src/regionapp/MainApp.java ^
  src/regionapp/controller/RootLayoutController.java ^
  src/regionapp/controller/RegionOverviewController.java ^
  src/regionapp/controller/RegionEditDialogController.java
if %ERRORLEVEL% EQU 0 (
    echo Компиляция прошла успешно.
) else (
    echo Ошибка компиляции!
)
pause
