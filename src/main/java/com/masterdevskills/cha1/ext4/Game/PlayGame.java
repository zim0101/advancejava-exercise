package com.masterdevskills.cha1.ext4.Game;

import com.masterdevskills.cha1.ext4.Card.Card;
import com.masterdevskills.cha1.ext4.Deck.DictatorDeck;
import com.masterdevskills.cha1.ext4.Deck.DictatorDeck.Rules;
import com.masterdevskills.cha1.ext4.Player.Player;
import java.util.List;
import java.util.Map;

public class PlayGame {
    public static void main(String[] args) {
        List<Player> players = List.of(new Player("Donald Trump"),
                new Player("Aladdin"));

        DictatorDeck dictatorDeck = new DictatorDeck();
        boolean playerHasBeenSet = dictatorDeck.setPlayers(players);

        if (playerHasBeenSet) {
            for (int i = 1; i <= Rules.TOTAL_DEAL.getValue(); i++) {
                Map<Player, List<Card>> records = dictatorDeck.deal();
                dictatorDeck.currentDeckPrinter(records);
                dictatorDeck.result(records, i);
            }
        } else {
            System.out.println("Player entry failed");
        }

    }
}
