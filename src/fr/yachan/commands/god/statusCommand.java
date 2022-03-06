package fr.yachan.commands.god;

import fr.yachan.Main;
import fr.yachan.usefull.GenericCommand;
import fr.yachan.usefull.Logs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class statusCommand extends GenericCommand {

    private String status;

    public statusCommand() {
        super(true, true, "status");
        this.status = "Voici mes informations :\n\nDate de naissance : 30/07/2020\n\nVersion :  "
                        + Main._VERSION +"\n\nJDA : "+Main._LIB_V.get("jda")
                        +"\nIni4J : "+Main._LIB_V.get("jda")
                        +"\npsql : "+Main._LIB_V.get("jda")
        ;
    }

    @Override
    protected void applyPrivateLogic(@NotNull MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        MessageEmbed messageEmbed = builder.setTitle("Information")
                .setDescription(this.status)
                .setColor(219675).build();
        event.getChannel().sendMessageEmbeds(messageEmbed).queue();
        Logs.logInfo(event.getAuthor().getName(),event.getChannel().getName(),
               "PRIVATE","Commande god effectué : getYasukoInformation");
    }

    @Override
    protected void applyPublicLogic(@NotNull MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        MessageEmbed messageEmbed = builder.setTitle("Information")
                .setDescription(this.status)
                .setColor(219675).build();
        event.getChannel().sendMessageEmbeds(messageEmbed).queue();
        Logs.logInfo(event.getAuthor().getName(),event.getChannel().getName(),
                event.getGuild().getName(),"Commande god effectué : getYasukoInformation");
    }
}
