package me.iblitzkriegi.vixio.effects.effChannel;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.MessageHistory;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.List;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/17/2016.
 */
public class EffPinMessage extends Effect {
    private Expression<String> id;
    @Override
    protected void execute(Event e) {
        try {
            String TxtChnl = ((EvntGuildMsgReceived) e).getEvtChannel();
            MessageHistory messageHistory = getAPI().getJDA().getTextChannelById(TxtChnl).getHistory();
            List<Message> messages = messageHistory.retrieve(2);
            for (Message s : messages) {
                if (s.getId().equalsIgnoreCase(id.getSingle(e))) {
                    s.pin();
                }
            }
        }catch (PermissionException x){
            Skript.warning(x.getLocalizedMessage());
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expr[0];
        return true;
    }
}