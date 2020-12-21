package cli;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SubcommandExecutor implements CommandExecutor{

    private final HashMap<String,CommandExecutor> executors = new HashMap<>();

    public void addCommandExecutor(String subcommand, CommandExecutor executor) {
        executors.put(subcommand, executor);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        String subcommandName = args[0];

        if(hasExecutor(subcommandName)){
            CommandExecutor executor = getExecutor(subcommandName);
            executor.onCommand(commandSender, command, s, (String[]) ArrayUtils.subarray(args,1,args.length));
            return true;
        }

        throw new NoExecutorForCommand(subcommandName);
    }

    private CommandExecutor getExecutor(String subcommand){
        return executors.get(subcommand);
    }

    public boolean hasExecutor(String subcommand) {
        return executors.containsKey(subcommand);
    }

    public static class NoExecutorForCommand extends RuntimeException{

        private final String subcommandName;

        public NoExecutorForCommand(String subcommandName){
            this.subcommandName = subcommandName;
        }

        public String getSubcommandName() {
            return subcommandName;
        }

        @Override
        public String getMessage(){
            return "No executor for subcommand \""+subcommandName+"\"!";
        }
    }
}
