package fr.yachan.commands.sora;

import fr.yachan.database.SoraDataBase;
import fr.yachan.entity.SumUpEntity;
import fr.yachan.usefull.GenericCommand;
import fr.yachan.usefull.Logs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class StartSoraClassifier extends GenericCommand {

    private SoraDataBase db;

    public StartSoraClassifier() {
        super(true, true, "sora");
        try {
            this.db = new SoraDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void applyPrivateLogic(@NotNull MessageReceivedEvent event) {
        sendPossibleSumUp(event.getChannel());
        //super.applyPrivateLogic(event);
    }

    @Override
    protected void applyPublicLogic(@NotNull MessageReceivedEvent event) {
        sendPossibleSumUp(event.getChannel());
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (!event.getUser().isBot())
        {
            String emote = event.getReaction().getReactionEmote().getEmoji();
            String messageId = event.getMessageId();
            Message msg = event.getChannel().getHistoryAround(messageId, 1).complete().getMessageById(messageId);
            if (msg.getEmbeds().size() >0){
                MessageEmbed eb = msg.getEmbeds().get(0);
                if (Objects.requireNonNull(eb.getTitle()).startsWith("Sum Up Id : ")){
                    int _sumId = Integer.parseInt(eb.getTitle().toString().replace("Sum Up Id : ",""));
                    String stat = "UN";
                    if (emote.equalsIgnoreCase("✅")) stat = "OK";
                    if (emote.equalsIgnoreCase("❌")) stat = "KO";
                    Logs.logDebug(emote);
                    if (!stat.equalsIgnoreCase("UN")) {
                        this.db.changeStatus(_sumId,stat);
                        sendPossibleSumUp(event.getChannel());

                    }
                }
            }

        }

    }

    private void sendPossibleSumUp(MessageChannel channel) {
        SumUpEntity sumUp = this.db.getAnUnKnowSumUp();
        EmbedBuilder builder = new EmbedBuilder();
        MessageEmbed messageEmbed = builder.setTitle("Sum Up Id : " + sumUp.getId())
                .setDescription(sumUp.getText())
                .setColor(219675).build();
        channel.sendMessageEmbeds(messageEmbed).queue(
                msg-> {
                    msg.addReaction("✅").queue();
                    msg.addReaction("❌").queue();
                }
        );
    }


}
