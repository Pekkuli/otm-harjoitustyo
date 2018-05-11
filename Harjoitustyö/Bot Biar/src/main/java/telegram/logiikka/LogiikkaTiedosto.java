package telegram.logiikka;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;

public class LogiikkaTiedosto {
    
    private final SendPhoto ylavartalo;
    private final SendPhoto ylavartalojatke;
    private final SendPhoto keskivartalo;
    private final SendPhoto alavartalo;
    private final SendPhoto alavartalojatke;
    private final String[] skilltree;
    public int longboilkm;
    private final int longboimax;
    
    /**
     *Inittaa logiikkatiedoston, tällä hetkellä luo vain komennon /longboi käyttämät kuvat
     * ja määrittää /longboi komennon maksimipituuden
     */
    public LogiikkaTiedosto() {
        this.longboimax = 4;
        this.longboilkm = 0;
        this.ylavartalo = new SendPhoto().setNewPhoto(new File("src/resources/images/ylävartalo.png"));
        this.ylavartalojatke = new SendPhoto().setNewPhoto(new File("src/resources/images/ylävartalojatke.png"));
        this.keskivartalo = new SendPhoto().setNewPhoto(new File("src/resources/images/keskivartalo.png"));
        this.alavartalo = new SendPhoto().setNewPhoto(new File("src/resources/images/alavartalo.png"));
        this.alavartalojatke = new SendPhoto().setNewPhoto(new File("src/resources/images/alavartalojatke.png"));
        
        this.skilltree = new String[] {"Total:", "Attack:", "Strength:", "Defence:", "Hitpoints:", "Ranged:", "Prayer:",
            "Magic:", "Cooking:", "Woodcutting:", "Fletching:", "Fishing:", "Firemaking:",
            "Crafting:", "Smithing:", "Mining:", "Herblore:", "Agility:", "Thieving:",
            "Slayer:", "Farming:", "Runecraft:", "Hunter:", "Construction:"};
    }
    
    /**
     *Metodi tarkistaa updaten sisältämän viestin sisällön ja palauttaa viestin sisällöstä riippuen eri vastauksen
     * @param update telegrammin palvelimelta saatu Update-olio, joka sisältää metodissa tarvittavan viestin
     * @return nice jos viestin on "1-98", "no life!" jos 99 ja muihin "OK... ( ͡° ͜ʖ ͡°)"
     */
    public String checkIf99(Update update) {
        try {
            if (Integer.parseInt(update.getMessage().getText()) < 99 && Integer.parseInt(update.getMessage().getText()) >= 1) {
                return "Nice";
            } else if (Integer.parseInt(update.getMessage().getText()) == 99) {
                return "No life!";
            }  else {
                return "OK... ( ͡° ͜ʖ ͡°)";
            }
        } catch (NumberFormatException | NullPointerException e) {
            return "OK... ( ͡° ͜ʖ ͡°)";
        }
    }
    
    /**
     *Abilityn eli komennon /price käyttämä logiikka, jolla käyttäjän kertomalle esineelle etsitään hinta
     * @param update telegrammin palvelimelta saatu Update-olio, joka sisältää metodissa tarvittavan viestin
     * @return esineen hinta tai viesti ettei esinettä löytynyt/ole olemassa
     * @throws java.net.MalformedURLException error
     * @throws java.io.IOException error
     */
    public String readPrice(Update update) throws MalformedURLException, IOException {
        String result = "The price of " + update.getMessage().getText() + " is "; 
        String price = "";
        for (Map.Entry<String, JsonElement> obj : readPriceList().getAsJsonObject().entrySet()) {
            if (obj.getValue().getAsJsonObject().get("name").getAsString().equalsIgnoreCase(update.getMessage().getText())) {
                price = obj.getValue().getAsJsonObject().get("overall_average").getAsString();
            }
        }
        return checkEmptyItem(update.getMessage().getText(), price);
    }
    
    /**
     *Komento joka hakee esineiden hinnat sisältävän json-taulukon
     * @return esineiden hinnat sisältävän json-taulukon
     * @throws java.net.MalformedURLException error
     * @throws java.io.IOException error
     */
    public JsonElement readPriceList() throws MalformedURLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://rsbuddy.com/exchange/summary.json").openStream()));
        String jsontaulukko = reader.readLine(); // Luetaan esineiden hinnat tietokannasta
        JsonElement jelement = new JsonParser().parse(jsontaulukko); //taulukon parseaminen
        return jelement;
    }
    
    /**
     *metod, joka tarkistaa haetun esineen hinnan
     * @param item esineen nimi
     * @param price esineen hinta, jos tyhjä palauttaa "Item not found!"
     * @return esineiden hinnat sisältävän json-taulukon
     */
    public String checkEmptyItem(String item, String price) {
        if (price.isEmpty()) {
            return "Item not found!"; 
        } else {
            return "The price of " + item + " is " + "*" + numberFormat(price) + "*gp";
        }
    }
    
    /**
     *Lukee käyttäjän viestin sisältämän pelaajahahmon nimen ja hakee tälle statsit
     * @param update telegrammin palvelimelta saatu Update-olio, joka sisältää metodissa tarvittavan viestin
     * @return pelaajan statsit jos pelaaja on olemassa
     * @throws java.net.MalformedURLException error
     * @throws java.io.IOException error
     */
    public String readCharacter(Update update) throws MalformedURLException, IOException {
        String hs = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
        
        hs += update.getMessage().getText();
        URL hiscore = new URL(hs);
        if (((HttpURLConnection) hiscore.openConnection()).getResponseCode() == 200) {
            return skillFormat(hiscore, update.getMessage().getText());
        } else {
            return "Character not found!";
        }
    }
    
    /**
     *Lukee käyttäjän viestin sisältämän pelaajahahmon nimen ja hakee tälle statsit
     * @param url osoite, josta pelaajan tiedot haetaan
     * @param msg String, joka sisältää kyseisen pelaajan nimen
     * @return pelaajan statsit markdown tyylillä formatoituna, jos pelaajaa ei löydy palauttaa "Character not found!"
     * @throws java.net.MalformedURLException error
     * @throws java.io.IOException error
     */
    public String skillFormat(URL url, String msg) throws IOException {
        StringBuilder skillit = new StringBuilder();
        if (((HttpURLConnection) url.openConnection()).getResponseCode() == 200) { // tarkistetaan onko pelaajahahmo olemassa tarkistamalla responsecode
            skillit.append("*Player: ").append(msg).append("*```\n"); // viestiin -> "pelaaja: [pelaajan nimi]"
            skillit.append(String.format("%-14s%-8s%-14s\n", "SKILL", "LVL", "EXP")); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            for (int i = 0; i < 24; i++) {
                String[] skilli = reader.readLine().split(","); // taulukko muutoa: skillname, lvl, exp jaetaan tämä -> String[] muotoilun helpottamiseksi
                skillit.append(String.format("%-14s%-8s%-14s\n", this.skilltree[i], skilli[1], skilli[2])); //jokainen skilli omalle riviille, muotoa: skill lvl exp
            }
            skillit.append("```"); //viimeistellään muotoilu
        }
        return skillit.toString(); //palautetaan muotoiltu taulukko / "Character not found!"
    }

    /**
     *Formatoi numeroita helpommin luettaviksi
     * @param number Formatoitava numero
     * @return 1000000000 formatoituu 1,000,000,000
     */
    public String numberFormat(String number) {
        String numero = "";
        for (int i = 0; i < number.length(); i++) {
            if ((number.length() - i) % 3 == 0 && i != 0) {
                numero += ",";
            }
            numero += number.charAt(i);
        }
        return numero;
    }

    /**
     *Abilityn eli komennon longboi käyttämä logiikkametodi, joka palauttaa listan kuvia, joka riippuu montako kertaa komentoa on kutsuttu
     * @param update telegrammin lähettämä Update-olio, josta saadaan metodissa tarvittavan keskustelyryhmän id:n
     * @return Lista SendPhoto-olioita
     * @throws java.io.FileNotFoundException error
     * @throws java.io.UnsupportedEncodingException error
     */
    public ArrayList<SendPhoto> longBoi(Update update) throws FileNotFoundException, UnsupportedEncodingException {
        if (this.longboilkm == this.longboimax) {
            this.longboilkm = 0;
            this.longboilkm++;
            return buildLongBoi(update.getMessage().getChatId(), this.longboilkm - 1);
        } else {
            this.longboilkm++;
            return buildLongBoi(update.getMessage().getChatId(), this.longboilkm - 1);
        }
    }
    
    /**
     *metodi, jolla rakennetaan lista longboi-komennon käyttämistä kuvista
     * @param chatid chatid, jonka perusteella api tietää mihin keskusteluun kuvat lähetetään
     * @param lkm numero, joka määrää kuvalistan koon
     * @return Lista SendPhoto-olioita
     */
    public ArrayList<SendPhoto> buildLongBoi(Long chatid, int lkm) {
        ArrayList<SendPhoto> boiList = new ArrayList();
        boiList.add(this.ylavartalo.setChatId(chatid));
        for (int i = 0; i < lkm; i++) {
            boiList.add(this.ylavartalojatke.setChatId(chatid));
        }
        boiList.add(this.keskivartalo.setChatId(chatid));
        for (int i = 0; i < lkm; i++) {
            boiList.add(this.alavartalojatke.setChatId(chatid));
        }
        boiList.add(this.alavartalo.setChatId(chatid));
        return boiList;
    }
    
    public void reset() {
        this.longboilkm = 0;
    }
}
