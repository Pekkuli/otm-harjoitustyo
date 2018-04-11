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
import java.util.function.Predicate;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;
import static org.telegram.telegrambots.api.methods.ParseMode.HTML;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotBiar extends AbilityBot{
    
    public static String BOT_TOKEN = "500444223:AAG9Gd2WTJlSaLFMhahvzjExMNZ6FtM_6nQ";
    public static String BOT_USERNAME = "BotBiar_bot";

    public BotBiar() {
        super(BOT_TOKEN,BOT_USERNAME);
    }
    
    public void setSender(MessageSender sender){
        this.sender=sender;
    }
  
    public void SetSilent(SilentSender silent){
        this.silent=silent;
    }
    
    public void ResetDb() throws IOException{
        this.db.clear();
        this.db.close();
    }
    

    @Override
    public int creatorId() {
        return 1337; // Your ID here
    }

    public Ability sayHelloWorld() {
         return Ability
            .builder()
            .name("hello")
            .info("says hello world!")
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {
                try{
                    sender.execute(new SendMessage(ctx.chatId(), "Hello world!"));
                } catch (TelegramApiException e) {}
                })
            .build();
    }
    
   
    
    public Ability saysHelloWorldToFriend() {
        return Ability.builder()
            .name("sayhi")
            .info("Says hi to a friend")
            .privacy(PUBLIC)
            .locality(USER)
            .input(1)
            .action(ctx -> {
                try{
                    sender.execute(new SendMessage(ctx.chatId(), "Hi "+ ctx.firstArg()+"!"));
                } catch (TelegramApiException e) {}
                })
            .build();
    }
    
    public Ability FishingLvl(){
        String msg ="Fishing lvl?";
        
        SendMessage mg = new SendMessage();
        
        return Ability.builder()
            .name("fishinglvl")
            .info("Tell your fishing level to the bot (1-99)")
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                  silent.send(Onko99(update), getChatId(update));
                },
                MESSAGE,
                REPLY,
                isReplyToBot(),
                isReplyToMessage(msg)
            )
            .build();
    }
    
    private Predicate<Update> isReplyToMessage(String message) {
      return update -> {
        Message reply = update.getMessage().getReplyToMessage();
        return reply.hasText() && reply.getText().equalsIgnoreCase(message);
      };
    }
  
    private Predicate<Update> isReplyToBot() {
      return update -> update.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }
    
    private String Onko99(Update update){
        try {
            Integer.parseInt(update.getMessage().getText());
            if(Integer.parseInt(update.getMessage().getText())<99 &&
                Integer.parseInt(update.getMessage().getText())>=1){
                return "Nice";
            } else if(Integer.parseInt(update.getMessage().getText()) ==99){
                return "No life!";
            }  else {
                return "OK... ( ͡° ͜ʖ ͡°)";
            }
        } catch(NumberFormatException | NullPointerException e){
            return "OK... ( ͡° ͜ʖ ͡°)";
        }
    }
    
    public Ability PriceCheck(){
        String msg ="Item name?";
        
        return Ability.builder()
            .name("price")
            .info("Checks the price of a specified item")
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                    try{
                        sender.execute(new SendMessage(getChatId(update),LueHinta(update)).setParseMode("Markdown"));
                    }catch (IOException | TelegramApiException e) {
                    }
                },
                MESSAGE,
                REPLY,
                isReplyToBot(),
                isReplyToMessage(msg)
            )
            .build();
    }
    
    public String LueHinta(Update update) throws MalformedURLException, IOException {
//        Luetaan esineiden hinnat tietokannasta
        URL summary = new URL("https://rsbuddy.com/exchange/summary.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(summary.openStream()));
        String json = reader.readLine();
        
        String item=update.getMessage().getText();
//        Json formatointia
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = mapper.readTree(json);
        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
//        Esineen haku tietokannasta
        String result ="The price of "+item+" is ";
        String price="";
        while(fieldsIterator.hasNext()){
            Map.Entry<String,JsonNode> field = fieldsIterator.next();
            if(field.getValue().get("name").asText().equalsIgnoreCase(item)){
                price=field.getValue().get("overall_average").asText();
                break;
            }
        }
        if(price.isEmpty()){
            result="Item not found!";
        } else {
            result+="*"+NumberFormat(price)+"*"+"gp";
        }
        return result;
    }
    
    public Ability CheckHighscore(){
        String msg ="Players name?";
        
        return Ability.builder()
            .name("hiscore")
            .info("Checks the highscore of specified player")
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                    try {
                        execute(new SendMessage(getChatId(update),LueHahmo(update)).setParseMode(HTML));
                    } catch (IOException | TelegramApiException ex) {
                    }
                },
                MESSAGE,
                REPLY,
                isReplyToBot(),
                isReplyToMessage(msg)
                )
                .build();
    }
    
    public String LueHahmo(Update update) throws MalformedURLException, IOException{
        String hs="http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
        
        String skillit="Character not found!";
        String[] skilltree = {"Total:", "Attack:", "Strength:", "Defence:", "Hitpoints:", "Ranged:", "Prayer:",
                            "Magic:", "Cooking:", "Woodcutting:", "Fletching:        ", "Fishing:", "Firemaking:",
                            "Crafting:", "Smithing:", "Mining:", "Herblore:", "Agility:", "Thieving:",
                            "Slayer:", "Farming:", "Runecraft:        ", "Hunter:             ", "Construction:"};
        
        hs+=update.getMessage().getText();
        URL hiscore = new URL(hs); 
        HttpURLConnection huc=(HttpURLConnection) hiscore.openConnection();
        
        if(huc.getResponseCode()==200){
            skillit="Player: "+update.getMessage().getText()+"\n";
            skillit+="SKILL     LVL     EXP \n";
            BufferedReader reader = new BufferedReader(new InputStreamReader(hiscore.openStream()));
            for(int i=0;i<24;i++){
                String[] skilli=reader.readLine().split(",");
                skillit+=skilltree[i]+" "+skilli[1]+"     "+skilli[2]+"\n";
            }
            System.out.println(skillit);
//            skillit=builder.toString();
//            System.out.println(builder.toString());
        } 
        return skillit;
    }
    
    private String NumberFormat(String number){
        String numero="";
        for(int i=0;i<number.length();i++){
            if((number.length()-i)%3 ==0 && i !=0){
                numero+=",";
            }
            numero+=number.charAt(i);
        }
        return numero;
    }
    
    
    
    public void LähetäViesti(SendMessage message){
        try{
                execute(message);
            } catch (TelegramApiException e) {
            }
    }
    
    public SendMessage MarcoPolo(long chat_id){
        int random = ThreadLocalRandom.current().nextInt(3);
        switch (random) {
            case 0:
                return new SendMessage(chat_id, "Polo!");
            case 1:
                return new SendMessage(chat_id, "Polo...");
            case 2:
                return new SendMessage(chat_id, "Polo?");
            default:
                return new SendMessage(chat_id, "Miten tässä nyt näin kävi?");
        }
    }
} 