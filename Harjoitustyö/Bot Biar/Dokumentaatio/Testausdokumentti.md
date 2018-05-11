# Testausdokumentti

Ohjelmaa on testattu sekö automaattisillä yksikkö- ja intragraatiotesteillä JUnitilla ja Mockitolla sekä manuaalisesti järjelstelmätason testein.

## yksikkö- ja integraatiotestaus
### sovelluslogiikka
Automaattiset testit keskittyivät sovelluslogiikan toiminnan testaamiseen. Sovelluslogiikka sijaitsee pakkauksessa [telegram.logiikka](https://github.com/Pekkuli/otm-harjoitustyo/tree/master/Harjoitusty%C3%B6/Bot%20Biar/src/main/java/telegram/logiikka). 
Logiikkaa testaa integraatiotesti [LogiikkaTiedostoTest](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/src/test/java/telegram/logiikka/LogiikkaTiedostoTest.java), jonka avulla
testataan sovelluksen toteuttamien komentojen logiikan toimivuutta.

Testejä varten käytetään [Mockitoa](http://site.mockito.org/) luomaan väärennöksiä logiikan käyttämistä luokista. Esimerkiksi testattaessa 
logiikan lähettämiä viestejä käyttäjälle, piti Abilityjen käyttämä sender-olio tai silent-olio, "mockata" ettei testi yrittänyt lähettää viestiä,
oikeasti telegrammiin vaan se jäi testiluokan sisälle jolloin sen sisältöä voitiin tarkastella.

### Testauskattavuus
Testikattavuudesta jätettiin pois botin käynnistävä [```telegram.main```](https://github.com/Pekkuli/otm-harjoitustyo/tree/master/Harjoitusty%C3%B6/Bot%20Biar/src/main/java/telegram/Main). 
Rivi kattavuus on 78% ja haarautumakattavuus 75% **Tässä pitää ottaa huomioon ettei sovelluksen käyttämille lambda funktioille tehty testejä**. 
Ilman näitä rivi- ja haarautumakattavuus on ~80-90%
<img src="https://raw.githubusercontent.com/Pekkuli/otm-harjoitustyo/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/testikattavuus.png" width=800>

### Checkstyle
Checkstylessä palautus hetkellä pitäisi olla 13 "virhettä" nämä ovat kaikki liian pitkistä metodeista johtuvia.
10 virheistä tulee luokasta [telegram.bot.BotBiar](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/src/main/java/telegram/bot/BotBiar.java)
ja 3 luokasta [telegram.logiikka.LogiikkaTiedosto](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/src/main/java/telegram/logiikka/LogiikkaTiedosto.java).
Luokan [telegram.bot.BotBiar](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/src/main/java/telegram/bot/BotBiar.java)
rakenteen takia (katso [Ability](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/arkkitehtuuri.md#abstraktio))
katsoin että koodin luettavuuden takia botin abilityt on hyvä jättää "auki". Ability:ihin liittyvät errorit ovat siis täysin tietoisesti jätetty sellaisiksi luettavuuden parantamiseksi.
Näistä en haluaisi miinuksia, koska jätin ne tahallani, mutta luokan
[telegram.logiikka.LogiikkaTiedosto](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/src/main/java/telegram/logiikka/LogiikkaTiedosto.java) 
3 virhettä jäi sen takia etten keksinyt tapaa,jolla kyseiset metodit saisi lyhyemmiksi. Hyväksyn jos näistä 3 virheestä rokotetaan pisteitä.

## Järjestelmätestaus
Sovelluksen järjestelmätestaus suoritettiin manuaalisesti.

## Asennus ja konfigurointi
Sovellus on testattu windows 7 ja 10 käyttöjärjestelmillä 
[käyttö-ohjeen](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/K%C3%A4ytt%C3%B6ohje.md) mukaisesti.

## Toiminnallisuudet
Kaikki [määrittelydokumentin](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/vaatimusmaarittely.md) ja [käyttöohjeen](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/K%C3%A4ytt%C3%B6ohje.md)
toiminnot on käyty läpi sekä väärillä, että toimivilla syötteillä.

