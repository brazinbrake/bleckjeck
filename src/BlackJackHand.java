//This class extends Hand class with a BlackJack-specific value getter
public class BlackJackHand extends Hand
{
    public int getBlackJackValue()
    {
        int val = 0;
        boolean ace = false;
        int cards = getCardCount();

        for (int i = 0; i < cards; i++)
        {
            Card c = getCard(i);
            int cardVal = c.getValue();

            //to correct for face card values
            if (cardVal > 10)
            {
                cardVal = 10;
            }
            //check if ace
            if (cardVal == 1)
            {
                ace = true;
            }
            val += cardVal;
        }
        //Evaluate ace at end
        //If there's an ace and changing it's value to 11 doesn't put us over...
        if (ace && (val + 10 < 22))
        {
            //correct the value of ace (add 10)
            val += 10;
        }
        return val;
    }
}
