package com.masterdevskills.cha1.ext4.Deck;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.masterdevskills.cha1.ext4.Card.Card;
import com.masterdevskills.cha1.ext4.Card.PlayingCard;
import com.masterdevskills.cha1.ext4.Player.Player;

public class DictatorDeck implements Deck {

    public enum Rules {
        TOTAL_DEAL(2),
        TOTAL_PLAYER(2),
        CARDS_PER_PLAYER_PER_DEAL(4);

        private final int value;

        Rules(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * A deck contains all the cards in it
     */
    private final List<Card> entireDeck = new ArrayList<>();

    List<Player> players = new ArrayList<>(Rules.TOTAL_PLAYER.getValue());

    /**
     * Constructor for DictatorDeck
     */
    public DictatorDeck() {
        setDictatorDeckCards();
    }

    /**
     * Sort entireDeck
     */
    public void sort() {
        Collections.sort(entireDeck);
    }

    public boolean setPlayers(List<Player> players) {
        try {
            this.players.addAll(players);
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    /**
     * Sort entireDeck by Comparator
     *
     * @param c Comparator
     */
    @Override
    public void sort(final Comparator<Card> c) {
        entireDeck.sort(c);
    }

    /**
     * Generate string by the deck where we will find
     * all the cards
     *
     * @return String
     */
    @Override
    public String deckToString() {
        return entireDeck.toString();
    }

    @Override
    public List<Card> getCards() {
        return entireDeck;
    }

    @Override
    public Deck deckFactory() {
        return null;
    }

    @Override
    public int size() {
        return entireDeck.size();
    }

    /**
     * Add card into Deck
     *
     * @param card Single card
     */
    @Override
    public void addCard(final Card card) {
        entireDeck.add(card);
    }

    @Override
    public void addCards(final List<Card> cards) {
        entireDeck.addAll(cards);
    }

    @Override
    public void addDeck(final Deck deck) {}

    /**
     * Shuffle the cards of the deck
     */
    @Override
    public void shuffle() {
        Collections.shuffle(entireDeck);
    }

    /**
     * Each game in dictator card has 2 deal, in each deal the card will be
     * shuffled and then it will be equally distributed throughout the
     * players.
     *
     * @return Hash map of player and their card for the deal
     */
    @Override
    public Map<Player, List<Card>> deal() {
        Map<Player, List<Card>> deckMap = new HashMap<>();
        try {
            this.shuffle();
            this.players.forEach(player ->
                    deckMap.put(player, this.distributeCards()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deckMap;
    }

    /**
     * Card distribution for one player in a deal
     *
     * @return List of card for specific player in a deal
     */
    public List<Card> distributeCards() {
        List<Card> head = this.entireDeck.stream()
                .filter(card ->
                        entireDeck.indexOf(card)
                                < Rules.CARDS_PER_PLAYER_PER_DEAL.value
                ).collect(Collectors.toList());

        this.entireDeck.removeAll(head);

        return head;
    }

    /**
     * Dictator Card game has 4 ranks for each suit. ACE, KING, SPADE and HEARTS
     * This is the defined rule for the dictator game. After generating all
     * the cards will set all the cards into entireDeck at the very beginning
     * of the game.
     */
    public void setDictatorDeckCards() {
        List<Card.Suit> suitList = List.of(Card.Suit.DIAMONDS,
                Card.Suit.CLUBS, Card.Suit.SPADES, Card.Suit.HEARTS);
        List<Card.Rank> rankList = List.of(Card.Rank.ACE, Card.Rank.KING,
                Card.Rank.QUEEN, Card.Rank.JACK);

        suitList.forEach(suit -> rankList.forEach(rank ->
            addCard(new PlayingCard(suit, rank))));

        System.out.println("First Scenario of Deck " + this.entireDeck);
    }

    /**
     * Sum all the card values for each player to prepare the result
     * Then find the player with max points (card value)
     * Print his/her name
     *
     * @param deal Map of player and their cards
     * @param deck number of deck
     */
    public void result(Map<Player, List<Card>> deal, int deck) {
        Map<Player, Integer> results = this.generateResult(deal);
        Stream<Player> playerStream = this.findWinner(results);

        this.printWinnerName(playerStream, deck);
    }

    /**
     * Sum all the card values for each player to prepare the result
     *
     * @param deal Map of player and their cards
     * @return Map of player and their total points
     */
    public Map<Player, Integer> generateResult(Map<Player, List<Card>> deal) {
        Map<Player, Integer> results = new HashMap<>();
        deal.forEach((player, cards) -> results.put(player, cards.stream()
                .mapToInt(card -> card.getRank().value()).sum()));

        return results;
    }

    /**
     * Finds the player with max points
     *
     * @param results Map of player and their total points
     * @return stream of the player
     */
    public Stream<Player> findWinner(Map<Player, Integer> results) {
        return results.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue()
                                .equals(Collections.max(results.values()))
                ).map(Map.Entry::getKey);
    }

    /**
     * Print player's name if present from stream
     *
     * @param playerStream Stream of the player
     * @param deck number of deck
     */
    public void printWinnerName(Stream<Player> playerStream, int deck) {
        String announcement = "Winner of Deck-" + deck + " is: ";
        playerStream
                .findFirst()
                .ifPresentOrElse((player) -> System.out.println(announcement
                                + player.name),
                        () -> System.out.println("No winner"));
    }
}
