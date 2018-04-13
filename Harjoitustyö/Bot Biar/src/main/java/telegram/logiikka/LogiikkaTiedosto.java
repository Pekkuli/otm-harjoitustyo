package telegram.logiikka;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;


public class LogiikkaTiedosto {
    
    public LogiikkaTiedosto(){
    }
    
    public String checkIf99(Update update) {
        try {
            Integer.parseInt(update.getMessage().getText());
            if (Integer.parseInt(update.getMessage().getText()) < 99 &&
                Integer.parseInt(update.getMessage().getText()) >= 1) {
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
    
    public String readPrice(Update update) throws MalformedURLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://rsbuddy.com/exchange/summary.json").openStream()));
        String jsontaulukko = reader.readLine(); // Luetaan esineiden hinnat tietokannasta
        String item = update.getMessage().getText();
        JsonNode juuri = new ObjectMapper(new JsonFactory()).readTree(jsontaulukko); //Json formatointi
        Iterator<Map.Entry<String, JsonNode>> iterator = juuri.fields();
        String result = "The price of " + item + " is "; 
        String price = "";
        while (iterator.hasNext()) { // Esineen haku tietokannasta 
            Map.Entry<String, JsonNode> kentta = iterator.next();
            if (kentta.getValue().get("name").asText().equalsIgnoreCase(item)) {
                price = kentta.getValue().get("overall_average").asText();
                break;
            }
        }
        if (price.isEmpty()) {
            result = "Item not found!";
        } else {
            result += "*" + numberFormat(price) + "*" + "gp"; 
        }
        return result;
    }
    
    public String readCharacter(Update update) throws MalformedURLException, IOException {
        String hs = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
        String skillit = "Character not found!";
        String[] skilltree = {"Total:", "Attack:", "Strength:", "Defence:", "Hitpoints:", "Ranged:", "Prayer:",
                              "Magic:", "Cooking:", "Woodcutting:", "Fletching:        ", "Fishing:", "Firemaking:",
                              "Crafting:", "Smithing:", "Mining:", "Herblore:", "Agility:", "Thieving:",
                              "Slayer:", "Farming:", "Runecraft:        ", "Hunter:             ", "Construction:"};
        hs += update.getMessage().getText();
        URL hiscore = new URL(hs); 
        HttpURLConnection huc = (HttpURLConnection) hiscore.openConnection();
        if (huc.getResponseCode() == 200) { // tarkistetaan onko pelaaja olemassa tarkistamalla responsecode
            skillit = "Player: " + update.getMessage().getText() + "\n" + "SKILL     LVL     EXP \n";
            BufferedReader reader = new BufferedReader(new InputStreamReader(hiscore.openStream()));
            for (int i = 0; i < 24; i++) {
                String[] skilli = reader.readLine().split(",");
                skillit += skilltree[i] + " " + skilli[1] + "     " + skilli[2] + "\n";
            }
        } 
        return skillit;
    }
    
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
    
    public SendMessage marcoPolo(long chatid) {
        int random = ThreadLocalRandom.current().nextInt(3);
        switch (random) {
            case 0:
                return new SendMessage(chatid, "Polo!");
            case 1:
                return new SendMessage(chatid, "Polo...");
            case 2:
                return new SendMessage(chatid, "Polo?");
            default:
                return new SendMessage(chatid, "Miten tässä nyt näin kävi?");
        }
    }
}
