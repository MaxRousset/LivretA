package maxrousset.com.github.livreta;

import java.math.BigDecimal;
import java.sql.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import fr.xephi.authme.events.LoginEvent;

public class MyListener implements Listener
{
	public MyListener() {}
		@EventHandler
		/* Detecte quand un joueur se login en utilisant authme */
		public void onJoin(fr.xephi.authme.events.LoginEvent event){
			/* Recupere le pseudo du joueur*/
			Player player = event.getPlayer();
			try {
				Date date = new Date(player.getLastPlayed()); /* Recupere le jour de dernier co du joueur*/
				BigDecimal money = Economy.getMoneyExact(player.getName() ); /* Recupere l argent du joueur*/
				BigDecimal moneyMax = new BigDecimal("100000"); /* Recupere la somme max*/
				int res = money.compareTo(moneyMax);/* Compare l argent du joueur a la somme max*/

				/* Si le joueur n est pas nouveau + a moin de 100 000$ + ne s est pas co aujourdhui*/
				if(player.hasPlayedBefore() && res == -1 && !date.toString().equals(new Date(System.currentTimeMillis()).toString())) {

					Economy.setMoney(player.getName(),money.multiply(new BigDecimal(1.01d)));/* Rajoute 1% de money a moneyNew */
					BigDecimal moneyNew = Economy.getMoneyExact(player.getName());/* Change l argent du joueur en moneyNew */
					BigDecimal moneyDiff = moneyNew.subtract(money);/* Calcul la diference*/

					/* Message destiné au joueur*/
					player.sendMessage("§aDurant votre absence votre compte a rapporté "+Math.round(moneyDiff.floatValue())+"$ d'intérêts");
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
