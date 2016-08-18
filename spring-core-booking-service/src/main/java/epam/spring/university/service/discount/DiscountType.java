package epam.spring.university.service.discount;

public enum DiscountType
{
    DATETIME(10), BIRTHDAY(5), VOLUME(50);

    public int getDiscount()
    {
        return discount;
    }

    private DiscountType(int discount)
    {
        this.discount = discount;
    }

    private int discount;

}
