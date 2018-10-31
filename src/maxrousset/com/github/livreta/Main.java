package maxrousset.com.github.livreta;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
      loadConfiguration();
      System.out.print("[LivretA] LivretA Enabled!");
      getServer().getPluginManager().registerEvents(new MyListener(), this);
    }

    @Override
    public void onDisable() {
      System.out.print("[LivretA] LivretA Disabled!");
    }

    public void loadConfiguration(){
    //See "Creating you're defaults"
    getConfig().options().copyDefaults(true); // NOTE: You do not have to use "" if the class extends the java plugin
    //Save the config whenever you manipulate it
    saveConfig();
    }

}
