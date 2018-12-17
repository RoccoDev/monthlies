package pw.roccodev.hive.monthlies.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import pw.roccodev.hive.monthlies.SupportedGame;

import java.io.IOException;

import static com.google.firebase.FirebaseApp.*;

public class Database {

    public static void initAll() {
        try {
            GoogleCredentials creds = Credentials.get();
            for(SupportedGame g : SupportedGame.values()) {
                initializeApp(new FirebaseOptions.Builder().setCredentials(creds)
                        .setDatabaseUrl("https://monthlies-" + g.name().toLowerCase() + ".firebaseio.com")
                                .setStorageBucket("monthlies-" + g.name().toLowerCase() + ".appspot.com").build(),
                        g.name());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
