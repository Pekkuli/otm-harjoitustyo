# Käyttöohje

Lataa release tai lähdekoodi. Käynnistä botti avaamalla releasesta saatu JAR-tiedosto tai lisää lähdekoodi esim. netbeanssiin ja suorita sieltä.
Voit viestitellä botin kanssa telegramissa seuraavan [linkin](https://t.me/BotBiar_bot) kautta. Telegram-botti eivät voi aloittaa keskusteluita,
jonka vuoksi käyttäjän on tehtävä se.

## Toiminnot
Botilla on useita eri komentoja liittyen peliin [Runescape](https://oldschool.runescape.com/) ja muutama "hassu" ylimääräinen komento.
Aloitettuasi keskustelun botin kanssa (botin pitää olla tässä vaiheessa päällä) pitäisi ruudulle tulla esiin botin aloitusviesti. Aloitusviesti
sisältää esim. klikattavan linkin /commands jota klikkaamalla näkee kaikki saatavilla olevat komennot. Botin komennot, jotka aiheuttavat automaattisen 
pakotetun vastauksen botin viestiin, voidaan suorittaa useita kertoja vastaamalla botin viestiin uudelleen ja uudelleen. 

### Esimerkki komennon suoritus
![Esimerkki](https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/esimerkki.gif)
Käytät /price. 
Tällöin botti pakottaa sinut automaattisesti vastaustilaan (engl. reply), botin lähettämään viestiin "Item name?".
Tähän viestiin vastatessa pelissä löytyvän esineen nimen esim. cannonball tai dragon hunter crossbow, botti yrittää etsiä esineelle hintaa.
Jos botti löytää esineen palauttaa se "the price of [esine1] is Xgp".
Kommenot, jossa vastataan botin aikaisempaan viestiin kuten äskeinen /price voidaan suorittaa myös siten, 
että vastataan tähän aikaisempaan viestiin. Jos halutaan tarkistaa toisen esineen hinta ei tarvitse kirjoittaa uudelleen /price
vaan riittää,että klikkaat botin viestiä "item name?" ja lähetät vastauksen (engl. reply) tähän viestiin. 
Esim. "item name?" -> reply -> cannonball -> "the price of [esine2] is Ygp".

### /help
Botti palauttaa yleisen informaation botista, jossa mm. mitä varten botti on ja linkin /commands komentoon.

### /fishinglvl
botti kysyy sinun fishing leveliäsi eli kalastustasoa.
Normaalisti pelaajan kalastustaso on välillä 1-99, mutta botin pitäisi reagoida mihin tahansa vastaukseen,
kyriilisiä ym. muita erikoismerkkejä ei ole testattu.

### /hiscore
Voit tarkastaa pelaajan highscoret.
Kuten aikaisemmassa komennossa botti kysyy pelaajan nimeä ja tarkastaa löytyykö kyseisellä nimellä pelaajaa.
Voit kokeilla esimerkiksi Woox ja B0aty, voit myös käydä [tarkastelemassa hiscoreja](http://services.runescape.com/m=hiscore_oldschool/overall.ws).

### /price
Komennon avulla voit kysyä esineen hintaa botilta. Samalla tavalla kuin aikaisemmin botti kysyy vastauksena esineen nimeä.
Tämän jälkeen botti palauttaa viestissä esineen hinnan jos botti löytää sille hinnan.
Voit hakea esimerkiksi esineitä: cannonball, twisted bow tai elysian spirit shield.

### /longboi
Komennolla voit tarkastaa LongBoi:n. (??? -> kokeile ( ͡° ͜ʖ ͡°))
Komento saa botin laittamaan keskusteluun kuvaa pelihahmosta, joka kasvaa jokaisella komento-kerralla.

### /reset
Resetoi botin

### /hello
Yksinkertainen komento joka palauttaa "hello world!

### /sayhi [Henkilön nimi]
Palauttaa tervehdyksen "hi [Henkilön nimi]!"

### /commmands
Näyttää kaikki botin saatavilla olevat komennot.
