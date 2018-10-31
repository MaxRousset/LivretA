package maxrousset.com.github.livreta;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
      getLogger().info("onEnable is called!");
      getServer().getPluginManager().registerEvents(new MyListener(), this);
    }

    @Override
    public void onDisable() {
      getLogger().info("onDisable is called!");
    }

}
