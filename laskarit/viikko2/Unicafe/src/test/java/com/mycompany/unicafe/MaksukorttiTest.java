package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void SaldoAlussaOikein(){
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void SaldonLatausToimii(){
        kortti.lataaRahaa(20);
        assertEquals(30, kortti.saldo());
    }
    
    @Test
    public void VähentääSaldoaOikein(){
        kortti.otaRahaa(10);
        assertEquals(0,kortti.saldo());
    }
    
    @Test
    public void SaldonEiMuutuJosEiRahaa(){
        kortti.otaRahaa(15);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void KortinTulostusToimii(){
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
