# Originelle Oktopusse HP
___

### Hallo und willkommen bei den originellen Oktupussen,

hier sind ein paar Infos, wie ihr unsere jars zum Laufen bringen könnt.
Server.jar sollte ohne Probleme laufen. Für die Client.jar und AI.jar gibt 2 Möglichkeiten: entweder über IntelliJ, oder über die Eingabeaufforderung CMD

**Mit IntelliJ:**
1. Zu Run Configurations gehen
2. Add VM Options
3. Einfügen: --module-path " * "--add-modules javafx.controls, javafx.fxml -jar " ** "
4. Statt * fügt ihr den Dateipfad zur javafx lib (z.B. C:\Program Files\Java\javafx-sdk-17.0.2\lib)
   und statt ** fügt ihr den Dateipfad zur jar Datei ein (z.B. C:\originelle_oktopusse\out\artifacts\Client_jar\Client.jar)

**Mit CMD:**
1. cd Dateipfad von jar Datei
2. java --module-path D:\openjfx-18.0.1_windows-x64_bin-sdk\javafx-sdk-17.0.2\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar Client.jar
3. Den Pfad für javafx lib anpassen


___
___


### Und hier noch ein paar Hinweise, für den Spielfluss:

1. Sobald der erste Spieler sein 5.Register gelegt hat, muss man auf dem game-window des anderen Spielers irgendwo hin klicken, damit der Timer angezeigt wird (mouse event)

2. Beim Spielen der Register immer abwechselnd erst "play register" bei allen Spielern klicken und dann "move robot". 


Wir freuen uns auf positives Feedback

Viel Spaß beim Spielen!

bleibt originell, 

eure Originellen Oktopusse :)
