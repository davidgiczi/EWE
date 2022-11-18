@ECHO OFF
CD C:\Users\User\Documents\newer_workspace_for_STS4_200206\ElectricWireEditor
START "ElectricWireEditor" java --module-path=C:\Users\User\Documents\apps\javafx-sdk-18.0.2\lib --add-modules=javafx.controls,javafx.fxml -jar EWE.jar
EXIT