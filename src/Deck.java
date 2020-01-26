//This class represents a card "deck" of 52 playing card multiples
public class Deck
{
    private static final int CARDSINDECK = 52;

    //Array of cards that will form a deck
    private Card[] deck;
    //Counter for used cards;
    private int discard;
    //how many standard decks in this deck
    private int multiple;

    //Allowable numbers for deck constructor
    int[] allowableDecks = new int[]
            {
            1, 2, 4, 6, 8
            };

    //Construct a shuffled deck (or multiple decks of allowed number)
    public Deck(int number) throws DeckSizeException
    {
        this.multiple = number;
        boolean created = false;

        for (int n: allowableDecks)
            //check if a valid number
            if (n == number)
            {
                //Allocate enough space for the number of decks
                this.deck = new Card[number * CARDSINDECK];

                //initialize deck array index
                int index = 0;
                //loop for number of decks
                for (int i = 0; i < number; i++)
                {
                    //loop for suits
                    for (int s = 0; s < 4; s++ )
                    {
                        //loop for values
                        for (int v = 1; v < 14; v++)
                        {
                            this.deck[index] = new Card(v, s);
                            //go to next index
                            index++;
                        }
                    }
                }
                //shuffle the deck
                shuffle();
                //set necessary variables
                created = true;
                this.discard = 0;
            }
        //If creation unsuccessful, throw the exception
        if (!created)
        {
            String message = "Could not create deck with given size";
            throw new DeckSizeException(message);
        }

    } //end constructor

    public void shuffle()
    {
        for (int i = this.deck.length -1; i > 0; i--)
        {
            //initialize randomizer to new number
            int rand = (int)(Math.random()*(i+1));
            //randomize card locations
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
    }

    //pop a card from the deck
    public Card dealCard()
    {
        //check if deck empty
        if (getCardsRemaining() == 0)
        {
            shuffle();
        }
        discard++;
        return deck[discard - 1];
    }

    //get the size of the remaining draw pile
    public int getCardsRemaining()
    {
        return (multiple * CARDSINDECK - discard);
    }

} //end class