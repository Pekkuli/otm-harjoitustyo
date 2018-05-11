# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on luoda botti peliä [Runescape](https://oldschool.runescape.com/) varten. Botin komentoihin kuuluisi erilaisia 
helppokäyttöominaisuuksia pelin pelaamiseen liittyen. Esimerkiksi esineiden hintojen etsimistä tai pelaajahahmojen statsien hakemista.

## Käyttäjät

Aloitetaan luomalla vain peruskäyttäjä, jolla oikeudet kaikkiin komentoihin, jos löytyy aihetta tai komentoideoita, joihin pääsyä
kannattaa syystä tai toisesta rajoitta, voidaan muiden käyttäjäryhmien luomista harkita.

## käyttöliittymäluonnos

![luonnos](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/käyttöliittymäluonnos.png)
Sovellus Toimii siten, että käyttäjän lähettämät komennot välittyvät telegrammin kautta botille, jossa botti lähettää telegrammin bot-API:in 
erilaisia pyyntöjä komennosta riippuen.

## Suunnitellut ominaisuudet

Esineiden hintojen haku, pelaajien tietojen hakeminen pelistä.
