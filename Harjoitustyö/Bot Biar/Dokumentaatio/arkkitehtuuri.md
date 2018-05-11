# Arkkitehtuurikuvaus

## Rakenne

<img src="https://github.com/Pekkuli/otm-harjoitustyo/blob/master/Harjoitusty%C3%B6/Bot%20Biar/Dokumentaatio/pakkausrakenne.png" width=240>

Ability
----------------------
Botissa käytetyt Abilityt ovat telegrammin Bot-API:a varten suunnitellun [java-kirjaston](https://github.com/rubenlagus/TelegramBots/) moduuli, Abilitybot. Tämän tarkoituksena on helpottaa
erilaisten komentojen toteuttamista boteille ja mahdollistaa mm. laskutoimitukset, kuvien ja tiedostojen käsittelyn. Soveltuu myös suurien 
data määrien käsittelyyn. 
 

**Abilitybot**in tärkeimpiä piirteitä:
 * Jokainen komento on erillinen ability
 * jokaisen komennon argumenttien pituus on mahdollista määrittää yhden luvun muutoksella
 * Komennoille voi määrittää eri tason luvat User | Admin | Creator
 * sisäänrakennettu tietokanta, joka on saatavilla jokaiselle komennolle
 * botti ylläpitää tietokantaa kaikista käyttäjistä, jotka ovat ottaneet yhteyttä bottiin.
 * Tietokanna pystyy varmuuskopioimaan ja palauttamaan komennolla
 * Käyttäjiä voi "bannata" ja "epäbannata" käyttämästä bottia
 * käyttäjiä voi "ylentää" ja "alentaa" "admineiksi" eli ylläpitäjiksi
 
 Abstraktio
 -----------
 
 Abstraktio määrittää uuden olion, Abilityn, jossa yhdistyy ehdot, flagit, toiminnot (engl. action), päätoiminnon jälkeiset toiminnot 
 (engl. post-atction) sekä vastaukset (engl. reply).
 Esimerkkinä yksinkertainen ***/hello*** komento
 
 
 ```java
public Ability sayHelloWorld() {
    return Ability
              .builder()
              .name("hello")
              .info("says hello world!")
              .input(0)
              .locality(USER)
              .privacy(PUBLIC)
              .action(ctx -> silent.send("Hello world!", ctx.chatId()))
              .post(ctx -> silent.send("Bye world!", ctx.chatId()))
              .build();
}
```
Koodipätkän erittely:
* *.name()* - abilityn nimi, määrittää /[komento] käytettävän
* *.info()* - komennon informaatio, mitä komento tekee
* *.input()* - komennon vaatimien argumenttien määrä, 0 -> komento ei välitä argumenttien määrästä
* *.locality()* - määrittää millaisissa keskusteluissa komentoa voidaan käyttää, GROUP, USER, ALL 
* *.privacy()* - määrittää kuka voi käyttää komentoa. CREATOR, ADMIN, PUBLIC
* *.action()* - komennon logiikan suoritus suoritetaan täällä. Lambda funktio joka ottaa MessageContext-olion.
    * *MessageContext* sisältää mahdollisuuden päästä käsiksi chatId:hen, User (käyttäjä-olio) sekä saatuun updateen.
* *.post()* - päätoiminnon jälkeen suoritettava logiikka

Yleisellä tasolla aina kun botti saa viestin joka voi sisältää tekstiä, kuvan, jonkin tiedoston tai jonkin yhdistelmän näitä, se tulee botille
Update-olion sisällä. Update sisältää kaiken keskustelun päivitykseen tarvittavan tiedon ja tätä kautta komentojen logiikka saa kaiken ulkoisen
informaation. 

Nopea läpikäynti ylläolevasta esimerkistä:

 * käyttäjä lähettää botille viestin, joka sisältää tekstin "/hello"
 * botti saa update-olion
 * botti huomaa, että update sisältää viestin ja että kyseessä on komento (viesti alkaa "/")
 * botti löytää kyseistä komentoa vastaavan komennon sen komento-listasta (komento-lista luodaan aian kun botti käynnistyy)
 * botti palauttaa komentoa vastaavan Abilityn
 * Ability tarkistaa argumenttien määrän 0 -> ei välitä mahdollisista annetuista argumenteista
 * Ability tarkistaa saako komentoa käyttää kyseisessä keskustelussa -> yksityiskeskustelu **USER** -> sallii
 * Ability suorittaa komennossa määritetyn logiikan
	* silent on Abilitybotin mukana luoto olio, jonka avulla voi mm. lähettää viestejä keskusteluihin.
	* tässä esimerkissä silent saa lambda funktiossa update-olion, josta silent nappaa keskustelun tunnuksen **chatiId**:en
	* lähettää viestin metodilla send(String viesti, String ChatId)
 * Ability suorittaa päätoiminnon jälkeisen toiminnon
	* Sama kuin päätoiminta, mutta eri viesti
 * Palauttaa rakennetun Abilityn





