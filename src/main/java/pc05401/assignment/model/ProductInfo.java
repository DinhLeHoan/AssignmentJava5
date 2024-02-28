package pc05401.assignment.model;
public class ProductInfo {
    private int productId;
    private int count;

    public ProductInfo(int productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}