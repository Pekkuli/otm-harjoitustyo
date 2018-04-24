package telegram.bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegram.logiikka.LogiikkaTiedosto;

public class BotBiar extends AbilityBot {
    
    public static String botToken = "500444223:AAG9Gd2WTJlSaLFMhahvzjExMNZ6FtM_6nQ";
    public static String botUserName = "BotBiar_bot";
    private final LogiikkaTiedosto logiikka;

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

    public Ability sayHelloWorld() {
        return Ability
            .builder().name("hello").info("say hello world!").locality(ALL).privacy(PUBLIC)
            .action(ctx -> {
                try {
                    sender.execute(new SendMessage(ctx.chatId(), "Hello world!"));
                } catch (TelegramApiException e) { }
            })
            .build();
    }
    
    public Ability saysHelloWorldToFriend() {
        return Ability
            .builder().name("sayhi").info("Say hi to a friend").privacy(PUBLIC).locality(ALL)
            .input(1).action(ctx -> {
                try {
                    sender.execute(new SendMessage(ctx.chatId(), "Hi " + ctx.firstArg() + "!"));
                } catch (TelegramApiException e) { }
            })
            .build();
    }
    
    public Ability fishingLvl() {
        String msg = "Fishing lvl?";
        return Ability.builder().name("fishinglvl")
            .info("Tell your fishing level to the bot (1-99)")
            .privacy(PUBLIC).locality(ALL).input(0)
            .action(ctx -> silent.forceReply(msg, ctx.chatId()))
            .reply(update -> {
                try {
                    sender.execute(new SendMessage(getChatId(update), logiikka.checkIf99(update)));
                } catch (TelegramApiException e) {
                }
            }, MESSAGE, REPLY, isReplyToBot(), isReplyToMessage(msg)).build();
    }
    
    private Predicate<Update> isReplyToMessage(String message) {
        return update -> {
            Message reply = update.getMessage().getReplyToMessage();
            return reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }
  
    private Predicate<Update> isReplyToBot() {
        return update -> update.getMessage()
                .getReplyToMessage().getFrom().getUserName()
                .equalsIgnoreCase(getBotUsername());
    }
    
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
            }, MESSAGE, REPLY, isReplyToBot(), isReplyToMessage(msg))
            .build();
    }
    
    public Ability checkHighScore() {
        String msg = "Players name?";
        return Ability.builder()
            .name("hiscore")
            .info("Check the highscore of specified player")
            .privacy(PUBLIC)
            .locality(ALL)
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
    
    public Ability longBoi() {
        return Ability
            .builder().name("longBoi").info("check longBoi").locality(ALL).privacy(PUBLIC)
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
    
//    public void messageTo(SendMessage message) {
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//        }
//    }
} 