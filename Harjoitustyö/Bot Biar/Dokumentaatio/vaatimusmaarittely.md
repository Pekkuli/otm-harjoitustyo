# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on luoda botti peliä [Runescape](https://oldschool.runescape.com/) varten. Botin komentoihin kuuluisi erilaisia 
helppokäyttöominaisuuksia pelin pelaamiseen liittyen. Esimerkiksi esineiden hintojen etsimistä tai pelaajahahmojen statsien hakemista.

## Käyttäjät

Aloitetaan luomalla vain peruskäyttäjä, jolla oikeudet kaikkiin komentoihin, jos löytyy aihetta tai komentoideoita, joihin pääsyä
kannattaa syystä tai toisesta rajoitta, voidaan muiden käyttäjäryhmien luomista harkita.

## käyttöliittymäluonnos

<img src="https://raw.githubusercontent.com/Pekkuli/otm-harjoitustyo/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/k%C3%A4ytt%C3%B6liittym%C3%A4luonnos.png" width=400>
Sovellus Toimii siten, että käyttäjän lähettämät komennot välittyvät telegrammin kautta botille, jossa botti lähettää telegrammin bot-API:in 
erilaisia pyyntöjä komennosta riippuen.

## Suunnitellut ominaisuudet

Esineiden hintojen haku, pelaajien tietojen hakeminen pelistä.

## Kehitysideoita

- Mahdollisuus käyttäjille määrittää suosikkeja esineiden hintojen hakuun jolloin olisi helppoa ja nopeaa
hakea usein haettujen esineiden hinnat kerralla.


- Mahdollisuus määrittää oma pelaajahahmo, jolloin käyttäjä voisi hakea oman hahmonsa "statsit" nopeasti.