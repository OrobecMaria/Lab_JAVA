@echo off
chcp 65001 >nul
echo Запуск приложения "Реестр Областей"...
"C:\Program Files\Axiom\AxiomJDK-25\bin\java.exe" --module-path "C:\ринх\razrabotka(java)\javafx-sdk-25.0.4\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -jar RegionApp.jar
pause