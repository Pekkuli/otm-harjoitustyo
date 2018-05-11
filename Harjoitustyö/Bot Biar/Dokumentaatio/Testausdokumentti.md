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

## Järjestelmätestaus
Sovelluksen järjestelmätestaus suoritettiin manuaalisesti.

## Asennus ja konfigurointi
Sovellus on testattu windows 7 ja 10 käyttöjärjestelmillä 
[käyttö-ohjeen](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/K%C3%A4ytt%C3%B6ohje.md) mukaisesti.

## Toiminnallisuudet
Kaikki [määrittelydokumentin](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/vaatimusmaarittely.md) ja [käyttöohjeen](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/K%C3%A4ytt%C3%B6ohje.md)
toiminnot on käyty läpi sekä väärillä, että toimivilla syötteillä.

