package ru.func.bot42;

import com.oopsjpeg.osu4j.OsuUser;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author func 31.03.2020
 * @project Bot42
 */
public class Main {

    private static boolean even = true;
    private static JDA JDA;
    private static Osu OSU;

    public static void main(String[] args) throws Exception {
        OSU = Osu.getAPI("ВАШ OSU КЛЮЧ");
        JDA = new JDABuilder("ВАШ DISCORD КЛЮЧ")
                .build();
        new Main().swapNames();
    }

    private void swapNames() throws InterruptedException {
        even = !even;
        JDA.awaitReady().getCategories()
                .get(1)
                .getVoiceChannels()
                .get(0)
                .getMembers()
                .forEach(member -> {
                    String name = member.getNickname() == null ?
                            member.getEffectiveName() :
                            member.getNickname().split("\\s+")[0];

                    try {
                        OsuUser user = OSU.users.query(new EndpointUsers.ArgumentsBuilder(
                                name.replace("фанк", "asyncFunc")
                        ).build());

                        member.modifyNickname(name + " " + (even ? "#" + user.getRank() : user.getPP() + "PP"))
                                .timeout(5, TimeUnit.SECONDS)
                                .submit();
                    } catch (Exception ignored) {
                    }
                });
        Thread.sleep(4000);
        swapNames();
    }
}
