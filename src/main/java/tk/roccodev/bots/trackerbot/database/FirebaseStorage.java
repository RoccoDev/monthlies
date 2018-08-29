package tk.roccodev.bots.trackerbot.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class FirebaseStorage {

    public static DatabaseReference reference;

    public static void init() throws IOException {


        FirebaseOptions opts = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(Base64.getDecoder().decode(System.getenv("FIREBASE_JSON")))))
                .setDatabaseUrl("https://staff-tracker-data.firebaseio.com").build();

        FirebaseApp.initializeApp(opts);
        reference = FirebaseDatabase.getInstance().getReference();

    }

}
