/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anssi
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void KassaLuodaanOikein(){
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0,kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void KateisOstoKasvattaaKassaaEdullinen(){
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void KateisOstoKasvattaaKassaaMaukas(){
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void KateisOstoVaihtorahatOikeinEdullinen(){
        assertEquals(260, kassa.syoEdullisesti(500));
    }
    
    @Test
    public void KateisOstoVaihtorahatOikeinMaukas(){
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test 
    public void KateisOstoLounaidenMääräKasvaaEdullinen(){
        kassa.syoEdullisesti(2500);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test 
    public void KateisOstoLounaidenMääräKasvaaMaukas(){
        kassa.syoMaukkaasti(2500);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void RahatPalautetaanJosEiRiitäEdullinen(){
        kassa.syoEdullisesti(20);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(20, kassa.syoEdullisesti(20));
    }
    
    @Test
    public void RahatPalautetaanJosEiRiitäMaukas(){
        kassa.syoMaukkaasti(20);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(20, kassa.syoMaukkaasti(20));
    }
    
    @Test
    public void KorttiOstoToimiiEdullinen(){
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        kortti.otaRahaa(600);
        kassa.syoEdullisesti(kortti);
        assertEquals(160, kortti.saldo());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void KorttiOstoToimiiMaukas(){
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        kortti.otaRahaa(300);
        kassa.syoMaukkaasti(kortti);
        assertEquals(300, kortti.saldo());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void KortinLatausToimii(){
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
        assertEquals(99900, kassa.kassassaRahaa());
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(1100, kortti.saldo());
        assertEquals(99900, kassa.kassassaRahaa());
    }
}
