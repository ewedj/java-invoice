package com.github.ewedj.drukowanie;

import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WydrukFakturyTest {


    private WydrukFaktury wydrukFaktury;


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        wydrukFaktury = new WydrukFaktury();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testDrukujNaglowek() {
        wydrukFaktury.drukujNaglowek("ABCD2024");
        Assert.assertEquals("==============================ABCD2024=================================\n", outContent.toString());
    }

    @Test
    public void testDrukujPozycje() {
        Map<Product,Integer> products = new HashMap<>();
        products.put(new TaxFreeProduct("Tablet",new BigDecimal("1678")),23);
        products.put(new TaxFreeProduct("kaczka",new BigDecimal("2")),2);
        wydrukFaktury.drukujPozycje(products);
        Assert.assertEquals("| Towar: Tablet  | Ilosc: 23 | Wartosc: 38594,00 PLN|\n" +
                "| Towar: kaczka  | Ilosc: 2 | Wartosc: 4,00 PLN|\n", outContent.toString());
    }

    @Test
    public void testDrukujStopke() {

        wydrukFaktury.drukujStopke(123);
        Assert.assertEquals("================================ Liczba pozycji: 123 ==========================\n", outContent.toString());
    }

    @Test
    public void testDrukujFakture() {
        try {
            wydrukFaktury.drukujFakture(null);

            Assert.fail("Nie powinno tu dotrzec");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Unexpected error, Faktura is null!");
        }
    }
}