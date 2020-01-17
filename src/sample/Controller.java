package sample;
import java.util.Random;
public class Controller {

    public int[][] calculateDamage(int quantity, int AC, String size){
        int[][] results = new int[2][quantity+1];
        int attackRoll = 0;
        int numDamageDice = 1;
        int damageDice = 0;
        int damageBonus = 0;
        int attackBonus = 0;

        if (size == null){
            return results;
        }
        else if (size.equals("Tiny")){
            attackBonus = 8;
            damageDice = 4;
            damageBonus = 4;
        }
        else if (size.equals("Small")){
            attackBonus = 6;
            damageDice = 8;
            damageBonus = 2;
        }
        else if (size.equals("Medium")){
            attackBonus = 5;
            damageDice = 6;
            damageBonus = 1;
            numDamageDice = 2;
        }
        else if (size.equals("Large")){
            attackBonus = 6;
            damageDice = 10;
            numDamageDice = 2;
            damageBonus = 2;
        }
        else if (size.equals("Huge")){
            attackBonus = 8;
            numDamageDice = 2;
            damageDice = 12;
            damageBonus = 4;
        }
        else {
            return results;
        }

        Random d20 = new Random();
        Random Damage = new Random();
        int damage = 0;
        for (int i = 0; i < quantity; i++) {
            damage = 0;
            attackRoll = d20.nextInt(20) + 1;
            results[0][i] = attackRoll;
            if (attackRoll == 20) {
                for (int diceNum = 0; diceNum < numDamageDice*2; diceNum++){
                    damage += Damage.nextInt(damageDice)+1;
                }
                damage += damageBonus*2;
                results[1][i] = damage;
            }
            else if (attackBonus+attackRoll >= AC){
                for (int diceNum = 0; diceNum < numDamageDice; diceNum++){
                    damage += Damage.nextInt(damageDice)+1;
                }
                damage += damageBonus;
                results[1][i] = damage;
            }
            else if (attackBonus+attackRoll < AC){
                results[1][i] = damage;
            }

            results[1][quantity] += damage;

        }

        return results;

    }
}
