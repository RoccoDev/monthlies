package pw.roccodev.hive.monthlies.firebase;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class Credentials {

    public static GoogleCredentials get() throws IOException {

        return GoogleCredentials
                .fromStream(new ByteArrayInputStream(Base64.getDecoder().decode(System.getenv("FIREBASE_JSON"))));

    }

}
