package fr.yachan.usefull;

import fr.yachan.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GenericCommand extends ListenerAdapter {
    private final boolean isGuildCommand;
    private final boolean isPrivateCommande;
    private final String keyWord;
    protected static String help;
    private HashMap<String, Boolean> rigth;

    public GenericCommand(boolean isGuildCommand, boolean isPrivateCommande, String keyWord) {
        this.isGuildCommand = isGuildCommand;
        this.isPrivateCommande = isPrivateCommande;
        this.keyWord = Main._PREFIX+keyWord;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        boolean isPrivateMessage = Objects.equals(event.getChannel().getType().toString(), "PRIVATE");
        if (event.getMessage().getContentRaw().startsWith(this.keyWord) && !event.getAuthor().isBot()){
            if (isPrivateMessage && this.isPrivateCommande)
            {

                if (!securityCheck(event.getAuthor())){

                    sendSecurityIssue(event.getAuthor());
                    return;
                }
                Logs.logTrace(event.getAuthor().getName(),event.getChannel().getName(),"PRIVATE",event.getMessage().getContentRaw());
                this.applyPrivateLogic(event);
            }else if (!isPrivateMessage && this.isGuildCommand)
            {
                if (!securityCheck(event.getAuthor(),event.getGuild())){
                    sendSecurityIssue(event.getAuthor());
                    Logs.logTrace(event.getAuthor().getName(),event.getChannel().getName(),event.getGuild().getName(),event.getMessage().getContentRaw());
                    return;
                }
                this.applyPublicLogic(event);
                Logs.logTrace(event.getAuthor().getName(),event.getChannel().getName(),event.getGuild().getName(),"Message Get");
            }

        }
    }

    private boolean securityCheck(User author, Guild guild) {
        //List<Member> authorized_member = guild.getMembersWithRoles();
        //authorized_member.contains(author);

        return true;
    }

    private boolean securityCheck(User author) {

        return true;
    }
    protected void applyPrivateLogic(@NotNull MessageReceivedEvent event){

    }

    protected void applyPublicLogic(@NotNull MessageReceivedEvent event){

    }


    private void sendSecurityIssue(User author) {
    }



    private void setUpSecurity(boolean isMenber, boolean isModo, boolean isAdmin){
        this.rigth = new HashMap<>();
        this.rigth.put("member",isMenber);
        this.rigth.put("modo",isModo);
        this.rigth.put("admin",isAdmin);
        this.rigth.put("YD",true);
    }
}
