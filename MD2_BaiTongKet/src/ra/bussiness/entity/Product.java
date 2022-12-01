package ra.bussiness.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private String productId;
    private String productName;
    private float productPrice;
    private float productDiscount;
    private float productExportPrice;
    private String productTille;
    private String productDescriptions;
    private boolean productStatus;
    private List<Color> colors;
    private List<Size> sizes;
    private Catalog catalogs;

    public Product() {
    }

    public Product(String productId, String productName, float productPrice, float productDiscount, float productExportPrice, String productTille, String productDescriptions, boolean productStatus, List<Color> colors, List<Size> sizes, Catalog catalogs) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productExportPrice = productExportPrice;
        this.productTille = productTille;
        this.productDescriptions = productDescriptions;
        this.productStatus = productStatus;
        this.colors = colors;
        this.sizes = sizes;
        this.catalogs = catalogs;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public float getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(float productDiscount) {
        this.productDiscount = productDiscount;
    }

    public float getProductExportPrice() {
        return productExportPrice;
    }

    public void setProductExportPrice(float productExportPrice) {
        this.productExportPrice = productExportPrice;
    }

    public String getProductTille() {
        return productTille;
    }

    public void setProductTille(String productTille) {
        this.productTille = productTille;
    }

    public String getProductDescriptions() {
        return productDescriptions;
    }

    public void setProductDescriptions(String productDescriptions) {
        this.productDescriptions = productDescriptions;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public Catalog getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Catalog catalogs) {
        this.catalogs = catalogs;
    }
}
