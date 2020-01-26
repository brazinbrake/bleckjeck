import java.util.Vector;

//This class represents a blackjack hand (for both player and dealer)
//Vector used because speed shouldn't be an issue with so few card objects
public class Hand {

    private Vector hand;

    public Hand()
    {
        hand = new Vector();
    }

    public void addCard(Card c)
    {
        if (c != null)
        {
            hand.addElement(c);
        }
    }

    //get cards in hand
    public int getCardCount()
    {
        return hand.size();
    }

    public Card getCard(int position)
    {
        if (position >= 0 && position < hand.size())
        {
            return (Card)hand.elementAt(position);
        }
        else
        {
            return null;
        }
    }
}
