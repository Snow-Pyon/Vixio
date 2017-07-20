package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(
        name = "UsersWithRole",
        title = "Users with Role",
        desc = "Get the Users with a Role via the Role's Name and the Guilds ID",
        syntax = "[discord] users with role %string% in guild %string%",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprUsersWithRole extends SimpleExpression<List> {
    Expression<String> vRole;
    Expression<String> vGuild;

    @Override
    protected List[] get(Event e) {
        return new List[]{getUsersWithRole(e)};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends List> getReturnType() {
        return List.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vRole = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        return true;
    }

    private List getUsersWithRole(Event e) {
        for (Map.Entry<String, JDA> jda : bots.entrySet()) {
            if (jda.getValue().getGuildById(vGuild.getSingle(e)) != null) {
                Guild g = jda.getValue().getGuildById(vGuild.getSingle(e));
                Role vR = null;
                for (Role s : g.getRoles()) {
                    if (s.getName().equals(vRole.getSingle(e))) {
                        vR = s;
                    }
                }
                return g.getMembersWithRoles(vR);
            }
        }
        return null;
    }
}
