package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private final Map<Product, Integer> products = new HashMap<>();
//    private Collection<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("product cannot be null");
        this.products.put(product, 1);

    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("quantity cannot be negative");
        if (quantity == 0)
            throw new IllegalArgumentException("quantity cannot be zero");
        this.products.put(product, quantity);
    }


    public BigDecimal getNetPrice() {

        BigDecimal netPrice = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            BigDecimal quantity = BigDecimal.valueOf(entry.getValue());
            netPrice = netPrice.add(product.getPrice().multiply(quantity));
        }
        return netPrice;
    }

    public BigDecimal getTax() {

        return getTotal().subtract(getNetPrice());
    }

    public BigDecimal getTotal() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            BigDecimal quantity = BigDecimal.valueOf(entry.getValue());
            totalPrice = totalPrice.add(product.getPriceWithTax().multiply(quantity));
        }

        return totalPrice;
    }
}
