package telegram.logiikka;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import telegram.bot.BotBiar;


public class LogiikkaTiedostoTest {
    
    private BotBiar bot = new BotBiar();
    private LogiikkaTiedosto logiikka = new LogiikkaTiedosto();
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
    public void tearDown() throws IOException {
        bot.resetDb();
    }

    @Test
    public void testCheckIf99() {
        Update upd = mockUpdateMsg("99");
        assertEquals("No life!", logiikka.checkIf99(upd));
        upd = mockUpdateMsg("4");
        assertEquals("Nice", logiikka.checkIf99(upd));
        upd = mockUpdateMsg("aaaa");
        assertEquals("OK... ( ͡° ͜ʖ ͡°)", logiikka.checkIf99(upd));
        upd = mockUpdateMsg("-1");
        assertEquals("OK... ( ͡° ͜ʖ ͡°)", logiikka.checkIf99(upd));  
    }

    @Test
    public void testReadPrice() throws IOException{
        Update upd = mockUpdateMsg("Cannonball");
        assertNotSame(logiikka.readPrice(upd), "Item not found!");
        upd = mockUpdateMsg("aaaaaaaaaaaaaa");
        assertEquals(logiikka.readPrice(upd), "Item not found!");
        upd = mockUpdateMsg("-12345");
        assertEquals(logiikka.readPrice(upd), "Item not found!");
    }

    @Test
    public void testReadCharacter() throws IOException{
        Update upd = mockUpdateMsg("Woox");
        assertNotSame(logiikka.readCharacter(upd), "Character not found!");
        upd = mockUpdateMsg("$a$");
        assertEquals(logiikka.readCharacter(upd), "Character not found!");
    }

    @Test
    public void testNumberFormat() {
        assertEquals("1,000,000,000", logiikka.numberFormat("1000000000"));
        assertEquals("100", logiikka.numberFormat("100"));
    }
    
//    @Test
//    public void testlongBoi(){
//        
//        assertEquals(logiikka.longBoi(update));
//    }


//    @Test
//    public void testMarcoPolo() {
//        logiikka.marcoPolo(0).getText().equalsIgnoreCase(anotherString)
//        assertEquals((True, logiikka.marcoPolo(1).;
//    }
    
    private Update mockUpdateMsg(String args) {
        Update upd = mock(Update.class);
        Message msg = mock(Message.class);
        User user = mock(User.class);
        
        when(msg.getFrom()).thenReturn(user);
        when(msg.getText()).thenReturn(args);
        when(msg.hasText()).thenReturn(true);
        when(msg.isUserMessage()).thenReturn(true);
        when(upd.getMessage()).thenReturn(msg);
        
        return upd;
    }
    
}
