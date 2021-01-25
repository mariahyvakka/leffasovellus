package Leffasovellus.test;
// Generated by ComTest BEGIN
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import Leffasovellus.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.27 13:24:41 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KatsotutTest {


  // Generated by ComTest BEGIN
  /** testLisaa33 */
  @Test
  public void testLisaa33() {    // Katsotut: 33
    Katsotut katsotut = new Katsotut(); 
    Katsottu kat = new Katsottu(), kat2 = new Katsottu(), kat3 = new Katsottu(); 
    assertEquals("From: Katsotut line: 36", 0, katsotut.getLkm()); 
    katsotut.lisaa(kat); assertEquals("From: Katsotut line: 37", 1, katsotut.getLkm()); 
    katsotut.lisaa(kat2); katsotut.lisaa(kat3); 
    assertEquals("From: Katsotut line: 39", 3, katsotut.getLkm()); 
  } // Generated by ComTest END
}