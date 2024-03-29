package com.github.ewedj.drukowanie;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

public class WydrukFaktury {
    protected void drukujNaglowek(String numerFaktury) {
        System.out.println("==============================" + numerFaktury
                + "=================================");
    }

    protected void drukujPozycje(Map<Product, Integer> products) {
        products.forEach((product, quantity) -> {
            System.out.printf(
                    "| Towar: %s  | Ilosc: %d | Wartosc: %.2f PLN|\n",
                    product.getName(),
                    quantity,
                    product.getPriceWithTax().multiply(BigDecimal.valueOf(quantity))
            );

        });
    }

    protected void drukujStopke(int liczbaProduktow) {
        System.out.printf(
                "================================ Liczba pozycji: %d ==========================\n",
                liczbaProduktow
        );
    }

    public void drukujFakture(Invoice faktura) {
        if (faktura == null) {
            throw new RuntimeException("Unexpected error, Faktura is null!");
        }
        drukujNaglowek(String.valueOf(faktura.getNumber()));
        drukujPozycje(faktura.getProducts());
        drukujStopke(faktura.getProductsQuantity());
    }
}
