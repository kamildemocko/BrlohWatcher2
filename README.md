# Brloh Watcher

! This readme is in Slovak as this is utility for slovak shop brloh.sk

## Popis
Skript sleduje ci je hra zo stranky brloh.sk na sklade a pri zmene statusu z nedostupne odosle push notifikaciu do aplikacie (web/android/ios) ntfy.

Priklad pouzitia je hra predavana ako pouzivana, ktora sa neda presunut do (mojho) mesta a teda je potrebne na pockat, kym bude v (mojom) meste dostupna.

### ntfy
[https://ntfy.sh/](https://ntfy.sh/)
- open source
- bez potreby registracie
- dostupny na webe, pre Android aj iOS


# Nalezitosti
Java runtime pre spustenie JAR suboru

## Nastavenia - config
subor **config.ini**
```plain
e_je_skladom_web_text = Nie je skladom
moje_mesto = Košice - Atrium Optima
ntfy_kanal = https://ntfy.sh/dkdkdk12345
```

Udaje doplnit na zaklade udajov, tak ako su napisane na stranke brloh.sk

subor **hry-linky.txt**
```plain
https://www.brloh.sk/gran-turismo-7-ps5-p23717
https://www.brloh.sk/star-wars-jedi-survivor-ps5-p43506
https://www.brloh.sk/nhl-24-ps5-p47810
```

Zoznam hier na sledovanie

# Spustanie
Spustanie mozne cez crontab/scheduler alebo docker
```plain
java -jar brloh_watcher2.jar
```

subory _config.ini_ a _hry-linky.txt_ musia byt pritomne v priecinku, kde je JAR subor 

