package pw.roccodev.hive.monthlies.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import pw.roccodev.hive.monthlies.SupportedGame;
import tk.roccodev.nightfallbot.Test;

import java.io.IOException;

import static com.google.firebase.FirebaseApp.*;

public class Database {

    public static void initAll() {
        try {
            GoogleCredentials creds = Credentials.get();
            for(SupportedGame g : SupportedGame.values()) {
                String name = g.getDbName();
                initializeApp(new FirebaseOptions.Builder().setCredentials(creds)
                        .setDatabaseUrl("https://monthlies-" + name + ".firebaseio.com")
                                .setStorageBucket("monthlies-" + name + ".appspot.com").build(),
                        g.name());
            }

            Test.run(creds);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
