@ECHO OFF
%~dp0\
START "ElectricWireDisplayer" java --module-path=%~dp0\javafx-sdk-18.0.2\lib --add-modules=javafx.controls,javafx.fxml -jar EWE.jar
EXIT