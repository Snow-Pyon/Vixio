package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

/**
 * Created by Blitz on 12/22/2016.
 */
@ExprAnnotation.Expression(
        name = "UserWithID",
        title = "User with ID",
        desc = "Get a User via their ID, can get any User on Discord",
        syntax = "user with id %string%",
        returntype = User.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprUserWithId extends SimpleExpression<User> {
    Expression<String> vUser;
    @Override
    protected User[] get(Event e) {
        return new User[]{getUser(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<String>) expressions[0];
        return true;
    }
    private User getUser(Event e){
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
            if(jda.getValue().getUserById(vUser.getSingle(e))!=null){
                return jda.getValue().getUserById(vUser.getSingle(e));
            }else{
                return jda.getValue().retrieveUserById(vUser.getSingle(e)).complete();
            }
        }
        return null;
    }
}
