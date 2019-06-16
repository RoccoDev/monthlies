package tk.roccodev.nightfallbot;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class Test {

    public static FirebaseApp otherApp;
    public static FirebaseApp seasonDataApp;
    public static FirebaseApp streakApp;
    public static FirebaseApp histoStreakApp;
    public static FirebaseApp miscApp;
    public static FirebaseApp kfApp;
    public static FirebaseApp mainApp;

    public static void run(GoogleCredentials cred) {
        FirebaseOptions optionsMisc = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://roccodev-misc.firebaseio.com/")
                .build();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://bedwarstoolkit-monthlies-bed.firebaseio.com/")
                .setStorageBucket("bedwarstoolkit-monthlies-bed.appspot.com")
                .build();

        FirebaseOptions options1 = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://bedwarstoolkit-season-data.firebaseio.com/")
                .build();

        FirebaseOptions options3 = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://bedwarstoolkit-streaks-bed.firebaseio.com/")
                .build();

        FirebaseOptions options4 = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://bedwarstoolkit-streaks-beds.firebaseio.com/")
                .build();

        FirebaseOptions options2 = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://beezig-fcm.firebaseio.com/")
                .build();

        FirebaseOptions options5 = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://monthlies-bed-kf.firebaseio.com/")
                .build();


        mainApp = FirebaseApp.initializeApp(options);
        otherApp = FirebaseApp.initializeApp(options2, "other");
        seasonDataApp = FirebaseApp.initializeApp(options1, "season-data");
        streakApp = FirebaseApp.initializeApp(options3, "streak");
        histoStreakApp = FirebaseApp.initializeApp(options4, "histostreak");
        miscApp = FirebaseApp.initializeApp(optionsMisc, "misc");
        kfApp = FirebaseApp.initializeApp(options5, "kf");
    }
}
