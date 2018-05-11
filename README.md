# Harjoitustyö: Bot Biar

Bot Biar on telegram-viestisovellukselle tehty botti. Botti toteuttaa peliin [Runescape](https://oldschool.runescape.com/) erilaisia helppokäyttötoiminnallisuuksia, kuten esineiden hinnan nouto, pelaajien "statsien" haun yms. muuta enemmän tai vähemmän hyödyllistä.

## Dokumentaatio

[Käyttöohje](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/K%C3%A4ytt%C3%B6ohje.md)

[Vaatimusmäärittely](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/Testausdokumentti.md)

[Työaikakirjanpito](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/ty%C3%B6aikakirjanpito.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```
generoi hakemistoon _target_ suoritettavan jar-tiedoston _BotBiar-1.0-SNAPSHOT.jar_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_


### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
