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
			/* Detecte quand un joueur se login en utilisant authme */
			public void onPlayerJoin(PlayerJoinEvent event){
				Player player = event.getPlayer(); // Recupere le pseudo du joueur
				Date date = new Date(player.getLastPlayed()); // Recupere le jour de dernier co du joueur
				Double money = econ.getBalance(player); // Recupere l argent du joueur
				int res = money.compareTo(moneyMax); // Compare l argent du joueur a la somme max

				/* Si le joueur n est pas nouveau + a moin de 100 000$ + ne s est pas co aujourdhui*/
				if(player.hasPlayedBefore() && res == -1 && !date.toString().equals(new Date(System.currentTimeMillis()).toString())){
					Double interet = money*tauxInteret/new Double(100d);// Calcul des interets
					econ.depositPlayer(player, interet); // Rajoute interet a money du joueur
					player.sendMessage(message+Math.round(interet.floatValue())+"$ d'intérêts");/* Message destiné au joueur*/
				}
				else {}
				}
}
