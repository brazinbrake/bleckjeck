import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final int BLACKJACK = 21;
    private static final int DEALERHIT= 16;
    private static final int STARTINGCURRENCY = 100;
    private static final String INVALIDMESSAGE = "Invalid Entry";

    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);
        int decks = 0;
        int currency = STARTINGCURRENCY;
        int bet = 0;

        boolean endGame = false;
        boolean validBet = false;
        boolean validDecks = false;

        /*
        *GAME SETUP
         */
        System.out.println("Welcome to BlackJack!");

        //loop through the deck prompt at least once and until bet is valid
        do
        {
            try
            {
                System.out.println("How many decks would you like to play with? 1, 2, 4, 6, or 8? " +
                        "(or enter -1 to exit): ");
                decks = input.nextInt();
            }
            catch (InputMismatchException e)
            {
                //catch the exc and fix the annoying scanner "bug"
                input.nextLine();
            }

            if (decks == -1)
            {
                break;
            }
            else
            {
                validDecks = (decks == 1 || decks == 2 || decks == 4 || decks == 6 || decks == 8);
                if (!validDecks)
                {
                    System.out.println(INVALIDMESSAGE);
                }
            }

        }while(!validDecks);
        //make the deck
        Deck deck = new Deck(decks);
        /*
         *END GAME SETUP
         */

        /*
        *START GAME LOOP
        */
        while(true)
        {
            if (decks == -1)
            {
                break;
            }
            System.out.println("User Currency: $" + currency);

            //loop through the bet prompt at least once and until bet is valid
            do
            {
                try
                {
                    //prompt
                    System.out.println("Enter your bet (or enter -1 to exit): $");
                    //get input
                    bet = input.nextInt();
                    //check input

                    //End game
                    if (bet == -1)
                    {
                        endGame = true;
                        break;
                    }
                    else
                    {
                        validBet = processBet(bet, currency);
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("DEALER: Wise guy, eh? How much are you betting?");
                    //catch the exc and fix the annoying scanner "bug"
                    input.nextLine();
                }
            } while(!validBet);
            //check that user bet is within range of user currency

            boolean playerWin = false;
            //play blackjack
            if (bet != -1)
            {
                playerWin = playRound(deck);
            }
            else
            {
                bet = 0;
            }


            //evaluate win/loss and perform relevant actions
            if (playerWin)
            {
                System.out.println("You win $" + bet);
                System.out.println("********************");
                currency += bet;
            }
            else
            {
                System.out.println("You lose $" + bet);
                System.out.println("********************");
                currency -= bet;
            }
            //check if user has lost all currency before loop restart
            if (currency == 0 || endGame)
            {
                endGameRoutine(currency);
                break;
            }

        }
        /*
         *END GAME LOOP
         */
    }//end main

    //Evaluate if a bet is valid (rtn true) or not (rtn false)
    private static boolean processBet(int bet, int currency) {
        //Invalid 0
        if (bet == 0)
        {
            System.out.println("*The pit boss comes up to you* PIT BOSS: Bet something or leave");
            return false;
        }
        //Invalid out of range
        else if (bet > currency || bet < -1)
        {
            System.out.println("DEALER: Wise guy, eh? How much are you betting?");
            return false;
        }
        //Valid
        else
        {
            return true;
        }

    }

    //BlackJack round logic
    static boolean playRound(Deck deck) throws Exception
    {
        Scanner input = new Scanner(System.in);

        //make the hands
        BlackJackHand player = new BlackJackHand();
        BlackJackHand dealer = new BlackJackHand();

        //deal the starting cards
        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());

        //check for dealer win (dealer wins ties)
        if (dealer.getBlackJackValue() == BLACKJACK)
        {
            System.out.println("Dealer got BlackJack");
            System.out.println("Dealer cards: " + dealer.getCard(0) + " and " + dealer.getCard(1));
            System.out.println("Your cards: " + player.getCard(0) + " and " + player.getCard(1));
            return false;
        }
        //check for player win
        if (player.getBlackJackValue() == BLACKJACK)
        {
            System.out.println("You got BlackJack!");
            System.out.println("Dealer cards: " + dealer.getCard(0) + " and " + dealer.getCard(1));
            System.out.println("Your cards: " + player.getCard(0) + " and " + player.getCard(1));
            return true;
        }
        //if neither has blackjack, continue
        while(true)
        {
            //display cards
            System.out.println("Dealer shows: " + dealer.getCard(0));
            System.out.print("Your cards: ");
            for (int i = 0; i < player.getCardCount(); i++)
            {
                System.out.print(player.getCard(i) + "   ");

            }
            System.out.println();

            boolean validChoice = false;
            //loop through until player stands or busts
            int playerChoice = 0;
            do
            {
                try
                {
                    System.out.println("Enter 1 to hit or 2 to stand");
                    playerChoice = input.nextInt();
                    validChoice = ((playerChoice == 1) || (playerChoice == 2));
                    //check input
                    //Invalid input
                }
                catch (InputMismatchException e)
                {
                    //catch the exc and fix the annoying scanner "bug"
                    input.nextLine();
                }
                if (!validChoice)
                {
                    System.out.println("DEALER: Come on. What'll it be? Hit or Stand?");
                }
            }while(!validChoice);

            //Stand
            if (playerChoice == 2)
            {
              break;
            }
            //Hit
            else
            {
                //draw and add
                Card hitCard = deck.dealCard();
                player.addCard(hitCard);
                System.out.println("DEALER: You hit with: " + hitCard);
                //evaluate
                if (player.getBlackJackValue() > 21)
                {
                    System.out.println("DEALER: Ha, You bust!");
                    return false;
                }
            }
        }//end while

        //dealer's turn when user stands
        System.out.println("Dealer cards: " + dealer.getCard(0) + " and " + dealer.getCard(1));
        //dealer will hit until greater than 16 AND greater than player value
        while ( dealer.getBlackJackValue() <= DEALERHIT && (dealer.getBlackJackValue() < player.getBlackJackValue()) )
        {
            Card hitCard = deck.dealCard();
            dealer.addCard(hitCard);
            System.out.println("Dealer hit with: " + hitCard);

            if (dealer.getBlackJackValue() > BLACKJACK)
            {
                System.out.println("Dealer Busts!");
                return true;
            }
        }//end while
        System.out.println("Dealer's total is: " + dealer.getBlackJackValue());
        System.out.println("Your total is: " + player.getBlackJackValue());
        //evaluate player and dealer not hitting blackjack
        //tie -- > dealer win
        if (dealer.getBlackJackValue() == player.getBlackJackValue())
        {
            System.out.println("Dealer wins ties");
            System.out.println("Dealer: I win!");
            return false;
        }
        //dealer win
        else if (dealer.getBlackJackValue() > player.getBlackJackValue())
        {
            System.out.println("Dealer: I win!");
            return false;
        }
        //player win
        else
        {
            System.out.println("You win!");
            return true;
        }

    }

    //Print the end results of the game
    private static void endGameRoutine(int currency)
    {
        System.out.println("********************");
        System.out.println("You walk away with $" + currency);
        boolean profited = currency > STARTINGCURRENCY;
        if (profited)
        {
            int profit = currency - STARTINGCURRENCY;
            System.out.println("Your net profit is $" + profit + ". You feel good! :D");
        }
        else if (currency == STARTINGCURRENCY)
        {
            System.out.println("Your net profit is $0. What a waste of time :/");
        }
        else
        {
            int loss = STARTINGCURRENCY - currency;
            System.out.println("Your net loss is $" + loss + ". You feel bad! :'(");
        }
    }
}
