package pw.roccodev.hive.monthlies.utils.discord;

import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;

public class SendWebhook {

    private static String WEBHOOK_URL = System.getenv("WEBHOOK_URL");
    private static TemmieWebhook hook = new TemmieWebhook(WEBHOOK_URL);

    public static void sendReset() {
        DiscordEmbed embed = DiscordEmbed.builder()
                .title("Monthlies have been reset!")
                .description("Go [here](https://hive.roccodev.pw/monthlies/archive) for info & leaderboard.")
                .build();

        hook.sendMessage(DiscordMessage.builder().content("").embed(embed).build());
    }

    public static void sendAlmostReset() {
        DiscordEmbed embed = DiscordEmbed.builder()
                .title("Monthlies are being reset soon!")
                .description("Monthlies are being reset in 2 hours.")
                .build();

        hook.sendMessage(DiscordMessage.builder().content("").embed(embed).build());
    }

}
