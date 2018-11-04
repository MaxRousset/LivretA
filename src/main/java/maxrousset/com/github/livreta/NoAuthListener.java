package maxrousset.com.github.livreta;

import java.sql.Date;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.milkbowl.vault.economy.Economy;

public class NoAuthListener implements Listener
{

	public NoAuthListener(){}
		private static RegisteredServiceProvider<Economy> rsp = JavaPlugin.getPlugin(Main.class).getServer().getServicesManager().getRegistration(Economy.class);
		private static Economy econ = rsp.getProvider();
		Double moneyMax = new Double(JavaPlugin.getPlugin(Main.class).getConfig().getInt("Conf.MontantMax"));  /* Recupere le montant max dans la conf */
		Double tauxInteret = new Double(JavaPlugin.getPlugin(Main.class).getConfig().getInt("Conf.TauxInteret")); /* Recupere le taux d interet dans la conf */
		String message = new String(JavaPlugin.getPlugin(Main.class).getConfig().getString("Conf.Msg")); /* Recupere le taux d interet dans la conf */

			@EventHandler
			/* Check for user login */
			public void onPlayerJoin(PlayerJoinEvent event){
				Player player = event.getPlayer(); // Get player name
				Date date = new Date(player.getLastPlayed()); // Get last played time of player
				Double money = econ.getBalance(player); // Get money of player
				int res = money.compareTo(moneyMax); // Compare player money to money max

				if(player.hasPlayedBefore() && res == -1 && !date.toString().equals(new Date(System.currentTimeMillis()).toString())){
					Double interet = money*tauxInteret/new Double(100d);//Calculate interest
					econ.depositPlayer(player, interet);// Add interest to player money
					player.sendMessage(message+Math.round(interet.floatValue())+"$ d'intérêts");// Broadcast message to player
				}
				else {}
				}
}
