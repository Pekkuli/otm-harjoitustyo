
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


public class Main {
    
    public static void main(String[] args) throws TelegramApiRequestException{
        ApiContextInitializer.init();
        
        TelegramBotsApi botapi = new TelegramBotsApi();
        
        botapi.registerBot(new BotBiar());
    }
}
