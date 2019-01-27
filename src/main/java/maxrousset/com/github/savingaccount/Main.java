package maxrousset.com.github.savingaccount;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

    private static Economy econ = null;
    private static final Logger log = Logger.getLogger("Minecraft");
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public void onEnable() {
      if (!setupEconomy() ) {
          log.severe(String.format(ANSI_RED + "[%s] - Disabled due to no Vault dependency found!" + ANSI_RESET, getDescription().getName()));
          getServer().getPluginManager().disablePlugin(this);
          return;
      }

      // Check for authme
      if (!setupAuthme() ) {
        loadConfiguration();
        getServer().getPluginManager().registerEvents(new NoAuthListener(), this);

        System.out.print(ANSI_GREEN + "[SavingAccount] SavingAccount Enabled!" + ANSI_RESET);
      }
      else{
      loadConfiguration();
      getServer().getPluginManager().registerEvents(new MyListener(), this);

      System.out.print(ANSI_GREEN + "[SavingAccount] SavingAccount Enabled With Authme Integration !" + ANSI_RESET);
      }
    }

    @Override
    public void onDisable() {
      System.out.print(ANSI_GREEN + "[SavingAccount] SavingAccount Disabled!" + ANSI_RESET);
    }

    public void loadConfiguration(){
      //Creating defaults if no config.yml
      getConfig().options().copyDefaults(true);
      //Save the config whenever you manipulate it
      saveConfig();
    }

    private boolean setupEconomy() {
      if (getServer().getPluginManager().getPlugin("Vault") == null) {
          return false;
      }
      RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp == null) {
          return false;
      }
      econ = rsp.getProvider();
      return econ != null;
    }

    private boolean setupAuthme() {
      if (getServer().getPluginManager().getPlugin("AuthMe") == null) {
          return false;
      }
      return econ != null;
    }

}
