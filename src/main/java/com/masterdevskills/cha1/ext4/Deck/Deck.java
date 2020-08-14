package com.masterdevskills.cha1.ext4.Deck;

import com.masterdevskills.cha1.ext4.Card.Card;
import com.masterdevskills.cha1.ext4.Player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface Deck {
    List<Card> getCards();

    Deck deckFactory();

    int size();

    void addCard(Card card);

    void addCards(List<Card> cards);

    void addDeck(Deck deck);

    void shuffle();

    void sort();

    void sort(Comparator<Card> c);

    String deckToString();

    Map<Player, List<Card>> deal()
            throws IllegalArgumentException;

    default void currentDeckPrinter(Map<Player, List<Card>> currentDeal) {
        currentDeal.forEach((player, cards) -> {
            System.out.println("---------- "+ player.name + " -------------");
            cards.forEach(card -> {
                String cardString =
                        card.getSuit() + ":" +
                        " | "
                        + card.getRank() + ":" + card.getRank().value();
                System.out.println(cardString);
            });
        });
    }
}
