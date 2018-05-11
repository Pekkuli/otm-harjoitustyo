package telegram.bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegram.logiikka.LogiikkaTiedosto;

public class BotBiar extends AbilityBot {

    public static String botToken = "500444223:AAG9Gd2WTJlSaLFMhahvzjExMNZ6FtM_6nQ";
    public static String botUserName = "BotBiar_bot";
    /**
    * BotBiarin käyttämä komentojen logiikan sisältämä luokka.
    */
    private final LogiikkaTiedosto logiikka;

    /**
     * Luo BotBiar-botin ja inittaa botin käyttämän logiikkatiedoston.
     */
    public BotBiar() {
        super(botToken , botUserName);
        this.logiikka = new LogiikkaTiedosto();
    }
    
    public void setSender(MessageSender sender) {
        this.sender = sender;
    }
  
    public void setSilent(SilentSender silent) {
        this.silent = silent;
    }
    
    public void resetDb() throws IOException {
        this.db.clear();
        this.db.close();
    }
    
    @Override
    public int creatorId() {
        return 1337; 
    }
    
    /**
     *Ability eli komento BotBiarin aloitusinfon esittämiseksi.
     * @return palauttaa viestin, joka sisältää botin yleisen informaation
     */
    public Ability start() {
        return Ability.builder()
                .name("start")
                .info("Show bot starting info")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    try {
                        SendMessage msg = new SendMessage(ctx.chatId(), 
                            "*Welcome!* \n This is Bot 🅱️iar, your [Runescape](https://oldschool.runescape.com/)-bot \n"
                            + "/commands gives all the commands you can use :) \n"
                            + "If you need help type /help");
                        msg.setParseMode("MarkDown");
                        sender.execute(msg);
                    } catch (TelegramApiException ex) { 
                    } 
                })
                .build();
    }
    
    /**
     *Ability eli komento BotBiarin aloitusinfon esittämiseksi (samam kuin /start).
     * @return palauttaa viestin, joka sisältää botin yleisen informaation
     */
    public Ability help() {
        return Ability.builder()
                .name("help")
                .info("Show bot starting info")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    try {
                        SendMessage msg = new SendMessage(ctx.chatId(), 
                            "*Welcome!* \n This is Bot 🅱️iar, your [Runescape](https://oldschool.runescape.com/)-bot \n"
                            + "/commands gives all the commands you can use :)");
                        msg.setParseMode("MarkDown");
                        sender.execute(msg);
                    } catch (TelegramApiException ex) { 
                    } 
                })
                .build();
    }
    
    /**
     *Metodi joka tarkistaa onko saatu viesti komento vai ei.
     * @return bottikomento, jos viesti tunnistettiin komennoksi,
     * muulloin palautetaan sama viesti kuin /help komennolla
     */
    public Reply noCommand() {
        Consumer<Update> action = (Update upd) -> {
            try {
                sender.execute(new SendMessage(upd.getMessage().getChatId(),
                        "*Welcome!* \n This is Bot 🅱️iar, your [Runescape](https://oldschool.runescape.com/)-bot \n"
                                + "/commands gives all the commands you can use :)").setParseMode("MarkDown"));
            } catch (TelegramApiException ex) {
            }
        };
        return Reply.of(action, noCmd());
    }
    
    private Predicate<Update> noCmd() {
        return upd -> {
            return !upd.getMessage().isCommand() && !upd.getMessage().isReply();
        };
    }
    
    /**
     *Ability eli komento BotBiarin resettaamiseksi
     * @return resettaa logiikan, oikeastaan vain /longboi-komentoa varten
     */
    public Ability reset() {
        return Ability.builder()
                .name("reset")
                .info("Reset BotBiar")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    logiikka.reset();
                    try {
                        sender.execute(new SendMessage(ctx.chatId(), "Bot 🅱️iar into reset →"));
                        this.logiikka.reset();
                    } catch (TelegramApiException e) { }
                })
                .post(ctx -> {
                    silent.execute(new SendMessage(ctx.chatId(), "Bot 🅱️iar is reset"));
                })
                .build();
    }

    /**
     *Ability eli komento botille, jota kutsuttaessa botti sanoo "hello world"
     * @return "hello world!" käyttäjän keskusteluun
     */
    public Ability sayHelloWorld() {
        return Ability.builder()
                .name("hello")
                .info("say hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    try {
                        sender.execute(new SendMessage(ctx.chatId(), "Hello world!"));
                    } catch (TelegramApiException e) { }
                })
            .build();
    }
    
    /**
     *Ability eli komento jota kutsuttaessa botti tervehtii mainittua henkilöä.
     * @return "Hi [Henkilönnimi]!" käyttäjän keskusteluun
     */
    public Ability saysHelloWorldToFriend() {
        return Ability.builder()
            .name("sayhi")
            .info("[Friends name] Say hi to a friend")
            .privacy(PUBLIC)
            .locality(ALL)
            .input(1).action(ctx -> {
                try {
                    sender.execute(new SendMessage(ctx.chatId(), "Hi " + ctx.firstArg() + "!"));
                } catch (TelegramApiException e) { }
            })
            .build();
    }
    
    /**
     *Ability eli komento, jota käyttämällä botti kysyy käyttäjän kalastustasoa eli fishing leveliä.
     * @return "get life" jos 99, "Nice" jos 1-98 ja muihin vastauksiin botti vastaa sarkastisesti
     */
    public Ability fishingLvl() {
        String msg = "Fishing lvl?";
        return Ability.builder()
            .name("fishinglvl")
            .info("Tell your fishing level to the bot (1-99)")
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                try {
                    sender.execute(new SendMessage(getChatId(update), logiikka.checkIf99(update)));
                } catch (TelegramApiException e) {
                }
            }, MESSAGE, REPLY, isReplyToBot(), isReplyToMessage(msg))
            .build();
    }
    
    public Predicate<Update> isReplyToMessage(String message) {
        return update -> {
            Message reply = update.getMessage().getReplyToMessage();
            return reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }
    
    public Predicate<Update> isReplyToBot() {
        return update -> update.getMessage()
                .getReplyToMessage().getFrom().getUserName()
                .equalsIgnoreCase(getBotUsername());
    }
    
    /**
     *Ability eli komento jonka käyttämällä botti kysyy esineen nimeä käyttäjältä ja hakee käytäjälle esineen hinnan
     * @return esineen hinta, jos esine on olemassa/botti löytää kyseisen esineen (palvelin alhaalla -> esineitä ei voi hakea)
     */
    public Ability priceCheck() {
        String msg = "Item name?";
        
        return Ability.builder()
            .name("price")
            .info("Check the price of a specified item")
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                try {
                    sender.execute(new SendMessage(getChatId(update), logiikka.readPrice(update)).setParseMode("MarkDown"));
                } catch (IOException | TelegramApiException e) {
                }
            }, MESSAGE, REPLY, 
            isReplyToBot(), 
            isReplyToMessage(msg))
            .build();
    }
    
    /**
     *Ability eli komento, jota käyttämällä botti kysyy käytäjältä pelaajan nimeä
     * @return pelaajan statsit, jos pelaaja on olemassa/botti löytää kyseisen hahmon
     */
    public Ability checkHighScore() {
        String msg = "Players name?";
        return Ability.builder()
            .name("hiscore")
            .info("Check the highscore of specified player")
            .privacy(PUBLIC).locality(ALL)
            .input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                    try {
                        execute(new SendMessage(getChatId(update), logiikka.readCharacter(update)).setParseMode("MarkDown"));
                    } catch (IOException | TelegramApiException e) {
                    }
            }, MESSAGE, REPLY,
            isReplyToBot(),
            isReplyToMessage(msg)
            )
            .build();
    }
    
    /**
     *Ability eli komento jota käyttämällä pelaaja voi tarkistaa longboin
     * @return longboi
     */
    public Ability longBoi() {
        return Ability
            .builder()
            .name("longBoi")
            .info("check longBoi")
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {
                try {
                    silent.send("Here you go!", ctx.chatId());
                    for (SendPhoto photo : logiikka.longBoi(ctx.update())) {
                        sendPhoto(photo);
                    }
                } catch (FileNotFoundException | UnsupportedEncodingException | TelegramApiException e) {
                } 
            }
            )
            .build();
    }
} 