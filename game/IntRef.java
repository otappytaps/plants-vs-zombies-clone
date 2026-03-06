package game;

public class IntRef 
{
    private int value;

    /**
     * Constructor for the IntRef class.
     * IntRef is a custom wrapper class that is made to simulate integer pointers.
     * @param value the value of the IntRef object.
     */

    public IntRef(int value)
    {
        this.value = value;
    }

    /**
     * Method for returning the value of the IntRef object.
     * @return the value attribute of the IntRef object
     */

    public int getValue()
    {
        return value;
    }

    /**
     * Method for modifying the value of the IntRef object
     * @param value the new value of the IntRef object
     */

    public void setValue(int value)
    {
        this.value = value;
    }
}