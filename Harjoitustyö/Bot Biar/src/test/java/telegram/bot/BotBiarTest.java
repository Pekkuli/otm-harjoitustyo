package telegram.bot;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.telegram.abilitybots.api.objects.EndUser;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class BotBiarTest {
    
    public static final int USER_ID = 1337;
    public static final long CHAT_ID = 1337L;
    
    private BotBiar bot = new BotBiar();
    private MessageSender sender;
    private SilentSender silent;
    
    @Before
    public void setUp() {
        
        sender = mock(MessageSender.class);
        silent = mock(SilentSender.class);
        bot.setSender(sender);
        bot.setSilent(silent);
    }
    
    @After
    public void tearDown() throws IOException{
        bot.resetDb();
    }
    
    @Test
    public void testSayHelloWorld() throws TelegramApiException{
        Update upd  = new Update();
        EndUser user = EndUser.endUser(USER_ID, "Pekka", "Puu", "PekkaPuu");
        MessageContext context = MessageContext.newContext(upd, user, CHAT_ID);
        
        bot.sayHelloWorld().action().accept(context);
        
        verify(sender, times(1)).execute(new SendMessage(CHAT_ID, "Hello world!"));
    }
    
    @Test
    public void testSayHelloToFriend() throws TelegramApiException{
        Update upd  = new Update();
        EndUser user = EndUser.endUser(USER_ID, "Pekka", "Puu", "PekkaPuu");
        MessageContext context = MessageContext.newContext(upd, user, CHAT_ID, "Pekka");
        
        bot.saysHelloWorldToFriend().action().accept(context);
        
        Mockito.verify(sender, times(1)).execute(new SendMessage(CHAT_ID, "Hi Pekka!"));
    }
    
//    @Test 
//    public void AskFishingLvlWorks() throws TelegramApiException{
//        Update upd = new Update();
//        EndUser user = EndUser.endUser(USER_ID, "Pekka", "Puu", "PekkaPuu");
//        MessageContext context = MessageContext.newContext(upd, user, CHAT_ID, "89");
//        bot.FishingLvl().action().accept(context);
//        verify(sender, times(1)).execute(new SendMessage(CHAT_ID, "Nice"));
//    }
}
