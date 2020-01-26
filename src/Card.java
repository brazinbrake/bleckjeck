public class Card {

    //Declare card properties
    public  int value;
    public int suit;

    //Assign face card code values
    public final static int ACE = 1;
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    //public final static int KING = 13;

    //Assign suit constants
    public final static int SPADES = 0;
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;

    //Card constructor
    public Card(int value, int suit)
    {
        this.value = value;
        this.suit = suit;
    }

    //Getter methods
    public int getValue()
    {
        return this.value;
    }

    public int getSuit()
    {
        return this.suit;
    }

    public String getValueString()
    {
        if (value > 1 && value < 11)
        {
            return Integer.toString(value);
        }
        else
        {
            if (value == ACE)
            {
                return "Ace";
            }
            if (value == JACK)
            {
                return "Jack";
            }
            if (value == QUEEN)
            {
                return "Queen";
            }
            else
            {
                return "King";
            }
        }
    }

    public String getSuitString()
    {
        switch (suit)
        {
            case SPADES: return "Spades";
            case HEARTS: return "Hearts";
            case DIAMONDS: return "Diamonds";
            case CLUBS: return "Clubs";
            default: return "Unknown";

        }

    }

    public String toString()
    {
        return this.getValueString() + " of " + this.getSuitString();
    }
}