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
    private int longboilkm = 0;
    
    public LogiikkaTiedosto() {
        this.ylavartalo = new SendPhoto().setNewPhoto(new File("src/resources/images/ylävartalo.png"));
        this.ylavartalojatke = new SendPhoto().setNewPhoto(new File("src/resources/images/ylävartalojatke.png"));
        this.keskivartalo = new SendPhoto().setNewPhoto(new File("src/resources/images/keskivartalo.png"));
        this.alavartalo = new SendPhoto().setNewPhoto(new File("src/resources/images/alavartalo.png"));
        this.alavartalojatke = new SendPhoto().setNewPhoto(new File("src/resources/images/alavartalojatke.png"));
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
        String result = "The price of " + update.getMessage().getText() + " is "; 
        String price = "";
        JsonElement jelement = new JsonParser().parse(jsontaulukko);
        for (Map.Entry<String, JsonElement> obj : jelement.getAsJsonObject().entrySet()) {
            if (obj.getValue().getAsJsonObject().get("name").getAsString().equalsIgnoreCase(update.getMessage().getText())) {
                price = obj.getValue().getAsJsonObject().get("overall_average").getAsString();
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
        StringBuilder skillit = new StringBuilder();
        String[] skilltree = {"Total:", "Attack:", "Strength:", "Defence:", "Hitpoints:", "Ranged:", "Prayer:",
                              "Magic:", "Cooking:", "Woodcutting:", "Fletching:", "Fishing:", "Firemaking:",
                              "Crafting:", "Smithing:", "Mining:", "Herblore:", "Agility:", "Thieving:",
                              "Slayer:", "Farming:", "Runecraft:", "Hunter:", "Construction:"};
        hs += update.getMessage().getText();
        URL hiscore = new URL(hs); 
        HttpURLConnection huc = (HttpURLConnection) hiscore.openConnection();
        if (huc.getResponseCode() == 200) { // tarkistetaan onko pelaajahahmo olemassa tarkistamalla responsecode
            skillit.append("*Player: ").append(update.getMessage().getText()).append("*```\n");
            skillit.append(String.format("%-14s%-8s%-14s\n", "SKILL", "LVL", "EXP"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(hiscore.openStream()));
            for (int i = 0; i < 24; i++) {
                String[] skilli = reader.readLine().split(",");
                skillit.append(String.format("%-14s%-8s%-14s\n", skilltree[i], skilli[1], skilli[2]));
            }
            skillit.append("```");
        } else {
            skillit.append("Character not found!");
        }
        return skillit.toString();
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
    
    public ArrayList<SendPhoto> longBoi(Update update) throws FileNotFoundException, UnsupportedEncodingException {
        ArrayList<SendPhoto> boiList = new ArrayList();
        Long chatId = update.getMessage().getChatId();
        boiList.add(this.ylavartalo.setChatId(chatId));
        for (int i = 0; i < this.longboilkm; i++) {
            boiList.add(this.ylavartalojatke.setChatId(chatId));
        }
        boiList.add(this.keskivartalo.setChatId(chatId));
        for (int i = 0; i < this.longboilkm; i++) {
            boiList.add(this.alavartalojatke.setChatId(chatId));
        }
        boiList.add(this.alavartalo.setChatId(chatId));
        this.longboilkm++;
        return boiList;
    }
    
//    public SendMessage marcoPolo(long chatid) {
//        int random = ThreadLocalRandom.current().nextInt(3);
//        switch (random) {
//            case 0:
//                return new SendMessage(chatid, "Polo!");
//            case 1:
//                return new SendMessage(chatid, "Polo...");
//            case 2:
//                return new SendMessage(chatid, "Polo?");
//            default:
//                return new SendMessage(chatid, "Miten tässä nyt näin kävi?");
//        }
//    }
    
}
