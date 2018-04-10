import org.junit.Before;
import org.junit.Test;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.objects.EndUser;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.api.objects.Update;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;


public class BotBiarTest {
    
    public static final int USER_ID = 1337;
    public static final long CHAT_ID = 1337L;
    
    private BotBiar bot;
    private MessageSender sender;
    
    @Before
    public void setUp() {
        bot = new BotBiar();
        sender = mock(MessageSender.class);
        bot.sender = sender;
        
    }
    
    @Test
    public void canAskFishingLvl(){
        Update upd = new Update();
        
        EndUser user = EndUser.endUser(USER_ID, "Pekka", "Puu", "PekkaPuu");
        MessageContext context = MessageContext.newContext(upd, user, CHAT_ID, "4");
        
        bot.FishingLvl().action().accept(context);
        
        Mockito.verify(sender, times(1)).send("Hello World!", CHAT_ID);
    }
}
