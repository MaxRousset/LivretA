package maxrousset.com.github.livreta;

import java.math.BigDecimal;
import java.sql.Date;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import fr.xephi.authme.events.LoginEvent;

public class MyListener implements Listener
{

	public MyListener(){}
		BigDecimal moneyMax = new BigDecimal(JavaPlugin.getPlugin(Main.class).getConfig().getInt("Conf.MontantMax"));  /* Recupere le montant max dans la conf */
		BigDecimal tauxInteret = new BigDecimal(JavaPlugin.getPlugin(Main.class).getConfig().getInt("Conf.TauxInteret")); /* Recupere le taux d interet dans la conf */

		@EventHandler
		/* Detecte quand un joueur se login en utilisant authme */
		public void onJoin(fr.xephi.authme.events.LoginEvent event){
			/* Recupere le pseudo du joueur*/
			Player player = event.getPlayer();
			try {
				Date date = new Date(player.getLastPlayed()); /* Recupere le jour de dernier co du joueur*/
				BigDecimal money = Economy.getMoneyExact(player.getName() ); /* Recupere l argent du joueur*/
				int res = money.compareTo(moneyMax);/* Compare l argent du joueur a la somme max*/

				/* Si le joueur n est pas nouveau + a moin de 100 000$ + ne s est pas co aujourdhui*/
				if(player.hasPlayedBefore() && res == -1 && !date.toString().equals(new Date(System.currentTimeMillis()).toString())){
					BigDecimal interet = money.multiply(tauxInteret).divide(new BigDecimal(100d));/* Calcul des interets */
					Economy.setMoney(player.getName(),money.add(interet));/* Rajoute interet a money du joueur*/
					player.sendMessage("§aDurant votre absence votre compte a rapporté "+Math.round(interet.floatValue())+"$ d'intérêts");/* Message destiné au joueur*/
				}
				else {
				}
			}
			catch (UserDoesNotExistException e){
				e.printStackTrace();
			}
			catch (NoLoanPermittedException e1){
				e1.printStackTrace();
			}
		}

}
